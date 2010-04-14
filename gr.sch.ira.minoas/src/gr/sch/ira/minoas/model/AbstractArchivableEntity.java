
package gr.sch.ira.minoas.model;

import gr.sch.ira.minoas.model.security.Principal;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@MappedSuperclass
public abstract class AbstractArchivableEntity extends BaseModel {

	/* We need to add a link to archives instances */

	//@OneToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="PREVIOUS_INSTANCE_ID", nullable=true)
	//private T previousInstance;
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "MODIFICATION_REASON", length = 128, nullable = true, updatable = false)
	private String modificationReason;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_BY_ID", nullable = true)
	private Principal modifiedBy;

	@Basic(fetch = FetchType.LAZY)
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_ON", nullable = true, updatable = false)
	private Date modifiedOn;

	/**
	 * @return the modificationReason
	 */
	public String getModificationReason() {
		return modificationReason;
	}

	/**
	 * @return the modifiedBy
	 */
	public Principal getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modificationReason the modificationReason to set
	 */
	public void setModificationReason(String modificationReason) {
		this.modificationReason = modificationReason;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Principal modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
