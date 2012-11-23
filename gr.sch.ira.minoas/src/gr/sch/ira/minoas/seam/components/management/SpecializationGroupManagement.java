package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.SpecializationGroupHome;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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
    
    @Transactional
    public String addSpecializationGroupAction() {
        if (!specializationGroupHome.isManaged()) {
            SpecializationGroup newSpecializationGroup = specializationGroupHome.getInstance();
            /* we are allowed to have a specialization group with no 
             * specializations inside only and only if the 
             * isVirtualSpecialization flag is set
             */
            if(!newSpecializationGroup.getIsVirtualGroup()) {
                /* is virtual group is not specified,
                 * check if the user has enter specialization at all
                 */
                if(newSpecializationGroup.getSpecializations().size() == 0) {
                    /* ops ! */
                    info("specialization group #0 creation failed because the group is not virtual and no specialization has been specified.", specializationGroupHome);
                    facesMessages.add(Severity.ERROR, "Πρέπει να ορίσετε τουλάχιστον μια ειδικότητα όταν η ομάδα δεν είναι εικονική.");
                    return ACTION_OUTCOME_FAILURE;
                }
            }
            
            newSpecializationGroup.setInsertedBy(getPrincipal());
            newSpecializationGroup.setInsertedOn(new Date());
            newSpecializationGroup.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
            specializationGroupHome.persist();
            getEntityManager().flush();
            if(newSpecializationGroup.getIsVirtualGroup()) {
                /* this is a virtual group, create a virtual specialization as well */
                Specialization virtualSpecialization = new Specialization();
                String _id = String.format("V%d", newSpecializationGroup.getId());
                if(_id.length()>6)
                    _id = _id.substring(0,6);
                virtualSpecialization.setId(_id);
                virtualSpecialization.setIsVirtual(Boolean.TRUE);
                virtualSpecialization.setTitle(String.format("%s (%s)", newSpecializationGroup.getTitle(), newSpecializationGroup.getSchoolYear().getTitle()));
                newSpecializationGroup.setVirtualSpecialization(virtualSpecialization);
                newSpecializationGroup.getSpecializations().add(virtualSpecialization);
                getEntityManager().flush();
            }
            info("specialization group #0 has been created", newSpecializationGroup);
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "specialization group home #0 is managed.", specializationGroupHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    @RaiseEvent("leaveCreated")
    public String triggerLeaveAddedAction() {
        info("triggered!");
        return ACTION_OUTCOME_SUCCESS;
    }

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
            if(specializationGroup.getIsVirtualGroup()) {
                Specialization virtualSpecialization = specializationGroup.getVirtualSpecialization();
                virtualSpecialization.setTitle(String.format("%s (%s)", specializationGroup.getTitle(), specializationGroup.getSchoolYear().getTitle()));
            }
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
        specializationGroupHome.clearInstance();
        SpecializationGroup group = specializationGroupHome.getInstance();
        group.setIsVirtualGroup(Boolean.FALSE);
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
