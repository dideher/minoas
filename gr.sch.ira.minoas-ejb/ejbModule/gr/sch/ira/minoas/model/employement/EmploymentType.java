/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

/**
 * @author slavikos
 *
 */
public enum EmploymentType {
	DEPUTY("DEPUTY_EMPLOYMENT_TYPE"),
	HOURLYBASED("HOURLYBASE_EMPLOYMENT_TYPE"),
	REGULAR("REGULAR_EMPLOYMENT_TYPE");
	
	private String key; 
	
	private EmploymentType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}
