package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SpecializationGroup;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Named("outstandingImprovementCriteria")
@ConversationScoped
public class OutstandingImprovementCriteria extends BaseCriteria  {

	private SpecializationGroup specializationGroup;

	private Character improvementRegion;



	/**
	 * 
	 */
	public OutstandingImprovementCriteria() {
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
