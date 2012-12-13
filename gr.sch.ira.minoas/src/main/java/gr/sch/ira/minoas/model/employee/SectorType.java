/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 */
public enum SectorType {
	PUBLIC_SECTOR("PUBLIC_SECTOR_TYPE"),
	PRIVATE_SECTOR("PRIVATE_SECTOR_TYPE");
	
	private String key;

	private SectorType(String key) {
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
