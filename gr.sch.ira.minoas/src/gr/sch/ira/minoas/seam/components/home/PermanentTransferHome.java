/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "permanentTransferHome")
public class PermanentTransferHome extends MinoasEntityHome<PermanentTransfer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transactional
	public String cancel() {
		clearInstance();
		return "updated";
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected PermanentTransfer createInstance() {
		PermanentTransfer instance = new PermanentTransfer();
		instance.setIsProcessed(Boolean.FALSE);
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "permanentTransfer", scope = ScopeType.PAGE)
	public PermanentTransfer getInstance() {
		// TODO Auto-generated method stub
		return (PermanentTransfer) super.getInstance();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {

		return super.persist();
	}

	public void updateValueDueToEmployee() {
		Employee selectedEmployee = getInstance().getEmployee();
		if (selectedEmployee != null) {
			info("updating values due to employee modification #0", selectedEmployee);
			PermanentTransfer instance = getInstance();
			Employment employment = instance.getEmployee().getCurrentEmployment();
			if(employment!=null && employment.getSchool()!=null) {
				instance.setSourcePYSDE(employment.getSchool().getPysde());
				instance.setSourceUnit(employment.getSchool());
				instance.setSourceRegionCode(employment.getSchool().getRegionCode());
			}
			//instance.setSourcePYSDE(employment.getSchool().getPysde());
			//instance.setSourceSchool(employment.getSchool());
		} else {
			warn("updateValueDueToEmployee called, but no employee is selected.");
		}
	}

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to outstanding improvement #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {

		return super.update();
	}

}
