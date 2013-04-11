/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.School;
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
@Scope(ScopeType.CONVERSATION)
@Restrict("#{identity.loggedIn}")
public class SchoolSearchAction extends BaseSearchAction<School> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	private String schoolRegion;

	@DataModel
	public List<School> schoolsSearchResult;

	private String searchString;

	private Integer sxolikaMoria;

	@Override
	public Query constructQuery() {

		StringBuilder queryBuilder = new StringBuilder(
				"SELECT s FROM School s WHERE ");
		List<String> paramList = new ArrayList<String>();
		paramList.add("lower(s.title) LIKE :schoolTitleSearchPattern");

		if (CoreUtils.isNonEmpty(getSchoolRegion())) {
			paramList.add("s.regionCode = :schoolRegionCode");
		}
		Iterator<String> itr = paramList.iterator();

		while (itr.hasNext()) {
			queryBuilder.append(itr.next());
			if (itr.hasNext()) {
				queryBuilder.append(" AND ");
			}
		}

		final Query query = getEntityManager().createQuery(
				queryBuilder.toString());

		/* handle parameters */
		query.setParameter("schoolTitleSearchPattern",
				CoreUtils.getSearchPattern(getSearchString()));
		if (CoreUtils.isNonEmpty(getSchoolRegion())) {
			query.setParameter("schoolRegionCode", getSchoolRegion());
		}

		return query;

	}

	@Override
	protected List<School> getResults() {
		return schoolsSearchResult;
	}

	public String getSchoolRegion() {
		return schoolRegion;
	}

	public String getSearchString() {
		return searchString;
	}

	public Integer getSxolikaMoria() {
		return sxolikaMoria;
	}

	@Override
	protected void setResults(List<School> results) {
		schoolsSearchResult = results;
	}

	public void setSchoolRegion(String schoolRegion) {
		this.schoolRegion = schoolRegion;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void setSxolikaMoria(Integer sxolikaMoria) {
		this.sxolikaMoria = sxolikaMoria;
	}

}
