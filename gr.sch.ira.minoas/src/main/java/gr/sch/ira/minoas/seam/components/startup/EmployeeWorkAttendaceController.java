package gr.sch.ira.minoas.seam.components.startup;

import static org.jboss.seam.ScopeType.SESSION;
import static org.jboss.seam.annotations.Install.APPLICATION;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEvent;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEventType;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Date;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Name(value = "employeeWorkAttendaceController")
@Scope(SESSION)
@Install(precedence=APPLICATION)
@Startup
public class EmployeeWorkAttendaceController extends BaseDatabaseAwareSeamComponent {
    
	
	public static final String EMPLOYEE_WORK_ATTENDANCE_ENTRY = "EMPLOYEE_WORK_ATTENDANCE_ENTRY";
	
	public static final String EMPLOYEE_WORK_ATTENDANCE_EXIT = "EMPLOYEE_WORK_ATTENDANCE_EXIT";
	
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    protected Principal getPrincipal(String principalName) {
		try {
			return (Principal) getEntityManager().createQuery("SELECT p FROM Principal p WHERE p.username = :username")
					.setParameter("username", principalName).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}
	}
    
    
    @Observer(EMPLOYEE_WORK_ATTENDANCE_ENTRY)
    @Transactional
    public void handlePrincipalEntry(String principal, Date event) {
    	System.err.println(String.format("principal '%s' has entered on '%s'.", principal, event));
    	WorkAttendanceEvent workevent = new WorkAttendanceEvent();
    	workevent.setAttendaceType(WorkAttendanceEventType.ENTRY);
    	workevent.setInsertedOn(new Date());
    	workevent.setInsertedBy(getPrincipal(principal));
    	workevent.setComment("Αυτόματη εισαγωγή κατά την είσοδο στο σύστημα.");
    	getEntityManager().persist(workevent);
    }
    
    @Observer(EMPLOYEE_WORK_ATTENDANCE_EXIT)
    @Transactional
    public void handlePrincipalExit(String principal, Date event) {
    	System.err.println(String.format("principal '%s' has entered on '%s'.", principal, event));
    	WorkAttendanceEvent workevent = new WorkAttendanceEvent();
    	workevent.setAttendaceType(WorkAttendanceEventType.EXIT);
    	workevent.setInsertedOn(new Date());
    	workevent.setInsertedBy(getPrincipal(principal));
    	workevent.setComment("Αυτόματη εισαγωγή κατά την έξοδο απο το σύστημα.");
    	getEntityManager().persist(workevent);
    }
    

}
