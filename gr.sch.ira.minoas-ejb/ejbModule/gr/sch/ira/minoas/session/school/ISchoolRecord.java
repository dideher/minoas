/**
 * 
 */
package gr.sch.ira.minoas.session.school;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
public interface ISchoolRecord {
	public void end();

	public boolean hasActiveSchool();

	public String reset();

	public void searchDeputyEmployments();

	public void searchRegularEmployments();

	/**
	 * Searches for secondments that are targeted to this very school unit.
	 */
	public void searchSchoolSecondments();

	public String selectDeputyEmployment();

	public String selectRegularEmployment();

	public String selectSecondment();

}
