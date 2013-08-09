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

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("serviceAllocationHome")
public class ServiceAllocationHome extends MinoasEntityHome<ServiceAllocation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private EmployeeHome employeeHome;

	@Transactional
	public String cancel() {
		ServiceAllocation currentServiceAllocation = getInstance();
		currentServiceAllocation.setActive(Boolean.FALSE);
		Employee employee = currentServiceAllocation.getEmployee();

		Employment employment = employee.getCurrentEmployment();
		if (employment != null)
			employment.setServiceAllocation(null);
		super.update();
		info("principal '#0' canceled employee #1 current service allocation #1.",
				getPrincipalName(), employee, currentServiceAllocation);
		clearInstance();
		return "updated";
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected ServiceAllocation createInstance() {
		ServiceAllocation instance = new ServiceAllocation();
		instance.setServiceType(ServiceAllocationType.SCHOOL_HEADMASTER);
		instance.setEstablished(getCoreSearching().getActiveSchoolYear(
				getEntityManager()).getSchoolYearStart());
		instance.setDueTo(getCoreSearching().getActiveSchoolYear(
				getEntityManager()).getSchoolYearStop());
		instance.setActive(Boolean.TRUE);
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "serviceAllocation", scope = ScopeType.PAGE)
	public ServiceAllocation getInstance() {
		return (ServiceAllocation) super.getInstance();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
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
		info("principal #0 is reverting updates to service allocation #1",
				getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	public void suggestHours() {
		ServiceAllocation instance = getInstance();
		switch (instance.getServiceType()) {
		case GRAND_HEADMASTER:
		case SCHOOL_HEADMASTER:
		case OFFICE_CHIEF:
			instance.setWorkingHoursOnRegularPosition(0);
			instance.setWorkingHoursOnServicingPosition(0);
			break;
		case SCHOOL_SUBHEADMASTER:
			instance.setWorkingHoursOnRegularPosition(0);
			instance.setWorkingHoursOnServicingPosition(8);
			break;
		}
		return;
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
