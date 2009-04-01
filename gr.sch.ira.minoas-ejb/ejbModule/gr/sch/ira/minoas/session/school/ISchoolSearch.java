package gr.sch.ira.minoas.session.school;

public interface ISchoolSearch {

	/**
	 * @return the searchString
	 */
	public String getSearchString();

	public String search();

	public String selectSchool();

	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString);

}