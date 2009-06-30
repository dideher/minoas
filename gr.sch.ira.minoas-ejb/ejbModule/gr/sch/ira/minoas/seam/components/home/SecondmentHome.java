package gr.sch.ira.minoas.seam.components.home;



import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.CoreSearching;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	@In()
	private CoreSearching coreSearching;

	@In(create = true)
	private EmployeeHome employeeHome;

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Transactional
	@Override
	public String persist() {
		Employee employee = employeeHome.getInstance();
		Secondment newSecondment = getInstance();
		info("trying to add new secondment #0 for employee #1,", newSecondment, employee);
		Employment currentEmployment = employee.getCurrentEmployment();
		Secondment currentSecondment = currentEmployment != null ? currentEmployment.getSecondment() : null;
		newSecondment.setSchoolYear(coreSearching.getActiveSchoolYear());
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setEmployee(employee);
		newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
		if (currentEmployment != null) {
			newSecondment.setAffectedEmployment(currentEmployment);

		}

		if (currentSecondment != null) {
			currentSecondment.setActive(Boolean.FALSE);
			currentSecondment.setSupersededBy(newSecondment);
			getEntityManager().merge(currentSecondment);
		}
		info("successfully registered new secondment #0 for employee #1,", newSecondment, employee);
		return super.persist();
	}

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
		super.update();
		clearInstance();
		return "updated";
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
		Secondment secondment =(Secondment)super.getInstance();
		secondment.setSecondmentType(SecondmentType.FULL_TO_SCHOOL);
		return secondment;
	}

}
