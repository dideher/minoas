/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.security.Principal;

import java.util.Date;

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

/**
 * @author slavikos
 * 
 */
@Entity
@Table(name = "AUDIT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Audit extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name="AUDIT_COMMENT", length=250, nullable=true)
	private String comment;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="AUDIT_TYPE", nullable=false)
	private AuditType type;
	/**
	 * 
	 */
	public Audit() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Audit(String comment, Principal principal) {
		this(AuditType.GENERAL, comment, principal);
	}
	
	public Audit(AuditType type, String comment, Principal principal) {
		super();
		this.comment = comment;
		this.type = type;
		setInsertedBy(principal);
		setInsertedOn(new Date(System.currentTimeMillis()));
	}

	public String getComment() {
		return comment;
	}

	public Integer getId() {
		return id;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the type
	 */
	public AuditType getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(AuditType type) {
		this.type = type;
	}

}
