/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.EmployeeUtil;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeManagement")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, IEmployeeManagement.class, IEmployeeAware.class })
public class EmployeeManagementBean extends EmployeeAwareSeamComponent implements IEmployeeManagement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(value = "coreSearching")
	private CoreSearching coreSearching;
	
	@In(value = "employeeUtil")
	private EmployeeUtil employeeUtil;

	@Out(required = false)
	private Secondment employeeActiveSecondment;

	@Out(required = false)
	private Secondment newSecondment;

	@Out(required = false)
	private SecondmentType selectedSecondmentType;

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#create()
	 */
	@Override
	@Create
	public void create() {
		super.create();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#destroy()
	 */
	@Override
	@Destroy
	@Remove
	public void destroy() {
		super.destroy();
	}

	/**
	 * @return the employeeActiveSecondment
	 */
	public Secondment getEmployeeActiveSecondment() {
		return employeeActiveSecondment;
	}

	public String prepareNewSecondment() {
		Employee employee = getEntityManager().merge(getActiveEmployee());
		/*
		 * A secondment for the given employee can be created only if the employ
		 * is regular or deputy (very rare)
		 */
		if (employeeUtil.isRegular(employee) || employeeUtil.isDeputy(employee)) {
			// oops! the employee is not valid for secondment
			warn("active employee '#0' is not valid for secondment (regular or deputy)", employee);
			getFacesMessages().addFromResourceBundle(FacesMessage.SEVERITY_ERROR,
					"EmployeeManagementBean.EMPLOYEE_TYPE_NOT_VALID_FOR_SECONDMENT", getActiveEmployee());
			return FAILURE_OUTCOME;
		}
		/*
		 * check if the employee has already an active secondment
		 */
		setEmployeeActiveSecondment(coreSearching.getEmployeeActiveSecondment(employee));
		if (getEmployeeActiveSecondment() != null) {
			warn("employee '#0' has already an active secondment '#1'.", getActiveEmployee(),
					getEmployeeActiveSecondment());
		}
		/* prepare the new secondment object */

		/* fill the obvious values */
		newSecondment = new Secondment();
		newSecondment.setEmployeeRequested(false);
		newSecondment.setActive(Boolean.TRUE);
		newSecondment.setSupersededBy(null);
		newSecondment.setSchoolYear(getActiveSchoolYear());
		newSecondment.setEmployeeRequested(Boolean.TRUE);
		newSecondment.setEmployee(getActiveEmployee());
		newSecondment.setEstablished(new Date((Calendar.getInstance(greekLocale)).getTimeInMillis()));
		Calendar dueTo = Calendar.getInstance(greekLocale);
		dueTo.set(Calendar.MONTH, Calendar.JUNE);
		dueTo.set(Calendar.DAY_OF_MONTH, 30);
		newSecondment.setDueTo(new Date(dueTo.getTimeInMillis()));

		/*
		 * check if the employee has a current employment. Not all employees
		 * have a current employment, especially the ones that came from other
		 * regions.
		 */

		Employment current_employment = employee.getCurrentEmployment();
		if (current_employment != null) {
			newSecondment.setAffectedEmployment(current_employment);
			newSecondment.setSourcePYSDE(getEntityManager().merge(current_employment.getSchool().getPysde()));
			newSecondment.setSourceUnit(getEntityManager().merge(current_employment.getSchool()));
			/*
			 * adjust working hours by retrieving the mandatory working hours
			 * from the affected employment. When copying do not copy any
			 * decrement since, this can be done later on.
			 */
			newSecondment.setFinalWorkingHours(current_employment.getMandatoryWorkingHours());
		} else {
			/* the employee has no current employment registered. This means that we don't have 
			 * a (school) unit to credit as the source unit, so we will use the PYSDE (each employee belongs to 
			 * a concrete PYSDE) to extract the source unit (each PYSDE has unit accosiated with)
			 */
			newSecondment.setSourcePYSDE(employee.getCurrentPYSDE());
			newSecondment.setSourceUnit(employee.getCurrentPYSDE().getRepresentedByUnit());
		}
		return SUCCESS_OUTCOME;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeManagement#saveSecondment()
	 */
	@End
	public String saveSecondment() {
		Person employee = getEntityManager().merge(getActiveEmployee());
		info("trying to save new seconment '#0' for employee '#1' during school year '#2'.", newSecondment, employee, getActiveSchoolYear());
		
		/* if the employee has already an secondment, then
		 * update it by disabling it and registering the new
		 * secondment as the successor of the old secondment.
		 */
		if (getEmployeeActiveSecondment() != null) {
			Secondment activeSecondment = getEntityManager().merge(getEmployeeActiveSecondment());
			activeSecondment.setActive(Boolean.FALSE);
			activeSecondment.setSupersededBy(newSecondment);
		}
		/* update the target PYSDE */
		if (newSecondment.getTargetUnit() != null) {
			newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
		}
		newSecondment.setInsertedOn(new Date(System.currentTimeMillis()));
		setEmployeeActiveSecondment(newSecondment);
		getEntityManager().persist(newSecondment);
		
		/* if the secondment affects an employment (ie the employee had
		 * a current employment when the secondment was created) then 
		 * update the employment as well.
		 */
		Employment employment = newSecondment.getAffectedEmployment();
		if(employment!=null) {
			employment.setSecondment(newSecondment);
		}
		getEntityManager().flush();
		return SUCCESS_OUTCOME;
	}

	public String secondmentTypeSelected() {
		info("lalalalala #0", newSecondment.getSecondmentType());
		selectedSecondmentType = newSecondment.getSecondmentType();
		return SUCCESS_OUTCOME;
	}

	/**
	 * @param employeeActiveSecondment the employeeActiveSecondment to set
	 */
	public void setEmployeeActiveSecondment(Secondment employeeActiveSecondment) {
		this.employeeActiveSecondment = employeeActiveSecondment;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeManagement#cancelNewSecondment()
	 */
	@End
	public String cancelNewSecondment() {
		getEntityManager().flush();
		return SUCCESS_OUTCOME;
	}

}
