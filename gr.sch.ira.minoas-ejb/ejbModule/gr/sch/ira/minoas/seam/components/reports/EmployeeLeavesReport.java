package gr.sch.ira.minoas.seam.components.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeLeaveCriteria;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeLeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */

@Name(value = "employeeLeavesReport")
@Scope(ScopeType.PAGE)
public class EmployeeLeavesReport extends BaseReport {

	@In(create = true, required = true)
	private EmployeeLeaveCriteria employeeLeaveCriteria;

	@DataModel(value = "reportData")
	private List<EmployeeLeaveReportItem> reportData;

	/**
	 * 
	 */
	public EmployeeLeavesReport() {
	}

	public void generateReport() {
		
		System.err.println(getEmployeeLeaveCriteria().getDateSearchType());

		Date effectiveDate = getEmployeeLeaveCriteria().getEffectiveDate();
		Date effectiveDateDueTo = getEmployeeLeaveCriteria().getEffectiveDateDueTo();
		LeaveType leaveType = getEmployeeLeaveCriteria().getLeaveType();
		Character region = getEmployeeLeaveCriteria().getRegion();
		School school = getEmployeeLeaveCriteria().getSchoolOfIntereset();
		SpecializationGroup specializationGroup = getEmployeeLeaveCriteria().getSpecializationGroup();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT l FROM Leave l WHERE l.active IS TRUE ");
		if (effectiveDateDueTo == null || effectiveDateDueTo.equals(effectiveDate)) {
			sb.append(" AND (:effectiveDate BETWEEN l.established AND l.dueTo) ");
		} else {
			sb.append(" AND (:effectiveDate >= l.established AND l.dueTo <= :effectiveDateDueTo) ");
		}
		if (leaveType != null) {
			sb.append(" AND l.leaveType=:leaveType ");
		}
		if (school != null) {
			sb.append(" AND l.employee.currentEmployment.school = :school");
		}
		if (region != null) {
			sb.append(" AND l.employee.currentEmployment.school.regionCode = :region");
		}
		if(specializationGroup!=null) {
			sb.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND l.employee.lastSpecialization MEMBER OF g.specializations) ");
		}
		
		sb.append(" ORDER BY l.employee.lastName");

		Query q = getEntityManager().createQuery(sb.toString());
		if (effectiveDateDueTo == null || effectiveDateDueTo.equals(effectiveDate)) {
			q.setParameter("effectiveDate", effectiveDate);

		} else {
			q.setParameter("effectiveDate", effectiveDate);
			q.setParameter("effectiveDateDueTo", effectiveDateDueTo);
		}
		if (leaveType != null) {
			q.setParameter("leaveType", leaveType);
		}
		if (school != null) {
			q.setParameter("school", school);
		}
		if (region != null) {
			q.setParameter("region", region);
		}
		if(specializationGroup!=null) {
			q.setParameter("specializationGroup", specializationGroup);
		}

		Collection<Leave> leaves = q.getResultList();
		info("found totally #0 leaves matching criteria", leaves.size());
		reportData = new ArrayList<EmployeeLeaveReportItem>(leaves.size());
		for (Leave leave : leaves) {
			reportData.add(new EmployeeLeaveReportItem(leave));
		}
	}

	/**
	 * @return the employeeLeaveCriteria
	 */
	public EmployeeLeaveCriteria getEmployeeLeaveCriteria() {
		return employeeLeaveCriteria;
	}

	/**
	 * @return the reportData
	 */
	public List<EmployeeLeaveReportItem> getReportData() {
		return reportData;
	}

	/**
	 * @param employeeLeaveCriteria the employeeLeaveCriteria to set
	 */
	public void setEmployeeLeaveCriteria(EmployeeLeaveCriteria employeeLeaveCriteria) {
		this.employeeLeaveCriteria = employeeLeaveCriteria;
	}

}
