/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Person;

/**
 * @author slavikos
 *
 */
public interface IEmployeeAware {

	public Person getActiveEmployee();

	public boolean hasActiveEmployee();

	public void setActiveEmployee(Employee activeEmployee);

}
