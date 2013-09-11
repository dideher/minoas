package gr.sch.ira.minoas.seam.components.startup;

import static org.jboss.seam.ScopeType.SESSION;
import static org.jboss.seam.annotations.Install.APPLICATION;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEvent;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEventType;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Transactional;

@Name(value = "employeeWorkAttendaceController")
@Scope(SESSION)
@Install(precedence = APPLICATION)
@Startup
public class EmployeeWorkAttendaceController extends
		BaseDatabaseAwareSeamComponent {

	public static final String EMPLOYEE_WORK_ATTENDANCE_ENTRY = "EMPLOYEE_WORK_ATTENDANCE_ENTRY";

	public static final String EMPLOYEE_WORK_ATTENDANCE_EXIT = "EMPLOYEE_WORK_ATTENDANCE_EXIT";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	protected Principal getPrincipal(String principalName) {
		try {
			return (Principal) getEntityManager()
					.createQuery(
							"SELECT p FROM Principal p WHERE p.username = :username")
					.setParameter("username", principalName).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}
	}

	@Observer(EMPLOYEE_WORK_ATTENDANCE_ENTRY)
	@Transactional
	public void handlePrincipalEntry(String principal, Date event) {
		
		/*
		 * we will store the entry only if there is no other ENTRY type event
		 * registered in the database for the given principal
		 */
		Date insertedDate = new Date();
		Date beginOfToday = DateUtils.truncate(insertedDate,
				Calendar.DAY_OF_MONTH);

		Principal principalObject = getPrincipal(principal);

		Long result = (Long) getEntityManager()
				.createQuery(
						"SELECT COUNT(*) FROM WorkAttendanceEvent e WHERE e.insertedBy=:principal AND e.attendaceType=:attendaceType AND (e.occuredOn >= :beginOfToday AND e.occuredOn <= :now)")
				.setParameter("principal", principalObject)
				.setParameter("attendaceType", WorkAttendanceEventType.ENTRY)
				.setParameter("beginOfToday", beginOfToday)
				.setParameter("now", insertedDate).getSingleResult();
		if (result == 0) {
			WorkAttendanceEvent workevent = new WorkAttendanceEvent();
			workevent.setAttendaceType(WorkAttendanceEventType.ENTRY);
			workevent.setInsertedOn(insertedDate);
			/* gh-136 : deduct 15 minutes in favour of employee */
			workevent.setOccuredOn(DateUtils.addMinutes(insertedDate, -15));
			workevent.setInsertedBy(principalObject);
			workevent
					.setComment("Αυτόματη εισαγωγή κατά την είσοδο στο σύστημα.");
			getEntityManager().persist(workevent);
		}

	}

	@SuppressWarnings("unchecked")
	@Observer(EMPLOYEE_WORK_ATTENDANCE_EXIT)
	@Transactional
	public void handlePrincipalExit(String principal, Date event) {
		
		Date insertedDate = new Date();
		Date beginOfToday = DateUtils.truncate(insertedDate,
				Calendar.DAY_OF_MONTH);
		
		Principal principalObject = getPrincipal(principal);
		 
		List<WorkAttendanceEvent> result = (List<WorkAttendanceEvent>)getEntityManager()
				.createQuery(
						"SELECT e FROM WorkAttendanceEvent e WHERE e.insertedBy=:principal AND e.attendaceType=:attendaceType AND (e.occuredOn >= :beginOfToday AND e.occuredOn <= :now)")
				.setParameter("principal", principalObject)
				.setParameter("attendaceType", WorkAttendanceEventType.EXIT)
				.setParameter("beginOfToday", beginOfToday)
				.setParameter("now", insertedDate).getResultList();
		
		if(result.size()>0) {
			WorkAttendanceEvent workevent = result.get(0);
			workevent.setOccuredOn(insertedDate);
			getEntityManager().merge(workevent);
		} else {
			WorkAttendanceEvent workevent = new WorkAttendanceEvent();
			workevent.setAttendaceType(WorkAttendanceEventType.EXIT);
			workevent.setInsertedOn(insertedDate);
			workevent.setOccuredOn(insertedDate);
			workevent.setInsertedBy(principalObject);
			workevent
					.setComment("Αυτόματη εισαγωγή κατά την έξοδο από το σύστημα.");
			getEntityManager().persist(workevent);
		}
		getEntityManager().flush();
		
	}

}
