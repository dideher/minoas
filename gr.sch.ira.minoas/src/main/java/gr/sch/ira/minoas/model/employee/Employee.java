package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @author slavikos
 *
 */
@Entity
@Table(name = "EMPLOYEE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Employee extends Person {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An employee can be active or not. 
	 */
	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CURRENT_EMPLOYMENT_ID", nullable = true)
	private Employment currentEmployment;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PYSDE_ID", nullable = false, updatable = true)
	private PYSDE currentPYSDE;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = { CascadeType.ALL })
	private Collection<Disposal> disposals = new ArrayList<Disposal>();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "EDUCATIONAL_LEVEL_TYPE", length = 2, nullable = true)
	private EducationalLevelType educationalLevelType;
	
	@OneToOne(mappedBy="employee", fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
	private EmployeeInfo employeeInfo;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<Employment> employments;
	
	@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="employee")
	private EmployeeExclusion exclusion;
	
	/**
	* Each employee have a specialization, which is actually the last employment's 
	* specialization.
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_SPECIALIZATION_ID", nullable = true)
	private Specialization lastSpecialization;

	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(name = "EMPLOYEE_LEAVE_ID", nullable = true)
	private EmployeeLeave leave;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = { CascadeType.ALL })
	private Collection<EmployeeLeave> leaves = new ArrayList<EmployeeLeave>();

	@Basic
	@Column(name = "LEGACY_CODE", nullable = true, updatable = false, length = 10)
	private String legacyCode;

	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.ALL}, mappedBy="employee")
	private Collection<PermanentTransfer> permanentTransfer =  new ArrayList<PermanentTransfer>();

	
	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "REGULAR_EMPLOYMEE_INFO_ID", nullable = true)
	private RegularEmployeeInfo regularEmployeeInfo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = { CascadeType.ALL })
	private Collection<Secondment> secondments = new ArrayList<Secondment>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = { CascadeType.ALL })
	private Collection<ServiceAllocation> serviceAllocations = new ArrayList<ServiceAllocation>();

	/**
	 * The date at which the system has last time updated the employee's service time (work experience, 
	 * mandatory work hours, etc)
	 */
	@Basic(fetch=FetchType.LAZY)
	@Column(name="SERVICE_LAST_UPDATED", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceLastUpdated = null;

	@Basic
	@Column(name = "SPECIAL_CATEGORY", nullable = true)
	private Boolean specialCategory = Boolean.FALSE;
	
	/**
     * The date at which the employee has been terminated
     */
	@Basic(fetch=FetchType.LAZY)
    @Column(name="TERMINATION_DATE", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
	private Date terminationDate = null;
	
	/**
     * An optional comment giving more information about the reason 
     * an employee's termination
     */
    @Basic(fetch=FetchType.LAZY)
    @Column(name="TERMINATION_COMMENT", nullable=true, length=250)
    private String terminationOptionalComment;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TERMINATION_TYPE_ID", nullable=true)
    private EmployeeTerminationReason terminationReason = null;

	@Enumerated(EnumType.STRING)
	@Column(name = "EMPLOYEE_TYPE", nullable = false, updatable = false)
	private EmployeeType type;
	
	
    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.ALL}, mappedBy="employee")
	private Collection<WorkExperience> workExperience =  new ArrayList<WorkExperience>();
	
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.ALL}, mappedBy="employee")
	private Collection<OutstandingImprovement> οutstandingImprovement =  new ArrayList<OutstandingImprovement>();
	
	/**
	 * 
	 */
	public Employee() {
		super();
	}

	/**
	 * Constructor to be used when cloning an Employee. Note, the cloning is simple and no
	 * complex referenced beans are cloned as well.
	 * @param e
	 */
	public Employee(Employee e) {
		super(e);
		this.setActive(e.getActive());
		this.setCurrentPYSDE(e.getCurrentPYSDE());
		this.setLastSpecialization(e.getLastSpecialization());
		this.setEducationalLevelType(e.getEducationalLevelType());
		this.setTerminationDate(e.getTerminationDate());
		this.setTerminationOptionalComment(e.getTerminationOptionalComment());
		this.setTerminationReason(e.getTerminationReason());
		this.setType(e.getType());
	}
	
	public Secondment addSecondment(Secondment secondment) {
		if (!getSecondments().contains(secondment)) {
			getSecondments().add(secondment);
			secondment.setEmployee(this);
		}
		return secondment;
	}

	public ServiceAllocation addServiceAllocation(ServiceAllocation serviceAllocation) {
		if (!getServiceAllocations().contains(serviceAllocation)) {
			getServiceAllocations().add(serviceAllocation);
			serviceAllocation.setEmployee(this);
		}
		return serviceAllocation;
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
	 * @return the disposals
	 */
	protected Collection<Disposal> getDisposals() {
		return disposals;
	}

	/**
	 * @return the educationalLevelType
	 */
	public EducationalLevelType getEducationalLevelType() {
		return educationalLevelType;
	}

	/**
	 * @return the employeeInfo
	 */
	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	/**
	 * @return the employments
	 */
	public Set<Employment> getEmployments() {
		return employments;
	}

	/**
	 * @return the exclusion
	 */
	public EmployeeExclusion getExclusion() {
		return exclusion;
	}

	/**
	 * @return the lastSpecialization
	 */
	public Specialization getLastSpecialization() {
		return lastSpecialization;
	}

	/**
	 * @return the leave
	 */
	public EmployeeLeave getLeave() {
		return leave;
	}

	/**
	 * @return the leaves
	 */
	public Collection<EmployeeLeave> getLeaves() {
		return leaves;
	}

	/**
	 * @return the legacyCode
	 */
	public String getLegacyCode() {
		return legacyCode;
	}

	/**
	 * @return the permanentTransfer
	 */
	public Collection<PermanentTransfer> getPermanentTransfer() {
		return permanentTransfer;
	}

	/**
	 * @return the regularDetail
	 */
	public RegularEmployeeInfo getRegularDetail() {
		return regularEmployeeInfo;
	}

	/**
	 * @return the regularEmployeeInfo
	 */
	public RegularEmployeeInfo getRegularEmployeeInfo() {
		return regularEmployeeInfo;
	}

	/**
	 * Returns, if possible, the employees regular employment school title. This
	 * operation can be called safely.
	 * 
	 * @return The emplouyee's regular employment school title or null
	 */
	public String getRegularSchoolTitle() {
		if(isRegularEmployee()) {
			Employment em = getCurrentEmployment();
			if(em != null) {
				try {
					return em.getSchool().getTitle();
				} catch(Exception ex) {
					return null;
				}
			} else return null;
		} else return null;
	}

	
	/**
	 * @return the secondments
	 */
	public Collection<Secondment> getSecondments() {
		return secondments;
	}

	/**
	 * @return the serviceAllocations
	 */
	public Collection<ServiceAllocation> getServiceAllocations() {
		return serviceAllocations;
	}

	/**
     * @return the serviceLastUpdated
     */
    public Date getServiceLastUpdated() {
        return serviceLastUpdated;
    }

	/**
	 * @return the specialCategory
	 */
	public Boolean getSpecialCategory() {
		return specialCategory;
	}

	/**
     * @return the terminationDate
     */
    public Date getTerminationDate() {
        return terminationDate;
    }

	/**
     * @return the terminationOptionalComment
     */
    public String getTerminationOptionalComment() {
        return terminationOptionalComment;
    }

	/**
     * @return the terminationReason
     */
    public EmployeeTerminationReason getTerminationReason() {
        return terminationReason;
    }

	/**
	 * @return the type
	 */
	public EmployeeType getType() {
		return type;
	}

	/**
     * @return the workExperience
     */
    public Collection<WorkExperience> getWorkExperience() {
        return workExperience;
    }

	/**
	 * @return the οutstandingImprovement
	 */
	public Collection<OutstandingImprovement> getΟutstandingImprovement() {
		return οutstandingImprovement;
	}

	public boolean isDeputyEmployee() {
        return type==EmployeeType.DEPUTY;
    }

	public boolean isHourlyPaidEmployee() {
        return type==EmployeeType.HOURLYPAID;
    }

	public boolean isRegularEmployee() {
        return type==EmployeeType.REGULAR;
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
	 * @param disposals the disposals to set
	 */
	protected void setDisposals(Collection<Disposal> disposals) {
		this.disposals = disposals;
	}

	/**
	 * @param educationalLevelType the educationalLevelType to set
	 */
	public void setEducationalLevelType(EducationalLevelType educationalLevelType) {
		this.educationalLevelType = educationalLevelType;
	}

    /**
	 * @param employeeInfo the employeeInfo to set
	 */
	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

    /**
	 * @param employments the employments to set
	 */
	public void setEmployments(Set<Employment> employments) {
		this.employments = employments;
	}
    
	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(EmployeeExclusion exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @param lastSpecialization the lastSpecialization to set
	 */
	public void setLastSpecialization(Specialization lastSpecialization) {
		this.lastSpecialization = lastSpecialization;
	}

	/**
	 * @param leave the leave to set
	 */
	public void setLeave(EmployeeLeave leave) {
		this.leave = leave;
	}

	/**
	 * @param leaves the leaves to set
	 */
	public void setLeaves(Collection<EmployeeLeave> leaves) {
		this.leaves = leaves;
	}

	/**
	 * @param legacyCode the legacyCode to set
	 */
	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
	}

	/**
	 * @param permanentTransfer the permanentTransfer to set
	 */
	public void setPermanentTransfer(Collection<PermanentTransfer> permanentTransfer) {
		this.permanentTransfer = permanentTransfer;
	}
	
    /**
	 * @param regularDetail the regularDetail to set
	 */
	public void setRegularDetail(RegularEmployeeInfo regularDetail) {
		this.regularEmployeeInfo = regularDetail;
	}

	/**
	 * @param regularEmployeeInfo the regularEmployeeInfo to set
	 */
	public void setRegularEmployeeInfo(RegularEmployeeInfo regularEmployeeInfo) {
		this.regularEmployeeInfo = regularEmployeeInfo;
	}

	/**
	 * @param secondments the secondments to set
	 */
	public void setSecondments(Collection<Secondment> secondments) {
		this.secondments = secondments;
	}
    
    /**
	 * @param serviceAllocations the serviceAllocations to set
	 */
	public void setServiceAllocations(Collection<ServiceAllocation> serviceAllocations) {
		this.serviceAllocations = serviceAllocations;
	}
    
    /**
     * @param serviceLastUpdated the serviceLastUpdated to set
     */
    public void setServiceLastUpdated(Date serviceLastUpdated) {
        this.serviceLastUpdated = serviceLastUpdated;
    }

    /**
	 * @param specialCategory the specialCategory to set
	 */
	public void setSpecialCategory(Boolean specialCategory) {
		this.specialCategory = specialCategory;
	}

    /**
     * @param terminationDate the terminationDate to set
     */
    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    /**
     * @param terminationOptionalComment the terminationOptionalComment to set
     */
    public void setTerminationOptionalComment(String terminationOptionalComment) {
        this.terminationOptionalComment = terminationOptionalComment;
    }

    /**
     * @param terminationReason the terminationReason to set
     */
    public void setTerminationReason(EmployeeTerminationReason terminationReason) {
        this.terminationReason = terminationReason;
    }

    /**
	 * @param type the type to set
	 */
	public void setType(EmployeeType type) {
		this.type = type;
	}

    /**
     * @param workExperience the workExperience to set
     */
    public void setWorkExperience(Collection<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    /**
	 * @param οutstandingImprovement the οutstandingImprovement to set
	 */
	public void setΟutstandingImprovement(
			Collection<OutstandingImprovement> οutstandingImprovement) {
		this.οutstandingImprovement = οutstandingImprovement;
	}

    public String toPrettyString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getLastName());
		sb.append(" ");
		sb.append(getFirstName());
		sb.append(" του ");
		sb.append(getFatherName());
		return sb.toString();
	}

	
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("(");
		sb.append(getId());
		sb.append(") ");
		sb.append(getLastName());
		sb.append(" ");
		sb.append(getFirstName());
		sb.append(" ");
		sb.append(getFatherName());
		sb.append(" ( ");
		sb.append(getType());
		sb.append(" )");
		sb.append(" ]");
		return sb.toString();
	}
	

}
