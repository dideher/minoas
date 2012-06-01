package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import sun.text.resources.DateFormatZoneData_el;



/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Entity
@Table(name = "EMPLOYEE_INFO")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeInfo extends BaseIDModel {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Join EmploeeInfo with its respective Employee
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
	/**
	 * Greek Official Gazette Αppointment Number (Αριθμός ΦΕΚ Διορισμού)
	 */
	@Basic
	@Column(name = "GOG_APPOINTMENT_NO", length = 4)
	private String gogAppointmentNo;

	/**
	 * Greek Official Gazette Αppointment Date (Ημερομηνία ΦΕΚ Διορισμού)
	 */
	@Basic
	@Column(name = "APPOINTMENT_GOG_DATE")
	private Date gogAppointmentDate;

	/**
	 * Entry into service date (Ημερομηνία ανάληψης υπηρεσίας)
	 */
	@Basic
	@Column(name = "ENTRY_INTO_SERVICE_DATE")
	private Date entryIntoServiceDate;

	/**
	 * Has a Master's Degree (Κατοχή Μεταπτυχιακού Διπλώματος)
	 */
	@Basic
	@Column(name = "HAS_A_MASTER_DEGREE")
	private Boolean hasAMasterDegree;

	/**
	 * Has a PhD Degree (Κατοχή Διδακτορικού)
	 */
	@Basic
	@Column(name = "HAS_A_PHD_DEGREE")
	private Boolean hasAPhD;

	/**
	 * Is a National School for Public Administration Graduate (Απόφοιτος της Σχολής Δημόσιας Διοίκησης)
	 */
	@Basic
	@Column(name = "IS_A_NAT_SCH_PUB_ADMIN_GRADUATE")
	private Boolean isANatSchPubAdminGraduate;
	
	/**
	 * Rank Information (Στοιχεία Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToOne(mappedBy="employeeInfo")
	private RankInfo rankInfo;

	
	
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
	 * @return the gogAppointmentNo
	 */
	public String getGogAppointmentNo() {
		return gogAppointmentNo;
	}

	/**
	 * @param gogAppointmentNo the gogAppointmentNo to set
	 */
	public void setGogAppointmentNo(String gogAppointmentNo) {
		this.gogAppointmentNo = gogAppointmentNo;
	}

	/**
	 * @return the gogAppointmentDate
	 */
	public Date getGogAppointmentDate() {
		return gogAppointmentDate;
	}

	/**
	 * @return the gogAppointmentDate
	 */
	public String getGogAppointmentDateString() {
		return DateFormat.getDateInstance(DateFormat.SHORT).format(gogAppointmentDate);
	}	
	
	
	/**
	 * @param gogAppointmentDate the gogAppointmentDate to set
	 */
	public void setGogAppointmentDate(Date gogAppointmentDate) {
		this.gogAppointmentDate = gogAppointmentDate;
	}

	/**
	 * @return the entryIntoServiceDate
	 */
	public Date getEntryIntoServiceDate() {
		return entryIntoServiceDate;
	}

	/**
	 * @param entryIntoServiceDate the entryIntoServiceDate to set
	 */
	public void setEntryIntoServiceDate(Date entryIntoServiceDate) {
		this.entryIntoServiceDate = entryIntoServiceDate;
	}

	/**
	 * @return the hasAMasterDegree
	 */
	public Boolean getHasAMasterDegree() {
		return hasAMasterDegree;
	}

	/**
	 * @param hasAMasterDegree the hasAMasterDegree to set
	 */
	public void setHasAMasterDegree(Boolean hasAMasterDegree) {
		this.hasAMasterDegree = hasAMasterDegree;
	}

	/**
	 * @return the hasAPhD
	 */
	public Boolean getHasAPhD() {
		return hasAPhD;
	}

	/**
	 * @param hasAPhD the hasAPhD to set
	 */
	public void setHasAPhD(Boolean hasAPhD) {
		this.hasAPhD = hasAPhD;
	}

	/**
	 * @return the isANatSchPubAdminGraduate
	 */
	public Boolean getIsANatSchPubAdminGraduate() {
		return isANatSchPubAdminGraduate;
	}

	/**
	 * @param isANatSchPubAdminGraduate the isANatSchPubAdminGraduate to set
	 */
	public void setIsANatSchPubAdminGraduate(Boolean isANatSchPubAdminGraduate) {
		this.isANatSchPubAdminGraduate = isANatSchPubAdminGraduate;
	}

	/**
	 * @return the rankInfo
	 */
	public RankInfo getRankInfo() {
		return rankInfo;
	}

	/**
	 * @param rankInfo the rankInfo to set
	 */
	public void setRankInfo(RankInfo rankInfo) {
		this.rankInfo = rankInfo;
	}
	
}
