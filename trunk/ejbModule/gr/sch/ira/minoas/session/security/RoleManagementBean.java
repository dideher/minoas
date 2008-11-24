/**
 * 
 */
package gr.sch.ira.minoas.session.security;

import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("roleManagement")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, IRoleManagement.class })
@Scope(ScopeType.CONVERSATION)
public class RoleManagementBean extends BaseStatefulSeamComponentImpl implements IRoleManagement {

	private String searchString;

	@In(value="coreSearching")
	private CoreSearching coreSearching;

	@DataModel
	private Collection<Role> roles;

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleManagement#selectRole()
	 */
	public void selectRole() {
		this.role = minoasDatabase.merge(this.role);
		info("role #0 selected for management", this.role);
	}

	@DataModelSelection()
	@Out(value = "selectedRole", required = false)
	private Role role;

	@In(required = false)
	@Out(required = false)
	private Role newRole;

	@In
	private EntityManager minoasDatabase;

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleManagement#removeRole(gr.sch.ira.minoas.model.security.Role)
	 */
	public void removeRole() {
		info("trying to remove role #0 from systminoasDatabase.", role);
		minoasDatabase.remove(this.role);
		info("removed succesfully role #0 from systminoasDatabase.", role);
		search();
	}

	public void saveRole() {
		info("about to save new role #0", this.newRole);
		Role existing_role = coreSearching.findRole(this.newRole.getId());
		if (existing_role == null) {
			minoasDatabase.persist(this.newRole);
			info("role #0, successfully saved.", this.newRole);
			minoasDatabase.flush();
			constructNewRole();
			search();
		}
		else {
			warn("ignoring save request of role #0, since that role already exists", this.newRole);
			facesMessages.add("role fdsf", newRole.getId());

		}
	}

	@Factory("roles")
	@Begin(join = true)
	public void search() {
		this.roles = coreSearching.searchRoles(getSearchString());

	}

	@Factory("newRole")
	public void constructNewRole() {
		info("constructing new instance of role");
		this.newRole = new Role("", "");
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
