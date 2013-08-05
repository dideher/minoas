package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.SpecializationHome;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name(value = "specializationManagement")
@Scope(ScopeType.PAGE)
public class SpecializationManagement extends BaseDatabaseAwareSeamComponent {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(required = false)
	private SpecializationHome specializationHome;

	@DataModel(value = "specializations")
	private Collection<Specialization> specializations;

	@Factory(value = "specializations")
	public void fetchSpecializations() {

		specializations = getCoreSearching().getSpecializations(
				getEntityManager(), Boolean.TRUE);

	}

	@Transactional(TransactionPropagationType.MANDATORY)
	public String updateSpecialization() {
		String persistanceReturnValue = null;
		try {
			persistanceReturnValue = specializationHome.update();
			getEntityManager().flush();
			specializations = getCoreSearching().getSpecializations(
					getEntityManager());
		} catch (Exception e) {

			facesMessages.add(Severity.ERROR,
					"Ο Κωδικός της ειδικότητας υπάρχει ήδη.");

		}
		return persistanceReturnValue;
	}

	@Transactional(TransactionPropagationType.MANDATORY)
	public String enableSpecialization() {
		if (specializationHome.isManaged()) {
			Specialization sp = specializationHome.getInstance();
			sp.setDisabled(Boolean.FALSE);
			getEntityManager().flush();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			getFacesMessages().add(Severity.ERROR,"Specialization not managed.");
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.MANDATORY)
	public String disableSpecialization() {
		if (specializationHome.isManaged()) {
			EntityManager em = getEntityManager();
			Object obj = em.createQuery("SELECT COUNT(*) FROM Employment e WHERE e.active IS TRUE AND (e.deleted IS NOT NULL OR e.deleted IS FALSE) AND e.specialization=:specialization").setParameter("specialization", specializationHome.getInstance()).getSingleResult();
			if(obj instanceof Number) {
				long employmentCount = ((Number)(obj)).longValue();
				if(employmentCount > 0) {
					getFacesMessages().add(Severity.ERROR, String.format("Υπάρχουν '%d' ενεργές σχέσεις εργασίας αυτής της ειδικότητας. Τερματίσετε τις σχέσεις ή τερματίστε του εργαζόμενους και δοκιμάστε ξανά.", employmentCount));
					return ACTION_OUTCOME_FAILURE;
				} else {
					Specialization sp = specializationHome.getInstance();
					sp.setDisabled(Boolean.TRUE);
					getEntityManager().flush();
					return ACTION_OUTCOME_SUCCESS;
				}
				
			} else {
				return ACTION_OUTCOME_FAILURE;
			}
			
		} else {
			getFacesMessages().add(Severity.ERROR,"Specialization not managed.");
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.MANDATORY)
	public String insertSpecialization() {
		String persistanceReturnValue = null;
		try {
			persistanceReturnValue = specializationHome.persist();
			getEntityManager().flush();
			specializations = getCoreSearching().getSpecializations(
					getEntityManager());
		} catch (Exception e) {

			facesMessages.add(Severity.ERROR,
					"Ο Κωδικός της ειδικότητας υπάρχει ήδη.");

		}
		return persistanceReturnValue;
	}
}
