
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.preparatory.PreparatoryOwner;

import java.util.Date;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "preparatoryOwnerHome")
public class PreparatoryOwnerHome extends MinoasEntityHome<PreparatoryOwner> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 * 
	 */
	@Override
	@Transactional
	public String persist() {
		PreparatoryOwner owner = (PreparatoryOwner)getInstance();
		owner.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	@Restrict("#{s:hasRole('MANAGE_PREPARATORY_OWNER') or s:hasRole('ADMIN')}")
	public String update() {
		PreparatoryOwner owner = (PreparatoryOwner)getInstance();
		owner.setModifiedOn(new Date(System.currentTimeMillis()));
		getLogger().info("principal '#0' updated preparatory owner #1", getPrincipalName(), owner);
		return super.update();
	}
	
	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}
	
}
