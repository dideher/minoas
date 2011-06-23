package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * 
 */
@Entity
@Table(name = "WORK_EXPERIENCE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WorkExperience extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

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
	
	@ManyToOne()
	@JoinColumn(name = "EXPERIENCE_UNIT", nullable = true)
	private Unit experienceUnit;
	
	@Column(name="COMMENT", nullable = true)
	private String comment;
	
	
	

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
	 * @return the experienceUnit
	 */
	public Unit getExperienceUnit() {
		return experienceUnit;
	}

	/**
	 * @param experienceUnit the experienceUnit to set
	 */
	public void setExperienceUnit(Unit experienceUnit) {
		this.experienceUnit = experienceUnit;
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

}
