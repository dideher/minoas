/**
 * 
 */
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.Address;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;



/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("addressHome")
@Scope(ScopeType.CONVERSATION)
public class AddressHome extends MinoasEntityHome<Address> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Address instance = new Address();
		return instance;
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
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "address", scope = ScopeType.PAGE)
	public Address getInstance() {
		// TODO Auto-generated method stub
		return (Address) super.getInstance();
	}

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to adress #1",
				getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

}
