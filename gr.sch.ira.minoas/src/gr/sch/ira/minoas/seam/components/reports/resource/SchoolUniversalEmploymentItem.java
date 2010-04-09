package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SchoolUniversalEmploymentItem extends EmploymentReportItem {

	private String employmentComment;

	/**
	 * 
	 */
	public SchoolUniversalEmploymentItem() {
		super();
	}

	public SchoolUniversalEmploymentItem(Disposal disposal) {
		super(disposal.getAffectedEmployment());
	}

	public SchoolUniversalEmploymentItem(Employee employee) {
		this();
		setId(employee.getId());
		setEmployeeLastName(employee.getLastName());
		setEmployeeFirstName(employee.getFirstName());
		setEmployeeFatherName(employee.getFatherName());
		setEmployeeMotherName(employee.getMotherName());
		setEmployeeType(employee.getType().name());
		setEmployeeTypeKey(employee.getType().getKey());
		setEmployeeBirthday(employee.getDateOfBirth());
		setEmployeeHasBigFamily(employee.getBigFamily());
		setEmployeeIsSpecialCategory(employee.getSpecialCategory());
		if (employee.getLastSpecialization() != null) {
			setEmployeeSpecialization(employee.getLastSpecialization().getTitle());
			setEmployeeSpecializationID(employee.getLastSpecialization().getId());
		}
		if (employee.getRegularDetail() != null)
			setEmployeeCode(employee.getRegularDetail().getRegistryID());

		if (employee.getCurrentEmployment() != null) {
			Employment employment = employee.getCurrentEmployment();
			setEmployeeFinalWorkingHours(employment.getFinalWorkingHours());
			setEmployeeMandatoryHours(employment.getMandatoryWorkingHours());
			setEmployeeEmploymentEstablishedDate(employment.getEstablished());
			setEmployeeEmploymentTerminatedDate(employment.getTerminated());
		}

	}

	/**
	 * @param employment
	 */
	public SchoolUniversalEmploymentItem(Employment employment) {
		super(employment);
	}

	public SchoolUniversalEmploymentItem(Secondment secondment) {

		super(secondment.getAffectedEmployment());
	}

	public SchoolUniversalEmploymentItem(ServiceAllocation serviceAllocation) {
		super(serviceAllocation.getAffectedEmployment());
	}

	/**
	 * @return the employmentComment
	 */
	public String getEmploymentComment() {
		return employmentComment;
	}

	/**
	 * @param employmentComment the employmentComment to set
	 */
	public void setEmploymentComment(String employmentComment) {
		this.employmentComment = employmentComment;
	}

}
