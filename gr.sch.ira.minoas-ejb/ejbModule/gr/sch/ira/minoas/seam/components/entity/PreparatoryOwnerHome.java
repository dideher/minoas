
package gr.sch.ira.minoas.seam.components.entity;

import gr.sch.ira.minoas.model.preparatory.PreparatoryOwner;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "preparatoryOwnerHome")
@Scope(ScopeType.CONVERSATION)
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
		PreparatoryOwner owner = getInstance();
		owner.setInsertedOn(new Date(System.currentTimeMillis()));
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		PreparatoryOwner owner = getInstance();
		owner.setModifiedOn(new Date(System.currentTimeMillis()));
		return super.update();
	}
	
	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		return super.createInstance();
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "preparatoryOwner")
	public PreparatoryOwner getInstance() {
		return (PreparatoryOwner)super.getInstance();
	}
}
