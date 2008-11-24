/**
 * 
 */
package gr.sch.ira.minoas.session.school;


/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
public interface ISchoolRecord {
	public void searchRegularEmployments();
	
	public void searchDeputyEmployments();
	
	public boolean hasActiveSchool();
	
	/**
	 * Searches for secondments that are targeted to this very school unit.
	 */
	public void searchSchoolSecondments();
	
	public String selectRegularEmployment();
	
	public String selectDeputyEmployment();
	
	public String selectSecondment();
	
	public String reset();
	
	public void end();
	
}
