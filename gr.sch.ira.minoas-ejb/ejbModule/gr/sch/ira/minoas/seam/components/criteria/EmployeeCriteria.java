package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employee.EmployeeType;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeCriteria")
@Scope(ScopeType.CONVERSATION)
public class EmployeeCriteria {
	
	private String firstName;
	
	private String lastName;
	
	private Specialization specialization;
	
	private SpecializationSearchType specializationSearchType;
	
	private SpecializationGroup specializationGroup;
	
	private EmployeeType type;
	
	private DateSearchType dateSearchType;

	private Date effectiveDate;

	private Date effectiveDateFrom;
	
	private Date effectiveDateUntil;
	
	private Boolean bigFamily;
	
	private Boolean specialCategory;

	/**
	 * @return the type
	 */
	public EmployeeType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EmployeeType type) {
		this.type = type;
	}
	private boolean onlyActive = true;
	/**
	 * 
	 */
	public EmployeeCriteria() {
		super();
		this.dateSearchType = DateSearchType.AFTER_DATE;
		this.specializationSearchType = SpecializationSearchType.SPECIALIZATION;
	}
	/**
	 * @return the onlyActive
	 */
	public boolean isOnlyActive() {
		return onlyActive;
	}
	/**
	 * @param onlyActive the onlyActive to set
	 */
	public void setOnlyActive(boolean onlyActive) {
		this.onlyActive = onlyActive;
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
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}
	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	/**
	 * @return the dateSearchType
	 */
	public DateSearchType getDateSearchType() {
		return dateSearchType;
	}
	/**
	 * @param dateSearchType the dateSearchType to set
	 */
	public void setDateSearchType(DateSearchType dateSearchType) {
		this.dateSearchType = dateSearchType;
	}
	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the effectiveDateFrom
	 */
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}
	/**
	 * @param effectiveDateFrom the effectiveDateFrom to set
	 */
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}
	/**
	 * @return the effectiveDateUntil
	 */
	public Date getEffectiveDateUntil() {
		return effectiveDateUntil;
	}
	/**
	 * @param effectiveDateUntil the effectiveDateUntil to set
	 */
	public void setEffectiveDateUntil(Date effectiveDateUntil) {
		this.effectiveDateUntil = effectiveDateUntil;
	}
	/**
	 * @return the specializationSearchType
	 */
	public SpecializationSearchType getSpecializationSearchType() {
		return specializationSearchType;
	}
	/**
	 * @param specializationSearchType the specializationSearchType to set
	 */
	public void setSpecializationSearchType(SpecializationSearchType specializationSearchType) {
		this.specializationSearchType = specializationSearchType;
	}
	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}
	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}
	/**
	 * @return the bigFamily
	 */
	public Boolean getBigFamily() {
		return bigFamily;
	}
	/**
	 * @param bigFamily the bigFamily to set
	 */
	public void setBigFamily(Boolean bigFamily) {
		this.bigFamily = bigFamily;
	}
	/**
	 * @return the specialCategory
	 */
	public Boolean getSpecialCategory() {
		return specialCategory;
	}
	/**
	 * @param specialCategory the specialCategory to set
	 */
	public void setSpecialCategory(Boolean specialCategory) {
		this.specialCategory = specialCategory;
	}

}
