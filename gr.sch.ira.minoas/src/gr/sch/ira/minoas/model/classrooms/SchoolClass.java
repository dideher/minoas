

package gr.sch.ira.minoas.model.classrooms;

import java.util.ArrayList;
import java.util.Collection;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Τάξη
 * 
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "SCHOOL_CLASS")
public class SchoolClass extends BaseIDModel {

	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="SCHOOL_TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private SchoolType schoolType;
	
	@Basic
	@Column(name="SORT_ORDER", nullable=true)
	private Integer sortOrder;
	
	@Basic
	@Column(name="TITLE", length=128, unique=true, nullable=false)
	private String title;

	@OneToMany(mappedBy="schoolClass")
	private Collection<CourseType> courses = new ArrayList<CourseType>();
	/**
	 * @return the schoolType
	 */
	public SchoolType getSchoolType() {
		return schoolType;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param schoolType the schoolType to set
	 */
	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the courses
	 */
	public Collection<CourseType> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(Collection<CourseType> courses) {
		this.courses = courses;
	}
	

}
