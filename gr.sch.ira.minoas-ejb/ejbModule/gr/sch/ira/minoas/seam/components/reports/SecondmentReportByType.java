package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.SecondmentCriteria;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
@Name("secondmentReportByType")
@Scope(ScopeType.CONVERSATION)
public class SecondmentReportByType extends BaseReport {

	@In(required = true)
	private SecondmentCriteria secondmentCriteria;

	@DataModel(value = "reportData")
	private Collection<SecondmentItem> reportData = null;

	/**
	 * 
	 */
	public SecondmentReportByType() {
	}

	public void generateReport() throws Exception {
		Date effectiveDate = getSecondmentCriteria().getEffectiveDate();
		Date effectiveDateFrom = getSecondmentCriteria().getEffectiveDateFrom();
		Date effectiveDateUntil = getSecondmentCriteria().getEffectiveDateUntil();
		SecondmentType secondmentType = getSecondmentCriteria().getSecondmentType();
		Character region = getSecondmentCriteria().getRegion();
		Unit targetUnit = getSecondmentCriteria().getTargetUnit();
		SpecializationGroup specializationGroup = getSecondmentCriteria()
		.getSpecializationGroup();
		Boolean employeeRequested = getSecondmentCriteria().getEmployeeRequested();
		DateSearchType dateSearchType = getSecondmentCriteria().getDateSearchType();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT s FROM Secondment s INNER JOIN FETCH s.employee WHERE s.active IS TRUE ");
		if(employeeRequested!=null) {
			sb.append("AND s.employeeRequested = :employeeRequested");
		}
		switch (dateSearchType) {
		case AFTER_DATE:
			sb.append("AND s.established >= :effectiveDate ");
			break;
		case BEFORE_DATE:
			sb.append("AND s.dueTo <= :effectiveDate ");
			break;
		case DURING_DATE:
			sb.append(" AND (:effectiveDate BETWEEN s.established AND s.dueTo) ");
			break;
		case DURING_DATE_PERIOD:
			sb.append(" AND (:effectiveDateFrom <= s.established AND  :effectiveDateUntil >= s.dueTo) ");
			break;
		}
		if (secondmentType != null) {
			sb.append(" AND s.secondmentType=:secondmentType ");
		}
		if(targetUnit!=null) {
			sb.append(" AND s.targetUnit=:targetUnit ");
		}
		if(region!=null) {
			sb.append(" AND s.employee.currentEmployment.school.regionCode=:region ");
		}
		if (specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND s.employee.lastSpecialization MEMBER OF g.specializations) ");
		}
		sb.append(" ORDER BY s.employee.lastName");

		Query q = getEntityManager().createQuery(sb.toString());
		if (dateSearchType != DateSearchType.DURING_DATE_PERIOD) {
			q.setParameter("effectiveDate", effectiveDate);

		} else {
			q.setParameter("effectiveDateFrom", effectiveDateFrom);
			q.setParameter("effectiveDateUntil", effectiveDateUntil);
		}
		if (secondmentType != null) {
			q.setParameter("secondmentType", secondmentType);
		}
		if(targetUnit!=null) {
			q.setParameter("targetUnit", targetUnit);
		}
		if(region!=null) {
			q.setParameter("region", region);
		}
		if (specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}
		if(employeeRequested!=null) {
		q.setParameter("employeeRequested", employeeRequested);
		}
		Collection<Secondment> secondments = q.getResultList();
		info("found totally #0 secondments matching criteria", secondments.size());
		reportData = new ArrayList<SecondmentItem>(secondments.size());
		for (Secondment secondment : secondments) {
			reportData.add(new SecondmentItem(secondment));
		}
	}

	public void generatePDFReport() throws Exception {
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("SECONDMENT_TYPE_FILTER",
					secondmentCriteria.getSecondmentType() != null ? getLocalizedMessage(secondmentCriteria
							.getSecondmentType().getKey()) : "Όλοι οι Τύποι");
			/* create the secondment type helper */
			for (SecondmentType secondmentType : getCoreSearching().getAvailableSecondmentTypes()) {
				parameters.put(secondmentType.name(), getLocalizedMessage(secondmentType.getKey()));
			}
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
			byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
					"/reports/secondmentByType.jasper"), parameters, (JRDataSource) ds);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=SecondmentReportByType.pdf");
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
	 * @return the secondmentCriteria
	 */
	public SecondmentCriteria getSecondmentCriteria() {
		return secondmentCriteria;
	}

	/**
	 * @param secondmentCriteria the secondmentCriteria to set
	 */
	public void setSecondmentCriteria(SecondmentCriteria secondmentCriteria) {
		this.secondmentCriteria = secondmentCriteria;
	}

	/**
	 * @return the reportData
	 */
	public Collection<SecondmentItem> getReportData() {
		return reportData;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(Collection<SecondmentItem> reportData) {
		this.reportData = reportData;
	}

}
