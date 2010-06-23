package gr.sch.ira.minoas.model.transfers;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.Employee;

@Entity
@Table(name = "OutstandingImprovement", uniqueConstraints=@UniqueConstraint(columnNames={"EMPLOYEE_ID", "SCHOOLYEAR_ID"}))
public class OutstandingImprovement extends BaseOutstandingTransfer {

	@ManyToOne
	@JoinColumn(name="SOURCE_SCHOOL_ID", nullable=false)
	private School sourceSchool;

	@ManyToOne
	@JoinColumn(name="TARGET_SCHOOL_ID", nullable=false)
	private School targetSchool;
	
	@Basic
	@Column(name = "IMPROVEMENT_REGION")
	private Character improvementRegionCode;

	/**
	 * @return the targetSchool
	 */
	public School getTargetSchool() {
		return targetSchool;
	}

	/**
	 * @param targetSchool the targetSchool to set
	 */
	public void setTargetSchool(School targetSchool) {
		this.targetSchool = targetSchool;
	}

	/**
	 * @return the sourceSchool
	 */
	public School getSourceSchool() {
		return sourceSchool;
	}

	/**
	 * @param sourceSchool the sourceSchool to set
	 */
	public void setSourceSchool(School sourceSchool) {
		this.sourceSchool = sourceSchool;
	}

	
	/**
	 * @see gr.sch.ira.minoas.model.transfers.BaseOutstandingTransfer#setEmployee(gr.sch.ira.minoas.model.employee.Employee)
	 */
	@Override
	public void setEmployee(Employee employee) {
		super.setEmployee(employee);
	}

	/**
	 * @return the improvementRegionCode
	 */
	public Character getImprovementRegionCode() {
		return improvementRegionCode;
	}

	/**
	 * @param improvementRegionCode the improvementRegionCode to set
	 */
	public void setImprovementRegionCode(Character improvementRegionCode) {
		this.improvementRegionCode = improvementRegionCode;
	}
	
}
