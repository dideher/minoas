/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.util.Collection;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("specializationGroupSuggester")
public class SpecializationSuggester extends BaseSuggester {

	@In
	private CoreSearching coreSearching;
	
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Specialization> suggest(Object specialization_search_pattern) {
		SchoolYear activeSchoolYear = coreSearching.getActiveSchoolYear(getEntityManager());
		if(activeSchoolYear!=null) 
			return getEntityManager().createQuery(
			"SELECT s from SpecializationGroup s WHERE s.schoolYear=:schoolYear AND lower(s.title) LIKE LOWER(:search_pattern)").setParameter(
			"search_pattern", CoreUtils.getSearchPattern(String.valueOf(specialization_search_pattern))).setParameter("schoolYear", activeSchoolYear)
			.getResultList();
		else
		return getEntityManager().createQuery(
				"SELECT s from SpecializationGroup s WHERE lower(s.title) LIKE LOWER(:search_pattern)").setParameter(
				"search_pattern", CoreUtils.getSearchPattern(String.valueOf(specialization_search_pattern)))
				.getResultList();
	}

}
