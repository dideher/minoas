/*
 * 
 *
 * Copyright (c) 2009 FORTHnet, S.A. All Rights Reserved.
 *
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package gr.sch.ira.minoas.model.preparatory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.EstablishmentLocation;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name="PREP_EST_LCS")
public class EstablishmentLicense extends BaseModel {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="NATURE_TYPE_ID", nullable=false)
	private PreparatoryUnitNature nature;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SCHOOL_YEAR_ID")
	private SchoolYear schoolYear;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PREPARATORY_UNIT_ID", nullable=true)
	private PreparatoryUnit unit;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PREPARATORY_OWNER_ID", nullable=false)
	private PreparatoryOwner owner;
	
	@Column(name="STATUS_TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private EstablishmentLicenseStatusType status;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PREPARATORY_EST_LICENSE_LANGUAGES")
	private Set<TeachingLanguage> teachingLanguages = new HashSet<TeachingLanguage>();
	
	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="license", cascade= { CascadeType.PERSIST, CascadeType.MERGE})
	private Set<EstablishmentLicenseRenewal> renewals = new HashSet<EstablishmentLicenseRenewal>();
	
	@Temporal(TemporalType.DATE)
	@Column(name="REQUEST_DATE", nullable=false)
	private Date requestDate; 
	
	@Basic
	@Column(name="REQUEST_JUDGMENT_DATE", nullable=true)
	private Date requestJudgmentDate;
	
	@Basic 
	@Column(name="REQUEST_JUDGMENT_NUMBER", nullable=true)
	private Integer requestJudgmentNumber;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ESTABLISHMENT_LOCATION_ID", nullable=false)
	private EstablishmentLocation establishmentLocation;
	
	/**
	 * @return the establishmentLocation
	 */
	public EstablishmentLocation getEstablishmentLocation() {
		return establishmentLocation;
	}

	/**
	 * @param establishmentLocation the establishmentLocation to set
	 */
	public void setEstablishmentLocation(EstablishmentLocation establishmentLocation) {
		this.establishmentLocation = establishmentLocation;
	}

	@Basic
	@Column(name="REQUEST_PROTOCOL", nullable=false, unique=true)
	private Integer requestProtocol;
	
	public void addRenewal(EstablishmentLicenseRenewal renewal) {
		this.renewals.add(renewal);
		renewal.setLicense(this);
	}

	/**
	 * @return the teachingLanguages
	 */
	public Set<TeachingLanguage> getTeachingLanguages() {
		return teachingLanguages;
	}

	/**
	 * @param teachingLanguages the teachingLanguages to set
	 */
	public void setTeachingLanguages(Set<TeachingLanguage> teachingLanguages) {
		this.teachingLanguages = teachingLanguages;
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
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the requestJudgmentDate
	 */
	public Date getRequestJudgmentDate() {
		return requestJudgmentDate;
	}

	/**
	 * @param requestJudgmentDate the requestJudgmentDate to set
	 */
	public void setRequestJudgmentDate(Date requestJudgmentDate) {
		this.requestJudgmentDate = requestJudgmentDate;
	}

	/**
	 * @return the requestJudgmentNumber
	 */
	public Integer getRequestJudgmentNumber() {
		return requestJudgmentNumber;
	}

	/**
	 * @param requestJudgmentNumber the requestJudgmentNumber to set
	 */
	public void setRequestJudgmentNumber(Integer requestJudgmentNumber) {
		this.requestJudgmentNumber = requestJudgmentNumber;
	}

	/**
	 * @return the requestProtocol
	 */
	public Integer getRequestProtocol() {
		return requestProtocol;
	}

	/**
	 * @param requestProtocol the requestProtocol to set
	 */
	public void setRequestProtocol(Integer requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	/**
	 * @return the nature
	 */
	public PreparatoryUnitNature getNature() {
		return nature;
	}

	/**
	 * @param nature the nature to set
	 */
	public void setNature(PreparatoryUnitNature nature) {
		this.nature = nature;
	}

	/**
	 * @return the unit
	 */
	public PreparatoryUnit getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(PreparatoryUnit unit) {
		this.unit = unit;
	}

	/**
	 * @return the status
	 */
	public EstablishmentLicenseStatusType getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(EstablishmentLicenseStatusType status) {
		this.status = status;
	}

	/**
	 * @return the owner
	 */
	public PreparatoryOwner getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(PreparatoryOwner owner) {
		this.owner = owner;
	}
	
	
	
	
}
