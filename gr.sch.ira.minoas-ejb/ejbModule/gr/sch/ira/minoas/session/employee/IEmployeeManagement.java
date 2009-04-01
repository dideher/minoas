/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
public interface IEmployeeManagement {

	public String cancelNewSecondment();

	public boolean hasActiveEmployee();

	public String prepareNewSecondment();

	public String saveSecondment();

	public String secondmentTypeSelected();

}
