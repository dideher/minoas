/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;

import java.util.Calendar;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.omg.CosCollection.SetHolder;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "leaveHome")
public class LeaveHome extends MinoasEntityHome<Leave> {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected String tempValueHolder1; /* used as a holder value in forms */ 
	

	@Transactional
	public String cancel() {
		Leave current_leave = getInstance();
		current_leave.setActive(Boolean.FALSE);
		Employee employee = current_leave.getEmployee();

		/*
		 * if the canceled leave is the employee's current leave then update the
		 * employee as well.
		 */
		if (employee.getLeave() != null && employee.getLeave().getId().equals(current_leave.getId()))
			employee.setLeave(null);
		super.update();
		info("principal '#0' canceled employee #1 current leave #1.", getPrincipalName(), employee, current_leave);
		clearInstance();
		return "updated";
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Leave createInstance() {
		Leave instance = new Leave();
		instance.setActive(Boolean.TRUE);
		instance.setLeaveType(LeaveType.THREE_MONTHS_LEAVE);
		Calendar cal = Calendar.getInstance();
		instance.setEstablished(new Date(cal.getTimeInMillis()));
		return instance;
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

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
	    return super.persist();
	    //Leave newLeave = getInstance();
//		Employee employee = getEntityManager().merge(employeeHome.getInstance());
//		Date established = DateUtils.truncate(newLeave.getEstablished(), Calendar.DAY_OF_MONTH);
//		Date dueTo = DateUtils.truncate(newLeave.getDueTo(), Calendar.DAY_OF_MONTH);
//		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
//
//		if (!validateLeave(newLeave, true)) {
//			return VALIDATION_ERROR_OUTCOME;
//		}
//
//		newLeave.setEmployee(employee);
//		/*
//		 * check if the leave should be set as the employee's current leave. A
//		 * leave can be set as the employee's current leave if and only if the
//		 * leave's period is current (ie, today is after and before leave's
//		 * established and dueTo dates respectively).
//		 */
//		if (today.after(established) && today.before(dueTo)) {
//			employee.setLeave(newLeave);
//		}
//
//		if (employee.getCurrentEmployment() != null) {
//			newLeave.setRegularSchool(getEntityManager().merge(employee.getCurrentEmployment().getSchool()));
//		}
//		newLeave.setInsertedBy(getPrincipal());
//		return super.persist();
	}

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to leave #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
	  return super.update();
//		Leave current_leave = getInstance();
//		Employee employee = getEntityManager().merge(employeeHome.getInstance());
//		Date established = DateUtils.truncate(current_leave.getEstablished(), Calendar.DAY_OF_MONTH);
//		Date dueTo = DateUtils.truncate(current_leave.getDueTo(), Calendar.DAY_OF_MONTH);
//		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
//		if (!validateLeave(current_leave, true)) {
//			return VALIDATION_ERROR_OUTCOME;
//		}
//
//		/*
//		 * check if the leave should be set as the employee's current leave. A
//		 * leave can be set as the employee's current leave if and only if the
//		 * leave's period is current (ie, today is after and before leave's
//		 * established and dueTo dates respectively).
//		 */
//		if (today.after(established) && today.before(dueTo)) {
//			employee.setLeave(current_leave);
//
//		} else {
//			/* if the current leave is not the employee's current leave, then
//			 * check if the leave used to be the employee's current leave and 
//			 * if so, remove it.
//			 */
//			if (employee.getLeave() != null && employee.getLeave().getId().equals(current_leave.getId())) {
//				employee.setLeave(null);
//			}
//
//		}
//		return super.update();
	}

    /**
     * @return the tempValueHolder1
     */
    public String getTempValueHolder1() {
        return tempValueHolder1;
    }

    /**
     * @param tempValueHolder1 the tempValueHolder1 to set
     */
    public void setTempValueHolder1(String tempValueHolder1) {
        this.tempValueHolder1 = tempValueHolder1;
    }

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#clearInstance()
     */
    @Override
    public void clearInstance() {
        super.clearInstance();
        setTempValueHolder1(null);
    }
	

}
