package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "SPECIALIZATION_GROUP", uniqueConstraints = { @UniqueConstraint(columnNames = { "TITLE", "SCHOOL_YEAR_ID" }) })
public class SpecializationGroup extends BaseIDModel implements Comparable<SpecializationGroup> {

	

	@ManyToOne
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinTable(name = "SPECIALIZATION_GROUP_SPECIALIZATIONS", joinColumns = { @JoinColumn(name = "SPECIALIZATION_GROUP_ID") }, inverseJoinColumns = { @JoinColumn(name = "SPECIALIZATION_ID") })
	private List<Specialization> specializations = new ArrayList<Specialization>();

	@Basic
	@Column(name = "TITLE", length = 128, nullable = false)
	private String title;

	/**
	 * 
	 */
	public SpecializationGroup() {
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the specializations
	 */
	public List<Specialization> getSpecializations() {
		return specializations;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param specializations the specializations to set
	 */
	public void setSpecializations(List<Specialization> specializations) {
		this.specializations = specializations;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpecializationGroup [");
		if (title != null) {
			builder.append("title=");
			builder.append(title);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SpecializationGroup o) {
		return title.compareTo(o.getTitle());
	}

}
