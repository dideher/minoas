package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SchoolType;
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
@Name("outstandingImprovementCriteria")
@Scope(ScopeType.CONVERSATION)
public class OutstandingImprovementCriteria {

	private SpecializationGroup specializationGroup;

	private Character sourceRegion;

	private Character targetRegion;

	/**
	 * 
	 */
	public OutstandingImprovementCriteria() {
		super();

	}

	/**
	 * @return the sourceRegion
	 */
	public Character getSourceRegion() {
		return sourceRegion;
	}

	/**
	 * @param sourceRegion the sourceRegion to set
	 */
	public void setSourceRegion(Character sourceRegion) {
		this.sourceRegion = sourceRegion;
	}

	/**
	 * @return the targetRegion
	 */
	public Character getTargetRegion() {
		return targetRegion;
	}

	/**
	 * @param targetRegion the targetRegion to set
	 */
	public void setTargetRegion(Character targetRegion) {
		this.targetRegion = targetRegion;
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
