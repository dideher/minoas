package gr.sch.ira.minoas.seam.components.criteria;

public enum SpecializationSearchType {

	SPECIALIZATION("SPECIALIZATION_SEARCH_TYPE"),
	SPECIALIZATION_GROUP("SPECIALIZATION_GROUP_SEARCH_TYPE");

	private String key;

	private SpecializationSearchType(String key) {
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
