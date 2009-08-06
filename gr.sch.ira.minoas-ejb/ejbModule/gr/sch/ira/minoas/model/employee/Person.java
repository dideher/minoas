package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.AbstractArchivableEntity;
import gr.sch.ira.minoas.model.core.Address;
import gr.sch.ira.minoas.model.core.Telephone;

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

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public abstract class Person extends AbstractArchivableEntity {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

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
	@Column(name = "FATHER_SURNAME", nullable = true, length = 35)
	private String fatherSurname;

	@Basic
	@Column(name = "FIRST_NAME", nullable = false, length = 25)
	private String firstName;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	@Column(name = "ID_NUMBER", unique = false, nullable = true, length = 10)
	private String idNumber;

	@Basic
	@Column(name = "ID_NUMBER_AUTHORITY", nullable = true, length = 64)
	private String idNumberAuthority;

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

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "MINOAS_EMPLOYEE_TELEPHONES")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Collection<Telephone> telephones = new ArrayList<Telephone>();

	@Basic
	@Column(name = "VAT_NUMBER", nullable=true, length = 10)
	private String vatNumber;

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
	 * @return the fatherSurname
	 */
	public String getFatherSurname() {
		return fatherSurname;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
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
	 * @return the idNumberAuthority
	 */
	public String getIdNumberAuthority() {
		return idNumberAuthority;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	public Boolean getMan() {
		return isMan();
	}

	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}

	/**
	 * @return the motherSurname
	 */
	public String getMotherSurname() {
		return motherSurname;
	}

	/**
	 * @return the telephones
	 */
	public Collection<Telephone> getTelephones() {
		return telephones;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @return the man
	 */
	public Boolean isMan() {
		return man;
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
	 * @param fatherSurname the fatherSurname to set
	 */
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	 * @param idNumberAuthority the idNumberAuthority to set
	 */
	public void setIdNumberAuthority(String idNumberAuthority) {
		this.idNumberAuthority = idNumberAuthority;
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
	 * @param motherSurname the motherSurname to set
	 */
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append(" [");
		if (lastName != null) {
			builder.append("lastName=");
			builder.append(lastName);
			builder.append(", ");
		}
		if (firstName != null) {
			builder.append("fistName=");
			builder.append(firstName);
			builder.append(", ");
		}
		if (fatherName != null) {
			builder.append("fatherName=");
			builder.append(fatherName);
			builder.append(", ");
		}
		if (motherName != null) {
			builder.append("motherName=");
			builder.append(motherName);
		}
		if (idNumber != null) {
			builder.append("idNumber=");
			builder.append(idNumber);
		}
		builder.append("]");
		return builder.toString();
	}

}
