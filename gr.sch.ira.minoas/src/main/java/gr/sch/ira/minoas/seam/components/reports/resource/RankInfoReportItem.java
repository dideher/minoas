package gr.sch.ira.minoas.seam.components.reports.resource;

import java.util.Date;

import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RankType;
import gr.sch.ira.minoas.model.security.Principal;

/**
 * @author <a href="mailto:andreadakis@gmail.com">Yorgos Andreadakis</a>
 * @version $Id$
 */
public class RankInfoReportItem extends EmploymentReportItem {
	/**
	 * Rank (Βαθμός)
	 */
	private RankType rank;
	
	/**
	 * Rank (Βαθμός)
	 */
	private String rankString;

	/**
	 * Salary Grade (Μισθολογικό Κλιμάκιο - Μ.Κ.)
	 */
	private Integer salaryGrade;

	/**
	 * Last Rank Date (Ημερομηνία λήψης τελευταίου Βαθμού)
	 */
	private Date lastRankDate;

	/**
	 * Last Salary Grade Date (Ημερομηνία λήψης τελευταίου Μ.Κ.)
	 */
	private Date lastSalaryGradeDate;
	
	/**
	 * EmployeeInfo
	 */
	private EmployeeInfo employeeInfo;
	
	/**
	 * PreviousRankInfo
	 */
	private RankInfo previousRankInfo;

	private String comments;
	
	private Principal insertedBy;
	
	/**
	 * 
	 */
	public RankInfoReportItem() {
		super();
	}
	


	/**
	 * Create an RankInfoReportItem from an employment.
	 * @param employment
	 */
	public RankInfoReportItem(RankInfo rInfo) {
		super(rInfo.getEmployeeInfo().getEmployee().getCurrentEmployment());
		configureItemFromRankInfo(rInfo);
	}
	
	protected void configureItemFromRankInfo(RankInfo rInfo) {
	    setRank(rInfo.getRank());
	    setSalaryGrade(rInfo.getSalaryGrade());
	    setLastRankDate(rInfo.getLastRankDate());
	    setLastSalaryGradeDate(rInfo.getLastSalaryGradeDate());
	    setEmployeeInfo(rInfo.getEmployeeInfo());
	    setPreviousRankInfo(rInfo.getPreviousRankInfo());
	    setInsertedBy(rInfo.getInsertedBy());
	    setComments(rInfo.getComments());
	}

	/**
	 * @return the rank
	 */
	public RankType getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(RankType rank) {
		this.rank = rank;
		this.rankString = rank.name();
	}

	/**
	 * @return the salaryGrade
	 */
	public Integer getSalaryGrade() {
		return salaryGrade;
	}

	/**
	 * @param salaryGrade the salaryGrade to set
	 */
	public void setSalaryGrade(Integer salaryGrade) {
		this.salaryGrade = salaryGrade;
	}

	/**
	 * @return the lastRankDate
	 */
	public Date getLastRankDate() {
		return lastRankDate;
	}

	/**
	 * @param lastRankDate the lastRankDate to set
	 */
	public void setLastRankDate(Date lastRankDate) {
		this.lastRankDate = lastRankDate;
	}

	/**
	 * @return the lastSalaryGradeDate
	 */
	public Date getLastSalaryGradeDate() {
		return lastSalaryGradeDate;
	}

	/**
	 * @param lastSalaryGradeDate the lastSalaryGradeDate to set
	 */
	public void setLastSalaryGradeDate(Date lastSalaryGradeDate) {
		this.lastSalaryGradeDate = lastSalaryGradeDate;
	}

	/**
	 * @return the employeeInfo
	 */
	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	/**
	 * @param employeeInfo the employeeInfo to set
	 */
	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	/**
	 * @return the previousRankInfo
	 */
	public RankInfo getPreviousRankInfo() {
		return previousRankInfo;
	}

	/**
	 * @param previousRankInfo the previousRankInfo to set
	 */
	public void setPreviousRankInfo(RankInfo previousRankInfo) {
		this.previousRankInfo = previousRankInfo;
	}



	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}



	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}



	/**
	 * @return the insertedBy
	 */
	public Principal getInsertedBy() {
		return insertedBy;
	}



	/**
	 * @param insertedBy the insertedBy to set
	 */
	public void setInsertedBy(Principal insertedBy) {
		this.insertedBy = insertedBy;
	}



	/**
	 * @return the rankString
	 */
	public String getRankString() {
		return rankString;
	}



	
}
