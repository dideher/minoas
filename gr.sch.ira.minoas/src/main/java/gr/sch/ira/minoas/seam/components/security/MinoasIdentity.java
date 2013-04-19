/**
 * 
 */
package gr.sch.ira.minoas.seam.components.security;

import static org.jboss.seam.ScopeType.SESSION;
import static org.jboss.seam.annotations.Install.APPLICATION;
import gr.sch.ira.minoas.seam.components.startup.EmployeeWorkAttendaceController;

import java.util.Date;

import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.Events;
import org.jboss.seam.security.Identity;

/**
 * @author slavikos
 *
 */
@Name("org.jboss.seam.security.identity")
@Scope(SESSION)
@BypassInterceptors
@Install(precedence=APPLICATION)
@Startup
public class MinoasIdentity extends Identity {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String login() {
		String result = super.login();
		try{
			if(result != null && result.equals("loggedIn")) {
				Events.instance().raiseAsynchronousEvent(EmployeeWorkAttendaceController.EMPLOYEE_WORK_ATTENDANCE_ENTRY, getPrincipal().getName(), new Date());
			}
		} catch(Exception ex) {
			
		}
		return result;
	}

	@Override
	public void logout() {
		String principal = getPrincipal().getName();
		Date eventDate = new Date();
		super.logout();
		Events.instance().raiseAsynchronousEvent(EmployeeWorkAttendaceController.EMPLOYEE_WORK_ATTENDANCE_EXIT, principal, eventDate);
	}

}
