/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employement.Secondment;

/**
 * @author slavikos
 *
 */
public interface IEmployeeRecord {
	public void endViewEmployeeRecord();

	public Secondment getSelectedEmployeeSecondment();

	public String search();

	public String searchEmployeeDeputyEmployments();

	public String searchEmployeeRegularEmployments();

	public String searchEmployeeSecondments();

	public String select();

	public void setSelectedEmployeeSecondment(Secondment selected_secondment);
}
