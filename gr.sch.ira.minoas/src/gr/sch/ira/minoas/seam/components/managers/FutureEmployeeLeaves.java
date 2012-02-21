package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;



@Name("futureEmployeeLeaves")
@Scope(ScopeType.PAGE)
public class FutureEmployeeLeaves extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    Collection<EmployeeLeave> futureLeaves = null;

    @In
    private EmployeeHome employeeHome;
    
    @Unwrap
    public Collection<EmployeeLeave> getEmployeeLeaves() {
        if(futureLeaves==null) {
            constructFutureLeavesForEmployee();
        }
        return futureLeaves;
    }
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void constructFutureLeavesForEmployee() {
        this.futureLeaves = getCoreSearching().getEmployeeFutureLeaves2(employeeHome.getInstance(), new Date());
    }
    
   

    
}
