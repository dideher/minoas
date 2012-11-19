package gr.sch.ira.minoas.model.employement;

import java.util.ArrayList;
import java.util.Collection;

import gr.sch.ira.minoas.model.BaseIDDeleteAwareModel;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * This class represents a special assigment. Every assigment is binded to a concrete
 * {@link Employee} in the system and always references a {@link SchoolYear}.
 * <br />
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "SPECIAL_ASSIGMENT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SpecialAssigment extends BaseIDDeleteAwareModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An employment may or may not be active.
	 */
	@Basic
	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active = Boolean.TRUE;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;


	@Basic
	@Column(name = "FINAL_WORKING_HOURS", nullable = false)
	private Integer finalWorkingHours;

	@OneToOne
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Unit unit;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "SCHOOL_YEAR_ID", nullable = false)
	private SchoolYear schoolYear;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SPECIALIZATION_GROUP_ID", nullable = false, updatable = false)
	private SpecializationGroup specializationGroup;
	
	@Basic
	@Column(name="COMMENT", nullable=true, length=512)
	private String comment;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="specialAssigment")
    private Collection<TeachingHourCDR> specialAssigmentCDRs = new ArrayList<TeachingHourCDR>();

	/**
	 * 
	 */
	public SpecialAssigment() {
		super();
		// TODO Auto-generated constructor stub
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
     * @return the finalWorkingHours
     */
    public Integer getFinalWorkingHours() {
        return finalWorkingHours;
    }

    /**
     * @param finalWorkingHours the finalWorkingHours to set
     */
    public void setFinalWorkingHours(Integer finalWorkingHours) {
        this.finalWorkingHours = finalWorkingHours;
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
     * @return the specializationGroup
     */
    public SpecializationGroup getSpecializationGroup() {
        return specializationGroup;
    }

    /**
     * @param specializationGroup the specializationGroup to set
     */
    public void setSpecializationGroup(SpecializationGroup specializationGroup) {
        this.specializationGroup = specializationGroup;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SpecialAssigment [finalWorkingHours=");
        builder.append(finalWorkingHours);
        builder.append(", unit=");
        builder.append(unit);
        builder.append(", schoolYear=");
        builder.append(schoolYear);
        builder.append(", specializationGroup=");
        builder.append(specializationGroup);
        builder.append(", comment=");
        builder.append(comment);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the specialAssigmentCDRs
     */
    public Collection<TeachingHourCDR> getSpecialAssigmentCDRs() {
        return specialAssigmentCDRs;
    }

    /**
     * @param specialAssigmentCDRs the specialAssigmentCDRs to set
     */
    public void setSpecialAssigmentCDRs(Collection<TeachingHourCDR> specialAssigmentCDRs) {
        this.specialAssigmentCDRs = specialAssigmentCDRs;
    }



}
