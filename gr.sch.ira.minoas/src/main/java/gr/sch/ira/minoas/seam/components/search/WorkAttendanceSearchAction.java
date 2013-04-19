/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import gr.sch.ira.minoas.model.employee.WorkAttendanceEvent;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.reports.resource.WorkAttendanceReportItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
@Name("workAttendanceSearch")
@Scope(ScopeType.PAGE)
@Restrict("#{identity.loggedIn}")
public class WorkAttendanceSearchAction extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	private Date referenceDay = new Date();

	private Date referenceDayFrom = new Date();

	private Date referenceDayTo = new Date();

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

	public Query constructQueryForPersonalEvents() {
		Date fromDate = DateUtils.truncate(referenceDayFrom, Calendar.DAY_OF_MONTH);
		Date toDate = DateUtils.addDays(DateUtils.truncate(referenceDayTo, Calendar.DAY_OF_MONTH),1);
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT w FROM WorkAttendanceEvent w WHERE ");
		List<String> paramList = new ArrayList<String>();
		paramList.add("(w.occuredOn > :fromDate AND w.occuredOn < :toDate)");
		paramList.add("(w.insertedBy = :principal)");
		Iterator<String> itr = paramList.iterator();

		while (itr.hasNext()) {
			queryBuilder.append(itr.next());
			if (itr.hasNext()) {
				queryBuilder.append(" AND ");
			}
		}

		queryBuilder.append(" ORDER BY w.occuredOn");

		final Query query = getEntityManager().createQuery(
				queryBuilder.toString());

		/* handle parameters */
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		query.setParameter("principal", getPrincipal());

		return query;

	}

	/**
	 * Constructs a list of {@link WorkAttendanceReportItem} from a list of {@link WorkAttendanceEvent} results
	 * for all principals for a given date.
	 * @param events
	 * @return
	 */
	protected ArrayList<WorkAttendanceReportItem> constructResult(
			List<WorkAttendanceEvent> events) {
		
		Map<Date, Map<Principal, List<WorkAttendanceEvent>>> outerMap = new LinkedHashMap<Date, Map<Principal,List<WorkAttendanceEvent>>>();
		
		
		for (WorkAttendanceEvent e : events) {
			Date tempDate = DateUtils.truncate(e.getOccuredOn(), Calendar.DAY_OF_MONTH);
			
			Map<Principal, List<WorkAttendanceEvent>> map = outerMap.get(tempDate);
			if(map == null) {
				map = new LinkedHashMap<Principal, List<WorkAttendanceEvent>>(); 
				outerMap.put(tempDate, map);
			}
			
			List<WorkAttendanceEvent> groupEvents = map.get(e.getInsertedBy());
			if (groupEvents == null) {
				groupEvents = new ArrayList<WorkAttendanceEvent>();
				map.put(e.getInsertedBy(), groupEvents);
			}
			groupEvents.add(e);

		}
		
		ArrayList<WorkAttendanceReportItem> workAttendanceSearchResult = new ArrayList<WorkAttendanceReportItem>(
				outerMap.size());
		for(Map<Principal, List<WorkAttendanceEvent>> map : outerMap.values()) {
			for (Principal principal : map.keySet()) {
				List<WorkAttendanceEvent> groupEvents = map.get(principal);
				WorkAttendanceReportItem item = new WorkAttendanceReportItem(
						principal);
				for (WorkAttendanceEvent e : groupEvents) {
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
		
		return workAttendanceSearchResult;
	}
	
	@SuppressWarnings("unchecked")
	public void find() {
		Query q = constructQuery();
		List<WorkAttendanceEvent> r = q
				.getResultList();
		workAttendanceSearchResult = constructResult(r);

	}

	
	@SuppressWarnings("unchecked")
	public void findMyWorkAttandances() {
		Query q = constructQueryForPersonalEvents();
		List<WorkAttendanceEvent> r = q
				.getResultList();
		workAttendanceSearchResult = constructResult(r);
	}

	public Date getReferenceDay() {
		return referenceDay;
	}
	
	public Date getReferenceDayFrom() {
		return referenceDayFrom;
	}

	public Date getReferenceDayTo() {
		return referenceDayTo;
	}

	public void reset() {
		this.referenceDay = new Date();
		this.referenceDayFrom = new Date();
		this.referenceDayTo = new Date();
		if(workAttendanceSearchResult!=null)
			workAttendanceSearchResult.clear();
	}

	public void setReferenceDay(Date referenceDay) {
		this.referenceDay = referenceDay;
	}

	public void setReferenceDayFrom(Date referenceDayFrom) {
		this.referenceDayFrom = referenceDayFrom;
	}

	public void setReferenceDayTo(Date referenceDayTo) {
		this.referenceDayTo = referenceDayTo;
	}

}
