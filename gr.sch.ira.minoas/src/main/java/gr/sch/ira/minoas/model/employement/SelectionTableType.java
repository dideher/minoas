/**
 * 
 */
package gr.sch.ira.minoas.model.employement;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
public enum SelectionTableType {
	ENIAIOS("ENIAIOS_TABLE_TYPE"),
	EIDIKIS_AGOGIS("EIDIKIS_AGOGIS_TABLE_TYPE");

	private String key;

	private SelectionTableType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}
