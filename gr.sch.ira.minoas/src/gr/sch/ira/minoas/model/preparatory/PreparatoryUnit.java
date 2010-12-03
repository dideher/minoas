package gr.sch.ira.minoas.model.preparatory;

import gr.sch.ira.minoas.model.core.Address;
import gr.sch.ira.minoas.model.core.Unit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "PREP_UNIT")
public class PreparatoryUnit extends Unit {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID", nullable = true)
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID", nullable = false)
	private PreparatoryOwner owner;

	/**
	 * 
	 */
	public PreparatoryUnit() {
		super();
	}

}
