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

package gr.sch.ira.minoas.model.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.AbstractArchivableEntity;
import gr.sch.ira.minoas.model.core.Address;
import gr.sch.ira.minoas.model.core.Telephone;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public abstract class Person extends AbstractArchivableEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ADDRESS_ID", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Address address;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DAY", nullable = true)
	private Date dateOfBirth;
	
	@Basic
	@Column(name = "FATHER_NAME", nullable = true, length = 25)
	private String fatherName;
	
	@Basic
	@Column(name = "FIRST_NAME", nullable = false, length = 25)
	private String firstName;
	
	@Basic
	@Column(name = "LAST_NAME", nullable = false, length = 35)
	private String lastName;
	
	@Basic
	@Column(name = "MAN", nullable = true)
	private Boolean man;
	
	@Basic
	@Column(name = "MOTHER_NAME", nullable = true, length = 25)
	private String motherName;
	
	@Basic
	@Column(name = "MOTHER_SURNAME", nullable = true, length = 35)
	private String motherSurname;
	
	@Basic
	@Column(name = "FATHER_SURNAME", nullable = true, length = 35)
	private String fatherSurname;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "MINOAS_EMPLOYEE_TELEPHONES")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Collection<Telephone> telephones = new ArrayList<Telephone>();
	
	@Basic
	@Column(name = "VAT_NUMBER", unique = false, nullable = true, length = 10)
	private String vatNumber;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	@Column(name = "ID_NUMBER", unique = false, nullable = true, length = 10)
	private String idNumber;
	
	@Basic
	@Column(name="ID_NUMBER_AUTHORITY", nullable=true, length=64)
	private String idNumberAuthority;

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}

	/**
	 * @return the telephones
	 */
	public Collection<Telephone> getTelephones() {
		return telephones;
	}

	/**
	 * @return the man
	 */
	public Boolean isMan() {
		return man;
	}

	public Boolean getMan() {
		return isMan();
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param man the man to set
	 */
	public void setMan(Boolean man) {
		this.man = man;
	}

	/**
	 * @param motherName the motherName to set
	 */
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @param telephones the telephones to set
	 */
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @return the idNumberAuthority
	 */
	public String getIdNumberAuthority() {
		return idNumberAuthority;
	}

	/**
	 * @param idNumberAuthority the idNumberAuthority to set
	 */
	public void setIdNumberAuthority(String idNumberAuthority) {
		this.idNumberAuthority = idNumberAuthority;
	}

	/**
	 * @return the motherSurname
	 */
	public String getMotherSurname() {
		return motherSurname;
	}

	/**
	 * @param motherSurname the motherSurname to set
	 */
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}

	/**
	 * @return the fatherSurname
	 */
	public String getFatherSurname() {
		return fatherSurname;
	}

	/**
	 * @param fatherSurname the fatherSurname to set
	 */
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}

}
