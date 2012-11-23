package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
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
 * Searches for disposals that must be activated.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("serviceAllocationActivationProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class ServiceAllocationActivactionProcessor extends BaseDatabaseAwareSeamComponent {

    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public ServiceAllocationActivactionProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<ServiceAllocation> getServiceAllocationThatShouldBeActivated(EntityManager em, Date today) {
        return em.createQuery("SELECT s from ServiceAllocation s WHERE s.active IS FALSE AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND :onDate  BETWEEN s.established AND s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleServiceAllocationActivation(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for service allocations that should be set current on #0", today);
	    Collection<ServiceAllocation> serviceAllocationsThatShouldBeActivated = getServiceAllocationThatShouldBeActivated(getEntityManager(), today);
	    info("found totally #0 disposals that should be activated", serviceAllocationsThatShouldBeActivated.size());
	    for(ServiceAllocation serviceAllocation : serviceAllocationsThatShouldBeActivated) {
	        Employee employee = serviceAllocation.getEmployee();
	        info("setting service allocation #0 as active for employee #1", serviceAllocation, employee);
	        //serviceAllocation.setActive(Boolean.TRUE);
	        
	        //employee.setLeave(newCurrentLeave);
	        //newCurrentLeave.setActive(Boolean.TRUE);
	    }
	    getEntityManager().flush();
	    return null;
	}
}
