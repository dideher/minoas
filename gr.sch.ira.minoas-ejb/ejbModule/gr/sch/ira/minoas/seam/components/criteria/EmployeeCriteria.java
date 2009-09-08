package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.Specialization;
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
	
	private String firstName;
	
	private String lastName;
	
	private Specialization specialization;
	
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
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}
	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

}
