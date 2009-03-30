package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatus;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public interface IEstablishmentLicenseStatusDAO extends IGenericDAO<EstablishmentLicenseStatus, Integer> {
	EstablishmentLicenseStatus findByType(EstablishmentLicenseStatusType establishmentLicenseStatusType);

}
