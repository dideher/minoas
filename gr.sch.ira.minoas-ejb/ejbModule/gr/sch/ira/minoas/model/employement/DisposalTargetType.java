
package gr.sch.ira.minoas.model.employement;

public enum DisposalTargetType {
	TO_SCHOOL("TO_SCHOOL_DISPOSAL_TARGET_TYPE"),
	TO_OFFICE("TO_OFFICE_DISPOSAL_TARGET_TYPE");
	
	
	/**
	 * @param key
	 */
	private DisposalTargetType(String key) {
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
