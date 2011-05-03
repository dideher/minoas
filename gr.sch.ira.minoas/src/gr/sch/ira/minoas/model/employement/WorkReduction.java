package gr.sch.ira.minoas.model.employement;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employee.Employee;

@Entity
@Table(name = "WORK_REDUCTION")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WorkReduction extends BaseIDModel {
    
   /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    
    @Basic
    @Column(name = "HOURS", length=512,nullable = true)
    private Integer hours;
    
    @Basic
    @Column(name = "COMMENT", length=512,nullable = true)
    private String comment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMPLOYMENT_ID")
    private Employment employment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SECONDMENT_ID")
    private Secondment secondment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DISPOSAL_ID")
    private Disposal disposal;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SERVICE_ALLOCATION_ID")
    private ServiceAllocation serviceAllocation;
    
    @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean active;
    
    @Basic
    @Column(name = "IS_DELETED", nullable = true)
    private Boolean deleted;
    
    @Basic
    @Column(name = "IS_AUTOCANCELED", nullable = true)
    private Boolean autoCanceled;
    
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

    /**
     * @return the hours
     */
    public Integer getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(Integer hours) {
        this.hours = hours;
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
     * @return the employment
     */
    public Employment getEmployment() {
        return employment;
    }

    /**
     * @param employment the employment to set
     */
    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    /**
     * @return the secondment
     */
    public Secondment getSecondment() {
        return secondment;
    }

    /**
     * @param secondment the secondment to set
     */
    public void setSecondment(Secondment secondment) {
        this.secondment = secondment;
    }

    /**
     * @return the disposal
     */
    public Disposal getDisposal() {
        return disposal;
    }

    /**
     * @param disposal the disposal to set
     */
    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }

    /**
     * @return the serviceAllocation
     */
    public ServiceAllocation getServiceAllocation() {
        return serviceAllocation;
    }

    /**
     * @param serviceAllocation the serviceAllocation to set
     */
    public void setServiceAllocation(ServiceAllocation serviceAllocation) {
        this.serviceAllocation = serviceAllocation;
    }

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
     * @return the deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
    
   
}
