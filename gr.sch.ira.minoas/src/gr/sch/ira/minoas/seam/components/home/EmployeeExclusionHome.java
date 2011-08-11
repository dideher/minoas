package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.EmployeeExclusion;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeExclusionHome")
@Scope(ScopeType.CONVERSATION)
public class EmployeeExclusionHome extends MinoasEntityHome<EmployeeExclusion> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected EmployeeExclusion createInstance() {
		EmployeeExclusion new_instance = new EmployeeExclusion();
		return new_instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employeeExclusion", scope = ScopeType.PAGE)
	public EmployeeExclusion getInstance() {
		return (EmployeeExclusion) super.getInstance();

	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		EmployeeExclusion instance = getInstance();
		instance.setInsertedBy(getPrincipal());
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

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}

}
