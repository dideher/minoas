/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.core.Audit;
import gr.sch.ira.minoas.model.core.AuditType;
import gr.sch.ira.minoas.model.security.Principal;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.faces.FacesMessages;
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

	@In
	protected EntityManager entityManager;
	
	@In()
	private CoreSearching coreSearching;

	@In(required = false)
	private Identity identity;
	

	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected Principal getPrincipal() {
		return coreSearching.getPrincipal(getEntityManager(), getPrincipalName());
	}

	protected String getPrincipalName() {
		return getIdentity() != null ? getIdentity().getPrincipal().getName() : "<anonymous>";
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
