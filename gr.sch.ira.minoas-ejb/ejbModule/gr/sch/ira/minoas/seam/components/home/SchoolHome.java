
package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import sun.util.logging.resources.logging;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.seam.components.CoreSearching;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolHome")
@Scope(ScopeType.CONVERSATION)
public class SchoolHome extends MinoasEntityHome<School> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@In(required=true)
	private CoreSearching coreSearching;
	
	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "school")
	public School getInstance() {
		return (School) super.getInstance();
	}
	
	public String addTeachingResource() {
		School instance = getDefinedInstace();
		TeachingRequirement req = new TeachingRequirement();
		req.setSchool(instance);
		req.setSchoolYear(coreSearching.getActiveSchoolYear());
		if(getIdentity()!=null) {
			
		}
		instance.addTeachingRequirement(req);
		return "added";
	}
	
	
	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}
	
	@Transactional
	public String updateTeachingResources() {
		School school = getDefinedInstace();
		school.setModifiedOn(new Date(System.currentTimeMillis()));
		super.update();
		getLogger().info(" user #0 has updated school's #1 teaching resources", getPrincipalName(), school);
		return "updated";
	}
	
}
