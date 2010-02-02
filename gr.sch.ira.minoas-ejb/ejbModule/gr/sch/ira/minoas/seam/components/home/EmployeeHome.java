package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.DeputyEmploymentInfo;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeHome")
@Scope(ScopeType.CONVERSATION)
public class EmployeeHome extends MinoasEntityHome<Employee> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private DeputyEmploymentInfoHome deputyEmploymentInfoHome;

	@In(create = true)
	private EmploymentHome employmentHome;

	@DataModel(value = "hourlyBasedEmployments")
	private Collection<Employment> hourlyBasedEmployments = new ArrayList<Employment>();

	@In(create = true)
	private RegularEmployeeInfoHome regularEmployeeInfoHome;

	@In(create = true)
	private SecondmentHome secondmentHome;

	@In(create = true)
	private ServiceAllocationHome serviceAllocationHome;

	@Transactional
	public String addNewEmployeeFromOtherPYSDE() {
		/*
		 * we will quickly create an employee to be used for secondment
		 */
		Employee new_employee = getInstance();
		new_employee.setActive(Boolean.TRUE);
		getEntityManager().persist(new_employee);
		if (!regularEmployeeInfoHome.isManaged()) {
			RegularEmployeeInfo info = regularEmployeeInfoHome.getInstance();
			info.setEmployee(new_employee);
			info.setInsertedBy(getPrincipal());
			new_employee.setRegularDetail(info);
			getEntityManager().persist(info);
		}
		wire();
		return persist();
	}

	@Transactional
	public String addNewEmployeeInLocalPYSDE() {

		if (isManaged() ||  employmentHome.isManaged() || regularEmployeeInfoHome.isManaged() || deputyEmploymentInfoHome.isManaged()) {
			throw new RuntimeException(
					"employee home or employment home or employeeRegularInfo or deputyEmploymentInfoHome is managed.");
		}

		RegularEmployeeInfo info = null;
		DeputyEmploymentInfo deputyEmploymentInfo = null;
		Employee new_employee = getInstance();
		Employment employment = employmentHome.getInstance();
		new_employee.setActive(Boolean.TRUE);
		new_employee.setCurrentPYSDE(getCoreSearching().getLocalPYSDE(getEntityManager()));
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
		getEntityManager().persist(new_employee);

		employment.setEmployee(new_employee);
		employment.setActive(Boolean.TRUE);
		employment.setInsertedBy(getPrincipal());
		employment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		employment.setSpecialization(new_employee.getLastSpecialization());

		if (new_employee.getType() == EmployeeType.REGULAR) {
			new_employee.setCurrentEmployment(employment);
			getEntityManager().persist(employment);

			info = regularEmployeeInfoHome.getInstance();
			info.setInsertedBy(getPrincipal());
			info.setEmployee(new_employee);
			new_employee.setRegularDetail(info);
			getEntityManager().persist(info);
		}

		if (new_employee.getType() == EmployeeType.DEPUTY) {
			new_employee.setCurrentEmployment(employment);
			getEntityManager().persist(employment);

			deputyEmploymentInfo = new DeputyEmploymentInfo();
			deputyEmploymentInfo.setEmployment(employment);
			deputyEmploymentInfo.setInsertedBy(getPrincipal());
			getEntityManager().persist(deputyEmploymentInfo);
		}

		if (new_employee.getType() == EmployeeType.HOURLYPAID) {
			for (Employment hemployment : getHourlyBasedEmployments()) {
				hemployment.setEmployee(new_employee);
				hemployment.setActive(Boolean.TRUE);
				hemployment.setInsertedBy(getPrincipal());
				hemployment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
				hemployment.setSpecialization(new_employee.getLastSpecialization());
				getEntityManager().persist(hemployment);
			}
		}
		return persist();
	}

	public void addNewHourlyBasedEmploymentItem() {
		Employment e = new Employment();
		e.setEmployee(getInstance());
		e.setActive(Boolean.TRUE);
		e.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		e.setInsertedBy(getPrincipal());
		e.setType(EmploymentType.HOURLYBASED);
		e.setEstablished(new Date());
		e.setFinalWorkingHours(11);
		e.setMandatoryWorkingHours(21);
		getHourlyBasedEmployments().add(e);

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
	 * @return the deputyEmploymentInfoHome
	 */
	public DeputyEmploymentInfoHome getDeputyEmploymentInfoHome() {
		return deputyEmploymentInfoHome;
	}

	/**
	 * @return the hourlyBasedEmployments
	 */
	public Collection<Employment> getHourlyBasedEmployments() {
		return hourlyBasedEmployments;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employee", scope = ScopeType.PAGE)
	public Employee getInstance() {
		return (Employee) super.getInstance();

	}

	/**
	 * @return the regularEmployeeInfoHome
	 */
	public RegularEmployeeInfoHome getRegularEmployeeInfoHome() {
		return regularEmployeeInfoHome;
	}

	public boolean hasDeputyEmployment() {
		return hasEmployment() && getInstance().getCurrentEmployment().getType().equals(EmploymentType.DEPUTY);
	}

	public boolean hasEmployment() {
		return getInstance().getCurrentEmployment() != null;
	}

	public boolean hasRegularEmployment() {
		return hasEmployment() && getInstance().getCurrentEmployment().getType().equals(EmploymentType.REGULAR);
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

	public void prepareForNewEmployee() {
		this.clearInstance();
		regularEmployeeInfoHome.clearInstance();
		employmentHome.clearInstance();
		deputyEmploymentInfoHome.clearInstance();
		employmentHome.getInstance().setEstablished(
				getCoreSearching().getActiveSchoolYear(getEntityManager()).getSchoolYearStart());
		employmentHome.getInstance().setFinalWorkingHours(21);
		employmentHome.getInstance().setMandatoryWorkingHours(21);
		addNewHourlyBasedEmploymentItem();
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
			if (employee.getType() == EmployeeType.REGULAR) {
				getEntityManager().remove(employee.getRegularDetail());
			}
			return super.remove();
		}
		return null;
	}

	public void removeNewHourlyBasedEmploymentItem(Employment newEmployment) {
		getHourlyBasedEmployments().remove(newEmployment);
	}

	/**
	 * @param deputyEmploymentInfoHome the deputyEmploymentInfoHome to set
	 */
	public void setDeputyEmploymentInfoHome(DeputyEmploymentInfoHome deputyEmploymentInfoHome) {
		this.deputyEmploymentInfoHome = deputyEmploymentInfoHome;
	}

	/**
	 * @param hourlyBasedEmployments the hourlyBasedEmployments to set
	 */
	public void setHourlyBasedEmployments(Collection<Employment> hourlyBasedEmployments) {
		this.hourlyBasedEmployments = hourlyBasedEmployments;
	}

	/**
	 * @param regularEmployeeInfoHome
	 *            the regularEmployeeInfoHome to set
	 */
	public void setRegularEmployeeInfoHome(RegularEmployeeInfoHome regularEmployeeInfoHome) {
		this.regularEmployeeInfoHome = regularEmployeeInfoHome;
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}

	public void verifyVATNumberForNewEmployee(ValueChangeEvent e) {
		String vatNumber = String.valueOf(e.getNewValue());
		if (vatNumber.trim().length() == 9) {
			Employee employee = getCoreSearching().getEmployeeByVatNumber(getEntityManager(), vatNumber);
			if (employee != null) {
				facesMessages.addToControl(e.getComponent().getId(), "Το ΑΦΜ που δηλώσατε είναι σε χρήση.");
			}
		}
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
		if (!serviceAllocationHome.isManaged()) {
			Employee employee = getInstance();
			ServiceAllocation newServiceAllocation = serviceAllocationHome.getInstance();
			Employment currentEmployment = employee != null ? employee.getCurrentEmployment() : null;
			if (currentEmployment != null) {
				newServiceAllocation.setSourceUnit(currentEmployment.getSchool());
			} else {
				newServiceAllocation.setSourceUnit(employee.getCurrentPYSDE().getRepresentedByUnit());
			}
		}
		return true;
	}
}
