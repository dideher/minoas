package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.Telephone;
import gr.sch.ira.minoas.model.core.TelephoneNumberType;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.PrincipalHome;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeWorkExperienceItem;

import java.util.Collection;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "principalManagement")
@Scope(ScopeType.PAGE)
public class PrincipalManagement extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(required = true, create = true)
    private PrincipalHome principalHome;

    @DataModel(value = "employeeWorkExperienceItems")
    private Collection<EmployeeWorkExperienceItem> employeeWorkExperienceItems;

    /**
     * @return the employeeWorkExperienceItems
     */
    public Collection<EmployeeWorkExperienceItem> getEmployeeWorkExperienceItems() {
        return employeeWorkExperienceItems;
    }

    /**
     * @param employeeWorkExperienceItems the employeeWorkExperienceItems to set
     */
    public void setEmployeeWorkExperienceItems(Collection<EmployeeWorkExperienceItem> employeeWorkExperienceItems) {
        this.employeeWorkExperienceItems = employeeWorkExperienceItems;
    }

    /**
     * @return the principalHome
     */
    public PrincipalHome getPrincipalHome() {
        return principalHome;
    }

    /**
     * @param principalHome the principalHome to set
     */
    public void setPrincipalHome(PrincipalHome principalHome) {
        this.principalHome = principalHome;
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    public String disablePrincipal() {
        if (getPrincipalHome().isManaged()) {
            Principal p = getPrincipalHome().getInstance();
            p.setActive(Boolean.FALSE);
            info("Principal '#0' disabled principal '#1'", getPrincipalName(), p.getUsername());
            getPrincipalHome().update();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            getFacesMessages().add(Severity.ERROR, "principal home is not managed");
            return ACTION_OUTCOME_FAILURE;
        }
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    public String enablePrincipal() {
        if (getPrincipalHome().isManaged()) {
            Principal p = getPrincipalHome().getInstance();
            p.setActive(Boolean.TRUE);
            info("Principal '#0' enabled principal '#1'", getPrincipalName(), p.getUsername());
            getPrincipalHome().update();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "principal home #0 is not managed.", getPrincipalHome());
            return ACTION_OUTCOME_FAILURE;
        }

    }

    
    
    @Transactional
    @RaiseEvent("principalModified")
    public String modifyPrincipalAction() {

        if (getPrincipalHome().isManaged()) {
            Principal principal = getPrincipalHome().getInstance();
            Telephone t = principal.getInformationTelephone();
            if(t==null) {
                t = new Telephone();
                t.setInsertedBy(principal);
                t.setType(TelephoneNumberType.WORK);
                t.setInsertedOn(new Date());
                t.setNumber(getPrincipalHome().getTempValueHolder1());
                principal.setInformationTelephone(t);
                getEntityManager().persist(t);
            } else
                    t.setNumber(getPrincipalHome().getTempValueHolder1());
            getPrincipalHome().update();
            
            getEntityManager().flush();
            info("principal #0 has been modified", principal);
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "principal home #0 is not managed.", getPrincipalHome());
            return ACTION_OUTCOME_FAILURE;
        }
    }

    @Transactional
    @RaiseEvent("principalCreated")
    public String addNewPrincipalAction() {
        if (!getPrincipalHome().isManaged()) {
            Principal p = getPrincipalHome().getInstance();
            p.setActive(Boolean.TRUE);
            getPrincipalHome().persist();
            Telephone t = new Telephone();
            t.setInsertedBy(p);
            t.setType(TelephoneNumberType.WORK);
            t.setInsertedOn(new Date());
            t.setNumber(getPrincipalHome().getTempValueHolder1());
            p.setInformationTelephone(t);
            getEntityManager().persist(t);
            
            getEntityManager().flush();
            info("principal #0 has been created", getPrincipalHome().getInstance());
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "principal home #0 is managed.", getPrincipalHome());
            return ACTION_OUTCOME_FAILURE;
        }
    }

}
