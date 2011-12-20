

package gr.sch.ira.minoas.model.employement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 */
@Entity
@Table(name = "EMPLOYEE_SERVICE_ALLOCATION")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Service extends BaseIDDeleteAwareModel {
    
   /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
     @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean active;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "SERVICE_TYPE_MAJOR")
    private ServiceType majorType;
    
    
    
    @Basic
    @Column(name = "IS_AUTOCANCELED", nullable = true)
    private Boolean autoCanceled;

//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "AFFECTED_EMPLOYMENT_ID", nullable = true)
//    private Employment affectedEmployment;
//    
//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "AFFECTED_SECONDMENT_ID", nullable = true)
//    private Secondment affectedSecondment;
    
    @Basic
    @Column(name = "COMMENT", nullable = true, length = 255)
    private String comment;

    @Basic
    @Column(name = "DUE_TO", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dueTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @Basic
    @Column(name = "ESTABLISHED", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date established;

    @Basic
    @Column(name = "PYSDE_ORDER", nullable = true, length = 25)
    private String pysdeOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "SERVICE_TYPE_MINOR")
    private ServiceAllocationType minorType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_UNIT_ID", nullable = true)
    private Unit serviceUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SOURCE_UNIT_ID", nullable = true)
    private Unit sourceUnit;

    @Basic
    @Column(name = "WORKING_HOURS", nullable = true)
    private Integer workingHours;
    
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="service")
    private Collection<TeachingHourCDR> serviceCDRs = new ArrayList<TeachingHourCDR>();

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the majorType
     */
    public ServiceType getMajorType() {
        return majorType;
    }

    /**
     * @param majorType the majorType to set
     */
    public void setMajorType(ServiceType majorType) {
        this.majorType = majorType;
    }

    /**
     * @return the autoCanceled
     */
    public Boolean getAutoCanceled() {
        return autoCanceled;
    }

    /**
     * @param autoCanceled the autoCanceled to set
     */
    public void setAutoCanceled(Boolean autoCanceled) {
        this.autoCanceled = autoCanceled;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the dueTo
     */
    public Date getDueTo() {
        return dueTo;
    }

    /**
     * @param dueTo the dueTo to set
     */
    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the established
     */
    public Date getEstablished() {
        return established;
    }

    /**
     * @param established the established to set
     */
    public void setEstablished(Date established) {
        this.established = established;
    }

    /**
     * @return the pysdeOrder
     */
    public String getPysdeOrder() {
        return pysdeOrder;
    }

    /**
     * @param pysdeOrder the pysdeOrder to set
     */
    public void setPysdeOrder(String pysdeOrder) {
        this.pysdeOrder = pysdeOrder;
    }

    /**
     * @return the minorType
     */
    public ServiceAllocationType getMinorType() {
        return minorType;
    }

    /**
     * @param minorType the minorType to set
     */
    public void setMinorType(ServiceAllocationType minorType) {
        this.minorType = minorType;
    }

    /**
     * @return the serviceUnit
     */
    public Unit getServiceUnit() {
        return serviceUnit;
    }

    /**
     * @param serviceUnit the serviceUnit to set
     */
    public void setServiceUnit(Unit serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    /**
     * @return the sourceUnit
     */
    public Unit getSourceUnit() {
        return sourceUnit;
    }

    /**
     * @param sourceUnit the sourceUnit to set
     */
    public void setSourceUnit(Unit sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    /**
     * @return the workingHours
     */
    public Integer getWorkingHours() {
        return workingHours;
    }

    /**
     * @param workingHours the workingHours to set
     */
    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    /**
     * @return the serviceCDRs
     */
    public Collection<TeachingHourCDR> getServiceCDRs() {
        return serviceCDRs;
    }

    /**
     * @param serviceCDRs the serviceCDRs to set
     */
    public void setServiceCDRs(Collection<TeachingHourCDR> serviceCDRs) {
        this.serviceCDRs = serviceCDRs;
    }
}
