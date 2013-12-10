/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employee.EmployeeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "SUITABLE_SPECIALIZATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeSuitableSpecialization extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "EMPLOYEE_TYPE")
	private EmployeeType employeeType;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SPECIALIZATION_ID", nullable = false)
	private Specialization suitableSpecialization;

	/**
	 * 
	 */
	public EmployeeSuitableSpecialization() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeSuitableSpecialization(EmployeeType employeeType) {
		super();
		this.employeeType = employeeType;
	}

	public EmployeeSuitableSpecialization(EmployeeType employeeType,
			Specialization suitableSpecialization) {
		super();
		this.employeeType = employeeType;
		this.suitableSpecialization = suitableSpecialization;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}


}
