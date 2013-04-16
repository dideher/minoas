/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.security.Principal;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author slavikos
 * 
 */
@Entity
@Table(name = "WORK_ATTENDANCE_EVENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkAttendanceEvent extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		

	@Enumerated(EnumType.STRING)
	@Column(name = "EVENT_TYPE", nullable = false)
	private WorkAttendanceEventType attendaceType;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 255)
	private String comment;

	
	public WorkAttendanceEventType getAttendaceType() {
		return attendaceType;
	}

	public void setAttendaceType(WorkAttendanceEventType attendaceType) {
		this.attendaceType = attendaceType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
