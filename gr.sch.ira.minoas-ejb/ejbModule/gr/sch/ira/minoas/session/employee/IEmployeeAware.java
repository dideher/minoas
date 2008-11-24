/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employee.Employee;

/**
 * @author slavikos
 *
 */
public interface IEmployeeAware {
	
	public boolean hasActiveEmployee();
	
	public Employee getActiveEmployee();
	
	public void setActiveEmployee(Employee activeEmployee);
	
}
