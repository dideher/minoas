/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEvent;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.reports.resource.WorkAttendanceReportItem;

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
@Name("workAttendanceSearch")
@Scope(ScopeType.CONVERSATION)
@Restrict("#{identity.loggedIn}")
public class WorkAttendanceSearchAction extends
BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	private Date referenceDay = new Date();
	
	
	
	public Date getReferenceDay() {
		return referenceDay;
	}

	public void setReferenceDay(Date referenceDay) {
		this.referenceDay = referenceDay;
	}


	@DataModel
	public List<WorkAttendanceReportItem> workAttendanceSearchResult;

	
	public Query constructQuery() {
		Date fromDate = DateUtils.truncate(referenceDay, Calendar.DAY_OF_MONTH);
		Date toDate = DateUtils.addDays(fromDate, 1);
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT w FROM WorkAttendanceEvent w WHERE ");
		List<String> paramList = new ArrayList<String>();
		paramList.add("(w.occuredOn >= :fromDate AND w.occuredOn < :toDate)");

		
		Iterator<String> itr = paramList.iterator();

		while (itr.hasNext()) {
			queryBuilder.append(itr.next());
			if (itr.hasNext()) {
				queryBuilder.append(" AND ");
			}
		}
		
		queryBuilder.append(" ORDER BY w.insertedBy.realName");

		final Query query = getEntityManager().createQuery(
				queryBuilder.toString());

		/* handle parameters */
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		return query;

	}

	public void find() {
		Query q = constructQuery();
		List<WorkAttendanceEvent> r = (List<WorkAttendanceEvent>)q.getResultList();
		Map<Principal, List<WorkAttendanceEvent>> map = new LinkedHashMap<Principal, List<WorkAttendanceEvent>>();
		
		for(WorkAttendanceEvent e : r) {
			List<WorkAttendanceEvent> groupEvents = map.get(e.getInsertedBy());
			if(groupEvents == null) {
				groupEvents = new ArrayList<WorkAttendanceEvent>();
				map.put(e.getInsertedBy(), groupEvents);
			}
			groupEvents.add(e);
			
		}
		workAttendanceSearchResult = new ArrayList<WorkAttendanceReportItem>(map.size());
		for(Principal principal : map.keySet()) {
			List<WorkAttendanceEvent> groupEvents = map.get(principal);
			WorkAttendanceReportItem item = new WorkAttendanceReportItem(principal);
			for(WorkAttendanceEvent e : groupEvents) {
				switch (e.getAttendaceType()) {
				case ENTRY:
					item.addEntryEvent(e);
					break;
				case EXIT:
					item.addExitEvent(e);
					break;
				default:
					break;
				}
			}
			workAttendanceSearchResult.add(item);
		}
 		
	}
	

}
