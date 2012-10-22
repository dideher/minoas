package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.WorkExperienceCalculation;

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
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("regularEmployeeServiceUpdaterProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class RegularEmployeeServiceUpdaterProcessor extends BaseDatabaseAwareSeamComponent {


    @In(required=true, create=true)
    private WorkExperienceCalculation workExperienceCalculation;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public RegularEmployeeServiceUpdaterProcessor() {
	}

	
	
	@SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<EmployeeLeave> getActiveLeaveThatSouldBeAutoCanceled(EntityManager em, Date today) {
        return em.createQuery("SELECT s from EmployeeLeave s WHERE s.active IS TRUE AND :onDate > s.dueTo ORDER BY s.established").setParameter("onDate", today).getResultList();
    }
	
	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle schedule(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("We will update regular employee's service as of today (#0)", today);
	    Collection<Employee> employees = getCoreSearching().getActiveEmployeesOfType(entityManager, EmployeeType.REGULAR);
	    info("found totally '#0' active regular employees. We will update each one of them.", employees.size());
	    for(Employee employee : employees) {
	        workExperienceCalculation.updateEmployeeExperience(employee);
	        getEntityManager().flush();
	    }
	    info("updated !");
	    return null;
	}
}
