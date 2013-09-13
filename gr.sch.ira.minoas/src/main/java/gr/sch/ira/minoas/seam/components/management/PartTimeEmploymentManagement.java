package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.PartTimeEmployment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.PartTimeEmploymentHome;

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

@Name(value = "partTimeEmploymentManagement")
@Scope(ScopeType.PAGE)
public class PartTimeEmploymentManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	@In(required=false)
	private PartTimeEmploymentHome partTimeEmploymentHome;
	
	/**
	 * Employees part time employment history
	 */
	@DataModel(scope=ScopeType.PAGE, value="partTimeEmploymentHistory")
	private Collection<PartTimeEmployment> partTimeEmploymentHistory = null;
	
	
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
	 * @return the partTimeEmploymentHome
	 */
	public PartTimeEmploymentHome getPartTimeEmploymentHome() {
		return partTimeEmploymentHome;
	}

	/**
	 * @param partTimeEmploymentHome the partTimeEmploymentHome to set
	 */
	public void setPartTimeEmploymentHome(
			PartTimeEmploymentHome partTimeEmploymentHome) {
		this.partTimeEmploymentHome = partTimeEmploymentHome;
	}

	/**
	 * @return the partTimeEmploymentHistory
	 */
	public Collection<PartTimeEmployment> getPartTimeEmploymentHistory() {
		return partTimeEmploymentHistory;
	}

	/**
	 * @param partTimeEmploymentHistory the partTimeEmploymentHistory to set
	 */
	public void setPartTimeEmploymentHistory(
			Collection<PartTimeEmployment> partTimeEmploymentHistory) {
		this.partTimeEmploymentHistory = partTimeEmploymentHistory;
	}

	public String modifyPartTimeEmployment() {
        if(partTimeEmploymentHome != null && partTimeEmploymentHome.isManaged()) {
            info("trying to modify part-time employment #0", partTimeEmploymentHome);
            /* check if the part-time employment dates are allowed. */
            PartTimeEmployment partTimeEmployment = partTimeEmploymentHome.getInstance();
            if(!validatePartTimeEmploymentDates(partTimeEmployment, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
            	partTimeEmploymentHome.update();
            	info("employee's #0 part-time employment #1 has been updated!", partTimeEmploymentHome.getInstance().getEmployee(), partTimeEmploymentHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "part-time employment home #0 is not managed, thus part-time employment #1 can't be modified.", partTimeEmploymentHome, partTimeEmploymentHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    public String deletePartTimeEmployment() {
        if(partTimeEmploymentHome != null && partTimeEmploymentHome.isManaged()) {
            info("trying to delete part time employment #0", partTimeEmploymentHome);
            PartTimeEmployment partTE = partTimeEmploymentHome.getInstance();
            partTE.setDeleted(Boolean.TRUE);
            partTE.setDeletedOn(new Date());
            partTE.setDeletedBy(getPrincipal());
            partTimeEmploymentHome.update();
            constructPartTimeEmploymentHistory();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "part-time employment home #0 is not managed, thus part-time employment #1 can't be deleted.", partTimeEmploymentHome, partTimeEmploymentHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    
    }
    @Transactional
    public String createPartTimeEmployment() {
        if(partTimeEmploymentHome != null && (!partTimeEmploymentHome.isManaged())) {
            info("trying to modify part-time employment #0", partTimeEmploymentHome);
            /* check if the part-time employment's dates are allowed. */
            
            PartTimeEmployment partTimeEmployment = partTimeEmploymentHome.getInstance();
            if(!validatePartTimeEmploymentDates(partTimeEmployment, true)){
            	return ACTION_OUTCOME_FAILURE;
            }else {
            	partTimeEmployment.setEmployee(getEmployeeHome().getInstance());
            	partTimeEmployment.setInsertedBy(getPrincipal());
            	partTimeEmployment.setInsertedOn(new Date());
            	partTimeEmploymentHome.persist();
				constructPartTimeEmploymentHistory();
				info("employee's #0 part-time employment #1 has been created!", partTimeEmploymentHome.getInstance().getEmployee(), partTimeEmploymentHome.getInstance());                
				return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "part-time employment home #0 is not managed, thus part-time employment #1 can't be created.", partTimeEmploymentHome, partTimeEmploymentHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }

	
	@Factory(value="partTimeEmploymentHistory",autoCreate=true)
	public void constructPartTimeEmploymentHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing part-time emloyment history for employee #0", employee);
	    setPartTimeEmploymentHistory(coreSearching.getPartTimeEmploymentHistory(employee));
	}
	
    
	/**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
    protected boolean validatePartTimeEmploymentDates(PartTimeEmployment partTimeEmployment, boolean addMessages) {
        Date startDate = DateUtils.truncate(partTimeEmployment.getStartDate(), Calendar.DAY_OF_MONTH);
        Date endDate = DateUtils.truncate(partTimeEmployment.getEndDate(), Calendar.DAY_OF_MONTH);
        /* check if the dates are correct */
        if (startDate.after(endDate)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "H ημ/νία έναρξης της περιόδου είναι μεταγενέστερη της ημ/νίας λήξης της.");
            return false;
        }

        Collection<PartTimeEmployment> partTimeEmploymentsList = getCoreSearching().getPartTimeEmploymentHistory(employeeHome.getInstance());
        for (PartTimeEmployment current_partTimeEmployment : partTimeEmploymentsList) {
            if (current_partTimeEmployment.getId().equals(partTimeEmployment.getId()))
                continue;
            Date currentStartDate = DateUtils.truncate(current_partTimeEmployment.getStartDate(), Calendar.DAY_OF_MONTH);
            Date currentEndDate = DateUtils.truncate(current_partTimeEmployment.getEndDate(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(startDate, currentStartDate) || DateUtils.isSameDay(endDate, currentEndDate)) {
                if (addMessages)
                    facesMessages.add(Severity.ERROR,
                            "Υπάρχει ήδη περίοδος με τις ημερομηνίες που εισάγατε.");
                return false;
            }

            if (DateUtils.isSameDay(startDate, currentEndDate)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νία έναρξης της περιόδου πρέπει να είναι μεταγενέστερη της ημ/νίας λήξης προηγούμενης περιόδου.");
                return false;
            }

            if ((startDate.before(currentStartDate) && endDate.after(currentStartDate))
                    || (startDate.after(currentStartDate) && endDate.before(currentEndDate))
                    || (startDate.before(currentEndDate) && endDate.after(currentEndDate))) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η περίοδος που ορίσατε επικαλύπτει προηγούμενες περιόδους. Παρακαλώ ορίστε διαφορετική περίοδο.");
                return false;
            }

        }

        return true;

    }
	

}
