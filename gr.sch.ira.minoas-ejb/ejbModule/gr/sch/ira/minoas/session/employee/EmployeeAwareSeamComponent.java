/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;

/**
 * @author slavikos
 *
 */
public class EmployeeAwareSeamComponent extends BaseStatefulSeamComponentImpl implements IEmployeeAware {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(value = "activeEmployee", scope = ScopeType.CONVERSATION, required = true)
	private Employee activeEmployee;

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeAware#getActiveEmployee()
	 */
	public Employee getActiveEmployee() {
		return activeEmployee;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeAware#hasActiveEmployee()
	 */
	public boolean hasActiveEmployee() {
		return getActiveEmployee() != null;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeAware#setActiveEmployee(gr.sch.ira.minoas.model.employee.Employee)
	 */
	public void setActiveEmployee(Employee activeEmployee) {
		this.activeEmployee = activeEmployee;
	}

}
