package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentCriteria")
@Scope(ScopeType.PAGE)
public class SecondmentCriteria  {
	private boolean employeeRequested = true;
	
	private SecondmentType secondmentType;
	
	private Unit targetUnit;
	
	private String region;
	
	private String comment;

	/**
	 * @return the employeeRequested
	 */
	public boolean isEmployeeRequested() {
		return employeeRequested;
	}

	/**
	 * @param employeeRequested the employeeRequested to set
	 */
	public void setEmployeeRequested(boolean employeeRequested) {
		this.employeeRequested = employeeRequested;
	}

	/**
	 * @return the secondmentType
	 */
	public SecondmentType getSecondmentType() {
		return secondmentType;
	}

	/**
	 * @param secondmentType the secondmentType to set
	 */
	public void setSecondmentType(SecondmentType secondmentType) {
		this.secondmentType = secondmentType;
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
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
