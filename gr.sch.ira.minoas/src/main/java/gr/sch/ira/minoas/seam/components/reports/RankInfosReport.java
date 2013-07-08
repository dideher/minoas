package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.criteria.RankInfoCriteria;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.security.Identity;

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
	private Collection<Audit> rankInfosReportData;

	@SuppressWarnings("unchecked")
    @Observer("org.jboss.seam.security.loginSuccessful")
	@Transactional(TransactionPropagationType.REQUIRED)
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
			sb.append(" AND r.lastRankDate <= :effectiveRankDateUntil");
		}
		
		if (effectiveSalaryGradeDateFrom != null) {
			sb.append(" AND r.lastSalaryGradeDate >= :effectiveSalaryGradeDateFrom");
		}
		
		if (effectiveSalaryGradeDateUntil != null) {
			sb.append(" AND r.lastSalaryGradeDate <= :effectiveSalaryGradeDateUntil");
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

		setRankInfosReportData(q.getResultList());
		info("found totally #0 rankInfo(s).", getRankInfosReportData().size());

	}

	public RankInfoCriteria getRankInfoCriteria() {
		return rankInfoCriteria;
	}

	public void setRankInfoCriteria(RankInfoCriteria rankInfoCriteria) {
		this.rankInfoCriteria = rankInfoCriteria;
	}

	public Collection<Audit> getRankInfosReportData() {
		return rankInfosReportData;
	}

	public void setRankInfosReportData(Collection<Audit> rankInfosReportData) {
		this.rankInfosReportData = rankInfosReportData;
	}

	



}
