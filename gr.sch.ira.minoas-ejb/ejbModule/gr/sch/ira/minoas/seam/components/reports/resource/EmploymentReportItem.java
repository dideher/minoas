package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Employment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmploymentReportItem extends EmployeeReportItem {

	private String employmentType;
	
	private String employmentTypeKey;
	
	private String school;
	
	
	
	
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
	 * @param leaveType the leaveType to set
	 */
	public void setEmploymentType(String leaveType) {
		this.employmentType = leaveType;
	}

	/**
	 * @return the leaveTypeKey
	 */
	public String getEmploymentTypeKey() {
		return employmentTypeKey;
	}

	/**
	 * @param leaveTypeKey the leaveTypeKey to set
	 */
	public void setEmploymentTypeKey(String leaveTypeKey) {
		this.employmentTypeKey = leaveTypeKey;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}


}
