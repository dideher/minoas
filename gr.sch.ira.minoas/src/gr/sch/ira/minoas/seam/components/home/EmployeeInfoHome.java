/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name(value = "employeeInfoHome")
@AutoCreate
public class EmployeeInfoHome extends MinoasEntityHome<EmployeeInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected EmployeeInfo createInstance() {
		EmployeeInfo instance = new EmployeeInfo();
//		instance.setActive(Boolean.TRUE);
		
		return instance;
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "employeeInfo", scope = ScopeType.PAGE)
	public EmployeeInfo getInstance() {
		// TODO Auto-generated method stub
		EmployeeInfo epInfo = (EmployeeInfo) super.getInstance();
		return epInfo;
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
    }
	
//	/**
//	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
//	 */
//	@Transactional
//	public String insertRankInfo() {
//		
//		EmployeeInfo employeeInfo = (EmployeeInfo)super.getInstance();
//		RankInfo newRankInfo = new RankInfo(employeeInfo.getRankInfo());
//		
//		employeeInfo.setRankInfo(newRankInfo);
//	
//		return update();
//	}


}
