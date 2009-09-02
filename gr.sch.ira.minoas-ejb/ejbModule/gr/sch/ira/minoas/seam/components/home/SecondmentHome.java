package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
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

	public void prepareForNewSecondment() {
		this.clearInstance();
		employeeHome.clearInstance();
	}

	protected boolean validateSecondment(Secondment secondment, boolean addMessages) {

		Date established = DateUtils.truncate(secondment.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(secondment.getDueTo(), Calendar.DAY_OF_MONTH);
		/* check if the dates are correct */
		if (established.after(dueTo)) {

			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Η ημερομηνία λήξης της απόσπασης πρέπει να είναι μεταγενέστερη της ένερξης. Κάνε ένα διάλειμα για καφέ !");
			return false;
		}
		/* source & target unit must not be same */

		if (secondment.getSourceUnit() != null
				&& secondment.getSourceUnit().getId().equals(secondment.getTargetUnit().getId())) {
			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Η μονάδα αποσπάσης πρέπει να είναι διαφορετική απο την τρέχουσα οργανική του εκπαιδευτικού. Καφέ ήπιες ;");
			return false;
		}
		Collection<Secondment> current_secondments = getCoreSearching().getEmployeeSecondments(
				employeeHome.getInstance());
		for (Secondment current_secondment : current_secondments) {
			if (current_secondment.getId().equals(secondment.getId()))
				continue;
			Date current_established = DateUtils.truncate(current_secondment.getEstablished(), Calendar.DAY_OF_MONTH);
			Date current_dueTo = DateUtils.truncate(current_secondment.getDueTo(), Calendar.DAY_OF_MONTH);
			if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
				if (addMessages)
					facesMessages
							.add(
									Severity.ERROR,
									"Υπάρχει ήδει καταχωρημένη απόσπαση για τον εκπαιδευτικό με τις ημερομηνίες που εισάγατε. Κάντε ενα διάλειμα να ξεσκωτίσεται.");
				return false;
			}

			if (DateUtils.isSameDay(established, current_dueTo)) {

				if (addMessages)
					facesMessages
							.add(Severity.ERROR,
									"Η ημ/νια έναρξης της απόσπασης πρέπει να είναι μεταγενέστερη της λήξης της προηγούμενης απόσπασης.");
				return false;
			}

			if ((established.before(current_established) && dueTo.after(current_established))
					|| (established.after(current_established) && dueTo.before(current_dueTo))
					|| (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
				if (addMessages)
					facesMessages
							.add(Severity.ERROR,
									"Υπάρχει επικαλυπτόμενο διάστημα της νέας απόσπασης με παλαιότερη. Μήπως να κάνεις ενα διάλειμα ;");
				return false;
			}

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

		Date established = DateUtils.truncate(newSecondment.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(newSecondment.getDueTo(), Calendar.DAY_OF_MONTH);
		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

		/* get some checking first */
		if (!validateSecondment(newSecondment, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}
		newSecondment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
		newSecondment.setInsertedBy(getPrincipal());

		employee.addSecondment(newSecondment);
		/*
		 * check if the secondment should be set as the employee's current secondment. A
		 * leave can be set as the employee's current secondment if and only if the
		 * secondments's period is current (ie, today is after and before leave's
		 * established and dueTo dates respectively).
		 */
		if (currentEmployment != null) {
			if (today.after(established) && today.before(dueTo)) {
				currentEmployment.setSecondment(newSecondment);
			}
			newSecondment.setAffectedEmployment(currentEmployment);

		}

		//
		//		/*
		//		 * if there is a current secondment, disabled it and inform the user
		//		 */
		//		if (currentSecondment != null) {
		//			currentSecondment.setActive(Boolean.FALSE);
		//			currentSecondment.setSupersededBy(newSecondment);
		//			getEntityManager().merge(currentSecondment);
		//			facesMessages
		//					.add(
		//							Severity.WARN,
		//							"Για τον εκπαιδευτικό #0 ο Μίνωας είχε καταχωρημένη και άλλη ενεργή απόσπαση στην μονάδα #1 με λήξη την #2, η οποία όμως ακυρώθηκε.",
		//							(employee.getLastName() + " " + employee
		//									.getFirstName()), currentSecondment
		//									.getTargetUnit().getTitle(),
		//							currentSecondment.getDueTo());
		//		}
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		Secondment current_secondment = getInstance();
		Employment employment = current_secondment.getAffectedEmployment();
		Date established = DateUtils.truncate(current_secondment.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(current_secondment.getDueTo(), Calendar.DAY_OF_MONTH);
		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
		if (!validateSecondment(current_secondment, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}

		/*
		 * check if the secondment should be set as the employee's current
		 * leave. A secondment can be set as the employee's current leave if and
		 * only if the secondment's period is current (ie, today is after and
		 * before secondment's established and dueTo dates respectively).
		 */
		if (employment != null) {
			if (today.after(established) && today.before(dueTo)) {

				employment.setSecondment(current_secondment);

			} else {
				/*
				 * if the current secodnment is not the employee's current
				 * secondment, then check if the leave secondment to be the
				 * employee's current secodment and if so, remove it.
				 */
				if (employment.getSecondment() != null
						&& employment.getSecondment().getId().equals(current_secondment.getId())) {
					employment.setSecondment(null);
				}

			}
		}
		return super.update();

	}

	@Transactional
	public String cancel() {
		Secondment current_secondment = getInstance();
		Employee employee = current_secondment.getEmployee();
		current_secondment.setActive(Boolean.FALSE);

		Employment employment = current_secondment.getAffectedEmployment();
		if (employment != null) {
			/*
			 * if the canceled secondment is the employee's current secondment
			 * then update the employee as well.
			 */
			if (employment.getSecondment().getId().equals(current_secondment.getId()))
				employment.setSecondment(null);
		}
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
