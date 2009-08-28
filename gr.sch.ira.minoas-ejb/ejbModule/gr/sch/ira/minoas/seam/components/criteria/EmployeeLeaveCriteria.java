package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeLeaveReportItem;

import java.util.Date;
import java.util.List;

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
private Date effectiveDate;
	
	private LeaveType leaveType;
	
	private Character region;
	
	private School schoolOfIntereset;
	
	private SpecializationGroup specializationGroup;

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the leaveType
	 */
	public LeaveType getLeaveType() {
		return leaveType;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
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
	 * @return the schoolOfIntereset
	 */
	public School getSchoolOfIntereset() {
		return schoolOfIntereset;
	}

	/**
	 * @param schoolOfIntereset the schoolOfIntereset to set
	 */
	public void setSchoolOfIntereset(School schoolOfIntereset) {
		this.schoolOfIntereset = schoolOfIntereset;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}



}
