package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.security.Principal;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("auditCriteria")
@Scope(ScopeType.CONVERSATION)
public class AuditCriteria {

	private Date effectiveDateFrom;

	private Date effectiveDateUntil;
	
	private Principal principal;
	
	private AuditType auditType;
	
	

	/**
		 * 
		 */
	public AuditCriteria() {
		super();
		
		effectiveDateFrom = DateUtils.truncate(DateUtils.addDays(new Date(System.currentTimeMillis()), -1), Calendar.DAY_OF_MONTH);
		effectiveDateUntil = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the effectiveDateDueTo
	 */
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	/**
	 * @return the effectiveDateUntil
	 */
	public Date getEffectiveDateUntil() {
		return effectiveDateUntil;
	}

	/**
	 * @param effectiveDateDueTo the effectiveDateDueTo to set
	 */
	public void setEffectiveDateFrom(Date effectiveDateDueTo) {
		this.effectiveDateFrom = effectiveDateDueTo;
	}

	/**
	 * @param effectiveDateUntil the effectiveDateUntil to set
	 */
	public void setEffectiveDateUntil(Date effectiveDateUntil) {
		this.effectiveDateUntil = effectiveDateUntil;
	}

	/**
	 * @return the principal
	 */
	public Principal getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	/**
	 * @return the auditType
	 */
	public AuditType getAuditType() {
		return auditType;
	}

	/**
	 * @param auditType the auditType to set
	 */
	public void setAuditType(AuditType auditType) {
		this.auditType = auditType;
	}

}
