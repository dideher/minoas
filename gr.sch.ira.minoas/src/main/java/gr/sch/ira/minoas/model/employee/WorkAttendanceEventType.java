package gr.sch.ira.minoas.model.employee;

public enum WorkAttendanceEventType {
	ENTRY("ENTRY_WORK_ATTENDANCE_TYPE"),
	EXIT("EXIT_WORK_ATTENDANCE_TYPE");

	private String key;

	private WorkAttendanceEventType(String key) {
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
