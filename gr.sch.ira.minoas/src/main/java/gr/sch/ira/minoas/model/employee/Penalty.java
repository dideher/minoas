package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "PENALTY")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Penalty extends BaseIDDeleteAwareModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Join Penalty with its respective Employee (Εργαζόμενος)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
	/**
	 * Penalty Type (Τύπος Ποινής)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "PENALTY_TYPE", nullable = false, updatable = true)
	private PenaltyType type;
	
	/**
	 * Penalty Award Date (Ημερομηνία Απονομής Ποινής)
	 */
	@Basic
	@Column(name = "PENALTY_AWARD_DATE")
	private Date penaltyAwardDate;
	
	/**
	 * Penalty Start Date (Ημερομηνία Έναρξης Ποινής)
	 */
	@Basic
	@Column(name = "PENALTY_START_DATE")
	private Date penaltyStartDate;
	
	/**
	 * Penalty End Date (Ημερομηνία Λήξης Ποινής)
	 */
	@Basic
	@Column(name = "PENALTY_END_DATE")
	private Date penaltyEndDate;
	
	/**
	 * Fine (Χρηματικό Πρόστιμο)
	 */
	@Basic
	@Column(name = "FINE")
	private Integer fine;
	
	/**
	 * Comment (Σχόλιο)
	 */
	@Basic
	@Column(name = "COMMENT", nullable = true, length = 256)
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
	 * @return the type
	 */
	public PenaltyType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PenaltyType type) {
		this.type = type;
	}

	/**
	 * @return the penaltyAwardDate
	 */
	public Date getPenaltyAwardDate() {
		return penaltyAwardDate;
	}

	/**
	 * @param penaltyAwardDate the penaltyAwardDate to set
	 */
	public void setPenaltyAwardDate(Date penaltyAwardDate) {
		this.penaltyAwardDate = penaltyAwardDate;
	}

	/**
	 * @return the penaltyStartDate
	 */
	public Date getPenaltyStartDate() {
		return penaltyStartDate;
	}

	/**
	 * @param penaltyStartDate the penaltyStartDate to set
	 */
	public void setPenaltyStartDate(Date penaltyStartDate) {
		this.penaltyStartDate = penaltyStartDate;
	}

	/**
	 * @return the penaltyEndDate
	 */
	public Date getPenaltyEndDate() {
		return penaltyEndDate;
	}

	/**
	 * @param penaltyEndDate the penaltyEndDate to set
	 */
	public void setPenaltyEndDate(Date penaltyEndDate) {
		this.penaltyEndDate = penaltyEndDate;
	}

	/**
	 * @return the fine
	 */
	public Integer getFine() {
		return fine;
	}

	/**
	 * @param fine the fine to set
	 */
	public void setFine(Integer fine) {
		this.fine = fine;
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
		return "Penalty [type=" + type + ", penaltyAwardDate="
				+ penaltyAwardDate + ", penaltyStartDate=" + penaltyStartDate
				+ ", penaltyEndDate=" + penaltyEndDate + ", fine=" + fine
				+ ", comment=" + comment + "]";
	}
	
}
