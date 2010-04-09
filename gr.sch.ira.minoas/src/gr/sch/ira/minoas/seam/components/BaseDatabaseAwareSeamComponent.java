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

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In
	protected EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
