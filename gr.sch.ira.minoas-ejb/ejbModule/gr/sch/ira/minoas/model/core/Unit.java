/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "UNIT")
@Table(name = "MINOAS_UNIT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQuery(name = Unit.NAMED_QUERY_FIND_UNIT_BY_TITLE, query = "SELECT u FROM Unit u WHERE u.title=:"
		+ Unit.QUERY_PARAMETER_UNIT_TITLE)
public class Unit extends BaseModel {

	public static final String NAMED_QUERY_FIND_UNIT_BY_TITLE = "findUnitByTitle";

	public static final String QUERY_PARAMETER_UNIT_TITLE = "title";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Address address;

	@Id
	@Column(name = "UNIT_ID", length = 3)
	private String id;

	@Basic
	@Column(name = "TITLE", nullable = false, unique = true, length = 80)
	private String title;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MINOAS_UNIT_TELEPHONES")
	private List<Telephone> telephones;

	@ManyToMany(fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@JoinTable(name = "MINOAS_UNIT_GATEGORIES", joinColumns = @JoinColumn(name = "UNIT_ID", referencedColumnName = "UNIT_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID"))
	private Collection<UnitCategory> categories;

	@SuppressWarnings("unused")
	@Version
	private Long version;

	@ManyToOne(fetch=FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@JoinColumn(name = "PYSDE_ID", nullable = true)
	private PYSDE pysde;

	/**
	 * 
	 */
	public Unit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the telephones
	 */
	public List<Telephone> getTelephones() {
		return telephones;
	}

	/**
	 * @param telephones
	 *            the telephones to set
	 */
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public PYSDE getPysde() {
		return pysde;
	}

	public void setPysde(PYSDE pysde) {
		this.pysde = pysde;
	}

}
