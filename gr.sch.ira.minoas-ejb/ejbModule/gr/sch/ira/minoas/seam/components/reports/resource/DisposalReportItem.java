package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Disposal;

import java.util.Date;

public class DisposalReportItem extends EmployeeReportItem {
	private String comment;
	private Date dueTo;
	private Date establishedIn;
	private Character sourceUnitRegion;
	private Character targetUnitRegion;
	private boolean requestByEmployee;
	private String disposalType;
	private String disposalTypeKey;
	private String disposalTargetType;
	private String disposalTargetTypeKey;
	private String sourceUnit;
	private String disposalUnit;
	private Integer days;
	private Integer hours;
	
	public DisposalReportItem() {
		super();
	}
	
	public DisposalReportItem(Disposal disposal) {
		super(disposal.getEmployee());
		this.comment = disposal.getComment();
		this.dueTo = disposal.getDueTo();
		this.establishedIn = disposal.getEstablished();
		this.disposalUnit = disposal.getDisposalUnit().getTitle();
		
		if(disposal.getAffectedEmployment()!=null) {
			this.sourceUnit = disposal.getAffectedEmployment().getSchool().getTitle();
		}
		if(disposal.getAffectedSecondment()!=null) {
			this.sourceUnit = disposal.getAffectedSecondment().getSourceUnit().getTitle();
		}
		this.disposalType = disposal.getType().name();
		this.disposalTypeKey = disposal.getType().getKey();
		this.hours = disposal.getHours();
		this.days = disposal.getDays();
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
	 * @return the establishedIn
	 */
	public Date getEstablishedIn() {
		return establishedIn;
	}

	/**
	 * @param establishedIn the establishedIn to set
	 */
	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	/**
	 * @return the sourceUnitRegion
	 */
	public Character getSourceUnitRegion() {
		return sourceUnitRegion;
	}

	/**
	 * @param sourceUnitRegion the sourceUnitRegion to set
	 */
	public void setSourceUnitRegion(Character sourceUnitRegion) {
		this.sourceUnitRegion = sourceUnitRegion;
	}

	/**
	 * @return the targetUnitRegion
	 */
	public Character getTargetUnitRegion() {
		return targetUnitRegion;
	}

	/**
	 * @param targetUnitRegion the targetUnitRegion to set
	 */
	public void setTargetUnitRegion(Character targetUnitRegion) {
		this.targetUnitRegion = targetUnitRegion;
	}

	/**
	 * @return the requestByEmployee
	 */
	public boolean isRequestByEmployee() {
		return requestByEmployee;
	}

	/**
	 * @param requestByEmployee the requestByEmployee to set
	 */
	public void setRequestByEmployee(boolean requestByEmployee) {
		this.requestByEmployee = requestByEmployee;
	}

	/**
	 * @return the disposalType
	 */
	public String getDisposalType() {
		return disposalType;
	}

	/**
	 * @param disposalType the disposalType to set
	 */
	public void setDisposalType(String disposalType) {
		this.disposalType = disposalType;
	}

	/**
	 * @return the disposalTypeKey
	 */
	public String getDisposalTypeKey() {
		return disposalTypeKey;
	}

	/**
	 * @param disposalTypeKey the disposalTypeKey to set
	 */
	public void setDisposalTypeKey(String disposalTypeKey) {
		this.disposalTypeKey = disposalTypeKey;
	}

	/**
	 * @return the disposalTargetType
	 */
	public String getDisposalTargetType() {
		return disposalTargetType;
	}

	/**
	 * @param disposalTargetType the disposalTargetType to set
	 */
	public void setDisposalTargetType(String disposalTargetType) {
		this.disposalTargetType = disposalTargetType;
	}

	/**
	 * @return the disposalTargetTypeKey
	 */
	public String getDisposalTargetTypeKey() {
		return disposalTargetTypeKey;
	}

	/**
	 * @param disposalTargetTypeKey the disposalTargetTypeKey to set
	 */
	public void setDisposalTargetTypeKey(String disposalTargetTypeKey) {
		this.disposalTargetTypeKey = disposalTargetTypeKey;
	}

	/**
	 * @return the sourceUnit
	 */
	public String getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @return the disposalUnit
	 */
	public String getDisposalUnit() {
		return disposalUnit;
	}

	/**
	 * @param disposalUnit the disposalUnit to set
	 */
	public void setDisposalUnit(String disposalUnit) {
		this.disposalUnit = disposalUnit;
	}

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
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