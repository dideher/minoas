
package gr.sch.ira.minoas.seam.components.reports;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;

import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityQuery;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class TeachingHourAnalysisReport extends BaseReport {
	
	@Transactional
	public Object foo() {
		/* get the active school year */
		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		
		
		/* first fetch all specialization groups */
		Collection<SpecializationGroup> specializationGroups = getCoreSearching().getSpecializationGroups(activeSchoolYear, getEntityManager());
		
		/* first fetch the teaching requirments */
		
		return null;
	}

}
