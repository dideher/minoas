package gr.sch.ira.minoas.model.employement;

public enum EmployeeBasicLeaveType {
    
    /**
     * Άνευ Αποδοχών
     */
    UNPAID_LEAVE("UNPAID_BASIC_LEAVE_TYPE"), 
    
    /**
     * Κανονική Άδεια
     */
    REGULAR_LEAVE("REGULAR_BASIC_LEAVE_TYPE"),
    
    /**
     * Γενική Άδεια
     */
    GENERIC_LEAVE("GENERIC_BASIC_LEAVE_TYPE");
    
    
    
    
    private String key;

    private EmployeeBasicLeaveType(String key) {
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
