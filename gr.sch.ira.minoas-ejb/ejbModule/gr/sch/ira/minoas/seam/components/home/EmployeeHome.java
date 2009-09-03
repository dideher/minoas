package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

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
		Employee new_employee = getInstance();
		new_employee.setActive(Boolean.TRUE);
		if(!regularEmployeeInfoHome.isManaged()) {
			RegularEmployeeInfo info = regularEmployeeInfoHome.getInstance();
			info.setInsertedBy(getPrincipal());
			new_employee.setRegularDetail(info);
			
		}
		if(employmentHome)
		wire();
		return persist();
	}
	
	@Transactional
	public String addNewEmployeeFromOtherPYSDE() {
		/* we will quickly create an employee
		 * to be used for secondment
		 */
		Employee new_employee = getInstance();
		new_employee.setActive(Boolean.TRUE);
		if(!regularEmployeeInfoHome.isManaged()) {
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
		employmentHome.getInstance().setEstablished(getCoreSearching().getActiveSchoolYear(getEntityManager()).getSchoolYearStart());
		employmentHome.getInstance().setFinalWorkingHours(21);
		employmentHome.getInstance().setMandatoryWorkingHours(21);
	}


	/**
	 * @param regularEmployeeInfoHome the regularEmployeeInfoHome to set
	 */
	public void setRegularEmployeeInfoHome(RegularEmployeeInfoHome regularEmployeeInfoHome) {
		this.regularEmployeeInfoHome = regularEmployeeInfoHome;
	}
}
