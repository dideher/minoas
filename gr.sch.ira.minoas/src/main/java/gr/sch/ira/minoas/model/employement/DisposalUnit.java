package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.Unit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "DISPOSAL_UNIT", uniqueConstraints = { @UniqueConstraint(columnNames = { "UNIT_ID",
		"DISPOSAL_TARGET_TYPE" }) })
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DisposalUnit extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISPOSAL_TARGET_TYPE", nullable = false)
	private DisposalTargetType type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Unit unit;

	/**
	 * 
	 */
	public DisposalUnit() {
		super();
	}

	/**
	 * @return the type
	 */
	public DisposalTargetType getType() {
		return type;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DisposalTargetType type) {
		this.type = type;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
