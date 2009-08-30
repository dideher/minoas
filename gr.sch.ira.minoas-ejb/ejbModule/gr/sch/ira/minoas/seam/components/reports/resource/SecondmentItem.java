package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import java.util.Date;

import javax.print.attribute.standard.Chromaticity;

public class SecondmentItem extends EmployeeReportItem {
	private String comment;
	private Date dueTo;
	private Date establishedIn;
	private Character region;
	private boolean requestByEmployee;
	private String secondmentType;
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
			this.region = ((School)secondment.getSourceUnit()).getRegionCode();
		}
		this.secondmentType = secondment.getSecondmentType().name();
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
	public Character getRegion() {
		return region;
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
	public void setRegion(Character region) {
		this.region = region;
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


}