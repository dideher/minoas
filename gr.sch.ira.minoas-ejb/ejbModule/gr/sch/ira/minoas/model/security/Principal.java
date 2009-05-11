/**
 * 
 */
package gr.sch.ira.minoas.model.security;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

/**
 * Represents an authorized user.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * */
@Table(name = "PRINCIPAL")
@Name("principal")
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Principal extends BaseModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "EMAIL", length = 60, nullable = true)
	private String email;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PRINCIPAL_ROLE", joinColumns = @JoinColumn(name = "PRINCIPAL_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles = new HashSet<Role>();

	@Basic
	@Column(updatable = false, name = "USERNAME", length = 16, nullable = false, unique = true)
	private String username;

	/**
	 * 
	 */
	public Principal() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @param username
	 * @param realName
	 * @param password
	 */
	public Principal(String username, String realName, String password) {
		super();
		this.username = username;
		this.realName = realName;
		this.password = password;
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
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Role addRole(Role aRole) {
		getRoles().add(aRole);
		return aRole;
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
