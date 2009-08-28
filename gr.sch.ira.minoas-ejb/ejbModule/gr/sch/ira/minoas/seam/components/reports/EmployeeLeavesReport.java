package gr.sch.ira.minoas.seam.components.reports;

import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeLeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */


@Name(value = "employeeLeavesReport")
@Scope(ScopeType.PAGE)
public class EmployeeLeavesReport extends BaseReport {

	private Date effectiveDate;
	
	private LeaveType leaveType;
	
	private Character region;
	
	@DataModel(value="reportData")
	private List<EmployeeLeaveReportItem> reportData;
	
	private School schoolOfIntereset;
	
	private SpecializationGroup specializationGroup;




	/**
	 * 
	 */
	public EmployeeLeavesReport() {
	}
	
	public void generateReport() {
		
	}




	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
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
	 * @return the reportData
	 */
	public List<EmployeeLeaveReportItem> getReportData() {
		return reportData;
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
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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
	 * @param reportData the reportData to set
	 */
	public void setReportData(List<EmployeeLeaveReportItem> reportData) {
		this.reportData = reportData;
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
