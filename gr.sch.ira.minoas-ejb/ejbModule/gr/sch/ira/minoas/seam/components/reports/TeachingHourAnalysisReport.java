package gr.sch.ira.minoas.seam.components.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolVoidAnalysisItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SpecializationGroupVoidAnalysis;
import gr.sch.ira.minoas.seam.components.reports.resource.SpecializationGroupVoidAnalysisItem;
import gr.sch.ira.minoas.seam.components.reports.resource.TeachingResource;

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

	public static final int HOURS_FOR_REGULAR_POSITION = 18;

	private Date effectiveDay = new Date(System.currentTimeMillis());

	private School school;

	private Character region;

	private SpecializationGroup specializationGroup;

	private List<SchoolVoidAnalysisItem> reportData;

	@DataModel(value = "reportDataBySpecialization")
	private Collection<SpecializationGroupVoidAnalysis> reportDataBySpecialization;

	@DataModel(value = "reportTextAnalysis")
	private Collection<String> reportTextAnalysis = new ArrayList<String>(100);

	@Transactional
	public Object foo() {
		boolean useTextAnalysis = false;
		try {
			long started = System.currentTimeMillis(), finished;
			Date today = DateUtils.truncate(effectiveDay, Calendar.DAY_OF_MONTH);

			info("generating report ");

			reportData = new ArrayList<SchoolVoidAnalysisItem>(100);

			/* get the active school year */
			SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

			/* fetch all specialization groups */
			Collection<SpecializationGroup> specializationGroups = null;
			if(specializationGroup!=null) {
				specializationGroups = new ArrayList<SpecializationGroup>(1);
				specializationGroups.add(specializationGroup);
			} else {
				specializationGroups = getCoreSearching().getSpecializationGroups(activeSchoolYear, getEntityManager());
			}
			
			HashMap<Specialization, SpecializationGroup> specializationGroupMap = new HashMap<Specialization, SpecializationGroup>();
			for (SpecializationGroup specializationGroup : specializationGroups) {
				for (Specialization specialization : specializationGroup.getSpecializations())
					specializationGroupMap.put(specialization, specializationGroup);
			}

			Collection<School> schools = null;
			/* fetch all school units */

			if (school != null) {
				schools = new ArrayList<School>(1);
				schools.add(school);
				useTextAnalysis = true;
				reportTextAnalysis.clear();
			} else {
				schools = getCoreSearching().getSchools(getEntityManager(), region);
			}
			for (School school : schools) {

				Collection<TeachingRequirement> schoolTeachingRequirments = getCoreSearching()
						.getSchoolTeachingRequirement(getEntityManager(), school, activeSchoolYear);

				Map<SpecializationGroup, TeachingRequirement> schoolTeachingRequirementMap = new HashMap<SpecializationGroup, TeachingRequirement>(
						schoolTeachingRequirments.size());
				for (TeachingRequirement requirement : schoolTeachingRequirments) {
					schoolTeachingRequirementMap.put(requirement.getSpecialization(), requirement);
				}

				SchoolVoidAnalysisItem item = new SchoolVoidAnalysisItem(school);
				item.populateResourcesUsingSpecializationGroups(specializationGroups);
				item.updateWithTeachingRequirements(schoolTeachingRequirments);

				Collection<Employee> schoolRegularEmployees = getCoreSearching().getSchoolRegularEmployees(
						getEntityManager(), school, activeSchoolYear, today, specializationGroup);
				info("found #0 employee(s) in school #1 with precence during day #2.", schoolRegularEmployees.size(),
						school, today);

				Collection<Secondment> secondments = getCoreSearching().getSchoolSecondments(getEntityManager(),
						school, activeSchoolYear, today, specializationGroup);

				Collection<ServiceAllocation> serviceAllocations = getCoreSearching().getSchoolServiceAllocations(
						getEntityManager(), school, today, specializationGroup);

				/* for all employees we know :
				 * 1) They don't appear to have a leave
				 * 2) They don't appear to have a secondment
				 * 3) They don't appear to have a service allocation
				 */
				for (Employee regularEmployee : schoolRegularEmployees) {
					SpecializationGroup employeesMappedSpecializationGroup = specializationGroupMap.get(regularEmployee
							.getLastSpecialization());

					if (employeesMappedSpecializationGroup == null) {
						/* the employee's specialization could not be mapped to a specialization group, so create
						 * a dynamic one
						 */
						employeesMappedSpecializationGroup = new SpecializationGroup();
						employeesMappedSpecializationGroup.setTitle(regularEmployee.getLastSpecialization().getId()
								+ " - " + regularEmployee.getLastSpecialization().getTitle()
								+ " [** ΔΕΝ ΑΝΗΚΕΙ ΣΕ ΟΜΑΔΑ ** ]");
						specializationGroupMap.put(regularEmployee.getLastSpecialization(),
								employeesMappedSpecializationGroup);
					}

					/* since we know that the employee has no leave or secondment is almost safe 
					 * to assume his is providing his working hour(s). 
					 */

					item.addEmployeeWorkingHours(employeesMappedSpecializationGroup, regularEmployee
							.getCurrentEmployment().getFinalWorkingHours());
					
					if (useTextAnalysis)
						reportTextAnalysis.add("Ο εκπαιδευτικός " + regularEmployee + " με ειδικότητα "
								+ regularEmployee.getLastSpecialization() + " και ομαδοποιημένη ειδικότητα "
								+ employeesMappedSpecializationGroup.getTitle() + " προσφέρει στην μονάδα συνολικά "
								+ regularEmployee.getCurrentEmployment().getFinalWorkingHours() + " διδακτικές ώρες.");

				}

				/* handle service allocations that are servicing
				 * in this very unit
				 */

				for (ServiceAllocation serviceAllocation : serviceAllocations) {
					SpecializationGroup employeesMappedSpecializationGroup = specializationGroupMap
							.get(serviceAllocation.getEmployee().getLastSpecialization());

					item.addEmployeeWorkingHours(employeesMappedSpecializationGroup, serviceAllocation
							.getWorkingHoursOnServicingPosition());
					if (useTextAnalysis)
						reportTextAnalysis.add("Ο εκπαιδευτικός " + serviceAllocation.getEmployee() + " με ειδικότητα "
								+ serviceAllocation.getEmployee().getLastSpecialization()
								+ " και ομαδοποιημένη ειδικότητα " + employeesMappedSpecializationGroup.getTitle()
								+ " που εκτελεί θητεία " + serviceAllocation + " προσφέρει στην μονάδα συνολικά "
								+ serviceAllocation.getWorkingHoursOnServicingPosition() + " διδακτικές ώρες.");
				}

				/* handle secondments that are teaching in this very unit */
				for (Secondment secondment : secondments) {
					SpecializationGroup employeesMappedSpecializationGroup = specializationGroupMap.get(secondment
							.getEmployee().getLastSpecialization());

					if(employeesMappedSpecializationGroup == null) {
						info("employeesMappedSpecializationGroup is null for employee with secondment #0", secondment);
					}
					item.addEmployeeWorkingHours(employeesMappedSpecializationGroup, secondment.getFinalWorkingHours());
					if (useTextAnalysis)
						reportTextAnalysis.add("Ο εκπαιδευτικός " + secondment.getEmployee() + " με ειδικότητα "
								+ secondment.getEmployee().getLastSpecialization() + " και ομαδοποιημένη ειδικότητα "
								+ employeesMappedSpecializationGroup.getTitle()
								+ " που έχει αποπαστεί στην μονάδα με την απόσπαση " + secondment
								+ " προσφέρει στην μονάδα συνολικά " + secondment.getFinalWorkingHours()
								+ " διδακτικές ώρες.");

				}

				reportData.add(item);

			}

			/* first fetch the teaching requirement */

			finished = System.currentTimeMillis();

			Map<SpecializationGroup, SpecializationGroupVoidAnalysis> reportDataBySpecializationMap = new HashMap<SpecializationGroup, SpecializationGroupVoidAnalysis>(
					100);
			for (SchoolVoidAnalysisItem schoolVoid : reportData) {
				for (TeachingResource resource : schoolVoid.getTeachingResources()) {

					if (resource.getRequired() == 0 && resource.getAvailable() == 0)
						continue;

					SpecializationGroupVoidAnalysis groupResources = reportDataBySpecializationMap.get(resource
							.getSpecializationGroup());
					if (groupResources == null) {
						groupResources = new SpecializationGroupVoidAnalysis();
						groupResources.setSpecializationGroup(resource.getSpecializationGroup());
						reportDataBySpecializationMap.put(resource.getSpecializationGroup(), groupResources);
					}
					SpecializationGroupVoidAnalysisItem item = new SpecializationGroupVoidAnalysisItem(schoolVoid
							.getSchool(), resource);
					groupResources.addTeachingResource(item);

				}
			}
			reportDataBySpecialization = new ArrayList<SpecializationGroupVoidAnalysis>(reportDataBySpecializationMap
					.values());
			for(SpecializationGroupVoidAnalysis g : reportDataBySpecialization) {
				g.sort();
			}
			Collections.sort(((List<SpecializationGroupVoidAnalysis>) reportDataBySpecialization));
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

	/**
	 * @return the reportDataBySpecialization
	 */
	public Collection<SpecializationGroupVoidAnalysis> getReportDataBySpecialization() {
		return reportDataBySpecialization;
	}

	/**
	 * @param reportDataBySpecialization the reportDataBySpecialization to set
	 */
	public void setReportDataBySpecialization(Collection<SpecializationGroupVoidAnalysis> reportDataBySpecialization) {
		this.reportDataBySpecialization = reportDataBySpecialization;
	}

	/**
	 * @return the reportTextAnalysis
	 */
	public Collection<String> getReportTextAnalysis() {
		return reportTextAnalysis;
	}

	/**
	 * @param reportTextAnalysis the reportTextAnalysis to set
	 */
	public void setReportTextAnalysis(Collection<String> reportTextAnalysis) {
		this.reportTextAnalysis = reportTextAnalysis;
	}

	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}

	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}

	

}
