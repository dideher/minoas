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

@MappedSuperclass
public class BaseIDDeleteAwareModel extends BaseIDModel {

	@Basic
	@Column(name = "IS_DELETED", nullable = true)
	private Boolean deleted;
	
	/**
	 * 
	 */
	public BaseIDDeleteAwareModel() {
		super();
		setDeleted(Boolean.FALSE);
	}

	@Basic(fetch = FetchType.LAZY)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_ON", nullable = true)
	private Date deletedOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELETED_BY_ID", nullable = true)
	private Principal deletedBy;
	
	@Basic
	@Column(name="DELETED_COMMENT", nullable=true, length=250)
	private String deletedComment;

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the deletedOn
	 */
	public Date getDeletedOn() {
		return deletedOn;
	}

	/**
	 * @param deletedOn the deletedOn to set
	 */
	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	/**
	 * @return the deletedBy
	 */
	public Principal getDeletedBy() {
		return deletedBy;
	}

	/**
	 * @param deletedBy the deletedBy to set
	 */
	public void setDeletedBy(Principal deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	 * @return the deletedComment
	 */
	public String getDeletedComment() {
		return deletedComment;
	}

	/**
	 * @param deletedComment the deletedComment to set
	 */
	public void setDeletedComment(String deletedComment) {
		this.deletedComment = deletedComment;
	}
}
