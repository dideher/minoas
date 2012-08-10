package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.SpecializationGroupHome;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "specializationGroupManagement")
@Scope(ScopeType.PAGE)
public class SpecializationGroupManagement extends BaseDatabaseAwareSeamComponent {
    

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(required = true, create=true)
    private SpecializationGroupHome specializationGroupHome;
    
    @DataModel(value="availableSpecializationGroups")
    private List<SpecializationGroup> availableSpecializationGroups;

    @Factory(value="availableSpecializationGroups")
    public void initializeAvailableSpecializationGroups() {
        EntityManager em = getEntityManager();
        this.availableSpecializationGroups = new ArrayList<SpecializationGroup>(getCoreSearching().getSpecializationGroups(getCoreSearching().getActiveSchoolYear(em), em));
    }
    
//    @Transactional
//    @RaiseEvent("leaveCreated")
//    public String addEmployeeLeaveAction() {
//        if (employeeHome.isManaged() && !(employeeLeaveHome.isManaged())) {
//            Employee employee = getEntityManager().merge(employeeHome.getInstance());
//            EmployeeLeave newLeave = employeeLeaveHome.getInstance();
//            newLeave.setEmployee(employee);
//            newLeave.setInsertedBy(getPrincipal());
//            newLeave.setInsertedOn(new Date());
//            newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
//            /* check if the employee has a current regular employment */
//            Employment employment = employee.getCurrentEmployment();
//            if (employment != null && employment.getType() == EmploymentType.REGULAR) {
//                newLeave.setRegularSchool(employment.getSchool());
//            }
//
//            if (validateLeave(newLeave, true)) {
//                newLeave.setNumberOfDays(computeLeaveDuration(newLeave.getEstablished(), newLeave.getDueTo()));
//                employeeLeaveHome.persist();
//                setLeaveDurarionInDaysHelper(0);
//                setLeaveDurationInDaysWithoutWeekends(0);
//                getEntityManager().flush();
//                info("leave #0 for employee #1 has been created", newLeave, employee);
//                return ACTION_OUTCOME_SUCCESS;
//            } else {
//                return ACTION_OUTCOME_FAILURE;
//            }
//        } else {
//            facesMessages.add(Severity.ERROR, "employee home #0 is not managed.", employeeHome);
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }
//    
//    @Transactional
//    @RaiseEvent("leaveCreated")
//    public String triggerLeaveAddedAction() {
//        info("triggered!");
//        return ACTION_OUTCOME_SUCCESS;
//    }
//
    @Transactional
    public String deleteSpecializagtionGroupAction() {
        if (specializationGroupHome.isManaged()) {
            SpecializationGroup specializationGroup = specializationGroupHome.getInstance();
            info("deleting specialization group #0", specializationGroup);
            specializationGroupHome.remove();
            getEntityManager().flush();
            initializeAvailableSpecializationGroups();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages
                    .add(Severity.ERROR, "specialization home #0 is not managed.", specializationGroupHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

    @Transactional
    public String modifySpecializationGroupAction() {

        if (specializationGroupHome.isManaged()) {
            SpecializationGroup specializationGroup = specializationGroupHome.getInstance();
            info("updating specialization group #0", specializationGroup);
            specializationGroupHome.update();
            getEntityManager().flush();
            initializeAvailableSpecializationGroups();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages
                    .add(Severity.ERROR, "specialization home #0 is not managed.", specializationGroupHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
    

    /* this method is called when the user clicks the "add new specialization group" */
    public void prepeareNewSpecializationGroup() {
//        employeeLeaveHome.clearInstance();
//        EmployeeLeave leave = employeeLeaveHome.getInstance();
//        leave.setEstablished(new Date());
//        leave.setDueTo(new Date());
//        leave.setEmployee(employeeHome.getInstance());
    }

    /**
     * @return the specializationGroupHome
     */
    public SpecializationGroupHome getSpecializationGroupHome() {
        return specializationGroupHome;
    }

    /**
     * @param specializationGroupHome the specializationGroupHome to set
     */
    public void setSpecializationGroupHome(SpecializationGroupHome specializationGroupHome) {
        this.specializationGroupHome = specializationGroupHome;
    }

    /**
     * @return the availableSpecializationGroups
     */
    public List<SpecializationGroup> getAvailableSpecializationGroups() {
        return availableSpecializationGroups;
    }

    /**
     * @param availableSpecializationGroups the availableSpecializationGroups to set
     */
    public void setAvailableSpecializationGroups(List<SpecializationGroup> availableSpecializationGroups) {
        this.availableSpecializationGroups = availableSpecializationGroups;
    }



}
