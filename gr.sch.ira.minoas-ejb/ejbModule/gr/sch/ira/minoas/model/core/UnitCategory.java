/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UnitCategory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Basic
	@Column(name="TITLE", nullable=false, unique=true, length=64)
	private String title;
	
	@ManyToMany(mappedBy="categories", fetch=FetchType.LAZY)
	private Collection<Unit> units;
	/**
	 * 
	 */
	public UnitCategory() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Collection<Unit> getUnits() {
		return units;
	}
	public void setUnits(Collection<Unit> units) {
		this.units = units;
	}
	
	

}
