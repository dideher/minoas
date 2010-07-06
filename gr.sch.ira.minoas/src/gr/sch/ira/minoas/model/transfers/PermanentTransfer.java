package gr.sch.ira.minoas.model.transfers;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.Unit;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PERMANENT_TRANSFER", uniqueConstraints=@UniqueConstraint(columnNames={"EMPLOYEE_ID", "SCHOOLYEAR_ID"}))
public class PermanentTransfer extends BaseOutstandingTransfer {
	
	@Basic
	@Column(name = "NEW_EMPLOYEE", nullable=true)
	private Boolean newEmployee;
	
	@ManyToOne
	@JoinColumn(name="SOURCE_UNIT_ID", nullable=true)
	private Unit sourceUnit;

	@ManyToOne
	@JoinColumn(name="TARGET_UNIT_ID", nullable=false)
	private Unit targetUnit;
	
	@Basic
	@Column(name = "SOURCE_REGION", nullable=true)
	private Character sourceRegionCode;
	
	@Basic
	@Column(name = "TARGET_REGION", nullable=true)
	private Character targetRegionCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSFER_TYPE", nullable=true)
	private PermanentTransferType type;

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

	/**
	 * @return the type
	 */
	public PermanentTransferType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PermanentTransferType type) {
		this.type = type;
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
