package gr.sch.ira.minoas.seam.components.managers;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

@Name("employeeTeachingService")
public class EmployeeTeachingService extends BaseSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In
    private EmployeeHome employeeHome;

    public EmployeeTeachingService() {
    }

    @Unwrap
    public Integer calculate() {
        try {
            EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
            if (employeeInfo != null)
                return employeeInfo.getSumOfTeachingExperience() + employeeInfo.getTotalWorkService();
            else
                return 0;
        } catch (NullPointerException npe) {
            return 0;
        }

    }

}
