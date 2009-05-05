package gr.sch.ira.minoas.model.preparatory;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "PREP_OWNER")
public class PreparatoryOwner extends PreparatoryOwnerOrDirector {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<PreparatoryUnit> owningUnits = new HashSet<PreparatoryUnit>();

}
