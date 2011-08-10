package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Leave;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeLeaveReportItem extends EmployeeReportItem {

	private String comment;

	private Date dueTo;

	private Date established;

	private String leaveType;

	private String leaveTypeKey;

	private Character region;

	private String school;

	/**
	 * 
	 */
	public EmployeeLeaveReportItem() {
		super();
	}

	public EmployeeLeaveReportItem(Leave leave) {
		super(leave.getEmployee());
		this.leaveType = leave.getLeaveType().toString();
		this.leaveTypeKey = leave.getLeaveType().getKey();
		this.established = leave.getEstablished();
		this.dueTo = leave.getDueTo();
		this.comment = leave.getComment();
		if (leave.getEmployee().getCurrentEmployment() != null) {
			this.school = leave.getEmployee().getCurrentEmployment().getSchool().getTitle();
			this.region = leave.getEmployee().getCurrentEmployment().getSchool().getRegionCode();
		}
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
	 * @return the leaveType
	 */
	public String getLeaveType() {
		return leaveType;
	}

	/**
	 * @return the leaveTypeKey
	 */
	public String getLeaveTypeKey() {
		return leaveTypeKey;
	}

	/**
	 * @return the region
	 */
	public Character getRegion() {
		return region;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	/**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	/**
	 * @param leaveTypeKey the leaveTypeKey to set
	 */
	public void setLeaveTypeKey(String leaveTypeKey) {
		this.leaveTypeKey = leaveTypeKey;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Character region) {
		this.region = region;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}
}
