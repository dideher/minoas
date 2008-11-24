/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

/**
 * @author slavikos
 *
 */
@Name("secondmentAwareUnitSuggester")
@Scope(ScopeType.CONVERSATION)
public class SecondmentAwareUnitSuggester extends BaseDatabaseAwareSeamComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@In(required=false)
	private SecondmentType selectedSecondmentType;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Unit> suggest(Object secondmemt_search_pattern) {
		info("selected secondment type is #0", selectedSecondmentType);
		return getMinoasDatabase().createQuery("SELECT u FROM Unit u WHERE LOWER(u.title) LIKE LOWER(:search_pattern)").setParameter("search_pattern", CoreUtils.getSearchPattern(String.valueOf(secondmemt_search_pattern))).getResultList();
	}

}
