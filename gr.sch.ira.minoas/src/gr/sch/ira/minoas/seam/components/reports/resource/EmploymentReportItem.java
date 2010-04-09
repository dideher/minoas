package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Employment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmploymentReportItem extends EmployeeReportItem {

	private String employmentType;

	private String employmentTypeKey;
	
	private String employmentSpecialization;
	
	private String employmentSpecializationID;

	private String school;
	
	private Character schoolRegion;

	/**
	 * 
	 */
	public EmploymentReportItem() {
		super();
	}

	public EmploymentReportItem(Employment employment) {
		super(employment.getEmployee());
		this.employmentType = employment.getType().toString();
		this.employmentTypeKey = employment.getType().getKey();
		if (employment.getSchool() != null) {
			setSchool(employment.getSchool().getTitle());
			setSchoolRegion(employment.getSchool().getRegionCode());
		}
		setEmploymentSpecialization(employment.getSpecialization().getTitle());
		setEmploymentSpecializationID(employment.getSpecialization().getId());
		setEmployeeFinalWorkingHours(employment.getFinalWorkingHours());
		setEmployeeMandatoryHours(employment.getMandatoryWorkingHours());
		setEmployeeEmploymentEstablishedDate(employment.getEstablished());
		setEmployeeEmploymentTerminatedDate(employment.getTerminated());
	}

	/**
	 * @return the leaveType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @return the leaveTypeKey
	 */
	public String getEmploymentTypeKey() {
		return employmentTypeKey;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setEmploymentType(String leaveType) {
		this.employmentType = leaveType;
	}

	/**
	 * @param leaveTypeKey the leaveTypeKey to set
	 */
	public void setEmploymentTypeKey(String leaveTypeKey) {
		this.employmentTypeKey = leaveTypeKey;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the schoolRegion
	 */
	public Character getSchoolRegion() {
		return schoolRegion;
	}

	/**
	 * @param schoolRegion the schoolRegion to set
	 */
	public void setSchoolRegion(Character schoolRegion) {
		this.schoolRegion = schoolRegion;
	}

	/**
	 * @return the employmentSpecialization
	 */
	public String getEmploymentSpecialization() {
		return employmentSpecialization;
	}

	/**
	 * @param employmentSpecialization the employmentSpecialization to set
	 */
	public void setEmploymentSpecialization(String employmentSpecialization) {
		this.employmentSpecialization = employmentSpecialization;
	}

	/**
	 * @return the employmentSpecializationID
	 */
	public String getEmploymentSpecializationID() {
		return employmentSpecializationID;
	}

	/**
	 * @param employmentSpecializationID the employmentSpecializationID to set
	 */
	public void setEmploymentSpecializationID(String employmentSpecializationID) {
		this.employmentSpecializationID = employmentSpecializationID;
	}

}
