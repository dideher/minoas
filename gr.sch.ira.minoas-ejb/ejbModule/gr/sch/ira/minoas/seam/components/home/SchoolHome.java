package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.TeachingRequirement;

import java.util.Date;
import java.util.Iterator;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolHome")
public class SchoolHome extends MinoasEntityHome<School> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	

	@In(required = false)
	@Out(required = false)
	private TeachingRequirement exampleTeachingRequirement;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "school", scope=ScopeType.PAGE)
	public School getInstance() {
		return (School) super.getInstance();
	}

	public String addTeachingResource() {
		School instance = getDefinedInstace();
		TeachingRequirement req = new TeachingRequirement();
		req.setSchool(instance);
		req.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
		if (getIdentity() != null) {

		}
		if (exampleTeachingRequirement != null) {
			req.setHours(exampleTeachingRequirement.getHours());
			req.setSpecialization(exampleTeachingRequirement.getSpecialization());
			req.setComment(exampleTeachingRequirement.getComment());
			exampleTeachingRequirement = new TeachingRequirement();
			

		}
		instance.addTeachingRequirement(req);
		joinTransaction();
		getEntityManager().flush();
		facesMessages.add("Οι συνολικά #0 διδακτίκες ώρες, ειδικότητας #1, προστέθηκαν με επιτυχία στην σχολική μονάδα #2.", req.getHours(), req.getSpecialization().getTitle(), req.getSchool().getTitle());
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
		for(Iterator<TeachingRequirement> it = school.getTeachingRequirements().iterator() ; it.hasNext();) {
			TeachingRequirement r = it.next();
			if(r.getHours()<=0) {
				r.setSchool(null);
				r.setSchoolYear(null);
				getEntityManager().remove(r);
				it.remove();
			}
		}
		getLogger().info(" user #0 has updated school's #1 teaching resources", getPrincipalName(), school);
		return super.update();
	}

}
