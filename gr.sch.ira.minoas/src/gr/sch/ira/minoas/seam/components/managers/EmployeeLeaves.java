package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;



@Name("employeeLeaves")
@Scope(ScopeType.PAGE)
public class EmployeeLeaves extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;
    
    private Collection<Leave> employeeLeaves = null;
    
    @Unwrap
    public Collection<Leave> getEmployeeLeaves() {
        if(employeeLeaves == null) {
            info("NULL !!!!");
            constructEmployeeLeaveHistory(); 
        }
        return this.employeeLeaves;
    }
    
    @Observer(value= { "leaveCreated", "leaveDeleted","leaveModified" })
    public void constructEmployeeLeaveHistory() {
        info("observer !");
        info("searcing !!!!!! ");
        this.employeeLeaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance());
    }
    
   

    
}
