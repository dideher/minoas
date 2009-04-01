package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;

public interface IEmployeeSearch {

	public Boolean getEmployeeActiveFilter();

	public String getEmployeeFatherNameFilter();

	public String getEmployeeFirstNameFilter();

	public String getEmployeeLastNameFilter();

	public String getEmployeeRegistryIDFilter();

	public Specialization getEmployeeSpecializationFilter();

	public String getEmployeeVATNumberFilter();

	public SchoolYear getSchoolYearFilter();

	public Specialization getSpecializationFilter();

	public String search();

	public String select();

	public void setEmployeeActiveFilter(Boolean employment_filter);

	public void setEmployeeFatherNameFilter(String fathername_filter);

	public void setEmployeeFirstNameFilter(String firstname_filter);

	public void setEmployeeLastNameFilter(String lastname_filter);

	public void setEmployeeRegistryIDFilter(String registryid_filter);

	public void setEmployeeSpecializationFilter(Specialization specialization_filter);

	public void setEmployeeVATNumberFilter(String vatnumber_filter);

	public void setSchoolYearFilter(SchoolYear school_year);

	public void setSpecializationFilter(Specialization specialization_filter);
}
