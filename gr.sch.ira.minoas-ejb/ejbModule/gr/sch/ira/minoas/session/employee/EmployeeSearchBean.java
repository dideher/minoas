/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeSearch")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, IEmployeeSearch.class })
@Scope(ScopeType.CONVERSATION)
public class EmployeeSearchBean extends BaseStatefulSeamComponentImpl implements IEmployeeSearch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Out(value = "activeEmployee", required = false, scope = ScopeType.CONVERSATION)
	private Person activeEmployee;

	private Boolean employeeActiveFilter;

	private String employeeFatherNameFilter;

	private String employeeFirstNameFilter;

	private String employeeLastNameFilter;

	private String employeeRegistryIDFilter;

	@DataModel(scope = ScopeType.PAGE, value = "employeesSearchResult")
	private List<Employee> employees;

	private Specialization employeeSpecializationFilter;

	private String employeeVATNumberFilter;

	private SchoolYear schoolYearFilter;

	@DataModelSelection("employeesSearchResult")
	private Person selectedEmployee;

	private Specialization specializationFilter;

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#create()
	 */
	@Override
	public void create() {
		super.create();
		setEmployeeActiveFilter(Boolean.TRUE);
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public Boolean getEmployeeActiveFilter() {
		return this.employeeActiveFilter;
	}

	/**
	 * @return the employeeFatherNameFilter
	 */
	public String getEmployeeFatherNameFilter() {
		return employeeFatherNameFilter;
	}

	/**
	 * @return the employeeFirstNameFilter
	 */
	public String getEmployeeFirstNameFilter() {
		return employeeFirstNameFilter;
	}

	/**
	 * @return the employeeLastNameFilter
	 */
	public String getEmployeeLastNameFilter() {
		return employeeLastNameFilter;
	}

	/**
	 * @return the employeeRegistryIDFilter
	 */
	public String getEmployeeRegistryIDFilter() {
		return employeeRegistryIDFilter;
	}

	/**
	 * @return the employeeSpecializationFilter
	 */
	public Specialization getEmployeeSpecializationFilter() {
		return employeeSpecializationFilter;
	}

	/**
	 * @return the employeeVATNumberFilter
	 */
	public String getEmployeeVATNumberFilter() {
		return employeeVATNumberFilter;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#getSchoolYearFilter()
	 */
	public SchoolYear getSchoolYearFilter() {
		return this.schoolYearFilter;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#getSpecializationFilter()
	 */
	public Specialization getSpecializationFilter() {
		return this.specializationFilter;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#search()
	 */
	public String search() {
		if (isNonEmpty(getEmployeeVATNumberFilter())) {
			info("searching for employee with VAT Number '#0'.", getEmployeeVATNumberFilter());
			employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.vatNumber=:vat_number")
					.setParameter("vat_number", getEmployeeVATNumberFilter()).getResultList();
			info("found #0 employee(s)", employees.size());
			return SUCCESS_OUTCOME;
		}

		if (isNonEmpty(getEmployeeRegistryIDFilter())) {
			info("searching for employee with registry ID '#0'.", getEmployeeRegistryIDFilter());
			employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.registryID=:registry_id")
					.setParameter("registry_id", getEmployeeRegistryIDFilter()).getResultList();
			info("found #0 employee(s)", employees.size());
			return SUCCESS_OUTCOME;
		}
		info(
				"searching for employees with matching '#0' last name, '#1' first name and '#2' father name with employment filter set to '#3'.",
				getEmployeeLastNameFilter(), getEmployeeFirstNameFilter(), getEmployeeFatherNameFilter(),
				getEmployeeActiveFilter());
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT e FROM Employee e WHERE (e.lastName LIKE UPPER(:lastName) AND e.firstName LIKE UPPER(:firstName) AND e.fatherName LIKE UPPER(:fatherName)) ");
		if (getEmployeeActiveFilter())
			sb.append("AND e.active = TRUE ");
		if (getEmployeeSpecializationFilter() != null) {
			sb.append("AND e.lastSpecialization=:specialization_filter ");
		}
		sb.append("ORDER BY e.lastName ASC, e.firstName ASC");
		Query q = entityManager.createQuery(sb.toString()).setParameter("lastName",
				CoreUtils.getSearchPattern(getEmployeeLastNameFilter())).setParameter("firstName",
				CoreUtils.getSearchPattern(getEmployeeFirstNameFilter())).setParameter("fatherName",
				CoreUtils.getSearchPattern(getEmployeeFatherNameFilter()));
		if (getEmployeeSpecializationFilter() != null) {
			q = q.setParameter("specialization_filter", getEmployeeSpecializationFilter());
		}
		employees = q.getResultList();
		info("found #0 employee(s)", employees.size());
		return SUCCESS_OUTCOME;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#select()
	 */
	public String select() {
		if (selectedEmployee != null) {
			this.activeEmployee = selectedEmployee;
			info("selected '#0' employee", this.activeEmployee);
			return EMPLOYEE_SELECTED_OUTCOME;
		} else
			return FAILURE_OUTCOME;

	}

	public void setEmployeeActiveFilter(Boolean employment_filter) {
		this.employeeActiveFilter = employment_filter;
	}

	/**
	 * @param employeeFatherNameFilter
	 *            the employeeFatherNameFilter to set
	 */
	public void setEmployeeFatherNameFilter(String employeeFatherNameFilter) {
		this.employeeFatherNameFilter = employeeFatherNameFilter;
	}

	/**
	 * @param employeeFirstNameFilter
	 *            the employeeFirstNameFilter to set
	 */
	public void setEmployeeFirstNameFilter(String employeeFirstNameFilter) {
		this.employeeFirstNameFilter = employeeFirstNameFilter;
	}

	/**
	 * @param employeeLastNameFilter
	 *            the employeeLastNameFilter to set
	 */
	public void setEmployeeLastNameFilter(String employeeLastNameFilter) {
		this.employeeLastNameFilter = employeeLastNameFilter;
	}

	/**
	 * @param employeeRegistryIDFilter
	 *            the employeeRegistryIDFilter to set
	 */
	public void setEmployeeRegistryIDFilter(String employeeRegistryIDFilter) {
		this.employeeRegistryIDFilter = employeeRegistryIDFilter;
	}

	/**
	 * @param employeeSpecializationFilter
	 *            the employeeSpecializationFilter to set
	 */
	public void setEmployeeSpecializationFilter(Specialization employeeSpecializationFilter) {
		this.employeeSpecializationFilter = employeeSpecializationFilter;
	}

	/**
	 * @param employeeVATNumberFilter
	 *            the employeeVATNumberFilter to set
	 */
	public void setEmployeeVATNumberFilter(String employeeVATNumberFilter) {
		this.employeeVATNumberFilter = employeeVATNumberFilter;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#setSchoolYearFilter(gr.sch.ira.minoas.model.core.SchoolYear)
	 */
	public void setSchoolYearFilter(SchoolYear school_year) {
		this.schoolYearFilter = school_year;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeSearch#setSpecializationFilter(gr.sch.ira.minoas.model.core.Specialization)
	 */
	public void setSpecializationFilter(Specialization specialization_filter) {
		this.specializationFilter = specializationFilter;
	}
}
