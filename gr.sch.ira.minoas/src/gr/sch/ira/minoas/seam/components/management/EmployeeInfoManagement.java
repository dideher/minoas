package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeInfoHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Scope(ScopeType.PAGE)
@Name(value = "employeeInfoManagement")
public class EmployeeInfoManagement extends BaseDatabaseAwareSeamComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(required = true)
	private CoreSearching coreSearching;

	@In(required = true, create = true)
	EmployeeHome employeeHome;
	
	@In(required = false)
	EmployeeInfoHome employeeInfoHome;


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
	 * @return the coreSearching
	 */
	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	/**
	 * @param coreSearching the coreSearching to set
	 */
	public void setCoreSearching(CoreSearching coreSearching) {
		this.coreSearching = coreSearching;
	}

	/**
	 * @return the employeeInfoHome
	 */
	public EmployeeInfoHome getEmployeeInfoHome() {
		return employeeInfoHome;
	}

	/**
	 * @param employeeInfoHome the employeeInfoHome to set
	 */
	public void setEmployeeInfoHome(EmployeeInfoHome employeeInfoHome) {
		this.employeeInfoHome = employeeInfoHome;
	}

	
	public String modifyEmployeeInfo() {
        if(employeeInfoHome != null && employeeInfoHome.isManaged()) {
            info("trying to modify work experience #0", employeeInfoHome);
            /* check if the work experience dates are allowed. */
//            EmployeeInfo employeeInfo = employeeInfoHome.getInstance();
//            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
//            	facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές.");
//                return ACTION_OUTCOME_FAILURE;
//            } else {
                info("employee's #0 employee info #1 has been updated!", employeeInfoHome.getInstance().getEmployee(), employeeInfoHome.getInstance());
                employeeInfoHome.update();
                return ACTION_OUTCOME_SUCCESS;
//            }
        } else {
            facesMessages.add(Severity.ERROR, "employeeInfo home #0 is not managed, thus #1 can't be modified.", employeeInfoHome, employeeInfoHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
}
