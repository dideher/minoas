package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.seam.components.reports.TeachingHourAnalysisReport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class TeachingHoursAnalysisItem {

	private Map<String, SchoolTeachingHoursItem> internalCacheMap;

	private Collection<SchoolTeachingHoursItem> schools = new ArrayList<SchoolTeachingHoursItem>();

	private Collection<SpecializationGroup> specializationGroups = new ArrayList<SpecializationGroup>();

	private Integer totalAvailableHours = 0;

	private Integer totalRequiredHours = 0;

	/**
	 * 
	 */
	public TeachingHoursAnalysisItem() {
		super();
	}

	/**
	 * @param specializationGroups
	 */
	public TeachingHoursAnalysisItem(Collection<School> schools, Collection<SpecializationGroup> specializationGroups) {
		this();
		this.specializationGroups.addAll(specializationGroups);
		internalPopulateWithSchools(schools);
	}

	public TeachingHoursAnalysisItem(Collection<School> schools, SpecializationGroup specializationGroup) {
		this();
		this.specializationGroups.add(specializationGroup);
		internalPopulateWithSchools(schools);
	}

	public void addSchoolAvailableHours(School school, int availableHours) {
		SchoolTeachingHoursItem item = internalCacheMap.get(school.getId());
		if (item != null) {
			int hours = item.getAvailableHours() + availableHours;
			item.setAvailableHours(hours);
		}
	}

	public void addSchoolRequiredHours(School school, int requiredHours) {
		SchoolTeachingHoursItem item = internalCacheMap.get(school.getId());
		if (item != null) {
			int hours = item.getRequiredHours() + requiredHours;
			item.setRequiredHours(hours);
		}
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

	public Integer getTotalMissingHours() {
		return (totalAvailableHours - totalRequiredHours);
	}

	public Integer getTotalMissingReqularEmployees() {
		return (getTotalMissingHours()) / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}

	/**
	 * @return the totalRequiredHours
	 */
	public Integer getTotalRequiredHours() {
		return totalRequiredHours;
	}

	private void internalPopulateWithSchools(Collection<School> schools) {
		internalCacheMap = new HashMap<String, SchoolTeachingHoursItem>(schools.size());
		for (School school : schools) {

			SchoolTeachingHoursItem item = new SchoolTeachingHoursItem(school, 0, 0);
			internalCacheMap.put(school.getId(), item);
			this.schools.add(item);
		}
	}

	public boolean isVoid() {
		return getTotalMissingReqularEmployees() < 0;
	}

	public void removeItemsWithZeroHours() {
		for (Iterator<SchoolTeachingHoursItem> it = schools.iterator(); it.hasNext();) {
			SchoolTeachingHoursItem item = it.next();
			if (item.getRequiredHours() == 0 && item.getAvailableHours() == 0) {
				it.remove();
				internalCacheMap.remove(item.getSchool().getId());
			}
		}
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(Collection<SchoolTeachingHoursItem> schools) {
		this.schools = schools;
	}

}
