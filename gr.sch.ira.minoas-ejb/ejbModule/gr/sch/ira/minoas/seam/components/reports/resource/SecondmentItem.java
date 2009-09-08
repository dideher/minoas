package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.Secondment;

import java.util.Date;

public class SecondmentItem extends EmployeeReportItem {
	private String comment;
	private Date dueTo;
	private Date establishedIn;
	private Character sourceUnitRegion;
	private Character targetUnitRegion;
	private boolean requestByEmployee;
	private String secondmentType;
	private String secondmentTypeKey;
	private Integer finalWorkingHours;
	private String sourceUnit;
	private String targetUnit;

	public SecondmentItem() {
		super();
	}
	
	public SecondmentItem(Secondment secondment) {
		super(secondment.getEmployee());
		this.comment = secondment.getComment();
		this.dueTo = secondment.getDueTo();
		this.establishedIn = secondment.getEstablished();
		this.requestByEmployee = secondment.isEmployeeRequested();
		this.sourceUnit = secondment.getSourceUnit().getTitle();
		this.targetUnit = secondment.getTargetUnit().getTitle();
		if(secondment.getSourceUnit() instanceof School) {
			this.sourceUnitRegion = ((School)secondment.getSourceUnit()).getRegionCode();
		}
		if(secondment.getTargetUnit() instanceof School) {
			this.targetUnitRegion = ((School)secondment.getTargetUnit()).getRegionCode();
		}
		this.secondmentType = secondment.getSecondmentType().name();
		this.secondmentTypeKey = secondment.getSecondmentType().getKey();
		this.finalWorkingHours = secondment.getFinalWorkingHours();
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
	 * @return the region
	 */
	public Character getSourceUnitRegion() {
		return sourceUnitRegion;
	}

	/**
	 * @return the secondmentType
	 */
	public String getSecondmentType() {
		return secondmentType;
	}

	public String getSourceUnit() {
		return sourceUnit;
	}

	public String getTargetUnit() {
		return targetUnit;
	}

	public boolean isRequestByEmployee() {
		return requestByEmployee;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	/**
	 * @param region the region to set
	 */
	public void setSourceUnitRegion(Character region) {
		this.sourceUnitRegion = region;
	}

	public void setRequestByEmployee(boolean requestByEmployee) {
		this.requestByEmployee = requestByEmployee;
	}

	/**
	 * @param secondmentType the secondmentType to set
	 */
	public void setSecondmentType(String secondmentType) {
		this.secondmentType = secondmentType;
	}

	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}

	/**
	 * @return the secondmentTypeKey
	 */
	public String getSecondmentTypeKey() {
		return secondmentTypeKey;
	}

	/**
	 * @param secondmentTypeKey the secondmentTypeKey to set
	 */
	public void setSecondmentTypeKey(String secondmentTypeKey) {
		this.secondmentTypeKey = secondmentTypeKey;
	}

	/**
	 * @return the finalWorkingHours
	 */
	public Integer getFinalWorkingHours() {
		return finalWorkingHours;
	}

	/**
	 * @param finalWorkingHours the finalWorkingHours to set
	 */
	public void setFinalWorkingHours(Integer finalWorkingHours) {
		this.finalWorkingHours = finalWorkingHours;
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
	
	public Character getRegion() {
		return getSourceUnitRegion();
	}


}