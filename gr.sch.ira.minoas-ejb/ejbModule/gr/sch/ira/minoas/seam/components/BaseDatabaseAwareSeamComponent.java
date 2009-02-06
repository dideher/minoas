/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;

/**
 * @author slavikos
 *
 */
public abstract class BaseDatabaseAwareSeamComponent extends BaseSeamComponent {
	
	@In
	protected EntityManager em;

	protected EntityManager getEm() {
		return em;
	}

}
