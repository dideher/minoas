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
import gr.sch.ira.minoas.model.core.Seat;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name="PREPARATORY_EST_LICENSE")
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
	
	@ManyToOne
	@JoinColumn(name="STATUS_ID", nullable=false)
	private EstablishmentLicenseStatus status;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PREPARATORY_EST_LICENSE_LANGUAGES")
	private Set<TeachingLanguage> teachingLanguages = new HashSet<TeachingLanguage>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TYPE_ID", nullable=false)
	private PreparatoryUnitNature type;
	
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
	@JoinColumn(name="SEAT_ID", nullable=false)
	private Seat seat;
	
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
	
	
	
	
}
