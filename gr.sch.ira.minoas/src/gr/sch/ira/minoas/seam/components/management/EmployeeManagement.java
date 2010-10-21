package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.EmployeeExclusion;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.EmployeeMergeRequest;
import gr.sch.ira.minoas.seam.components.home.EmployeeExclusionHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeManagement")
@Scope(ScopeType.CONVERSATION)
public class EmployeeManagement extends BaseDatabaseAwareSeamComponent {

	@In(required = true, create = true)
	private EmployeeHome employeeHome;

	@In(required = true, create = true)
	private EmployeeExclusionHome employeeExclusionHome;
	
	@In(required = true, create = true)
	private EmployeeMergeRequest employeeMergeRequest;
	
	
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
	
	@Transactional(TransactionPropagationType.REQUIRED)
	public String removeEmployeeFromExclusionList() {
		if (getEmployeeExclusionHome().isManaged()) {
			getEmployeeHome().getInstance().setExclusion(null);
			getEmployeeHome().update();
			getEmployeeExclusionHome().remove();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee Exclusion #0 is not managed.", getEmployeeExclusionHome());
			return ACTION_OUTCOME_FAILURE;
		}

	}
	
	@Transactional(TransactionPropagationType.REQUIRED)
	public String mergeEmployees() {
		EmployeeHome sourceEmployeeHome = new EmployeeHome();
		sourceEmployeeHome.setId(getEmployeeMergeRequest().getSourceEmployee().getId());
		
		EmployeeHome targetEmployeeHome = new EmployeeHome();
		targetEmployeeHome.setId(getEmployeeMergeRequest().getTargetEmployee().getId());
		if(sourceEmployeeHome.isManaged() && targetEmployeeHome.isManaged()) {
			info("trying to merge employee #0 to #1", sourceEmployeeHome.getInstance(), targetEmployeeHome.getInstance());
			
			/* employees must not be same */
			if(sourceEmployeeHome.getId().equals(targetEmployeeHome.getId())) {
				facesMessages.add(Severity.ERROR, "Δεν μπορείτε να συνχωνεύσεται τον εκπαιδευτικό στον εαυτό του. Kάντε ενα διάλλειμα για καφε και τσιγάρο.");
				return ACTION_OUTCOME_FAILURE;
			}
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee #0 is not managed.");
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String addEmployeeToExclusionList() {
		if (getEmployeeHome().isManaged()) {
			getEmployeeExclusionHome().clearInstance();
			EmployeeExclusion instance = getEmployeeExclusionHome().getInstance();
			instance.setEmployee(getEmployeeHome().getInstance());
			getEmployeeExclusionHome().persist();
			getEmployeeHome().getInstance().setExclusion(instance);
			getEmployeeHome().update();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee #0 is not managed.");
			return ACTION_OUTCOME_FAILURE;
		}

	}
	
	@Transactional
	public String transferEmployee() {
		Employment newEmployment = new Employment();
		return null;
	}

	/**
	 * @return the employeeExclusionHome
	 */
	public EmployeeExclusionHome getEmployeeExclusionHome() {
		return employeeExclusionHome;
	}

	/**
	 * @param employeeExclusionHome the employeeExclusionHome to set
	 */
	public void setEmployeeExclusionHome(EmployeeExclusionHome employeeExclusionHome) {
		this.employeeExclusionHome = employeeExclusionHome;
	}

	/**
	 * @return the employeeMergeRequest
	 */
	public EmployeeMergeRequest getEmployeeMergeRequest() {
		return employeeMergeRequest;
	}

	/**
	 * @param employeeMergeRequest the employeeMergeRequest to set
	 */
	public void setEmployeeMergeRequest(EmployeeMergeRequest employeeMergeRequest) {
		this.employeeMergeRequest = employeeMergeRequest;
	}

}
