package gr.sch.ira.minoas.model.employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 */
@Entity
@Table(name = "EVALUATION")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Evaluation extends BaseIDModel {

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
	 * Evaluation Date (Ημερομηνία Αξιολόγησης)
	 */
	@Basic
	@Column(name = "EVALUATION_DATE")
	private Date evaluationDate;
	
	/**
	 * Grade (Βαθμός)
	 */
	@Basic
	@Column(name = "EVALUATION_GRADE")
	private Byte evaluationGrade;
	
	/**
	 * Evaluation Start Date (Αρχική ημερομηνία περιόδου αξιολόγησης)
	 */
	@Basic
	@Column(name = "EVALUATION_START_DATE")
	private Date evaluationStartDate;
	
	/**
	 * Evaluation Start Date (Τελική ημερομηνία περιόδου αξιολόγησης)
	 */
	@Basic
	@Column(name = "EVALUATION_END_DATE")
	private Date evaluationEndDate;

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
	 * @return the evaluationDate
	 */
	public Date getEvaluationDate() {
		return evaluationDate;
	}

	/**
	 * @param evaluationDate the evaluationDate to set
	 */
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	/**
	 * @return the evaluationGrade
	 */
	public Byte getEvaluationGrade() {
		return evaluationGrade;
	}

	/**
	 * @param evaluationGrade the evaluationGrade to set
	 */
	public void setEvaluationGrade(Byte evaluationGrade) {
		this.evaluationGrade = evaluationGrade;
	}

	/**
	 * @return the evaluationStartDate
	 */
	public Date getEvaluationStartDate() {
		return evaluationStartDate;
	}

	/**
	 * @param evaluationStartDate the evaluationStartDate to set
	 */
	public void setEvaluationStartDate(Date evaluationStartDate) {
		this.evaluationStartDate = evaluationStartDate;
	}

	/**
	 * @return the evaluationEndDate
	 */
	public Date getEvaluationEndDate() {
		return evaluationEndDate;
	}

	/**
	 * @param evaluationEndDate the evaluationEndDate to set
	 */
	public void setEvaluationEndDate(Date evaluationEndDate) {
		this.evaluationEndDate = evaluationEndDate;
	}
	
}
