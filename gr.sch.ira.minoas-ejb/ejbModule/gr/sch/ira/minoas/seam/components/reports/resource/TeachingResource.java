package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.seam.components.reports.TeachingHourAnalysisReport;

public class TeachingResource {
	private Integer required;

	private SpecializationGroup specializationGroup;

	private Integer available;

	/**
	 * 
	 */
	public TeachingResource() {
		super();
		this.required = 0;
		this.available = 0;
	}

	/**
	 * @param specializationGroup
	 */
	public TeachingResource(SpecializationGroup specializationGroup) {
		this();
		this.specializationGroup = specializationGroup;

	}

	public void addHours(Integer hours) {
		available += hours;
	}

	public Integer getMissingReqularEmployees() {
		return ((-1) * getMissingHours()) / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}

	public Float getMissingReqularEmployeesAsFloat() {
		return new Float((float) ((-1) * getMissingHours()) / (float) TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION);
	}

	public boolean isVoid() {
		return getMissingReqularEmployees() > 0;
	}

	public Integer getMissingHours() {
		return available - required;
	}

	/**
	 * @return the required
	 */
	public Integer getRequired() {
		return required;
	}

	public Integer getRequiredRegularEmployees() {
		return required > 0 ? required / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION : 0;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @return the total
	 */
	public Integer getAvailable() {
		return available;
	}

	public boolean hasMissingHours() {
		return getMissingHours() > 0;
	}

	public void removeHours(Integer hours) {
		available -= hours;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(Integer required) {
		this.required = required;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

	/**
	 * @param total the total to set
	 */
	public void setAvailable(Integer total) {
		this.available = total;
	}
}
