/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author slavikos
 *
 */
@Name("schoolUnitSuggester")
@Scope(ScopeType.CONVERSATION)
public class SchoolUnitSuggester extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Unit> suggest(Object secondmemt_search_pattern) {
		return getEntityManager().createQuery("SELECT s FROM School s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)")
				.setParameter("search_pattern", CoreUtils.getSearchPattern(String.valueOf(secondmemt_search_pattern)))
				.getResultList();
	}

}
