package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Leave;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeLeaveReportItem extends EmployeeReportItem {

	private String leaveType;
	
	private String leaveTypeKey;
	
	private Date established;
	
	private Date dueTo;
	
	private String school;
	
	private Character region;
	
	private String comment;
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
		if(leave.getEmployee().getCurrentEmployment()!=null) {
			this.school = leave.getEmployee().getCurrentEmployment().getSchool().getTitle();
			this.region = leave.getEmployee().getCurrentEmployment().getSchool().getRegionCode();
		}
	}

	/**
	 * @return the leaveType
	 */
	public String getLeaveType() {
		return leaveType;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	/**
	 * @return the leaveTypeKey
	 */
	public String getLeaveTypeKey() {
		return leaveTypeKey;
	}

	/**
	 * @param leaveTypeKey the leaveTypeKey to set
	 */
	public void setLeaveTypeKey(String leaveTypeKey) {
		this.leaveTypeKey = leaveTypeKey;
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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
