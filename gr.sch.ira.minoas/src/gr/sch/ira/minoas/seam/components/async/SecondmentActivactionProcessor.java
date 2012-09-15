package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
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
 * Searches for secondments that must be activated.
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentActivationProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class SecondmentActivactionProcessor extends BaseDatabaseAwareSeamComponent {

    
    
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public SecondmentActivactionProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Secondment> getSecondmentThatShouldBeActivated(EntityManager em, Date today) {
        return em.createQuery("SELECT s from Secondment s WHERE s.active IS FALSE AND (s.deleted IS FALSE OR s.deleted IS NULL) AND :onDate  BETWEEN s.established AND s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle scheduleSecondmentActivation(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for leaves that should be set current on #0", today);
	    Collection<Secondment> secondmentsThatShouldBeActivated = getSecondmentThatShouldBeActivated(getEntityManager(), today);
	    info("found totally #0 secondments that should be activated", secondmentsThatShouldBeActivated.size());
	    for(Secondment secondment : secondmentsThatShouldBeActivated) {
	        Employee employee = secondment.getEmployee();
	        info("setting secondment #0 as active for employee #1", secondment, employee);
	        secondment.setActive(Boolean.TRUE);
	        
	        //employee.setLeave(newCurrentLeave);
	        //newCurrentLeave.setActive(Boolean.TRUE);
	    }
	    getEntityManager().flush();
	    return null;
	}
}
