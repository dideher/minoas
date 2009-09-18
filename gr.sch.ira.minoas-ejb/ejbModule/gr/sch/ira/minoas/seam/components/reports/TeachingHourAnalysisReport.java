package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationGroupSearchType;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolTeachingHoursItem;
import gr.sch.ira.minoas.seam.components.reports.resource.TeachingHoursAnalysisItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

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

	private List<SpecializationGroup> specializationGroups = new ArrayList<SpecializationGroup>();

	private SpecializationGroupSearchType specializationGroupSearchType = SpecializationGroupSearchType.SINGLE_SPECIALIZATION_GROUP;

	@DataModel(value = "reportTextAnalysis")
	private Collection<String> reportTextAnalysis = new ArrayList<String>(100);

	@DataModel(value = "reportData2")
	private Collection<TeachingHoursAnalysisItem> reportData2;

	public void generateReport() {

		boolean useTextAnalysis = false;
		try {
			long started = System.currentTimeMillis(), finished;
			Date today = DateUtils.truncate(effectiveDay, Calendar.DAY_OF_MONTH);

			info("generating report ");

			/* get the active school year */
			SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

			/* According to the user's request, construct an array of all specialization groups
			 * that need to appear in the report
			 */
			Collection<SpecializationGroup> reportSpecializationGroups = null;
			if (getSpecializationGroupSearchType() == SpecializationGroupSearchType.SINGLE_SPECIALIZATION_GROUP) {
				/* One specialization group type requested, therefore the report will contain 
				 * either the user's selected specialization group or all specialization groups if
				 * the user left the field unspecified
				 */
				if (getSpecializationGroup() != null) {
					/* user has specified a concrete specialization group */
					reportSpecializationGroups = new ArrayList<SpecializationGroup>(1);
					reportSpecializationGroups.add(getSpecializationGroup());
				} else {
					/* user did not specified a concrete specialization group,
					 * so fetch all groups
					 */
					reportSpecializationGroups = getCoreSearching().getSpecializationGroups(activeSchoolYear,
							getEntityManager());
				}
			} else {
				/* user has selected many specialization groups */
				reportSpecializationGroups = new ArrayList<SpecializationGroup>(getSpecializationGroups().size());
				reportSpecializationGroups.addAll(getSpecializationGroups());
			}

			/* Construct a hash so we can quickly find a specializationGroup from the employee's specialization. */
			Collection<SpecializationGroup> allSpecializationGroups = getCoreSearching().getSpecializationGroups(
					activeSchoolYear, getEntityManager());
			HashMap<Specialization, SpecializationGroup> specializationGroupMap = new HashMap<Specialization, SpecializationGroup>();
			for (SpecializationGroup specializationGroup : allSpecializationGroups) {
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

			if (getSpecializationGroupSearchType() == SpecializationGroupSearchType.MULTIPLE_SPECIALIZATION_GROUPS) {
				/* Since the specialization group search type is MULTIPLE, then the report will contain
				 * one single instance with the selected specialization groups 
				 */
				Collection<TeachingHoursAnalysisItem> r = new ArrayList<TeachingHoursAnalysisItem>();
				TeachingHoursAnalysisItem item = new TeachingHoursAnalysisItem(reportSpecializationGroups);
				r.add(item);
				setReportData2(r);
			} else {
				/* Since the specialization group search type is *NOT* MULTIPLE, then the report will
				 * contain either one instance with the selected specialization groups or all specialization
				 * groups, each in it's private instance.
				 */
				Collection<TeachingHoursAnalysisItem> r = new ArrayList<TeachingHoursAnalysisItem>(
						reportSpecializationGroups.size());
				for (SpecializationGroup specializationGroup : reportSpecializationGroups) {
					r.add(new TeachingHoursAnalysisItem(specializationGroup));
				}
				setReportData2(r);
			}

			for (TeachingHoursAnalysisItem reportItem : getReportData2()) {
				/* iterate through all schools */
				for (School school : schools) {

					SchoolTeachingHoursItem schoolItem = new SchoolTeachingHoursItem();
					schoolItem.setSchool(school);

					/******************** we need to cache this *************************/
					/* fetch the teaching requirement for the given school */
					Collection<TeachingRequirement> schoolTeachingRequirments = getCoreSearching()
							.getSchoolTeachingRequirement(getEntityManager(), school, activeSchoolYear);

					/* 
					 * construct a map so we can easily find a teaching requirement according to the
					 * specializationGroup
					 */
					Map<SpecializationGroup, TeachingRequirement> schoolTeachingRequirementMap = new HashMap<SpecializationGroup, TeachingRequirement>(
							schoolTeachingRequirments.size());
					for (TeachingRequirement requirement : schoolTeachingRequirments) {
						schoolTeachingRequirementMap.put(requirement.getSpecialization(), requirement);
					}
					/******************** we need to cache this *************************/

					int requiredHours = 0;
					for (SpecializationGroup specializationGroup : reportItem.getSpecializationGroups()) {
						TeachingRequirement requirement = schoolTeachingRequirementMap.get(specializationGroup);
						if (requirement != null)
							requiredHours += requirement.getHours();
					}

					schoolItem.setRequiredHours(requiredHours);

					/* for the given school, get all regular employees with a compatible specialization */

					Collection<Employee> regularEmployees = getCoreSearching().getSchoolRegularEmployees(
							getEntityManager(), school, activeSchoolYear, today, reportSpecializationGroups);

					int hours = 0;
					for (Employee regularEmployee : regularEmployees) {
						hours += regularEmployee.getCurrentEmployment().getFinalWorkingHours();
					}

					Collection<Secondment> schoolSecondments = getCoreSearching().getSchoolSecondments(
							getEntityManager(), school, activeSchoolYear, today, reportSpecializationGroups);

					for (Secondment secondment : schoolSecondments) {
						hours += secondment.getFinalWorkingHours();
					}

					Collection<ServiceAllocation> schoolServiceAllocations = getCoreSearching()
							.getSchoolServiceAllocations(getEntityManager(), school, today, reportSpecializationGroups);

					for (ServiceAllocation serviceAllocation : schoolServiceAllocations) {
						hours += serviceAllocation.getWorkingHoursOnServicingPosition();
					}

					schoolItem.setAvailableHours(hours);
					if (!(hours == 0 && requiredHours == 0))
						reportItem.addSchoolItem(schoolItem);
				}
			}

			/* first fetch the teaching requirement */

			finished = System.currentTimeMillis();
			info("report has been generated in #0 [ms]", (finished - started));
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

	/**
	 * @return the specializationGroupSearchType
	 */
	public SpecializationGroupSearchType getSpecializationGroupSearchType() {
		return specializationGroupSearchType;
	}

	/**
	 * @param specializationGroupSearchType the specializationGroupSearchType to set
	 */
	public void setSpecializationGroupSearchType(SpecializationGroupSearchType specializationGroupSearchType) {
		this.specializationGroupSearchType = specializationGroupSearchType;
	}

	/**
	 * @return the specializationGroups
	 */
	public List<SpecializationGroup> getSpecializationGroups() {
		return specializationGroups;
	}

	/**
	 * @param specializationGroups the specializationGroups to set
	 */
	public void setSpecializationGroups(List<SpecializationGroup> specializationGroups) {
		this.specializationGroups = specializationGroups;
	}

	/**
	 * @return the reportData2
	 */
	public Collection<TeachingHoursAnalysisItem> getReportData2() {
		return reportData2;
	}

	/**
	 * @param reportData2 the reportData2 to set
	 */
	public void setReportData2(Collection<TeachingHoursAnalysisItem> reportData2) {
		this.reportData2 = reportData2;
	}

}
