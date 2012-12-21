package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private EmployeeHome employeeHome;

	
	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Secondment createInstance() {
		Secondment instance = new Secondment();
		instance.setSecondmentType(SecondmentType.FULL_TO_SCHOOL);
		instance.setEmployeeRequested(Boolean.TRUE);
		instance
				.setEstablished(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStart());
		instance.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "secondment", scope = ScopeType.PAGE)
	public Secondment getInstance() {
		return (Secondment) super.getInstance();
	}

	

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to secondment #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	

	

}
