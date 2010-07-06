/**
 * 
 */
package gr.sch.ira.minoas.seam.components.suggesters;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.PermanentTransferHome;

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
@Name("permanentTransferAwareUnitSuggester")
@Scope(ScopeType.PAGE)
public class PermanentTransferAwareUnitSuggester extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(required = false)
	private PermanentTransferHome permanentTransferHome;

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Unit> suggest(Object search_pattern) {
		Collection<Unit> returnCollection = null;
		PermanentTransferType type = permanentTransferHome != null ? type = permanentTransferHome.getInstance()
				.getType() : null;

		/*
		if (type != null) {
			return getEntityManager()
					.createQuery(
							"SELECT u.unit FROM DisposalUnit u WHERE (u.unit.title) LIKE LOWER(:search_pattern) AND u.type=:type ORDER BY u.unit.title")
					.setParameter("search_pattern",
							CoreUtils.getSearchPattern(String.valueOf(secondmemt_search_pattern))).setParameter("type",
							type).getResultList();

		} else {
			return getEntityManager()
					.createQuery(
							"SELECT u.unit FROM DisposalUnit u WHERE (u.unit.title) LIKE LOWER(:search_pattern) ORDER BY u.unit.title")
					.setParameter("search_pattern",
							CoreUtils.getSearchPattern(String.valueOf(secondmemt_search_pattern))).getResultList();
		}
		*/
		switch (type) {
		case WITHIN_PYSDE:
			returnCollection = getEntityManager()
					.createQuery(
							"SELECT s FROM Unit s WHERE LOWER(s.title) LIKE LOWER(:search_pattern) AND s.pysde.localPYSDE IS TRUE")
					.setParameter("search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern)))
					.getResultList();
			break;
		case TO_OTHER_PYSDE:
			returnCollection = getEntityManager().createQuery(
					"SELECT s FROM Unit s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)  AND s.pysde.localPYSDE IS FALSE").setParameter(
					"search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).getResultList();
			break;
		default:
			returnCollection = getEntityManager().createQuery(
					"SELECT s FROM Unit s WHERE LOWER(s.title) LIKE LOWER(:search_pattern)").setParameter(
					"search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).getResultList();
		}
		return returnCollection;

	}

}
