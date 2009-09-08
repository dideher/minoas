/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.PYSDE;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("pysdeSuggester")
public class PYSDESuggester extends BaseSuggester {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<PYSDE> suggest(Object search_pattern) {
		return getEntityManager()
				.createQuery(
						"SELECT p FROM PYSDE p WHERE lower(p.title) LIKE LOWER(:search_pattern) ORDER BY p.title")
				.setParameter("search_pattern",
						CoreUtils.getSearchPattern(String.valueOf(search_pattern))).getResultList();
	}

}
