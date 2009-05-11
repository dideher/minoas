package gr.sch.ira.minoas.session.persistent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Name;

import gr.sch.ira.minoas.model.security.Role;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Stateless(name = "RoleDAO")
@Local(IRoleDAO.class)
@Name("roleDAO")
public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements IRoleDAO {

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Collection<Role> findAll() {
		return getEntityManager().createQuery("FROM Role r ORDER (r.name)").getResultList();
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findByExample(java.lang.Object)
	 */
	public Role findByExample(Role entityInstance) {
		throw new RuntimeException("not implemented yet.");
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IRoleDAO#findByName(java.lang.String)
	 */
	public Role findByName(String name) {
		try {
			return (Role) getEntityManager().createQuery("FROM Role r WHERE r.name=:name").setParameter("name",
					name).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
