package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.util.Calendar;
import java.util.Date;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employmentHome")
@Scope(ScopeType.CONVERSATION)
public class EmploymentHome extends MinoasEntityHome<Employment> {

	@In(create = true)
	private EmployeeHome employeeHome;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employment", scope = ScopeType.PAGE)
	public Employment getInstance() {
		return (Employment) super.getInstance();
	}

	@Transactional
	public String revert() {
		if (isManaged()) {
			getEntityManager().refresh(getInstance());
			return "reverted";
		} else
			return null;
	}

	/**
	 * Used to prepare the instance as a new regular employment
	 * for an existing employee
	 */
	@Transactional(TransactionPropagationType.REQUIRED)
	public void prepareForNewRegularEmploymentOfEmployee() {
		if (isManaged()) {
			this.clearInstance();
		}
		if (!isManaged()) {
			if (employeeHome.isManaged()) {
				Employment instance = getInstance();
				Employee employee = employeeHome.getInstance();

				/* Retrieve current employment */
				Employment currentEmployment = employee.getCurrentEmployment();
				instance.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
				instance.setEmployee(employee);
				instance.setType(EmploymentType.REGULAR);
				instance.setSpecialization(employee.getLastSpecialization());
				instance.setFinalWorkingHours(currentEmployment.getFinalWorkingHours());
				instance.setMandatoryWorkingHours(currentEmployment.getMandatoryWorkingHours());

			} else
				throw new RuntimeException("employee home is not managed");
		} else
			throw new RuntimeException("employment home is managed, we need a fresh copy");
	}

	@Transactional
	public String insertNewRegularEmploymentOfEmployee() {
		if (!isManaged()) {
			joinTransaction();
			Employment instance = getInstance();
			Employee employee = getEntityManager().merge(employeeHome.getInstance());
			/* Retrieve current employment */
			Employment currentEmployment = getEntityManager().merge(employee.getCurrentEmployment());

			/* check if the current employment overlaps with
			 * the new employment
			 */
			Date currentEmploymentDueTo = DateUtils.truncate(instance.getEstablished(), Calendar.HOUR_OF_DAY);
			currentEmploymentDueTo = DateUtils.addDays(currentEmploymentDueTo, -1);

			Date currentEmploymentEstablished = DateUtils.truncate(currentEmployment.getEstablished(),
					Calendar.HOUR_OF_DAY);

			Date newEmploymentEstablished = DateUtils.truncate(instance.getEstablished(), Calendar.HOUR_OF_DAY);

			/* checks if the new employment really starts after the current employment */
			if (!newEmploymentEstablished.after(currentEmploymentEstablished)) {
				facesMessages
						.add(Severity.ERROR,
								"H ημ/νια ανάληψης υπηρεσίας πρέπει να είναι μεταγενέστερη. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
				return null;
			}

			instance.setActive(Boolean.TRUE);
			instance.setEmployee(employee);
			instance.setInsertedBy(getPrincipal());
			String lala = super.persist();
			if (lala != null) {
				currentEmployment.setActive(Boolean.FALSE);
				currentEmployment.setSupersededBy(instance);
				currentEmployment.setTerminated(currentEmploymentDueTo);
				employee.setCurrentEmployment(instance);

				getEntityManager().merge(currentEmployment);
				getEntityManager().merge(employee);
				getEntityManager().flush();
			}
			return lala;
		} else
			throw new RuntimeException("employment home is managed, we need a fresh copy");
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		/* Employment Update is special. We create a new employment and set the
		 * current employment as parent.
		 */
		joinTransaction();
		Employment current_employment = getInstance();
		Employee employee = current_employment.getEmployee();
		Employment new_employment = new Employment();

		/* clone the employment */
		new_employment.setEmployee(current_employment.getEmployee());
		new_employment.setActive(Boolean.TRUE);
		new_employment.setEstablished(current_employment.getEstablished());
		new_employment.setFinalWorkingHours(current_employment.getFinalWorkingHours());
		new_employment.setMandatoryWorkingHours(current_employment.getMandatoryWorkingHours());
		new_employment.setSchool(current_employment.getSchool());
		new_employment.setSchoolYear(current_employment.getSchoolYear());
		new_employment.setInsertedOn(new Date(System.currentTimeMillis()));
		new_employment.setSpecialization(current_employment.getSpecialization());
		new_employment.setType(current_employment.getType());
		new_employment.setSecondment(current_employment.getSecondment());
		getEntityManager().persist(new_employment);

		/* get a fresh copy of the current employment */
		getEntityManager().refresh(current_employment);

		/* update the current (now old) employment */

		current_employment.setSupersededBy(new_employment);
		current_employment.setActive(Boolean.FALSE);
		current_employment.setTerminated(new_employment.getEstablished());

		/* update the employee */
		employee.setCurrentEmployment(new_employment);
		employee.setLastSpecialization(new_employment.getSpecialization());

		return super.update();
	}

}
