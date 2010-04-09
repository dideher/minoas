/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.Name;

/**
 * A class representing a school registered in the system.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "SCHOOL")
@Name("school")
@DiscriminatorValue("SCHOOL")
@PrimaryKeyJoinColumn(name = "UNIT_ID")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class School extends Unit {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GROUP_ID", nullable = true)
	private SchoolGroup group;

	@Basic
	@Column(name = "MINISTRY_CODE", length = 7, unique = false, nullable = true)
	private String ministryCode;

	@Basic
	@Column(name = "POINTS", nullable = true)
	private Byte points;

	@Basic
	@Column(name = "REGION")
	private Character regionCode;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "school", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.PERSIST })
	@OrderBy("specialization ASC")
	private Collection<TeachingRequirement> teachingRequirements = new ArrayList<TeachingRequirement>();

	@Enumerated(EnumType.STRING)
	@Column(name = "SCHOOL_TYPE", nullable = true)
	private SchoolType type;

	public void addTeachingRequirement(TeachingRequirement req) {
		req.setSchool(this);
		getTeachingRequirements().add(req);
	}

	/**
	 * @return the group
	 */
	public SchoolGroup getGroup() {
		return group;
	}

	/**
	 * @return the ministryCode
	 */
	public String getMinistryCode() {
		return ministryCode;
	}

	/**
	 * @return the points
	 */
	public Byte getPoints() {
		return points;
	}

	/**
	 * @return the regionCode
	 */
	public Character getRegionCode() {
		return regionCode;
	}

	/**
	 * @return the teachingRequirements
	 */
	public Collection<TeachingRequirement> getTeachingRequirements() {
		return teachingRequirements;
	}

	/**
	 * @return the type
	 */
	public SchoolType getType() {
		return type;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(SchoolGroup group) {
		this.group = group;
	}

	/**
	 * @param ministryCode the ministryCode to set
	 */
	public void setMinistryCode(String ministryCode) {
		this.ministryCode = ministryCode;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(Byte points) {
		this.points = points;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(Character regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @param teachingRequirements the teachingRequirements to set
	 */
	public void setTeachingRequirements(Collection<TeachingRequirement> teachingRequirements) {
		this.teachingRequirements = teachingRequirements;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SchoolType type) {
		this.type = type;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append(getMinistryCode());
		sb.append(" -  ");
		sb.append(getTitle());
		sb.append(" (");
		sb.append(getRegionCode());
		sb.append(")] ");
		return sb.toString();
	}

}
