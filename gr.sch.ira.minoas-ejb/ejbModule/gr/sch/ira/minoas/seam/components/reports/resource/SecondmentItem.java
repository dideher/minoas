package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import java.util.Date;

import javax.print.attribute.standard.Chromaticity;

public class SecondmentItem {
	private String specialization;
	private String specializationID;
	private String employeeName;
	private String employeeSurname;
	private String employeeFather;
	private String employeeCode;
	private String comment;
	private boolean requestByEmployee;
	private Date dueTo;
	private Date establishedIn;
	private String sourceUnit;
	private String targetUnit;
	private String secondmentType;
	private Character region;

	public SecondmentItem() {
		super();
	}
	
	public SecondmentItem(Secondment secondment) {
		super();
		this.employeeName = secondment.getEmployee().getFirstName();
		this.employeeSurname = secondment.getEmployee().getLastName();
		this.employeeFather = secondment.getEmployee().getFatherName();
		if(secondment.getEmployee().getRegularDetail()!=null)
			this.employeeCode = secondment.getEmployee().getRegularDetail().getRegistryID();
		this.specialization = secondment.getEmployee().getLastSpecialization().getTitle();
		this.comment = secondment.getComment();
		this.dueTo = secondment.getDueTo();
		this.establishedIn = secondment.getEstablished();
		this.requestByEmployee = secondment.isEmployeeRequested();
		this.sourceUnit = secondment.getSourceUnit().getTitle();
		this.targetUnit = secondment.getTargetUnit().getTitle();
		this.specializationID = secondment.getEmployee().getLastSpecialization().getId();
		this.employeeName = secondment.getEmployee().getFirstName();
		if(secondment.getSourceUnit() instanceof School) {
			this.region = ((School)secondment.getSourceUnit()).getRegionCode();
		}
		this.secondmentType = secondment.getSecondmentType().name();
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(String specializationID) {
		this.specializationID = specializationID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}

	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getEmployeeFather() {
		return employeeFather;
	}

	public void setEmployeeFather(String employeeFather) {
		this.employeeFather = employeeFather;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isRequestByEmployee() {
		return requestByEmployee;
	}

	public void setRequestByEmployee(boolean requestByEmployee) {
		this.requestByEmployee = requestByEmployee;
	}

	public Date getDueTo() {
		return dueTo;
	}

	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	public Date getEstablishedIn() {
		return establishedIn;
	}

	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	public String getSourceUnit() {
		return sourceUnit;
	}

	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	public String getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
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
	 * @return the secondmentType
	 */
	public String getSecondmentType() {
		return secondmentType;
	}

	/**
	 * @param secondmentType the secondmentType to set
	 */
	public void setSecondmentType(String secondmentType) {
		this.secondmentType = secondmentType;
	}


}