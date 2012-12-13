/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "outstandingImprovementHome")
public class OutstandingImprovementHome extends MinoasEntityHome<OutstandingImprovement> {

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
	protected OutstandingImprovement createInstance() {
		OutstandingImprovement instance = new OutstandingImprovement();
		instance.setIsProcessed(Boolean.FALSE);
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "outstandingImprovement", scope = ScopeType.PAGE)
	public OutstandingImprovement getInstance() {
		// TODO Auto-generated method stub
		return (OutstandingImprovement) super.getInstance();
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
			OutstandingImprovement instance = getInstance();
			Employment employment = instance.getEmployee().getCurrentEmployment();
			instance.setSourcePYSDE(employment.getSchool().getPysde());
			instance.setSourceSchool(employment.getSchool());
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
