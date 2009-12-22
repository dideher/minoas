package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.EmploymentType;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employmentCriteria")
@Scope(ScopeType.CONVERSATION)
public class EmploymentCriteria {

	private DateSearchType dateSearchType;

	private Date effectiveDate;

	private Date effectiveDateFrom;

	private Date effectiveDateUntil;

	private String firstName;

	private String lastName;

	private boolean onlyActive = true;

	private SchoolYear schoolYear;

	private Specialization specialization;

	private SpecializationGroup specializationGroup;

	private SpecializationSearchType specializationSearchType;

	private EmploymentType type;
	
	private String sorting;

	/**
	 * 
	 */
	public EmploymentCriteria() {
		super();
		this.dateSearchType = DateSearchType.AFTER_DATE;
		this.specializationSearchType = SpecializationSearchType.SPECIALIZATION;
	}

	/**
	 * @return the dateSearchType
	 */
	public DateSearchType getDateSearchType() {
		return dateSearchType;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return the effectiveDateFrom
	 */
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	/**
	 * @return the effectiveDateUntil
	 */
	public Date getEffectiveDateUntil() {
		return effectiveDateUntil;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @return the specializationSearchType
	 */
	public SpecializationSearchType getSpecializationSearchType() {
		return specializationSearchType;
	}

	/**
	 * @return the type
	 */
	public EmploymentType getType() {
		return type;
	}

	/**
	 * @return the onlyActive
	 */
	public boolean isOnlyActive() {
		return onlyActive;
	}

	/**
	 * @param dateSearchType the dateSearchType to set
	 */
	public void setDateSearchType(DateSearchType dateSearchType) {
		this.dateSearchType = dateSearchType;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @param effectiveDateFrom the effectiveDateFrom to set
	 */
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	/**
	 * @param effectiveDateUntil the effectiveDateUntil to set
	 */
	public void setEffectiveDateUntil(Date effectiveDateUntil) {
		this.effectiveDateUntil = effectiveDateUntil;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param onlyActive the onlyActive to set
	 */
	public void setOnlyActive(boolean onlyActive) {
		this.onlyActive = onlyActive;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

	/**
	 * @param specializationSearchType the specializationSearchType to set
	 */
	public void setSpecializationSearchType(SpecializationSearchType specializationSearchType) {
		this.specializationSearchType = specializationSearchType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EmploymentType type) {
		this.type = type;
	}

	/**
	 * @return the sorting
	 */
	public String getSorting() {
		return sorting;
	}

	/**
	 * @param sorting the sorting to set
	 */
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

}
