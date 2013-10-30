package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "EMPLOYEE_LEAVES")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeLeave extends BaseIDDeleteAwareModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;
	
	@Basic
    @Column(name = "IS_AUTOCANCELED", nullable = true)
    private Boolean autoCanceled;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 255)
	private String comment;

	@Basic
	@Column(name = "DUE_TO", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dueTo;

	@Basic
	@Column(name="EFFECTIVE_DAYS_COUNT", nullable=true)
	private Integer effectiveNumberOfDays;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="EMPLOYEE_LEAVE_TYPE_ID", nullable=true)
	private EmployeeLeaveType employeeLeaveType;

	@Basic
	@Column(name = "ESTABLISHED", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date established;
	
	@Basic
    @Column(name="GENERATES_CDRS", nullable=true)
    private Boolean generatesCDRs = Boolean.FALSE;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="leave")
    private Collection<TeachingHourCDR> leaveCDRs = new ArrayList<TeachingHourCDR>();
	
	@Basic
    @Column(name="DAYS_COUNT", nullable=true)
    private Integer numberOfDays;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGULAR_SCHOOL_ID", nullable = true)
	private School regularSchool;
	
	public EmployeeLeave() {
		super();
	}
	
	/**
	 * Constructor to be used when cloning an EmployeeLeave. Note, the cloning is simple and no
	 * complex referenced beans are cloned as well.
	 * 
	 * @param e
	 */
	public EmployeeLeave(EmployeeLeave e) {
		this();
		if(e != null) {
			this.setId(e.getId());
			this.setActive(e.getActive());
			this.setAutoCanceled(e.getAutoCanceled());
			this.setComment(e.getComment());
			this.setDeleted(e.getDeleted());
			this.setDeletedBy(e.getDeletedBy());
			this.setDeletedComment(e.getDeletedComment());
			this.setDeletedOn(e.getDeletedOn());
			this.setDueTo(e.getDueTo());
			this.setEffectiveNumberOfDays(e.getEffectiveNumberOfDays());
			this.setEmployeeLeaveType(e.getEmployeeLeaveType());
			this.setEstablished(e.getEstablished());
			this.setGeneratesCDRs(e.getGeneratesCDRs());
			this.setInsertedBy(e.getInsertedBy());
			this.setInsertedOn(e.getInsertedOn());
			this.setNumberOfDays(e.getNumberOfDays());
			this.setRegularSchool(e.getRegularSchool());
		}
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
     * @return the autoCanceled
     */
    public Boolean getAutoCanceled() {
        return autoCanceled;
    }

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
     * @return the effectiveNumberOfDays
     */
    public Integer getEffectiveNumberOfDays() {
        return effectiveNumberOfDays;
    }

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
     * @return the employeeLeaveType
     */
    public EmployeeLeaveType getEmployeeLeaveType() {
        return employeeLeaveType;
    }

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
     * @return the generatesCDRs
     */
    public Boolean getGeneratesCDRs() {
        return generatesCDRs;
    }

	/**
     * @return the leaveCDRs
     */
    public Collection<TeachingHourCDR> getLeaveCDRs() {
        return leaveCDRs;
    }

	/**
     * @return the numberOfDays
     */
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

	

	/**
	 * @return the regularSchool
	 */
	public School getRegularSchool() {
		return regularSchool;
	}

	public boolean isCurrent() {
        Date currentDate = DateUtils.truncate(new Date(),  Calendar.DAY_OF_MONTH);
        Date established = DateUtils.truncate(getEstablished(),  Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(getDueTo(),  Calendar.DAY_OF_MONTH);
        return  getActive() && ( (established.before(currentDate) || established.equals(currentDate)) && (dueTo.after(currentDate) || dueTo.equals(currentDate)));
    }

   
    public boolean isFuture() {
        Date currentDate = DateUtils.truncate(new Date(),  Calendar.DAY_OF_MONTH);
        return DateUtils.truncate(getEstablished(),  Calendar.DAY_OF_MONTH).after(currentDate); 
    }

    public boolean isPast() {
        Date currentDate = DateUtils.truncate(new Date(),  Calendar.DAY_OF_MONTH);
        return DateUtils.truncate(getEstablished(),  Calendar.DAY_OF_MONTH).before(currentDate);
    }

    /**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

    /**
     * @param autoCanceled the autoCanceled to set
     */
    public void setAutoCanceled(Boolean autoCanceled) {
        this.autoCanceled = autoCanceled;
    }

    /**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

    /**
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

    /**
     * @param effectiveNumberOfDays the effectiveNumberOfDays to set
     */
    public void setEffectiveNumberOfDays(Integer effectiveNumberOfDays) {
        this.effectiveNumberOfDays = effectiveNumberOfDays;
    }

    /**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
    
    /**
     * @param employeeLeaveType the employeeLeaveType to set
     */
    public void setEmployeeLeaveType(EmployeeLeaveType employeeLeaveType) {
        this.employeeLeaveType = employeeLeaveType;
    }
    
    /**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}
    
    /**
     * @param generatesCDRs the generatesCDRs to set
     */
    public void setGeneratesCDRs(Boolean generatesCDRs) {
        this.generatesCDRs = generatesCDRs;
    }

    /**
     * @param leaveCDRs the leaveCDRs to set
     */
    public void setLeaveCDRs(Collection<TeachingHourCDR> leaveCDRs) {
        this.leaveCDRs = leaveCDRs;
    }

    /**
     * @param numberOfDays the numberOfDays to set
     */
    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
	 * @param regularSchool the regularSchool to set
	 */
	public void setRegularSchool(School regularSchool) {
		this.regularSchool = regularSchool;
	}

    /**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Leave [");
		if (employee != null) {
			builder.append("employee=");
			builder.append(employee);
			builder.append(", ");
		}
		if (employeeLeaveType != null) {
			builder.append("employeeLeaveType=");
			builder.append(employeeLeaveType);
			builder.append(", ");
		}
		if (established != null) {
			builder.append("established=");
			builder.append(established);
			builder.append(", ");
		}
		if (dueTo != null) {
			builder.append("dueTo=");
			builder.append(dueTo);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

}
