package gr.sch.ira.minoas.seam.components.reports.teachingResources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.reports.BaseReport;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.framework.EntityQuery;


/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "teachingVoidBySpecializationReport")
@Scope(ScopeType.CONVERSATION)
public class TeachingHourAnalysisReport extends BaseReport {

	private Date effectiveDay;
	
	private School school;
	
	private Character region;
	
	@DataModel
	private List<SchoolVoidAnalysisItem> reportData;
	
	@Transactional
	public Object foo() {
		try {
			long started = System.currentTimeMillis(), finished;
			Calendar todayCal = Calendar.getInstance();
			todayCal.set(Calendar.DAY_OF_MONTH, 20);
			todayCal.set(Calendar.MONTH, Calendar.OCTOBER);
			Date today = DateUtils.truncate(todayCal.getTime(), Calendar.DAY_OF_MONTH);

			info("generating report ");
			
			reportData = new ArrayList<SchoolVoidAnalysisItem>(100);
			
			/* get the active school year */
			SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

			/* fetch all specialization groups */
			Collection<SpecializationGroup> specializationGroups = getCoreSearching().getSpecializationGroups(
					activeSchoolYear, getEntityManager());

			
			HashMap<Specialization, SpecializationGroup> specializationGroupMap = new HashMap<Specialization, SpecializationGroup>();
			for(SpecializationGroup specializationGroup : specializationGroups) {
				for(Specialization specialization : specializationGroup.getSpecializations() )
					specializationGroupMap.put(specialization, specializationGroup);
			}
			
			
			/* fetch all school units */
			Collection<School> schools = getCoreSearching().getSchools(getEntityManager());
			int total = 0;
			int count = 0;
			for (School school : schools) {
				
				
				
				
				
				
				Collection<TeachingRequirement> schoolTeachingRequirments = getCoreSearching()
						.getSchoolTeachingRequirement(getEntityManager(), school, activeSchoolYear);

				
				Map<SpecializationGroup, TeachingRequirement> schoolTeachingRequirementMap = new HashMap<SpecializationGroup, TeachingRequirement>(schoolTeachingRequirments.size());
				for(TeachingRequirement requirement : schoolTeachingRequirments) {
					schoolTeachingRequirementMap.put(requirement.getSpecialization(), requirement);
				}
				
				
				SchoolVoidAnalysisItem item = new SchoolVoidAnalysisItem(school);
				item.populateResourcesUsingSpecializationGroups(specializationGroups);
				item.updateWithTeachingRequirements(schoolTeachingRequirments);
				
				
				
				Collection<Employee> schoolRegularEmployees = getEntityManager()
						.createQuery(
								"SELECT DISTINCT e FROM Employee AS e LEFT OUTER JOIN FETCH e.secondments  WHERE (e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
										+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
										+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
										+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND (a.serviceUnit IS NOT :school) AND (:dayOfInterest BETWEEN a.established AND a.dueTo))"
						).setParameter("school", school).setParameter("schoolYear", activeSchoolYear).setParameter("dayOfInterest", today).getResultList();
				info("found #0 employee(s) in school #1 with precence during day #2.", schoolRegularEmployees.size(),
						school, today);
				total += schoolRegularEmployees.size();
				count++;
				if (count == 40)
					break;
				/* find first all employees having a regular possition in the school */
				//Collection<Employee> schoolRegularEmployees = getCoreSearching().getSchoolEmployeeWithPresence(getEntityManager(), today, school, activeSchoolYear);
				Collection<Secondment> secondments = getEntityManager()
						.createQuery(
								"SELECT s FROM Secondment s WHERE (s.targetUnit=:school AND s.schoolYear=:schoolYear) AND (s.established <= :dayOfInterest AND s.dueTo >= :dayOfInterest))")
						.setParameter("school", school).setParameter("schoolYear", activeSchoolYear).setParameter(
								"dayOfInterest", today).getResultList();
				//info("found #0 secondments(s) in school #1 during school year #2 with precence during day #3.", secondments.size(), school, activeSchoolYear, today);

				Collection<ServiceAllocation> serviceAllocations = getEntityManager()
						.createQuery(
								"SELECT s FROM ServiceAllocation s WHERE (s.serviceUnit=:school AND (s.established <= :dayOfInterest AND s.dueTo >= :dayOfInterest))")
						.setParameter("school", school).setParameter("dayOfInterest", today).getResultList();
				//info("found #0 service allocations(s) in school #1 with precence during day #2.", serviceAllocations.size(), school, today);

				
				
				
				
				/* for all employees we know :
				 * 1) They don't appear to have a leave
				 * 2) They don't appear to have a secondment
				 * 3) They don't appear to have a service allocation out of their home school unit
				 */
				for(Employee regularEmployee : schoolRegularEmployees) {
					
					info("handling employee #0", regularEmployee);
					SpecializationGroup employeesMappedSpecializationGroup = specializationGroupMap.get(regularEmployee.getLastSpecialization());
					info("employee's #0 specialization #1 has been mapped to specialization group #2", regularEmployee, regularEmployee.getLastSpecialization(), employeesMappedSpecializationGroup);
					TeachingRequirement requirement = schoolTeachingRequirementMap.get(employeesMappedSpecializationGroup);
					
					if(requirement!=null) {
						info("employee's #0 specialization #1 will be used for teaching requirement #2.", regularEmployee, regularEmployee.getLastSpecialization(), requirement);
						
					}
					
					
					
					regularEmployee.getSecondments();
					regularEmployee.getServiceAllocations();
					regularEmployee.getLeaves();
				}
				
				
				
				
				
				
				
				
				
				reportData.add(item);
				
				
				
			}
			info("employee precense query fetched totally #0 employee(s)", total);

			/* first fetch the teaching requirement */

			finished = System.currentTimeMillis();
			info("report has been generated in #0 [ms]", (finished - started));
			return "lalal";
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			throw new RuntimeException(ex);
		}
		
	}

	/**
	 * @return the effectiveDay
	 */
	public Date getEffectiveDay() {
		return effectiveDay;
	}

	/**
	 * @param effectiveDay the effectiveDay to set
	 */
	public void setEffectiveDay(Date effectiveDay) {
		this.effectiveDay = effectiveDay;
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * @return the region
	 */
	public Character getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(Character region) {
		this.region = region;
	}

	/**
	 * @return the reportData
	 */
	public List<SchoolVoidAnalysisItem> getReportData() {
		return reportData;
	}

	/**
	 * @param reportData the reportData to set
	 */
	public void setReportData(List<SchoolVoidAnalysisItem> reportData) {
		this.reportData = reportData;
	}

}
