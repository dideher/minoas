package gr.sch.ira.minoas.model.preparatory;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.Name;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "PRE_TYPE")
@Name("preparatoryUnitType")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PreparatoryUnitNature  extends BaseModel {

	/**
	 * 
	 */
	public PreparatoryUnitNature() {
		super();
	}

	/**
	 * @param type
	 * @param title
	 */
	public PreparatoryUnitNature(PreparatoryUnitNatureType type, String title) {
		super();
		this.type = type;
		this.title = title;
	}

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Basic
	@Column(name = "TITLE", length = 64, nullable = false, unique = true)
	private String title;
	
	@Enumerated(EnumType.STRING)
	@Column(name="UNIT_TYPE", nullable=false, unique=true)
	private PreparatoryUnitNatureType type; 

//	public void addPreparatoryUnit(PreparatoryUnit unit) {
//		unit.setType(this);
//		getPreparatoryUnits().add(unit);
//	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public PreparatoryUnitNatureType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PreparatoryUnitNatureType type) {
		this.type = type;
	}

	
	
}
