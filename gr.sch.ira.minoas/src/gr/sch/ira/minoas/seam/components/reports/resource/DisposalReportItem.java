package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Disposal;

import java.util.Date;

public class DisposalReportItem extends EmployeeReportItem {
	private String comment;

	private Integer days;

	private String disposalTargetType;

	private String disposalTargetTypeKey;

	private String disposalType;

	private String disposalTypeKey;

	private String disposalUnit;

	private Date dueTo;

	private Date establishedIn;

	private Integer hours;

	private boolean requestByEmployee;

	private String sourceUnit;

	private Character sourceUnitRegion;

	private Character targetUnitRegion;

	public DisposalReportItem() {
		super();
	}

	public DisposalReportItem(Disposal disposal) {
		super(disposal.getEmployee());
		this.comment = disposal.getComment();
		this.dueTo = disposal.getDueTo();
		this.establishedIn = disposal.getEstablished();
		this.disposalUnit = disposal.getDisposalUnit().getTitle();

		if (disposal.getAffectedEmployment() != null) {
			this.sourceUnit = disposal.getAffectedEmployment().getSchool().getTitle();
		}
		if (disposal.getAffectedSecondment() != null) {
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
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @return the disposalTargetType
	 */
	public String getDisposalTargetType() {
		return disposalTargetType;
	}

	/**
	 * @return the disposalTargetTypeKey
	 */
	public String getDisposalTargetTypeKey() {
		return disposalTargetTypeKey;
	}

	/**
	 * @return the disposalType
	 */
	public String getDisposalType() {
		return disposalType;
	}

	/**
	 * @return the disposalTypeKey
	 */
	public String getDisposalTypeKey() {
		return disposalTypeKey;
	}

	/**
	 * @return the disposalUnit
	 */
	public String getDisposalUnit() {
		return disposalUnit;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @return the establishedIn
	 */
	public Date getEstablishedIn() {
		return establishedIn;
	}

	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}

	/**
	 * @return the sourceUnit
	 */
	public String getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @return the sourceUnitRegion
	 */
	public Character getSourceUnitRegion() {
		return sourceUnitRegion;
	}

	/**
	 * @return the targetUnitRegion
	 */
	public Character getTargetUnitRegion() {
		return targetUnitRegion;
	}

	/**
	 * @return the requestByEmployee
	 */
	public boolean isRequestByEmployee() {
		return requestByEmployee;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * @param disposalTargetType the disposalTargetType to set
	 */
	public void setDisposalTargetType(String disposalTargetType) {
		this.disposalTargetType = disposalTargetType;
	}

	/**
	 * @param disposalTargetTypeKey the disposalTargetTypeKey to set
	 */
	public void setDisposalTargetTypeKey(String disposalTargetTypeKey) {
		this.disposalTargetTypeKey = disposalTargetTypeKey;
	}

	/**
	 * @param disposalType the disposalType to set
	 */
	public void setDisposalType(String disposalType) {
		this.disposalType = disposalType;
	}

	/**
	 * @param disposalTypeKey the disposalTypeKey to set
	 */
	public void setDisposalTypeKey(String disposalTypeKey) {
		this.disposalTypeKey = disposalTypeKey;
	}

	/**
	 * @param disposalUnit the disposalUnit to set
	 */
	public void setDisposalUnit(String disposalUnit) {
		this.disposalUnit = disposalUnit;
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

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}

	/**
	 * @param requestByEmployee the requestByEmployee to set
	 */
	public void setRequestByEmployee(boolean requestByEmployee) {
		this.requestByEmployee = requestByEmployee;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @param sourceUnitRegion the sourceUnitRegion to set
	 */
	public void setSourceUnitRegion(Character sourceUnitRegion) {
		this.sourceUnitRegion = sourceUnitRegion;
	}

	/**
	 * @param targetUnitRegion the targetUnitRegion to set
	 */
	public void setTargetUnitRegion(Character targetUnitRegion) {
		this.targetUnitRegion = targetUnitRegion;
	}

}