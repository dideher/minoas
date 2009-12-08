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

	@In(create = true)
	private IPrincipalDAO principalDAO;

	@In(create = true)
	private IRoleDAO roleDAO;

	protected Principal createPrincipal(String username, String password, String realname) {
		Principal principal = principalDAO.findByUsername(username);
		if (principal == null) {
			principal = new Principal(username, password, realname);
			principalDAO.persist(principal);
		} else {
			log.info("principal \"#0\" already exists in the database", username);
		}
		return principal;
	}

	protected Role createRole(String name, String desc, Principal creator) {
		Role role = roleDAO.findByName(name);
		if (role == null) {
			role = new Role(name, desc);
			role.setInsertedBy(creator);
			roleDAO.persist(role);
			log.info("successfully created role \"#0\" with description \"#1\" in database.", role.getName(), role
					.getDescription());
		} else {
			log.info("role \"#0\" already exists in the database.", name);
		}
		return role;
	}

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		log.info("initializing security model");

		Principal admin = createPrincipal("admin", "admin", "Ο Θέος");
		Role adminRole = createRole("ADMIN", "Admin Role - The god him self", admin);
		admin.addRole(adminRole);

		/* Employee */
		createRole("ADD_EMPLOYEE", "Add New Employee", admin);
		createRole("ADD_EMPLOYEE_HOURLY_BASED", "Add New Hourly Based Employee", admin);
		createRole("ADD_EMPLOYEE_DEPUTY", "Add New Deputy Employee", admin);
		createRole("ADD_EMPLOYEE_REGULAR", "Add New Regular Employee", admin);

		createRole("MANAGE_EMPLOYEE", "Manage Employee", admin);
		createRole("VIEW_EMPLOYEE", "View Employee", admin);

		createRole("VIEW_PREPARATORY", "View Preparatory Data", admin);
		createRole("MANAGE_PREPARATORY_OWNER", "Manage Preperatory Owner", admin);
		createRole("MANAGE_PREPARATORY_EST_LICENSE", "Manage Preparatory Establishment License", admin);
		createRole("MANAGE_SPECIALIZATION_GROUP", "Manage Specialization Groups", admin);
		/* Secondments */
		createRole("ADD_SECONDMENT", "Add New Secondments", admin);
		createRole("MANAGE_SECONDMENT", "Manage Secondment", admin);
		createRole("VIEW_SECONDMENT", "View Secondment", admin);
		/* Service Allocation */
		createRole("ADD_SERVICE_ALLOCATION", "Add New Service Allocation", admin);
		createRole("MANAGE_SERVICE_ALLOCATION", "Manage Service Allocation", admin);
		createRole("VIEW_SERVICE_ALLOCATION", "View Service Allocation", admin);
		/* Leave */
		createRole("ADD_LEAVE", "Add New Leave", admin);
		createRole("MANAGE_LEAVE", "Manage Leave", admin);
		createRole("VIEW_LEAVE", "View Leave", admin);
		/* Disposal */
		createRole("ADD_DISPOSAL", "Add New Disposal", admin);
		createRole("MANAGE_DISPOSAL", "Manage Disposal", admin);
		createRole("VIEW_DISPOSAL", "View Disposal", admin);
		/* Reports */
		createRole("VIEW_SCHOOL_REPORT", "View School Oriented Reports", admin);

	}
}
