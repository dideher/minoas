package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Leave;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmploymentReportItem extends EmployeeReportItem {

	private String employmentType;
	
	private String employmentTypeKey;
	
	private Date established;
	
	private Date dueTo;
	
	private String school;
	
	
	private Integer hours;
	
	private Integer mandatoryHours;
	
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
		this.established = employment.getEstablished();
		this.dueTo = employment.getTerminated();
		this.hours = employment.getFinalWorkingHours();
		
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
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
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

	
	/**
	 * @return the mandatoryHours
	 */
	public Integer getMandatoryHours() {
		return mandatoryHours;
	}

	/**
	 * @param mandatoryHours the mandatoryHours to set
	 */
	public void setMandatoryHours(Integer mandatoryHours) {
		this.mandatoryHours = mandatoryHours;
	}

	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}
}
