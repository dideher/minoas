package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.seam.components.reports.TeachingHourAnalysisReport;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class TeachingHoursAnalysisItem {

	private Collection<SchoolTeachingHoursItem> schools = new ArrayList<SchoolTeachingHoursItem>();

	private Collection<SpecializationGroup> specializationGroups = new ArrayList<SpecializationGroup>();

	private Integer totalAvailableHours = 0;

	private Integer totalRequiredHours = 0;
	
	public boolean isVoid() {
		return getTotalMissingReqularEmployees() < 0;
	}

	public Integer getTotalMissingReqularEmployees() {
		return ( getTotalMissingHours()) / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}
	/**
	 * 
	 */
	public TeachingHoursAnalysisItem() {
		super();
	}

	public TeachingHoursAnalysisItem(SpecializationGroup specializationGroup) {
		this();
		this.specializationGroups.add(specializationGroup);
	}

	/**
	 * @param specializationGroups
	 */
	public TeachingHoursAnalysisItem(Collection<SpecializationGroup> specializationGroups) {
		this();
		this.specializationGroups.addAll(specializationGroups);
	}

	/**
	 * @return the schools
	 */
	public Collection<SchoolTeachingHoursItem> getSchools() {
		return schools;
	}

	/**
	 * @return the specializationGroups
	 */
	public Collection<SpecializationGroup> getSpecializationGroups() {
		return specializationGroups;
	}

	/**
	 * @return the totalAvailableHours
	 */
	public Integer getTotalAvailableHours() {
		return totalAvailableHours;
	}

	/**
	 * @return the totalRequiredHours
	 */
	public Integer getTotalRequiredHours() {
		return totalRequiredHours;
	}
	
	public Integer getTotalMissingHours() {
		return (totalAvailableHours-totalRequiredHours);
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(Collection<SchoolTeachingHoursItem> schools) {
		this.schools = schools;
	}
	
	public void addSchoolItem(SchoolTeachingHoursItem schoolItem) {
		totalAvailableHours+=schoolItem.getAvailableHours();
		totalRequiredHours+=schoolItem.getRequiredHours();
		this.schools.add(schoolItem);
	}

}
