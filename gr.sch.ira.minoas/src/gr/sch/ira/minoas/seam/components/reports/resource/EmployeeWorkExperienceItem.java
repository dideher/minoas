/**
 * 
 */
package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.WorkExperience;

import java.util.Date;

/**
 * @author slavikos
 * 
 */
public class EmployeeWorkExperienceItem extends BaseIDReportItem {

	protected EmployeeReportItem employee;

	protected Date established;

	protected Date dueTo;

	protected String comment;

	protected Integer typeId;

	protected String typeTitle;
	
	public EmployeeWorkExperienceItem(WorkExperience workExperience) {
		super(workExperience);
		if (workExperience.getEmployee() != null)
			employee = new EmployeeReportItem(workExperience.getEmployee());
		established = workExperience.getFromDate();
		dueTo = workExperience.getToDate();
		comment = workExperience.getComment();
		if (workExperience.getType() != null) {
			typeId = workExperience.getType().getId();
			typeTitle = workExperience.getType().getTitle();
		}
	}

	/**
	 * @return the employee
	 */
	public EmployeeReportItem getEmployee() {
		return employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(EmployeeReportItem employee) {
		this.employee = employee;
	}

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
	 * @param established
	 *            the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @param dueTo
	 *            the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
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

	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the typeTitle
	 */
	public String getTypeTitle() {
		return typeTitle;
	}

	/**
	 * @param typeTitle
	 *            the typeTitle to set
	 */
	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

}
