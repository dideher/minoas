package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SchoolUniversalEmploymentsGroup {
	
	private List<SchoolUniversalEmploymentItem> items;
	private Integer requiredHours;
	
	private SpecializationGroup specializationGroup;
	private Integer availableHours;
	
	public Collection<SchoolUniversalEmploymentItem> getEmploymentItems() {
		return items;
	}

	/**
	 * 
	 */
	public SchoolUniversalEmploymentsGroup() {
		super();
		this.items = new ArrayList<SchoolUniversalEmploymentItem>();
	}

	public void add(SchoolUniversalEmploymentItem item) {
		items.add(item);
		requiredHours = Integer.valueOf(0);
		availableHours = Integer.valueOf(0);
		this.availableHours+=item.getEmployeeFinalWorkingHours();
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

	/**
	 * @return the totalHours
	 */
	public Integer getAvailableHours() {
		return availableHours;
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

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setAvailableHours(Integer totalHours) {
		this.availableHours = totalHours;
	}

	public boolean hasMissingHours() {
		return getMissingHours() > 0;
	}
	
	public Integer getMissingHours() {
		return null;
		//return getAvailableHours() - getRequiredHours();
	}
}
