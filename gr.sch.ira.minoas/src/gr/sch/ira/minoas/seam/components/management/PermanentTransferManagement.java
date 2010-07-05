package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.PermanentTransferHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "permanentTransferManagement")
@Scope(ScopeType.CONVERSATION)
public class PermanentTransferManagement extends BaseDatabaseAwareSeamComponent {

	@In(required = true, create = true)
	private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;

	@In(required = true, create = true)
	private PermanentTransferHome permanentTransferHome;

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

	

	protected boolean isPermanentTransferValid(PermanentTransfer improvement) {
		boolean result = true;
		/*
		if (!(improvement.getSourceSchool().getRegionCode().equals(improvement.getTargetSchool().getRegionCode()))) {
			result = false;
			facesMessages.add(Severity.ERROR,
					"Η σχολική μονάδα βελτίωσεις πρέπει να είναι στην ίδια περιοχή με την οργανική.");
		}
		*/
		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String updatePermanentTransfer() {
		if (permanentTransferHome.isManaged()) {
			PermanentTransfer instance = getPermanentTransferHome().getInstance();
			if (isPermanentTransferValid(instance)) {
				getPermanentTransferHome().update();
				return ACTION_OUTCOME_SUCCESS;
			} else
				return ACTION_OUTCOME_FAILURE;

		} else {
			facesMessages.add(Severity.ERROR, "Permanent Transfer #0 is *NOT* managed.",
					permanentTransferHome);
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String createNewPermanentTransfer() {
		info("new permanent transfer");
		if (!permanentTransferHome.isManaged()) {
			PermanentTransfer instance = getPermanentTransferHome().getInstance();
			instance.setEmployeeRegistryID(instance.getEmployee().getRegularDetail().getRegistryID());
			instance.setSchoolYear(instance.getEmployee().getCurrentEmployment().getSchoolYear());
			//instance.setImprovementRegionCode(instance.getTargetSchool().getRegionCode());
			instance.setTargetPYSDE(instance.getTargetUnit().getPysde());
			if(instance.getTargetUnit() instanceof School) {
				instance.setTargetRegionCode(((School)instance.getTargetUnit()).getRegionCode());
			}
			instance.setEffectiveDate(coreSearching.getActiveSchoolYear(getEntityManager()).getSchoolYearStop());
			if (isPermanentTransferValid(instance)) {
				getPermanentTransferHome().persist();
				return ACTION_OUTCOME_SUCCESS;
			} else
				return ACTION_OUTCOME_FAILURE;
		} else {
			facesMessages.add(Severity.ERROR, "Permanent Transfer #0 is managed.", permanentTransferHome);
			return ACTION_OUTCOME_FAILURE;
		}

	}

	/**
	 * @return the permanentTransferHome
	 */
	public PermanentTransferHome getPermanentTransferHome() {
		return permanentTransferHome;
	}

	/**
	 * @param permanentTransferHome the permanentTransferHome to set
	 */
	public void setPermanentTransferHome(PermanentTransferHome permanentTransferHome) {
		this.permanentTransferHome = permanentTransferHome;
	}

}
