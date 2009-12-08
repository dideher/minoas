/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Represent a school year.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * */
@Entity
@Table(name = "SCHOOL_YEAR")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SchoolYear extends BaseIDModel {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "IS_CURRENT_YEAR", nullable = false, updatable = true)
	private boolean currentSchoolYear;

	@Basic
	@Column(name = "DESCRIPTION", length = 64, nullable = true)
	private String description;

	@Basic
	@Column(name = "TITLE", length = 32, nullable = false, updatable = true, unique = true)
	private String title;

	@Basic
	@Column(name = "YEAR", nullable = false, unique = true)
	private Integer year;

	/**
	 * 
	 */
	public SchoolYear() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 */
	public SchoolYear(String title) {
		super();
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public Date getSchoolYearStart() {
		return getTeachingSchoolYearStart();
	}

	public Date getSchoolYearStop() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getYear() + 1);
		cal.set(Calendar.MONTH, Calendar.AUGUST);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();

	}

	public Date getTeachingSchoolYearStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getYear());
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public Date getTeachingSchoolYearStop() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getYear() + 1);
		cal.set(Calendar.MONTH, Calendar.JUNE);
		cal.set(Calendar.DAY_OF_MONTH, 30);
		return cal.getTime();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @return the currentSchoolYear
	 */
	public boolean isCurrentSchoolYear() {
		return currentSchoolYear;
	}

	/**
	 * @param currentSchoolYear the currentSchoolYear to set
	 */
	public void setCurrentSchoolYear(boolean currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getId());
		sb.append("/");
		sb.append(getTitle());
		sb.append("]");
		return sb.toString();
	}

}
