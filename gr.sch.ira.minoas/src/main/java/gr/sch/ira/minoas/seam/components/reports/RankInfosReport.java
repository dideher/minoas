package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RankType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.printout.PrintoutRecipients;
import gr.sch.ira.minoas.model.printout.PrintoutSignatures;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.management.EmployeeLeavesManagement.PrintingHelper;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.RankInfoReportItem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.security.Identity;

import com.thoughtworks.xstream.persistence.FileStreamStrategy;

@Name("rankInfosReport")
@Scope(ScopeType.CONVERSATION)
public class RankInfosReport extends BaseReport {

    @In
	protected Identity identity;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true, required = true)
	private RankInfoCriteria rankInfoCriteria;

	@DataModel(value = "rankInfosReportData")
	private List<RankInfoReportItem> rankInfosReportData;

	
	
    private List<PrintoutRecipients> rankInfoPrintounRecipientList = new ArrayList<PrintoutRecipients>();

    private List<PrintoutRecipients> rankInfoPrintounRecipientListSource = new ArrayList<PrintoutRecipients>();

    private PrintoutSignatures rankInfoPrintoutSignature;

    private Collection<PrintoutSignatures> rankInfoPrintoutSignatureSource = new ArrayList<PrintoutSignatures>();
	
	
	
	
//	@SuppressWarnings("unchecked")
//    @Observer("org.jboss.seam.security.loginSuccessful")
//	@Transactional(TransactionPropagationType.REQUIRED)
	public void generateReport() {

		String registryId = getRankInfoCriteria().getRegistryId();
		String lastName = getRankInfoCriteria().getLastName();
		Date effectiveRankDateFrom = getRankInfoCriteria().getEffectiveRankDateFrom();
		Date effectiveRankDateUntil = getRankInfoCriteria().getEffectiveRankDateUntil();
		Date effectiveSalaryGradeDateFrom = getRankInfoCriteria().getEffectiveSalaryGradeDateFrom();
		Date effectiveSalaryGradeDateUntil = getRankInfoCriteria().getEffectiveSalaryGradeDateUntil();
		
		
		
		
		info("generating rankInfos report from #1 until #2", effectiveRankDateFrom, effectiveRankDateUntil);

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r FROM RankInfo r WHERE 1=1");

		if (effectiveRankDateFrom != null) {
			sb.append(" AND r.lastRankDate >= :effectiveRankDateFrom");
		}
		
		if (effectiveRankDateUntil != null) {
			effectiveRankDateUntil = DateUtils.add(effectiveRankDateUntil, Calendar.DAY_OF_MONTH, 1);
			sb.append(" AND r.lastRankDate < :effectiveRankDateUntil");
		}
		
		if (effectiveSalaryGradeDateFrom != null) {
			sb.append(" AND r.lastSalaryGradeDate >= :effectiveSalaryGradeDateFrom");
		}
		
		if (effectiveSalaryGradeDateUntil != null) {
			effectiveSalaryGradeDateUntil= DateUtils.add(effectiveSalaryGradeDateUntil, Calendar.DAY_OF_MONTH, 1);
			sb.append(" AND r.lastSalaryGradeDate < :effectiveSalaryGradeDateUntil");
		}
		
		if (isNonEmpty(lastName)) {
            sb.append(" AND r.employeeInfo.employee.lastName LIKE LOWER(CONCAT('%', CONCAT(:lastName,'%')))");
        }
		
		if (isNonEmpty(registryId)) {
            sb.append(" AND r.employeeInfo.employee.regularEmployeeInfo.registryID LIKE CONCAT('%', CONCAT(:registryId,'%'))");
        }

		sb.append(" ORDER BY r.employeeInfo.employee.lastName, r.employeeInfo.employee.firstName, r.rank, r.salaryGrade ASC");

		Query q = getEntityManager().createQuery(sb.toString());

		
		if (effectiveRankDateFrom != null) {
			q.setParameter("effectiveRankDateFrom", effectiveRankDateFrom);
		}
		
		if (effectiveRankDateUntil != null) {
			q.setParameter("effectiveRankDateUntil", effectiveRankDateUntil);
		}
		
		if (effectiveSalaryGradeDateFrom != null) {
			q.setParameter("effectiveSalaryGradeDateFrom", effectiveSalaryGradeDateFrom);
		}
		
		if (effectiveSalaryGradeDateUntil != null) {
			q.setParameter("effectiveSalaryGradeDateUntil", effectiveSalaryGradeDateUntil);
		}
		
		if (isNonEmpty(lastName)) {
			q.setParameter("lastName", lastName);
		}
		
		if (isNonEmpty(registryId)) {
			q.setParameter("registryId", registryId);
		}

		@SuppressWarnings("unchecked")
		Collection<RankInfo> rankInfos = q.getResultList();
		info("found totally #0 rankInfo(s).", rankInfos.size());
		rankInfosReportData = new ArrayList<RankInfoReportItem>(rankInfos.size());
		for (RankInfo rInfo : rankInfos) {
			rankInfosReportData.add(new RankInfoReportItem(rInfo));
		}
	}
	
	public void generatePDFReport() throws Exception {
		try {
			Map<String, Object> parameters = constructReportParameters();
			if(rankInfosReportData == null || rankInfosReportData.size() == 0)
				throw new Exception("Πρέπει πρώτα να πραγματοποιήσετε κάποια αναζήτηση για να την εκτυπώσετε στην συνέχεια!");
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rankInfosReportData);
			byte[] bytes = null;
			try {
				InputStream fis = this.getClass().getResourceAsStream("/reports/rankInfobyDate.jasper");
				bytes = JasperRunManager.runReportToPdf(fis, parameters, (JRDataSource) ds);
			} catch (Throwable t) {
				System.err.println(t);
			}
			HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=RankInfoReportByDate.pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			getFacesContext().responseComplete();
		} catch (Exception ex) {
			error("report generation has failed due to exception #0", ex, ex.getMessage());
			getFacesMessages().add(Severity.ERROR, "Η παραγωγή του report απέτυχε, λόγω σφάλματος #0", ex.getMessage());
		}
	}
	
	
	protected Map<String, Object> constructReportParameters() {
		Map<String, Object> parameters = new HashMap<String, Object>();

        Principal currentPrincipal = getPrincipal();
        parameters.put("employeeForInformation", currentPrincipal.getRealName());
        if(currentPrincipal.getInformationTelephone()!=null) {
            parameters.put("employeeForInformationTelephone", currentPrincipal.getInformationTelephone().getNumber());
        } else 
            parameters.put("employeeForInformationTelephone", "");

        parameters.put("signatureTitle", normalizeStringForXML(rankInfoPrintoutSignature.getSignatureTitle()));
        parameters.put("signatureName", normalizeStringForXML(rankInfoPrintoutSignature.getSignatureName()));
        
        StringBuffer sb = new StringBuffer();
        sb.append("<b>");
        int counter = 1;
        for (PrintoutRecipients recipient : rankInfoPrintounRecipientList) {
            sb.append(String.format("%d %s<br />", counter++, recipient.getRecipientTitle()));
        }

        sb.append("</b>");
        parameters.put("notificationList", sb.toString());
        
        /* Add rank types into the parameters in order to display the appropriate rank in the report*/
		for (RankType rankType : getCoreSearching().getRankTypes()) {
			parameters.put(rankType.name(), getLocalizedMessage(rankType.getKey()));
		}
		return parameters;

	}

    protected String normalizeStringForXML(String value) {
        if(value!=null) {
            String returnValue = value.replace("&", "&amp;"); 
            returnValue = returnValue.replace("<", "&lt;");
            returnValue = returnValue.replace(">", "&gt;");
            return returnValue;
        } else 
            return EMPTY_STRING;
    }
    
    /* this method is called when the user clicks the "print leave" */
    public void prepeareForRankInfoReportPrint() {
        Collection<SelectItem> list = new ArrayList<SelectItem>();
        for (PrintoutRecipients r : getCoreSearching().getPrintoutRecipients(getEntityManager())) {
            list.add(new SelectItem(r, r.getRecipientTitle()));
        }

        this.rankInfoPrintounRecipientListSource = new ArrayList<PrintoutRecipients>(getCoreSearching()
                .getPrintoutRecipients(getEntityManager()));
        this.rankInfoPrintoutSignatureSource = getCoreSearching().getPrintoutSignatures(getEntityManager());
        
    }
	
	public RankInfoCriteria getRankInfoCriteria() {
		return rankInfoCriteria;
	}

	public void setRankInfoCriteria(RankInfoCriteria rankInfoCriteria) {
		this.rankInfoCriteria = rankInfoCriteria;
	}

	public List<RankInfoReportItem> getRankInfosReportData() {
		return rankInfosReportData;
	}

	public void setRankInfosReportData(List<RankInfoReportItem> rankInfosReportData) {
		this.rankInfosReportData = rankInfosReportData;
	}

	/**
	 * @return the identity
	 */
	public Identity getIdentity() {
		return identity;
	}

	/**
	 * @param identity the identity to set
	 */
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	/**
	 * @return the rankInfoPrintounRecipientList
	 */
	public List<PrintoutRecipients> getRankInfoPrintounRecipientList() {
		return rankInfoPrintounRecipientList;
	}

	/**
	 * @param rankInfoPrintounRecipientList the rankInfoPrintounRecipientList to set
	 */
	public void setRankInfoPrintounRecipientList(
			List<PrintoutRecipients> rankInfoPrintounRecipientList) {
		this.rankInfoPrintounRecipientList = rankInfoPrintounRecipientList;
	}

	/**
	 * @return the rankInfoPrintounRecipientListSource
	 */
	public List<PrintoutRecipients> getRankInfoPrintounRecipientListSource() {
		return rankInfoPrintounRecipientListSource;
	}

	/**
	 * @param rankInfoPrintounRecipientListSource the rankInfoPrintounRecipientListSource to set
	 */
	public void setRankInfoPrintounRecipientListSource(
			List<PrintoutRecipients> rankInfoPrintounRecipientListSource) {
		this.rankInfoPrintounRecipientListSource = rankInfoPrintounRecipientListSource;
	}

	/**
	 * @return the rankInfoPrintoutSignature
	 */
	public PrintoutSignatures getRankInfoPrintoutSignature() {
		return rankInfoPrintoutSignature;
	}

	/**
	 * @param rankInfoPrintoutSignature the rankInfoPrintoutSignature to set
	 */
	public void setRankInfoPrintoutSignature(
			PrintoutSignatures rankInfoPrintoutSignature) {
		this.rankInfoPrintoutSignature = rankInfoPrintoutSignature;
	}

	/**
	 * @return the rankInfoPrintoutSignatureSource
	 */
	public Collection<PrintoutSignatures> getRankInfoPrintoutSignatureSource() {
		return rankInfoPrintoutSignatureSource;
	}

	/**
	 * @param rankInfoPrintoutSignatureSource the rankInfoPrintoutSignatureSource to set
	 */
	public void setRankInfoPrintoutSignatureSource(
			Collection<PrintoutSignatures> rankInfoPrintoutSignatureSource) {
		this.rankInfoPrintoutSignatureSource = rankInfoPrintoutSignatureSource;
	}

	



}
