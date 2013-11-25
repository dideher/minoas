/**
 * 
 */
package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.EmployeeCRDCalculation;
import gr.sch.ira.minoas.seam.components.home.DisposalHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeLeaveHome;
import gr.sch.ira.minoas.seam.components.home.EmploymentHome;
import gr.sch.ira.minoas.seam.components.home.SecondmentHome;
import gr.sch.ira.minoas.seam.components.home.ServiceAllocationHome;
import gr.sch.ira.minoas.seam.components.home.SpecialAssigmentHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;

/**
 * @author slavikos
 * 
 */
@Name("EmployeeCDRQueueManager")
@Scope(ScopeType.STATELESS)
public class EmployeeCDRQueueManager extends BaseDatabaseAwareSeamComponent {

	@In(create = true)
	EmployeeHome employeeHome;

	@In(create = true)
	EmploymentHome employmentHome;

	@In(create = true)
	SecondmentHome secondmentHome;

	@In(create = true)
	ServiceAllocationHome serviceAllocationHome;

	@In(create = true)
	DisposalHome disposalHome;

	@In(create = true)
	EmployeeLeaveHome employeeLeaveHome;

	@In(create = true)
	SpecialAssigmentHome specialAssigmentHome;

	@In(create = true)
	EmployeeCRDCalculation employeeCRDCalculation;

	/**
	 * 
	 */
	public EmployeeCDRQueueManager() {
		// TODO Auto-generated constructor stub
	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.Employee" })
	public void handleEmployeeUpdate() {
		if (employeeHome != null && employeeHome.isManaged()) {
			info(String
					.format("employee '%s' update detected, will force CDR generation.",
							employeeHome.getInstance()));
			Employee e = employeeHome.getInstance();
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}
	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.Employment" })
	public void handleEmploymentUpdate() {
		if (employmentHome != null && employmentHome.isManaged()) {
			info(String
					.format("employment '%s' update detected, will force CDR generation.",
							employmentHome.getInstance()));
			Employee e = employmentHome.getInstance().getEmployee();
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}

	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.Disposal" })
	public void handleDisposalUpdate() {
		if (disposalHome != null && disposalHome.isManaged()) {
			info(String
					.format("disposal '%s' update detected, will force CDR generation.",
							disposalHome.getInstance()));
			Employee e = disposalHome.getInstance().getEmployee();
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}

	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.Secondment" })
	public void handleSecondmentUpdate() {
		if (secondmentHome != null && secondmentHome.isManaged()) {
			info(String
					.format("secondment '%s' update detected, will force CDR generation.",
							secondmentHome.getInstance()));
			Employee e = secondmentHome.getInstance().getEmployee();
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}

	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.ServiceAllocation" })
	public void handleServiceAllocationUpdate() {
		if (serviceAllocationHome != null && serviceAllocationHome.isManaged()) {
			info(String
					.format("service allocation '%s' update detected, will force CDR generation.",
							serviceAllocationHome.getInstance()));
			Employee e = serviceAllocationHome.getInstance().getEmployee();
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}

	}

	@Observer({ "org.jboss.seam.afterTransactionSuccess.SpecialAssigment" })
	public void handleSpecialAssigmentUpdate() {
		if (specialAssigmentHome != null && specialAssigmentHome.isManaged()) {
			Employee e = specialAssigmentHome.getInstance().getEmployee();
			info(String
					.format("special assigment '%s' update detected, will force CDR generation.",
							specialAssigmentHome.getInstance()));
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}
	}
	
	@Observer({ "org.jboss.seam.afterTransactionSuccess.EmployeeLeave" })
	public void handleEmployeeLeaveUpdate() {
		if (employeeLeaveHome != null && employeeLeaveHome.isManaged()) {
			Employee e = employeeLeaveHome.getInstance().getEmployee();
			info(String
					.format("employee leave '%s' update detected, will force CDR generation.",
							employeeLeaveHome.getInstance()));
			employeeCRDCalculation.calculateEmployeeCDR(e);
			info(String.format("employee's '%s' cdr(s) has been updated.",
					e.toPrettyString()));
		}

	}

}
