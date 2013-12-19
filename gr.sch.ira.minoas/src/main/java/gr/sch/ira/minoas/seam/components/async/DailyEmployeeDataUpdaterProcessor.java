package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.RankInfoCalculation;
import gr.sch.ira.minoas.seam.components.WorkExperienceCalculation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
@Name("dailyEmployeeDataUpdaterProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class DailyEmployeeDataUpdaterProcessor extends BaseDatabaseAwareSeamComponent {


    @In(required=true, create=true)
    private WorkExperienceCalculation workExperienceCalculation;
    
    @In(required=true, create=true)
    private RankInfoCalculation rankInfoCalculation;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public DailyEmployeeDataUpdaterProcessor() {
	}

	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle schedule(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    Date today = new Date();
	    info("We will update regular employee's service as of today (#0)", today);
	    int count = 0;
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    
	    List<Integer> employeeIDs = getEntityManager().createQuery("SELECT e.id FROM Employee e INNER JOIN e.employeeInfo WHERE e.type=:employeeType AND e.active IS TRUE AND (e.serviceLastUpdated IS NULL or e.serviceLastUpdated<:lastUpdated)")
                .setParameter("employeeType", EmployeeType.REGULAR).setParameter("lastUpdated", cal.getTime()).getResultList();
	    
	   info("found totally '#0' active regular employees whose work experience and RankInfo we will update.", employeeIDs.size());
	    for(Integer employeeID : employeeIDs) {
	        Employee employee = getEntityManager().find(Employee.class, employeeID);
	        try {
	            workExperienceCalculation.updateEmployeeExperience(getEntityManager().find(Employee.class, employeeID));
	            
	        } catch(Exception ex) {
	            error(String.format("failed to update regular employee '%s' service due to an exception.", employee), ex);
	        }
	        
	        
	        //	Try to re-calculate the RankInfo for this employee. Maybe he needs to change Rank or SalaryGrade.
	       rankInfoCalculation.recalculateRankInfo(employee);
	        
	        employee.setServiceLastUpdated(new Date());
            if(count++>100) {
                info("updated work experience and RankInfo calculations for #0 employees, bailing out.", count);
                break;
            }	        
	        
	    }
	    getEntityManager().flush();
	    info("finished !");
	    return null;
	}
}
