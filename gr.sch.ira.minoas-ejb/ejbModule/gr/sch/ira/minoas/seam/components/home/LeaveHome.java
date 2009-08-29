/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "leaveHome")
public class LeaveHome extends MinoasEntityHome<Leave> {

	@In(create = true)
	private EmployeeHome employeeHome;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transactional
	public boolean wire() {
		Leave instance = getInstance();
		if (!isManaged()) {
			Employee employee = employeeHome != null ? employeeHome
					.getInstance() : null;
			instance.setEmployee(employee);
		}
		return true;
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Leave instance = new Leave();
		instance.setLeaveType(LeaveType.LABOUR_LEAVE);
		Calendar cal = Calendar.getInstance();
		instance.setEstablished(new Date(cal.getTimeInMillis()));
		cal.add(Calendar.DAY_OF_MONTH, instance.getLeaveType()
				.getDurationInDays());
		instance.setDueTo(new Date(cal.getTimeInMillis()));
		return instance;
	}
	
	protected boolean validateLeave(Leave leave,
			boolean addMessages) {
		Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
		/* check if the dates are correct */
		if (established.after(
				dueTo)) {

			if (addMessages)
				facesMessages
						.add(
								Severity.ERROR,
								"Η ημ/νια λήξης της άδειας πρέπει να είναι μεταγενέστερη της έναρξης. Μήπως να κάνεις ένα διάλειμα ;");
			return false;
		}
		
		
		
		
		Collection<Leave> current_leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance());
		for(Leave current_leave : current_leaves) {
			if(current_leave.getId().equals(leave.getId()))
				continue;
			Date current_established = DateUtils.truncate(current_leave.getEstablished(), Calendar.DAY_OF_MONTH);
			Date current_dueTo = DateUtils.truncate(current_leave.getDueTo(), Calendar.DAY_OF_MONTH);
			if(DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
				if(addMessages) 
					facesMessages
					.add(
							Severity.ERROR,
							"Υπάρχει ήδει άδεια με τις ημερομηνίες που εισάγατε. Μήπως να κάνεις ένα διάλειμα ;");
				return false;
			}
			
			if (DateUtils.isSameDay(established, current_dueTo)) {

				if (addMessages)
					facesMessages
							.add(
									Severity.ERROR,
									"Η ημ/νια έναρξης της άδειας πρέπει να είναι μεταγενέστερη της λήξης της προηγούμενης άδειας. Μήπως να κάνεις ένα διάλειμα ;");
				return false;
			}
			
			
			if((established.before(current_established) && dueTo.after(current_established)) || ( established.after(current_established) && dueTo.before(current_dueTo)) || (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
				if(addMessages) 
					facesMessages
					.add(
							Severity.ERROR,
							"Υπάρχει επικαλυπτόμενο διάστημα με υπάρχουσες άδειες! Μήπως να κάνεις ένα διάλειμα ;");
				return false;
			}
			
		}
		
		return true;

	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		Leave newLeave = getInstance();
		Employee employee = employeeHome.getInstance();
		
		

		if (!validateLeave(newLeave, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}
		
		/* check if the leave should be activated. A leave can be activated
		 * if and only if the leave's period is current (ie, today is after and 
		 * before leave's established and dueTo dates respectively).
		 */
		
		Date today = new Date(System.currentTimeMillis());
		newLeave.setActive(today.after(newLeave.getEstablished()) && today.before(newLeave.getDueTo()));
		
		/* if the leave is active, the update the employee */
		if(newLeave.getActive()) {
			employee.setLeave(newLeave);
		}
		newLeave.setInsertedBy(getPrincipal());
		newLeave.setEmployee(employee);
		return super.persist();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		Leave current_leave = getInstance();
		Employee employee = employeeHome.getInstance();
		if (!validateLeave(current_leave, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}
		
		/* check if the leave should be activated. A leave can be activated
		 * if and only if the leave's period is current (ie, today is after and 
		 * before leave's established and dueTo dates respectively).
		 */
		
		Date today = new Date(System.currentTimeMillis());
		current_leave.setActive(today.after(current_leave.getEstablished()) && today.before(current_leave.getDueTo()));
		
		/* if the leave is active, then update the employee */
		if(current_leave.getActive()) {
			employee.setLeave(current_leave);
		} else {
			employee.setLeave(null);
		}
		return super.update();
	}

	
	@Transactional
	public String cancel() {
		Leave current_leave = getInstance();
		current_leave.setActive(Boolean.FALSE);
		Employee employee = current_leave.getEmployee();
		if (employee != null)
			employee.setLeave(null);
		super.update();
		info("principal '#0' canceled employee #1 current leave #1.",
				getPrincipalName(), employee, current_leave);
		clearInstance();
		return "updated";
	}
	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "leave", scope = ScopeType.PAGE)
	public Leave getInstance() {
		// TODO Auto-generated method stub
		return (Leave) super.getInstance();
	}
	
	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to leave #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

}
