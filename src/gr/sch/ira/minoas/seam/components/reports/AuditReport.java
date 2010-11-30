package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.criteria.AuditCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.Unwrap;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.security.Identity;

@Name("auditReport")
@Scope(ScopeType.CONVERSATION)
public class AuditReport extends BaseReport {

	@In
	protected Identity identity;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true, required = true)
	private AuditCriteria auditCriteria;

	@DataModel(value = "auditReportData")
	private Collection<Audit> auditReportData;

	protected Principal getPrincipal(String principalName) {
		try {
			return (Principal) getEntityManager().createQuery("SELECT p FROM Principal p WHERE p.username = :username")
					.setParameter("username", principalName).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}
	}

	@Observer("org.jboss.seam.security.loginSuccessful")
	@Transactional(TransactionPropagationType.REQUIRED)
	public void generateReport() {
		Principal principal = getAuditCriteria().getPrincipal();

		if (!identity.hasRole("ADMIN")) {
			principal = getPrincipal(identity.getCredentials().getUsername());
		}
		AuditType type = getAuditCriteria().getAuditType();
		Date effectiveDateFrom = getAuditCriteria().getEffectiveDateFrom();
		Date effectiveDateUntil = getAuditCriteria().getEffectiveDateUntil();

		info("generating audit report of type #0 for principal #1 from #1 until #2", type, principal,
				effectiveDateFrom, effectiveDateUntil);

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a FROM Audit a WHERE ");
		sb.append(" a.insertedOn >= :effectiveDateFrom ");

		if (effectiveDateUntil != null) {
			sb.append(" AND a.insertedOn <= :effectiveDateUntil");
		}

		if (principal != null) {
			sb.append(" AND a.insertedBy = :principal");
		}

		if (type != null) {
			sb.append(" AND a.type = :type");
		}
		sb.append(" ORDER BY a.insertedOn DESC");

		Query q = getEntityManager().createQuery(sb.toString());

		q.setParameter("effectiveDateFrom", effectiveDateFrom);

		if (effectiveDateUntil != null) {
			q.setParameter("effectiveDateUntil", effectiveDateUntil);
		}

		if (principal != null) {
			q.setParameter("principal", principal);
		}

		if (type != null) {
			q.setParameter("type", type);
		}
		setAuditReportData(q.getResultList());
		info("found totally #0 audit event(s).", getAuditReportData().size());

	}

	/**
	 * @return the auditCriteria
	 */
	public AuditCriteria getAuditCriteria() {
		return auditCriteria;
	}

	/**
	 * @param auditCriteria the auditCriteria to set
	 */
	public void setAuditCriteria(AuditCriteria auditCriteria) {
		this.auditCriteria = auditCriteria;
	}

	/**
	 * @return the auditReportData
	 */
	public Collection<Audit> getAuditReportData() {
		return auditReportData;
	}

	/**
	 * @param auditReportData the auditReportData to set
	 */
	public void setAuditReportData(Collection<Audit> auditReportData) {
		this.auditReportData = auditReportData;
	}



}
