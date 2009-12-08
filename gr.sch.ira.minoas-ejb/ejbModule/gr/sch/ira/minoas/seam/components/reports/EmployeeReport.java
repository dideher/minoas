package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeCriteria;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "employeeReport")
@Scope(ScopeType.CONVERSATION)
public class EmployeeReport extends BaseReport {

	@In(create = true, required = true)
	private EmployeeCriteria employeeCriteria;

	@DataModel(value = "reportData")
	private List<EmployeeReportItem> reportData;

	/**
	 * 
	 */
	public EmployeeReport() {
	}

	public void generatePDFReport() throws Exception {

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("EMPLOYEE_TYPE_FILTER",
					employeeCriteria.getType() != null ? getLocalizedMessage(employeeCriteria.getType().getKey())
							: "Όλοι οι Τύποι");
			switch (getEmployeeCriteria().getSpecializationSearchType()) {
			case SPECIALIZATION_GROUP:
				parameters.put("EMPLOYEE_SPECIALIZATION_FILTER",
						employeeCriteria.getSpecializationGroup() != null ? employeeCriteria.getSpecializationGroup()
								.getTitle() : "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			case SPECIALIZATION:
				parameters.put("EMPLOYEE_SPECIALIZATION_FILTER",
						employeeCriteria.getSpecialization() != null ? employeeCriteria.getSpecialization().getTitle()
								: "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			}
			parameters.put("EMPLOYEE_DATE_SEARCH_FILTER", getLocalizedMessage(employeeCriteria.getDateSearchType()
					.getKey()));

			parameters.put("EMPLOYEE_EFFECTIVE_DATE_FILTER", employeeCriteria.getEffectiveDate());
			parameters.put("EMPLOYEE_EFFECTIVE_DATE_FROM_FILTER", employeeCriteria.getEffectiveDateFrom());
			parameters.put("EMPLOYEE_EFFECTIVE_DATE_UNTIL_FILTER", employeeCriteria.getEffectiveDateUntil());

			/* create the leave type helper */
			for (EmployeeType employeeType : getCoreSearching().getEmployeeTypes()) {
				parameters.put(employeeType.name(), getLocalizedMessage(employeeType.getKey()));
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
			byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
					"/reports/employeeByType.jasper"), parameters, (JRDataSource) ds);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=EmployeeReportByType.pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}

	}

	/**
	 * 
	 */
	public void generateReport() {

		Date effectiveDate = getEmployeeCriteria().getEffectiveDate();
		Date effectiveDateFrom = getEmployeeCriteria().getEffectiveDateFrom();
		Date effectiveDateUntil = getEmployeeCriteria().getEffectiveDateUntil();
		EmployeeType employeeTye = getEmployeeCriteria().getType();
		SpecializationSearchType specializationSearchType = getEmployeeCriteria().getSpecializationSearchType();
		SpecializationGroup specializationGroup = getEmployeeCriteria().getSpecializationGroup();
		Specialization specialization = getEmployeeCriteria().getSpecialization();
		Boolean specialCategory = getEmployeeCriteria().getSpecialCategory();
		Boolean bigFamily = getEmployeeCriteria().getBigFamily();

		DateSearchType dateSearchType = getEmployeeCriteria().getDateSearchType();
		//
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT e FROM Employee e WHERE e.active IS TRUE ");
		switch (dateSearchType) {
		case AFTER_DATE:
			sb
					.append(" AND EXISTS (SELECT em FROM Employment em WHERE em.employee=e AND em.active IS TRUE AND em.established >= :effectiveDate) ");
			break;
		case BEFORE_DATE:
			sb
					.append(" AND EXISTS (SELECT em FROM Employment em WHERE em.employee=e AND em.active IS TRUE AND em.established <= :effectiveDate) ");
			break;
		case DURING_DATE:
			sb
					.append(" AND EXISTS (SELECT em FROM Employment em WHERE em.employee=e AND em.active IS TRUE AND (:effectiveDate BETWEEN em.established AND em.terminated)) ");
			break;
		case DURING_DATE_PERIOD:
			sb
					.append(" AND EXISTS (SELECT em FROM Employment em WHERE em.employee=e AND em.active IS TRUE AND (:effectiveDateFrom <= em.established AND  :effectiveDateUntil >= em.terminated)) ");
			break;
		}
		if (employeeTye != null) {
			sb.append(" AND e.type=:employeeType ");
		}

		//		if (school != null) {
		//			sb.append(" AND l.employee.currentEmployment.school = :school ");
		//		}
		//		if (region != null) {
		//			sb
		//					.append(" AND l.employee.currentEmployment.school.regionCode = :region ");
		//		}
		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND e.lastSpecialization MEMBER OF g.specializations) ");
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			sb.append(" AND e.lastSpecialization=:specialization) ");
		}

		sb.append(" ORDER BY e.lastName, e.firstName, e.lastSpecialization.id");

		Query q = getEntityManager().createQuery(sb.toString());
		if (dateSearchType != DateSearchType.DURING_DATE_PERIOD) {
			q.setParameter("effectiveDate", effectiveDate);

		} else {
			q.setParameter("effectiveDateFrom", effectiveDateFrom);
			q.setParameter("effectiveDateUntil", effectiveDateUntil);
		}
		if (employeeTye != null) {
			q.setParameter("employeeType", employeeTye);
		}
		//		if (school != null) {
		//			q.setParameter("school", school);
		//		}
		//		if (region != null) {
		//			q.setParameter("region", region);
		//		}
		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			q.setParameter("specialization", specialization);
		}

		Collection<Employee> employees = q.getResultList();
		info("found totally #0 employees matching criteria", employees.size());
		reportData = new ArrayList<EmployeeReportItem>(employees.size());
		for (Employee employee : employees) {
			reportData.add(new EmployeeReportItem(employee));
		}
	}

	/**
	 * @return the employeeCriteria
	 */
	public EmployeeCriteria getEmployeeCriteria() {
		return employeeCriteria;
	}

	/**
	 * @return the reportData
	 */
	public List<EmployeeReportItem> getReportData() {
		return reportData;
	}

	/**
	 * @param employeeCriteria the employeeCriteria to set
	 */
	public void setEmployeeCriteria(EmployeeCriteria employeeCriteria) {
		this.employeeCriteria = employeeCriteria;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(List<EmployeeReportItem> reportData) {
		this.reportData = reportData;
	}

}
