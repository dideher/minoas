/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
public enum MaritalStatusType {
	UNKNOWN("UKNOWN_MARITAL_STATUS_TYPE"),
	SINGLE("SINGLE_MARITAL_STATUS_TYPE"),
	MARRIED("MARRIED_MARITAL_STATUS_TYPE"),
	DIVORCED("DIVORCED_MARITAL_STATUS_TYPE"),
	WIDOWER("WIDOWER_MARITAL_STATUS_TYPE");

	private String key;

	private MaritalStatusType(String key) {
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
