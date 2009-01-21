/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseModel;
import gr.sch.ira.minoas.model.INamedQueryConstants;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.Person;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.Name;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Table(name = "SECONDMENT")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Name("secondment")
@NamedQueries( {
		@NamedQuery(name = INamedQueryConstants.NAMED_QUERY_SECONDMENT_FIND_ALL_ACTIVE, query = "SELECT c FROM Secondment c WHERE c.active=TRUE AND c.supersededBy IS NULL AND c.schoolYear=:"
				+ INamedQueryConstants.QUERY_PARAMETER_SCHOOL_YEAR),
		@NamedQuery(name = INamedQueryConstants.NAMED_QUERY_SECONDMENT_FIND_ALL_WITHIN_PYSDE, query = "SELECT c FROM Secondment c WHERE c.active=TRUE AND c.supersededBy IS NULL AND c.sourcePYSDE=c.targetPYSDE AND c.schoolYear=:"
				+ INamedQueryConstants.QUERY_PARAMETER_SCHOOL_YEAR),
		@NamedQuery(name = INamedQueryConstants.NAMED_QUERY_SECONDMENT_FIND_SCHOOL_INCOMING, query = "SELECT c FROM Secondment c WHERE c.active=TRUE AND c.supersededBy IS NULL AND c.targetUnit=:"
				+ INamedQueryConstants.QUERY_PARAMETER_SCHOOL
				+ " AND c.schoolYear=:"
				+ INamedQueryConstants.QUERY_PARAMETER_SCHOOL_YEAR) })
public class Secondment extends BaseModel {

	/**
	 * @return the mandatoryWorkingHours
	 */
	public Integer getMandatoryWorkingHours() {
		return mandatoryWorkingHours;
	}

	/**
	 * @param mandatoryWorkingHours the mandatoryWorkingHours to set
	 */
	public void setMandatoryWorkingHours(Integer mandatoryWorkingHours) {
		this.mandatoryWorkingHours = mandatoryWorkingHours;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A secondment may or may not be active.
	 */
	@Basic
	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean active;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "PARENT_EMPLOYMENT_ID", nullable = true)
	private Employment affectedEmployment;

	@Basic
	@Column(name = "DUE_TO", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dueTo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	@Basic
	@Column(name = "EMPLOYEE_REQUESTED", nullable = true)
	private Boolean employeeRequested;

	@Basic
	@Column(name = "ESTABLISHED", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date established;

	@Basic
	@Column(name = "FINAL_WORKING_HOURS", nullable = true)
	private Integer finalWorkingHours;

	@Basic
	@Column(name = "MANDATORY_WORKING_HOURS", nullable = true)
	private Integer mandatoryWorkingHours;

	@Basic
	@Column(name = "HEADMASTER_ORDER", nullable = true, length = 25)
	private String headMasterOrder;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
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

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "SECONDMENT_TYPE_ID", nullable = false)
	private SecondmentType secondmentType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOURCE_PYSDE_ID", nullable = true)
	private PYSDE sourcePYSDE;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SOURCE_UNIT_ID", nullable = false)
	private Unit sourceUnit;


	@Basic
	@Column(name = "WORK_HRS_DECR", nullable = true)
	private Integer workingHoursDecrement;

	@Basic
	@Column(name = "WORK_HRS_DECR_REASON", nullable = true)
	private String workingHoursDecrementReason;

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the affectedEmployment
	 */
	public Employment getAffectedEmployment() {
		return affectedEmployment;
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	public Person getEmployee() {
		return employee;
	}

	/**
	 * @return the employeeRequested
	 */
	public Boolean getEmployeeRequested() {
		return employeeRequested;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public Date getInsertedOn() {
		return insertedOn;
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
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param affectedEmployment the affectedEmployment to set
	 */
	public void setAffectedEmployment(Employment affectedEmployment) {
		this.affectedEmployment = affectedEmployment;
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
	public void setEmployeeRequested(Boolean employeeRequested) {
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setInsertedOn(Date insertedOn) {
		this.insertedOn = insertedOn;
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

	public Unit getSourceUnit() {
		return sourceUnit;
	}

	public void setSourceUnit(Unit sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

}
