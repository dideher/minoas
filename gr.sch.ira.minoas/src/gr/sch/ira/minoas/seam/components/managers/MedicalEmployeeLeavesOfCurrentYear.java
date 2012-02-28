package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Arrays;
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



@Name("medicalEmployeeLeavesOfCurrentYear")
@Scope(ScopeType.PAGE)
public class MedicalEmployeeLeavesOfCurrentYear extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;
    
    private Integer medicalEmployeeLeavesOfCurrentYear = null;
    
    @In(required=false)
    private Date leaveComputationReferenceDay;
    
    
    @Unwrap
    public Integer getRegularEmployeeLeavesOfCurrentYear() {
        if(medicalEmployeeLeavesOfCurrentYear==null) {
            computeData();
        }
        return medicalEmployeeLeavesOfCurrentYear;
    }
    
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void computeData() {
        Date today = leaveComputationReferenceDay != null ? leaveComputationReferenceDay : new Date();
        Date startOfYear =  DateUtils.truncate(DateUtils.setMonths(DateUtils.setDays(today, 1), 0), Calendar.DAY_OF_MONTH);
        Date endOfYear = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
        Collection<EmployeeLeaveType> leaveTypes = getCoreSearching().getLeaveTypes(CoreSearching.MEDICAL_TYPE_LEAVE_TYPES);
        
        Collection<EmployeeLeave> leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance(), startOfYear, endOfYear, leaveTypes);
        int count = 0;
        for(EmployeeLeave l : leaves) {
            count+=l.getEffectiveNumberOfDays();
        }
        this.medicalEmployeeLeavesOfCurrentYear = count;
    }
   

    
}
