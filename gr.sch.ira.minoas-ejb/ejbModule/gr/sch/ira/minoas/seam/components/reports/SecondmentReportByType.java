
package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employement.Secondment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentReportByType")
public class SecondmentReportByType {

	@In(required=true, value="secondmentsByTypeListQuery")
	private EntityQuery<Secondment> secondmentsByTypeListQuery;
	
	public class ReportRow {
		public ReportRow() {
			super();
		}
		
		public ReportRow(Secondment secondment) {
			super();
			this.employeeName = secondment.getEmployee().getFirstName();
			this.employeeSurname = secondment.getEmployee().getLastName();
			this.employeeFather = secondment.getEmployee().getFatherName();
			if(secondment.getEmployee().getRegularDetail()!=null)
				this.employeeCode = secondment.getEmployee().getRegularDetail().getRegistryID();
			this.specialization = secondment.getEmployee().getLastSpecialization().getTitle();
			this.comment = secondment.getComment();
			this.dueTo = secondment.getDueTo();
			this.establishedIn = secondment.getEstablished();
			this.requestByEmployee = secondment.getEmployeeRequested();
			this.sourceUnit = secondment.getSourceUnit().getTitle();
			this.targetUnit = secondment.getTargetUnit().getTitle();
			this.specializationID = secondment.getEmployee().getLastSpecialization().getId();
			this.employeeName = secondment.getEmployee().getFirstName();
		}
		
		private String specialization;
		private String specializationID;
		private String employeeName;
		private String employeeSurname;
		private String employeeFather;
		private String employeeCode;
		private String comment;
		private boolean requestByEmployee;
		private Date dueTo;
		private Date establishedIn;
		private String sourceUnit;
		private String targetUnit;
		/**
		 * @return the specialization
		 */
		public String getSpecialization() {
			return specialization;
		}
		/**
		 * @param specialization the specialization to set
		 */
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		/**
		 * @return the specializationID
		 */
		public String getSpecializationID() {
			return specializationID;
		}
		/**
		 * @param specializationID the specializationID to set
		 */
		public void setSpecializationID(String specializationID) {
			this.specializationID = specializationID;
		}
		/**
		 * @return the employeeName
		 */
		public String getEmployeeName() {
			return employeeName;
		}
		/**
		 * @param employeeName the employeeName to set
		 */
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		/**
		 * @return the employeeSurname
		 */
		public String getEmployeeSurname() {
			return employeeSurname;
		}
		/**
		 * @param employeeSurname the employeeSurname to set
		 */
		public void setEmployeeSurname(String employeeSurname) {
			this.employeeSurname = employeeSurname;
		}
		/**
		 * @return the employeeFather
		 */
		public String getEmployeeFather() {
			return employeeFather;
		}
		/**
		 * @param employeeFather the employeeFather to set
		 */
		public void setEmployeeFather(String employeeFather) {
			this.employeeFather = employeeFather;
		}
		/**
		 * @return the employeeCode
		 */
		public String getEmployeeCode() {
			return employeeCode;
		}
		/**
		 * @param employeeCode the employeeCode to set
		 */
		public void setEmployeeCode(String employeeCode) {
			this.employeeCode = employeeCode;
		}
		/**
		 * @return the comment
		 */
		public String getComment() {
			return comment;
		}
		/**
		 * @param comment the comment to set
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}
		/**
		 * @return the requestByEmployee
		 */
		public boolean isRequestByEmployee() {
			return requestByEmployee;
		}
		/**
		 * @param requestByEmployee the requestByEmployee to set
		 */
		public void setRequestByEmployee(boolean requestByEmployee) {
			this.requestByEmployee = requestByEmployee;
		}
		/**
		 * @return the dueTo
		 */
		public Date getDueTo() {
			return dueTo;
		}
		/**
		 * @param dueTo the dueTo to set
		 */
		public void setDueTo(Date dueTo) {
			this.dueTo = dueTo;
		}
		/**
		 * @return the establishedIn
		 */
		public Date getEstablishedIn() {
			return establishedIn;
		}
		/**
		 * @param establishedIn the establishedIn to set
		 */
		public void setEstablishedIn(Date establishedIn) {
			this.establishedIn = establishedIn;
		}
		/**
		 * @return the sourceUnit
		 */
		public String getSourceUnit() {
			return sourceUnit;
		}
		/**
		 * @param sourceUnit the sourceUnit to set
		 */
		public void setSourceUnit(String sourceUnit) {
			this.sourceUnit = sourceUnit;
		}
		/**
		 * @return the targetUnit
		 */
		public String getTargetUnit() {
			return targetUnit;
		}
		/**
		 * @param targetUnit the targetUnit to set
		 */
		public void setTargetUnit(String targetUnit) {
			this.targetUnit = targetUnit;
		}
		
	}
	/**
	 * 
	 */
	public SecondmentReportByType() {
	}

	public void runReport() throws Exception {
		try {
		Map<String, String> parameters = new HashMap<String, String>();
		List<ReportRow> rows = new ArrayList<ReportRow>(100);
		for(Secondment secondment : secondmentsByTypeListQuery.getResultList()) {
			rows.add(new ReportRow(secondment));
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rows);
		byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream("/reports/secondmentByType.jasper"), parameters,(JRDataSource)ds);
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition","attachment;filename=SecondmentReportByType.pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		servletOutputStream.write(bytes, 0, bytes.length);
		servletOutputStream.flush();
		servletOutputStream.close();
		FacesContext.getCurrentInstance().responseComplete();
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
