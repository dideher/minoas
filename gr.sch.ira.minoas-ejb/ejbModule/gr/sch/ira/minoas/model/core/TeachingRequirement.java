/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */

@Entity
@Table(name = "TEACHING_REQUIREMENT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class TeachingRequirement extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REQUIREMENT_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "SPECIALIZATION_ID")
	private Specialization specialization;
	
	@ManyToOne
	@JoinColumn(name = "SCHOOL_YEAR_ID")
	private SchoolYear schoolYear;
	
	@ManyToOne
	@JoinColumn(name="SCHOOL_ID")
	private Unit school;
	
	@Basic
	@Column(name="HOURS")
	private Integer hours;
	
	
	/**
	 * 
	 */
	public TeachingRequirement() {
		super();
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


	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}


	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}


	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}


	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}


	/**
	 * @return the school
	 */
	public Unit getSchool() {
		return school;
	}


	/**
	 * @param school the school to set
	 */
	public void setSchool(Unit school) {
		this.school = school;
	}


	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}


	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}

}
