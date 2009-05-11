

package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.security.Principal;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public interface IPrincipalDAO extends IGenericDAO<Principal, Long> {
	
	public Principal findByUsername(String username);
}
