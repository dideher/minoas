package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.EmployeeExclusion;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeExclusionHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.OutstandingImprovementHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "outstandingImprovementManagement")
@Scope(ScopeType.CONVERSATION)
public class OutstandingImprovementManagement extends BaseDatabaseAwareSeamComponent {

	@In(required = true, create = true)
	private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;

	@In(required = true, create = true)
	private OutstandingImprovementHome outstandingImprovementHome;

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
	 * @return the outstandingImprovementHome
	 */
	public OutstandingImprovementHome getOutstandingImprovementHome() {
		return outstandingImprovementHome;
	}

	/**
	 * @param outstandingImprovementHome the outstandingImprovementHome to set
	 */
	public void setOutstandingImprovementHome(OutstandingImprovementHome outstandingImprovementHome) {
		this.outstandingImprovementHome = outstandingImprovementHome;
	}

	protected boolean isOutstandingImprovementValid(OutstandingImprovement improvement) {
		boolean result = true;
		if (!(improvement.getSourceSchool().getRegionCode().equals(improvement.getTargetSchool().getRegionCode()))) {
			result = false;
			facesMessages.add(Severity.ERROR,
					"Η σχολική μονάδα βελτίωσεις πρέπει να είναι στην ίδια περιοχή με την οργανική.");
		}
		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String updateImprovement() {
		if (outstandingImprovementHome.isManaged()) {
			OutstandingImprovement instance = getOutstandingImprovementHome().getInstance();
			if (isOutstandingImprovementValid(instance)) {
				getOutstandingImprovementHome().update();
				return ACTION_OUTCOME_SUCCESS;
			} else
				return ACTION_OUTCOME_FAILURE;

		} else {
			facesMessages.add(Severity.ERROR, "Outstanding Improvement #0 is *NOT* managed.",
					outstandingImprovementHome);
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String createNewImprovement() {
		info("new improvment");
		if (!outstandingImprovementHome.isManaged()) {
			OutstandingImprovement instance = getOutstandingImprovementHome().getInstance();
			instance.setEmployeeRegistryID(instance.getEmployee().getRegularDetail().getRegistryID());
			instance.setSchoolYear(instance.getEmployee().getCurrentEmployment().getSchoolYear());
			instance.setImprovementRegionCode(instance.getTargetSchool().getRegionCode());
			instance.setTargetPYSDE(instance.getTargetSchool().getPysde());
			instance.setEffectiveDate(coreSearching.getActiveSchoolYear(getEntityManager()).getSchoolYearStop());
			if (isOutstandingImprovementValid(instance)) {
				getOutstandingImprovementHome().persist();
				return ACTION_OUTCOME_SUCCESS;
			} else
				return ACTION_OUTCOME_FAILURE;
		} else {
			facesMessages.add(Severity.ERROR, "Outstanding Improvement #0 is managed.", outstandingImprovementHome);
			return ACTION_OUTCOME_FAILURE;
		}

	}

}
