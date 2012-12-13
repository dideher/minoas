package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchoolUniversalEmploymentsGroup {

	private Integer availableHours = 0;

	private List<SchoolUniversalEmploymentItem> items = new ArrayList<SchoolUniversalEmploymentItem>();

	private Integer mandatoryHours = 0;

	private Integer requiredHours = 0;

	private SpecializationGroup specializationGroup;

	/**
	 * 
	 */
	public SchoolUniversalEmploymentsGroup() {
		super();
	}

	/**
	 * @param specializationGroup
	 */
	public SchoolUniversalEmploymentsGroup(SpecializationGroup specializationGroup) {
		this();
		this.specializationGroup = specializationGroup;
	}

	public boolean add(SchoolUniversalEmploymentItem item) {

		this.availableHours += item.getEmployeeFinalWorkingHours();
		this.mandatoryHours += item.getEmployeeMandatoryHours();
		return items.add(item);
	}

	/**
	 * @return the totalHours
	 */
	public Integer getAvailableHours() {
		return availableHours;
	}

	public List<SchoolUniversalEmploymentItem> getItems() {
		return Collections.unmodifiableList(this.items);
	}

	/**
	 * @return the mandatoryHours
	 */
	public Integer getMandatoryHours() {
		return mandatoryHours;
	}

	public Integer getMissingHours() {
		return getAvailableHours() - getRequiredHours();
	}

	/**
	 * @return the requiredHours
	 */
	public Integer getRequiredHours() {
		return requiredHours;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	public boolean hasMissingHours() {
		return getMissingHours() > 0;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setAvailableHours(Integer totalHours) {
		this.availableHours = totalHours;
	}

	/**
	 * @param mandatoryHours the mandatoryHours to set
	 */
	public void setMandatoryHours(Integer mandatoryHours) {
		this.mandatoryHours = mandatoryHours;
	}

	/**
	 * @param requiredHours the requiredHours to set
	 */
	public void setRequiredHours(Integer requiredHours) {
		this.requiredHours = requiredHours;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

	public int size() {
		return items.size();
	}

}
