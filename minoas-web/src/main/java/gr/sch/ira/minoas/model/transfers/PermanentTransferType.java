
package gr.sch.ira.minoas.model.transfers;

public enum PermanentTransferType {
	
	WITHIN_PYSDE("WITHIN_PYSDE_PERMANENT_TRANSFER_TYPE"),
	FROM_OTHER_PYSDE("FROM_OTHER_PYSDE_PERMANENT_TRANSFER_TYPE"),
	TO_OTHER_PYSDE("TO_OTHER_PYSDE_PERMANENT_TRANSFER_TYPE");
	
	private String key;

	private PermanentTransferType(String key) {
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
