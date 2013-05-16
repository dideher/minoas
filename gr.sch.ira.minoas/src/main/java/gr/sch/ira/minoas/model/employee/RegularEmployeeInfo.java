package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "REGULAR_EMPLOYEE_INFO")

public class RegularEmployeeInfo extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Greek Official Gazette Αppointment Number (Αριθμός ΦΕΚ Διορισμού)
	 */
	@Basic
	@Column(name = "GOG_APPOINTMENT_NO", length = 4)
	private String appointmentGOG;

	/**
	 * Greek Official Gazette Αppointment Date (Ημερομηνία ΦΕΚ Διορισμού)
	 */
	@Basic
	@Column(name = "GOG_APPOINTMENT_DATE")
	private Date appointmentGOGDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	/**
	 * Registry ID (Αριθμός Μητρώου (ΑΜ))
	 */
	@Basic
	@Column(name = "REGISTRY_ID", length = 7)
	private String registryID;
	
	
	/**
	 * Permanent Employee Act (Πράξη Μονιμοποίησης)
	 */
	@Basic
	@Column(name = "PERMANENT_EMPLOYEE_ACT", length = 8)
	private String permanentEmployeeAct;
	
	/**
	 * Permanent Empoloyee Act Date (Ημερομηνία Πράξης Μονιμοποίησης)
	 */
	@Basic
	@Column(name = "PERMANENT_EMPLOYEE_ACT_DATE")
	private Date permanentEmployeeActDate;
	
	/**
	 * Is Recently Hired (Νεοδιορισμένος)
	 */
	@Basic
	@Column(name = "IS_RECENTLY_HIRED")
	private Boolean isRecentlyHired;


	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the registryID
	 */
	public String getRegistryID() {
		return registryID;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param registryID the registryID to set
	 */
	public void setRegistryID(String registryID) {
		this.registryID = registryID;
	}

	/**
	 * @return the permanentEmployeeAct
	 */
	public String getPermanentEmployeeAct() {
		return permanentEmployeeAct;
	}

	/**
	 * @param permanentEmployeeAct the permanentEmployeeAct to set
	 */
	public void setPermanentEmployeeAct(String permanentEmployeeAct) {
		this.permanentEmployeeAct = permanentEmployeeAct;
	}

	/**
	 * @return the permanentEmployeeActDate
	 */
	public Date getPermanentEmployeeActDate() {
		return permanentEmployeeActDate;
	}

	/**
	 * @param permanentEmployeeActDate the permanentEmployeeActDate to set
	 */
	public void setPermanentEmployeeActDate(Date permanentEmployeeActDate) {
		this.permanentEmployeeActDate = permanentEmployeeActDate;
	}

	/**
	 * @return the isRecentlyHired
	 */
	public Boolean getIsRecentlyHired() {
		return isRecentlyHired;
	}

	/**
	 * @param isRecentlyHired the isRecentlyHired to set
	 */
	public void setIsRecentlyHired(Boolean isRecentlyHired) {
		this.isRecentlyHired = isRecentlyHired;
	}
	
	/**
	 * @return the appointmentGOG
	 */
	public String getAppointmentGOG() {
		return appointmentGOG;
	}

	/**
	 * @param appointmentGOG the appointmentGOG to set
	 */
	public void setAppointmentGOG(String appointmentGOG) {
		this.appointmentGOG = appointmentGOG;
	}

	/**
	 * @return the appointmentGOGDate
	 */
	public Date getAppointmentGOGDate() {
		return appointmentGOGDate;
	}

	/**
	 * @param appointmentGOGDate the appointmentGOGDate to set
	 */
	public void setAppointmentGOGDate(Date appointmentGOGDate) {
		this.appointmentGOGDate = appointmentGOGDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Regular EmployeeInfo [appointmentGOG=" + appointmentGOG + ", appointmentGOGDate="
				+ appointmentGOGDate + ", registryID=" + registryID + ", permanentEmployeeAct=" + permanentEmployeeAct
				+ ", permanentEmployeeActDate=" + permanentEmployeeActDate
				+ ", isRecentlyHired=" + isRecentlyHired + "]";
	}

}
