package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.seam.components.reports.TeachingHourAnalysisReport;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SchoolTeachingHoursItem {

	private School school;
	
	private Integer availableHours = 0;

	private Integer requiredHours = 0;
	
	public Integer getMissingHours() {
		return (availableHours-requiredHours);
	}
	
	public Integer getMissingReqularEmployees() {
		return (getMissingHours()) / TeachingHourAnalysisReport.HOURS_FOR_REGULAR_POSITION;
	}
	/**
	 * 
	 */
	public SchoolTeachingHoursItem() {
	}
	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}
	/**
	 * @return the availableHours
	 */
	public Integer getAvailableHours() {
		return availableHours;
	}
	/**
	 * @param availableHours the availableHours to set
	 */
	public void setAvailableHours(Integer availableHours) {
		this.availableHours = availableHours;
	}
	/**
	 * @return the requiredHours
	 */
	public Integer getRequiredHours() {
		return requiredHours;
	}
	/**
	 * @param requiredHours the requiredHours to set
	 */
	public void setRequiredHours(Integer requiredHours) {
		this.requiredHours = requiredHours;
	}

}
