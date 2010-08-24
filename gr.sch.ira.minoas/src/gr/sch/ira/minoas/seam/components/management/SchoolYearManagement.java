package gr.sch.ira.minoas.seam.components.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.SchoolHome;
import gr.sch.ira.minoas.seam.components.home.SchoolYearHome;
import gr.sch.ira.minoas.seam.components.reports.resource.TeachingResource;

@Name(value = "schoolYearManagement")
@Scope(ScopeType.CONVERSATION)
public class SchoolYearManagement extends BaseDatabaseAwareSeamComponent {

	@DataModel
	private Collection<SchoolYear> schoolYears;

	@In(required = true, create = true)
	private SchoolYearHome schoolYearHome;

	@In(required = true, create = true)
	private CoreSearching coreSearching;

	private String newSchoolYearYear;

	@Transactional
	public String createNewSchoolYear() {
		if (!getSchoolYearHome().isManaged()) {
			info("creating new school year #0", getNewSchoolYearYear());
			int schoolYearInt = Integer.parseInt(getNewSchoolYearYear());
			SchoolYear instance = getSchoolYearHome().getInstance();
			instance.setDescription("Σχολικό Έτος " + schoolYearInt + "-" + (schoolYearInt + 1));
			instance.setTitle(schoolYearInt + "-" + (schoolYearInt + 1));
			instance.setYear(new Integer(schoolYearInt));
			instance.setCurrentSchoolYear(false);
			String result = getSchoolYearHome().persist();
			fetchSchoolYears();
			return result;
		} else {
			getFacesMessages().add(Severity.ERROR, "school year home is managed", (Object[]) null);
			return null;
		}
	}

	@Transactional
	public void doUpgrade() {
		if (getSchoolYearHome().isManaged()) {
			EntityManager em = getEntityManager();
			SchoolYear sourceSchoolYear = coreSearching.getActiveSchoolYear(em);
			SchoolYear targetSchoolYear = getSchoolYearHome().getInstance();
			info("upgrading school year #0 to #1", sourceSchoolYear, targetSchoolYear);
			
			
			//if (true)
			//	return;
			info("upgrading/handling teaching requirment.");
			Collection<TeachingRequirement> teachingRequirements = coreSearching.getTeachingRequirement(em,
					sourceSchoolYear);
			info("found #0 teaching requirment(s) for upgrading/handling.", teachingRequirements.size());
			for (TeachingRequirement requirement : teachingRequirements) {
				TeachingRequirement newRequirement = new TeachingRequirement();
				newRequirement.setInsertedBy(null);
				newRequirement.setComment(requirement.getComment());
				newRequirement.setHours(requirement.getHours());
				newRequirement.setSchoolYear(targetSchoolYear);
				newRequirement.setSpecialization(requirement.getSpecialization());
				newRequirement.setSchool(requirement.getSchool());
				em.persist(newRequirement);
			}
			
			Collection<SpecializationGroup> groups = coreSearching.getSpecializationGroups(sourceSchoolYear, em);
			info("upgrading/handling specialization groups.");
			for (SpecializationGroup group : groups) {
				SpecializationGroup newGroup = new SpecializationGroup();
				newGroup.setInsertedBy(group.getInsertedBy());
				newGroup.setSchoolYear(targetSchoolYear);
				newGroup.getSpecializations().addAll(group.getSpecializations());
				newGroup.setTitle(group.getTitle());
				em.persist(newGroup);
			}

			info("upgrading/handling hourly employment(s).");
			/* handle hourly based employments -> not upgraded */
			Collection<Employment> employments = coreSearching.getEmploymentsOfType(EmploymentType.HOURLYBASED,
					sourceSchoolYear);
			info("found #0 hourly employment(s) for upgrading/handling.", employments.size());
			for (Employment hourlyEmployment : employments) {
				Employee e = hourlyEmployment.getEmployee();
				if (e.getCurrentEmployment() != null) {
					e.setCurrentEmployment(null);
					info("hourly employment #0 is no longer marked as current for employee #1", hourlyEmployment, e);
				}
			}

			info("upgrading/handling deputy employment(s).");
			/* handle deputy based employments -> not upgraded */
			employments = coreSearching.getEmploymentsOfType(EmploymentType.DEPUTY, sourceSchoolYear);
			info("found #0 deputy employment(s) for upgrading/handling.", employments.size());
			for (Employment deputyEmployment : employments) {
				Employee e = deputyEmployment.getEmployee();
				if (e.getCurrentEmployment() != null) {
					e.setCurrentEmployment(null);
					deputyEmployment.setTerminated(sourceSchoolYear.getTeachingSchoolYearStop());
					info("deputy employment #0 is no longer marked as current for employee #1", deputyEmployment, e);
				}
			}

			/* handle service allocations */
			info("upgrading/handling service allocation(s).");
			Collection<ServiceAllocation> activeServiceAllocations = coreSearching.getActiveServiceAllocations(em);
			info("found #0 active service allocations.", activeServiceAllocations.size());
			Date serviceAllocationDueDate = targetSchoolYear.getSchoolYearStart();
			for (ServiceAllocation serviceAllocation : activeServiceAllocations) {
				if (serviceAllocation.getDueTo().before(serviceAllocationDueDate)
						|| serviceAllocation.getDueTo().equals(serviceAllocationDueDate)) {
					// service allocation should be marked as not-active
					info("service allocation #0 with due date #1, will be marked as not active.", serviceAllocation,
							serviceAllocation.getDueTo());
					serviceAllocation.setActive(false);
				}
			}
			/* handle secondments */
			info("upgrading/handling secondment(s).");
			Collection<Secondment> activeSecondments = coreSearching.getActiveSecondments(em);
			info("found #0 active secondment(s).", activeSecondments.size());
			Date secondmentDueDate = targetSchoolYear.getSchoolYearStart();
			for (Secondment secondment : activeSecondments) {
				if (secondment.getDueTo().before(secondmentDueDate) || secondment.getDueTo().equals(secondmentDueDate)) {
					// service allocation should be marked as not-active
					info("secondment #0 with due date #1, will be marked as not active.", secondment, secondment
							.getDueTo());
					secondment.setActive(false);
				}
			}
			
			/* handle regular employments */
			info("upgrading/handling regular employment(s).");
			
			/* before handling regular employments, we need to exclude employments that are
			 * associated with outstaning improvements & transfers
			 */
			Collection<Employee> excludeEmployees = new ArrayList<Employee>(100);
			
			Collection<Employee> e1 = coreSearching.getEmployeesWithUnProcessedOutstandingImprovements(em, sourceSchoolYear);
			Collection<Employee> e2 = coreSearching.getEmployeesWithUnProcessedPermanentTransfers(em, sourceSchoolYear);
			if(e1!=null)
				excludeEmployees.addAll(e1);
			if(e2!=null)
				excludeEmployees.addAll(e2);
			
			employments = coreSearching.getEmploymentsOfType(EmploymentType.REGULAR, sourceSchoolYear,excludeEmployees);
			info("found #0 regular employment(s) for upgrading/handling.", employments.size());
			for (Employment regularEmployment : employments) {
				Employment newEmployment = new Employment();
				newEmployment.setActive(Boolean.TRUE);
				newEmployment.setEstablished(targetSchoolYear.getSchoolYearStart());
				newEmployment.setFinalWorkingHours(regularEmployment.getFinalWorkingHours());
				newEmployment.setMandatoryWorkingHours(regularEmployment.getMandatoryWorkingHours());
				newEmployment.setType(regularEmployment.getType());
				newEmployment.setSchool(regularEmployment.getSchool());
				newEmployment.setSchoolYear(targetSchoolYear);
				newEmployment.setSpecialization(regularEmployment.getSpecialization());
				newEmployment.setEmployee(regularEmployment.getEmployee());
				
				regularEmployment.getEmployee().getEmployments().add(newEmployment);
				regularEmployment.getEmployee().setCurrentEmployment(newEmployment);
				regularEmployment.setTerminated(sourceSchoolYear.getSchoolYearStop());
				regularEmployment.setActive(Boolean.FALSE);
				regularEmployment.setSupersededBy(newEmployment);
				em.persist(newEmployment);
			}
			
			/* now handle the improvements */
			Collection<OutstandingImprovement> improvements = coreSearching.getUnProcessedOutstandingImprovements(em, sourceSchoolYear);
			info("found #0 improvements for upgrading/handling.", improvements.size());
			for(OutstandingImprovement improvement : improvements) {
				Employment regularEmployment = improvement.getEmployee().getCurrentEmployment();
				Employment newEmployment = new Employment();
				newEmployment.setActive(Boolean.TRUE);
				newEmployment.setEstablished(targetSchoolYear.getSchoolYearStart());
				newEmployment.setFinalWorkingHours(regularEmployment.getFinalWorkingHours());
				newEmployment.setMandatoryWorkingHours(regularEmployment.getMandatoryWorkingHours());
				newEmployment.setType(regularEmployment.getType());
				newEmployment.setSchool(improvement.getTargetSchool());
				newEmployment.setSchoolYear(targetSchoolYear);
				newEmployment.setSpecialization(regularEmployment.getSpecialization());
				newEmployment.setEmployee(regularEmployment.getEmployee());
				
				regularEmployment.getEmployee().getEmployments().add(newEmployment);
				regularEmployment.getEmployee().setCurrentEmployment(newEmployment);
				regularEmployment.setTerminated(sourceSchoolYear.getSchoolYearStop());
				regularEmployment.setActive(Boolean.FALSE);
				regularEmployment.setSupersededBy(newEmployment);
				
				improvement.setIsProcessed(Boolean.TRUE);
				em.persist(newEmployment);
			}
			
			/* now handle transfers */
			Collection<PermanentTransfer> transfers = coreSearching.getgetUnProcessedPermanentTransfers(em, sourceSchoolYear);
			info("found #0 transfers for upgrading/handling.", transfers.size());
			for(PermanentTransfer transfer : transfers) {
				if(transfer.getEmployee()!=null && transfer.getType()==PermanentTransferType.WITHIN_PYSDE) {
					Employment regularEmployment = transfer.getEmployee().getCurrentEmployment();
					Employment newEmployment = new Employment();
					newEmployment.setActive(Boolean.TRUE);
					newEmployment.setEstablished(targetSchoolYear.getSchoolYearStart());
					newEmployment.setFinalWorkingHours(regularEmployment.getFinalWorkingHours());
					newEmployment.setMandatoryWorkingHours(regularEmployment.getMandatoryWorkingHours());
					newEmployment.setType(regularEmployment.getType());
					
					newEmployment.setSchool(em.find(School.class, transfer.getTargetUnit().getId()));
					newEmployment.setSchoolYear(targetSchoolYear);
					newEmployment.setSpecialization(regularEmployment.getSpecialization());
					newEmployment.setEmployee(regularEmployment.getEmployee());
					
					regularEmployment.getEmployee().getEmployments().add(newEmployment);
					regularEmployment.getEmployee().setCurrentEmployment(newEmployment);
					regularEmployment.setTerminated(sourceSchoolYear.getSchoolYearStop());
					regularEmployment.setActive(Boolean.FALSE);
					regularEmployment.setSupersededBy(newEmployment);
					
					transfer.setIsProcessed(Boolean.TRUE);
					em.persist(newEmployment);
				}
				
			}
			em.flush();
			fetchSchoolYears();
		} else {
			getFacesMessages().add(Severity.ERROR, "school year home is NOT managed", (Object[]) null);
			return;
		}

	}

	public void fetchSchoolYears() {
		this.schoolYears = coreSearching.getSchoolYears(getEntityManager());
	}

	/**
	 * @return the schoolYears
	 */

	public Collection<SchoolYear> getSchoolYears() {
		return schoolYears;
	}

	/**
	 * @param schoolYears the schoolYears to set
	 */
	public void setSchoolYears(Collection<SchoolYear> schoolYears) {
		this.schoolYears = schoolYears;
	}

	/**
	 * @return the newSchoolYearYear
	 */
	public String getNewSchoolYearYear() {
		return newSchoolYearYear;
	}

	/**
	 * @param newSchoolYearYear the newSchoolYearYear to set
	 */
	public void setNewSchoolYearYear(String newSchoolYearYear) {
		this.newSchoolYearYear = newSchoolYearYear;
	}

	/**
	 * @return the schoolYearHome
	 */
	public SchoolYearHome getSchoolYearHome() {
		return schoolYearHome;
	}

	/**
	 * @param schoolYearHome the schoolYearHome to set
	 */
	public void setSchoolYearHome(SchoolYearHome schoolYearHome) {
		this.schoolYearHome = schoolYearHome;
	}

}
