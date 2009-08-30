package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.Employee;

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
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "EMPLOYEE_LEAVE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Leave extends BaseIDModel {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;



	@Basic
	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Leave [");
		if (employee != null) {
			builder.append("employee=");
			builder.append(employee);
			builder.append(", ");
		}
		if (leaveType != null) {
			builder.append("leaveType=");
			builder.append(leaveType);
			builder.append(", ");
		}
		if (established != null) {
			builder.append("established=");
			builder.append(established);
			builder.append(", ");
		}
		if (dueTo != null) {
			builder.append("dueTo=");
			builder.append(dueTo);
		}
		builder.append("]");
		return builder.toString();
	}

	@Basic
	@Column(name="COMMENT", nullable=true, length=255)
	private String comment;

	@Basic
	@Column(name = "DUE_TO", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dueTo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
	@Basic
	@Column(name = "ESTABLISHED", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date established;
	
	@Enumerated(EnumType.STRING)
	@Column(name="LEAVE_TYPE", length=64, nullable=false)
	private LeaveType leaveType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="REGULAR_SCHOOL_ID", nullable=true)
	private School regularSchool;
	
	
	/**
	 * @return the regularSchool
	 */
	public School getRegularSchool() {
		return regularSchool;
	}

	/**
	 * @param regularSchool the regularSchool to set
	 */
	public void setRegularSchool(School regularSchool) {
		this.regularSchool = regularSchool;
	}

	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	/**
	 * @return the leaveType
	 */
	public LeaveType getLeaveType() {
		return leaveType;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	/**
	 * @param leaveType the leaveType to set
	 */
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	

	
}
