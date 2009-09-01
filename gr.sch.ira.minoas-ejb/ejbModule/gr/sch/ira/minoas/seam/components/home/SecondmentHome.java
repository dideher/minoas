package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private EmployeeHome employeeHome;

	protected boolean validateSecondment(Secondment secondment, boolean addMessages) {
		/* source & target unit must not be same */

		if (secondment.getSourceUnit() != null
				&& secondment.getSourceUnit().getId().equals(secondment.getTargetUnit().getId())) {
			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Η μονάδα αποσπάσης πρέπει να είναι διαφορετική απο την τρέχουσα οργανική του εκπαιδευτικού. Καφέ ήπιες ;");
			return false;
		}
		/* check if the dates are correct */
		if (secondment.getEstablished().after(secondment.getDueTo())) {

			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Η ημ/νια λήξης της απόσπασης πρέπει να είναι μεταγενέστερη της έναρξης. Μήπως να κάνεις ένα διάλειμα ;");
			return false;
		}
		return true;

	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Transactional
	@Override
	public String persist() {

		Employee employee = employeeHome.getInstance();
		Employment currentEmployment = employee.getCurrentEmployment();
		Secondment currentSecondment = currentEmployment != null ? currentEmployment.getSecondment() : null;
		Secondment newSecondment = getInstance();
		/* get some checking first */
		if (!validateSecondment(newSecondment, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}
		newSecondment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setEmployee(employee);
		newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
		newSecondment.setInsertedBy(getPrincipal());
		if (currentEmployment != null) {
			newSecondment.setAffectedEmployment(currentEmployment);
			currentEmployment.setSecondment(newSecondment);
		}

		/*
		 * if there is a current secondment, disabled it and inform the user
		 */
		if (currentSecondment != null) {
			currentSecondment.setActive(Boolean.FALSE);
			currentSecondment.setSupersededBy(newSecondment);
			getEntityManager().merge(currentSecondment);
			facesMessages
					.add(
							Severity.WARN,
							"Για τον εκπαιδευτικό #0 ο Μίνωας είχε καταχωρημένη και άλλη ενεργή απόσπαση στην μονάδα #1 με λήξη την #2, η οποία όμως ακυρώθηκε.",
							(employee.getLastName() + " " + employee.getFirstName()), currentSecondment.getTargetUnit()
									.getTitle(), currentSecondment.getDueTo());
		}
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		Secondment current_secondment = getInstance();
		if (!validateSecondment(current_secondment, true)) {
			return VALIDATION_ERROR_OUTCOME;
		} else
			return super.update();

	}

	@Transactional
	public String cancel() {
		Secondment current_secondment = getInstance();
		current_secondment.setActive(Boolean.FALSE);
		current_secondment.setAffectedEmployment(null);
		Employee employee = current_secondment.getEmployee();

		Employment employment = current_secondment.getAffectedEmployment();
		if (employment != null)
			employment.setSecondment(null);
		super.update();
		info("principal '#0' canceled employee #1 current secondment #1.", getPrincipalName(), employee,
				current_secondment);
		clearInstance();
		return "updated";
	}

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to secondment #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "secondment", scope = ScopeType.PAGE)
	public Secondment getInstance() {
		return (Secondment) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Secondment instance = new Secondment();
		instance.setSecondmentType(SecondmentType.FULL_TO_SCHOOL);
		instance.setEmployeeRequested(Boolean.TRUE);
		instance
				.setEstablished(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStart());
		instance.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
		return instance;
	}

}
