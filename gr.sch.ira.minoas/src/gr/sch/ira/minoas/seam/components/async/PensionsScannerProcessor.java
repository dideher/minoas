package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Collection;
import java.util.Date;


import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * Synchronize Pensions in Spanoudakis DB
 * 
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("pensionsScannerProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class PensionsScannerProcessor extends BaseDatabaseAwareSeamComponent {

    @In(required = true, create = true, value="spanoudakisEntityManager")
    protected EntityManager spanoudakisEntityManager;
    
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public PensionsScannerProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<EmployeeLeave> getActiveLeaveThatSouldBeSetCurrent(EntityManager em, Date today) {
        return em.createQuery("SELECT s from EmployeeLeave s WHERE s.active IS TRUE AND s.employee.leave IS NULL AND  :onDate  BETWEEN s.established AND s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle schedulePensionsCleanup(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("will check for leaves that should be set current on #0", today);
	    getSpanoudakisEntityManager().getTransaction().begin();
	    Query query = getSpanoudakisEntityManager().createNativeQuery("UPDATE minoas..EMPLOYEE SET IS_ACTIVE=0 WHERE ID IN (SELECT e2.ID FROM minoas..EMPLOYEE e2 INNER JOIN mkdb..basiko AS b ON e2.LEGACY_CODE COLLATE DATABASE_DEFAULT=b.KVD COLLATE DATABASE_DEFAULT WHERE b.ANHKEI_ID='9'AND e2.IS_ACTIVE=1)");
	    int updatedEmployees = query.executeUpdate();
	    info(String.format("de-activated totally '%d' employees because they are in pension.", updatedEmployees));
	    
	    query = getSpanoudakisEntityManager().createNativeQuery("UPDATE minoas..SERVICE_ALLOCATION SET IS_ACTIVE=0 WHERE ID IN (SELECT s.ID FROM minoas..SERVICE_ALLOCATION s INNER JOIN minoas..EMPLOYEE e ON e.ID=s.EMPLOYEE_ID WHERE e.IS_ACTIVE=0) AND IS_ACTIVE=1");
	    int updatedserviceAllocations = query.executeUpdate();
	    info(String.format("de-activated totally '%d' service allocations.", updatedserviceAllocations));
	    
	    query = getSpanoudakisEntityManager().createNativeQuery("UPDATE minoas..SECONDMENT SET IS_ACTIVE=0 WHERE ID IN (SELECT s.ID FROM minoas..SECONDMENT s INNER JOIN minoas..EMPLOYEE e ON e.ID=s.EMPLOYEE_ID WHERE e.IS_ACTIVE=0) AND IS_ACTIVE=1");
        int updatedSecondments = query.executeUpdate();
        info(String.format("de-activated totally '%d' secondments.", updatedSecondments));
        
        query = getSpanoudakisEntityManager().createNativeQuery("UPDATE minoas..EMPLOYEE_LEAVES SET IS_ACTIVE=0 WHERE ID IN (SELECT l.ID FROM minoas..EMPLOYEE_LEAVES l INNER JOIN minoas..EMPLOYEE e ON e.ID=l.EMPLOYEE_ID WHERE e.IS_ACTIVE=0) AND IS_ACTIVE=1");
        int updatedLeaves= query.executeUpdate();
        info(String.format("de-activated totally '%d' leaves.", updatedLeaves));
        
        query = getSpanoudakisEntityManager().createNativeQuery("UPDATE minoas..EMPLOYMENT SET IS_ACTIVE=0 WHERE ID IN (SELECT em.ID FROM minoas..EMPLOYMENT em INNER JOIN minoas..EMPLOYEE e ON e.ID=em.EMPLOYEE_ID WHERE e.IS_ACTIVE=0) AND IS_ACTIVE=1");
        int updatedEmployments= query.executeUpdate();
        info(String.format("de-activated totally '%d' employments.", updatedEmployments));
        
	    getSpanoudakisEntityManager().getTransaction().commit();
	    getEntityManager().flush();
	    return null;
	}



    /**
     * @return the spanoudakisEntityManager
     */
    public EntityManager getSpanoudakisEntityManager() {
        return spanoudakisEntityManager;
    }



    /**
     * @param spanoudakisEntityManager the spanoudakisEntityManager to set
     */
    public void setSpanoudakisEntityManager(EntityManager spanoudakisEntityManager) {
        this.spanoudakisEntityManager = spanoudakisEntityManager;
    }
}
