package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.TeachingRequirement;
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
@Name(value = "teachingRequirement")
@Scope(ScopeType.CONVERSATION)
public class TeachingRequirementHome extends MinoasEntityHome<TeachingRequirement> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In()
	private CoreSearching coreSearching;

	
	@In(required=false)
	private SchoolHome schoolHomeOwnerHome;

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
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	public String persist() {
		//EstablishmentLicense license = getInstance();
		//license.setInsertedOn(new Date(System.currentTimeMillis()));
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
