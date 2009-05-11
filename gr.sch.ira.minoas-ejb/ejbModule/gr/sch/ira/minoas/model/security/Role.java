/**
 * 
 */
package gr.sch.ira.minoas.model.security;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.AutoCreate;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "ROLE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Role extends BaseModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name = "ID", length = 32, updatable = false)
	private Long id;

	@Basic
	@Column(name = "NAME", nullable = false, length = 32, unique=true )
	private String name;
	
	@Basic
	@Column(name="DESCR", nullable=true, length=250)
	private String description;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 */
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * @param name
	 * @param description
	 */
	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			Role otherRole = (Role) obj;
			if (this.name != null) {
				return this.name.equals(otherRole.getName());
			} else if (this.name == null && otherRole.getName() == null)
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * @return the title
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/**
	 * @param title the title to set
	 */
	public void setName(String title) {
		this.name = title;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Role:");
		sb.append(getName());
		sb.append(">");
		return sb.toString();
	}
}
