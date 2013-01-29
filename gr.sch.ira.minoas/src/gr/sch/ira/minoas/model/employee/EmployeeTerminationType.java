/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author slavikos
 *
 */
public enum EmployeeTerminationType {
	DEPUTY("DEPUTY_EMPLOYEE_TYPE"),
	HOURLYPAID("HOURLYPAID_EMPLOYEE_TYPE"),
	REGULAR("REGULAR_EMPLOYEE_TYPE");

	private String key;

	private EmployeeTerminationType(String key) {
		this.key = key;
	}

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
