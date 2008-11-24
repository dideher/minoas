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
	public String search();
	
	public String searchEmployeeRegularEmployments();
	
	public String searchEmployeeDeputyEmployments();
	
	public String searchEmployeeSecondments();
	
	public Secondment getSelectedEmployeeSecondment();
	
	public void setSelectedEmployeeSecondment(Secondment selected_secondment);
	
	public void endViewEmployeeRecord();
	
	public String select();
}
