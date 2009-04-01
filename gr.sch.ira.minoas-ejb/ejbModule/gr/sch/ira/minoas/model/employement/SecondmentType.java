/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.INamedQueryConstants;
import gr.sch.ira.minoas.model.core.UnitCategory;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author slavikos
 * 
 */
@Entity
@Table(name = "SECONDMENT_TYPE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQuery(name = INamedQueryConstants.NAMED_QUERY_SECONDMENTTYPE_FIND_BY_TITLE, query = "SELECT c FROM SecondmentType c WHERE c.title=:"
		+ INamedQueryConstants.QUERY_PARAMETER_SECONDMENT_TITLE)
public class SecondmentType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MINOAS_SECONDMENT_TYPE_UNIT_CATEGORIES", joinColumns = @JoinColumn(name = "SECONDMENT_TYPE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "UNIT_CATEGORY_ID", referencedColumnName = "ID"))
	private Collection<UnitCategory> suitableCategoryUnits;

	@Basic
	@Column(name = "TITLE", length = 64, nullable = false, unique = true)
	private String title;

	/**
	 * 
	 */
	public SecondmentType() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	protected Collection<UnitCategory> getSuitableCategoryUnits() {
		return suitableCategoryUnits;
	}

	public String getTitle() {
		return title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	protected void setSuitableCategoryUnits(Collection<UnitCategory> suitableCategoryUnits) {
		this.suitableCategoryUnits = suitableCategoryUnits;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
