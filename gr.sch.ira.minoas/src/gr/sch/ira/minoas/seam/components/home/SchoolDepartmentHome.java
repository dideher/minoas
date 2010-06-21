/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.classrooms.SchoolClass;
import gr.sch.ira.minoas.model.classrooms.SchoolDepartment;
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

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("schoolDepartmentHome")
public class SchoolDepartmentHome extends MinoasEntityHome<SchoolDepartment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected SchoolDepartment createInstance() {
		SchoolDepartment instance = new SchoolDepartment();
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "schoolDepartment", scope = ScopeType.PAGE)
	public SchoolDepartment getInstance() {
		return (SchoolDepartment) super.getInstance();
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
		getEntityManager().refresh(getInstance());
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
