/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author slavikos
 * 
 */
@Entity
@Table(name = "SPECIALIZATION")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Specialization extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SPECIALIZATION_ID", length = 6)
	private String id;
	
	
	@Column(name = "TITLE", nullable = false, length = 70)
	private String title;

	/**
	 * 
	 */
	public Specialization() {
		// TODO Auto-generated constructor stub
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return getId();
	}

}
