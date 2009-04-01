/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
@Entity
@Table(name = "TELEPHONE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Telephone extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 60)
	private String comment;

	@Id
	@Column(name = "TELEPHONE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	@Column(name = "NUMBER", nullable = false, length = 20)
	private String number;

	@Basic
	@Enumerated
	@Column(name = "TYPE", nullable = true)
	private TelephoneNumberType type;

	/**
	 * 
	 */
	public Telephone() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @return the type
	 */
	public TelephoneNumberType getType() {
		return type;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TelephoneNumberType type) {
		this.type = type;
	}

}
