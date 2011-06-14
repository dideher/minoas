package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employee.Employee;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "SALARY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Salary extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy="salary")
	private Employee employee;

	@Basic
	@Column(name = "INITIAL_SALARY_LEVEL", nullable = false)
	private Integer initialSalaryLevel;

	@Basic
    @Column(name = "CURRENT_SALARY_LEVEL", nullable = false)
	private Integer currentSalaryLevel;

	@Basic
    @Column(name = "COMMENT", nullable = true)
	private String comment;

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the initialSalaryLevel
	 */
	public Integer getInitialSalaryLevel() {
		return initialSalaryLevel;
	}

	/**
	 * @param initialSalaryLevel
	 *            the initialSalaryLevel to set
	 */
	public void setInitialSalaryLevel(Integer initialSalaryLevel) {
		this.initialSalaryLevel = initialSalaryLevel;
	}

	/**
	 * @return the currentSalaryLevel
	 */
	public Integer getCurrentSalaryLevel() {
		return currentSalaryLevel;
	}

	/**
	 * @param currentSalaryLevel
	 *            the currentSalaryLevel to set
	 */
	public void setCurrentSalaryLevel(Integer currentSalaryLevel) {
		this.currentSalaryLevel = currentSalaryLevel;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
