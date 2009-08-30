/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author slavikos
 *
 */
public enum EmployeeType {
	DEPUTY("DEPUTY_EMPLOYEE_TYPE"),
	HOURLYPAID("HOURLYPAID_EMPLOYEE_TYPE"),
	REGULAR("REGULAR_EMPLOYEE_TYPE");
	
	private EmployeeType(String key) {
		this.key = key;
	}
	private String key;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
