/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author slavikos
 * 
 */
@Name("schoolSearch")
@Scope(ScopeType.SESSION)
@Restrict("#{identity.loggedIn}")
public class SchoolSearchAction extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	@DataModel
	private List<School> schools;

	private String searchString;

	private Integer sxolikaMoria;
	private Integer pageSize = 10;

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	private Integer page = 0;

	public Integer getSxolikaMoria() {
		return sxolikaMoria;
	}

	public void setSxolikaMoria(Integer sxolikaMoria) {
		this.sxolikaMoria = sxolikaMoria;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void find() {
		page = 0;
		querySchools();
	}

	@SuppressWarnings("unchecked")
	private void querySchools() {
		System.err.println("searchingggg !!!!!!!!");
		schools = getEntityManager()
				.createQuery(
						"SELECT s from School s WHERE lower(s.title) LIKE :search_pattern AND s.ministryCode != '0000000'")
				.setParameter("search_pattern",
						CoreUtils.getSearchPattern(getSearchString()))
				.setMaxResults(pageSize).setFirstResult(page * pageSize)
				.getResultList();
	}

	public boolean isNextPageAvailable() {
		return schools != null && schools.size() == pageSize;
	}

	public boolean isPreviousPageAvailable() {
		return page > 0;
	}

	public void handleSearchStringChange(ValueChangeEvent e) {
		System.err.println("handleSearchStringChange !!!!!!!!");
		page = 0;
		setSearchString((String) e.getNewValue());
		querySchools();
	}

	public void nextPage() {
		if (isNextPageAvailable()) {
			page++;
			querySchools();
		}
	}

	public void previousPage() {
		if (isPreviousPageAvailable()) {
			page--;
			querySchools();
		}
	}
}
