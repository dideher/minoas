package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;

import java.util.Date;

public class LeaveItem extends EmployeeReportItem {
	private String comment;
	private Date dueTo;
	private Date establishedIn;
	private String leaveType;
	private String leaveTypeKey;
	private String regularUnit;
	
	
	public LeaveItem() {
		super();
	}
	
	public LeaveItem(Leave leave) {
		super(leave.getEmployee());
		this.comment = leave.getComment();
		this.dueTo = leave.getDueTo();
		this.establishedIn = leave.getEstablished();
		if(leave.getEmployee().getCurrentEmployment()!=null)
			this.regularUnit = leave.getEmployee().getCurrentEmployment().getSchool().getTitle(); 
		this.leaveType = leave.getLeaveType().name();
		this.leaveTypeKey = leave.getLeaveType().getKey();
	}

	
	public String getComment() {
		return comment;
	}

	public Date getDueTo() {
		return dueTo;
	}

	public Date getEstablishedIn() {
		return establishedIn;
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
	 * @return the regularUnit
	 */
	public String getRegularUnit() {
		return regularUnit;
	}

	/**
	 * @param regularUnit the regularUnit to set
	 */
	public void setRegularUnit(String regularUnit) {
		this.regularUnit = regularUnit;
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
	 * @param establishedIn the establishedIn to set
	 */
	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	

}