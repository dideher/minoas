package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Startup()
@Scope(ScopeType.APPLICATION)
@Name("SecurityStartup")
public class SecurityStartup extends BaseDatabaseAwareSeamComponent {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    protected Principal getPrincipal(String principalName) {
		try {
			return (Principal) getEntityManager().createQuery("SELECT p FROM Principal p WHERE p.username = :username")
					.setParameter("username", principalName).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}
	}

	protected Role getRole(String roleName) {

		try {
			return (Role) getEntityManager().createQuery("SELECT r FROM Role r WHERE r.name = :roleName").setParameter(
					"roleName", roleName).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}

	protected Principal createPrincipal(String username, String password, String realname) {
		Principal principal = getPrincipal(username);
		if (principal == null) {
			principal = new Principal(username, password, realname);
			getEntityManager().persist(principal);
		} else {
			info("principal \"#0\" already exists in the database", username);
		}
		return principal;
	}

	protected Role createRole(String name, String desc, Principal creator) {
		Role role = getRole(name);
		if (role == null) {
			role = new Role(name, desc);
			role.setInsertedBy(creator);
			getEntityManager().persist(role);
			info("successfully created role \"#0\" with description \"#1\" in database.", role.getName(), role
					.getDescription());
		} else {
			info("role \"#0\" already exists in the database.", name);
		}
		return role;
	}

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		info("initializing security model");

		Principal admin = createPrincipal("admin", "admin", "Ο Θέος");
		Role adminRole = createRole("ADMIN", "Admin Role - The god him self", admin);
		admin.addRole(adminRole);

		/* Employee */
		createRole("ADD_EMPLOYEE", "Add New Employee", admin);
		
		
		createRole("ADD_EMPLOYEE_HOURLY_BASED", "Add New Hourly Based Employee", admin);
		createRole("ADD_EMPLOYEE_DEPUTY", "Add New Deputy Employee", admin);
		createRole("ADD_EMPLOYEE_REGULAR", "Add New Regular Employee", admin);
        
		/* Employee Management */
		createRole("MANAGE_EMPLOYEE", "Manage Employee", admin);
        
        createRole("MANAGE_EMPLOYEE_BASIC_INFO", "Manage Employee's Basic Info", admin);
        createRole("MANAGE_EMPLOYEE_SPECIALIZATION", "Manage Employee's Specialization", admin);
        createRole("MANAGE_EMPLOYEE_REGULAR_REGISTRY", "Manage Employee's Regular Registry ID", admin);
        
		createRole("MANAGE_EMPLOYMENT", "Manage Employment", admin);
		createRole("MANAGE_EMPLOYMENT_HOURLY_BASED", "Manage Hourly Based Employment", admin);
		createRole("MANAGE_EMPLOYMENT_DEPUTY", "Manage Deputy Employment", admin);
		createRole("MANAGE_EMPLOYMENT_REGULAR", "Manage Regular Employment", admin);
		createRole("DELETE_EMPLOYMENT_REGULAR", "Delete Regular Employment", admin);
		createRole("DELETE_EMPLOYMENT_DEUPTY", "Delete Deputy Employment", admin);
		
		

		createRole("VIEW_EMPLOYEE", "View Employee", admin);

		createRole("VIEW_PREPARATORY", "View Preparatory Data", admin);
		createRole("MANAGE_PREPARATORY_OWNER", "Manage Preperatory Owner", admin);
		createRole("MANAGE_PREPARATORY_EST_LICENSE", "Manage Preparatory Establishment License", admin);
		createRole("MANAGE_SPECIALIZATION_GROUP", "Manage Specialization Groups", admin);
		/* Secondments */
		createRole("ADD_SECONDMENT", "Add New Secondments", admin);
		createRole("MANAGE_SECONDMENT", "Manage Secondment", admin);
		createRole("VIEW_SECONDMENT", "View Secondment", admin);
		createRole("DELETE_SECONDMENT", "Delete Secondment", admin);
		
		/* Service Allocation */
		createRole("ADD_SERVICE_ALLOCATION", "Add New Service Allocation", admin);
		createRole("MANAGE_SERVICE_ALLOCATION", "Manage Service Allocation", admin);
		createRole("VIEW_SERVICE_ALLOCATION", "View Service Allocation", admin);
		/* Leave */
		createRole("ADD_LEAVE", "Add New Leave", admin);
		createRole("MANAGE_LEAVE", "Manage Leave", admin);
		createRole("VIEW_LEAVE", "View Leave", admin);
		createRole("PRINT_LEAVE", "Print Leave", admin);
		createRole("DELETE_LEAVE", "Delete Leave", admin);
		/* Disposal */
		createRole("ADD_DISPOSAL", "Add New Disposal", admin);
		createRole("MANAGE_DISPOSAL", "Manage Disposal", admin);
		createRole("VIEW_DISPOSAL", "View Disposal", admin);
		/* Reports */
		createRole("VIEW_SCHOOL_REPORT", "View School Oriented Reports", admin);

		/* School Unit */
		createRole("MANAGE_SCHOOL_BASIC", "Basic School Unit Management", admin);
		
		/* Improvements */
		createRole("MANAGE_IMPROVEMENTS", "Manage Improvements", admin);
		createRole("VIEW_IMPROVEMENTS", "View Improvements", admin);
		
		/* Permanent Transfers */
		createRole("VIEW_PERMANENT_TRANSFERS", "View Permanent Transfers", admin);
		createRole("MANAGE_PERMANENT_TRANSFERS", "Manage Permanent Transfers", admin);
		
		/* Work Experience */
		createRole("MODIFY_WORKEXPERIENCE", "Modify a Work Experience", admin);
		createRole("DELETE_WORKEXPERIENCE", "Delete a Work Experience", admin);
		createRole("ADD_WORKEXPERIENCE", "Add a Work Experience", admin);
		createRole("MANUAL_EXPERIENCE_CALCULATION", "Manually compute employee's exprience", admin);
		
		/* Evaluations - Αξιολογήσεις Εκπαιδευτικού */
		createRole("MODIFY_EVALUATION", "Modify an Evaluation", admin);
		createRole("DELETE_EVALUATION", "Delete an Evaluation", admin);
		createRole("ADD_EVALUATION", "Add an Evaluation", admin);

		/* Penalties - Ποινές Εκπαιδευτικού */
		createRole("MODIFY_PENALTY", "Modify a Penalty", admin);
		createRole("DELETE_PENALTY", "Delete a Penalty", admin);
		createRole("ADD_PENALTY", "Add a Penalty", admin);
		
		/* Employee Info */
		createRole("VIEW_EMPLOYEE_INFO", "View Employee's Info Data", admin);
		createRole("MODIFY_EMPLOYEE_INFO", "Modify an Employee's Info Data", admin);
		
		/* Rank Info */
		createRole("INSERT_RANK_INFO", "Insert new RankInfo for an Employee", admin);
		
		/* Part-time Employment - Διαχείριση Μειωμένων Ωραρίων */
		createRole("MODIFY_PART_TIME_EMPLOYMENT", "Modify a Part-time Employment", admin);
		createRole("DELETE_PART_TIME_EMPLOYMENT", "Delete a Part-time Employment", admin);
		createRole("ADD_PART_TIME_EMPLOYMENT", "Add a Part-time Employment", admin);

		/* CDR Management - Διαχείριση CDR */
		createRole("RENEW_CDRS", "Allow the renewal of the CDRs", admin);
	}
}
