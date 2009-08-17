/**
 * 
 */
package gr.sch.ira.minoas.model.employement;


/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public enum LeaveType {
	/**
	 * Κύησης
	 */
	LABOUR_LEAVE("LABOUR_LEAVE_TYPE", (30*2)),
	/**
	 * Λοχείας
	 */
	PUERPERIUM_LEAVE("PUERPERIUM_LEAVE_TYPE", 30*3),
	/**
	 * Εκπαιδευτική
	 */
	LEARNING_LEAVE("LEARNING_LEAVE_TYPE", 30*12),
	/**
	 * 9-μήνη
	 */
	NINE_MONTHS_LEAVE("NINE_MONTHS_LEAVE_TYPE", 30*9),
	/**
	 * 3-μήνη
	 */
	THREE_MONTHS_LEAVE("THREE_MONTHS_LEAVE_TYPE", 30*3),
	/**
	 * Ασθενείας
	 */
	MEDICAL_LEAVE("MEDICAL_LEAVE_TYPE", 0),
	/**
	 * Υιοθεσίας
	 */
	ADOPTION_LEAVE("ADOPTION_LEAVE_TYPE", 30*3),
	/**
	 * Άνευ Αποδοχών
	 */
	UNPAID_LEAVE("UNPAID_LEAVE_TYPE", (30*12));
	
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