package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employement.Employment;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "MINOAS_EMPLOYEE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Employee extends Person {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An employee can be active or not. 
	 */
	@Basic
	@Column(name="IS_ACTIVE", nullable=true)
	private Boolean active;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CURRENT_EMPLOYMENT_ID", nullable=true)
	private Employment currentEmployment;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="PYSDE_ID", nullable=false, updatable=true)
	private PYSDE currentPYSDE;

	@OneToMany(mappedBy="employee", fetch=FetchType.LAZY)
	private Set<Employment> employments;

	/**
	 * Each employee have a specialization, which is actually the last employment's 
	 * specialization.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LAST_SPECIALIZATION_ID", nullable=true)
	private Specialization lastSpecialization;
	
	@Basic
	@Column(name="LEGACY_CODE", nullable=false, updatable=false, unique=true, length=10)
	private String legacyCode;

	@OneToOne(optional=true, fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn(name="ID")
	//@JoinColumn(name="REGULAR_EMPLOYMEE_DETAIL_ID", nullable=true)
	private RegularEmployeeInfo regularEmployeeInfo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="EMPLOYEE_TYPE", nullable=false, updatable=false)
	private EmployeeType type;
	
	/**
	 * 
	 */
	public Employee() {
		super();
	}
	
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	
	
	/**
	 * @return the currentEmployment
	 */
	public Employment getCurrentEmployment() {
		return currentEmployment;
	}
	
	/**
	 * @return the currentPYSDE
	 */
	public PYSDE getCurrentPYSDE() {
		return currentPYSDE;
	}
	
	/**
	 * @return the employments
	 */
	public Set<Employment> getEmployments() {
		return employments;
	}

	/**
	 * @return the lastSpecialization
	 */
	public Specialization getLastSpecialization() {
		return lastSpecialization;
	}

	/**
	 * @return the legacyCode
	 */
	public String getLegacyCode() {
		return legacyCode;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param currentEmployment the currentEmployment to set
	 */
	public void setCurrentEmployment(Employment currentEmployment) {
		this.currentEmployment = currentEmployment;
	}

	/**
	 * @param currentPYSDE the currentPYSDE to set
	 */
	public void setCurrentPYSDE(PYSDE currentPYSDE) {
		this.currentPYSDE = currentPYSDE;
	}

	/**
	 * @param employments the employments to set
	 */
	public void setEmployments(Set<Employment> employments) {
		this.employments = employments;
	}

	/**
	 * @param lastSpecialization the lastSpecialization to set
	 */
	public void setLastSpecialization(Specialization lastSpecialization) {
		this.lastSpecialization = lastSpecialization;
	}

	/**
	 * @param legacyCode the legacyCode to set
	 */
	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
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
	 * @return the regularDetail
	 */
	public RegularEmployeeInfo getRegularDetail() {
		return regularEmployeeInfo;
	}

	/**
	 * @param regularDetail the regularDetail to set
	 */
	public void setRegularDetail(RegularEmployeeInfo regularDetail) {
		this.regularEmployeeInfo = regularDetail;
	}

	/**
	 * @return the type
	 */
	public EmployeeType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EmployeeType type) {
		this.type = type;
	}

	
}
