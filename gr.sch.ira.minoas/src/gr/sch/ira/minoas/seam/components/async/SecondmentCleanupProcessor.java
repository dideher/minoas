package gr.sch.ira.minoas.seam.components.async;

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
import org.jboss.seam.annotations.async.FinalExpiration;
import org.jboss.seam.annotations.async.IntervalCron;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentCleanupProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class SecondmentCleanupProcessor extends BaseDatabaseAwareSeamComponent {

    
    @In
    private CoreSearching coreSearching;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public SecondmentCleanupProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Secondment> getActiveSecondmentsThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Secondment s WHERE s.active IS TRUE AND :onDate NOT BETWEEN s.established AND s.dueTo").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for secondments that should be canceled on #0", today);
	    Collection<Secondment> activeSecondments = getActiveSecondmentsThatSouldBeAutoCanceled(getEntityManager(), today);
	    info("found totally #0 secondments that should be auto-canceled", activeSecondments.size());
	    for(Secondment invalidSecondment : activeSecondments) {
	        info("auto canceling secondment #0", invalidSecondment);
	        invalidSecondment.setActive(false);
	        invalidSecondment.setAutoCanceled(Boolean.TRUE);
	        if(invalidSecondment.getSecondmentCDR()!=null) {
	            getEntityManager().remove(invalidSecondment.getSecondmentCDR());
	            invalidSecondment.setSecondmentCDR(null);
	        }
	    }
	    getEntityManager().flush();
	    return null;
	}
}
