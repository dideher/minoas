package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
	@Column(name = "GOG_APPOINTMENT_DATE")
	private Date gogAppointmentDate;

	
	/**
	 * Entry into service Act (Πράξη ανάληψης υπηρεσίας)
	 */
	@Basic
	@Column(name = "ENTRY_INTO_SERVICE_ACT", length = 5)
	private String entryIntoServiceAct;
	
	/**
	 * Entry into service date (Ημερομηνία ανάληψης υπηρεσίας)
	 */
	@Basic
	@Column(name = "ENTRY_INTO_SERVICE_DATE")
	private Date entryIntoServiceDate;
	
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
	 * Has a Master's Degree (Κατοχή Μεταπτυχιακού Διπλώματος)
	 */
	@Basic
	@Column(name = "HAS_A_MASTER_DEGREE")
	private Boolean hasAMasterDegree;
	
	/**
	 * Master's Degree Date (Ημερομηνία Μεταπτυχιακού Διπλώματος)
	 */
	@Basic
	@Column(name = "MSC_DATE")
	private Date mscDate;

	/**
	 * Has a PhD Degree (Κατοχή Διδακτορικού)
	 */
	@Basic
	@Column(name = "HAS_A_PHD_DEGREE")
	private Boolean hasAPhD;
	
	/**
	 * PhD Degree Date (Ημερομηνία Διδακτορικού)
	 */
	@Basic
	@Column(name = "PHD_DATE")
	private Date phdDate;

	/**
	 * Is a National School for Public Administration Graduate (Απόφοιτος της Σχολής Δημόσιας Διοίκησης)
	 */
	@Basic
	@Column(name = "IS_A_NAT_SCH_PUB_ADMIN_GRADUATE")
	private Boolean isANatSchPubAdminGraduate;
	

	/**
	 * National School for Public Administration Degree Date (Ημερομηνία Σχολής Δημόσιας Διοίκησης)
	 */
	@Basic
	@Column(name = "NAT_SCH_PUB_ADMIN_DATE")
	private Date natSchPubAdminDate;
	
	/**
	 * Is Recently Hired (Νεοδιορισμένος)
	 */
	@Basic
	@Column(name = "IS_RECENTLY_HIRED")
	private Boolean isRecentlyHired;

	
	/**
	 * Rank Information (Στοιχεία Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToMany(mappedBy="employeeInfo")
	//private RankInfo rankInfo;
	private Collection<RankInfo> rankInfos = new ArrayList<RankInfo>();
	
	
	/**
	 * Current Rank Information (Στοιχεία Τρέχοντος Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CURRENT_RANK_INFO_ID", nullable = true)
	private RankInfo currentRankInfo;
	
	
	/**
	 * Public/Private Sector (Δημόσιος/Ιδιωτικός Τομέας)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "SECTOR", nullable = false, updatable = true)
	private SectorType sector;

	
	/**
	 * Sum of Educational Experience (Άθροισμα Εκπαιδευτικής Προϋπηρεσίας)
	 */
	@Basic
	@Column(name = "SUM_OF_EDUCATIONAL_EXPERIENCE")
	private Integer sumOfEducationalExperience;
	
	/**
	 * Sum of Teaching Experience (Άθροισμα Διδακτικής Προϋπηρεσίας)
	 */
	@Basic
	@Column(name = "SUM_OF_TEACHING_EXPERIENCE")
	private Integer sumOfTeachingExperience;
	
	/**
	 * Sum of Any Type Of Working Experience (Άθροισμα Προϋπηρεσίας Οποιουδήποτε Τύπου)
	 */
	@Basic
	@Column(name = "SUM_OF_EXPERIENCE")
	private Integer sumOfExperience;

	/**
	 * Total Work Service in days (Does NOT include prior work experience)
	 * Συνολική Υπηρεσία από την ημ/νία διορισμού έως σήμερα (δεν συμπεριλαμβάνει προϋπηρεσίες)
	 */
	@Basic
	@Column(name = "TOTAL_WORK_SERVICE")
	private Integer totalWorkService;
	
	
	public EmployeeInfo() {
		super();
	}
	
	
	/**
	 * @param employee
	 * @param gogAppointmentNo
	 * @param gogAppointmentDate
	 * @param entryIntoServiceAct
	 * @param entryIntoServiceDate
	 * @param permanentEmployeeAct
	 * @param permanentEmployeeActDate
	 * @param hasAMasterDegree
	 * @param mscDate
	 * @param hasAPhD
	 * @param phdDate
	 * @param isANatSchPubAdminGraduate
	 * @param natSchPubAdminDate
	 * @param isRecentlyHired
	 * @param currentRankInfo
	 * @param sector
	 * @param sumOfEducationalExperience
	 * @param sumOfTeachingExperience
	 * @param sumOfExperience
	 * @param totalWorkService
	 */
	public EmployeeInfo(Employee employee, String gogAppointmentNo,
			Date gogAppointmentDate, String entryIntoServiceAct,
			Date entryIntoServiceDate, String permanentEmployeeAct,
			Date permanentEmployeeActDate, Boolean hasAMasterDegree,
			Date mscDate, Boolean hasAPhD, Date phdDate,
			Boolean isANatSchPubAdminGraduate, Date natSchPubAdminDate,
			Boolean isRecentlyHired, RankInfo currentRankInfo,
			SectorType sector, Integer sumOfEducationalExperience,
			Integer sumOfTeachingExperience, Integer sumOfExperience,
			Integer totalWorkService) {
		super();
		this.employee = employee;
		this.gogAppointmentNo = gogAppointmentNo;
		this.gogAppointmentDate = gogAppointmentDate;
		this.entryIntoServiceAct = entryIntoServiceAct;
		this.entryIntoServiceDate = entryIntoServiceDate;
		this.permanentEmployeeAct = permanentEmployeeAct;
		this.permanentEmployeeActDate = permanentEmployeeActDate;
		this.hasAMasterDegree = hasAMasterDegree;
		this.mscDate = mscDate;
		this.hasAPhD = hasAPhD;
		this.phdDate = phdDate;
		this.isANatSchPubAdminGraduate = isANatSchPubAdminGraduate;
		this.natSchPubAdminDate = natSchPubAdminDate;
		this.isRecentlyHired = isRecentlyHired;
		this.currentRankInfo = currentRankInfo;
		this.sector = sector;
		this.sumOfEducationalExperience = sumOfEducationalExperience;
		this.sumOfTeachingExperience = sumOfTeachingExperience;
		this.sumOfExperience = sumOfExperience;
		this.totalWorkService = totalWorkService;
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

//	/**
//	 * @return the gogAppointmentDate
//	 */
//	public String getGogAppointmentDateString() {
//		if(gogAppointmentDate == null)
//			return "";
//		else
//			return DateFormat.getDateInstance(DateFormat.SHORT).format(gogAppointmentDate);
//	}	
	
	
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
	 * @return the rankInfos
	 */
	public Collection<RankInfo> getRankInfos() {
		return rankInfos;
	}

	/**
	 * @param rankInfo the rankInfos to set
	 */
	public void setRankInfos(Collection<RankInfo> rankInfos) {
		this.rankInfos = rankInfos;
	}

	/**
	 * @return the currentRankInfo
	 */
	public RankInfo getCurrentRankInfo() {
		return currentRankInfo;
	}

	/**
	 * @param currentRankInfo the currentRankInfo to set
	 */
	public void setCurrentRankInfo(RankInfo currentRankInfo) {
		this.currentRankInfo = currentRankInfo;
	}

	/**
	 * @return the mscDate
	 */
	public Date getMscDate() {
		return mscDate;
	}

	/**
	 * @param mscDate the mscDate to set
	 */
	public void setMscDate(Date mscDate) {
		this.mscDate = mscDate;
	}

	/**
	 * @return the phdDate
	 */
	public Date getPhdDate() {
		return phdDate;
	}

	/**
	 * @param phdDate the phdDate to set
	 */
	public void setPhdDate(Date phdDate) {
		this.phdDate = phdDate;
	}

	/**
	 * @return the natSchPubAdminDate
	 */
	public Date getNatSchPubAdminDate() {
		return natSchPubAdminDate;
	}

	/**
	 * @param natSchPubAdminDate the natSchPubAdminDate to set
	 */
	public void setNatSchPubAdminDate(Date natSchPubAdminDate) {
		this.natSchPubAdminDate = natSchPubAdminDate;
	}

	/**
	 * @return the entryIntoServiceAct
	 */
	public String getEntryIntoServiceAct() {
		return entryIntoServiceAct;
	}

	/**
	 * @param entryIntoServiceAct the entryIntoServiceAct to set
	 */
	public void setEntryIntoServiceAct(String entryIntoServiceAct) {
		this.entryIntoServiceAct = entryIntoServiceAct;
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

	public void setSector(SectorType sector) {
		this.sector = sector;
	}

	public SectorType getSector() {
		return sector;
	}

	/**
	 * @return the sumOfEducationalExperience
	 */
	public Integer getSumOfEducationalExperience() {
		return sumOfEducationalExperience;
	}

	/**
	 * @return the sumOfEducationalExperience as a Year_Month_Day string
	 */
	public String getSumOfEducationalExperienceYear_Month_Day() {
		if(sumOfEducationalExperience == null)
			return "";
		else
			return CoreUtils.getNoOfDaysInYear_Month_DayFormat(sumOfEducationalExperience);
	}
	
	/**
	 * @param sumOfEducationalExperience the sumOfEducationalExperience to set
	 */
	public void setSumOfEducationalExperience(Integer sumOfEducationalExperience) {
		this.sumOfEducationalExperience = sumOfEducationalExperience;
	}

	/**
	 * @return the sumOfTeachingExperience
	 */
	public Integer getSumOfTeachingExperience() {
		return sumOfTeachingExperience;
	}

	/**
	 * @return the sumOfTeachingExperience as a Year_Month_Day string
	 */
	public String getSumOfTeachingExperienceYear_Month_Day() {
		return CoreUtils.getNoOfDaysInYear_Month_DayFormat(sumOfTeachingExperience);
	}
	
	/**
	 * @param sumOfTeachingExperience the sumOfTeachingExperience to set
	 */
	public void setSumOfTeachingExperience(Integer sumOfTeachingExperience) {
		this.sumOfTeachingExperience = sumOfTeachingExperience;
	}

	/**
	 * @return the sumOfExperience
	 */
	public Integer getSumOfExperience() {
		return sumOfExperience;
	}

	/**
	 * @return the sumOfExperience as a Year_Month_Day string
	 */
	public String getSumOfExperienceYear_Month_Day() {
		return CoreUtils.getNoOfDaysInYear_Month_DayFormat(sumOfExperience);
	}
	
	/**
	 * @param sumOfExperience the sumOfExperience to set
	 */
	public void setSumOfExperience(Integer sumOfExperience) {
		this.sumOfExperience = sumOfExperience;
	}

	/**
	 * @return the totalWorkService
	 */
	public Integer getTotalWorkService() {
		return totalWorkService;
	}

	/**
	 * @return the totalWorkService as a Year_Month_Day string
	 */
	public String getTotalWorkServiceYear_Month_Day() {
		return CoreUtils.getNoOfDaysInYear_Month_DayFormat(totalWorkService);
	}
	
	/**
	 * @param totalWorkService the totalWorkService to set
	 */
	public void setTotalWorkService(Integer totalWorkService) {
		this.totalWorkService = totalWorkService;
	}

	/**
	 * Check if EmployeeInfo currently has a RankInfo assigned to it.
	 * In other words if there is a row in RankInfo table holding the Rank and Salary Grade info assigned to this employee.  
	 * 
	 */
	public boolean hasCurrentRankInfo() {
		if(getCurrentRankInfo() == null)
			return false;
		else
			return true;
	}
	
	/**
	 * Reset EmployeeInfo and initialize its attributes
	 * 
	 */
	public void resetEmployeeInfo() {
		setSumOfEducationalExperience(0);
		setSumOfTeachingExperience(0);
		setSumOfExperience(0);
		setSector(SectorType.PUBLIC_SECTOR);
		
		RankInfo rInfo = new RankInfo(RankType.RANK_ST, 0, EducationalLevelType.UNIVERSITY_EDUCATION_LEVEL);
		rInfo.setEmployeeInfo(this);
		setCurrentRankInfo(rInfo);
		getRankInfos().add(rInfo);
		
		setIsANatSchPubAdminGraduate(false);
		setHasAMasterDegree(false);
		setHasAPhD(false);
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeInfo [gogAppointmentNo=" + gogAppointmentNo
				+ ", gogAppointmentDate=" + gogAppointmentDate
				+ ", entryIntoServiceAct=" + entryIntoServiceAct
				+ ", entryIntoServiceDate=" + entryIntoServiceDate
				+ ", permanentEmployeeAct=" + permanentEmployeeAct
				+ ", permanentEmployeeActDate=" + permanentEmployeeActDate
				+ ", hasAMasterDegree=" + hasAMasterDegree + ", mscDate="
				+ mscDate + ", hasAPhD=" + hasAPhD + ", phdDate=" + phdDate
				+ ", isANatSchPubAdminGraduate=" + isANatSchPubAdminGraduate
				+ ", natSchPubAdminDate=" + natSchPubAdminDate
				+ ", isRecentlyHired=" + isRecentlyHired + ", currentRankInfo="
				+ currentRankInfo + ", sector=" + sector
				+ ", sumOfEducationalExperience=" + sumOfEducationalExperience
				+ ", sumOfTeachingExperience=" + sumOfTeachingExperience
				+ ", sumOfExperience=" + sumOfExperience
				+ ", totalWorkService=" + totalWorkService + "]";
	}
	
	
}
