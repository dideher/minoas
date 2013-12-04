package gr.sch.ira.minoas.seam.components.managers;

import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

@Name("employeeDeputyEmployments")
@Scope(ScopeType.PAGE)
public class EmployeeDeputyEmployments extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    private Collection<Employment> employeeEmployments = null;

    @Unwrap
    public Collection<Employment> getEmployeeLeaves() {
        if (employeeEmployments == null) {
            constructEmployeeEmploymentHistory();
        }
        return this.employeeEmployments;
    }

    @Observer(value = { "employmentCreated", "employmentDeleted", "employmentModified" })
    public void constructEmployeeEmploymentHistory() {
        this.employeeEmployments = getCoreSearching().getAllEmployeeEmploymentsOfType(employeeHome.getInstance(), EmploymentType.DEPUTY);
    }

}
