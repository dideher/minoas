package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.security.Principal;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Name;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Stateless(name = "PrincipalDAO")
@Local(IPrincipalDAO.class)
@Name("principalDAO")
public class PrincipalDAOImpl extends GenericDAOImpl<Principal, Long> implements IPrincipalDAO {

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Collection<Principal> findAll() {
		return getEntityManager().createQuery("FROM Principal p ORDER (p.username)").getResultList();
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findByExample(java.lang.Object)
	 */
	public Principal findByExample(Principal entityInstance) {
		throw new RuntimeException("not implemented yet.");
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IPrincipalDAO#findByUsername(java.lang.String)
	 */
	public Principal findByUsername(String username) {
		try {
			return (Principal) getEntityManager().createQuery("FROM Principal p WHERE p.username=:username")
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
