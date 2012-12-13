package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SchoolType;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.EmploymentType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeTeachingHoursReportCriteria")
@Scope(ScopeType.CONVERSATION)
public class EmployeeTeachingHoursReportCriteria {

	
	private SchoolYear schoolYear;

	private Specialization specialization;

	private SpecializationGroup specializationGroup;

	private SpecializationSearchType specializationSearchType;

	private EmploymentType type;
	
	private String sorting;
	
	private Character region;
	
	private SchoolType schoolType;

	/**
	 * 
	 */
	public EmployeeTeachingHoursReportCriteria() {
		super();
		this.specializationSearchType = SpecializationSearchType.SPECIALIZATION;
	}

	

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @return the specializationSearchType
	 */
	public SpecializationSearchType getSpecializationSearchType() {
		return specializationSearchType;
	}

	/**
	 * @return the type
	 */
	public EmploymentType getType() {
		return type;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

	/**
	 * @param specializationSearchType the specializationSearchType to set
	 */
	public void setSpecializationSearchType(SpecializationSearchType specializationSearchType) {
		this.specializationSearchType = specializationSearchType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EmploymentType type) {
		this.type = type;
	}

	/**
	 * @return the sorting
	 */
	public String getSorting() {
		return sorting;
	}

	/**
	 * @param sorting the sorting to set
	 */
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	/**
	 * @return the region
	 */
	public Character getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Character region) {
		this.region = region;
	}

	/**
	 * @return the schoolType
	 */
	public SchoolType getSchoolType() {
		return schoolType;
	}

	/**
	 * @param schoolType the schoolType to set
	 */
	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

}
