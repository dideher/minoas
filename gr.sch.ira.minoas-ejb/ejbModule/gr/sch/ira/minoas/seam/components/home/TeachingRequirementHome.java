package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicense;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;
import gr.sch.ira.minoas.model.preparatory.PreparatoryOwner;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "teachingRequirementHome")
@Scope(ScopeType.CONVERSATION)
public class TeachingRequirementHome extends MinoasEntityHome<TeachingRequirement> {

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	@Transactional
	public String remove() {
		School school = schoolHome.getDefinedInstace();
		TeachingRequirement req = (TeachingRequirement) getInstance();
		school.getTeachingRequirements().remove(req);
		req.setSchool(null);
		req.setSchoolYear(null);
		return super.remove();
	}

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In()
	private CoreSearching coreSearching;

	
	@In(create=true)
	private SchoolHome schoolHome;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	
	protected TeachingRequirement createInstance() {
		TeachingRequirement instance = (TeachingRequirement) super.createInstance();
//		instance.setStatusType(EstablishmentLicenseStatusType.PENDING);
//		instance.setRequestDate(new Date(System.currentTimeMillis()));
//		instance.setSchoolYear(coreSearching.getActiveSchoolYear());
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "teachingRequirement", scope = ScopeType.PAGE)
	public TeachingRequirement getInstance() {
		TeachingRequirement req =(TeachingRequirement)super.getInstance();
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
		req.setSchoolYear(coreSearching.getActiveSchoolYear(getEntityManager()));
		
		school.addTeachingRequirement(req);
		getEntityManager().persist(req);
		
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

	public void wire() {
		
	}
	
	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

}
