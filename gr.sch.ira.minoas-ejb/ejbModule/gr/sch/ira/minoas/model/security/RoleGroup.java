/**
 * 
 */
package gr.sch.ira.minoas.model.security;

import gr.sch.ira.minoas.model.BaseModel;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "ROLE_GROUP")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RoleGroup extends BaseModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Comment for <code>id</code>
	 */
	@Id
	@Column(name = "ID", length = 32, updatable = false)
	private String id;

	/**
	 * Comment for <code>roles</code>
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MINOAS_ROLEGROUP_ROLE")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private List<Role> roles;

	/**
	 * Comment for <code>title</code>
	 */
	@Basic
	@Column(name = "TITLE", nullable = true)
	private String title;

	/**
	 * 
	 */
	public RoleGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param title
	 */
	public RoleGroup(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoleGroup) {
			RoleGroup otherRoleGroup = (RoleGroup) obj;
			if (this.id != null) {
				return this.id.equals(otherRoleGroup.getId());
			} else if (this.id == null && otherRoleGroup.getId() == null)
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<RoleGroup:");
		sb.append(getId());
		sb.append(">");
		return sb.toString();
	}

}
