/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employement.WorkExperienceType;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @author <a mailto:filippos@slavik.gr>Filippos Slavik</a>
 */
@Name("workExperienceTypesExtendedSuggester")
public class WorkExperienceTypesExtendedSuggester extends BaseSuggester {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    @Transactional
    public Collection<WorkExperienceType> suggest(Object work_experience_type_search_pattern) {
        System.err.println(work_experience_type_search_pattern);
        Integer workExId = null;
        try {
            workExId = new Integer(String.valueOf(work_experience_type_search_pattern));
        } catch (Exception ex) {
            workExId = null;
        }
        if (workExId != null) {
            return getEntityManager()
                    .createQuery("SELECT s FROM WorkExperienceType s WHERE s.id=:search_pattern AND s.active IS TRUE ORDER BY s.id ASC")
                    .setParameter("search_pattern", workExId).getResultList();
        } else {
            return getEntityManager()
                    .createQuery(
                            "SELECT s FROM WorkExperienceType s WHERE s.title LIKE LOWER(:search_pattern) AND s.active IS TRUE ORDER BY s.title ASC")
                    .setParameter("search_pattern",
                            CoreUtils.getSearchPattern(String.valueOf(work_experience_type_search_pattern)))
                    .getResultList();
        }

    }

}
