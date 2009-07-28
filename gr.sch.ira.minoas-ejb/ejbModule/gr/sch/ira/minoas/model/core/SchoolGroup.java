
package gr.sch.ira.minoas.model.core;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseModel;

@Entity
@Table(name = "SCHOOL_GROUP")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SchoolGroup extends BaseModel {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Basic
	@Column(name="TITLE", unique=true, nullable=false)
	private String title;
	
	@OneToMany(mappedBy="group", fetch=FetchType.EAGER)
	private Collection<School> schools = new ArrayList<School>();

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the schools
	 */
	public Collection<School> getSchools() {
		return schools;
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(Collection<School> schools) {
		this.schools = schools;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
