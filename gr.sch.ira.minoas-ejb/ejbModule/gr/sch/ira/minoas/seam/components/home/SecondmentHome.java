package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.event.ValueChangeEvent;

import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	public void checkSecondmentEndDate(ValueChangeEvent e) {
		Date newEndDate = (Date) e.getNewValue();
		if (newEndDate.before(getInstance().getEstablished())) {
			facesMessages.addToControl(e.getComponent().getId(),
					"Η ημ/νια λήξης της απόσπασης πρέπει να είναι μεταγενέστερη της έναρξης.");
		}
	}

	public void checkSecondmentDueDate(ValueChangeEvent e) {
		Secondment secondment = getInstance();
		if (secondment.getEstablished() != null) {
			Date newEndDate = (Date) e.getNewValue();
			if (newEndDate.before(secondment.getEstablished())) {
				facesMessages.addToControl(e.getComponent().getId(),
						"Η ημ/νια λήξης της απόσπασης πρέπει να είναι μεταγενέστερη της έναρξης.");
			}
		}
	}

	@In(create = true)
	private EmployeeHome employeeHome;

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
		newSecondment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setEmployee(employee);
		newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
		newSecondment.setInsertedBy(getPrincipal());
		if (currentEmployment != null) {
			newSecondment.setAffectedEmployment(currentEmployment);
		}

		if (currentSecondment != null) {
			currentSecondment.setActive(Boolean.FALSE);
			currentSecondment.setSupersededBy(newSecondment);
			getEntityManager().merge(currentSecondment);

		}
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
		current_secondment.setActive(Boolean.FALSE);
		getEntityManager().merge(current_secondment);
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
		info("reverting updates to secondment #0", getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "secondment", scope = ScopeType.PAGE)
	public Secondment getInstance() {
		Secondment secondment = (Secondment) super.getInstance();
		secondment.setSecondmentType(SecondmentType.FULL_TO_SCHOOL);
		secondment.setEmployeeRequested(Boolean.TRUE);
		return secondment;
	}

}
