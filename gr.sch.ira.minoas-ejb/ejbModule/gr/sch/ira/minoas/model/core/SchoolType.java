package gr.sch.ira.minoas.model.core;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum SchoolType {

	EPAL("EPAL_SCHOOL_TYPE"),
	EPAS("EPAS_SCHOOL_TYPE"),
	GEL("GEL_SCHOOL_TYPE"),
	GYM("GYM_SCHOOL_TYPE");

	private String key;

	/**
	 * @param key
	 */
	private SchoolType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}