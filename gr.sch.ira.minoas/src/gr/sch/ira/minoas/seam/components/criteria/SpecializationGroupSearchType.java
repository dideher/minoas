package gr.sch.ira.minoas.seam.components.criteria;

public enum SpecializationGroupSearchType {

	MULTIPLE_SPECIALIZATION_GROUPS("MULTIPLE_SPECIALIZATION_GROUPS_SEARCH_TYPE"),
	SINGLE_SPECIALIZATION_GROUP("SINGLE_SPECIALIZATION_GROUP_SEARCH_TYPE");

	private String key;

	private SpecializationGroupSearchType(String key) {
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
