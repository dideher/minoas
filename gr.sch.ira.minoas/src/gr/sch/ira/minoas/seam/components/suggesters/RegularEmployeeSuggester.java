/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;

import java.util.Collection;
import java.util.Collections;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("regularEmployeeSuggester")
public class RegularEmployeeSuggester extends BaseSuggester {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Employee> suggest(Object search_pattern) {
		Integer registryID = null;
		String searchPattern = String.valueOf(search_pattern);
		try {
			registryID = Integer.parseInt(searchPattern);
		} catch (Exception ex) {

		}
		if (registryID != null && searchPattern.length() >= 4) {
			return getEntityManager()
			.createQuery(
					"SELECT e from Employee e WHERE e.type=:employeeType AND e.active IS TRUE AND e.regularEmployeeInfo IS NOT NULL AND e.regularEmployeeInfo.registryID LIKE :search_pattern")
			.setParameter("search_pattern", CoreUtils.getSearchPattern(searchPattern))
			.setParameter("employeeType", EmployeeType.REGULAR).getResultList();
		} else if (searchPattern.length() >= 3) {
			return getEntityManager()
			.createQuery(
					"SELECT e from Employee e WHERE e.type=:employeeType AND e.active IS TRUE AND e.regularEmployeeInfo IS NOT NULL AND (lower(e.lastName) LIKE LOWER(:search_pattern) OR lower(e.firstName) LIKE LOWER(:search_pattern))")
			.setParameter("search_pattern", CoreUtils.getSearchPattern(searchPattern))
			.setParameter("employeeType", EmployeeType.REGULAR).getResultList();
			
		} else
			return Collections.EMPTY_LIST;
	}

}
