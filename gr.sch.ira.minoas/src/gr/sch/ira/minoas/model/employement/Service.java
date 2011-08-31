

package gr.sch.ira.minoas.model.employement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 */
public class Service extends BaseIDDeleteAwareModel {
    @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean active;
    
    @Basic
    @Column(name = "IS_AUTOCANCELED", nullable = true)
    private Boolean autoCanceled;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "AFFECTED_EMPLOYMENT_ID", nullable = true)
    private Employment affectedEmployment;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "AFFECTED_SECONDMENT_ID", nullable = true)
    private Secondment affectedSecondment;
    
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
    @Column(name = "SERVICE_TYPE")
    private ServiceAllocationType serviceType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_UNIT_ID", nullable = true)
    private Unit serviceUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SOURCE_UNIT_ID", nullable = true)
    private Unit sourceUnit;

    @Basic
    @Column(name = "WORKING_HOURS", nullable = true)
    private Integer workingHours;

    
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="services")
    private Collection<TeachingHourCDR> serviceCDRs = new ArrayList<TeachingHourCDR>();
}
