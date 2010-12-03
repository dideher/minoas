/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
@Entity
@Table(name = "TELEPHONE")
public class Telephone extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 60)
	private String comment;

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
