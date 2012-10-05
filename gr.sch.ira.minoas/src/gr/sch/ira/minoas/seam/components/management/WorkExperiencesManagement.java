package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.WorkExperienceHome;

import java.util.Collection;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "workExperiencesManagement")
@Scope(ScopeType.PAGE)
public class WorkExperiencesManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	@In(required=false)
	private WorkExperienceHome workExperienceHome;
	
	/**
	 * Employees current leave history
	 */
	@DataModel(scope=ScopeType.PAGE, value="workExperienceHistory")
	private Collection<WorkExperience> workExperienceHistory = null;
	
	@Factory(value="workExperienceHistory",autoCreate=true)
	public void constructWorkExperienceHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing work experience history for employee #0", employee);
	    setWorkExperienceHistory(coreSearching.getWorkExperienceHistory(employee));
	}
	
	
	
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
    public Collection<WorkExperience> getWorkExperienceHistory() {
        return workExperienceHistory;
    }

    /**
     * @param workExperienceHistory the workExperienceHistory to set
     */
    public void setWorkExperienceHistory(Collection<WorkExperience> workExperienceHistory) {
        this.workExperienceHistory = workExperienceHistory;
    }


    public String modifyWorkExperience() {
        if(workExperienceHome != null && workExperienceHome.isManaged()) {
            info("trying to modify work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
            	facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                info("employee's #0 work experience #1 has been updated!", workExperienceHome.getInstance().getEmployee(), workExperienceHome.getInstance());
                workExperienceHome.update();
                return ACTION_OUTCOME_SUCCESS;
            }
            
            
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is not managed, thus #1 work experience can't be modified.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    public String deleteWorkExperience() {
        if(workExperienceHome != null && workExperienceHome.isManaged()) {
            info("trying to delete work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            workExp.setActive(Boolean.FALSE);
            workExp.setDeleted(Boolean.TRUE);
            workExp.setDeletedOn(new Date());
            workExp.setDeletedBy(getPrincipal());
            workExperienceHome.update();
            constructWorkExperienceHistory();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is not managed, thus #1 work experience can't be deleted.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    
    }
    @Transactional
    public String createWorkExperience() {
        if(workExperienceHome != null && (!workExperienceHome.isManaged())) {
            info("trying to modify work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
                facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις εισάγατε, δεν είναι αποδεκτές.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                info("employee's #0 work experience #1 has been updated!", workExperienceHome.getInstance().getEmployee(), workExperienceHome.getInstance());
                
                workExp.setActive(Boolean.TRUE);
                workExp.setEmployee(getEmployeeHome().getInstance());
                workExp.setInsertedBy(getPrincipal());
                workExp.setInsertedOn(new Date());
                workExp.setCalendarExperienceDays(1000); /* we need to compute this correctly */
                workExperienceHome.persist();
                constructWorkExperienceHistory();
                
                return ACTION_OUTCOME_SUCCESS;
            }
            
            
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is managed, thus #1 work experience can't be created.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }

    /* this method is called when the user clicks the "add new leave" */
    public void prepareNewWorkExperience() {
    	workExperienceHome.clearInstance();
    	WorkExperience workExp = workExperienceHome.getInstance();
    	workExp.setFromDate(new Date());
    	workExp.setToDate(new Date());
    	workExp.setComment(null);
    	workExp.setEmployee(employeeHome.getInstance());

    }

}
