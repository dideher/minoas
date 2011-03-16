package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
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
@Name("serviceAllocationCleanupProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class ServiceAllocationCleanupProcessor extends BaseDatabaseAwareSeamComponent {

    
    @In
    private CoreSearching coreSearching;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public ServiceAllocationCleanupProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<ServiceAllocation> getActiveServiceAllocationThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from ServiceAllocation s WHERE s.active IS TRUE AND :onDate NOT BETWEEN s.established AND s.dueTo").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for service allocation that should be canceled on #0", today);
	    Collection<ServiceAllocation> activeServiceAllocations = getActiveServiceAllocationThatSouldBeAutoCanceled(getEntityManager(), today);
	    info("found totally #0 service allocations that should be auto-canceled", activeServiceAllocations.size());
	    for(ServiceAllocation invalidServiceAllocation : activeServiceAllocations) {
	        info("auto canceling service allocation #0", invalidServiceAllocation);
	        invalidServiceAllocation.setActive(false);
	        invalidServiceAllocation.setAutoCanceled(Boolean.TRUE);
	        if(invalidServiceAllocation.getServiceAllocationCDR()!=null) {
	            getEntityManager().remove(invalidServiceAllocation.getServiceAllocationCDR());
	            invalidServiceAllocation.setServiceAllocationCDR(null);
	        }
	    }
	    getEntityManager().flush();
	    return null;
	}
}
