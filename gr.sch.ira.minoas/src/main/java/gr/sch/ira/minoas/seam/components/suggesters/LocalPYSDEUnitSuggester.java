/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.PermanentTransferHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author slavikos
 * 
 */
@Name("localPYSDEUnitSuggester")
@Scope(ScopeType.PAGE)
public class LocalPYSDEUnitSuggester extends BaseDatabaseAwareSeamComponent {

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
						"SELECT s FROM Unit s WHERE LOWER(s.title) LIKE LOWER(:search_pattern) AND s.pysde.localPYSDE IS TRUE")
				.setParameter(
						"search_pattern",
						CoreUtils.getSearchPattern(String
								.valueOf(search_pattern))).getResultList();

		return returnCollection;

	}

}
