package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatus;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.annotations.Name;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Stateless
@Local(IEstablishmentLicenseStatusDAO.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Name(value = "establishmentLicenseStatusDAO")
public class EstablishmentLicenseStatusDAOImpl extends GenericDAOImpl<EstablishmentLicenseStatus, Integer> implements
		IEstablishmentLicenseStatusDAO {

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Collection<EstablishmentLicenseStatus> findAll() {
		try {
			return getEntityManager().createQuery("FROM EstablishmentLicenseStatus p ORDER BY (p.title)")
					.getResultList();
		} catch (javax.persistence.NoResultException nre) {
			return EMPTY_COLLECTION;
		}
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IEstablishmentLicenseStatusDAO#findByType(gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatus.EstablishmentLicenseStatusType)
	 */
	public EstablishmentLicenseStatus findByType(EstablishmentLicenseStatusType establishmentLicenseStatusType) {
		try {
			return (EstablishmentLicenseStatus) getEntityManager().createQuery(
					"FROM EstablishmentLicenseStatus p WHERE p.statusType=:statusType ORDER BY (p.title)").setParameter("statusType",
					establishmentLicenseStatusType).getSingleResult();
		} catch (javax.persistence.NoResultException nre) {
			return null;
		}
	}

}
