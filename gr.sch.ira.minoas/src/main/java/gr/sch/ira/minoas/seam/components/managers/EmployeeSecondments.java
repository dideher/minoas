package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("employeeSecondments")
@Scope(ScopeType.PAGE)
public class EmployeeSecondments extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    private Collection<Secondment> employeeSecondments = null;

    @Unwrap
    public Collection<Secondment> getEmployeeLeaves() {
        if (employeeSecondments == null) {
            constructEmployeeSecondmentHistory();
        }
        return this.employeeSecondments;
    }

    @Observer(value = { "secondmentCreated", "secondmentDeleted", "secondmentModified" })
    public void constructEmployeeSecondmentHistory() {
        this.employeeSecondments = getCoreSearching().getAllEmployeeSecondments(employeeHome.getInstance());
    }

}
