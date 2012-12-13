

package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.employee.Employee;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("employeeMergeRequest")
@Scope(ScopeType.CONVERSATION)
public class EmployeeMergeRequest  {
	
	private String sourceEmployeeStr;
	private String targetEmployeeStr;
	
	
	private Employee sourceEmployee;
	private Employee targetEmployee;
	/**
	 * @return the sourceEmployee
	 */
	public Employee getSourceEmployee() {
		return sourceEmployee;
	}
	/**
	 * @param sourceEmployee the sourceEmployee to set
	 */
	public void setSourceEmployee(Employee sourceEmployee) {
		this.sourceEmployee = sourceEmployee;
	}
	/**
	 * @return the targetEmployee
	 */
	public Employee getTargetEmployee() {
		return targetEmployee;
	}
	/**
	 * @param targetEmployee the targetEmployee to set
	 */
	public void setTargetEmployee(Employee targetEmployee) {
		this.targetEmployee = targetEmployee;
	}
	/**
	 * @return the sourceEmployeeStr
	 */
	public String getSourceEmployeeStr() {
		return sourceEmployeeStr;
	}
	/**
	 * @param sourceEmployeeStr the sourceEmployeeStr to set
	 */
	public void setSourceEmployeeStr(String sourceEmployeeStr) {
		this.sourceEmployeeStr = sourceEmployeeStr;
	}
	/**
	 * @return the targetEmployeeStr
	 */
	public String getTargetEmployeeStr() {
		return targetEmployeeStr;
	}
	/**
	 * @param targetEmployeeStr the targetEmployeeStr to set
	 */
	public void setTargetEmployeeStr(String targetEmployeeStr) {
		this.targetEmployeeStr = targetEmployeeStr;
	}
	
}
