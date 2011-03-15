package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.converters.DatabaseAwareBaseConverter;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalCron;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("leaveCleanupProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class LeaveCleanup extends BaseDatabaseAwareSeamComponent {

    
    @In
    private CoreSearching coreSearching;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public LeaveCleanup() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Leave> getActiveLeaveThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Leave s WHERE s.active IS TRUE AND s.employee=:employee AND :onDate BETWEEN s.established AND s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, @IntervalCron String cron ) {
	    Date today = new Date();
	    info("will check for leaves that should be canceled on #0", today);
	    Collection<Leave> activeLeaves = getActiveLeaveThatSouldBeAutoCanceled(getEntityManager(), today);
	    info("found totally #0 leaves that should be auto-canceled", activeSecondments.size());
	    for(Leave invalidLeave : activeLeaves) {
	        info("auto canceling leaves #0", invalidSecondment);
	        invalidLeave.setActive(false);
	        invalidLeave.setAutoCanceled(Boolean.TRUE);
	        if(invalidSecondment.getSecondmentCDR()!=null) {
	            getEntityManager().remove(invalidSecondment.getSecondmentCDR());
	            invalidSecondment.setSecondmentCDR(null);
	        }
	    }
	    getEntityManager().flush();
	    return null;
	}
}
