package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.DeputyEmploymentInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("deputyEmploymentInfoHome")
@Scope(ScopeType.CONVERSATION)
public class DeputyEmploymentInfoHome extends MinoasEntityHome<DeputyEmploymentInfo> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		DeputyEmploymentInfo new_instance = new DeputyEmploymentInfo();
		return new_instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "deputyEmploymentInfo", scope = ScopeType.PAGE)
	public DeputyEmploymentInfo getInstance() {
		return (DeputyEmploymentInfo) super.getInstance();

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
