package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "specializationGroupHome")
public class SpecializationGroupHome extends MinoasEntityHome<SpecializationGroup> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "specializationGroup", scope = ScopeType.PAGE)
	public SpecializationGroup getInstance() {
		return (SpecializationGroup) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		SpecializationGroup group = (SpecializationGroup) getInstance();
		group.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		group.setInsertedBy(getPrincipal());
		getEntityManager().persist(group);
		getLogger()
				.info("specialization group #0 has been created by #1", getInstance().getTitle(), getPrincipalName());
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		getLogger()
				.info("specialiaztion group #0 has been deleted by #1", getInstance().getTitle(), getPrincipalName());
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
		SpecializationGroup instance = getInstance();
		getLogger().info("specialiaztion group #0 has been updated by #1", instance.getTitle(), getPrincipalName());
		return super.update();
	}

}
