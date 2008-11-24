/**
 * 
 */
package gr.sch.ira.minoas.session.security;

import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.model.security.RoleGroup;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author slavikos
 * 
 */
@Name("roleGroupManagement")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, IRoleGroupManagement.class })
@Scope(ScopeType.CONVERSATION)
public class RoleGroupManagementBean extends BaseStatefulSeamComponentImpl implements IRoleGroupManagement {

	@In(value="coreSearching")
	private CoreSearching coreSearching;

	@In
	private EntityManager minoasDatabase;

	@In(required = false)
	@Out(required = false)
	private RoleGroup newRoleGroup;

	@DataModelSelection
	@Out(value = "selectedRoleGroup", required = false)
	private RoleGroup roleGroup;

	@DataModel
	private Collection<RoleGroup> roleGroups;

	private String searchString;

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#constructNewRoleGroup()
	 */
	@Factory("newRoleGroup")
	public void constructNewRoleGroup() {
		info("constructing new instance of empty role group as requested.");
		this.newRoleGroup = new RoleGroup("", "");
	}

	public String getSearchString() {
		return searchString;
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#removeRoleGroup()
	 */
	@End
	public void removeRoleGroup() {
		info("about to remove role group #0.", roleGroup);
		RoleGroup role_to_remove = minoasDatabase.merge(this.roleGroup);
		minoasDatabase.remove(role_to_remove);
		minoasDatabase.flush();
		info("role group #0 has been removed.", this.roleGroup);
		search();
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#saveRoleGroup()
	 */
	@End
	public void saveRoleGroup() {
		info("about to save new role group #0", this.newRoleGroup);
		RoleGroup existing_role_group = coreSearching.findRoleGroup(newRoleGroup.getId());
		if (existing_role_group == null) {
			minoasDatabase.persist(this.newRoleGroup);

			info("role group #0, successfully saved, with totally #1 roles registered.", this.newRoleGroup,
					this.newRoleGroup.getRoles().size());
			constructNewRoleGroup();
			search();
		}
		else {
			warn("ignoring save request of role #0, since that role already exists", this.newRoleGroup);
			facesMessages.add("role fdsf", newRoleGroup.getId());
		}

	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#search()
	 */
	@Factory("roleGroups")
	@Begin(join = true)
	public void search() {
		this.roleGroups = coreSearching.searchRoleGroups(getSearchString());
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#selectRoleGroup()
	 */
	@Begin(join = true)
	public void selectRoleGroup() {
		info("role group #0 selected for management", this.roleGroup);
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#setSearchString(java.lang.String)
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupManagement#updateRoleGroup()
	 */
	@End
	public void updateRoleGroup() {
		info("trying to update existing #0 role group", this.roleGroup);
		for (Role role : roleGroup.getRoles()) {
			minoasDatabase.merge(role);
		}
		minoasDatabase.merge(roleGroup);
		minoasDatabase.flush();
		search();
	}

}
