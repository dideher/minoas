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

	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Leave instance = new Leave();
		instance.setActive(Boolean.TRUE);
		instance.setLeaveType(LeaveType.THREE_MONTHS_LEAVE);
		Calendar cal = Calendar.getInstance();
		instance.setEstablished(new Date(cal.getTimeInMillis()));
		return instance;
	}

	protected boolean validateLeave(Leave leave, boolean addMessages) {
		Date established = DateUtils.truncate(leave.getEstablished(),
				Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils
				.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
		/* check if the dates are correct */
		if (established.after(dueTo)) {

			if (addMessages)
				facesMessages
						.add(
								Severity.ERROR,
								"Η ημ/νια λήξης της άδειας πρέπει να είναι μεταγενέστερη της έναρξης. Μήπως να κάνεις ένα διάλειμα ;");
			return false;
		}

		Collection<Leave> current_leaves = getCoreSearching()
				.getEmployeeLeaves(employeeHome.getInstance());
		for (Leave current_leave : current_leaves) {
			if (current_leave.getId().equals(leave.getId()))
				continue;
			Date current_established = DateUtils.truncate(current_leave
					.getEstablished(), Calendar.DAY_OF_MONTH);
			Date current_dueTo = DateUtils.truncate(current_leave.getDueTo(),
					Calendar.DAY_OF_MONTH);
			if (DateUtils.isSameDay(established, current_established)
					|| DateUtils.isSameDay(dueTo, current_dueTo)) {
				if (addMessages)
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

			if ((established.before(current_established) && dueTo
					.after(current_established))
					|| (established.after(current_established) && dueTo
							.before(current_dueTo))
					|| (established.before(current_dueTo) && dueTo
							.after(current_dueTo))) {
				if (addMessages)
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
		Employee employee = getEntityManager().merge(employeeHome.getInstance());
		Date established = DateUtils.truncate(newLeave.getEstablished(),
				Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(newLeave.getDueTo(),
				Calendar.DAY_OF_MONTH);
		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()),
				Calendar.DAY_OF_MONTH);

		if (!validateLeave(newLeave, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}
		
		newLeave.setEmployee(employee);
		/*
		 * check if the leave should be set as the employee's current leave. A
		 * leave can be set as the employee's current leave if and only if the
		 * leave's period is current (ie, today is after and before leave's
		 * established and dueTo dates respectively).
		 */
		if (today.after(established) && today.before(dueTo)) {
			employee.setLeave(newLeave);
		}
		
		if (employee.getCurrentEmployment() != null) {
			newLeave.setRegularSchool(getEntityManager().merge(employee.getCurrentEmployment()
					.getSchool()));
		}
		newLeave.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		Leave current_leave = getInstance();
		Employee employee = getEntityManager().merge(employeeHome.getInstance());
		Date established = DateUtils.truncate(current_leave.getEstablished(),
				Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(current_leave.getDueTo(),
				Calendar.DAY_OF_MONTH);
		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()),
				Calendar.DAY_OF_MONTH);
		if (!validateLeave(current_leave, true)) {
			return VALIDATION_ERROR_OUTCOME;
		}

		/*
		 * check if the leave should be set as the employee's current leave. A
		 * leave can be set as the employee's current leave if and only if the
		 * leave's period is current (ie, today is after and before leave's
		 * established and dueTo dates respectively).
		 */
		if (today.after(established) && today.before(dueTo)) {
			employee.setLeave(current_leave);
			
		} else {
			/* if the current leave is not the employee's current leave, then
			 * check if the leave used to be the employee's current leave and 
			 * if so, remove it.
			 */
			if (employee.getLeave() != null
					&& employee.getLeave().getId().equals(current_leave.getId())) {
				employee.setLeave(null);
			}
			
		}
		return super.update();
	}

	@Transactional
	public String cancel() {
		Leave current_leave = getInstance();
		current_leave.setActive(Boolean.FALSE);
		Employee employee = current_leave.getEmployee();

		/*
		 * if the canceled leave is the employee's current leave then update the
		 * employee as well.
		 */
		if (employee.getLeave() != null
				&& employee.getLeave().getId().equals(current_leave.getId()))
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
		info("principal #0 is reverting updates to leave #1",
				getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

}
