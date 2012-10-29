package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 */
@Entity
@Table(name = "PART_TIME_EMPLOYMENT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class PartTimeEmployment extends BaseIDDeleteAwareModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Join Evaluation with its respective Employee
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
	/**
	 * Working Hurs per Week (Εβδομαδιαίες ώρες εργασίας)
	 */
	@Basic
	@Column(name = "HOURS_PER_WEEK")
	private Byte hoursPerWeek;
	
	/**
	 * Start Date (Ημερομηνία Έναρξης)
	 */
	@Basic
	@Column(name = "START_DATE")
	private Date startDate;
	
	/**
	 * End Date (Ημερομηνία Λήξης)
	 */
	@Basic
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name="COMMENT", nullable = true)
	private String comment;

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
	 * @return the hoursPerWeek
	 */
	public Byte getHoursPerWeek() {
		return hoursPerWeek;
	}

	/**
	 * @param hoursPerWeek the hoursPerWeek to set
	 */
	public void setHoursPerWeek(Byte hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PartTimeEmployment [hoursPerWeek=" + hoursPerWeek
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", comment=" + comment + "]";
	}
	
}
