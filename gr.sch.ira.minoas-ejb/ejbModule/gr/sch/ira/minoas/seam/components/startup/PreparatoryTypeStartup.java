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

package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNature;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;
import gr.sch.ira.minoas.seam.components.entity.PreparatoryUnitNatureHome;
import gr.sch.ira.minoas.session.persistent.IPreparatoryUnitNatureDAO;

import javax.ejb.EJB;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Startup()
@Scope(ScopeType.APPLICATION)
@Name("PreparatoryTypeStartup")
public class PreparatoryTypeStartup {

	@Logger
	private Log log;

	@In
	protected EntityManager entityManager;

	@In(create = true)
	protected IPreparatoryUnitNatureDAO preparatoryUnitNatureDAO;

	@In(create=true)
	protected PreparatoryUnitNatureHome preparatoryUnitNatureHome;
	
	
	protected EntityManager getMinoasDatabase() {
		return entityManager;
	}

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		System.err.println("******************************************************************************");
		System.err.println(preparatoryUnitNatureHome);
		if(preparatoryUnitNatureHome!=null) {
			
		}
		if (preparatoryUnitNatureDAO.findByNatureType(PreparatoryUnitNatureType.LANGUAGE_CENTER) == null)
			preparatoryUnitNatureDAO.persist(new PreparatoryUnitNature(PreparatoryUnitNatureType.LANGUAGE_CENTER,
					"Κέντρο Ξένων Γλωσσών"));
		if (preparatoryUnitNatureDAO.findByNatureType(PreparatoryUnitNatureType.SECONDARY_EDUCATION) == null)
			preparatoryUnitNatureDAO.persist(new PreparatoryUnitNature(PreparatoryUnitNatureType.SECONDARY_EDUCATION,
					"Φροντιστήριο Δευτ/θμιας Εκπ/σης"));

	}
}
