package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;

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
	
	private Boolean employeeHasBigFamily;
	
	private Boolean employeeIsSpecialCategory;

	private int employeeMandatoryHours;
	
	private int employeeFinalWorkingHours;
	
	private Date employeeEmploymentEstablishedDate;
	
	private Date employeeEmploymentTerminatedDate;

	private String employeeMotherName;

	private String employeeSpecialization;

	private String employeeSpecializationID;
	
	private String employeeType;
	
	private String employeeTypeKey;

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
		this.employeeType = employee.getType().name();
		this.employeeTypeKey = employee.getType().getKey();
		this.employeeBirthday = employee.getDateOfBirth();
		this.employeeHasBigFamily = employee.getBigFamily();
		this.employeeIsSpecialCategory = employee.getSpecialCategory();
		if (employee.getLastSpecialization() != null) {
			this.employeeSpecialization = employee.getLastSpecialization().getTitle();
			this.employeeSpecializationID = employee.getLastSpecialization().getId();
		}
		if(employee.getRegularDetail()!=null)
			this.employeeCode = employee.getRegularDetail().getRegistryID();
		
		if(employee.getCurrentEmployment()!=null) {
			Employment employment = employee.getCurrentEmployment(); 
			this.employeeFinalWorkingHours = employment.getFinalWorkingHours();
			this.employeeMandatoryHours = employment.getMandatoryWorkingHours();
			this.employeeEmploymentEstablishedDate = employment.getEstablished();
			this.employeeEmploymentTerminatedDate = employment.getTerminated();
		}
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

	/**
	 * @return the employeeTypeKey
	 */
	public String getEmployeeTypeKey() {
		return employeeTypeKey;
	}

	/**
	 * @param employeeTypeKey the employeeTypeKey to set
	 */
	public void setEmployeeTypeKey(String employeeTypeKey) {
		this.employeeTypeKey = employeeTypeKey;
	}

	/**
	 * @return the employeeFinalWorkingHours
	 */
	public int getEmployeeFinalWorkingHours() {
		return employeeFinalWorkingHours;
	}

	/**
	 * @param employeeFinalWorkingHours the employeeFinalWorkingHours to set
	 */
	public void setEmployeeFinalWorkingHours(int employeeFinalWorkingHours) {
		this.employeeFinalWorkingHours = employeeFinalWorkingHours;
	}

	/**
	 * @return the employeeEmploymentEstablishedDate
	 */
	public Date getEmployeeEmploymentEstablishedDate() {
		return employeeEmploymentEstablishedDate;
	}

	/**
	 * @param employeeEmploymentEstablishedDate the employeeEmploymentEstablishedDate to set
	 */
	public void setEmployeeEmploymentEstablishedDate(Date employeeEmploymentEstablishedDate) {
		this.employeeEmploymentEstablishedDate = employeeEmploymentEstablishedDate;
	}

	/**
	 * @return the employeeEmploymentTerminatedDate
	 */
	public Date getEmployeeEmploymentTerminatedDate() {
		return employeeEmploymentTerminatedDate;
	}

	/**
	 * @param employeeEmploymentTerminatedDate the employeeEmploymentTerminatedDate to set
	 */
	public void setEmployeeEmploymentTerminatedDate(Date employeeEmploymentTerminatedDate) {
		this.employeeEmploymentTerminatedDate = employeeEmploymentTerminatedDate;
	}

	/**
	 * @return the employeeHasBigFamily
	 */
	public Boolean getEmployeeHasBigFamily() {
		return employeeHasBigFamily;
	}

	/**
	 * @param employeeHasBigFamily the employeeHasBigFamily to set
	 */
	public void setEmployeeHasBigFamily(Boolean employeeHasBigFamily) {
		this.employeeHasBigFamily = employeeHasBigFamily;
	}

	/**
	 * @return the employeeIsSpecialCategory
	 */
	public Boolean getEmployeeIsSpecialCategory() {
		return employeeIsSpecialCategory;
	}

	/**
	 * @param employeeIsSpecialCategory the employeeIsSpecialCategory to set
	 */
	public void setEmployeeIsSpecialCategory(Boolean employeeIsSpecialCategory) {
		this.employeeIsSpecialCategory = employeeIsSpecialCategory;
	}

}
