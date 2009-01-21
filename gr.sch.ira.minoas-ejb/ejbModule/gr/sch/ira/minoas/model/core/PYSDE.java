/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.employee.Employee;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.Name;

/**
 * @author slavikos
 *
 */
@Entity
@Name("pysde")
@Table(name = "PYSDE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PYSDE extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	/**
	 * 
	 */
	@Basic
	@Column(name = "IS_LOCAL_PYSDE", nullable = false, updatable = true)
	private boolean localPYSDE;

	@OneToOne
	@JoinColumn(name="REPRESENTED_UNIT_ID", nullable=true, updatable=false)
	private Unit representedByUnit;
	
	@Basic
	@Column(name = "TITLE", length = 64, nullable = false, updatable = true, unique = true)
	private String title;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pysde")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Collection<Unit> units;
	
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	@OneToMany(fetch=FetchType.LAZY, mappedBy="currentPYSDE")
	private Collection<Employee> employees;
	
	/**
	 * 
	 */
	public PYSDE() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	/**
	 * @return the representedByUnit
	 */
	public Unit getRepresentedByUnit() {
		return representedByUnit;
	}

	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the units
	 */
	public Collection<Unit> getUnits() {
		return units;
	}
	
	/**
	 * @return the localPYSDE
	 */
	public boolean isLocalPYSDE() {
		return localPYSDE;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param localPYSDE the localPYSDE to set
	 */
	public void setLocalPYSDE(boolean localPYSDE) {
		this.localPYSDE = localPYSDE;
	}
	/**
	 * @param representedByUnit the representedByUnit to set
	 */
	public void setRepresentedByUnit(Unit representedByUnit) {
		this.representedByUnit = representedByUnit;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(Collection<Unit> units) {
		this.units = units;
	}

	/**
	 * @return the employees
	 */
	public Collection<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

}
