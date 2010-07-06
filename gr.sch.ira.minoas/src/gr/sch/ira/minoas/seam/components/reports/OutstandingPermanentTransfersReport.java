package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.OutstandingImprovementCriteria;
import gr.sch.ira.minoas.seam.components.criteria.OutstandingPermanentTransferCriteria;
import gr.sch.ira.minoas.seam.components.criteria.SecondmentCriteria;
import gr.sch.ira.minoas.seam.components.reports.resource.OutstandingImprovementItem;
import gr.sch.ira.minoas.seam.components.reports.resource.OutstandingPermanentTransferItem;
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
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("outstandingPermanentTransfersReport")
@Scope(ScopeType.CONVERSATION)
public class OutstandingPermanentTransfersReport extends BaseReport {

	@DataModel(value = "reportData")
	private Collection<OutstandingPermanentTransferItem> reportData = null;

	@In(required = true, create=true)
	private  OutstandingPermanentTransferCriteria outstandingPermanentTransferCriteria;

	/**
	 * 
	 */
	public OutstandingPermanentTransfersReport() {
	}

	public void generateReport() throws Exception {

		Unit sourceUnit = getOutstandingPermanentTransferCriteria().getSourceUnit();
		Unit targetUnit = getOutstandingPermanentTransferCriteria().getTargetUnit();
		PermanentTransferType transferType = getOutstandingPermanentTransferCriteria().getTransferType();
		SpecializationGroup specializationGroup = getOutstandingPermanentTransferCriteria().getSpecializationGroup();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT i FROM PermanentTransfer i WHERE i.isProcessed IS FALSE ");
		
		if (specializationGroup != null) {
			sb
					.append(" AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specializationGroup AND i.employee.lastSpecialization MEMBER OF g.specializations) ");
		}
		
		if(sourceUnit!=null) {
			sb.append(" AND i.sourceUnit = :sourceUnit");
		}
		
		if(targetUnit!=null) {
			sb.append(" AND i.targetUnit = :targetUnit");
		}
		
		if(transferType!=null) {
			sb.append(" AND i.type = :transferType");
		}
		sb.append(" ORDER BY i.employee.lastName");

		Query q = getEntityManager().createQuery(sb.toString());
		if (specializationGroup != null) {
			q.setParameter("specializationGroup", specializationGroup);
		}
		if(sourceUnit!=null) {
			q.setParameter("sourceUnit", sourceUnit);
		}
		
		if(targetUnit!=null) {
			q.setParameter("targetUnit", targetUnit);
		}
		
		if(transferType!=null) {
			q.setParameter("transferType", transferType);
		}
		Collection<PermanentTransfer> permanentTransfers = q.getResultList();
		info("found totally #0 outstanding permanent transfer(s) matching criteria", permanentTransfers.size());
		reportData = new ArrayList<OutstandingPermanentTransferItem>(permanentTransfers.size());
		for (PermanentTransfer permanentTransfer : permanentTransfers) {
			reportData.add(new OutstandingPermanentTransferItem(permanentTransfer));
		}
	}

	
	/**
	 * @return the reportData
	 */
	public Collection<OutstandingPermanentTransferItem> getReportData() {
		return reportData;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(Collection<OutstandingPermanentTransferItem> reportData) {
		this.reportData = reportData;
	}

	/**
	 * @return the outstandingPermanentTransferCriteria
	 */
	public OutstandingPermanentTransferCriteria getOutstandingPermanentTransferCriteria() {
		return outstandingPermanentTransferCriteria;
	}

	/**
	 * @param outstandingPermanentTransferCriteria the outstandingPermanentTransferCriteria to set
	 */
	public void setOutstandingPermanentTransferCriteria(
			OutstandingPermanentTransferCriteria outstandingPermanentTransferCriteria) {
		this.outstandingPermanentTransferCriteria = outstandingPermanentTransferCriteria;
	}

	

}
