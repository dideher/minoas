/**
 * 
 */
package gr.sch.ira.minoas.session.security;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public interface IRoleSearch {

	public String getSearchPattern();

	public String getSearchString();

	public void search();

	public void setSearchString(String searchString);
}
