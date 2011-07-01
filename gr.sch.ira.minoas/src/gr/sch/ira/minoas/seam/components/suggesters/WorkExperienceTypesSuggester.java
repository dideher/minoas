/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.model.employement.WorkExperienceType;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * 
 */
@Name("workExperienceTypesSuggester")
public class WorkExperienceTypesSuggester extends BaseSuggester {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<WorkExperienceType> suggest(
			Object work_experience_type_search_pattern) {

		return getEntityManager().createQuery(
				"SELECT s FROM WorkExperienceType s ORDER BY s.title ASC")
				.getResultList();
	}

}
