package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
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
    
    Collection<Leave> futureLeaves = null;

    @In
    private EmployeeHome employeeHome;
    
    @In(required=true)
    private CoreSearching coreSearching;
    
    @Unwrap
    public Collection<Leave> getEmployeeLeaves() {
        if(futureLeaves==null) {
            this.futureLeaves = coreSearching.getEmployeeFutureLeaves(employeeHome.getInstance(), new Date());
            info("NULLLLLLLL");
        }
        return futureLeaves;
    }
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void constructFutureLeavesForEmployee() {
        info("Obvserver !!!!!");
        this.futureLeaves = coreSearching.getEmployeeFutureLeaves(employeeHome.getInstance(), new Date());
    }
    
   

    
}
