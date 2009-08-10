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
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "SECONDMENT_UNIT", uniqueConstraints = { @UniqueConstraint(columnNames = { "UNIT_ID", "SECONDMENT_TYPE" }) })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SecondmentUnit extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Unit unit;

	@Enumerated(EnumType.STRING)
	@Column(name = "SECONDMENT_TYPE", nullable = false)
	private SecondmentType type;

	/**
	 * 
	 */
	public SecondmentUnit() {
		super();
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * @return the type
	 */
	public SecondmentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SecondmentType type) {
		this.type = type;
	};
}
