/**
 * 
 */
package gr.sch.ira.minoas.session.security;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public interface IRoleGroupSearch {

	public String getSearchString();

	public void search();

	public void setSearchString(String searchString);
}
