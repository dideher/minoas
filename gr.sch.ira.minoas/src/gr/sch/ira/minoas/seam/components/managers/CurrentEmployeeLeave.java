package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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
    
    @In(required=true)
    private CoreSearching coreSearching;
    
    @Unwrap
    public Leave getEmployeeLeaves() {
        Employee employee = employeeHome.getInstance();
        info("searching for employee's #0 current leave", employee);
        return coreSearching.getEmployeeActiveLeave(getEntityManager(), employee, new Date());
    }
    
   

    
}
