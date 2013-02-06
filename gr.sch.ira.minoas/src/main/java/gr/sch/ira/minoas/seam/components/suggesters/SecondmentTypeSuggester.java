/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("secondmentTypeSuggester")
public class SecondmentTypeSuggester extends BaseSuggester {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<SecondmentType> suggest(Object secondmemt_search_pattern) {
		return getEntityManager().createQuery(
				"SELECT s from SecondmentType s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)").setParameter(
				"search_pattern", CoreUtils.getSearchPattern(String.valueOf(secondmemt_search_pattern)))
				.getResultList();
	}

}
