package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * This class represents an employment. Every employment is binded to a concrete
 * {@link Employee} in the system and always references a {@link SchoolYear}.
 * <br />
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "EMPLOYMENT")
public class Employment extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An employment may or may not be active.
	 */
	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active;
	
	@Basic
	@Column(name="HOME_BASED", nullable = true)
	private Boolean homeBased = Boolean.FALSE;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "DEPUTY_EMPLOYMENT_INFO_ID", nullable = true)
	private DeputyEmploymentInfo deputyEmploymentInfo;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Basic
	@Column(name = "ESTABLISHED_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date established;

	@Basic
	@Column(name = "FINAL_WORKING_HOURS", nullable = true)
	private Integer finalWorkingHours;

	@Basic
	@Column(name = "MANDATORY_WORK_HRS", nullable = false)
	private Integer mandatoryWorkingHours;

	@OneToOne
	@JoinColumn(name = "SCHOOL_ID", nullable = false)
	private School school;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "SECONDMENT_ID", nullable = true)
	private Secondment secondment;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ALLOCATION_ID", nullable = true)
	private ServiceAllocation serviceAllocation;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPECIALIZATION_ID", nullable = false, updatable = false)
	private Specialization specialization;

	/**
	 * An employment may be superseded by another employment
	 */
	@OneToOne
	@JoinColumn(name = "SUPERSEDED_BY_ID", nullable = true)
	private Employment supersededBy;

	@Basic
	@Column(name = "IS_TEMPORAR", nullable = true)
	private Boolean temporar;

	@Basic
	@Column(name = "TERMINATED_DATE", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date terminated;

	@Enumerated(EnumType.STRING)
	@Column(name = "EMPLOYMENT_TYPE", nullable = false, updatable = false)
	private EmploymentType type;

	@Basic
	@Column(name = "WORK_HRS_DECR", nullable = true)
	private Integer workingHoursDecrement;

	@Basic
	@Column(name = "WORK_HRS_DECR_REASON", nullable = true)
	private String workingHoursDecrementReason;

	/**
	 * 
	 */
	public Employment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the deputyEmploymentInfo
	 */
	public DeputyEmploymentInfo getDeputyEmploymentInfo() {
		return deputyEmploymentInfo;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
	 * @return the finalWorkingHours
	 */
	public Integer getFinalWorkingHours() {
		return finalWorkingHours;
	}

	/**
	 * @return the mandatoryWorkingHours
	 */
	public Integer getMandatoryWorkingHours() {
		return mandatoryWorkingHours;
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	public Secondment getSecondment() {
		return secondment;
	}

	/**
	 * @return the serviceAllocation
	 */
	public ServiceAllocation getServiceAllocation() {
		return serviceAllocation;
	}

	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}

	/**
	 * @return the supersededBy
	 */
	public Employment getSupersededBy() {
		return supersededBy;
	}

	/**
	 * @return the temporar
	 */
	public Boolean getTemporar() {
		return temporar;
	}

	/**
	 * @return the terminated
	 */
	public Date getTerminated() {
		return terminated;
	}

	/**
	 * @return the type
	 */
	public EmploymentType getType() {
		return type;
	}

	/**
	 * @return the workingHoursDecrement
	 */
	public Integer getWorkingHoursDecrement() {
		return workingHoursDecrement;
	}

	/**
	 * @return the workingHoursDecrementReason
	 */
	public String getWorkingHoursDecrementReason() {
		return workingHoursDecrementReason;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param deputyEmploymentInfo the deputyEmploymentInfo to set
	 */
	public void setDeputyEmploymentInfo(DeputyEmploymentInfo deputyEmploymentInfo) {
		this.deputyEmploymentInfo = deputyEmploymentInfo;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	/**
	 * @param finalWorkingHours the finalWorkingHours to set
	 */
	public void setFinalWorkingHours(Integer finalWorkingHours) {
		this.finalWorkingHours = finalWorkingHours;
	}

	/**
	 * @param mandatoryWorkingHours the mandatoryWorkingHours to set
	 */
	public void setMandatoryWorkingHours(Integer mandatoryWorkingHours) {
		this.mandatoryWorkingHours = mandatoryWorkingHours;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	/**
	 * @param serviceAllocation the serviceAllocation to set
	 */
	public void setServiceAllocation(ServiceAllocation serviceAllocation) {
		this.serviceAllocation = serviceAllocation;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	/**
	 * @param supersededBy the supersededBy to set
	 */
	public void setSupersededBy(Employment supersededBy) {
		this.supersededBy = supersededBy;
	}

	/**
	 * @param temporar the temporar to set
	 */
	public void setTemporar(Boolean temporar) {
		this.temporar = temporar;
	}

	/**
	 * @param terminated the terminated to set
	 */
	public void setTerminated(Date terminated) {
		this.terminated = terminated;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EmploymentType type) {
		this.type = type;
	}

	/**
	 * @param workingHoursDecrement the workingHoursDecrement to set
	 */
	public void setWorkingHoursDecrement(Integer workingHoursDecrement) {
		this.workingHoursDecrement = workingHoursDecrement;
	}

	/**
	 * @param workingHoursDecrementReason the workingHoursDecrementReason to set
	 */
	public void setWorkingHoursDecrementReason(String workingHoursDecrementReason) {
		this.workingHoursDecrementReason = workingHoursDecrementReason;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ employment for ");
		sb.append(getEmployee());
		sb.append(" employee ");
		sb.append(" on school unit ");
		sb.append(getSchool());
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * @return the homeBased
	 */
	public Boolean getHomeBased() {
		return homeBased;
	}

	/**
	 * @param homeBased the homeBased to set
	 */
	public void setHomeBased(Boolean homeBased) {
		this.homeBased = homeBased;
	}

}
