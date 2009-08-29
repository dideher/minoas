package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeLeaveReportItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeLeaveCriteria")
@Scope(ScopeType.PAGE)
public class EmployeeLeaveCriteria {
	
	private DateSearchType dateSearchType;

	private Date effectiveDate;

	private Date effectiveDateDueTo;

	private LeaveType leaveType;

	private Character region;

	private School schoolOfIntereset;

	private SpecializationGroup specializationGroup;

	/**
		 * 
		 */
	public EmployeeLeaveCriteria() {
		super();
		effectiveDate = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
		dateSearchType = DateSearchType.DURING_DATE_PERIOD;
	}

	/**
	 * @return the dateSearchType
	 */
	public DateSearchType getDateSearchType() {
		return dateSearchType;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return the effectiveDateDueTo
	 */
	public Date getEffectiveDateDueTo() {
		return effectiveDateDueTo;
	}

	/**
	 * @return the leaveType
	 */
	public LeaveType getLeaveType() {
		return leaveType;
	}

	/**
	 * @return the region
	 */
	public Character getRegion() {
		return region;
	}

	/**
	 * @return the schoolOfIntereset
	 */
	public School getSchoolOfIntereset() {
		return schoolOfIntereset;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @param dateSearchType the dateSearchType to set
	 */
	public void setDateSearchType(DateSearchType dateSearchType) {
		this.dateSearchType = dateSearchType;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @param effectiveDateDueTo the effectiveDateDueTo to set
	 */
	public void setEffectiveDateDueTo(Date effectiveDateDueTo) {
		this.effectiveDateDueTo = effectiveDateDueTo;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Character region) {
		this.region = region;
	}

	/**
	 * @param schoolOfIntereset the schoolOfIntereset to set
	 */
	public void setSchoolOfIntereset(School schoolOfIntereset) {
		this.schoolOfIntereset = schoolOfIntereset;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

}
