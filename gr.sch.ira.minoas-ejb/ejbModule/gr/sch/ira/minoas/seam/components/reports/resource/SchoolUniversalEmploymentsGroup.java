package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SchoolUniversalEmploymentsGroup extends AbstractList<SchoolUniversalEmploymentItem> {

	private Integer availableHours;

	private List<SchoolUniversalEmploymentItem> items = new ArrayList<SchoolUniversalEmploymentItem>();

	private Integer requiredHours;

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

	

	/**
	 * @see java.util.AbstractList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, SchoolUniversalEmploymentItem element) {
		this.add(element);
	}

	/**
	 * @see java.util.AbstractList#add(java.lang.Object)
	 */
	@Override
	public boolean add(SchoolUniversalEmploymentItem item) {
		requiredHours = Integer.valueOf(0);
		availableHours = Integer.valueOf(0);
		this.availableHours += item.getEmployeeFinalWorkingHours();
		return items.add(item);
	}

	/**
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public SchoolUniversalEmploymentItem get(int index) {
		return items.get(index);
	}

	/**
	 * @return the totalHours
	 */
	public Integer getAvailableHours() {
		return availableHours;
	}

	public Integer getMissingHours() {
		return null;
		//return getAvailableHours() - getRequiredHours();
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
	
	public String getSpecializationGroupTitle() {
		return specializationGroup.getTitle();
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
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return items.size();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchoolUniversalEmploymentsGroup [specializationGroup=" + specializationGroup + "]";
	}
}
