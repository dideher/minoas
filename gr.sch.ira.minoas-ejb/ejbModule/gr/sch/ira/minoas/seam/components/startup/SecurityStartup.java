package gr.sch.ira.minoas.seam.components.startup;

import javax.ejb.EJB;

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
		Role role;
		Principal p;
		
		/* check if there is an ADMIN Role & Principal */
		if(roleDAO.findByName("ADMIN")==null) {
			role = createRole("ADMIN", "Admin Role - The god him self");
		}
		if(principalDAO.findByUsername("admin")==null) {
			p = new Principal("admin", "Administrator", "admin.*");
			p.addRole(roleDAO.findByName("ADMIN"));
			principalDAO.persist(p);
		} else {
			principalDAO.findByUsername("admin").addRole(roleDAO.findByName("ADMIN"));
		}
		
		createRole("VIEW_PREPARATORY", "View Preparatory Data");
		createRole("MANAGE_PREPARATORY_OWNER", "Manage Preperatory Owner");
		createRole("MANAGE_PREPARATORY_EST_LICENSE", "Manage Preparatory Establishment License");
	}
	
	
	protected Role createRole(String name, String desc) {
		Role role = roleDAO.findByName(name);
		if(role==null) {
			role = new Role(name, desc);
			roleDAO.persist(role);
			log.info("successfully created role \"#0\" with description \"#1\" in database.", role.getName(), role.getDescription());
		} else {
			log.info("role #0 already exists in the database.", name);
		}
		return role;
	}
}
