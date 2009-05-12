package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.session.persistent.IPrincipalDAO;
import gr.sch.ira.minoas.session.persistent.IRoleDAO;

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
@Name("SecurityStartup")
public class SecurityStartup {

	
	@Logger
	private Log log;

	@In(create=true)
	private IRoleDAO roleDAO;
	
	@In(create=true)
	private IPrincipalDAO principalDAO;
	
	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		log.info("initializing security model");
		
		Principal admin = createPrincipal("admin", "admin", "Ο Θέος");
		Role adminRole = createRole("ADMIN", "Admin Role - The god him self", admin);
		admin.addRole(adminRole);
		
		createRole("VIEW_PREPARATORY", "View Preparatory Data", admin);
		createRole("MANAGE_PREPARATORY_OWNER", "Manage Preperatory Owner", admin);
		createRole("MANAGE_PREPARATORY_EST_LICENSE", "Manage Preparatory Establishment License", admin);
	}
	
	protected Principal createPrincipal(String username, String password, String realname) {
		Principal principal = principalDAO.findByUsername(username);
		if(principal==null) {
			principal = new Principal(username, password, realname);
			principalDAO.persist(principal);
		} else {
			log.info("principal \"#0\" already exists in the database", username);
		}
		return principal;
	}
	protected Role createRole(String name, String desc, Principal creator) {
		Role role = roleDAO.findByName(name);
		if(role==null) {
			role = new Role(name, desc);
			role.setInsertedBy(creator);
			roleDAO.persist(role);
			log.info("successfully created role \"#0\" with description \"#1\" in database.", role.getName(), role.getDescription());
		} else {
			log.info("role \"#0\" already exists in the database.", name);
		}
		return role;
	}
}
