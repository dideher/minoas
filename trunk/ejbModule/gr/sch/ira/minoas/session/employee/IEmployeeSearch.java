package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;

public interface IEmployeeSearch {
	
	public String search();
	
	public String select();
	
	public void setSpecializationFilter(Specialization specialization_filter);
	
	public void setSchoolYearFilter(SchoolYear school_year);
	
	public SchoolYear getSchoolYearFilter();
	
	public Specialization getSpecializationFilter();
	
	public void setEmployeeActiveFilter(Boolean employment_filter);
	
	public Boolean getEmployeeActiveFilter();
	
	public void setEmployeeLastNameFilter(String lastname_filter);
	
	public void setEmployeeFirstNameFilter(String firstname_filter);

	public void setEmployeeFatherNameFilter(String fathername_filter);
	
	public String getEmployeeLastNameFilter();
	
	public String getEmployeeFirstNameFilter();

	public String getEmployeeFatherNameFilter();
	
	public void setEmployeeVATNumberFilter(String vatnumber_filter);
	
	public String getEmployeeVATNumberFilter();
	
	public void setEmployeeRegistryIDFilter(String registryid_filter);
	
	public String getEmployeeRegistryIDFilter();	
	
	public Specialization getEmployeeSpecializationFilter();
	
	public void setEmployeeSpecializationFilter(Specialization specialization_filter);
}
