package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.EmployeeMergeRequest;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeMergeManagement")
@Scope(ScopeType.CONVERSATION)
public class EmployeeMergeManagement extends BaseDatabaseAwareSeamComponent {

	@In(required = true, create = true)
	private EmployeeMergeRequest employeeMergeRequest;
	
	
	public void prepareForEmployeeMerge() {
		info("prepearing employee merge request");
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
