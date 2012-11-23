

package gr.sch.ira.minoas.dbsync;

public class EmployeeSignature {
    private String legacyCode;
    
    private Integer minoasEmployeeId; 
    
    private String signature;

    /**
     * @return the legacyCode
     */
    public String getLegacyCode() {
        return legacyCode;
    }

    /**
     * @param legacyCode the legacyCode to set
     */
    public void setLegacyCode(String legacyCode) {
        this.legacyCode = legacyCode;
    }

    /**
     * @return the minoasEmployeeId
     */
    public Integer getMinoasEmployeeId() {
        return minoasEmployeeId;
    }

    /**
     * @param minoasEmployeeId the minoasEmployeeId to set
     */
    public void setMinoasEmployeeId(Integer minoasEmployeeId) {
        this.minoasEmployeeId = minoasEmployeeId;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmployeeSignature [legacyCode=");
        builder.append(legacyCode);
        builder.append(", minoasEmployeeId=");
        builder.append(minoasEmployeeId);
        builder.append(", signature=");
        builder.append(signature);
        builder.append("]");
        return builder.toString();
    }
}
