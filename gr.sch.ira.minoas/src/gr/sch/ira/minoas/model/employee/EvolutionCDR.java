package gr.sch.ira.minoas.model.employee;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Adreadakis</a>
 *
 */
@Entity
@Table(name = "EVOLUTION_CDR")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EvolutionCDR extends BaseIDModel {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Join EvolutionCDR with its respective Employee
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;
	
	/**
	 * Start Date (Ημερομηνία Έναρξης της περιόδου)
	 */
	@Basic
	@Column(name = "START_DATE")
	private Date startDate;
	
	/**
	 * End Date (Ημερομηνία Λήξης της περιόδου)
	 */
	@Basic
	@Column(name = "END_DATE")
	private Date endDate;
	
	/**
	 * Rank Information (Στοιχεία Βαθμού και Μισθολογικού Κλιμακίου)
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RANK_INFO_ID", nullable = false)
	private RankInfo rankInfo;
	
	/**
	 * Achieved the Promotion Quota (Περιελήφθη στην ποσόστωση των προακτέων)
	 */
	@Basic
	@Column(name = "ACHIEVED_PROMOTION_QUOTA")
	private Boolean achievedPromotionQuota;

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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/**
	 * @return the achievedPromotionQuota
	 */
	public Boolean getAchievedPromotionQuota() {
		return achievedPromotionQuota;
	}

	/**
	 * @param achievedPromotionQuota the achievedPromotionQuota to set
	 */
	public void setAchievedPromotionQuota(Boolean achievedPromotionQuota) {
		this.achievedPromotionQuota = achievedPromotionQuota;
	}
	
}
