package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

@Name(value = "employeeLeavesManagement")
@Scope(ScopeType.PAGE)
public class EmployeeLeavesManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	@Out(required=false, scope=ScopeType.PAGE)
	private Leave employeeActiveLeave;
	
	/**
	 * Employees current leave history
	 */
	@DataModel(scope=ScopeType.PAGE, value="employeeLeaveHistory")
	private Collection<Leave> employeeLeaveHistory = null;
	
	@Factory(value="employeeActiveLeave")
	public void findEmployeeActiveLeave() {
	    Employee employee = getEmployeeHome().getInstance();
	    setEmployeeActiveLeave(employee.getLeave());
	}

	
	@Factory(value="employeeLeaveHistory",autoCreate=true)
	public void constructLeaveHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing leave history for employee #0", employee);
	    setEmployeeLeaveHistory(coreSearching.getEmployeeLeaveHistory(employee));
	}
	
	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}
	
    /**
     * @return the leaveHistory
     */
    public Collection<Leave> getEmployeeLeaveHistory() {
        return employeeLeaveHistory;
    }

    /**
     * @param leaveHistory the leaveHistory to set
     */
    public void setEmployeeLeaveHistory(Collection<Leave> leaveHistory) {
        this.employeeLeaveHistory = leaveHistory;
    }


    /**
     * @return the employeeActiveLeave
     */
    public Leave getEmployeeActiveLeave() {
        return employeeActiveLeave;
    }


    /**
     * @param employeeActiveLeave the employeeActiveLeave to set
     */
    public void setEmployeeActiveLeave(Leave employeeActiveLeave) {
        this.employeeActiveLeave = employeeActiveLeave;
    }
	
	

}
