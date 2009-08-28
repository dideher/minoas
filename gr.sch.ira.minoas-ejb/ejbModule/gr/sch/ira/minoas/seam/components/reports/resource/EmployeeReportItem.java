package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeReportItem {

	/**
	 * 
	 */
	public EmployeeReportItem() {
		super();
	}

	public EmployeeReportItem(Employee employee) {
		this();
		this.lastName = employee.getLastName();
		this.firstName = employee.getFirstName();
		this.fatherName = employee.getFatherName();
		this.motherName = employee.getMotherName();
		if (employee.getLastSpecialization() != null) {
			this.specialization = employee.getLastSpecialization().getTitle();
			this.specializationCode = employee.getLastSpecialization().getId();
		}
	}

	private String lastName;

	private String firstName;

	private String fatherName;

	private String motherName;

	private String specializationCode;

	private String specialization;

	private String employeeType;

	private Date birthday;

	private int mandatoryHours;

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}

	/**
	 * @param motherName the motherName to set
	 */
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	/**
	 * @return the specializationCode
	 */
	public String getSpecializationCode() {
		return specializationCode;
	}

	/**
	 * @param specializationCode the specializationCode to set
	 */
	public void setSpecializationCode(String specializationCode) {
		this.specializationCode = specializationCode;
	}

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * @return the employeeType
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the mandatoryHours
	 */
	public int getMandatoryHours() {
		return mandatoryHours;
	}

	/**
	 * @param mandatoryHours the mandatoryHours to set
	 */
	public void setMandatoryHours(int mandatoryHours) {
		this.mandatoryHours = mandatoryHours;
	}

}
