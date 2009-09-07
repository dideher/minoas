package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;

import java.util.Date;

import javax.management.RuntimeErrorException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeHome")
@Scope(ScopeType.CONVERSATION)
public class EmployeeHome extends MinoasEntityHome<Employee> {

	@In(create = true)
	private SecondmentHome secondmentHome;

	@In(create = true)
	private RegularEmployeeInfoHome regularEmployeeInfoHome;

	@In(create = true)
	private EmploymentHome employmentHome;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employee", scope = ScopeType.PAGE)
	public Employee getInstance() {
		return (Employee) super.getInstance();

	}

	public boolean hasEmployment() {
		return getInstance().getCurrentEmployment() != null;
	}

	public boolean hasRegularEmployment() {
		return hasEmployment() && getInstance().getCurrentEmployment().getType().equals(EmploymentType.REGULAR);
	}

	public boolean hasDeputyEmployment() {
		return hasEmployment() && getInstance().getCurrentEmployment().getType().equals(EmploymentType.DEPUTY);
	}

	@Transactional
	public String addNewRegularEmployment(Employment employment) {
		joinTransaction();
		Employee employee = getInstance();
		Employment old_employment = employee.getCurrentEmployment();

		/* update the new emploment */
		employment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		employment.setEmployee(employee);
		employment.setActive(Boolean.TRUE);
		getEntityManager().persist(employment);

		/* update the employee */
		employee.setCurrentEmployment(employment);
		employee.setLastSpecialization(employment.getSpecialization());

		if (old_employment != null) {
			/* modify the current employment */
			old_employment.setActive(Boolean.FALSE);
			old_employment.setTerminated(new Date(System.currentTimeMillis()));
			old_employment.setSupersededBy(employment);
		}
		getEntityManager().flush();
		raiseAfterTransactionSuccessEvent();
		return "added";
	}

	@Transactional
	public boolean wire() {
		if (!secondmentHome.isManaged()) {
			Employee employee = getInstance();
			Secondment newSecondment = secondmentHome.getInstance();
			Employment currentEmployment = employee.getCurrentEmployment();
			if (currentEmployment != null) {
				newSecondment.setSourceUnit(currentEmployment.getSchool());
				newSecondment.setMandatoryWorkingHours(currentEmployment.getMandatoryWorkingHours());
				newSecondment.setFinalWorkingHours(currentEmployment.getFinalWorkingHours());
			} else {
				newSecondment.setSourceUnit(employee.getCurrentPYSDE().getRepresentedByUnit());
			}
		}
		return true;
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		Employee employee = getInstance();
		employee.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}

	public String addNewEmployeeInLocalPYSDE() {
		if (employmentHome.isManaged() || regularEmployeeInfoHome.isManaged()) {
			throw new RuntimeException("employment home or employeeRegularInfo is managed.");
		}

		Employee new_employee = getInstance();
		new_employee.setActive(Boolean.TRUE);
		new_employee.setCurrentPYSDE(getCoreSearching().getLocalPYSDE(getEntityManager()));

		RegularEmployeeInfo info = regularEmployeeInfoHome.getInstance();
		info.setInsertedBy(getPrincipal());
		getEntityManager().persist(info);

		Employment employment = employmentHome.getInstance();
		employment.setEmployee(new_employee);
		employment.setActive(Boolean.TRUE);
		employment.setInsertedBy(getPrincipal());
		employment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		employment.setSpecialization(new_employee.getLastSpecialization());
		switch (new_employee.getType()) {
		case DEPUTY:
			employment.setType(EmploymentType.DEPUTY);
			break;
		case HOURLYPAID:
			employment.setType(EmploymentType.HOURLYBASED);
			break;
		case REGULAR:
			employment.setType(EmploymentType.REGULAR);
			break;
		}
		getEntityManager().persist(employment);

		/* wire things */

		new_employee.setRegularDetail(info);
		new_employee.setCurrentEmployment(employment);

		return persist();
	}

	@Transactional
	public String addNewEmployeeFromOtherPYSDE() {
		/*
		 * we will quickly create an employee to be used for secondment
		 */
		Employee new_employee = getInstance();
		new_employee.setActive(Boolean.TRUE);
		if (!regularEmployeeInfoHome.isManaged()) {
			RegularEmployeeInfo info = regularEmployeeInfoHome.getInstance();
			info.setInsertedBy(getPrincipal());
			new_employee.setRegularDetail(info);

		}
		wire();
		return persist();
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Employee new_instance = new Employee();
		new_instance.setType(EmployeeType.REGULAR);
		return new_instance;
	}

	/**
	 * @return the regularEmployeeInfoHome
	 */
	public RegularEmployeeInfoHome getRegularEmployeeInfoHome() {
		return regularEmployeeInfoHome;
	}

	public void prepareForNewEmployee() {
		this.clearInstance();
		regularEmployeeInfoHome.clearInstance();
		employmentHome.clearInstance();
		employmentHome.getInstance().setEstablished(
				getCoreSearching().getActiveSchoolYear(getEntityManager()).getSchoolYearStart());
		employmentHome.getInstance().setFinalWorkingHours(21);
		employmentHome.getInstance().setMandatoryWorkingHours(21);
	}

	/**
	 * @param regularEmployeeInfoHome
	 *            the regularEmployeeInfoHome to set
	 */
	public void setRegularEmployeeInfoHome(RegularEmployeeInfoHome regularEmployeeInfoHome) {
		this.regularEmployeeInfoHome = regularEmployeeInfoHome;
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#remove()
	 */
	@Override
	@Transactional
	@Restrict("#{s:hasRole('ADMIN')}")
	public String remove() {
		if (isManaged()) {
			Employee employee = getInstance();
			info("principal #0 is trying to remove employee #1", getPrincipalName(), employee);
			return super.remove();
		}
		return null;
	}
}
