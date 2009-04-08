package gr.sch.ira.minoas.model.preparatory;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.EstablishmentLocation;
import gr.sch.ira.minoas.model.core.SchoolYear;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "PREP_EST_LCS")
public class EstablishmentLicense extends BaseModel {

	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "STATUS_TYPE", nullable = false)
	private EstablishmentLicenseStatusType statusType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTABLISHMENT_LOCATION_ID", nullable = false)
	private EstablishmentLocation establishmentLocation;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NATURE_TYPE", nullable = false)
	private PreparatoryUnitNatureType natureType;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PREPARATORY_OWNER_ID", nullable = false)
	private PreparatoryOwner owner;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "license", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<EstablishmentLicenseRenewal> renewals = new ArrayList<EstablishmentLicenseRenewal>();

	@Temporal(TemporalType.DATE)
	@Column(name = "REQUEST_DATE", nullable = false)
	private Date requestDate;

	@Basic
	@Column(name = "REQUEST_JUDGMENT_DATE", nullable = true)
	private Date requestJudgmentDate;

	@Basic
	@Column(name = "REQUEST_JUDGMENT_NUMBER", nullable = true)
	private Integer requestJudgmentNumber;

	@Basic
	@Column(name = "REQUEST_PROTOCOL", nullable = false, unique = true)
	private Integer requestProtocol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_YEAR_ID")
	private SchoolYear schoolYear;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PREPARATORY_EST_LICENSE_LANGUAGES")
	private List<TeachingLanguage> teachingLanguages = new ArrayList<TeachingLanguage>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PREPARATORY_UNIT_ID", nullable = true)
	private PreparatoryUnit unit;

	public void addRenewal(EstablishmentLicenseRenewal renewal) {
		this.renewals.add(renewal);
		renewal.setLicense(this);
	}

	/**
	 * @return the establishmentLocation
	 */
	public EstablishmentLocation getEstablishmentLocation() {
		return establishmentLocation;
	}

	/**
	 * @return the natureType
	 */
	public PreparatoryUnitNatureType getNatureType() {
		return natureType;
	}

	/**
	 * @return the owner
	 */
	public PreparatoryOwner getOwner() {
		return owner;
	}

	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * @return the requestJudgmentDate
	 */
	public Date getRequestJudgmentDate() {
		return requestJudgmentDate;
	}

	/**
	 * @return the requestJudgmentNumber
	 */
	public Integer getRequestJudgmentNumber() {
		return requestJudgmentNumber;
	}

	/**
	 * @return the requestProtocol
	 */
	public Integer getRequestProtocol() {
		return requestProtocol;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the statusType
	 */
	public EstablishmentLicenseStatusType getStatusType() {
		return statusType;
	}

	/**
	 * @return the teachingLanguages
	 */
	public List<TeachingLanguage> getTeachingLanguages() {
		return teachingLanguages;
	}

	/**
	 * @return the unit
	 */
	public PreparatoryUnit getUnit() {
		return unit;
	}

	/**
	 * @param establishmentLocation the establishmentLocation to set
	 */
	public void setEstablishmentLocation(EstablishmentLocation establishmentLocation) {
		this.establishmentLocation = establishmentLocation;
	}

	/**
	 * @param natureType the natureType to set
	 */
	public void setNatureType(PreparatoryUnitNatureType natureType) {
		this.natureType = natureType;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(PreparatoryOwner owner) {
		this.owner = owner;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @param requestJudgmentDate the requestJudgmentDate to set
	 */
	public void setRequestJudgmentDate(Date requestJudgmentDate) {
		this.requestJudgmentDate = requestJudgmentDate;
	}

	/**
	 * @param requestJudgmentNumber the requestJudgmentNumber to set
	 */
	public void setRequestJudgmentNumber(Integer requestJudgmentNumber) {
		this.requestJudgmentNumber = requestJudgmentNumber;
	}

	/**
	 * @param requestProtocol the requestProtocol to set
	 */
	public void setRequestProtocol(Integer requestProtocol) {
		this.requestProtocol = requestProtocol;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param establishmentLicenseStatusType the statusType to set
	 */
	public void setStatusType(EstablishmentLicenseStatusType establishmentLicenseStatusType) {
		this.statusType = establishmentLicenseStatusType;
	}

	/**
	 * @param teachingLanguages the teachingLanguages to set
	 */
	public void setTeachingLanguages(List<TeachingLanguage> teachingLanguages) {
		this.teachingLanguages = teachingLanguages;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(PreparatoryUnit unit) {
		this.unit = unit;
	}

}
