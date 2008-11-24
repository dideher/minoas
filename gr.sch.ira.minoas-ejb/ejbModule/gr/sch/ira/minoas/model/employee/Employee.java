package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.Address;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Telephone;
import gr.sch.ira.minoas.model.employement.Employment;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "MINOAS_EMPLOYEE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("EMPLOYEE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Employee extends BaseModel {

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the lastSpecialization
	 */
	public Specialization getLastSpecialization() {
		return lastSpecialization;
	}

	/**
	 * @param lastSpecialization the lastSpecialization to set
	 */
	public void setLastSpecialization(Specialization lastSpecialization) {
		this.lastSpecialization = lastSpecialization;
	}

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Address address;

	@Basic
	@Column(name = "BIRTH_DAY", nullable=true)
	private Date dateOfBirth;

	@Basic
	@Column(name = "FATHER_NAME", nullable = true, length = 15)
	private String fatherName;

	@Basic
	@Column(name = "FIRST_NAME", nullable = false, length = 15)
	private String firstName;

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	@Column(name = "ID_NUMBER", unique = false, nullable=true, length = 10)
	private String idNumber;

	@Basic
	@Column(name = "LAST_NAME", nullable = false, length = 35)
	private String lastName;

	@Basic
	@Column(name="LEGACY_CODE", nullable=false, updatable=false, unique=true, length=10)
	private String legacyCode;
	
	@Basic
	@Column(name="MAN", nullable=true)
	private Boolean man;

	@Basic
	@Column(name = "MOTHER_NAME", nullable = true, length = 15)
	private String motherName;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="MINOAS_EMPLOYEE_TELEPHONES")
	private List<Telephone> telephones;
	
	@Basic
	@Column(name = "VAT_NUMBER", unique = false, nullable = true, length = 10)
	private String vatNumber;
	
	@OneToOne
	@JoinColumn(name="CURRENT_EMPLOYMENT_ID", nullable=true)
	private Employment currentEmployment;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="PYSDE_ID", nullable=false, updatable=true)
	private PYSDE currentPYSDE;
	
	/**
	 * Each employee have a specialization, which is actually the last employment's 
	 * specialization.
	 */
	@ManyToOne
	@JoinColumn(name="LAST_SPECIALIZATION_ID", nullable=true)
	private Specialization lastSpecialization;
	
	/**
	 * An employee can be active or not. 
	 */
	@Basic
	@Column(name="IS_ACTIVE", nullable=true)
	private Boolean active;
	
	
	@OneToMany(mappedBy="employee", fetch=FetchType.LAZY)
	private Set<Employment> employments;
	
	@SuppressWarnings("unused")
	@Version
	private Long version;
	
	/**
	 * 
	 */
	public Employee() {
		super();
	}
	
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
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the legacyCode
	 */
	public String getLegacyCode() {
		return legacyCode;
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
	public List<Telephone> getTelephones() {
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
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param legacyCode the legacyCode to set
	 */
	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
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
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getLastName());
		sb.append(" ");
		sb.append(getFirstName());
		sb.append(" ");
		sb.append(getFatherName());
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * @return the employments
	 */
	public Set<Employment> getEmployments() {
		return employments;
	}

	/**
	 * @param employments the employments to set
	 */
	public void setEmployments(Set<Employment> employments) {
		this.employments = employments;
	}

	/**
	 * @return the currentEmployment
	 */
	public Employment getCurrentEmployment() {
		return currentEmployment;
	}

	/**
	 * @param currentEmployment the currentEmployment to set
	 */
	public void setCurrentEmployment(Employment currentEmployment) {
		this.currentEmployment = currentEmployment;
	}

	/**
	 * @return the currentPYSDE
	 */
	public PYSDE getCurrentPYSDE() {
		return currentPYSDE;
	}

	/**
	 * @param currentPYSDE the currentPYSDE to set
	 */
	public void setCurrentPYSDE(PYSDE currentPYSDE) {
		this.currentPYSDE = currentPYSDE;
	}

	
}
