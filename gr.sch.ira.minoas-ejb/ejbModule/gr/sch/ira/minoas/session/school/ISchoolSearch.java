package gr.sch.ira.minoas.session.school;

public interface ISchoolSearch {

	/**
	 * @return the searchString
	 */
	public String getSearchString();

	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString);

	
	public String selectSchool();

	public String search();


}