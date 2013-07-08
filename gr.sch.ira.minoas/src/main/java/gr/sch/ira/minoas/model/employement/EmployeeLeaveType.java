package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employee.EmployeeType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "EMPLOYEE_LEAVE_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeLeaveType extends BaseIDModel {

    /**
     * Comment for <code>serialVersionUID</code> 
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * If not active, then UI should not suggest this leave type
     */
    @Basic
    @Column(name="IS_ACTIVE", nullable=true)
    private Boolean active = Boolean.TRUE;
    
    @Enumerated(EnumType.STRING)
    @Column(name="BASIC_TYPE", nullable=true)
    private EmployeeBasicLeaveType basicType;
    
    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    private String description;
    
    @Basic
    @Column(name="GENERATES_CDRS", nullable=true)
    private Boolean generatesCDRs = Boolean.FALSE;
    
    @Basic
    @Column(name="LEGACY_CODE", nullable=false, unique=true)
    private String legacyCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name="EMPLOYEE_TYPE", nullable=false)
    private EmployeeType suitableForEmployeeType;
   
    public Boolean getActive() {
		return active;
	}

	/**
     * @return the basicType
     */
    public EmployeeBasicLeaveType getBasicType() {
        return basicType;
    }

	/**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the generatesCDRs
     */
    public Boolean getGeneratesCDRs() {
        return generatesCDRs;
    }

    /**
     * @return the legacyCode
     */
    public String getLegacyCode() {
        return legacyCode;
    }

    /**
     * @return the suitableForEmployeeType
     */
    public EmployeeType getSuitableForEmployeeType() {
        return suitableForEmployeeType;
    }

    public void setActive(Boolean active) {
		this.active = active;
	}

    /**
     * @param basicType the basicType to set
     */
    public void setBasicType(EmployeeBasicLeaveType basicType) {
        this.basicType = basicType;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param generatesCDRs the generatesCDRs to set
     */
    public void setGeneratesCDRs(Boolean generatesCDRs) {
        this.generatesCDRs = generatesCDRs;
    }

    /**
     * @param legacyCode the legacyCode to set
     */
    public void setLegacyCode(String legacyCode) {
        this.legacyCode = legacyCode;
    }

    /**
     * @param suitableForEmployeeType the suitableForEmployeeType to set
     */
    public void setSuitableForEmployeeType(EmployeeType suitableForEmployeeType) {
        this.suitableForEmployeeType = suitableForEmployeeType;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmployeeLeaveType [legacyCode=");
        builder.append(legacyCode);
        builder.append(", description=");
        builder.append(description);
        builder.append(", generatesCDRs=");
        builder.append(generatesCDRs);
        builder.append("]");
        return builder.toString();
    }
}
