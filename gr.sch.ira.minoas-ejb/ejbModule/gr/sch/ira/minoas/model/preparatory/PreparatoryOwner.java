package gr.sch.ira.minoas.model.preparatory;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jboss.seam.annotations.Name;

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
