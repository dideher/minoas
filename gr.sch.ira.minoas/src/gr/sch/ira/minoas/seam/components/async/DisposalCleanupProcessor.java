package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
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
@Name("disposalCleanupProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class DisposalCleanupProcessor extends BaseDatabaseAwareSeamComponent {

    
    @In
    private CoreSearching coreSearching;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public DisposalCleanupProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Disposal> getActiveDisposalThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Disposal s WHERE s.active IS TRUE AND :onDate NOT BETWEEN s.established AND s.dueTo").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for disposals that should be canceled on #0", today);
	    Collection<Disposal> activeDisposals = getActiveDisposalThatSouldBeAutoCanceled(getEntityManager(), today);
	    info("found totally #0 disposals that should be auto-canceled", activeDisposals.size());
	    for(Disposal invalidDisposal : activeDisposals) {
	        info("auto canceling disposal #0", invalidDisposal);
	        invalidDisposal.setActive(false);
	        invalidDisposal.setAutoCanceled(Boolean.TRUE);
	        if(invalidDisposal.getDisposalCDRs()!=null) {
	            for(TeachingHourCDR cdr : invalidDisposal.getDisposalCDRs()) {
	                cdr.setDisposal(null);
	                getEntityManager().remove(cdr);
	            }
	            invalidDisposal.getDisposalCDRs().clear();
	        }
	    }
	    getEntityManager().flush();
	    return null;
	}
}
