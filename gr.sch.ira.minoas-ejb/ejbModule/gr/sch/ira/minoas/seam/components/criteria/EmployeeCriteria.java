package gr.sch.ira.minoas.seam.components.criteria;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeCriteria")
@Scope(ScopeType.CONVERSATION)
public class EmployeeCriteria {

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
