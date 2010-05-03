package gr.sch.ira.minoas.seam.components.reports;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("basicUsageReport")
@Scope(ScopeType.SESSION)
public class BasicUsageReport extends BaseReport {

	public class AuditWinnersReportData {
		private Collection<Object[]> rawData;

		private Collection<Integer> auditWinnerCounts;

		private Collection<String> auditWinnerNames;

		/**
		 * @return the rawData
		 */
		public Collection<Object[]> getRawData() {
			return rawData;
		}

		/**
		 * @param rawData the rawData to set
		 */
		public void setRawData(Collection<Object[]> rawData) {
			this.rawData = rawData;
		}

		/**
		 * @return the auditWinnerCounts
		 */
		public Collection<Integer> getAuditWinnerCounts() {
			return auditWinnerCounts;
		}

		/**
		 * @param auditWinnerCounts the auditWinnerCounts to set
		 */
		public void setAuditWinnerCounts(Collection<Integer> auditWinnerCounts) {
			this.auditWinnerCounts = auditWinnerCounts;
		}

		/**
		 * @return the auditWinnerNames
		 */
		public Collection<String> getAuditWinnerNames() {
			return auditWinnerNames;
		}

		/**
		 * @param auditWinnerNames the auditWinnerNames to set
		 */
		public void setAuditWinnerNames(Collection<String> auditWinnerNames) {
			this.auditWinnerNames = auditWinnerNames;
		}

		public String getAuditWinnerNamesForGoogleChart() {
			return "Lala|louou";
		}

		public String getAuditWinnerCountsForGoogleChart() {
			return "1|2";
		}
		
		public String getGoogleChartURL() {
			String baseURL = "http://chart.apis.google.com/chart?cht=p3&chs=250x100&chd=t:60,40&chl=Hello|World";
			return "http://chart.apis.google.com/chart?cht=p3&chs=250x100&chd=t:60,40&chl=Hello|World";
		}
	}

	@Out(required = false)
	private AuditWinnersReportData auditWinningReport;

	@Observer("org.jboss.seam.security.loginSuccessful")
	@Transactional(TransactionPropagationType.REQUIRED)
	public void generateWinningReport() {
		info("generate audit winning report");
		Collection<Object[]> rawData = getEntityManager()
				.createQuery(
						"SELECT (SELECT p FROM Principal p WHERE p.id=a.insertedBy.id), COUNT(a.insertedBy) FROM Audit a GROUP BY (a.insertedBy) ORDER BY COUNT(a.insertedBy) DESC")
				.getResultList();
		AuditWinnersReportData reportData = new AuditWinnersReportData();
		Collection<Integer> counts = new ArrayList<Integer>(rawData.size());
		Collection<String> names = new ArrayList<String>(rawData.size());
		for (Object[] item : rawData) {
			names.add(String.valueOf(item[0]));
			counts.add(Integer.valueOf(String.valueOf(item[1])));
		}
		reportData.setRawData(rawData);
		reportData.setAuditWinnerNames(names);
		reportData.setAuditWinnerCounts(counts);
		setAuditWinningReport(reportData);
		info("winning audit report has been generated.");
	}

	/**
	 * @return the auditWinningReport
	 */
	public AuditWinnersReportData getAuditWinningReport() {
		return auditWinningReport;
	}

	/**
	 * @param auditWinningReport the auditWinningReport to set
	 */
	public void setAuditWinningReport(AuditWinnersReportData auditWinningReport) {
		this.auditWinningReport = auditWinningReport;
	}

}
