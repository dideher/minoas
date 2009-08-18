
package gr.sch.ira.minoas.seam.components.reports;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityQuery;

import sun.security.action.GetLongAction;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value="teachingVoidBySpecializationReport")
public class TeachingHourAnalysisReport extends BaseReport {
	
	@Transactional
	public Object foo() {
		long started = System.currentTimeMillis(), finished;
		Calendar todayCal = Calendar.getInstance();
		todayCal.set(Calendar.DAY_OF_MONTH, 20);
		todayCal.set(Calendar.MONTH, Calendar.OCTOBER);
		Date today = DateUtils.truncate(todayCal.getTime(), Calendar.DAY_OF_MONTH);
		
		info("generating report ");
		/* get the active school year */
		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		
		
		/* fetch all specialization groups */
		Collection<SpecializationGroup> specializationGroups = getCoreSearching().getSpecializationGroups(activeSchoolYear, getEntityManager());
		
		/* fetch all school units */
		Collection<School> schools = getCoreSearching().getSchools(getEntityManager());
		int total = 0;
		for(School school : schools) {
			Collection<TeachingRequirement> schoolTeachingRequirments = getCoreSearching().getSchoolTeachingRequirement(getEntityManager(), school, activeSchoolYear);
			
			/* find first all employees having a regular possition in the school */
			Collection<Employee> schoolRegularEmployees = getCoreSearching().getSchoolEmployeeWithPresence(getEntityManager(), today, school, activeSchoolYear);
			info("found #0 employee(s) in school #1 during school year #2 with precence during day #3.", schoolRegularEmployees.size(), school, activeSchoolYear, today);
			total+= schoolRegularEmployees.size();
		}
		info("employee precense query fetched totally #0 employee(s)", total);
		
		
		
		/* first fetch the teaching requirement */
		
		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished-started));
		return "lalal";
	}

}
