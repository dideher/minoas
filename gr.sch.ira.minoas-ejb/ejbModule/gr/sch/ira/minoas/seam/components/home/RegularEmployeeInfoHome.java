package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("regularEmployeeInfoHome")
@Scope(ScopeType.CONVERSATION)
public class RegularEmployeeInfoHome extends MinoasEntityHome<RegularEmployeeInfo> {

	

	
	

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "regularEmployeeInfo", scope = ScopeType.PAGE)
	public RegularEmployeeInfo getInstance() {
		return (RegularEmployeeInfo) super.getInstance();
		
	}

	

	

	@Transactional
	public boolean wire() {
		return true;
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
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}
	
	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		RegularEmployeeInfo new_instance = new RegularEmployeeInfo();
		return new_instance;
	}
}
