/*
 * 
 *
 * Copyright (c) 2009 FORTHnet, S.A. All Rights Reserved.
 *
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package gr.sch.ira.minoas.seam.components.home;

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

	@In(required = false)
	private Identity identity;
	
	@In(required=false) FacesMessages facesMessages;

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
	
	protected String getPrincipalName() {
		return getIdentity()!=null ? getIdentity().getPrincipal().getName() : "<anonymous>";
	}

	/**
	 * @see org.jboss.seam.framework.EntityHome#getPersistenceContextName()
	 */
	@Override
	protected String getPersistenceContextName() {
		return PERSITESTENCE_CONTEXT_NAME;
	}

	/**
	 * @return the logger
	 */
	protected Log getLogger() {
		return logger;
	}

}
