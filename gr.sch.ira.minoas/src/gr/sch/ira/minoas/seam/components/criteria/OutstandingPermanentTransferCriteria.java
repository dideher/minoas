package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("outstandingPermanentTransferCriteria")
@Scope(ScopeType.CONVERSATION)
public class OutstandingPermanentTransferCriteria {

	private SpecializationGroup specializationGroup;

	private Character improvementRegion;



	/**
	 * 
	 */
	public OutstandingPermanentTransferCriteria() {
		super();

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
	 * @return the improvementRegion
	 */
	public Character getImprovementRegion() {
		return improvementRegion;
	}

	/**
	 * @param improvementRegion the improvementRegion to set
	 */
	public void setImprovementRegion(Character improvementRegion) {
		this.improvementRegion = improvementRegion;
	}

}
