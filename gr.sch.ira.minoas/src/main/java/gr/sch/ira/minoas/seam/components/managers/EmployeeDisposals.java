package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("employeeDisposals")
@Scope(ScopeType.PAGE)
public class EmployeeDisposals extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    private Collection<Disposal> employeeDisposals = null;

    @Unwrap
    public Collection<Disposal> getEmployeeDisposals() {
        if (employeeDisposals == null) {
            constructEmployeeDisposalHistory();
        }
        return this.employeeDisposals;
    }

    @Observer(value = { "disposalCreated", "disposalDeleted", "disposalModified" })
    public void constructEmployeeDisposalHistory() {
        this.employeeDisposals = getCoreSearching().getAllEmployeeDisposals(employeeHome.getInstance());
    }

}
