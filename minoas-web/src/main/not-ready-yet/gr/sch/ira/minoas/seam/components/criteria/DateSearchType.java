package gr.sch.ira.minoas.seam.components.criteria;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum DateSearchType {
	AFTER_DATE("AFTER_DATE_DATE_SEARCH_TYPE"),
	BEFORE_DATE("BEFORE_DATE_DATE_SEARCH_TYPE"),
	DURING_DATE("DURING_DATE_DATE_SEARCH_TYPE"),
	DURING_DATE_PERIOD("DURING_DATE_PERIOD_DATE_SEARCH_TYPE");

	private String key;

	private DateSearchType(String key) {
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
