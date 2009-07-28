package gr.sch.ira.minoas.model.core;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum SchoolType {
	
	GYM("GYM_SCHOOL_TYPE_KEY"),
	GEL("GEL_SCHOOL_TYPE_KEY"),
	EPAL("EPAL_SCHOOL_TYPE_KEY"),
	EPAS("EPAS_SCHOOL_TYPE_KEY");
	
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