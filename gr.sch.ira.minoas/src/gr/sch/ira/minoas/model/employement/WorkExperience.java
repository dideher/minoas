package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WORK_EXPERIENCE")
public class WorkExperience extends BaseIDModel {

	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "EXPERIENCE_TYPE")
	private WorkExperienceType type;

	@ManyToOne()
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Temporal(TemporalType.DATE)
	@Column(name = "FROM_DATE", nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TO_DATE", nullable = false)
	private Date toDate;

	@Column(name = "CALENDAR_DAYS", nullable = true)
	private Long experienceDays;

	@Column(name = "ACTUAL_DAYS", nullable = true)
	private Long actualExperienceDays;
	
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
	 * @return the experienceDays
	 */
	public Long getExperienceDays() {
		return experienceDays;
	}

	/**
	 * @param experienceDays the experienceDays to set
	 */
	public void setExperienceDays(Long experienceDays) {
		this.experienceDays = experienceDays;
	}

	/**
	 * @return the actualExperienceDays
	 */
	public Long getActualExperienceDays() {
		return actualExperienceDays;
	}

	/**
	 * @param actualExperienceDays the actualExperienceDays to set
	 */
	public void setActualExperienceDays(Long actualExperienceDays) {
		this.actualExperienceDays = actualExperienceDays;
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
