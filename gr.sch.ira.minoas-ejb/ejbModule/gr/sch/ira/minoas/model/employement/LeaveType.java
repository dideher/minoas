/**
 * 
 */
package gr.sch.ira.minoas.model.employement;


/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public enum LeaveType {
	LABOUR_LEAVE("LABOUR_LEAVE_KEY", (30*9)),
	UNPAID_LEAVE("UNPAID_LEAVE_KEY", (30*12));
	
	private LeaveType(String key, Integer durationInDays) {
		this.key = key;
		this.durationInDays = durationInDays;
	}
	private String key;
	private Integer durationInDays;
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
	/**
	 * @return the durationInDays
	 */
	public Integer getDurationInDays() {
		return durationInDays;
	}
	/**
	 * @param durationInDays the durationInDays to set
	 */
	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}
}
