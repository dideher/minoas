package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatus;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;
import gr.sch.ira.minoas.session.persistent.IEstablishmentLicenseStatusDAO;

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
@Name("EstablishmentLicenseStatusStartup")
public class EstablishmentLicenseStatusStartup {

	@In(create = true)
	protected IEstablishmentLicenseStatusDAO establishmentLicenseStatusDAO;

	@Logger
	private Log log;

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		if (establishmentLicenseStatusDAO.findByType(EstablishmentLicenseStatusType.CANCELED) == null) {
			log.info("created establishment license status {0}", establishmentLicenseStatusDAO
					.persist(new EstablishmentLicenseStatus(EstablishmentLicenseStatusType.CANCELED, "Άκυρη")));
		}
		if (establishmentLicenseStatusDAO.findByType(EstablishmentLicenseStatusType.EXPIRED) == null) {
			log.info("created establishment license status {0}", establishmentLicenseStatusDAO
					.persist(new EstablishmentLicenseStatus(EstablishmentLicenseStatusType.EXPIRED, "Έχει Λήξει")));
		}
		if (establishmentLicenseStatusDAO.findByType(EstablishmentLicenseStatusType.PENDING) == null) {
			log.info("created establishment license status {0}", establishmentLicenseStatusDAO
					.persist(new EstablishmentLicenseStatus(EstablishmentLicenseStatusType.PENDING, "Εκκρεμεί")));
		}
		if (establishmentLicenseStatusDAO.findByType(EstablishmentLicenseStatusType.PENDING_RENEWAL) == null) {
			log.info("created establishment license status {0}", establishmentLicenseStatusDAO
					.persist(new EstablishmentLicenseStatus(EstablishmentLicenseStatusType.PENDING_RENEWAL, "Εκκρεμεί Ανανέωση")));
		}
		if (establishmentLicenseStatusDAO.findByType(EstablishmentLicenseStatusType.REJECTED) == null) {
			log.info("created establishment license status {0}", establishmentLicenseStatusDAO
					.persist(new EstablishmentLicenseStatus(EstablishmentLicenseStatusType.REJECTED, "Απορριφθείσα")));
		}
	}
}
