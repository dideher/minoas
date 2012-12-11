/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("specializationGroupVirtualEditionSuggester")
public class SpecializationGroupVirtualEditionSuggester extends BaseSuggester {

    
    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<SpecializationGroup> suggest(Object specialization_search_pattern) {
	    SchoolYear schoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		return getEntityManager()
				.createQuery(
						"SELECT s from SpecializationGroup s WHERE (lower(s.id) LIKE LOWER(:search_pattern) OR LOWER(s.title) LIKE LOWER(:search_pattern)) AND s.isVirtualGroup IS TRUE AND s.schoolYear=:schoolYear")
				.setParameter("schoolYear", schoolYear).setParameter("search_pattern",
						CoreUtils.getSearchPattern(String.valueOf(specialization_search_pattern))).getResultList();
	}

}
