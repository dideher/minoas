package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;



@Name("regularEmployeeLeavesOfPreviousYear")
@Scope(ScopeType.PAGE)
public class RegularEmployeeLeavesOfPreviousYear extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;
    
    @In(required=false)
    private Date leaveComputationReferenceDay;
    
    private Integer regularEmployeeLeavesOfPreviousYear = null;
    
    @Unwrap
    public Integer getRegularEmployeeLeavesOfCurrentYear() {
        if(regularEmployeeLeavesOfPreviousYear==null) {
            computeData();
        }
        return regularEmployeeLeavesOfPreviousYear;
    }
    
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void computeData() {
        Date today = leaveComputationReferenceDay != null ? leaveComputationReferenceDay : new Date();
        Date startOfYear =  DateUtils.truncate(DateUtils.addYears(DateUtils.setMonths(DateUtils.setDays(today, 1), 0), -1), Calendar.DAY_OF_MONTH);
        Date endOfYear = DateUtils.addYears(DateUtils.addDays(startOfYear, -1), 1);
        Collection<EmployeeLeaveType> leaveTypes = getCoreSearching().getLeaveTypes(CoreSearching.REGULAR_TYPE_LEAVE_TYPES);
        
        Collection<EmployeeLeave> leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance(), startOfYear, endOfYear, leaveTypes);
        int count = 0;
        for(EmployeeLeave l : leaves) {
        	try {
        		count+=l.getEffectiveNumberOfDays();
        	} catch(NullPointerException npe) {
        		// ignore
        	}
        }
        this.regularEmployeeLeavesOfPreviousYear = count;
    }
   

    
}
