/**
 * 
 */
package gr.sch.ira.minoas.model.employee;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 */
public enum RankType {
	RANK_ST("ST_RANK_TYPE"),
	RANK_E("E_RANK_TYPE"),
	RANK_D("D_RANK_TYPE"),
	RANK_C("C_RANK_TYPE"),
	RANK_B("B_RANK_TYPE"),
	RANK_A("A_RANK_TYPE");
	
	private String key;

	private RankType(String key) {
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
