/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.WorkExperience;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name(value = "workExperienceHome")
public class WorkExperienceHome extends MinoasEntityHome<WorkExperience> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* used as a holder value in forms */ 
	protected String tempValueHolder1; 

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected WorkExperience createInstance() {
		WorkExperience instance = new WorkExperience();
		instance.setActive(Boolean.TRUE);
		
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "workExperience", scope = ScopeType.PAGE)
	public WorkExperience getInstance() {
		// TODO Auto-generated method stub
		return (WorkExperience) super.getInstance();
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

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#clearInstance()
     */
    @Override
    public void clearInstance() {
        super.clearInstance();
        setTempValueHolder1(null);
    }
    
	/**
	 * @return the tempValueHolder1
	 */
	public String getTempValueHolder1() {
		return tempValueHolder1;
	}

	/**
	 * @param tempValueHolder1 the tempValueHolder1 to set
	 */
	public void setTempValueHolder1(String tempValueHolder1) {
		this.tempValueHolder1 = tempValueHolder1;
	}
	


}
