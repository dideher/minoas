package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.employement.DisposalType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("disposalHome")
@AutoCreate
public class DisposalHome extends MinoasEntityHome<Disposal> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private EmployeeHome employeeHome;

	@In(create = true)
	private EmploymentHome employmentHome;

	@Transactional
	public String cancel() {
		Disposal current_disposal = getInstance();
		current_disposal.setActive(Boolean.FALSE);
		super.update();
		info("principal '#0' canceled employee #1 current leave #1.", getPrincipalName(), current_disposal
				.getEmployee(), current_disposal);
		return "updated";
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Disposal createInstance() {
		Disposal new_instance = new Disposal();
		new_instance.setActive(Boolean.TRUE);
		new_instance.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
		new_instance.setType(DisposalType.PARTIAL);
		new_instance.setTargetType(DisposalTargetType.TO_SCHOOL);
		return new_instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "disposal", scope = ScopeType.PAGE)
	public Disposal getInstance() {
		return (Disposal) super.getInstance();

	}
	
	/**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
     */
    @Override
    @Transactional
    public String persist() {
        return super.persist();
    }

    @Transactional
    public String revert() {
        info("principal #0 is reverting updates to leave #1", getPrincipalName(), getInstance());
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

	

	@Transactional
	public void prepareForNewDisposal() {
		this.clearInstance();
		employmentHome.clearInstance();
	}

	

	@Transactional
	public boolean wire() {
		return true;
	}

}
