package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Penalty;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.PenaltyHome;

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

@Name(value = "penaltyManagement")
@Scope(ScopeType.PAGE)
public class PenaltyManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	@In(required=false)
	private PenaltyHome penaltyHome;
	
	/**
	 * Employees penalty history
	 */
	@DataModel(scope=ScopeType.PAGE, value="penaltyHistory")
	private Collection<Penalty> penaltyHistory = null;
	
	
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
     * @return the penaltyHistory
     */
    public Collection<Penalty> getPenaltyHistory() {
        return penaltyHistory;
    }

    /**
     * @param penaltyHistory the penaltyHistory to set
     */
    public void setPenaltyHistory(Collection<Penalty> penaltyHistory) {
        this.penaltyHistory = penaltyHistory;
    }


    public String modifyPenalty() {
        if(penaltyHome != null && penaltyHome.isManaged()) {
            info("trying to modify penalty #0", penaltyHome);
            /* check if the penalty dates are allowed. */
            Penalty penalty = penaltyHome.getInstance();
            if(!validatePenaltyDates(penalty, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
            	penaltyHome.update();
            	info("employee's #0 penalty #1 has been updated!", penaltyHome.getInstance().getEmployee(), penaltyHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "penalty home #0 is not managed, thus penalty #1 can't be modified.", penaltyHome, penaltyHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    public String deletePenalty() {
        if(penaltyHome != null && penaltyHome.isManaged()) {
            info("trying to delete penalty #0", penaltyHome);
            Penalty pnlt = penaltyHome.getInstance();
            pnlt.setDeleted(Boolean.TRUE);
            pnlt.setDeletedOn(new Date());
            pnlt.setDeletedBy(getPrincipal());
            penaltyHome.update();
            constructPenaltyHistory();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "penalty home #0 is not managed, thus penalty #1 can't be deleted.", penaltyHome, penaltyHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    
    }
    @Transactional
    public String createPenalty() {
        if(penaltyHome != null && (!penaltyHome.isManaged())) {
            info("trying to modify penalty #0", penaltyHome);
            /* check if the penalty dates are allowed. */
            
            Penalty penalty = penaltyHome.getInstance();
            if(!validatePenaltyDates(penalty, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
            	penalty.setEmployee(getEmployeeHome().getInstance());
            	penalty.setInsertedBy(getPrincipal());
            	penalty.setInsertedOn(new Date());
            	penaltyHome.persist();
				constructPenaltyHistory();
				info("employee's #0 penalty #1 has been created!", penaltyHome.getInstance().getEmployee(), penaltyHome.getInstance());                
				return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "penalty home #0 is not managed, thus penalty #1 can't be created.", penaltyHome, penaltyHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }

	
	@Factory(value="penaltyHistory", autoCreate=true)
	public void constructPenaltyHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing penalty history for employee #0", employee);
	    setPenaltyHistory(coreSearching.getPenaltyHistory(employee));
	}
	
    
	/**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
    protected boolean validatePenaltyDates(Penalty penalty, boolean addMessages) {
        Date penaltyStartDate = DateUtils.truncate(penalty.getPenaltyStartDate(), Calendar.DAY_OF_MONTH);
        Date penaltyEndDate = DateUtils.truncate(penalty.getPenaltyEndDate(), Calendar.DAY_OF_MONTH);
        /* check if the dates are correct */
        if (penaltyStartDate.after(penaltyEndDate)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "H ημ/νία έναρξης της ποινής είναι μεταγενέστερη της ημ/νίας λήξης της.");
            return false;
        }

        Collection<Penalty> penaltyList = getCoreSearching().getPenaltyHistory(employeeHome.getInstance());
        for (Penalty current_penalty : penaltyList) {
            if (current_penalty.getId().equals(penalty.getId()))
                continue;
            Date currentPnltStartDate = DateUtils.truncate(current_penalty.getPenaltyStartDate(), Calendar.DAY_OF_MONTH);
            Date currentPnltEndDate = DateUtils.truncate(current_penalty.getPenaltyEndDate(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(penaltyStartDate, currentPnltStartDate) || DateUtils.isSameDay(penaltyEndDate, currentPnltEndDate)) {
                if (addMessages)
                    facesMessages.add(Severity.ERROR,
                            "Υπάρχει ήδη ποινή με τις ημερομηνίες που εισάγατε.");
                return false;
            }

            if (DateUtils.isSameDay(penaltyStartDate, currentPnltEndDate)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νία έναρξης της ποινής πρέπει να είναι μεταγενέστερη της ημ/νίας λήξης προηγούμενης ποινής.");
                return false;
            }

            if ((penaltyStartDate.before(currentPnltStartDate) && penaltyEndDate.after(currentPnltStartDate))
                    || (penaltyStartDate.after(currentPnltStartDate) && penaltyEndDate.before(currentPnltEndDate))
                    || (penaltyStartDate.before(currentPnltEndDate) && penaltyEndDate.after(currentPnltEndDate))) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η περίοδος της ποινής που ορίσατε επικαλύπτει προηγούμενες περιόδους ποινών. Παρακαλώ ορίστε διαφορετική περίοδο ποινής.");
                return false;
            }

        }

        return true;

    }
	

}
