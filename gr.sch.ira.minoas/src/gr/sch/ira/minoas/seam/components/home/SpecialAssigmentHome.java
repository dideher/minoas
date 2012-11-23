/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.SpecialAssigment;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "specialAssigmentHome")
@AutoCreate
public class SpecialAssigmentHome extends MinoasEntityHome<SpecialAssigment> {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected SpecialAssigment createInstance() {
	    SpecialAssigment instance = new SpecialAssigment();
		instance.setActive(Boolean.TRUE);
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "specialAssigment", scope = ScopeType.PAGE)
	public SpecialAssigment getInstance() {
		// TODO Auto-generated method stub
		return (SpecialAssigment) super.getInstance();
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
		info("principal #0 is reverting updates to special assigment #1", getPrincipalName(), getInstance());
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

   
    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#clearInstance()
     */
    @Override
    public void clearInstance() {
        super.clearInstance();
    }
	

}
