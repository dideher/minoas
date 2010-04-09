/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.AbstractArchivableEntity;

import java.util.ArrayList;
import java.util.Collection;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "UNIT_TYPE")
@Table(name = "UNIT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Unit extends AbstractArchivableEntity implements Comparable<Unit> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID", nullable = true)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Address address;

	@ManyToMany(fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@JoinTable(name = "MINOAS_UNIT_GATEGORIES", joinColumns = @JoinColumn(name = "UNIT_ID", referencedColumnName = "UNIT_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID"))
	private Collection<UnitCategory> categories = new ArrayList<UnitCategory>();

	@Id
	@Column(name = "UNIT_ID", length = 4)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICE_ID", nullable = true)
	private OrganizationalOffice office;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@JoinColumn(name = "PYSDE_ID", nullable = true)
	private PYSDE pysde;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MINOAS_UNIT_TELEPHONES")
	private Collection<Telephone> telephones = new ArrayList<Telephone>();

	@Basic
	@Column(name = "TITLE", nullable = false, unique = true, length = 80)
	private String title;

	/**
	 * 
	 */
	public Unit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Unit o) {
		return this.title.compareTo(o.getTitle());
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the categories
	 */
	protected Collection<UnitCategory> getCategories() {
		return categories;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the office
	 */
	public OrganizationalOffice getOffice() {
		return office;
	}

	public PYSDE getPysde() {
		return pysde;
	}

	/**
	 * @return the telephones
	 */
	public Collection<Telephone> getTelephones() {
		return telephones;
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
	 * @param categories the categories to set
	 */
	protected void setCategories(Collection<UnitCategory> categories) {
		this.categories = categories;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param office the office to set
	 */
	public void setOffice(OrganizationalOffice office) {
		this.office = office;
	}

	public void setPysde(PYSDE pysde) {
		this.pysde = pysde;
	}

	/**
	 * @param telephones
	 *            the telephones to set
	 */
	public void setTelephones(Collection<Telephone> telephones) {
		this.telephones = telephones;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append("");
		sb.append(getTitle());
		sb.append("] ");
		return sb.toString();
	}

}
