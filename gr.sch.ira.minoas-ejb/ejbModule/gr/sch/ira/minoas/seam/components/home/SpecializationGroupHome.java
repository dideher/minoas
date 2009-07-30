package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;
import java.util.Iterator;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import sun.util.logging.resources.logging;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.seam.components.CoreSearching;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "specializationGroupHome")
public class SpecializationGroupHome extends MinoasEntityHome<SpecializationGroup> {

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		getLogger().info("specialiaztion group #0 has been deleted by #1", getInstance().getTitle(), getIdentity().getPrincipal().getName());
		return super.remove();
	}


	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(required = true)
	private CoreSearching coreSearching;

	
	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "specializationGroup", scope=ScopeType.PAGE)
	public SpecializationGroup getInstance() {
		return (SpecializationGroup) super.getInstance();
	}


	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}


	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		SpecializationGroup group = (SpecializationGroup)getInstance();
		group.setSchoolYear(coreSearching.getActiveSchoolYear(getEntityManager()));
		getEntityManager().persist(group);
		return super.persist();
	}


	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}


	/**
	 * @see org.jboss.seam.framework.Home#setId(java.lang.Object)
	 */
	@Override
	public void setId(Object id) {
		super.setId(id);
	}


}
