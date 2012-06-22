package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Evaluation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EvaluationHome;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "evaluationsManagement")
@Scope(ScopeType.PAGE)
public class EvaluationsManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	@In(required=false)
	private EvaluationHome evaluationHome;
	
	/**
	 * Employees current leave history
	 */
	@DataModel(scope=ScopeType.PAGE, value="evaluationHistory")
	private Collection<Evaluation> evaluationHistory = null;
	
	
	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}
	
    /**
     * @return the workExperienceHistory
     */
    public Collection<Evaluation> getEvaluationHistory() {
        return evaluationHistory;
    }

    /**
     * @param workExperienceHistory the workExperienceHistory to set
     */
    public void setEvaluationHistory(Collection<Evaluation> evaluationHistory) {
        this.evaluationHistory = evaluationHistory;
    }


    public String modifyEvaluation() {
        if(evaluationHome != null && evaluationHome.isManaged()) {
            info("trying to modify evaluation #0", evaluationHome);
            /* check if the evaluation dates are allowed. */
            Evaluation evaluation = evaluationHome.getInstance();
            if(!validateEvaluationDates(evaluation, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
            	evaluationHome.update();
            	info("employee's #0 evaluation #1 has been updated!", evaluationHome.getInstance().getEmployee(), evaluationHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "evaluation home #0 is not managed, thus evaluation #1 can't be modified.", evaluationHome, evaluationHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    public String deleteEvaluation() {
        if(evaluationHome != null && evaluationHome.isManaged()) {
            info("trying to delete evaluation #0", evaluationHome);
            Evaluation eval = evaluationHome.getInstance();
            eval.setDeleted(Boolean.TRUE);
            eval.setDeletedOn(new Date());
            eval.setDeletedBy(getPrincipal());
            evaluationHome.update();
            constructEvaluationHistory();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "evaluation home #0 is not managed, thus evaluation #1 can't be deleted.", evaluationHome, evaluationHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    
    }
    @Transactional
    public String createEvaluation() {
        if(evaluationHome != null && (!evaluationHome.isManaged())) {
            info("trying to modify evaluation #0", evaluationHome);
            /* check if the evaluation dates are allowed. */
            
            Evaluation evaluation = evaluationHome.getInstance();
            if(!validateEvaluationDates(evaluation, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
				evaluation.setEmployee(getEmployeeHome().getInstance());
				evaluation.setInsertedBy(getPrincipal());
				evaluation.setInsertedOn(new Date());
				evaluationHome.persist();
				constructEvaluationHistory();
				info("employee's #0 evaluation #1 has been created!", evaluationHome.getInstance().getEmployee(), evaluationHome.getInstance());                
				return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "evaluation home #0 is not managed, thus evaluation #1 can't be created.", evaluationHome, evaluationHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }

	
	@Factory(value="evaluationHistory",autoCreate=true)
	public void constructEvaluationHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing evaluation history for employee #0", employee);
	    setEvaluationHistory(coreSearching.getEvaluationHistory(employee));
	}
	
    
	/**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
    protected boolean validateEvaluationDates(Evaluation evaluation, boolean addMessages) {
        Date evaluationStartDate = DateUtils.truncate(evaluation.getEvaluationStartDate(), Calendar.DAY_OF_MONTH);
        Date evaluationEndDate = DateUtils.truncate(evaluation.getEvaluationEndDate(), Calendar.DAY_OF_MONTH);
        /* check if the dates are correct */
        if (evaluationStartDate.after(evaluationEndDate)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "H ημ/νία έναρξης της περιόδου αξιολόγησης είναι μεταγενέστερη της ημ/νιας λήξης της.");
            return false;
        }

        Collection<Evaluation> evaluationsList = getCoreSearching().getEvaluationHistory(employeeHome.getInstance());
        for (Evaluation current_evaluation : evaluationsList) {
            if (current_evaluation.getId().equals(evaluation.getId()))
                continue;
            Date currentEvalStartDate = DateUtils.truncate(current_evaluation.getEvaluationStartDate(), Calendar.DAY_OF_MONTH);
            Date currentEvalEndDate = DateUtils.truncate(current_evaluation.getEvaluationEndDate(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(evaluationStartDate, currentEvalStartDate) || DateUtils.isSameDay(evaluationEndDate, currentEvalEndDate)) {
                if (addMessages)
                    facesMessages.add(Severity.ERROR,
                            "Υπάρχει ήδη αξιολόγηση με τις ημερομηνίες που εισάγατε.");
                return false;
            }

            if (DateUtils.isSameDay(evaluationStartDate, currentEvalEndDate)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νία έναρξης της περιόδου αξιολόγησης πρέπει να είναι μεταγενέστερη της ημ/νιας λήξης προηγούμενης περιόδου αξιολόγησης.");
                return false;
            }

            if ((evaluationStartDate.before(currentEvalStartDate) && evaluationEndDate.after(currentEvalStartDate))
                    || (evaluationStartDate.after(currentEvalStartDate) && evaluationEndDate.before(currentEvalEndDate))
                    || (evaluationStartDate.before(currentEvalEndDate) && evaluationEndDate.after(currentEvalEndDate))) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η περίοδος που αξιολόγησης που ορίσατε επικαλύπτει προηγούμενες περιόδους αξιολόγησης. Παρακαλώ ορίστε διαφορετική περίοδο αξιολόγησης.");
                return false;
            }

        }

        return true;

    }
	

}
