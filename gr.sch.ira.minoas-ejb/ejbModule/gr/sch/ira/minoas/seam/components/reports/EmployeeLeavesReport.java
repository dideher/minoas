package gr.sch.ira.minoas.seam.components.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.core.SeamResourceBundle;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeLeaveCriteria;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeLeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */

@Name(value = "employeeLeavesReport")
@Scope(ScopeType.CONVERSATION)
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

	

	public void generatePDFReport() {

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters
					.put(
							"LEAVE_TYPE_FILTER",
							employeeLeaveCriteria.getLeaveType() != null ? getLocalizedMessage(employeeLeaveCriteria
									.getLeaveType().getKey())
									: "Όλοι οι Τύποι");
			parameters.put("LEAVE_SPECIALIZATION_FILTER", employeeLeaveCriteria
					.getSpecializationGroup() != null ? employeeLeaveCriteria
					.getSpecializationGroup().getTitle()
					: "Όλες οι Ειδικότητες");

			parameters.put("LEAVE_SCHOOL_FILTER", employeeLeaveCriteria
					.getSchoolOfIntereset() != null ? employeeLeaveCriteria
					.getSchoolOfIntereset().getTitle()
					: "Όλες οι Σχολικές Μονάδες");

			parameters.put("LEAVE_REGION_FILTER", employeeLeaveCriteria
					.getRegion() != null ? employeeLeaveCriteria.getRegion()
					.toString()
					+ "' Ηρακλείου" : "Όλες οι Περιοχές");

			parameters.put("LEAVE_DATE_SEARCH_FILTER",
					getLocalizedMessage(employeeLeaveCriteria
							.getDateSearchType().getKey()));

			parameters.put("LEAVE_EFFECTIVE_DATE_FILTER", employeeLeaveCriteria
					.getEffectiveDate());
			parameters.put("LEAVE_EFFECTIVE_DATE_FROM_FILTER", employeeLeaveCriteria
					.getEffectiveDateFrom());
			parameters.put("LEAVE_EFFECTIVE_DATE_UNTIL_FILTER", employeeLeaveCriteria
					.getEffectiveDateUntil());

			/* create the leave type helper */
			for (LeaveType leaveType : getCoreSearching()
					.getAvailableLeaveTypes()) {
				parameters.put(leaveType.name(), getLocalizedMessage(leaveType
						.getKey()));
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					reportData);
			byte[] bytes = JasperRunManager.runReportToPdf(this.getClass()
					.getResourceAsStream("/reports/leaveByType.jasper"),
					parameters, (JRDataSource) ds);
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=LeaveReportByType.pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}

	}

	public void generateReport() {

		Date effectiveDate = getEmployeeLeaveCriteria().getEffectiveDate();
		Date effectiveDateFrom = getEmployeeLeaveCriteria()
				.getEffectiveDateFrom();
		Date effectiveDateUntil = getEmployeeLeaveCriteria()
				.getEffectiveDateUntil();
		LeaveType leaveType = getEmployeeLeaveCriteria().getLeaveType();
		Character region = getEmployeeLeaveCriteria().getRegion();
		School school = getEmployeeLeaveCriteria().getSchoolOfIntereset();
		SpecializationGroup specializationGroup = getEmployeeLeaveCriteria()
				.getSpecializationGroup();
		DateSearchType dateSearchType = getEmployeeLeaveCriteria()
				.getDateSearchType();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT l FROM Leave l WHERE l.active IS TRUE ");
		switch (dateSearchType) {
		case AFTER_DATE:
			sb.append("AND l.established >= :effectiveDate ");
			break;
		case BEFORE_DATE:
			sb.append("AND l.dueTo <= :effectiveDate ");
			break;
		case DURING_DATE:
			sb
					.append(" AND (:effectiveDate BETWEEN l.established AND l.dueTo) ");
			break;
		case DURING_DATE_PERIOD:
			sb
					.append(" AND (:effectiveDateFrom <= l.established AND  :effectiveDateUntil >= l.dueTo) ");
			break;
		}
		if (leaveType != null) {
			sb.append(" AND l.leaveType=:leaveType ");
		}
		if (school != null) {
			sb.append(" AND l.employee.currentEmployment.school = :school ");
		}
		if (region != null) {
			sb
					.append(" AND l.employee.currentEmployment.school.regionCode = :region ");
		}
		if (specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND l.employee.lastSpecialization MEMBER OF g.specializations) ");
		}

		sb.append(" ORDER BY l.employee.lastName");

		Query q = getEntityManager().createQuery(sb.toString());
		if (dateSearchType != DateSearchType.DURING_DATE_PERIOD) {
			q.setParameter("effectiveDate", effectiveDate);

		} else {
			q.setParameter("effectiveDateFrom", effectiveDateFrom);
			q.setParameter("effectiveDateUntil", effectiveDateUntil);
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
		if (specializationGroup != null) {
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
	 * @param employeeLeaveCriteria
	 *            the employeeLeaveCriteria to set
	 */
	public void setEmployeeLeaveCriteria(
			EmployeeLeaveCriteria employeeLeaveCriteria) {
		this.employeeLeaveCriteria = employeeLeaveCriteria;
	}

}
