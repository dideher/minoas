package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		/* Secondment Update is special. We create a new Secondment and set the
		 * current employment as parent.
		 */
		try {
			joinTransaction();
			Secondment current_secondment = getInstance();
			Employment affected_employment = current_secondment.getAffectedEmployment();
			Secondment newSecondment = (Secondment) current_secondment.clone();

			/* revert the current secondment */
			getEntityManager().refresh(current_secondment);
			current_secondment.setActive(Boolean.FALSE);
			current_secondment.setSupersededBy(newSecondment);
			if (affected_employment != null) {
				affected_employment.setSecondment(newSecondment);
			}

			getEntityManager().persist(newSecondment);
			getEntityManager().merge(current_secondment);
			getEntityManager().merge(affected_employment);
			return super.update();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	@Transactional
	public String cancel() {
		joinTransaction();
		Secondment current_secondment = getInstance();
		Employee employee = current_secondment.getEmployee();
		Employment employment = current_secondment.getAffectedEmployment();
		info("trying to cancel employee #0 current secondment #1.", employee, current_secondment);
		current_secondment.setActive(Boolean.FALSE);
		getEntityManager().merge(current_secondment);
		if (employment != null)
			employment.setSecondment(null);
		return super.update();
	}

	@Transactional
	public String revert() {
		info("reverting updates to secondment #0", getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		return super.createInstance();
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "secondment", scope = ScopeType.PAGE)
	public Secondment getInstance() {
		return (Secondment) super.getInstance();
	}

}
