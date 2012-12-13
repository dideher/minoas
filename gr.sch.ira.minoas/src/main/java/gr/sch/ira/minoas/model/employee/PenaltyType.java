/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 */
public enum PenaltyType {
	PROSECUTION("PROSECUTION_PENALTY_TYPE"),
	DISEASE("DISEASE_PENALTY_TYPE"),
	UNAUTHORIZED_ABSENCE("UNAUTHORIZED_ABSENCE_PENALTY_TYPE"),
	TEMPORARY_TERMINATION("TEMPORARY_TERMINATION_PENALTY_TYPE"),
	DOWNGRADE("DOWNGRADE_PENALTY_TYPE");
	
	private String key;

	private PenaltyType(String key) {
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
