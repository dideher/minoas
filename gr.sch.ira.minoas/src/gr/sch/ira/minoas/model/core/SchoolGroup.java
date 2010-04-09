package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SCHOOL_GROUP")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SchoolGroup extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
	private Collection<School> schools = new ArrayList<School>();

	@Basic
	@Column(name = "TITLE", unique = true, nullable = false)
	private String title;

	/**
	 * @return the schools
	 */
	public Collection<School> getSchools() {
		return schools;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(Collection<School> schools) {
		this.schools = schools;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
