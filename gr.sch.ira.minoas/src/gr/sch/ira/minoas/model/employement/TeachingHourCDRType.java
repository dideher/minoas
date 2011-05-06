package gr.sch.ira.minoas.model.employement;

public enum TeachingHourCDRType {
    EMPLOYMENT("EMPLOYMENT_CDR_TYPE_KEY"),
    SECONDMENT("SECONDMENT_CDR_TYPE_KEY"),
    DISPOSAL("DISPOSAL_CDR_TYPE_KEY"),
    SERVICE_ALLOCATION("SERVICE_ALLOCATION_CDR_TYPE_KEY"),
    LEAVE("LEAVE_CDR_TYPE_KEY"),
    GENERAL("GENERAL_CDR_TYPE_KEY");
    
    private String key;

    private TeachingHourCDRType(String key) {
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
