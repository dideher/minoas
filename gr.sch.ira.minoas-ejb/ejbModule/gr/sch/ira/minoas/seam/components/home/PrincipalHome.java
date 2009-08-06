package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.security.Principal;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "principalHome")
public class PrincipalHome extends MinoasEntityHome<Principal> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Transactional
	public String disablePrincipal() {
		Principal p = getInstance();
		p.setActive(Boolean.FALSE);
		getLogger().info("Principal '#0' disabled principal '#1'",
				getPrincipalName(), p.getUsername());
		return super.update();
	}

	@Transactional
	public String enablePrincipal() {
		Principal p = getInstance();
		p.setActive(Boolean.TRUE);
		getLogger().info("Principal '#0' enabled principal '#1'",
				getPrincipalName(), p.getUsername());
		return super.update();

	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "principal", scope = ScopeType.PAGE)
	public Principal getInstance() {
		return (Principal) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		/* check if the username is already in use */
		Principal newPrincipal = getInstance();
		Principal temp = getCoreSearching().getPrincipal(getEntityManager(),
				newPrincipal.getUsername());
		if (temp != null) {
			getLogger()
					.warn(
							"principal '#0' tried to insert a principal with an existing username '#1'.",
							getPrincipalName(), temp.getUsername());
			return DUPLICATE_VALUE_OUTCOME;
		}
		newPrincipal.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		return super.remove();
	}

	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}

}
