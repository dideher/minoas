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
	 * A flag to denote if this specialization has been disabled.
	 */
	private Boolean disabled = Boolean.FALSE;
	
	
	/**
	 * The public specialization ID that should be used when printing reports, etc.
	 */
	@Basic
	@Column(name = "PUBLIC_SPECIALIZATION_ID", length = 6, nullable=true)
    private String publicId;

	
	/**
	 * The public specialization title that should be used when printintg reports, etc
	 */
	@Basic
	@Column(name = "PUBLIC_TITLE", nullable = true, length = 70)
    private String publicTitle;
	
	
	
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

    /**
     * Returns the public specialization id. If there is no public id, then
     * the legacy ID will be returned instead.
     * @return the publicId
     */
    public String getPublicId() {
        return publicId != null ? publicId : id;
    }

    /**
     * @param publicId the publicId to set
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * Returns the public specialization title. If there is no public title, then
     * the legacy title will be returned instead.
     * @return the publicTitle
     */
    public String getPublicTitle() {
        return publicTitle != null ? publicTitle : title;
    }

    /**
     * @param publicTitle the publicTitle to set
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
    
    

}
