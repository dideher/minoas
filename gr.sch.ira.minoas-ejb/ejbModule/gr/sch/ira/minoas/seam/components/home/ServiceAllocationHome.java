/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("serviceAllocationHome")
public class ServiceAllocationHome extends MinoasEntityHome<ServiceAllocation> {

	@In(create = true)
	private EmployeeHome employeeHome;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "serviceAllocation", scope = ScopeType.PAGE)
	public ServiceAllocation getInstance() {
		return (ServiceAllocation) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		ServiceAllocation instance = new ServiceAllocation();
		instance.setServiceType(ServiceAllocationType.SCHOOL_HEADMASTER);
		instance.setEstablished(getCoreSearching().getActiveSchoolYear(
				getEntityManager()).getStartDate());
		instance.setDueTo(getCoreSearching().getActiveSchoolYear(
				getEntityManager()).getEndDate());
		instance.setActive(Boolean.TRUE);
		return instance;
	}
	
	@Transactional
	public boolean wire() {
		ServiceAllocation instance = getInstance();
		if (!isManaged()) {
			Employee employee = employeeHome != null ? employeeHome.getInstance() : null;
			Employment currentEmployment = employee != null ? employee.getCurrentEmployment() : null;
			if (currentEmployment != null) {
				instance.setSourceUnit(currentEmployment.getSchool());
			} else {
				instance.setSourceUnit(employee.getCurrentPYSDE().getRepresentedByUnit());
			}
		}
		return true;
	}
}
