/**
 * 
 */
package gr.sch.ira.minoas.session.security;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public interface IRoleSearch {

	public void search();

	public String getSearchPattern();

	public void setSearchString(String searchString);

	public String getSearchString();
}
