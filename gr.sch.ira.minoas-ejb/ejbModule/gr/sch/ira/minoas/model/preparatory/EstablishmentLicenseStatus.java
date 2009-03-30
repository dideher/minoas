package gr.sch.ira.minoas.model.preparatory;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "PREP_LCS_STATUS_TYPE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class EstablishmentLicenseStatus  extends BaseModel {

	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name="LICENSE_STATUS_TYPE", nullable=false, unique=true)
	private EstablishmentLicenseStatusType statusType;
	
	@Basic
	@Column(name = "LICENSE_STATUS_TITLE", length = 64, nullable = false, unique = true)
	private String title; 

	

	

	/**
	 * 
	 */
	public EstablishmentLicenseStatus() {
		super();
	}

	/**
	 * @param establishmentLicenseStatusType
	 * @param title
	 */
	public EstablishmentLicenseStatus(EstablishmentLicenseStatusType establishmentLicenseStatusType, String title) {
		super();
		this.statusType = establishmentLicenseStatusType;
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the type
	 */
	public EstablishmentLicenseStatusType getType() {
		return statusType;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param establishmentLicenseStatusType the type to set
	 */
	public void setType(EstablishmentLicenseStatusType establishmentLicenseStatusType) {
		this.statusType = establishmentLicenseStatusType;
	}

	
}
