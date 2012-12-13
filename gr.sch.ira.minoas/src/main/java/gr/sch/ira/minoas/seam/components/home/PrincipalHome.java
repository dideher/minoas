package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.core.Telephone;
import gr.sch.ira.minoas.model.security.Principal;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "principalHome")
public class PrincipalHome extends MinoasEntityHome<Principal> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	
	protected String tempValueHolder1; /* used as a holder value in forms */ 
	
	protected String tempValueHolder2; /* used as a holder value in forms */ 

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "principal", scope = ScopeType.PAGE)
	public Principal getInstance() {
		return (Principal) super.getInstance();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		/* check if the username is already in use */
		Principal newPrincipal = getInstance();
		Principal temp = getCoreSearching().getPrincipal(getEntityManager(), newPrincipal.getUsername());
		if (temp != null) {
			getLogger().warn("principal '#0' tried to insert a principal with an existing username '#1'.",
					getPrincipalName(), temp.getUsername());
			return DUPLICATE_VALUE_OUTCOME;
		}
		newPrincipal.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#remove()
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
	 * @see org.jboss.seam.framework.EntityHome#update()
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
     * @return the tempValueHolder2
     */
    public String getTempValueHolder2() {
        return tempValueHolder2;
    }

    /**
     * @param tempValueHolder2 the tempValueHolder2 to set
     */
    public void setTempValueHolder2(String tempValueHolder2) {
        this.tempValueHolder2 = tempValueHolder2;
    }

    /**
     * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#setId(java.lang.Object)
     */
    @Override
    public void setId(Object id) {
        super.setId(id);
        Telephone t = getInstance().getInformationTelephone();
        setTempValueHolder1(t != null ? t.getNumber() : null);
    }

}
