package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.Secondment;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
@AutoCreate
public class SecondmentHome extends MinoasEntityHome<Secondment> {

	
    protected String tempValueHolder1; /* used as a holder value in forms */ 
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Secondment createInstance() {
		Secondment instance = new Secondment();
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

	
    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#clearInstance()
     */
    @Override
    public void clearInstance() {
        super.clearInstance();
        setTempValueHolder1(null);
    }
	

}
