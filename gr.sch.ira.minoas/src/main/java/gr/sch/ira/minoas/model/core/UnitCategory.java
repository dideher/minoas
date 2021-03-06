/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @author slavikos
 *
 */
@Entity
@Table(name = "UNIT_CATEGORY")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UnitCategory extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "TITLE", nullable = false, unique = true, length = 64)
	private String title;

	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private Collection<Unit> units;

	/**
	 * 
	 */
	public UnitCategory() {
	}

	public String getTitle() {
		return title;
	}

	public Collection<Unit> getUnits() {
		return units;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUnits(Collection<Unit> units) {
		this.units = units;
	}

}
