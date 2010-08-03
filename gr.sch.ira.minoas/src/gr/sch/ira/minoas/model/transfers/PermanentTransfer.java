package gr.sch.ira.minoas.model.transfers;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PERMANENT_TRANSFER", uniqueConstraints=@UniqueConstraint(columnNames={"EMPLOYEE_REGISTRY_ID", "SCHOOLYEAR_ID"}))
public class PermanentTransfer extends BaseOutstandingTransfer {
	
	@Basic
	@Column(name="EMPLOYEE_REGISTRY_ID", nullable=false)
	private String employeeRegistryID;
	
	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID", nullable=true)
	private Employee employee;
	
	@Basic
	@Column(name="NEW_EMPLOYEE_NAME", nullable=true)
	private String employeeName;
	
	@Basic
	@Column(name="NEW_EMPLOYEE_SURNAME", nullable=true)
	private String employeeSurname;
	
	@Basic
	@Column(name="NEW_EMPLOYEE_AFM", nullable=true)
	private String employeeAFM;
	
	@Basic
	@Column(name="NEW_EMPLOYEE_FATHER_NAME", nullable=true)
	private String employeeFatherName;
	
	@Basic
	@Column(name="NEW_EMPLOYEE_MOTHER_NAME", nullable=true)
	private String employeeMotherName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_SPECIALIZATION_ID", nullable = true)
	private Specialization employeeSpecialization;
	
	@Basic
	@Column(name = "NEW_EMPLOYEE", nullable=true)
	private Boolean newEmployee;
	
	@ManyToOne
	@JoinColumn(name="SOURCE_UNIT_ID", nullable=true)
	private Unit sourceUnit;

	@ManyToOne
	@JoinColumn(name="TARGET_UNIT_ID", nullable=false)
	private Unit targetUnit;
	
	@Basic
	@Column(name = "SOURCE_REGION", nullable=true)
	private Character sourceRegionCode;
	
	@Basic
	@Column(name = "TARGET_REGION", nullable=true)
	private Character targetRegionCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSFER_TYPE", nullable=true)
	private PermanentTransferType type;

	/**
	 * @return the sourceUnit
	 */
	public Unit getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(Unit sourceUnit) {
		this.sourceUnit = sourceUnit;
	}
	

	/**
	 * @return the sourceRegionCode
	 */
	public Character getSourceRegionCode() {
		return sourceRegionCode;
	}

	/**
	 * @param sourceRegionCode the sourceRegionCode to set
	 */
	public void setSourceRegionCode(Character sourceRegionCode) {
		this.sourceRegionCode = sourceRegionCode;
	}

	/**
	 * @return the targetRegionCode
	 */
	public Character getTargetRegionCode() {
		return targetRegionCode;
	}

	/**
	 * @param targetRegionCode the targetRegionCode to set
	 */
	public void setTargetRegionCode(Character targetRegionCode) {
		this.targetRegionCode = targetRegionCode;
	}

	/**
	 * @return the type
	 */
	public PermanentTransferType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PermanentTransferType type) {
		this.type = type;
	}

	/**
	 * @return the targetUnit
	 */
	public Unit getTargetUnit() {
		return targetUnit;
	}

	/**
	 * @param targetUnit the targetUnit to set
	 */
	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
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

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return the employeeSurname
	 */
	public String getEmployeeSurname() {
		return employeeSurname;
	}

	/**
	 * @param employeeSurname the employeeSurname to set
	 */
	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	/**
	 * @return the employeeAFM
	 */
	public String getEmployeeAFM() {
		return employeeAFM;
	}

	/**
	 * @param employeeAFM the employeeAFM to set
	 */
	public void setEmployeeAFM(String employeeAFM) {
		this.employeeAFM = employeeAFM;
	}

	/**
	 * @return the employeeFatherName
	 */
	public String getEmployeeFatherName() {
		return employeeFatherName;
	}

	/**
	 * @param employeeFatherName the employeeFatherName to set
	 */
	public void setEmployeeFatherName(String employeeFatherName) {
		this.employeeFatherName = employeeFatherName;
	}

	/**
	 * @return the employeeMotherName
	 */
	public String getEmployeeMotherName() {
		return employeeMotherName;
	}

	/**
	 * @param employeeMotherName the employeeMotherName to set
	 */
	public void setEmployeeMotherName(String employeeMotherName) {
		this.employeeMotherName = employeeMotherName;
	}

	/**
	 * @return the newEmployee
	 */
	public Boolean getNewEmployee() {
		return newEmployee;
	}

	/**
	 * @param newEmployee the newEmployee to set
	 */
	public void setNewEmployee(Boolean newEmployee) {
		this.newEmployee = newEmployee;
	}

	/**
	 * @return the employeeSpecialization
	 */
	public Specialization getEmployeeSpecialization() {
		return employeeSpecialization;
	}

	/**
	 * @param employeeSpecialization the employeeSpecialization to set
	 */
	public void setEmployeeSpecialization(Specialization employeeSpecialization) {
		this.employeeSpecialization = employeeSpecialization;
	}

}
