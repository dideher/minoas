package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolType;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeTeachingHoursReportCriteria;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeTeachingHoursReportItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "employeeTeachingHoursReport")
@Scope(ScopeType.CONVERSATION)
public class EmployeeTeachingHoursReport extends BaseReport {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(create = true, required = true)
	private EmployeeTeachingHoursReportCriteria employeeTeachingHoursReportCriteria;

	@DataModel(value = "reportData")
	private List<EmployeeTeachingHoursReportItem> reportData = new ArrayList<EmployeeTeachingHoursReportItem>();

	/**
	 * 
	 */
	public EmployeeTeachingHoursReport() {
	}

	
//	protected Map<String, Object> constructReportParameters() {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("EMPLOYMENT_TYPE_FILTER",
//				employmentCriteria.getType() != null ? getLocalizedMessage(employmentCriteria.getType().getKey())
//						: "Όλοι οι Τύποι");
//		switch (getEmploymentCriteria().getSpecializationSearchType()) {
//		case SPECIALIZATION_GROUP:
//			parameters.put("EMPLOYMENT_SPECIALIZATION_FILTER",
//					employmentCriteria.getSpecializationGroup() != null ? employmentCriteria.getSpecializationGroup()
//							.getTitle() : "Όλες οι Ομάδες Ειδικοτήτων");
//			break;
//		case SPECIALIZATION:
//			parameters.put("EMPLOYMENT_SPECIALIZATION_FILTER",
//					employmentCriteria.getSpecialization() != null ? employmentCriteria.getSpecialization().getTitle()
//							: "Όλες οι Ομάδες Ειδικοτήτων");
//			break;
//		}
//		if (employmentCriteria.getRegion() != null)
//			parameters.put("EMPLOYMENT_SCHOOL_REGION_FILTER", employmentCriteria.getRegion() + " ΗΡΑΚΛΕΙΟΥ");
//		else
//			parameters.put("EMPLOYMENT_SCHOOL_REGION_FILTER", "Όλες οι Περιοχές");
//		parameters.put("EMPLOYMENT_SCHOOL_YEAR_FILTER", employmentCriteria.getSchoolYear().getDescription());
//
//		/* create the leave type helper */
//		for (EmployeeType employeeType : getCoreSearching().getEmployeeTypes()) {
//			parameters.put(employeeType.name(), getLocalizedMessage(employeeType.getKey()));
//		}
//		return parameters;
//	}

//	public void generatePDFReport() throws Exception {
//		try {
//			Map<String, Object> parameters = constructReportParameters();
//			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
//			byte[] bytes = null;
//			try {
//				bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
//						"/reports/employmentByType.jasper"), parameters, (JRDataSource) ds);
//			} catch (Throwable t) {
//				System.err.println(t);
//			}
//			HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext()
//					.getResponse();
//			response.setContentType("application/pdf");
//			response.addHeader("Content-Disposition", "attachment;filename=EmployeeReportByType.pdf");
//			response.setContentLength(bytes.length);
//			ServletOutputStream servletOutputStream = response.getOutputStream();
//			servletOutputStream.write(bytes, 0, bytes.length);
//			servletOutputStream.flush();
//			servletOutputStream.close();
//			getFacesContext().responseComplete();
//		} catch (Exception ex) {
//			error("report generation has failed due to exception #0", ex, ex.getMessage());
//			getFacesMessages().add(Severity.ERROR, "Η παραγωγή του report απέτυχε, λόγω σφάλματος #0", ex.getMessage());
//		}
//	}
//
//	public void generateExcelReport() throws Exception {
//		OutputStream output = null;
//		try {
//			Map<String, Object> parameters = constructReportParameters();
//			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
//
//			JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getResourceAsStream(
//					"/reports/employmentByType.jasper"), parameters, ds);
//			ByteArrayOutputStream bout = new ByteArrayOutputStream();
//			output = new BufferedOutputStream(bout);
//			JRXlsExporter exporter = new JRXlsExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
//			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//			exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, new Integer(1000));
//			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//			exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
//			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//			exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
//			
//
//			try {
//				exporter.exportReport();
//				output.flush();
//			} catch (Throwable t) {
//				System.err.println(t);
//			}
//			byte[] bytes = bout.toByteArray();
//			HttpServletResponse response = (HttpServletResponse)getFacesContext().getExternalContext()
//					.getResponse();
//			//response.setContentType("application/pdf");
//			response.addHeader("Content-Disposition", "attachment;filename=EmployeeReportByType.xls");
//			response.setContentLength(bytes.length);
//			ServletOutputStream servletOutputStream = response.getOutputStream();
//			servletOutputStream.write(bytes, 0, bytes.length);
//			servletOutputStream.flush();
//			servletOutputStream.close();
//			getFacesContext().responseComplete();
//		} catch (Exception ex) {
//			error("report generation has failed due to exception #0", ex, ex.getMessage());
//			getFacesMessages().add(Severity.ERROR, "Η παραγωγή του report απέτυχε, λόγω σφάλματος #0", ex.getMessage());
//		} finally {
//			try {
//				if (output != null)
//					output.close();
//			} catch (Exception ex) {
//
//			}
//		}
//
//	}

	/**
	 * 
	 */
	public void generateReport() {
	    long started = System.currentTimeMillis();
        info("generating employee teaching hours report");
		EmploymentType employeeTye = getEmployeeTeachingHoursReportCriteria().getType();
		SpecializationSearchType specializationSearchType = getEmployeeTeachingHoursReportCriteria().getSpecializationSearchType();
		SpecializationGroup specializationGroup = getEmployeeTeachingHoursReportCriteria().getSpecializationGroup();
		Specialization specialization = getEmployeeTeachingHoursReportCriteria().getSpecialization();
		SchoolYear schoolYear = getEmployeeTeachingHoursReportCriteria().getSchoolYear();
		Character region = getEmployeeTeachingHoursReportCriteria().getRegion();
		SchoolType schoolType = getEmployeeTeachingHoursReportCriteria().getSchoolType();

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT cdr FROM TeachingHourCDR cdr JOIN FETCH cdr.employee WHERE cdr.employee.active IS TRUE AND cdr.schoolYear=:schoolYear ");

//		if (employeeTye != null) {
//			sb.append(" AND em.type=:employmentType ");
//		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND cdr.employee.lastSpecialization MEMBER OF g.specializations) ");
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			sb.append(" AND cdr.employee.lastSpecialization = :specialization ");
		}

//		if (region != null) {
//			sb.append(" AND em.school.regionCode = :region ");
//		}
//
//		if (schoolType != null) {
//			sb.append(" AND em.school.type = :schoolType ");
//		}

		if (String.valueOf(getEmployeeTeachingHoursReportCriteria().getSorting()).equals("specialization"))
			sb.append(" ORDER BY cdr.employee.lastSpecialization.id, cdr.employee.lastName, cdr.employee.firstName, cdr.id");
		else if (String.valueOf(getEmployeeTeachingHoursReportCriteria().getSorting()).equals("surname"))
			sb.append(" ORDER BY cdr.employee.lastName, cdr.employee.firstName, cdr.id");
		else
			sb.append(" ORDER BY cdr.employee.lastName, cdr.employee.firstName, cdr.id");
		Query q = getEntityManager().createQuery(sb.toString());
		q.setParameter("schoolYear", schoolYear);

//		if (employeeTye != null) {
//			q.setParameter("employmentType", employeeTye);
//		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION_GROUP && specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}

		if (specializationSearchType == SpecializationSearchType.SPECIALIZATION && specialization != null) {
			q.setParameter("specialization", specialization);
		}

//		if (region != null) {
//			q.setParameter("region", region);
//		}
//
//		if (schoolType != null) {
//			q.setParameter("schoolType", schoolType);
//		}

		Collection<TeachingHourCDR> cdrs = q.getResultList();
		//info("found totally #0 cdr(s) matching criteria", cdrs.size());
		Map<Integer, EmployeeTeachingHoursReportItem> employee = new LinkedHashMap<Integer, EmployeeTeachingHoursReportItem>(cdrs.size()/2);
		reportData = new ArrayList<EmployeeTeachingHoursReportItem>(cdrs.size());
		for (TeachingHourCDR cdr : cdrs) {
			//reportData.add(new EmployeeTeachingHoursReportItem(employment.getEmployee()));
		    EmployeeTeachingHoursReportItem item = employee.get(cdr.getEmployee().getId());
		    if(item==null) {
		        item = new EmployeeTeachingHoursReportItem(cdr.getEmployee());
		        employee.put(cdr.getEmployee().getId(), item);
		    }
		    item.updateWith(cdr);
		    //info("employee #0 -> type #1 for hours #2 on unit #3 due to #4", cdr.getEmployee(), cdr.getCdrType(), cdr.getHours(), cdr.getUnit(), cdr.getComment());
		}
		reportData.addAll(employee.values());
		long finished = System.currentTimeMillis();
        
        info("generated employee teaching hours report #0 [ms]", (finished-started));
	}




    /**
     * @return the employeeTeachingHoursReportCriteria
     */
    public EmployeeTeachingHoursReportCriteria getEmployeeTeachingHoursReportCriteria() {
        return employeeTeachingHoursReportCriteria;
    }


    /**
     * @param employeeTeachingHoursReportCriteria the employeeTeachingHoursReportCriteria to set
     */
    public void setEmployeeTeachingHoursReportCriteria(
            EmployeeTeachingHoursReportCriteria employeeTeachingHoursReportCriteria) {
        this.employeeTeachingHoursReportCriteria = employeeTeachingHoursReportCriteria;
    }


    /**
     * @return the reportData
     */
    public List<EmployeeTeachingHoursReportItem> getReportData() {
        return reportData;
    }


    /**
     * @param reportData the reportData to set
     */
    public void setReportData(List<EmployeeTeachingHoursReportItem> reportData) {
        this.reportData = reportData;
    }

}
