package gr.sch.ira.minoas.model.transfers;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employement.Employment;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Μετάταξη Προσωπικού
 * 
 * @author Filippos Slavik (filippos@slavik.gr)
 *
 */
@Entity
@Table(name = "EMPLOYEE_TYPE_TRANSFER", uniqueConstraints=@UniqueConstraint(columnNames={"EMPLOYEE_ID", "NEW_EMPLOYEE_ID"}))
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeTypeTransfer extends BaseIDDeleteAwareModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    
    @Basic
	@Column(name = "GOG_APPOINTMENT_NO", length = 5, nullable=false)
	private String appointmentGOG;
	
	
	@Basic
	@Column(name = "GOG_APPOINTMENT_DATE", nullable=false)
	private Date appointmentGOGDate;

	@Basic
	@Column(name = "TRANSFER_COMMENT", nullable=true, length=1024)
	private String comment;

	@Basic
    @Column(name="EFFECTIVE_ON", nullable=false)
    private Date effectiveOn;

	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID", nullable=false)
	private Employee employee;

	@Basic
	@Column(name = "ENTRY_INTO_SERVICE_ACT", length = 5, nullable=false)
	private String entryIntoServiceAct;

	@Basic
	@Column(name = "ENTRY_INTO_SERVICE_DATE", nullable=false)
	private Date entryIntoServiceDate;

	@ManyToOne
	@JoinColumn(name="NEW_EMPLOYEE_ID", nullable=true)
	private Employee newEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEW_EMPLOYEE_SPECIALIZATION_ID", nullable = false)
	private Specialization newEmployeeSpecialization;

	@Enumerated(EnumType.STRING)
	@Column(name = "NEW_EMPLOYEE_TYPE", nullable=false)
	private EmployeeType newEmployeeType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEW_EMPLOYMENT_ID", nullable = true)
	private Employment newEmployment;
    
    public String getAppointmentGOG() {
		return appointmentGOG;
	}


	public Date getAppointmentGOGDate() {
		return appointmentGOGDate;
	}


	public String getComment() {
		return comment;
	}
	
	
	public Date getEffectiveOn() {
		return effectiveOn;
	}
	
    public Employee getEmployee() {
		return employee;
	}
	
	public String getEntryIntoServiceAct() {
		return entryIntoServiceAct;
	}
	
	
	public Date getEntryIntoServiceDate() {
		return entryIntoServiceDate;
	}
	
	
	public Employee getNewEmployee() {
		return newEmployee;
	}
	
	public Specialization getNewEmployeeSpecialization() {
		return newEmployeeSpecialization;
	}
	
	public EmployeeType getNewEmployeeType() {
		return newEmployeeType;
	}

	public Employment getNewEmployment() {
		return newEmployment;
	}

	public void setAppointmentGOG(String appointmentGOG) {
		this.appointmentGOG = appointmentGOG;
	}

	public void setAppointmentGOGDate(Date appointmentGOGDate) {
		this.appointmentGOGDate = appointmentGOGDate;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setEffectiveOn(Date effectiveOn) {
		this.effectiveOn = effectiveOn;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setEntryIntoServiceAct(String entryIntoServiceAct) {
		this.entryIntoServiceAct = entryIntoServiceAct;
	}

	public void setEntryIntoServiceDate(Date entryIntoServiceDate) {
		this.entryIntoServiceDate = entryIntoServiceDate;
	}

	public void setNewEmployee(Employee newEmployee) {
		this.newEmployee = newEmployee;
	}

	public void setNewEmployeeSpecialization(
			Specialization newEmployeeSpecialization) {
		this.newEmployeeSpecialization = newEmployeeSpecialization;
	}

	public void setNewEmployeeType(EmployeeType newEmployeeType) {
		this.newEmployeeType = newEmployeeType;
	}

	public void setNewEmployment(Employment newEmployment) {
		this.newEmployment = newEmployment;
	}

}
