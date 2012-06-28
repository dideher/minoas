
package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "TEACHING_HOUR_CDR")
@Cache(usage=CacheConcurrencyStrategy.NONE)
public class TeachingHourCDR extends BaseIDModel {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
    private SchoolYear schoolYear;
    
    @ManyToOne
    @JoinColumn(name = "UNIT_ID", nullable = false)
    private Unit unit;
    
    @Basic
    @Column(name="HOURS", nullable=false)
    private Integer hours;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Employee employee;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="SPECIALIZATION_ID", nullable=true)
    private Specialization specialization;
    
    @Enumerated(EnumType.STRING)
    @Column(name="CDR_TYPE", nullable=false)
    private TeachingHourCDRType cdrType;
    
    /**
     * Is set to 'true' then this CDR is 'logistic'. A logistic CDR is a special CDR (most of the time 
     * with negative hours) generated to substract teaching hours from a unit. It is almost always the
     * result of some other CDR. For example, assume that there is an employee with a regular employment 
     * in unit A that has been moved (with a secondment) to unit B. There will be 3 CDRs. One for the 
     * regular employment and one for the secondment and one logistic CDR that will substract hours from 
     * unit A due to the CDR that adds hours to unit B (due to the secodnament)
     * Comment for <code>logisticCDR</code>
     */
    @Basic
    @Column(name="IS_LOGISTIC", nullable=true)
    private Boolean logisticCDR = Boolean.FALSE;
    
    @Basic
    @Column(name="COMMENT", length=1024)
    private String comment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Employment employment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SECONDMENT_ID", nullable=true)
    private Secondment secondment;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SERVICE_ALLOCATION_ID", nullable=true)
    private ServiceAllocation serviceAllocation;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMPLOYEE_LEAVE_ID", nullable=true)
    private EmployeeLeave leave;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DISPOSAL_ID", nullable=true)
    private Disposal disposal;

    /**
     * @return the schoolYear
     */
    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

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
     * @return the cdrType
     */
    public TeachingHourCDRType getCdrType() {
        return cdrType;
    }

    /**
     * @param cdrType the cdrType to set
     */
    public void setCdrType(TeachingHourCDRType cdrType) {
        this.cdrType = cdrType;
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
     * @return the leave
     */
    public EmployeeLeave getLeave() {
        return leave;
    }

    /**
     * @param leave the leave to set
     */
    public void setLeave(EmployeeLeave leave) {
        this.leave = leave;
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
     * @return the unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TeachingHourCDR [schoolYear=");
        builder.append(schoolYear);
        builder.append(", unit=");
        builder.append(unit);
        builder.append(", hours=");
        builder.append(hours);
        builder.append(", employee=");
        builder.append(employee);
        builder.append(", cdrType=");
        builder.append(cdrType);
        builder.append(", comment=");
        builder.append(comment);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the specialization
     */
    public Specialization getSpecialization() {
        return specialization;
    }

    /**
     * @param specialization the specialization to set
     */
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    /**
     * @return the logisticCDR
     */
    public Boolean getLogisticCDR() {
        return logisticCDR;
    }

    /**
     * @param logisticCDR the logisticCDR to set
     */
    public void setLogisticCDR(Boolean logisticCDR) {
        this.logisticCDR = logisticCDR;
    }
}
