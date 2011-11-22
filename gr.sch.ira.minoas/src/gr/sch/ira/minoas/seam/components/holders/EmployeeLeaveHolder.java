

package gr.sch.ira.minoas.seam.components.holders;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.LeaveType;

import java.io.Serializable;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("employeeLeaveHolder")
@Scope(ScopeType.PAGE)
public class EmployeeLeaveHolder implements Serializable {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String comment;

    private Date dueTo;

    
    private Date established;

    private LeaveType leaveType;

    private School regularSchool;
    
    private EmployeeLeaveType employeeLeaveType;

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
     * @return the leaveType
     */
    public LeaveType getLeaveType() {
        return leaveType;
    }

    /**
     * @param leaveType the leaveType to set
     */
    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    /**
     * @return the regularSchool
     */
    public School getRegularSchool() {
        return regularSchool;
    }

    /**
     * @param regularSchool the regularSchool to set
     */
    public void setRegularSchool(School regularSchool) {
        this.regularSchool = regularSchool;
    }

    /**
     * @return the employeeLeaveType
     */
    public EmployeeLeaveType getEmployeeLeaveType() {
        return employeeLeaveType;
    }

    /**
     * @param employeeLeaveType the employeeLeaveType to set
     */
    public void setEmployeeLeaveType(EmployeeLeaveType employeeLeaveType) {
        this.employeeLeaveType = employeeLeaveType;
    }
}
