/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author
 * 
 */
@Entity
@Table(name = "ORGANIZATIONAL_OFFICE")
public class OrganizationalOffice extends BaseIDModel  {

	private static final long serialVersionUID = 1L;


	@Column(name = "TITLE", unique=true, nullable=false)
	private String title;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="office")
	private Collection<Unit> units = new ArrayList<Unit>();


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
	 * @return the units
	 */
	public Collection<Unit> getUnits() {
		return units;
	}


	/**
	 * @param units the units to set
	 */
	public void setUnits(Collection<Unit> units) {
		this.units = units;
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
