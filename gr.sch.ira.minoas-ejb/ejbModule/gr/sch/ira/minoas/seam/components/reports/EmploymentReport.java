package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolType;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.seam.components.criteria.EmploymentCriteria;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;

import java.util.ArrayList;
import java.util.Collection;
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
@Name(value = "employmentReport")
@Scope(ScopeType.CONVERSATION)
public class EmploymentReport extends BaseReport {

	@In(create = true, required = true)
	private EmploymentCriteria employmentCriteria;

	@DataModel(value = "reportData")
	private List<EmploymentReportItem> reportData;

	/**
	 * 
	 */
	public EmploymentReport() {
	}

	public void generatePDFReport() throws Exception {

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("EMPLOYMENT_TYPE_FILTER",
					employmentCriteria.getType() != null ? getLocalizedMessage(employmentCriteria.getType().getKey())
							: "Όλοι οι Τύποι");
			switch (getEmploymentCriteria().getSpecializationSearchType()) {
			case SPECIALIZATION_GROUP:
				parameters.put("EMPLOYMENT_SPECIALIZATION_FILTER",
						employmentCriteria.getSpecializationGroup() != null ? employmentCriteria
								.getSpecializationGroup().getTitle() : "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			case SPECIALIZATION:
				parameters.put("EMPLOYMENT_SPECIALIZATION_FILTER",
						employmentCriteria.getSpecialization() != null ? employmentCriteria.getSpecialization()
								.getTitle() : "Όλες οι Ομάδες Ειδικοτήτων");
				break;
			}
			if (employmentCriteria.getRegion() != null)
				parameters.put("EMPLOYMENT_SCHOOL_REGION_FILTER", employmentCriteria.getRegion() + " ΗΡΑΚΛΕΙΟΥ");
			else
				parameters.put("EMPLOYMENT_SCHOOL_REGION_FILTER", "Όλες οι Περιοχές");
			parameters.put("EMPLOYMENT_SCHOOL_YEAR_FILTER", employmentCriteria.getSchoolYear().getDescription());

			/* create the leave type helper */
			for (EmployeeType employeeType : getCoreSearching().getEmployeeTypes()) {
				parameters.put(employeeType.name(), getLocalizedMessage(employeeType.getKey()));
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
			byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
					"/reports/employmentByType.jasper"), parameters, (JRDataSource) ds);
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

		EmploymentType employeeTye = getEmploymentCriteria().getType();
		SpecializationSearchType specializationSearchType = getEmploymentCriteria().getSpecializationSearchType();
		SpecializationGroup specializationGroup = getEmploymentCriteria().getSpecializationGroup();
		Specialization specialization = getEmploymentCriteria().getSpecialization();
		SchoolYear schoolYear = getEmploymentCriteria().getSchoolYear();
		Character region = getEmploymentCriteria().getRegion();
		SchoolType schoolType = getEmploymentCriteria().getSchoolType();

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT em FROM Employment em JOIN FETCH em.employee WHERE em.active IS TRUE AND em.employee.active IS TRUE AND em.schoolYear=:schoolYear ");

		if (employeeTye != null) {
			sb.append(" AND em.type=:employmentType ");
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND em.specialization MEMBER OF g.specializations) ");
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			sb.append(" AND em.specialization = :specialization ");
		}

		if (region != null) {
			sb.append(" AND em.school.regionCode = :region ");
		}

		if (schoolType != null) {
			sb.append(" AND em.school.type = :schoolType ");
		}

		if (String.valueOf(getEmploymentCriteria().getSorting()).equals("specialization"))
			sb.append(" ORDER BY em.specialization.id, em.employee.lastName, em.employee.firstName");
		else if (String.valueOf(getEmploymentCriteria().getSorting()).equals("surname"))
			sb.append(" ORDER BY em.employee.lastName, em.employee.firstName");
		else
			sb.append(" ORDER BY em.type,em.specialization.id, em.employee.lastName, em.employee.firstName");
		Query q = getEntityManager().createQuery(sb.toString());
		q.setParameter("schoolYear", schoolYear);

		if (employeeTye != null) {
			q.setParameter("employmentType", employeeTye);
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			q.setParameter("specialization", specialization);
		}

		if (region != null) {
			q.setParameter("region", region);
		}

		if (schoolType != null) {
			q.setParameter("schoolType", schoolType);
		}

		Collection<Employment> employments = q.getResultList();
		info("found totally #0 employments matching criteria", employments.size());
		reportData = new ArrayList<EmploymentReportItem>(employments.size());
		for (Employment employment : employments) {
			reportData.add(new EmploymentReportItem(employment));
		}
	}

	/**
	 * @return the employeeCriteria
	 */
	public EmploymentCriteria getEmploymentCriteria() {
		return employmentCriteria;
	}

	/**
	 * @return the reportData
	 */
	public List<EmploymentReportItem> getReportData() {
		return reportData;
	}

	/**
	 * @param employeeCriteria the employeeCriteria to set
	 */
	public void setEmploymentCriteria(EmploymentCriteria employeeCriteria) {
		this.employmentCriteria = employeeCriteria;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(List<EmploymentReportItem> reportData) {
		this.reportData = reportData;
	}

}
