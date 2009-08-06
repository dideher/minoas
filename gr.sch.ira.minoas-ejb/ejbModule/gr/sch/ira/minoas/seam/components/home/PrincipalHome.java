package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
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
	
	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "principal", scope=ScopeType.PAGE)
	public Principal getInstance() {
		return (Principal) super.getInstance();
	}

	
	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		return super.persist();
	}


	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		return super.persist();
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
