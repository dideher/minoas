package gr.sch.ira.minoas.model.employement;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;

@Entity
@Table(name = "EMPLOYEE_LEAVE_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeLeaveType extends BaseIDModel {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    

    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name="BASIC_TYPE", nullable=true)
    private EmployeeBasicLeaveType basicType;
    
    @Basic
    @Column(name="GENERATES_CDRS", nullable=true)
    private Boolean generatesCDRs = Boolean.FALSE;
    
    @Basic
    @Column(name="LEGACY_CODE", nullable=false, unique=true)
    private String legacyCode;
   
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the basicType
     */
    public EmployeeBasicLeaveType getBasicType() {
        return basicType;
    }

    /**
     * @param basicType the basicType to set
     */
    public void setBasicType(EmployeeBasicLeaveType basicType) {
        this.basicType = basicType;
    }

    /**
     * @return the generatesCDRs
     */
    public Boolean getGeneratesCDRs() {
        return generatesCDRs;
    }

    /**
     * @param generatesCDRs the generatesCDRs to set
     */
    public void setGeneratesCDRs(Boolean generatesCDRs) {
        this.generatesCDRs = generatesCDRs;
    }

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
}
