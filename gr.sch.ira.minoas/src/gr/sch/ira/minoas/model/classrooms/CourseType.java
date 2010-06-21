package gr.sch.ira.minoas.model.classrooms;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Μάθημα
 * 
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "COURSE_TYPE")
public class CourseType extends BaseIDModel {
	
	
	@Basic
	@Column(name="TITLE", length=128, unique=true, nullable=false)
	private String title;
	
	@ManyToOne
	@JoinColumn(name="SCHOOL_CLASS_ID", nullable=false)
	private SchoolClass schoolClass;
	
	@ManyToMany
	@JoinTable(name="COURSE_FIRST_LEVEL_SPECIALIZATIONS")
	private  Collection<Specialization> firstLevelSpecializationAssignees = new ArrayList<Specialization>();
	
	@ManyToMany
	@JoinTable(name="COURSE_SECOND_LEVEL_SPECIALIZATIONS")
	private  Collection<Specialization> secondLevelSpecializationAssignees = new ArrayList<Specialization>();
	
	@Basic
	@Column(name="TEACHING_HOURS", nullable=true)
	private Integer teachingHours;

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
	 * @return the schoolClass
	 */
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	/**
	 * @param schoolClass the schoolClass to set
	 */
	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	/**
	 * @return the firstLevelSpecializationAssignees
	 */
	public Collection<Specialization> getFirstLevelSpecializationAssignees() {
		return firstLevelSpecializationAssignees;
	}

	/**
	 * @param firstLevelSpecializationAssignees the firstLevelSpecializationAssignees to set
	 */
	public void setFirstLevelSpecializationAssignees(Collection<Specialization> firstLevelSpecializationAssignees) {
		this.firstLevelSpecializationAssignees = firstLevelSpecializationAssignees;
	}

	/**
	 * @return the secondLevelSpecializationAssignees
	 */
	public Collection<Specialization> getSecondLevelSpecializationAssignees() {
		return secondLevelSpecializationAssignees;
	}

	/**
	 * @param secondLevelSpecializationAssignees the secondLevelSpecializationAssignees to set
	 */
	public void setSecondLevelSpecializationAssignees(Collection<Specialization> secondLevelSpecializationAssignees) {
		this.secondLevelSpecializationAssignees = secondLevelSpecializationAssignees;
	}

	/**
	 * @return the teachingHours
	 */
	public Integer getTeachingHours() {
		return teachingHours;
	}

	/**
	 * @param teachingHours the teachingHours to set
	 */
	public void setTeachingHours(Integer teachingHours) {
		this.teachingHours = teachingHours;
	}
	
	
	
}
