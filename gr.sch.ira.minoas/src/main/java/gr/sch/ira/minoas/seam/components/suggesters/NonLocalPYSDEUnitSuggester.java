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
@Name("nonLocalPYSDEUnitSuggester")
@Scope(ScopeType.PAGE)
public class NonLocalPYSDEUnitSuggester extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Unit> suggest(Object search_pattern) {
		Collection<Unit> returnCollection = null;

		returnCollection = getEntityManager()
				.createQuery(
						"SELECT s FROM Unit s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)  AND s.pysde.localPYSDE IS FALSE")
				.setParameter(
						"search_pattern",
						CoreUtils.getSearchPattern(String
								.valueOf(search_pattern))).getResultList();

		return returnCollection;

	}

}
