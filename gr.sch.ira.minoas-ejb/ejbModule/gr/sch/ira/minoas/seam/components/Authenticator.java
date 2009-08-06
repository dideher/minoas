package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator extends BaseDatabaseAwareSeamComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	@In
	protected Identity identity;

	@Transactional
	public boolean authenticate() {
		String username = identity.getCredentials().getUsername();
		try {
			info("authenticating user #0", username);
			Principal p = (Principal) getEntityManager().createQuery(
					"SELECT OBJECT(p) FROM Principal p WHERE p.username = :username").setParameter("username", username)
					.getSingleResult();
			if(p.getActive()==false) {
				info("principal '#0' is disabled, therefore access is denied.", username, p.getRoles());
				return false;
				
			}
			if (p.getPassword().equals(identity.getCredentials().getPassword())) {

				for (Role role : p.getRoles()) {
					identity.addRole(role.getName());
				}
				info("user #0 has been authenticated successfully and granted the #1 role(s)", username, p.getRoles());
				return true;
			} else {
				warn("user #0 specified invalid password, therefore access will be denied.", username);
				return false;
			}
		} catch (NoResultException nre) {
			warn("user #0 not found, therefore access will be denied.", username);
			return false;
		}
	}
}
