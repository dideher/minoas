package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * 
 */
@Entity
@Table(name = "WORK_EXPERIENCE")

public class WorkExperience extends BaseIDDeleteAwareModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @Basic
    @Column(name = "LEGACY_CODE", nullable = true)
    private Integer legacyCode;

	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active;
	
	@ManyToOne()
	@JoinColumn(name="EXPERIENCE_TYPE_ID", nullable=false)
	private WorkExperienceType type;

	@ManyToOne(optional=false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Temporal(TemporalType.DATE)
	@Column(name = "FROM_DATE", nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TO_DATE", nullable = false)
	private Date toDate;

	@Column(name = "CALENDAR_EXPERIENCE_DAYS", nullable = false)
	private Integer calendarExperienceDays;
	
	@Column(name = "ACTUAL_DAYS", nullable = false)
	private Integer actualDays;

	@Column(name = "NUMBER_OF_WORK_EXPERIENCE_HOURS", nullable = true)
	private Integer numberOfWorkExperienceHours;
	
	@Column(name = "MANDATORY_HOURS", nullable = true)
	private Integer mandatoryHours;
	
	@Column(name = "FINAL_WORKING_HOURS", nullable = true)
	private Integer finalWorkingHours;
	
	@Column(name="COMMENT", nullable = true)
	private String comment;
	
	//	Εκπαιδευτική Προϋπηρεσία
	@Basic
	@Column(name = "EDUCATIONAL", nullable = false)
	private Boolean educational;
	
	//	Εκπαιδευτική Προϋπηρεσία
	@Basic
	@Column(name = "TEACHING", nullable = false)
	private Boolean teaching;

	
	/**
	 * @return the actualDays
	 */
	public Integer getActualDays() {
		return actualDays;
	}

	/**
	 * @param actualDays the actualDays to set
	 */
	public void setActualDays(Integer actualDays) {
		this.actualDays = actualDays;
	}

	/**
	 * @return the type
	 */
	public WorkExperienceType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(WorkExperienceType type) {
		this.type = type;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	

	/**
	 * @return the calendarExperienceDays
	 */
	public Integer getCalendarExperienceDays() {
		return calendarExperienceDays;
	}

	/**
	 * @param calendarExperienceDays the calendarExperienceDays to set
	 */
	public void setCalendarExperienceDays(Integer calendarExperienceDays) {
		this.calendarExperienceDays = calendarExperienceDays;
	}

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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

    /**
     * @return the legacyCode
     */
    public Integer getLegacyCode() {
        return legacyCode;
    }

    /**
     * @param legacyCode the legacyCode to set
     */
    public void setLegacyCode(Integer legacyCode) {
        this.legacyCode = legacyCode;
    }

	/**
	 * @return the mandatoryHours
	 */
	public Integer getMandatoryHours() {
		return mandatoryHours;
	}

	/**
	 * @param mandatoryHours the mandatoryHours to set
	 */
	public void setMandatoryHours(Integer mandatoryHours) {
		this.mandatoryHours = mandatoryHours;
	}

	/**
	 * @return the finalWorkingHours
	 */
	public Integer getFinalWorkingHours() {
		return finalWorkingHours;
	}

	/**
	 * @param finalWorkingHours the finalWorkingHours to set
	 */
	public void setFinalWorkingHours(Integer finalWorkingHours) {
		this.finalWorkingHours = finalWorkingHours;
	}

	/**
	 * @return the numberOfWorkExperienceHours
	 */
	public Integer getNumberOfWorkExperienceHours() {
		return numberOfWorkExperienceHours;
	}

	/**
	 * @param numberOfWorkExperienceHours the numberOfWorkExperienceHours to set
	 */
	public void setNumberOfWorkExperienceHours(Integer numberOfWorkExperienceHours) {
		this.numberOfWorkExperienceHours = numberOfWorkExperienceHours;
	}

	/**
	 * @return the educational
	 */
	public Boolean getEducational() {
		return educational;
	}

	/**
	 * @param educational the educational to set
	 */
	public void setEducational(Boolean educational) {
		this.educational = educational;
	}

	/**
	 * @return the teaching
	 */
	public Boolean getTeaching() {
		return teaching;
	}

	/**
	 * @param teaching the teaching to set
	 */
	public void setTeaching(Boolean teaching) {
		this.teaching = teaching;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkExperience [legacyCode=" + legacyCode + ", active="
				+ active + ", type=" + type + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", calendarExperienceDays="
				+ calendarExperienceDays + ", actualDays=" + actualDays
				+ ", numberOfWorkExperienceHours="
				+ numberOfWorkExperienceHours + ", mandatoryHours="
				+ mandatoryHours + ", finalWorkingHours=" + finalWorkingHours
				+ ", comment=" + comment + ", educational=" + educational
				+ ", teaching=" + teaching + "]";
	}

    
}
