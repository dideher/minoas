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

	
	@In(create = true)
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
		joinTransaction();
		Employee employee = getInstance();
		newSecondment = secondmentHome.getInstance();
		info("trying to add new secondment #0 for employee #1,", newSecondment, employee);
		Employment currentEmployment = employee.getCurrentEmployment();
		Secondment currentSecondment = currentEmployment != null ? currentEmployment.getSecondment() : null;
		newSecondment.setSchoolYear(coreSearching.getActiveSchoolYear());
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setEmployee(employee);
		newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
		if(currentEmployment!=null) {
			newSecondment.setAffectedEmployment(currentEmployment);
			
		}
		
		if(currentSecondment!=null) {
			currentSecondment.setActive(Boolean.FALSE);
			currentSecondment.setSupersededBy(newSecondment);
			getEntityManager().merge(currentSecondment);
		}
		
		getEntityManager().persist(newSecondment);
		getEntityManager().merge(employee);
		getEntityManager().flush();
		raiseAfterTransactionSuccessEvent();
		info("successfully registered new secondment #0 for employee #1,", newSecondment, employee);
		return "added";
	}

	@Transactional
	public boolean wire() {
		joinTransaction();	
		Employee employee = getInstance();
		Secondment newSecondment = secondmentHome.getInstance();
		if (newSecondment != null) {
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

}
