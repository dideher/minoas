package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.employee.EmployeeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeCriteria")
@Scope(ScopeType.PAGE)
public class EmployeeCriteria {
	
	private EmployeeType type;

	/**
	 * @return the type
	 */
	public EmployeeType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EmployeeType type) {
		this.type = type;
	}
	private boolean onlyActive = true;
	/**
	 * 
	 */
	public EmployeeCriteria() {
		super();
	}
	/**
	 * @return the onlyActive
	 */
	public boolean isOnlyActive() {
		return onlyActive;
	}
	/**
	 * @param onlyActive the onlyActive to set
	 */
	public void setOnlyActive(boolean onlyActive) {
		this.onlyActive = onlyActive;
	}

}
