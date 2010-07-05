package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class OutstandingPermanentTransferItem extends EmployeeReportItem {

	private String sourceUnit;
	
	private String targetUnit;
	
	private Character sourceRegionCode;
	
	private Character targetRegionCode;
	
	private String transferType;
	
	private String transferTypeKey;
	
	private String sourcePysde;
	
	private String targetPysde;
	/**
	 * 
	 */
	public OutstandingPermanentTransferItem() {
		super();
	}

	/**
	 * @param employee
	 */
	public OutstandingPermanentTransferItem(PermanentTransfer permanentTransfer) {
		super(permanentTransfer.getEmployee());
		setId(permanentTransfer.getId());
		Unit sourceUnit = permanentTransfer.getSourceUnit();
		if(sourceUnit!=null) {
			setSourceUnit(sourceUnit.getTitle());
			if(sourceUnit.getPysde()!=null)
				setSourcePysde(sourceUnit.getPysde().getTitle());
			if(sourceUnit instanceof School) {
				setSourceRegionCode(((School)sourceUnit).getRegionCode());
			}
		}
		Unit targetUnit = permanentTransfer.getTargetUnit();
		if(targetUnit!=null) {
			setTargetUnit(targetUnit.getTitle());
			if(targetUnit.getPysde()!=null)
				setTargetPysde(targetUnit.getPysde().getTitle());
			if(targetUnit instanceof School) {
				setTargetRegionCode(((School)targetUnit).getRegionCode());
			}
		}
		setTransferType(permanentTransfer.getType().name());
		setTransferTypeKey(permanentTransfer.getType().getKey());
		
	}

	/**
	 * @return the sourceUnit
	 */
	public String getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @return the targetUnit
	 */
	public String getTargetUnit() {
		return targetUnit;
	}

	/**
	 * @param targetUnit the targetUnit to set
	 */
	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}

	
	/**
	 * @return the transferType
	 */
	public String getTransferType() {
		return transferType;
	}

	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	/**
	 * @return the transferTypeKey
	 */
	public String getTransferTypeKey() {
		return transferTypeKey;
	}

	/**
	 * @param transferTypeKey the transferTypeKey to set
	 */
	public void setTransferTypeKey(String transferTypeKey) {
		this.transferTypeKey = transferTypeKey;
	}

	/**
	 * @return the sourcePysde
	 */
	public String getSourcePysde() {
		return sourcePysde;
	}

	/**
	 * @param sourcePysde the sourcePysde to set
	 */
	public void setSourcePysde(String sourcePysde) {
		this.sourcePysde = sourcePysde;
	}

	/**
	 * @return the targetPysde
	 */
	public String getTargetPysde() {
		return targetPysde;
	}

	/**
	 * @param targetPysde the targetPysde to set
	 */
	public void setTargetPysde(String targetPysde) {
		this.targetPysde = targetPysde;
	}

	/**
	 * @return the sourceRegionCode
	 */
	public Character getSourceRegionCode() {
		return sourceRegionCode;
	}

	/**
	 * @param sourceRegionCode the sourceRegionCode to set
	 */
	public void setSourceRegionCode(Character sourceRegionCode) {
		this.sourceRegionCode = sourceRegionCode;
	}

	/**
	 * @return the targetRegionCode
	 */
	public Character getTargetRegionCode() {
		return targetRegionCode;
	}

	/**
	 * @param targetRegionCode the targetRegionCode to set
	 */
	public void setTargetRegionCode(Character targetRegionCode) {
		this.targetRegionCode = targetRegionCode;
	}

	
	

}
