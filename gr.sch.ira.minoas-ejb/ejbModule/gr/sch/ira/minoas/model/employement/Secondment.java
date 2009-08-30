/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.INamedQueryConstants;
import gr.sch.ira.minoas.model.core.PYSDE;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "SECONDMENT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Secondment extends BaseIDModel implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A secondment may or may not be active.
	 */
	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private boolean active;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "PARENT_EMPLOYMENT_ID", nullable = true)
	private Employment affectedEmployment;

	@Basic
	@Column(name = "COMMENT", nullable = true, length = 255)
	private String comment;

	@Basic
	@Column(name = "DUE_TO", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dueTo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Basic
	@Column(name = "EMPLOYEE_REQUESTED", nullable = true)
	private boolean employeeRequested;

	@Basic
	@Column(name = "ESTABLISHED", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date established;

	@Basic
	@Column(name = "FINAL_WORKING_HOURS", nullable = true)
	private Integer finalWorkingHours;

	@Basic
	@Column(name = "HEADMASTER_ORDER", nullable = true, length = 25)
	private String headMasterOrder;

	@Basic
	@Column(name = "MANDATORY_WORKING_HOURS", nullable = true)
	private Integer mandatoryWorkingHours;

	@Basic
	@Column(name = "MINISTERIAL_ORDER", nullable = true, length = 25)
	private String ministerialOrder;

	@Basic
	@Column(name = "PYSDE_ORDER", nullable = true, length = 25)
	private String pysdeOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPLACEMENT_EMPLOYMENT_ID", nullable = true)
	private Employment replacementFor;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@Enumerated(EnumType.STRING)
	@Column(name = "SECONDMENT_TYPE")
	private SecondmentType secondmentType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOURCE_PYSDE_ID", nullable = true)
	private PYSDE sourcePYSDE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOURCE_UNIT_ID", nullable = false)
	private Unit sourceUnit;

	/**
	 * A secondment may be superseded by another secondment
	 */
	@OneToOne
	@JoinColumn(name = "SUPERSEDED_BY_ID", nullable = true)
	private Secondment supersededBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TARGET_PYSDE_ID", nullable = true)
	private PYSDE targetPYSDE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TARGET_UNIT_ID", nullable = false)
	private Unit targetUnit;

	@Basic
	@Column(name = "WORK_HRS_DECR", nullable = true)
	private Integer workingHoursDecrement;

	@Basic
	@Column(name = "WORK_HRS_DECR_REASON", nullable = true)
	private String workingHoursDecrementReason;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Secondment clone = (Secondment) super.clone();
		clone.setId(null);
		return clone;
	}

	/**
	 * @return the affectedEmployment
	 */
	public Employment getAffectedEmployment() {
		return affectedEmployment;
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

	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the established
	 */
	public Date getEstablished() {
		return established;
	}

	

	public Integer getFinalWorkingHours() {
		return finalWorkingHours;
	}

	public String getHeadMasterOrder() {
		return headMasterOrder;
	}

	/**
	 * @return the mandatoryWorkingHours
	 */
	public Integer getMandatoryWorkingHours() {
		return mandatoryWorkingHours;
	}

	public String getMinisterialOrder() {
		return ministerialOrder;
	}

	public String getPysdeOrder() {
		return pysdeOrder;
	}

	/**
	 * @return the replacementFor
	 */
	public Employment getReplacementFor() {
		return replacementFor;
	}

	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	public SecondmentType getSecondmentType() {
		return secondmentType;
	}

	public PYSDE getSourcePYSDE() {
		return sourcePYSDE;
	}

	public Unit getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @return the supersededBy
	 */
	public Secondment getSupersededBy() {
		return supersededBy;
	}

	public PYSDE getTargetPYSDE() {
		return targetPYSDE;
	}

	public Unit getTargetUnit() {
		return targetUnit;
	}

	public Integer getWorkingHoursDecrement() {
		return workingHoursDecrement;
	}

	public String getWorkingHoursDecrementReason() {
		return workingHoursDecrementReason;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the employeeRequested
	 */
	public boolean isEmployeeRequested() {
		return employeeRequested;
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

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param employeeRequested the employeeRequested to set
	 */
	public void setEmployeeRequested(boolean employeeRequested) {
		this.employeeRequested = employeeRequested;
	}

	/**
	 * @param established the established to set
	 */
	public void setEstablished(Date established) {
		this.established = established;
	}

	public void setFinalWorkingHours(Integer finalWorkingHours) {
		this.finalWorkingHours = finalWorkingHours;
	}

	public void setHeadMasterOrder(String headMasterOrder) {
		this.headMasterOrder = headMasterOrder;
	}

	/**
	 * @param mandatoryWorkingHours the mandatoryWorkingHours to set
	 */
	public void setMandatoryWorkingHours(Integer mandatoryWorkingHours) {
		this.mandatoryWorkingHours = mandatoryWorkingHours;
	}

	public void setMinisterialOrder(String ministerialOrder) {
		this.ministerialOrder = ministerialOrder;
	}

	public void setPysdeOrder(String pysdeOrder) {
		this.pysdeOrder = pysdeOrder;
	}

	/**
	 * @param replacementFor the replacementFor to set
	 */
	public void setReplacementFor(Employment replacementFor) {
		this.replacementFor = replacementFor;
	}

	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setSecondmentType(SecondmentType secondmentType) {
		this.secondmentType = secondmentType;
	}

	public void setSourcePYSDE(PYSDE sourcePYSDE) {
		this.sourcePYSDE = sourcePYSDE;
	}

	public void setSourceUnit(Unit sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @param supersededBy the supersededBy to set
	 */
	public void setSupersededBy(Secondment supersededBy) {
		this.supersededBy = supersededBy;
	}

	public void setTargetPYSDE(PYSDE targetPYSDE) {
		this.targetPYSDE = targetPYSDE;
	}

	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
	}

	public void setWorkingHoursDecrement(Integer workingHoursDecrement) {
		this.workingHoursDecrement = workingHoursDecrement;
	}

	public void setWorkingHoursDecrementReason(String workingHoursDecrementReason) {
		this.workingHoursDecrementReason = workingHoursDecrementReason;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Secondment [");
		if (sourceUnit != null) {
			builder.append("sourceUnit=");
			builder.append(sourceUnit);
			builder.append(", ");
		}
		if (targetUnit != null) {
			builder.append("targetUnit=");
			builder.append(targetUnit);
			builder.append(", ");
		}
		if (employee != null) {
			builder.append("employee=");
			builder.append(employee);
			builder.append(", ");
		}
		if (finalWorkingHours != null) {
			builder.append("finalWorkingHours=");
			builder.append(finalWorkingHours);
			builder.append(", ");
		}
		if (schoolYear != null) {
			builder.append("schoolYear=");
			builder.append(schoolYear);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

}
