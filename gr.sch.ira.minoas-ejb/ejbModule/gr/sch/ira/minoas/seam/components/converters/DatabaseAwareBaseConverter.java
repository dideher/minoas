/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;

/**
 * @author slavikos
 *
 */
public abstract class DatabaseAwareBaseConverter extends BaseConverter {
	
	@In
	private EntityManager minoasDatabase;

	protected EntityManager getMinoasDatabase() {
		return minoasDatabase;
	}

}
