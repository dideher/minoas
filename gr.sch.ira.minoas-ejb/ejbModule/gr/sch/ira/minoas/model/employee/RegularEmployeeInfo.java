

package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "REGULAR_EMPLOYEE_INFO")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RegularEmployeeInfo extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Greek Official Gazette of appointment (÷≈  ƒÈÔÒÈÛÏÔ˝)
	 * 
	 */
	@Basic
	@Column(name = "APPOINTMENT_GOF", length = 4)
	private String appointmentGOF;

	/**
	 * Greek Official Gazette of Date appointment («ÏÂÒÔÏÁÌﬂ· ÷≈  ƒÈÔÒÈÛÏÔ˝)
	 * 
	 */
	@Basic
	@Column(name = "APPOINTMENT_GOF_DATE")
	private Date appointmentGOFDate;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID", nullable=false)
	private Employee employee;

	

	@Basic
	@Column(name = "REGISTRY_ID", length = 7)
	private String registryID;

	/**
	 * @return the appointmentGOF
	 */
	public String getAppointmentGOF() {
		return appointmentGOF;
	}

	/**
	 * @return the appointmentGOFDate
	 */
	public Date getAppointmentGOFDate() {
		return appointmentGOFDate;
	}

	/**
	 * @return the registryID
	 */
	public String getRegistryID() {
		return registryID;
	}

	/**
	 * @param appointmentGOF the appointmentGOF to set
	 */
	public void setAppointmentGOF(String appointmentGOF) {
		this.appointmentGOF = appointmentGOF;
	}

	/**
	 * @param appointmentGOFDate the appointmentGOFDate to set
	 */
	public void setAppointmentGOFDate(Date appointmentGOFDate) {
		this.appointmentGOFDate = appointmentGOFDate;
	}

	/**
	 * @param registryID the registryID to set
	 */
	public void setRegistryID(String registryID) {
		this.registryID = registryID;
	}

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

}
