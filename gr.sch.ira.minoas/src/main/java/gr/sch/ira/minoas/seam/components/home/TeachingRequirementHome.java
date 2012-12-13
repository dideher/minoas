package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.TeachingRequirement;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "teachingRequirementHome")
public class TeachingRequirementHome extends MinoasEntityHome<TeachingRequirement> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private SchoolHome schoolHome;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected TeachingRequirement createInstance() {
		TeachingRequirement instance = (TeachingRequirement) super.createInstance();
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "teachingRequirement", scope = ScopeType.PAGE)
	public TeachingRequirement getInstance() {
		TeachingRequirement req = (TeachingRequirement) super.getInstance();
		return req;
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	public String persist() {
		School school = schoolHome.getDefinedInstace();
		TeachingRequirement req = (TeachingRequirement) getInstance();
		req.setSchool(school);
		req.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		school.addTeachingRequirement(req);
		req.setInsertedBy(getPrincipal());
		getEntityManager().persist(req);
		getLogger().info("principal '#0' created teaching requirement #1.", getPrincipalName(), req);
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		School school = schoolHome.getDefinedInstace();
		TeachingRequirement req = (TeachingRequirement) getInstance();
		school.getTeachingRequirements().remove(req);
		getLogger().info("principal '#0' deleted teaching requirement #1.", getPrincipalName(), req);
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
		getLogger().info("principal '#0' updated teaching requirement #1.", getPrincipalName(), getInstance());
		return super.update();
	}

}
