/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;
import gr.sch.ira.minoas.model.employement.Service;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 */
@Name("serviceHome")
public class ServiceHome extends MinoasEntityHome<Service> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

   /**
     * @see org.jboss.seam.framework.Home#createInstance()
     */
    @Override
    protected Service createInstance() {
        Service instance = new Service();
//		instance.setServiceType(ServiceAllocationType.SCHOOL_HEADMASTER);
//		instance.setEstablished(getCoreSearching().getActiveSchoolYear(getEntityManager()).getSchoolYearStart());
//		instance.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getSchoolYearStop());
//		instance.setActive(Boolean.TRUE);
        return instance;
    }

    /**
     * @see org.jboss.seam.framework.Home#getInstance()
     */
    @Override
    @Factory(value = "service", scope = ScopeType.PAGE)
    public Service getInstance() {
        return (Service) super.getInstance();
    }

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
     */
    @Override
    @Transactional
    public String persist() {
        getInstance().setInsertedBy(getPrincipal());
        return super.persist();
    }

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#remove()
     */
    @Override
    @Transactional
    public String remove() {
        return super.remove();
    }

    @Transactional
    public String revert() {
//		info("principal #0 is reverting updates to service allocation #1", getPrincipalName(), getInstance());
//		getEntityManager().refresh(getInstance());
        return "reverted";
    }

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
     */
    @Override
    @Transactional
    public String update() {
        return super.update();
    }

}
