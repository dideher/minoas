/*
 * 
 *
 * Copyright (c) 2009 FORTHnet, S.A. All Rights Reserved.
 *
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package gr.sch.ira.minoas.seam.components.entity;

import java.util.Date;

import javax.persistence.PrePersist;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicense;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;
import gr.sch.ira.minoas.model.preparatory.PreparatoryOwner;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value="establishmentLicenseHome")
public class EstablishmentLicenseHome extends MinoasEntityHome<EstablishmentLicense> {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	@In(required=true)
	private SchoolYear activeSchoolYear;
	
	@In(create = true)
	private PreparatoryOwnerHome preparatoryOwnerHome;

	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected EstablishmentLicense createInstance() {
		EstablishmentLicense instance = (EstablishmentLicense)super.createInstance();
		instance.setStatus(EstablishmentLicenseStatusType.PENDING);
		instance.setRequestDate(new Date(System.currentTimeMillis()));
		instance.setSchoolYear(activeSchoolYear);
		return instance;
	}
	
	public void wire() {
		PreparatoryOwner owner = preparatoryOwnerHome.getDefinedInstace();
		if (owner!=null) {
			getInstance().setOwner(owner);
		}
	}


	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value="establishmentLicense")
	public EstablishmentLicense getInstance() {
		return (EstablishmentLicense)super.getInstance();
	}
	
	

}
