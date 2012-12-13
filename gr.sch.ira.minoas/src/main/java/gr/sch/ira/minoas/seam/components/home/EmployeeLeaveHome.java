/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employement.EmployeeLeave;

import java.util.Calendar;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "employeeLeaveHome")
@AutoCreate
public class EmployeeLeaveHome extends MinoasEntityHome<EmployeeLeave> {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected String tempValueHolder1; /* used as a holder value in forms */ 
	

	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected EmployeeLeave createInstance() {
	    EmployeeLeave instance = new EmployeeLeave();
		instance.setActive(Boolean.TRUE);
		Calendar cal = Calendar.getInstance();
		instance.setEstablished(new Date(cal.getTimeInMillis()));
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employeeLeave", scope = ScopeType.PAGE)
	public EmployeeLeave getInstance() {
		// TODO Auto-generated method stub
		return (EmployeeLeave) super.getInstance();
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
