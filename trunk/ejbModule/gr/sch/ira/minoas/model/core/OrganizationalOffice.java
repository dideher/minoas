/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * @author
 * 
 */
@Entity
@Table(name = "MINOAS_ORGANIZATIONAL_OFFICE")
public class OrganizationalOffice extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 1)
	private String id;

	@Column(name = "TITLE")
	private String title;

	@SuppressWarnings("unused")
	@Version
	private Long version;

	// private Collection<School> schools;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	// /**
	// * @return the schools
	// */
	// public Collection<School> getSchools() {
	// return schools;
	// }
	// /**
	// * @param schools the schools to set
	// */
	// public void setSchools(Collection<School> schools) {
	// this.schools = schools;
	// }
}
