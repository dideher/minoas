/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Specialization extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SPECIALIZATION_ID", length = 6)
	private String id;

	@Column(name = "TITLE", nullable = false, length = 70)
	private String title;
	
	/**
	 * If set to true then this specialization has been created to
	 * support a virtual specialization group
	 */
	@Basic
	@Column(name="IS_VIRTUAL", nullable=true)
	private Boolean isVirtual = Boolean.FALSE;

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

    /**
     * @return the isVirtual
     */
    public Boolean getIsVirtual() {
        return isVirtual;
    }

    /**
     * @param isVirtual the isVirtual to set
     */
    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

}
