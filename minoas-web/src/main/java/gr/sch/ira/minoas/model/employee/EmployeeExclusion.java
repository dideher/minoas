package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "EMPLOYEE_EXCLUSION")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeExclusion extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	
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
