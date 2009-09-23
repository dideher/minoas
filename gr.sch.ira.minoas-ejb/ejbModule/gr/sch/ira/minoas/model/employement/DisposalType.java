package gr.sch.ira.minoas.model.employement;

public enum DisposalType {
	PARTIAL("PARTIAL_DISPOSAL_TYPE"),
	TOTAL("TOTAL_DISPOSAL_TYPE");
	
	private String key; 
	
	private DisposalType(String key) {
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
