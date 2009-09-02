package gr.sch.ira.minoas.seam.components.criteria;

import java.util.Calendar;
import java.util.Date;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentCriteria")
@Scope(ScopeType.CONVERSATION)
public class SecondmentCriteria  {
	private String comment;
	
	private DateSearchType dateSearchType;
	
	private Date effectiveDate;
	
	private Date effectiveDateFrom;
	
	private Date effectiveDateUntil;
	
	private boolean employeeRequested = true;

	private Character region;

	private SecondmentType secondmentType;
	
	private Unit targetUnit;
	
	private SpecializationGroup specializationGroup;

	/**
	 * 
	 */
	public SecondmentCriteria() {
		super();
		effectiveDate = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
		effectiveDateFrom = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
		dateSearchType = DateSearchType.DURING_DATE;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
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
	 * @return the region
	 */
	public Character getRegion() {
		return region;
	}

	/**
	 * @return the secondmentType
	 */
	public SecondmentType getSecondmentType() {
		return secondmentType;
	}

	/**
	 * @return the targetUnit
	 */
	public Unit getTargetUnit() {
		return targetUnit;
	}

	/**
	 * @return the employeeRequested
	 */
	public boolean isEmployeeRequested() {
		return employeeRequested;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @param employeeRequested the employeeRequested to set
	 */
	public void setEmployeeRequested(boolean employeeRequested) {
		this.employeeRequested = employeeRequested;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Character region) {
		this.region = region;
	}

	/**
	 * @param secondmentType the secondmentType to set
	 */
	public void setSecondmentType(SecondmentType secondmentType) {
		this.secondmentType = secondmentType;
	}

	/**
	 * @param targetUnit the targetUnit to set
	 */
	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
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
}
