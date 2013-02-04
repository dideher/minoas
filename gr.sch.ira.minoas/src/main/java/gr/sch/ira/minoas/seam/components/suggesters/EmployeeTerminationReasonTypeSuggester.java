/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.EmployeeTerminationReason;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeTerminationReasonTypeSuggester")
public class EmployeeTerminationReasonTypeSuggester extends BaseSuggester {

	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<EmployeeTerminationReason> suggest(Object search_pattern) {
		return getEntityManager()
                .createQuery("SELECT s FROM EmployeeTerminationReason s WHERE LOWER(s.description) LIKE LOWER(:search_pattern) ORDER BY s.description" ).setParameter(
                        "search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).getResultList();
    }

}
