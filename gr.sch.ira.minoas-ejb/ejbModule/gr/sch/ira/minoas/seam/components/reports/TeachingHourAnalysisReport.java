package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
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
import java.util.Iterator;
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

	@DataModel(value = "reportData")
	private Collection<TeachingHoursAnalysisItem> reportData;

	public void generateReport() {

		boolean useTextAnalysis = (school != null);
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

			info("****** WE WILL USE THE FOLLOWING SPECIALIZATIONS : #0", reportSpecializationGroups);
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

			/* according to school & specialization selection, construct an empty report skeleton */
			if (getSpecializationGroupSearchType() == SpecializationGroupSearchType.MULTIPLE_SPECIALIZATION_GROUPS) {
				/* Since the specialization group search type is MULTIPLE, then the report will contain
				 * one single instance with the selected specialization groups 
				 */
				Collection<TeachingHoursAnalysisItem> r = new ArrayList<TeachingHoursAnalysisItem>();
				TeachingHoursAnalysisItem item = new TeachingHoursAnalysisItem(schools, reportSpecializationGroups);
				r.add(item);
				setReportData(r);
			} else {
				/* Since the specialization group search type is *NOT* MULTIPLE, then the report will
				 * contain either one instance with the selected specialization groups or all specialization
				 * groups, each in it's private instance.
				 */
				Collection<TeachingHoursAnalysisItem> r = new ArrayList<TeachingHoursAnalysisItem>(
						reportSpecializationGroups.size());
				for (SpecializationGroup specializationGroup : reportSpecializationGroups) {
					r.add(new TeachingHoursAnalysisItem(schools, specializationGroup));
				}
				setReportData(r);
			}

			/* Create a cache map for fast retrieving a requirment (in a school) based on
			 * the specialization group */

			Map<String, Map<SpecializationGroup, TeachingRequirement>> schoolsTeachingRequirementMap = new HashMap<String, Map<SpecializationGroup, TeachingRequirement>>(
					schools.size());
			for (School school : schools) {
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
				schoolsTeachingRequirementMap.put(school.getId(), schoolTeachingRequirementMap);
			}
			for (Iterator<TeachingHoursAnalysisItem> it = getReportData().iterator(); it.hasNext();) {

				TeachingHoursAnalysisItem reportItem = it.next();
				for (SchoolTeachingHoursItem item : reportItem.getSchools()) {
					School schoolItem = item.getSchool();

					int requiredHours = 0;
					int hours = 0;

					/* calculate the required hours */
					for (SpecializationGroup specializationGroup : reportItem.getSpecializationGroups()) {
						TeachingRequirement requirement = schoolsTeachingRequirementMap.get(schoolItem.getId()).get(
								specializationGroup);
						if (requirement != null)
							requiredHours += requirement.getHours();
					}
					reportItem.addSchoolRequiredHours(schoolItem, requiredHours);

					/* for the given school, get all regular employees with a compatible specialization */

					Collection<Employee> regularEmployees = getCoreSearching().getSchoolActiveEmployees(
							getEntityManager(), schoolItem, activeSchoolYear, today,
							reportItem.getSpecializationGroups());

					for (Employee regularEmployee : regularEmployees) {
						int employeeHours = regularEmployee.getCurrentEmployment().getFinalWorkingHours();
						Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
								regularEmployee, today);
						int disposalHours = 0;
						for (Disposal disposal : disposals) {
							disposalHours += disposal.getHours();
						}
						hours += (employeeHours - disposalHours);
						if (useTextAnalysis) {
							if (disposalHours > 0) {
								reportTextAnalysis.add("Ο εκπαιδευτικός " + regularEmployee.toPrettyString() + " ("
										+ getLocalizedMessage(regularEmployee.getType().getKey())
										+ ") προσφέρει στην μονάδα " + (employeeHours - disposalHours) + " (["
										+ employeeHours + " - " + disposalHours
										+ "] λόγω διάθεσης ) ωρες με ειδικότητα "
										+ regularEmployee.getLastSpecialization());

							} else {
								reportTextAnalysis.add("Ο εκπαιδευτικός " + regularEmployee.toPrettyString() + " ("
										+ regularEmployee.getType() + ") προσφέρει στην μονάδα " + employeeHours
										+ " ωρες με ειδικότητα " + regularEmployee.getLastSpecialization());
							}
						}
					}

					Collection<Secondment> schoolSecondments = getCoreSearching().getSchoolSecondments(
							getEntityManager(), schoolItem, activeSchoolYear, today,
							reportItem.getSpecializationGroups());

					for (Secondment secondment : schoolSecondments) {
						int employeeHours = secondment.getFinalWorkingHours();
						hours += employeeHours;
						if (useTextAnalysis) {
							reportTextAnalysis.add("Ο εκπαιδευτικός " + secondment.getEmployee().toPrettyString()
									+ " (" + getLocalizedMessage(secondment.getEmployee().getType().getKey())
									+ ") που υπηρετεί στην μονάδα με απόσπαση απο το \""
									+ secondment.getSourceUnit().getTitle() + "\", προσφέρει " + employeeHours
									+ " ωρες με ειδικότητα " + secondment.getEmployee().getLastSpecialization());
						}
					}

					/* handle service allocation that are serving here */
					Collection<ServiceAllocation> schoolIncomingServiceAllocations = getCoreSearching()
							.getSchoolIncomingServiceAllocations(getEntityManager(), schoolItem, today,
									reportItem.getSpecializationGroups());

					for (ServiceAllocation serviceAllocation : schoolIncomingServiceAllocations) {
						int employeeHours = serviceAllocation.getWorkingHoursOnServicingPosition();
						hours += employeeHours;
						if (useTextAnalysis) {
							reportTextAnalysis.add("Ο εκπαιδευτικός "
									+ serviceAllocation.getEmployee().toPrettyString()
									+ " που υπηρετεί στην μονάδα με θητεία \""
									+ getLocalizedMessage(serviceAllocation.getServiceType().getKey())
									+ "\", προσφέρει " + employeeHours + " ωρες με ειδικότητα "
									+ serviceAllocation.getEmployee().getLastSpecialization());
						}
					}

					/* handle service allocations that are still serving teaching hours
					 * in their regular school
					 */
					Collection<ServiceAllocation> schoolOutgoingServiceAllocations = getCoreSearching()
							.getSchoolOutgoingServiceAllocations(getEntityManager(), schoolItem, today,
									reportItem.getSpecializationGroups());

					for (ServiceAllocation serviceAllocation : schoolOutgoingServiceAllocations) {
						int employeeHours = serviceAllocation.getWorkingHoursOnRegularPosition();
						if (employeeHours > 0) {
							hours += employeeHours;
							if (useTextAnalysis) {
								reportTextAnalysis.add("Ο εκπαιδευτικός "
										+ serviceAllocation.getEmployee().toPrettyString()
										+ " που υπηρετεί στην μονάδα \""
										+ serviceAllocation.getServiceUnit().getTitle() + "\" με θητεία \""
										+ getLocalizedMessage(serviceAllocation.getServiceType().getKey())
										+ "\", προσφέρει στην οργανική του μονάδα συνολικά " + employeeHours
										+ " ωρες με ειδικότητα "
										+ serviceAllocation.getEmployee().getLastSpecialization());
							}
						} else
							continue;
					}

					Collection<Disposal> schoolDisposals = getCoreSearching().getSchoolDisposals(getEntityManager(),
							schoolItem, activeSchoolYear, today, reportItem.getSpecializationGroups());

					for (Disposal disposal : schoolDisposals) {
						int employeeHours = disposal.getHours();
						hours += employeeHours;
						if (useTextAnalysis) {
							reportTextAnalysis.add("Ο εκπαιδευτικός " + disposal.getEmployee().toPrettyString()
									+ " που υπηρετεί στην μονάδα με διάθεση προσφέρει " + employeeHours
									+ " ωρες με ειδικότητα " + disposal.getEmployee().getLastSpecialization());
						}
					}

					reportItem.addSchoolAvailableHours(schoolItem, hours);

				}
				reportItem.removeItemsWithZeroHours();
				if (reportItem.getSchools().size() == 0) {
					it.remove();
				}
			}

			schoolsTeachingRequirementMap.clear();

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
	public Collection<TeachingHoursAnalysisItem> getReportData() {
		return reportData;
	}

	/**
	 * @param reportData2 the reportData2 to set
	 */
	public void setReportData(Collection<TeachingHoursAnalysisItem> reportData2) {
		this.reportData = reportData2;
	}

}
