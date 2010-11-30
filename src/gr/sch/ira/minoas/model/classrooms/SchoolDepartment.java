
package gr.sch.ira.minoas.model.classrooms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;

/**
 * Τμήμα !
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "SCHOOL_DEPARTMENT")
public class SchoolDepartment extends BaseIDModel {

	@ManyToOne
	@JoinColumn(name="SCHOOL_ID", nullable=false)
	private School school;
	
	@ManyToOne
	@JoinColumn(name="SCHOOL_YEAR_ID", nullable=false)
	private SchoolYear schoolYear;
	
	@ManyToOne
	@JoinColumn(name="COURSE_TYPE_ID", nullable=false)
	private CourseType course;
	
	@Basic
	@Column(name="DEPARTMENT_COUNT", nullable=false)
	private Integer departmentCount;
	
	@Basic
	@Column(name="STUDENT_COUNT", nullable=false)
	private Integer studentCount;

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
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
	 * @return the course
	 */
	public CourseType getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(CourseType course) {
		this.course = course;
	}

	/**
	 * @return the departmentCount
	 */
	public Integer getDepartmentCount() {
		return departmentCount;
	}

	/**
	 * @param departmentCount the departmentCount to set
	 */
	public void setDepartmentCount(Integer departmentCount) {
		this.departmentCount = departmentCount;
	}

	/**
	 * @return the studentCount
	 */
	public Integer getStudentCount() {
		return studentCount;
	}

	/**
	 * @param studentCount the studentCount to set
	 */
	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}
}
