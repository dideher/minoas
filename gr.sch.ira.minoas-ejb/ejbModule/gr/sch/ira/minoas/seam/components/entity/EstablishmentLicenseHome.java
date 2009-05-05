package gr.sch.ira.minoas.seam.components.entity;

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

	@In()
	private CoreSearching coreSearching;

	
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
		instance.setSchoolYear(coreSearching.getActiveSchoolYear());
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

	public void wire() {
		System.err.println("**** wire called ****");
		if(preparatoryOwnerHome!=null) {
		PreparatoryOwner owner = preparatoryOwnerHome.getDefinedInstace();
		if (owner != null) {
			getInstance().setOwner(owner);
		}
		}
	}
	
	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

}
