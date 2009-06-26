package gr.sch.ira.minoas.model.employement;

public enum SecondmentType {
	FULL_TO_SCHOOL("FULL_TO_SCHOOL_SECONDMENT_TYPE"),
	OTHER_PYSDE("OTHER_PYSDE_SECONDMENT_TYPE"),
	FULL_TO_OFFICE("FULL_TO_OFFICE_SECONDMENT_TYPE");
	
	
	/**
	 * @param key
	 */
	private SecondmentType(String key) {
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
