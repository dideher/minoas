/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.model.core.SchoolGroup;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author slavikos
 *
 */
@Name("organizationalOfficeSuggester")
public class OrganizationOfficeSuggester extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<OrganizationalOffice> suggest(Object unit_search_pattern) {
		return getEntityManager().createQuery(
				"SELECT s FROM OrganizationalOffice s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)").setParameter(
				"search_pattern", CoreUtils.getSearchPattern(String.valueOf(unit_search_pattern))).getResultList();
	}

}
