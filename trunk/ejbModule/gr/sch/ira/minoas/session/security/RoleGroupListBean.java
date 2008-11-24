/**
 * 
 */
package gr.sch.ira.minoas.session.security;

import gr.sch.ira.minoas.model.security.RoleGroup;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Local;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("availableRoleGroups")
@Scope(ScopeType.EVENT)
@Restrict("#{identity.loggedIn}")
@Stateful
@Local( { IBaseStatefulSeamComponent.class, IRoleGroupList.class })
public class RoleGroupListBean extends BaseStatefulSeamComponentImpl implements IRoleGroupList {

	@In(value="coreSearching")
	private CoreSearching coreSearching;

	public List<RoleGroup> roleGroupList;

	public Map<String, RoleGroup> roleGroupMap;

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#create()
	 */
	@Override
	public void create() {
		super.create();
		this.roleGroupList = coreSearching.getAvailableRoleGroups();
		TreeMap<String, RoleGroup> roleGroupMapMap = new TreeMap<String, RoleGroup>();
		for (RoleGroup office : this.roleGroupList) {
			roleGroupMapMap.put(office.getTitle(), office);
		}
		this.roleGroupMap = roleGroupMapMap;
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupList#getAsList()
	 */
	public List<RoleGroup> getAsList() {
		return this.roleGroupList;
	}

	/**
	 * @see gr.sch.ira.minoas.session.security.IRoleGroupList#getAsMap()
	 */
	public Map<String, RoleGroup> getAsMap() {
		return this.roleGroupMap;
	}

}
