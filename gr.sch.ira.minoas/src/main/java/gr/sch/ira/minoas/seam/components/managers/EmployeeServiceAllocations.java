package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("employeeServiceAllocations")
@Scope(ScopeType.PAGE)
public class EmployeeServiceAllocations extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    private Collection<ServiceAllocation> employeeServiceAllocations = null;

    @Unwrap
    public Collection<ServiceAllocation> getEmployeeDisposals() {
        if (employeeServiceAllocations == null) {
        	constructEmployeeServiceAllocationHistory();
        }
        return this.employeeServiceAllocations;
    }

    @Observer(value = { "serviceAllocationCreated", "serviceAllocationDeleted", "serviceAllocationModified" })
    public void constructEmployeeServiceAllocationHistory() {
        this.employeeServiceAllocations = getCoreSearching().getEmployeeServiceAllocation(getEntityManager(),employeeHome.getInstance());
    }

}
