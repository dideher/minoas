package gr.sch.ira.minoas.seam.components.actions;

import java.util.Collection;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.criteria.EmployeeCriteria;

@Name(value = "searchEmployeeAction")
@Scope(ScopeType.PAGE)
public class SearchEmployeeAction extends BaseDatabaseAwareSeamComponent {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(scope = ScopeType.PAGE)
    private EmployeeCriteria employeeCriteria;

    @DataModel
    private Collection<Employee> foundEmployees;

    public String searchEmployeesAction() {
        long started = System.currentTimeMillis();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT e FROM Employee e WHERE e.active IS :active ");
        boolean shouldPerformQuery = false;
        if (isNonEmpty(employeeCriteria.getLastName())) {
            sql.append("AND e.lastName LIKE LOWER(CONCAT('%', CONCAT(:lastName,'%'))) ");
            shouldPerformQuery = true;
        }
        if (isNonEmpty(employeeCriteria.getFirstName())) {
            sql.append("AND e.firstName LIKE LOWER(CONCAT('%', CONCAT(:firstName,'%'))) ");
            shouldPerformQuery = true;
        }
        if (isNonEmpty(employeeCriteria.getSpecialization())) {
            sql.append("AND e.lastSpecialization = :specialization ");
            shouldPerformQuery = true;
        }
        if (isNonEmpty(employeeCriteria.getType())) {
            sql.append("AND e.type = :employeeType ");
        }

        if (isNonEmpty(employeeCriteria.getVatNumber())) {
            sql.append("AND e.vatNumber LIKE CONCAT('%', CONCAT(:vatNumber,'%')) ");
            shouldPerformQuery = true;
        }

        if (isNonEmpty(employeeCriteria.getRegistryNumber())) {
            sql.append("AND e.regularEmployeeInfo.registryID LIKE CONCAT('%', CONCAT(:registryID,'%'))");
            shouldPerformQuery = true;
        }

        if (shouldPerformQuery) {

            Query q = getEntityManager().createQuery(sql.toString());
            q.setParameter("active", employeeCriteria.isOnlyActive());
            if (isNonEmpty(employeeCriteria.getLastName()))
                q.setParameter("lastName", employeeCriteria.getLastName());
            if (isNonEmpty(employeeCriteria.getFirstName()))
                q.setParameter("firstName", employeeCriteria.getFirstName());
            if (isNonEmpty(employeeCriteria.getSpecialization()))
                q.setParameter("specialization", employeeCriteria.getSpecialization());
            if (isNonEmpty(employeeCriteria.getType()))
                q.setParameter("employeeType", employeeCriteria.getType());
            if (isNonEmpty(employeeCriteria.getVatNumber())) {
                q.setParameter("vatNumber", employeeCriteria.getVatNumber());
            }
            if (isNonEmpty(employeeCriteria.getRegistryNumber())) {
                q.setParameter("registryID", employeeCriteria.getRegistryNumber());
            }
            foundEmployees = q.getResultList();
            info("searchEmployeeAction: found '#0' employees in totally '#1' ms", foundEmployees.size(),
                    (System.currentTimeMillis() - started));
            return ACTION_OUTCOME_SUCCESS;
        } else
            return ACTION_OUTCOME_FAILURE;

    }
}
