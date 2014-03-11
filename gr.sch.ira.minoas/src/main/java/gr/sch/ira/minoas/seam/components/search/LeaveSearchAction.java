/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author slavikos
 * 
 */
@Name("leaveSearch")
@Scope(ScopeType.CONVERSATION)
@Restrict("#{identity.loggedIn}")
public class LeaveSearchAction extends BaseSearchAction<EmployeeLeave> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	private Date insertionDateEnd = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	
	private Date insertionDateStart = DateUtils.truncate(DateUtils.addDays(new Date(), -2), Calendar.DAY_OF_MONTH);
	
	@DataModel
	public List<LeaveReportItem> leavesSearchResult;
	
	private String legacyLeaveCode;

	private String orderBy;

	private List<EmployeeLeave> privateLeavesSearchResult;

	@Override
	public Query constructQuery() {
		
		if(insertionDateStart == null)
			insertionDateStart =  DateUtils.truncate(DateUtils.addDays(new Date(), -2), Calendar.DAY_OF_MONTH);
		
		if(insertionDateEnd == null)
			insertionDateEnd = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		
			StringBuilder queryBuilder = new StringBuilder(
				"SELECT l FROM EmployeeLeave l WHERE (l.insertedOn IS NOT NULL AND l.insertedOn >= :insertionDateStart AND l.insertedOn <= :insertionDateEnd) AND ");
		List<String> paramList = new ArrayList<String>();
		
		paramList.add("deleted=0");

		if (CoreUtils.isNonEmpty(getLegacyLeaveCode())) {
			paramList.add("l.employeeLeaveType.legacyCode = :leaveLegacyCode");
		}
		Iterator<String> itr = paramList.iterator();

		while (itr.hasNext()) {
			queryBuilder.append(itr.next());
			if (itr.hasNext()) {
				queryBuilder.append(" AND ");
			}
		}
		
		if(orderBy.equals("date"))
			queryBuilder.append(" ORDER BY l.insertedOn");
		else
			queryBuilder.append(" ORDER BY l.employee.lastName, l.employee.firstName");
		
		final Query query = getEntityManager().createQuery(
				queryBuilder.toString());
		

		/* handle parameters */
		query.setParameter("insertionDateStart", insertionDateStart);
		query.setParameter("insertionDateEnd", insertionDateEnd);
		
		if (CoreUtils.isNonEmpty(getLegacyLeaveCode())) {
			query.setParameter("leaveLegacyCode", getLegacyLeaveCode());
		}

		return query;

	}

	public Date getInsertionDateEnd() {
		return insertionDateEnd;
	}

	public Date getInsertionDateStart() {
		return insertionDateStart;
	}
	
	public List<LeaveReportItem> getLeavesSearchResult() {
		return leavesSearchResult;
	}
	

	public String getLegacyLeaveCode() {
		return legacyLeaveCode;
	}

	
	public String getOrderBy() {
		return orderBy;
	}
	

	@Override
	protected List<EmployeeLeave> getResults() {
		return privateLeavesSearchResult;
	}
	

	public void setInsertionDateEnd(Date insertionDateEnd) {
		this.insertionDateEnd = insertionDateEnd;
	}

	public void setInsertionDateStart(Date insertionDateStart) {
		this.insertionDateStart = insertionDateStart;
	}

	public void setLeavesSearchResult(List<LeaveReportItem> leavesSearchResult) {
		this.leavesSearchResult = leavesSearchResult;
	}

	public void setLegacyLeaveCode(String legacyLeaveCode) {
		this.legacyLeaveCode = legacyLeaveCode;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	protected void setResults(List<EmployeeLeave> results) {
		privateLeavesSearchResult = results;
		leavesSearchResult = new ArrayList<LeaveReportItem>();
		for(EmployeeLeave l : results) {
			leavesSearchResult.add(new LeaveReportItem(l));
		}
	}

	

}
