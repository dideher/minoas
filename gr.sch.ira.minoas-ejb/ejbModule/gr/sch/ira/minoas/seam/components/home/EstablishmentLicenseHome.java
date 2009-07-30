package gr.sch.ira.minoas.seam.components.home;

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
@Name(value = "establishmentLicenseHome")
@Scope(ScopeType.CONVERSATION)
public class EstablishmentLicenseHome extends MinoasEntityHome<EstablishmentLicense> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	

	
	@In(required=false)
	private PreparatoryOwnerHome preparatoryOwnerHome;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected EstablishmentLicense createInstance() {
		EstablishmentLicense instance = (EstablishmentLicense) super.createInstance();
		instance.setStatusType(EstablishmentLicenseStatusType.PENDING);
		instance.setRequestDate(new Date(System.currentTimeMillis()));
		instance.setSchoolYear(getCoreSearching().getActiveSchoolYear());
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "establishmentLicense")
	public EstablishmentLicense getInstance() {
		return (EstablishmentLicense) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	public String persist() {
		EstablishmentLicense license = getInstance();
		license.setInsertedOn(new Date(System.currentTimeMillis()));
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
		EstablishmentLicense license = getInstance();
		
		/* If the license status is PENDING, check 
		 * if the user just specified an judgment date & number. 
		 * If so, update the status of the license to VALID.
		 */
		if(license.getStatusType().equals(EstablishmentLicenseStatusType.PENDING)) {
			if(license.getRequestJudgmentDate()!=null && license.getRequestJudgmentNumber()!=null) {
				license.setStatusType(EstablishmentLicenseStatusType.VALID);
			}
		}
		return super.update();
	}
	
	public void wire() {
		if(preparatoryOwnerHome!=null) {
		PreparatoryOwner owner = preparatoryOwnerHome.getDefinedInstace();
		if (owner != null) {
			getInstance().setOwner(owner);
		}
		}
	}

}
