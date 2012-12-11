package gr.sch.ira.minoas.model.employement;

public enum SecondmentType {
	EKFE("EKFE_SECONDMENT_TYPE"),
	FOREIS("FOREIS_SECONDMENT_TYPE"),
	FULL_TO_SCHOOL("FULL_TO_SCHOOL_SECONDMENT_TYPE"),
	OTHER_PYSDE("OTHER_PYSDE_SECONDMENT_TYPE"),
	PYSDE_FOREIS("PYSDE_FOREIS_SECONDMENT_TYPE"),
	TO_ABROAD("TO_ABROAD_SECONDMENT_TYPE"),
	TO_OFFICE("TO_OFFICE_SECONDMENT_TYPE"),
	VARIOUS("VARIOUS_SECONDMENT_TYPE");

	private String key;

	/**
	 * @param key
	 */
	private SecondmentType(String key) {
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
