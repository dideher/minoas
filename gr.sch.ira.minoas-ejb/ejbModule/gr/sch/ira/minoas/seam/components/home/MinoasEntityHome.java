
package gr.sch.ira.minoas.seam.components.home;

import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.lang.reflect.ParameterizedType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public abstract class MinoasEntityHome<E> extends EntityHome {

	public static final String PERSITESTENCE_CONTEXT_NAME = "entityManager";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In()
	private CoreSearching coreSearching;
	
	@In(required=false) FacesMessages facesMessages;
	
	@In(required = false)
	private Identity identity;

	@Logger
	private Log logger;

	private Class<E> persistentClass;
	
	/**
	 * @see org.jboss.seam.framework.EntityHome#create()
	 */
	@Override
	public void create() {
		super.create();
		persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	public E getDefinedInstace() {
		if (isIdDefined()) {
			return getEntityManager().find(persistentClass, getId());
		} else
			return null;
	}

	/**
	 * @return the identity
	 */
	protected Identity getIdentity() {
		return identity;
	}
	
	/**
	 * @return the logger
	 */
	protected Log getLogger() {
		return logger;
	}
	
	/**
	 * @see org.jboss.seam.framework.EntityHome#getPersistenceContextName()
	 */
	@Override
	protected String getPersistenceContextName() {
		return PERSITESTENCE_CONTEXT_NAME;
	}

	protected Principal getPrincipal() {
		return coreSearching.getPrincipal(getEntityManager(), getPrincipalName());
	}

	protected String getPrincipalName() {
		return getIdentity()!=null ? getIdentity().getPrincipal().getName() : "<anonymous>";
	}

}
