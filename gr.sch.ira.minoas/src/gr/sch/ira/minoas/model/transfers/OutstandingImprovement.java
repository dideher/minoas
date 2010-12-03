package gr.sch.ira.minoas.model.transfers;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.Employee;

@Entity
@Table(name = "OutstandingImprovement", uniqueConstraints=@UniqueConstraint(columnNames={"EMPLOYEE_ID", "SCHOOLYEAR_ID"}))
public class OutstandingImprovement extends BaseOutstandingTransfer {

	@Basic
	@Column(name="EMPLOYEE_REGISTRY_ID", nullable=false)
	private String employeeRegistryID;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID", nullable=false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="SOURCE_SCHOOL_ID", nullable=false)
	private School sourceSchool;

	@ManyToOne
	@JoinColumn(name="TARGET_SCHOOL_ID", nullable=false)
	private School targetSchool;
	
	@Basic
	@Column(name = "IMPROVEMENT_REGION")
	private Character improvementRegionCode;

	/**
	 * @return the targetSchool
	 */
	public School getTargetSchool() {
		return targetSchool;
	}

	/**
	 * @param targetSchool the targetSchool to set
	 */
	public void setTargetSchool(School targetSchool) {
		this.targetSchool = targetSchool;
	}

	/**
	 * @return the sourceSchool
	 */
	public School getSourceSchool() {
		return sourceSchool;
	}

	/**
	 * @param sourceSchool the sourceSchool to set
	 */
	public void setSourceSchool(School sourceSchool) {
		this.sourceSchool = sourceSchool;
	}

	

	/**
	 * @return the improvementRegionCode
	 */
	public Character getImprovementRegionCode() {
		return improvementRegionCode;
	}

	/**
	 * @param improvementRegionCode the improvementRegionCode to set
	 */
	public void setImprovementRegionCode(Character improvementRegionCode) {
		this.improvementRegionCode = improvementRegionCode;
	}

	/**
	 * @return the employeeRegistryID
	 */
	public String getEmployeeRegistryID() {
		return employeeRegistryID;
	}

	/**
	 * @param employeeRegistryID the employeeRegistryID to set
	 */
	public void setEmployeeRegistryID(String employeeRegistryID) {
		this.employeeRegistryID = employeeRegistryID;
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
	
}
