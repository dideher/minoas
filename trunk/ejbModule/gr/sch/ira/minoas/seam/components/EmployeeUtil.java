/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.employee.DeputyEmployee;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.HourlyPaidEmployee;
import gr.sch.ira.minoas.model.employee.RegularEmployee;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeUtil")
@Scope(ScopeType.EVENT)
public class EmployeeUtil extends BaseSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getRegistryID(Employee employee) {
		if (employee instanceof RegularEmployee) {
			return ((RegularEmployee) employee).getRegistryID();
		} else
			return null;
	}

	public boolean isRegular(Employee employee) {
		return employee instanceof RegularEmployee;
	}

	public boolean isDeputy(Employee employee) {
		return employee instanceof DeputyEmployee;
	}

	public boolean isHourlyPaid(Employee employee) {
		return employee instanceof HourlyPaidEmployee;
	}

	public String prettyFormat(Employee employee) {
		if (employee != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(employee.getLastName());
			sb.append(" ");
			sb.append(employee.getFirstName());
			sb.append(" του ");
			sb.append(employee.getFatherName());
			if (employee.getCurrentEmployment() != null) {
				sb.append(" (");
				sb.append(employee.getCurrentEmployment().getSpecialization().getId());
				sb.append(" )");
			}
			return sb.toString();
		} else
			return null;
	}

	public String prettyFormatNoSpecialization(Employee employee) {
		if (employee != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(employee.getLastName());
			sb.append(" ");
			sb.append(employee.getFirstName());
			sb.append(" του ");
			sb.append(employee.getFatherName());
			return sb.toString();
		} else
			return null;

	}

	public boolean hasSecondment(Employee employee) {
		if (employee != null && employee.getCurrentEmployment() != null) {
			return employee.getCurrentEmployment().getSecondment() != null;
		} else
			return false;
	}
}
