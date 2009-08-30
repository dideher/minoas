package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeReportItem {

	private Date employeeBirthday;

	private String employeeCode;

	private String employeeFatherName;

	private String employeeFirstName;

	private String employeeLastName;

	private int employeeMandatoryHours;

	private String employeeMotherName;

	private String employeeSpecialization;

	private String employeeSpecializationID;
	
	private String employeeType;

	/**
	 * 
	 */
	public EmployeeReportItem() {
		super();
	}

	public EmployeeReportItem(Employee employee) {
		this();
		this.employeeLastName = employee.getLastName();
		this.employeeFirstName = employee.getFirstName();
		this.employeeFatherName = employee.getFatherName();
		this.employeeMotherName = employee.getMotherName();
		if (employee.getLastSpecialization() != null) {
			this.employeeSpecialization = employee.getLastSpecialization().getTitle();
			this.employeeSpecializationID = employee.getLastSpecialization().getId();
		}
		if(employee.getRegularDetail()!=null)
			this.employeeCode = employee.getRegularDetail().getRegistryID();
	}

	/**
	 * @return the birthday
	 */
	public Date getEmployeeBirthday() {
		return employeeBirthday;
	}

	/**
	 * @return the fatherName
	 */
	public String getEmployeeFatherName() {
		return employeeFatherName;
	}

	/**
	 * @return the firstName
	 */
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	/**
	 * @return the lastName
	 */
	public String getEmployeeLastName() {
		return employeeLastName;
	}

	/**
	 * @return the mandatoryHours
	 */
	public int getEmployeeMandatoryHours() {
		return employeeMandatoryHours;
	}

	/**
	 * @return the motherName
	 */
	public String getEmployeeMotherName() {
		return employeeMotherName;
	}

	/**
	 * @return the specialization
	 */
	public String getEmployeeSpecialization() {
		return employeeSpecialization;
	}

	/**
	 * @return the specializationCode
	 */
	public String getEmployeeSpecializationID() {
		return employeeSpecializationID;
	}

	/**
	 * @return the employeeType
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setEmployeeBirthday(Date birthday) {
		this.employeeBirthday = birthday;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setEmployeeFatherName(String fatherName) {
		this.employeeFatherName = fatherName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setEmployeeFirstName(String firstName) {
		this.employeeFirstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setEmployeeLastName(String lastName) {
		this.employeeLastName = lastName;
	}

	/**
	 * @param mandatoryHours the mandatoryHours to set
	 */
	public void setEmployeeMandatoryHours(int mandatoryHours) {
		this.employeeMandatoryHours = mandatoryHours;
	}

	/**
	 * @param motherName the motherName to set
	 */
	public void setEmployeeMotherName(String motherName) {
		this.employeeMotherName = motherName;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setEmployeeSpecialization(String specialization) {
		this.employeeSpecialization = specialization;
	}

	/**
	 * @param specializationCode the specializationCode to set
	 */
	public void setEmployeeSpecializationID(String specializationCode) {
		this.employeeSpecializationID = specializationCode;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

}
