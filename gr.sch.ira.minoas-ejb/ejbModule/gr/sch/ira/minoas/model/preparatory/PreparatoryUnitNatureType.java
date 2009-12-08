package gr.sch.ira.minoas.model.preparatory;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum PreparatoryUnitNatureType {
	LANGUAGE_CENTER("LANGUAGE_CENTER_KEY"),
	SECONDARY_EDUCATION("SECONDARY_EDUCATION_KEY");

	private String key;

	/**
	 * @param key
	 */
	PreparatoryUnitNatureType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
