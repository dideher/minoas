/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeLeaveTypeSuggester")
public class EmployeeLeaveTypeSuggester extends BaseSuggester {

	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<EmployeeLeaveType> suggest(Object search_pattern) {
		return getEntityManager()
                .createQuery("SELECT s FROM EmployeeLeaveType s WHERE LOWER(s.description) LIKE LOWER(:search_pattern) OR (s.legacyCode LIKE :search_pattern2) ORDER BY s.legacyCode" ).setParameter(
                        "search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).setParameter(
                                "search_pattern2", CoreUtils.getSearchPattern(String.valueOf(search_pattern)))
                .getResultList();
    }

}
