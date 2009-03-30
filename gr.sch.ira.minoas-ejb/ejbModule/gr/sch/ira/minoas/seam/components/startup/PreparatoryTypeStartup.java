package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNature;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;
import gr.sch.ira.minoas.session.persistent.IPreparatoryUnitNatureDAO;

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
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Startup()
@Scope(ScopeType.APPLICATION)
@Name("PreparatoryTypeStartup")
public class PreparatoryTypeStartup {

	@Logger
	private Log log;


	@In(create = true)
	protected IPreparatoryUnitNatureDAO preparatoryUnitNatureDAO;

	
	

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
	if (preparatoryUnitNatureDAO.findByNatureType(PreparatoryUnitNatureType.LANGUAGE_CENTER) == null)
			preparatoryUnitNatureDAO.persist(new PreparatoryUnitNature(PreparatoryUnitNatureType.LANGUAGE_CENTER,
					"Κέντρο Ξένων Γλωσσών"));
		if (preparatoryUnitNatureDAO.findByNatureType(PreparatoryUnitNatureType.SECONDARY_EDUCATION) == null)
			preparatoryUnitNatureDAO.persist(new PreparatoryUnitNature(PreparatoryUnitNatureType.SECONDARY_EDUCATION,
					"Φροντιστήριο Δευτ/θμιας Εκπ/σης"));

	}
}
