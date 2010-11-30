/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public enum WorkExperienceType {

	ADOPTION_WORKEXPERIENCE("ADOPTION_WORKEXPERIENCE_TYPE"),

	KIOFORIA_WORKEXPERIENCE("KIOFORIA_WORKEXPERIENCE_TYPE");

	private String key;

	private WorkExperienceType(String key) {
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
