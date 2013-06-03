package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.NonRegularEmploymentInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name("nonRegularEmploymentInfoHome")
@Scope(ScopeType.CONVERSATION)
public class NonRegularEmploymentInfoHome extends MinoasEntityHome<NonRegularEmploymentInfo> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected NonRegularEmploymentInfo createInstance() {
		NonRegularEmploymentInfo new_instance = new NonRegularEmploymentInfo();
		return new_instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "nonRegularEmploymentInfo", scope = ScopeType.PAGE)
	public NonRegularEmploymentInfo getInstance() {
		return (NonRegularEmploymentInfo) super.getInstance();

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

	@Transactional
	public boolean wire() {
		return true;
	}
}
