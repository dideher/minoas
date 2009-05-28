
package gr.sch.ira.minoas.seam.components.home;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import gr.sch.ira.minoas.model.core.School;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolHome")
@Scope(ScopeType.CONVERSATION)
public class SchoolHome extends MinoasEntityHome<School> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "school")
	public School getInstance() {
		return (School) super.getInstance();
	}
	
}
