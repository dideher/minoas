package gr.sch.ira.minoas.model.transfers;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.jboss.aspects.security.Unchecked;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.employee.Employee;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@MappedSuperclass
public class BaseOutstandingTransfer extends BaseIDModel {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID", nullable=false)
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="SCHOOLYEAR_ID", nullable=false)
	private SchoolYear schoolYear;
	
	@Basic
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@ManyToOne
	@JoinColumn(name="SOURCE_PYSDE_ID", nullable=false)
	private PYSDE sourcePYSDE;
	
	@ManyToOne
	@JoinColumn(name="TARGET_PYSDE_ID", nullable=false)
	private PYSDE targetPYSDE;
	
	@Basic
	@Column(name="IS_PROCESSED", nullable=false)
	private Boolean isProcessed;
	

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the sourcePYSDE
	 */
	public PYSDE getSourcePYSDE() {
		return sourcePYSDE;
	}

	/**
	 * @param sourcePYSDE the sourcePYSDE to set
	 */
	public void setSourcePYSDE(PYSDE sourcePYSDE) {
		this.sourcePYSDE = sourcePYSDE;
	}

	/**
	 * @return the targetPYSDE
	 */
	public PYSDE getTargetPYSDE() {
		return targetPYSDE;
	}

	/**
	 * @param targetPYSDE the targetPYSDE to set
	 */
	public void setTargetPYSDE(PYSDE targetPYSDE) {
		this.targetPYSDE = targetPYSDE;
	}

	/**
	 * @return the isProcessed
	 */
	public Boolean getIsProcessed() {
		return isProcessed;
	}

	/**
	 * @param isProcessed the isProcessed to set
	 */
	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
	
}
