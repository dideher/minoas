/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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

	@Basic
	@Column(name = "MINISTRY_CODE", length = 7, unique = false, nullable = true)
	private String ministryCode;

	@Basic
	@Column(name = "POINTS", nullable = true)
	private Byte points;

	@Basic
	@Column(name = "REGION")
	private Character regionCode;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="school")
	private Collection<TeachingRequirement> teachingRequirements = new ArrayList<TeachingRequirement>();

	/**
	 * @return the teachingRequirements
	 */
	public Collection<TeachingRequirement> getTeachingRequirements() {
		return teachingRequirements;
	}

	/**
	 * @param teachingRequirements the teachingRequirements to set
	 */
	public void setTeachingRequirements(Collection<TeachingRequirement> teachingRequirements) {
		this.teachingRequirements = teachingRequirements;
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
