

package gr.sch.ira.minoas.seam.components.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

import gr.sch.ira.minoas.model.employement.WorkReduction;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

@Name(value = "employeeWorkReductionManagement")
@Scope(ScopeType.PAGE)
public class EmployeeWorkReductionManagement extends BaseDatabaseAwareSeamComponent {
    
    @In(required=false)
    private EmployeeHome employeeHome;
    
    @DataModel(value="employeesWorkReductions")
    private Collection<WorkReduction> employeesWorkReductions;
    
    @Factory(value="employeesWorkReductions")
    public void fetchEmployeeWorkReductions() {
       
        
        
        WorkReduction w = new WorkReduction();
        w.setComment("reduction 1");
        w.setHours(12);
        w.setEmployee(employeeHome.getInstance());
        w.setActive(Boolean.TRUE);
        getEntityManager().persist(w);
        getEntityManager().flush();
       
        
        employeesWorkReductions = getCoreSearching().getEmployeeWorkReductions(getEntityManager(), employeeHome.getInstance());
        
        
        
    }

}
