package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;



@Name("currentEmployeeLeave")
@Scope(ScopeType.PAGE)
public class CurrentEmployeeLeave extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;
    
    private EmployeeLeave currentLeave = null;
    
    @Unwrap
    public EmployeeLeave getEmployeeLeaves() {
        if(currentLeave==null) {
            constructCurrentLeaveForEmployee();
        }
        return currentLeave;
    }
    
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void constructCurrentLeaveForEmployee() {
        this.currentLeave = getCoreSearching().getEmployeeActiveLeave2(getEntityManager(), employeeHome.getInstance(), new Date());
    }
   

    
}
