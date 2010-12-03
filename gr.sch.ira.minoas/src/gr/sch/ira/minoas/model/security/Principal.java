/**
 * 
 */
package gr.sch.ira.minoas.model.security;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.seam.annotations.Name;

/**
 * Represents an authorized user.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * */
@Table(name = "PRINCIPAL")
@Name("principal")
@Entity
public class Principal extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active = Boolean.TRUE;

	@Basic
	@Column(name = "EMAIL", length = 60, nullable = true)
	private String email;

	@Basic
	@ManyToOne
	@JoinColumn(name = "OFFICE_ID", nullable = true)
	private OrganizationalOffice office;

	@Basic
	@Column(name = "PASSWORD", nullable = false, length = 64)
	private String password;

	@Basic
	@Column(name = "REAL_NAME", length = 90, nullable = false)
	private String realName;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "PRINCIPAL_ROLE", joinColumns = @JoinColumn(name = "PRINCIPAL_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles = new ArrayList<Role>();

	@Basic
	@Column(updatable = false, name = "USERNAME", length = 16, nullable = false, unique = true)
	private String username;

	/**
	 * 
	 */
	public Principal() {
		super();

	}

	/**
	 * @param username
	 * @param realName
	 * @param password
	 */
	public Principal(String username, String password, String realName) {
		super();
		this.username = username;
		this.realName = realName;
		this.password = password;
		this.active = Boolean.TRUE;
	}

	public Role addRole(Role aRole) {
		if (!roles.contains(aRole)) {
			getRoles().add(aRole);
		}
		return aRole;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the office
	 */
	public OrganizationalOffice getOffice() {
		return office;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param office the office to set
	 */
	public void setOffice(OrganizationalOffice office) {
		this.office = office;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getUsername());
		sb.append("/");
		sb.append(getRealName());
		sb.append("]");
		return sb.toString();
	}

}
