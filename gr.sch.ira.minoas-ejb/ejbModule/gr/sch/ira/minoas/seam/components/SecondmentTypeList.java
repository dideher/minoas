/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.employement.SecondmentType;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;

/**
 * @author slavikos
 *
 */
@Name("secondmentTypes")
@Scope(ScopeType.PAGE)
public class SecondmentTypeList extends BaseDatabaseAwareSeamComponent {

	private Collection<SecondmentType> secondmentTypes;
	
	
	@Unwrap
	public Collection<SecondmentType> unwrap() {
		if(secondmentTypes==null) {
			secondmentTypes = getEntityManager().createQuery("SELECT s FROM SecondmentType s").getResultList();
		}
		return secondmentTypes;
	}
}
