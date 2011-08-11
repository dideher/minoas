/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.security.Principal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.security.Identity;

/**
 * @author slavikos
 *
 */
public abstract class BaseDatabaseAwareSeamComponent extends BaseSeamComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Inject
	private CoreSearching coreSearching;

	@Inject
	private Identity identity;
	

	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected Principal getPrincipal() {
		return coreSearching.getPrincipal(getEntityManager(), getPrincipalName());
	}

	protected String getPrincipalName() {
		return getIdentity() != null ? getIdentity().getUser().getId() : "<anonymous>";
	}

	
	protected void logAudit(AuditType type, String comment) {
		Audit audit = new Audit(type, comment, getPrincipal());
		getEntityManager().persist(audit);
	}
	/**
	 * @return the coreSearching
	 */
	protected CoreSearching getCoreSearching() {
		return coreSearching;
	}

	/**
	 * @return the identity
	 */
	protected Identity getIdentity() {
		return identity;
	}

}
