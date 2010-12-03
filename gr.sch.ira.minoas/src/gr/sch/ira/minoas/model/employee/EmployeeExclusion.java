package gr.sch.ira.minoas.model.employee;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gr.sch.ira.minoas.model.BaseIDModel;

@Entity
@Table(name = "EMPLOYEE_EXCLUSION")
public class EmployeeExclusion extends BaseIDModel {
	
	@OneToOne
	@JoinColumn(name="EMPLOYEE_ID", unique=true)
	private Employee employee;
	
	@Basic
	@Column(name="REASON", length=256, nullable=true)
	private String exclusionReason;

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
	 * @return the exclusionReason
	 */
	public String getExclusionReason() {
		return exclusionReason;
	}

	/**
	 * @param exclusionReason the exclusionReason to set
	 */
	public void setExclusionReason(String exclusionReason) {
		this.exclusionReason = exclusionReason;
	}
}
