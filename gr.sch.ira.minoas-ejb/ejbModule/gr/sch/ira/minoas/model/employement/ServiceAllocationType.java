/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

/**
 * @author slavikos
 *
 */
public enum ServiceAllocationType {
	
	SCHOOL_HEADMASTER("SCHOOL_HEADMASTER_ALLOCATION_SERVICE_TYPE"),
	SCHOOL_SUBHEADMASTER("SCHOOL_SUBHEADMASTER_ALLOCATION_SERVICE_TYPE"),
	GRAND_HEADMASTER("GRAND_HEADMASTER_ALLOCATION_SERVICE_TYPE"),
	OFFICE_CHIEF("OFFICE_CHIEF_ALLOCATION_SERVICE_TYPE");
	
	private ServiceAllocationType(String key) {
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
