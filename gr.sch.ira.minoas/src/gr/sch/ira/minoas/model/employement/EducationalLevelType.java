/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * 
 */
public enum EducationalLevelType {
	/**
	 * Πανεπιστημιακής Εκπαίδευσης
	 */
	UNIVERSITY_EDUCATION_LEVEL("UNIVERSITY_EDUCATION_LEVEL_KEY"),
	/**
	 * Τεχνολογικής Εκπαίδευσης
	 */
	TECHNOLOGIGAL_EDUCATION_LEVEL("TECHNOLOGIGAL_EDUCATION_LEVEL_KEY"),
	/**
	 * Δευτεροβάθμιας Εκπαίδευσης
	 */
	SECONDARY_EDUCATION_LEVEL("SECONDARY_EDUCATION_LEVEL_KEY");

	private String key;

	private EducationalLevelType(String key) {
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
