package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
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
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("leaveCleanupProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class LeaveCleanupProcessor extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public LeaveCleanupProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Leave> getActiveLeaveThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Leave s WHERE s.active IS TRUE AND :onDate > s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for leaves that should be canceled on #0", today);
	    Collection<Leave> activeLeaves = getActiveLeaveThatSouldBeAutoCanceled(getEntityManager(), today);
	    info("found totally #0 leaves that should be auto-canceled", activeLeaves.size());
	    for(Leave invalidLeave : activeLeaves) {
	        info("auto canceling leave #0", invalidLeave);
	        invalidLeave.setActive(false);
	        invalidLeave.setAutoCanceled(Boolean.TRUE);
	        Employee em = invalidLeave.getEmployee();
	        em.setLeave(null);
	        if(invalidLeave.getLeaveCDRs()!=null) {
                for(TeachingHourCDR cdr : invalidLeave.getLeaveCDRs()) {
                    cdr.setLeave(null);
                    getEntityManager().remove(cdr);
                }
                invalidLeave.getLeaveCDRs().clear();
            }
	    }
	    getEntityManager().flush();
	    return null;
	}
}
