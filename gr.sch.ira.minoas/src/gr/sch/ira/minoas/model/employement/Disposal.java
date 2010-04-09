/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Unit;
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

/**
 * @author slavikos
 * 
 */
@Entity
@Table(name = "DISPOSAL")
public class Disposal extends BaseIDModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean active;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "PARENT_EMPLOYMENT_ID", nullable = true)
	private Employment affectedEmployment;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "PARENT_SECONDMENT_ID", nullable = true)
	private Secondment affectedSecondment;

	@Basic
	@Column(name = "COMMENT", nullable = true)
	private String comment;

	@Basic
	@Column(name = "DAYS", nullable = false)
	private Integer days;

	@ManyToOne
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Unit disposalUnit;

	@Basic
	@Column(name = "DUE_TO", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dueTo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Basic
	@Column(name = "ESTABLISHED", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date established;

	@Basic
	@Column(name = "HEADMASTER_ORDER", nullable = true, length = 25)
	private String headMasterOrder;

	@Basic
	@Column(name = "HOURS", nullable = false)
	private Integer hours;

	@Basic
	@Column(name = "PYSDE_ORDER", nullable = true, length = 25)
	private String pysdeOrder;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISPOSAL_TARGET_TYPE", nullable = false)
	private DisposalTargetType targetType;

	@Enumerated(EnumType.STRING)
	@Column(name = "DISPOSAL_TYPE", nullable = false)
	private DisposalType type;

	/**
	 * 
	 */
	public Disposal() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the affectedEmployment
	 */
	public Employment getAffectedEmployment() {
		return affectedEmployment;
	}

	/**
	 * @return the affectedSecondment
	 */
	public Secondment getAffectedSecondment() {
		return affectedSecondment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @return the disposalUnit
	 */
	public Unit getDisposalUnit() {
		return disposalUnit;
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
	 * @return the headMasterOrder
	 */
	public String getHeadMasterOrder() {
		return headMasterOrder;
	}

	/**
	 * @return the hours
	 */
	public Integer getHours() {
		return hours;
	}

	/**
	 * @return the pysdeOrder
	 */
	public String getPysdeOrder() {
		return pysdeOrder;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the targetType
	 */
	public DisposalTargetType getTargetType() {
		return targetType;
	}

	/**
	 * @return the type
	 */
	public DisposalType getType() {
		return type;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @param affectedEmployment the affectedEmployment to set
	 */
	public void setAffectedEmployment(Employment affectedEmployment) {
		this.affectedEmployment = affectedEmployment;
	}

	/**
	 * @param affectedSecondment the affectedSecondment to set
	 */
	public void setAffectedSecondment(Secondment affectedSecondment) {
		this.affectedSecondment = affectedSecondment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * @param disposalUnit the disposalUnit to set
	 */
	public void setDisposalUnit(Unit disposalUnit) {
		this.disposalUnit = disposalUnit;
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
	 * @param headMasterOrder the headMasterOrder to set
	 */
	public void setHeadMasterOrder(String headMasterOrder) {
		this.headMasterOrder = headMasterOrder;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}

	/**
	 * @param pysdeOrder the pysdeOrder to set
	 */
	public void setPysdeOrder(String pysdeOrder) {
		this.pysdeOrder = pysdeOrder;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @param targetType the targetType to set
	 */
	public void setTargetType(DisposalTargetType targetType) {
		this.targetType = targetType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DisposalType type) {
		this.type = type;
	}

}
