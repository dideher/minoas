package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmploymentReportItem extends EmployeeReportItem {

	private String employmentSpecialization;

	private String employmentSpecializationID;
	
	private String employmentType;
	
	private String employmentTypeKey;

	private String school;
	
	private Character schoolRegion;

	/**
	 * 
	 */
	public EmploymentReportItem() {
		super();
	}

	/**
	 * Create an EmploymentReportItem from an employment.
	 * @param employment
	 */
	public EmploymentReportItem(Employment employment) {
		super(employment.getEmployee());
		configureItemFromEmployment(employment);
	}
	
	/**
     * Create an EmploymentReportItem from an disposal. There are situations
     * where an disposal is accosiated with an employment or with a secodnment. 
     * This is very true for employees from other PYSDE with no regular employment on the 
     * local PYSDE.
     *  
     * @param disposal
     */
	public EmploymentReportItem(Disposal disposal) {
	    super(disposal.getEmployee());
	    if(disposal.getAffectedEmployment()!=null)
	        configureItemFromEmployment(disposal.getAffectedEmployment());
	    else if(disposal.getAffectedSecondment()!=null) 
	        configureItemFromSecondment(disposal.getAffectedSecondment());
	}
	
	/**
	 * Create an EmploymentReportItem from an secondment. There are situations
	 * where an secondment is not accosiated with an employment. This is very
	 * true for employees from other PYSDE with no regular employment on the 
	 * local PYSDE.
	 *  
	 * @param secondment
	 */
	public  EmploymentReportItem(Secondment secondment) {
	    super(secondment.getEmployee());
	    configureItemFromSecondment(secondment);
	    
	}
	
	protected void configureItemFromEmployment(Employment employment) {
	    this.employmentType = employment.getType().toString();
        this.employmentTypeKey = employment.getType().getKey();
        if (employment.getSchool() != null) {
            setSchool(employment.getSchool().getTitle());
            setSchoolRegion(employment.getSchool().getRegionCode());
        }
        setEmploymentSpecialization(employment.getSpecialization().getTitle());
        setEmploymentSpecializationID(employment.getSpecialization().getId());
        setEmployeeFinalWorkingHours(employment.getFinalWorkingHours());
        setEmployeeMandatoryHours(employment.getMandatoryWorkingHours());
        setEmployeeEmploymentEstablishedDate(employment.getEstablished());
        setEmployeeEmploymentTerminatedDate(employment.getTerminated());
	}
	
	protected void configureItemFromSecondment(Secondment secondment) {
	  //this.employmentType = employment.getType().toString();
        //this.employmentTypeKey = employment.getType().getKey();
        if (secondment.getTargetUnit() != null) {
            setSchool(secondment.getTargetUnit().getTitle());
            if(secondment.getTargetUnit() instanceof School)
            setSchoolRegion(((School)secondment.getTargetUnit()).getRegionCode());
        }
        /*
        setEmploymentSpecialization(employment.getSpecialization().getTitle());
        setEmploymentSpecializationID(employment.getSpecialization().getId());
        setEmployeeFinalWorkingHours(employment.getFinalWorkingHours());
        setEmployeeMandatoryHours(employment.getMandatoryWorkingHours());
        setEmployeeEmploymentEstablishedDate(employment.getEstablished());
        setEmployeeEmploymentTerminatedDate(employment.getTerminated());
        */
	}

	/**
	 * @return the employmentSpecialization
	 */
	public String getEmploymentSpecialization() {
		return employmentSpecialization;
	}

	/**
	 * @return the employmentSpecializationID
	 */
	public String getEmploymentSpecializationID() {
		return employmentSpecializationID;
	}

	/**
	 * @return the leaveType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @return the leaveTypeKey
	 */
	public String getEmploymentTypeKey() {
		return employmentTypeKey;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @return the schoolRegion
	 */
	public Character getSchoolRegion() {
		return schoolRegion;
	}

	/**
	 * @param employmentSpecialization the employmentSpecialization to set
	 */
	public void setEmploymentSpecialization(String employmentSpecialization) {
		this.employmentSpecialization = employmentSpecialization;
	}

	/**
	 * @param employmentSpecializationID the employmentSpecializationID to set
	 */
	public void setEmploymentSpecializationID(String employmentSpecializationID) {
		this.employmentSpecializationID = employmentSpecializationID;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setEmploymentType(String leaveType) {
		this.employmentType = leaveType;
	}

	/**
	 * @param leaveTypeKey the leaveTypeKey to set
	 */
	public void setEmploymentTypeKey(String leaveTypeKey) {
		this.employmentTypeKey = leaveTypeKey;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @param schoolRegion the schoolRegion to set
	 */
	public void setSchoolRegion(Character schoolRegion) {
		this.schoolRegion = schoolRegion;
	}

}
