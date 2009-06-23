package gr.sch.ira.minoas.seam.components.home;

import java.util.Collection;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.CoreSearching;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeHome")
public class EmployeeHome extends MinoasEntityHome<Employee> {

	@In()
	private CoreSearching coreSearching;
	
	@In(create=true, value="newSecondment")
	@Out
	private Secondment newSecondment;
	
	@In(create=true)
	private SecondmentHome secondmentHome;
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
		employment.setSchoolYear(coreSearching.getActiveSchoolYear());
		employment.setEmployee(employee);
		employment.setActive(Boolean.TRUE);
		getEntityManager().persist(employment);
		
		/* update the employee */
		employee.setCurrentEmployment(employment);
		employee.setLastSpecialization(employment.getSpecialization());
		employee.setModifiedOn(new Date(System.currentTimeMillis()));

		
		
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
	public String addNewSecodmentEmployment(Secondment newSecondment) {
		return "added";
	}
	
	public boolean wire() {
		Employee employee = getInstance();
		if(newSecondment!=null) {
			newSecondment.setSourceUnit(employee.getCurrentEmployment().getSchool());
		}
		return true;
	}

}
