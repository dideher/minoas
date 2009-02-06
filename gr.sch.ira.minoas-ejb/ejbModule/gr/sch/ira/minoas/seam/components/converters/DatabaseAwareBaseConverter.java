/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;

/**
 * @author slavikos
 *
 */

public abstract class DatabaseAwareBaseConverter extends BaseConverter {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ENTITY_MANAGER_SEAM_COMPONENT_NAME = "em";
	
	protected EntityManager getMinoasDatabase() {
		return (EntityManager)Component.getInstance(ENTITY_MANAGER_SEAM_COMPONENT_NAME);
	}

}
