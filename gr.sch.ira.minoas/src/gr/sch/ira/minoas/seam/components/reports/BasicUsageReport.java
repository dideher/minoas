package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.security.Principal;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.dialect.IngresDialect;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.mail.ui.context.MailFacesContextImpl;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("basicUsageReport")
@Scope(ScopeType.SESSION)
public class BasicUsageReport extends BaseReport {

	public class AuditWinnersReportData {
		private Collection<Object[]> rawData;

		private Collection<Long> auditWinnerCounts;

		private Collection<Principal> auditWinnerPrincipals;

		private Collection<Long> employeeTypeCounts;

		private Collection<EmployeeType> employeeTypes;

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

		public String getAuditWinnerNamesForGoogleChart() {
			StringBuffer sb = new StringBuffer();
			for (Iterator<Principal> it = getAuditWinningReport().getAuditWinnerPrincipals().iterator(); it.hasNext();) {
				Principal p = it.next();
				sb.append(p.getUsername());
				if (it.hasNext())
					sb.append("|");
			}
			return sb.toString();
		}

		public String getAuditWinnerCountsForGoogleChart() {
			StringBuffer sb = new StringBuffer();
			// compute the sum 
			long sum = 0;
			for(Long v : getAuditWinningReport().getAuditWinnerCounts()) {
				sum += v.longValue();
			}
			for (Iterator<Long> it = getAuditWinningReport().getAuditWinnerCounts().iterator(); it.hasNext();) {
				Long count = it.next();
				sb.append(new Float(((float)count / (float)sum) * 100));
				if (it.hasNext())
					sb.append(",");
			}
			return sb.toString();
		}

		public String getAuditWinnersGoogleChartURL() {
			String baseURL = "http://chart.apis.google.com/chart?cht=p3&chs=420x150&chd=t:{0}&chl={1}";
			String options[] = { getAuditWinnerCountsForGoogleChart(), getAuditWinnerNamesForGoogleChart() };
			MessageFormat mf = new MessageFormat(baseURL);
			String url = mf.format(options);
			return url;
		}

		public String getEmployeeReportGoogleChartURL() throws MalformedURLException  {
			ArrayList<Long> helperCount = new ArrayList<Long>(getEmployeeTypeCounts());
			StringBuffer emloyeesCount = new StringBuffer();
			long sum = 0;
			for(Long v : helperCount) {
				sum += v.longValue();
			}
			for(Iterator<Long> it = helperCount.iterator() ; it.hasNext() ; ) {
				Long count = it.next();
				emloyeesCount.append(new Float(((float)count / (float)sum) * 100));
				if (it.hasNext())
					emloyeesCount.append(",");
			}
			
			StringBuffer emloyeesTypes = new StringBuffer();
			int count = 0;
			for(Iterator<EmployeeType> it = getEmployeeTypes().iterator() ; it.hasNext() ; ) {
				emloyeesTypes.append(it.next());
				emloyeesTypes.append(" (");
				emloyeesTypes.append(helperCount.get(count));
				emloyeesTypes.append(")");
				if (it.hasNext())
					emloyeesTypes.append("|");
				count++;
			}
			
			String baseURL = "http://chart.apis.google.com/chart?cht=p3&chs=420x150&chd=t:{0}&chl={1}";
			String options[] = { emloyeesCount.toString(),  emloyeesTypes.toString()};
			MessageFormat mf = new MessageFormat(baseURL);
			String url = mf.format(options);
			return new URL(url).toExternalForm();
		}

		/**
		 * @return the auditWinnerPrincipals
		 */
		public Collection<Principal> getAuditWinnerPrincipals() {
			return auditWinnerPrincipals;
		}

		/**
		 * @param auditWinnerPrincipals the auditWinnerPrincipals to set
		 */
		public void setAuditWinnerPrincipals(Collection<Principal> auditWinnerPrincipals) {
			this.auditWinnerPrincipals = auditWinnerPrincipals;
		}

		/**
		 * @return the auditWinnerCounts
		 */
		public Collection<Long> getAuditWinnerCounts() {
			return auditWinnerCounts;
		}

		/**
		 * @param auditWinnerCounts the auditWinnerCounts to set
		 */
		public void setAuditWinnerCounts(Collection<Long> auditWinnerCounts) {
			this.auditWinnerCounts = auditWinnerCounts;
		}

		/**
		 * @return the employeeTypeCounts
		 */
		public Collection<Long> getEmployeeTypeCounts() {
			return employeeTypeCounts;
		}

		/**
		 * @param employeeTypeCounts the employeeTypeCounts to set
		 */
		public void setEmployeeTypeCounts(Collection<Long> employeeTypeCounts) {
			this.employeeTypeCounts = employeeTypeCounts;
		}

		/**
		 * @return the employeeTypes
		 */
		public Collection<EmployeeType> getEmployeeTypes() {
			return employeeTypes;
		}

		/**
		 * @param employeeTypes the employeeTypes to set
		 */
		public void setEmployeeTypes(Collection<EmployeeType> employeeTypes) {
			this.employeeTypes = employeeTypes;
		}

	}

	@Out(required = false)
	private AuditWinnersReportData auditWinningReport;

	@Observer("org.jboss.seam.security.loginSuccessful")
	@Transactional(TransactionPropagationType.REQUIRED)
	public void generateReport() {
		info("generating basic usage report");
		Date fromDate = DateUtils.addDays(new Date(), -30);
		Collection<Object[]> rawData = getEntityManager()
				.createQuery(
						"SELECT (SELECT p FROM Principal p WHERE p.id=a.insertedBy.id), COUNT(a.insertedBy) FROM Audit a  GROUP BY (a.insertedBy) ORDER BY COUNT(a.insertedBy) DESC")
				.getResultList();
		AuditWinnersReportData reportData = new AuditWinnersReportData();
		Collection<Long> counts = new ArrayList<Long>(rawData.size());
		Collection<Principal> principals = new ArrayList<Principal>(rawData.size());
		int max_rows = 3;
		int count = 1;
		for (Object[] item : rawData) {
			principals.add((Principal) item[0]);
			counts.add((Long) (item[1]));
			if (count % max_rows == 0)
				break;
			count++;
		}
		reportData.setRawData(rawData);
		reportData.setAuditWinnerPrincipals(principals);
		reportData.setAuditWinnerCounts(counts);

		Collection<Object[]> employeeReportData = getEntityManager().createQuery(
				"SELECT (e.type), COUNT(e.type) FROM Employee e GROUP BY (e.type) ORDER BY COUNT(e.type) DESC WHERE e.active IS TRUE")
				.getResultList();

		Collection<Long> employeeCounts = new ArrayList<Long>(employeeReportData.size());
		Collection<EmployeeType> employeeTypes = new ArrayList<EmployeeType>(employeeReportData.size());

		for (Object[] item : employeeReportData) {
			employeeTypes.add((EmployeeType) item[0]);
			employeeCounts.add((Long) (item[1]));
		}
		reportData.setEmployeeTypeCounts(employeeCounts);
		reportData.setEmployeeTypes(employeeTypes);
		setAuditWinningReport(reportData);
		info("basic usage report has been generated.");
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
