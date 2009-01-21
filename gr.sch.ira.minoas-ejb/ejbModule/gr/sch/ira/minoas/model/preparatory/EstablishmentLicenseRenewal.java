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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.SchoolYear;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name="PREPARATORY_EST_LICENSE_RENEWAL")
public class EstablishmentLicenseRenewal extends BaseModel {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SCHOOL_YEAR_ID")
	private SchoolYear schoolYear;
	
	@Basic
	@Column(name="REQUEST_DATE", nullable=false, updatable=false)
	@Temporal(TemporalType.DATE)
	private Date requestDate;
	
	@Basic
	@Column(name="REQUEST_PROTOCOL", nullable=false, updatable=false)
	private Integer requestProtocol;
	
	@Basic
	@Column(name="RENEWAL_DATE", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date renewalDate;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="LICENSE_ID", nullable=false, updatable=false)
	private EstablishmentLicense license;
	/**
	 * 
	 */
	public EstablishmentLicenseRenewal() {
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the renewalDate
	 */
	public Date getRenewalDate() {
		return renewalDate;
	}
	/**
	 * @param renewalDate the renewalDate to set
	 */
	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}
	/**
	 * @return the license
	 */
	public EstablishmentLicense getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(EstablishmentLicense license) {
		this.license = license;
	}
	
	

}
