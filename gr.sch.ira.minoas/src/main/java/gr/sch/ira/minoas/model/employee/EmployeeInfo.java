package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
	 * Current Rank Information (Στοιχεία Τρέχοντος Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "CURRENT_RANK_INFO_ID", nullable = true)
	private RankInfo currentRankInfo;

	/**
	 * Join EmploeeInfo with its respective Employee
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
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
	 * Master's Degree Date (Ημερομηνία Μεταπτυχιακού Διπλώματος)
	 */
	@Basic
	@Column(name = "MSC_DATE")
	private Date mscDate;
	

	/**
	 * National School for Public Administration Degree Date (Ημερομηνία Σχολής Δημόσιας Διοίκησης)
	 */
	@Basic
	@Column(name = "NAT_SCH_PUB_ADMIN_DATE")
	private Date natSchPubAdminDate;
	


	
	/**
	 * PhD Degree Date (Ημερομηνία Διδακτορικού)
	 */
	@Basic
	@Column(name = "PHD_DATE")
	private Date phdDate;
	
	
	/**
	 * Rank Information (Στοιχεία Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToMany(mappedBy="employeeInfo")
	//private RankInfo rankInfo;
	private Collection<RankInfo> rankInfos = new ArrayList<RankInfo>();
	
	
	/**
	 * Public/Private Sector (Δημόσιος/Ιδιωτικός Τομέας)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "SECTOR", nullable = false, updatable = true)
	private SectorType sector;

	
	/**
	 * Total days while the employee was on an unpaid leave. Days are calculated on a 360year basic and
	 * for each year 30 days are deducted as a gratis (this is the law). 
	 * 
	 * In Greek : Ημέρες άδειας κατά τις οποίες ο εργαζόμενος ήταν σε άδεια ανευ αποδοχών.
	 */
	@Basic
	@Column(name = "SUM_OF_UNPAID_LEAVES", nullable=true)
	private Integer sumOfDaysDuringUnpaidLeaves;
	
	/**
	 * Sum of Educational Experience (Άθροισμα Εκπαιδευτικής Προϋπηρεσίας)
	 */
	@Basic
	@Column(name = "SUM_OF_EDUCATIONAL_EXPERIENCE")
	private Integer sumOfEducationalExperience;
	
	/**
	 * Sum of Any Type Of Working Experience (Άθροισμα Προϋπηρεσίας Οποιουδήποτε Τύπου)
	 */
	@Basic
	@Column(name = "SUM_OF_EXPERIENCE")
	private Integer sumOfExperience;

	/**
	 * Sum of Teaching Experience (Άθροισμα Διδακτικής Προϋπηρεσίας)
	 */
	@Basic
	@Column(name = "SUM_OF_TEACHING_EXPERIENCE")
	private Integer sumOfTeachingExperience;
	
	/**
	 * Total Work Service in days (Does NOT include prior work experience)
	 * Συνολική Υπηρεσία από την ημ/νία διορισμού έως σήμερα (δεν συμπεριλαμβάνει προϋπηρεσίες)
	 */
	@Basic
	@Column(name = "TOTAL_WORK_SERVICE")
	private Integer totalWorkService;
	
	
	public EmployeeInfo() {
		super();
		this.employee = null;
		this.hasAMasterDegree = false;
		this.mscDate = null;
		this.hasAPhD = false;
		this.phdDate = null;
		this.isANatSchPubAdminGraduate = false;
		this.natSchPubAdminDate = null;
		this.currentRankInfo = null;
		this.sector = SectorType.PUBLIC_SECTOR;
		this.sumOfEducationalExperience = 0;
		this.sumOfTeachingExperience = 0;
		this.sumOfExperience = 0;
		this.totalWorkService = 0;
	}


	/**
	 * @param employee
	 * @param gogAppointmentNo
	 * @param gogAppointmentDate
	 * @param hasAMasterDegree
	 * @param mscDate
	 * @param hasAPhD
	 * @param phdDate
	 * @param isANatSchPubAdminGraduate
	 * @param natSchPubAdminDate
	 * @param currentRankInfo
	 * @param sector
	 * @param sumOfEducationalExperience
	 * @param sumOfTeachingExperience
	 * @param sumOfExperience
	 * @param totalWorkService
	 */
	public EmployeeInfo(Employee employee, String gogAppointmentNo,
			Date gogAppointmentDate, String entryIntoServiceAct,
			Boolean hasAMasterDegree,
			Date mscDate, Boolean hasAPhD, Date phdDate,
			Boolean isANatSchPubAdminGraduate, Date natSchPubAdminDate,
			Boolean isRecentlyHired, RankInfo currentRankInfo,
			SectorType sector, Integer sumOfEducationalExperience,
			Integer sumOfTeachingExperience, Integer sumOfExperience,
			Integer totalWorkService) {
		super();
		this.employee = employee;
		this.hasAMasterDegree = hasAMasterDegree;
		this.mscDate = mscDate;
		this.hasAPhD = hasAPhD;
		this.phdDate = phdDate;
		this.isANatSchPubAdminGraduate = isANatSchPubAdminGraduate;
		this.natSchPubAdminDate = natSchPubAdminDate;
		this.currentRankInfo = currentRankInfo;
		this.sector = sector;
		this.sumOfEducationalExperience = sumOfEducationalExperience;
		this.sumOfTeachingExperience = sumOfTeachingExperience;
		this.sumOfExperience = sumOfExperience;
		this.totalWorkService = totalWorkService;
	}


	/**
	 * @return the currentRankInfo
	 */
	public RankInfo getCurrentRankInfo() {
		return currentRankInfo;
	}
	
	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the hasAMasterDegree
	 */
	public Boolean getHasAMasterDegree() {
		return hasAMasterDegree;
	}

	/**
	 * @return the hasAPhD
	 */
	public Boolean getHasAPhD() {
		return hasAPhD;
	}

	
	/**
	 * @return the rankInfos
	 */
	public Collection<RankInfo> getNonDeletedRankInfos() {
		Collection<RankInfo> nonDeletedRankInfos = new ArrayList<RankInfo>();
		for (Iterator<RankInfo> iter = rankInfos.iterator(); iter.hasNext();) {
			RankInfo rInfo = (RankInfo) iter.next();
			if(rInfo!= null && (rInfo.getDeleted()== null || !rInfo.getDeleted())) {
				nonDeletedRankInfos.add(rInfo);
			}
		}
		return nonDeletedRankInfos;
	}
	

	/**
	 * @return the isANatSchPubAdminGraduate
	 */
	public Boolean getIsANatSchPubAdminGraduate() {
		return isANatSchPubAdminGraduate;
	}

	/**
	 * @return the mscDate
	 */
	public Date getMscDate() {
		return mscDate;
	}

	/**
	 * @return the natSchPubAdminDate
	 */
	public Date getNatSchPubAdminDate() {
		return natSchPubAdminDate;
	}

	/**
	 * @return the phdDate
	 */
	public Date getPhdDate() {
		return phdDate;
	}

	/**
	 * @return the rankInfos
	 */
	public Collection<RankInfo> getRankInfos() {
		return rankInfos;
	}

	public SectorType getSector() {
		return sector;
	}

	public Integer getSumOfDaysDuringUnpaidLeaves() {
		return sumOfDaysDuringUnpaidLeaves;
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

	/**
	 * @param currentRankInfo the currentRankInfo to set
	 */
	public void setCurrentRankInfo(RankInfo currentRankInfo) {
		this.currentRankInfo = currentRankInfo;
		if(currentRankInfo != null && !rankInfos.contains(currentRankInfo)) {
			rankInfos.add(currentRankInfo);
		}
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param hasAMasterDegree the hasAMasterDegree to set
	 */
	public void setHasAMasterDegree(Boolean hasAMasterDegree) {
		this.hasAMasterDegree = hasAMasterDegree;
	}
	
	/**
	 * @param hasAPhD the hasAPhD to set
	 */
	public void setHasAPhD(Boolean hasAPhD) {
		this.hasAPhD = hasAPhD;
	}

	/**
	 * @param isANatSchPubAdminGraduate the isANatSchPubAdminGraduate to set
	 */
	public void setIsANatSchPubAdminGraduate(Boolean isANatSchPubAdminGraduate) {
		this.isANatSchPubAdminGraduate = isANatSchPubAdminGraduate;
	}

	/**
	 * @param mscDate the mscDate to set
	 */
	public void setMscDate(Date mscDate) {
		this.mscDate = mscDate;
	}
	
	/**
	 * @param natSchPubAdminDate the natSchPubAdminDate to set
	 */
	public void setNatSchPubAdminDate(Date natSchPubAdminDate) {
		this.natSchPubAdminDate = natSchPubAdminDate;
	}

	/**
	 * @param phdDate the phdDate to set
	 */
	public void setPhdDate(Date phdDate) {
		this.phdDate = phdDate;
	}

	/**
	 * @param rankInfo the rankInfos to set
	 */
	public void setRankInfos(Collection<RankInfo> rankInfos) {
		this.rankInfos = rankInfos;
	}
	
	public void setSector(SectorType sector) {
		this.sector = sector;
	}

	public void setSumOfDaysDuringUnpaidLeaves(Integer sumOfDaysDuringUnpaidLeaves) {
		this.sumOfDaysDuringUnpaidLeaves = sumOfDaysDuringUnpaidLeaves;
	}

	/**
	 * @param sumOfEducationalExperience the sumOfEducationalExperience to set
	 */
	public void setSumOfEducationalExperience(Integer sumOfEducationalExperience) {
		this.sumOfEducationalExperience = sumOfEducationalExperience;
	}
	
	/**
	 * @param sumOfExperience the sumOfExperience to set
	 */
	public void setSumOfExperience(Integer sumOfExperience) {
		this.sumOfExperience = sumOfExperience;
	}

	/**
	 * @param sumOfTeachingExperience the sumOfTeachingExperience to set
	 */
	public void setSumOfTeachingExperience(Integer sumOfTeachingExperience) {
		this.sumOfTeachingExperience = sumOfTeachingExperience;
	}
	
	/**
	 * @param totalWorkService the totalWorkService to set
	 */
	public void setTotalWorkService(Integer totalWorkService) {
		this.totalWorkService = totalWorkService;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeInfo [hasAMasterDegree=" + hasAMasterDegree + ", mscDate="
				+ mscDate + ", hasAPhD=" + hasAPhD + ", phdDate=" + phdDate
				+ ", isANatSchPubAdminGraduate=" + isANatSchPubAdminGraduate
				+ ", natSchPubAdminDate=" + natSchPubAdminDate
				+ currentRankInfo + ", sector=" + sector
				+ ", sumOfEducationalExperience=" + sumOfEducationalExperience
				+ ", sumOfTeachingExperience=" + sumOfTeachingExperience
				+ ", sumOfExperience=" + sumOfExperience
				+ ", totalWorkService=" + totalWorkService + "]";
	}
	
	
}
