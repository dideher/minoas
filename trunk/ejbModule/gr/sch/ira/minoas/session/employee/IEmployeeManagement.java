/**
 * 
 */
package gr.sch.ira.minoas.session.employee;



/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
public interface IEmployeeManagement {
	
	public String prepareNewSecondmet();
	
	public String secondmentTypeSelected();
	
	public String saveSecondment();
	
	public String cancelNewSecondment();
	
	public boolean hasActiveEmployee();
	
}
