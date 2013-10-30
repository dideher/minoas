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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "REGULAR_EMPLOYEE_INFO")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
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
	 * Is Recently Hired (Νεοδιορισμένος)
	 */
	@Basic
	@Column(name = "IS_RECENTLY_HIRED")
	private Boolean isRecentlyHired;

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
	 * Registry ID (Αριθμός Μητρώου (ΑΜ))
	 */
	@Basic
	@Column(name = "REGISTRY_ID", length = 7)
	private String registryID;

	/**
	 * Greek Official Gazette Αppointment Number (Αριθμός ΦΕΚ Μετάταξης). 
	 * Αφορά υπάλληλο που ήταν πχ Μόνιμος Εκπ/σος και μετατάχθηκε σε Διοικητικός
	 */
	@Basic
	@Column(name = "TRAN_GOG_APPOINTMENT_NO", length = 4)
	private String transferAppointmentGOG;
	
	/**
	 * Greek Official Gazette Αppointment Date (Ημερομηνία ΦΕΚ Διορισμού)
	 * Αφορά υπάλληλο που ήταν πχ Μόνιμος Εκπ/σος και μετατάχθηκε σε Διοικητικός
	 */
	@Basic
	@Column(name = "TRAN_GOG_APPOINTMENT_DATE")
	private Date transferAppointmentGOGDate;

	public RegularEmployeeInfo() {
		super();
	}
	
	/**
	 * Constructor to be used when cloning an RegularEmployeeInfo. Note, the cloning is simple and no
	 * complex referenced beans are cloned as well.
	 * @param i
	 */
	public RegularEmployeeInfo(RegularEmployeeInfo i) {
		super();
		if(i != null) {
			this.setId(i.getId());
			this.setAppointmentGOG(i.getAppointmentGOG());
			this.setAppointmentGOGDate(i.getAppointmentGOGDate());
			this.setInsertedBy(i.getInsertedBy());
			this.setInsertedOn(i.getInsertedOn());
			this.setIsRecentlyHired(i.getIsRecentlyHired());
			this.setPermanentEmployeeAct(i.getPermanentEmployeeAct());
			this.setPermanentEmployeeActDate(i.getPermanentEmployeeActDate());
			this.setRegistryID(i.getRegistryID());
			this.setTransferAppointmentGOG(i.getTransferAppointmentGOG());
			this.setTransferAppointmentGOGDate(i.getTransferAppointmentGOGDate());
		}
	}
	
	
	/**
	 * @return the appointmentGOG
	 */
	public String getAppointmentGOG() {
		return appointmentGOG;
	}
	
	/**
	 * @return the appointmentGOGDate
	 */
	public Date getAppointmentGOGDate() {
		return appointmentGOGDate;
	}
	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}


	/**
	 * @return the isRecentlyHired
	 */
	public Boolean getIsRecentlyHired() {
		return isRecentlyHired;
	}

	/**
	 * @return the permanentEmployeeAct
	 */
	public String getPermanentEmployeeAct() {
		return permanentEmployeeAct;
	}

	/**
	 * @return the permanentEmployeeActDate
	 */
	public Date getPermanentEmployeeActDate() {
		return permanentEmployeeActDate;
	}

	/**
	 * @return the registryID
	 */
	public String getRegistryID() {
		return registryID;
	}

	public String getTransferAppointmentGOG() {
		return transferAppointmentGOG;
	}

	public Date getTransferAppointmentGOGDate() {
		return transferAppointmentGOGDate;
	}

	/**
	 * @param appointmentGOG the appointmentGOG to set
	 */
	public void setAppointmentGOG(String appointmentGOG) {
		this.appointmentGOG = appointmentGOG;
	}

	/**
	 * @param appointmentGOGDate the appointmentGOGDate to set
	 */
	public void setAppointmentGOGDate(Date appointmentGOGDate) {
		this.appointmentGOGDate = appointmentGOGDate;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param isRecentlyHired the isRecentlyHired to set
	 */
	public void setIsRecentlyHired(Boolean isRecentlyHired) {
		this.isRecentlyHired = isRecentlyHired;
	}
	
	/**
	 * @param permanentEmployeeAct the permanentEmployeeAct to set
	 */
	public void setPermanentEmployeeAct(String permanentEmployeeAct) {
		this.permanentEmployeeAct = permanentEmployeeAct;
	}

	/**
	 * @param permanentEmployeeActDate the permanentEmployeeActDate to set
	 */
	public void setPermanentEmployeeActDate(Date permanentEmployeeActDate) {
		this.permanentEmployeeActDate = permanentEmployeeActDate;
	}

	/**
	 * @param registryID the registryID to set
	 */
	public void setRegistryID(String registryID) {
		this.registryID = registryID;
	}

	public void setTransferAppointmentGOG(String transferAppointmentGOG) {
		this.transferAppointmentGOG = transferAppointmentGOG;
	}

	public void setTransferAppointmentGOGDate(Date transferAppointmentGOGDate) {
		this.transferAppointmentGOGDate = transferAppointmentGOGDate;
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
