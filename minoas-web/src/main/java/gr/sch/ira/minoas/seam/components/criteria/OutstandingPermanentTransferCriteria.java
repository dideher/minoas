package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Named("outstandingPermanentTransferCriteria")
@ConversationScoped
public class OutstandingPermanentTransferCriteria extends BaseCriteria  {

	private SpecializationGroup specializationGroup;

	private PermanentTransferType transferType;
	
	private Unit sourceUnit;
	
	private Unit targetUnit;



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
	 * @return the transferType
	 */
	public PermanentTransferType getTransferType() {
		return transferType;
	}


	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(PermanentTransferType transferType) {
		this.transferType = transferType;
	}


	/**
	 * @return the sourceUnit
	 */
	public Unit getSourceUnit() {
		return sourceUnit;
	}


	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(Unit sourceUnit) {
		this.sourceUnit = sourceUnit;
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

}
