/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.core.Unit;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * @author slavikos
 * 
 */
public class Disposal extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Employment affectedEmployment;

	@OneToMany
	@JoinColumn(name = "UNIT_ID")
	private Unit disposalUnit;

	@Basic
	@Column(name = "ESTABLISHED", nullable = true)
	private Date established;

	@Basic
	@Column(name = "HOURS", nullable = false)
	private Integer hours;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@SuppressWarnings("unused")
	@Version
	private Long version;

	/**
	 * 
	 */
	public Disposal() {
		// TODO Auto-generated constructor stub
	}

}
