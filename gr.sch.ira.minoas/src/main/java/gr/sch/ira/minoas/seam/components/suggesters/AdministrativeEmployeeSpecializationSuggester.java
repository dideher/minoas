/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.EmployeeType;

import java.util.Collection;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * Returns specializations that are suitable for administrative
 * employees
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("administrativeEmployeeSpecializationSuggester")
public class AdministrativeEmployeeSpecializationSuggester extends BaseSuggester {

	
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Specialization> suggest(Object specialization_search_pattern) {
		return getEntityManager().createQuery(
				"SELECT s from Specialization s WHERE (s.disabled IS FALSE OR s.disabled IS NULL) AND s.id IN (SELECT es.suitableSpecialization.id FROM EmployeeSuitableSpecialization es WHERE es.employeeType=:employeeType) AND (lower(s.id) LIKE LOWER(:search_pattern) OR LOWER(s.title) LIKE LOWER(:search_pattern))").setParameter(
				"search_pattern", CoreUtils.getSearchPattern(String.valueOf(specialization_search_pattern))).setParameter("employeeType", EmployeeType.ADMINISTRATIVE)
				.getResultList();
	}

}
