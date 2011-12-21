package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.FinalExpiration;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * Searches for active leaves that should be set as current for a given
 * employee.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("leaveActivationProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class LeaveActivactionProcessor extends BaseDatabaseAwareSeamComponent {

    
    
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public LeaveActivactionProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Leave> getActiveLeaveThatSouldBeSetCurrent(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Leave s WHERE s.active IS TRUE AND s.employee.leave IS NULL AND  :onDate  BETWEEN s.established AND s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for leaves that should be set current on #0", today);
	    Collection<Leave> activeLeaves = getActiveLeaveThatSouldBeSetCurrent(getEntityManager(), today);
	    info("found totally #0 leaves should be set current", activeLeaves.size());
	    for(Leave newCurrentLeave : activeLeaves) {
	        Employee employee = newCurrentLeave.getEmployee();
	        info("setting leave #0 as current for employee #1", newCurrentLeave, employee);
	        employee.setLeave(newCurrentLeave);
	    }
	    getEntityManager().flush();
	    return null;
	}
}
