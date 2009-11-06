package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.EstablishmentLocation;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.employement.DisposalType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;
import gr.sch.ira.minoas.model.preparatory.TeachingLanguage;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationGroupSearchType;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

@Name("coreSearching")
@Scope(ScopeType.EVENT)
@AutoCreate
public class CoreSearching extends BaseDatabaseAwareSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Role findRole(String roleID) {
		return getEntityManager().find(Role.class, roleID);
	}

	public SchoolYear getActiveSchoolYear(EntityManager entityManager) {
		try {
			debug("trying to find active school year");
			EntityManager em = getEntityManager(entityManager);
			return (SchoolYear) em.createQuery("SELECT s from SchoolYear s WHERE s.currentSchoolYear IS TRUE")
					.getSingleResult();
		} catch (NoResultException nre) {
			warn("no active school year found");
			return null;
		}
	}

	@Factory(value = "dateSearchTypes")
	public DateSearchType[] getAvailableDateSearchTypes() {
		return DateSearchType.values();
	}

	@Factory(value = "disposalTargetTypes")
	public DisposalTargetType[] getAvailableDisposalTargetTypes() {
		return DisposalTargetType.values();
	}

	@Factory(value = "disposalTypes")
	public DisposalType[] getAvailableDisposalTypes() {
		return DisposalType.values();
	}

	@Factory(value = "establishmentLicenseStatusTypes")
	public EstablishmentLicenseStatusType[] getAvailableEstablishmentLicenseStatusTypes() {
		return EstablishmentLicenseStatusType.values();
	}

	@SuppressWarnings("unchecked")
	@Factory(value = "establishmentLocations")
	public Collection<EstablishmentLocation> getAvailableEstablishments() {
		return getEntityManager().createQuery("FROM EstablishmentLocation e ORDER BY (e.title)").getResultList();
	}

	@Factory(value = "leaveTypes")
	public LeaveType[] getAvailableLeaveTypes() {
		return LeaveType.values();
	}

	@SuppressWarnings("unchecked")
	public List<OrganizationalOffice> getAvailableOrganizationalOffices() {
		debug("fetching all available organizational offices");
		List return_value = getEntityManager().createQuery("SELECT r from OrganizationalOffice r").getResultList();
		debug("found totally #0 organizational office(s).", return_value.size());
		return return_value;
	}

	@Factory(value = "preparatoryUnitNatureTypes")
	public PreparatoryUnitNatureType[] getAvailablePreparatoryNatures() {
		return PreparatoryUnitNatureType.values();
	}

	@SuppressWarnings("unchecked")
	public List<Role> getAvailableRoles() {
		debug("fetching all available groups");
		List return_value = getEntityManager().createQuery("SELECT r from Role r").getResultList();
		debug("found totally #0 role(s).", return_value.size());
		return return_value;

	}

	public List<SchoolYear> getAvailableSchoolYears() {
		return getAvailableSchoolYears(null);
	}

	@SuppressWarnings("unchecked")
	public List<SchoolYear> getAvailableSchoolYears(EntityManager entityManager) {
		debug("fetching all available school years");
		EntityManager em = getEntityManager(entityManager);
		List<SchoolYear> return_value = em.createQuery("SELECT r from SchoolYear r").getResultList();
		debug("found totally #0 school years(s).", return_value.size());
		return return_value;
	}

	@Factory(value = "secondmentTypes")
	public SecondmentType[] getAvailableSecondmentTypes() {
		return SecondmentType.values();
	}

	@Factory(value = "serviceAllocationTypes")
	public ServiceAllocationType[] getAvailableServiceAllocationTypes() {
		return ServiceAllocationType.values();
	}

	@SuppressWarnings("unchecked")
	@Factory(value = "teachingLanguages")
	public Collection<TeachingLanguage> getAvailableTeachingLanguages() {
		return getEntityManager().createQuery("FROM TeachingLanguage e ORDER BY (e.language)").getResultList();
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public Employee getEmployeeByVatNumber(EntityManager entityManager, String vatNumber) {
		EntityManager em = getEntityManager(entityManager);
		try {
			return (Employee) em.createQuery("SELECT e from Employee e WHERE e.vatNumber=:vatNumber").setParameter(
					"vatNumber", vatNumber).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public Secondment getEmployeeActiveSecondment(EntityManager entityManager, Person employee, Date onDate) {
		EntityManager em = getEntityManager(entityManager);
		try {
			return (Secondment) em
					.createQuery(
							"SELECT s from Secondment s WHERE s.active IS TRUE AND s.employee=:employee AND :onDate BETWEEN s.established AND s.dueTo ORDER BY s.insertedOn")
					.setParameter("employee", employee).setParameter("onDate", onDate).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public Leave getEmployeeActiveLeave(EntityManager entityManager, Person employee, Date onDate) {
		EntityManager em = getEntityManager(entityManager);

		try {
			return (Leave) em.createQuery(
					"SELECT s from Leave s WHERE s.active IS TRUE AND s.employee=:employee AND :onDate BETWEEN s.established AND s.dueTo ORDER BY s.established")
					.setParameter("employee", employee).setParameter("onDate", onDate).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransactionPropagationType.REQUIRED)
	public Collection<Disposal> getEmployeeActiveDisposals(EntityManager em, Person employee, Date dayOfIntereset) {

		Collection<Disposal> result = null;
		result = getEntityManager(em)
				.createQuery(
						"SELECT s FROM Disposal s WHERE s.active IS TRUE AND s.employee=:employee AND :dayOfInterest BETWEEN s.established AND s.dueTo ORDER BY s.insertedOn")
				.setParameter("employee", employee).setParameter("dayOfInterest", dayOfIntereset).getResultList();
		return result;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	@SuppressWarnings("unchecked")
	public Collection<Employment> getEmployeeEmployments(Person employee) {
		List<Employment> result;
		debug("trying to featch all  employments for employee '#0'", employee);
		result = entityManager.createQuery(
				"SELECT e from Employment e WHERE e.employee=:employee ORDER BY e.schoolYear.title").setParameter(
				"employee", employee).getResultList();
		info("found totally '#0' employments for employee '#1'.", result.size(), employee);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransactionPropagationType.REQUIRED)
	public Collection<Employment> getEmployeeEmployments(Person employee, SchoolYear schoolyear) {
		List<Employment> result;
		debug("trying to featch all  employments for employee '#0' during school year '#1'.", employee, schoolyear);
		result = entityManager.createQuery(
				"SELECT e from Employment e WHERE e.employee=:employee AND e.schoolYear=:schoolyear").setParameter(
				"employee", employee).setParameter("schoolyear", schoolyear).getResultList();
		info("found totally '#0' employments for regular employee '#1' during school year '#2'.", result.size(),
				employee, schoolyear);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransactionPropagationType.REQUIRED)
	public Collection<Employment> getEmployeeEmploymentsOfType(Person employee, EmploymentType type) {
		List<Employment> result;
		debug("trying to featch all  employments for employee '#0'", employee);
		result = entityManager.createQuery(
				"SELECT e from Employment e WHERE e.employee=:employee AND e.type=:type ORDER BY e.schoolYear.title")
				.setParameter("employee", employee).setParameter("type", type).getResultList();
		info("found totally '#0' employments for regular employee '#1'.", result.size(), employee);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransactionPropagationType.REQUIRED)
	public Collection<Leave> getEmployeeLeaves(Person employee) {
		Collection<Leave> result = null;
		info("searching employee's '#0' leaves.", employee);
		result = entityManager.createQuery(
				"SELECT s from Leave s WHERE s.active IS TRUE AND s.employee=:employee ORDER BY s.established")
				.setParameter("employee", employee).getResultList();
		info("found totally '#0' leave(s) for employee '#1'.", result.size(), employee);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Collection<Secondment> getEmployeeSecondments(Person employee) {
		Collection<Secondment> result = null;
		info("searching employee's '#0' secondments.", employee);
		result = entityManager.createQuery(
				"SELECT s from Secondment s WHERE s.active IS TRUE AND s.employee=:employee ORDER BY s.insertedOn")
				.setParameter("employee", employee).getResultList();
		info("found totally '#0' secondments for employee '#1'.", result.size(), employee);
		return result;
	}

	@Factory(value = "employeeTypes")
	public EmployeeType[] getEmployeeTypes() {
		return EmployeeType.values();
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected EntityManager getEntityManager(EntityManager entityManager) {
		return entityManager != null ? entityManager : getEntityManager();
	}

	public PYSDE getLocalPYSDE(EntityManager entityManager) {
		return (PYSDE) getEntityManager(entityManager).createQuery("SELECT p FROM PYSDE p WHERE p.localPYSDE IS TRUE")
				.getSingleResult();
	}

	public Principal getPrincipal(EntityManager entityManager, String username) {
		try {
			EntityManager em = getEntityManager(entityManager);
			return (Principal) em.createQuery("SELECT OBJECT(p) FROM Principal p WHERE p.username=:username")
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public Collection<Employee> getSchoolActiveEmployees(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense) {
		return getSchoolActiveEmployees(em, school, schoolYear, dayOfPrecense, (SpecializationGroup) null);
	}

	public Collection<Employee> getSchoolActiveEmployeesOfEmploymentType(EntityManager em, School school,
			SchoolYear schoolYear, Date dayOfPrecense, EmploymentType employmentType) {
		return getSchoolActiveEmployeesOfEmploymentType(em, school, schoolYear, dayOfPrecense,
				(SpecializationGroup) null, employmentType);
	}

	public Collection<Employee> getSchoolActiveEmployees(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND e.lastSpecialization MEMBER OF g.specializations)"
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("specializations", specializationGroups).setParameter("schoolYear", schoolYear)
					.setParameter("dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND  e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
					.getResultList();
		}
	}

	public Collection<Employee> getSchoolActiveEmployeesOfEmploymentType(EntityManager em, School school,
			SchoolYear schoolYear, Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups,
			EmploymentType employmentType) {
		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.type=:employmentType AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND e.lastSpecialization MEMBER OF g.specializations)"
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("specializations", specializationGroups).setParameter("schoolYear", schoolYear)
					.setParameter("employmentType", employmentType).setParameter("dayOfInterest", dayOfPrecense)
					.getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND  e.currentEmployment.active IS TRUE AND e.currentEmployment.type=:employmentType AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("employmentType", employmentType).setParameter("schoolYear", schoolYear)
					.setParameter("dayOfInterest", dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Employee> getSchoolActiveEmployeesOfEmploymentType(EntityManager em, School school,
			SchoolYear schoolYear, Date dayOfPrecense, SpecializationGroup specializationGroup,
			EmploymentType employmentType) {
		if (specializationGroup != null) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.type=:employmentType AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specialization AND e.lastSpecialization MEMBER OF g.specializations)"
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("specialization",
							specializationGroup).setParameter("school", school).setParameter("schoolYear", schoolYear)
					.setParameter("employmentType", employmentType).setParameter("dayOfInterest", dayOfPrecense)
					.getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.type=:employmentType AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("employmentType", employmentType).setParameter("schoolYear", schoolYear)
					.setParameter("dayOfInterest", dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Employee> getSchoolActiveEmployees(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, SpecializationGroup specializationGroup) {
		if (specializationGroup != null) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specialization AND e.lastSpecialization MEMBER OF g.specializations)"
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("specialization",
							specializationGroup).setParameter("school", school).setParameter("schoolYear", schoolYear)
					.setParameter("dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND  e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
									+ " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND NOT EXISTS(SELECT l FROM Leave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
					.setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
					.getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Disposal> getSchoolDisposals(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {

		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)"
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"specializations", specializationGroups).setParameter("school", school).setParameter(
							"schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"school", school).setParameter("schoolYear", schoolYear).setParameter("dayOfInterest",
							dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Employee> getSchoolEmployeeWithPresence(EntityManager entityManager, Date dayOfPrecense,
			School school, SchoolYear schoolYear) {
		return getEntityManager(entityManager)
				.createQuery(
						"SELECT e FROM Employee e LEFT OUTER JOIN e.secondments sec WHERE (e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) AND (:dayOfPrecense >= sec.established AND :dayOfPrecense <= sec.dueTo)")
				.setParameter("school", school).setParameter("schoolYear", schoolYear).setParameter("dayOfPrecense",
						dayOfPrecense).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Employment> getSchoolEmployments(EntityManager entityManager, SchoolYear schoolyear, Unit school) {
		long started = System.currentTimeMillis(), finished;
		info("fetching all employments in school unit #0 during school year #1", school, schoolyear);
		List<Employment> return_value = getEntityManager(entityManager)
				.createQuery(
						"SELECT e FROM Employment e WHERE e.school=:school AND e.schoolYear=:schoolyear AND e.active IS TRUE ORDER BY e.specialization.id, e.employee.lastName")
				.setParameter("school", school).setParameter("schoolyear", schoolyear).getResultList();
		finished = System.currentTimeMillis();
		info(
				"found totally #0 deputy employment(s) in school unit #1 and school year #2. The operation tool #3 [ms] to complete",
				return_value.size(), school, schoolyear, Long.valueOf((finished - started)));
		return return_value;
	}

	@SuppressWarnings("unchecked")
	public List<Employment> getSchoolEmploymentsOfType(EntityManager entityManager, SchoolYear schoolyear, Unit school,
			EmploymentType type) {
		long started = System.currentTimeMillis(), finished;
		info("fetching all employments of type #3 in school unit #0 during school year #1", school, schoolyear, type);
		List<Employment> return_value = getEntityManager(entityManager)
				.createQuery(
						"SELECT e FROM Employment e WHERE e.school=:school AND e.schoolYear=:schoolyear AND e.type=:type AND e.active IS TRUE ORDER BY e.specialization.id, e.employee.lastName")
				.setParameter("school", school).setParameter("schoolyear", schoolyear).setParameter("type", type)
				.getResultList();
		finished = System.currentTimeMillis();
		info(
				"found totally #0 employment(s) of type #3 in school unit #1 during school year #2. The operation took #4 [ms] to complete. ",
				return_value.size(), school, schoolyear, type, Long.valueOf(finished - started));
		return return_value;
	}

	@SuppressWarnings("unchecked")
	public Collection<Leave> getSchoolLeaves(EntityManager em, School school, SchoolYear schoolYear,
			Date effectiveDate, Collection<SpecializationGroup> specializationGroups) {

		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT l FROM Leave l JOIN FETCH l.employee WHERE (l.active IS TRUE AND l.employee.currentEmployment.school=:school AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations) "
									+ " ORDER BY l.employee.lastSpecialization.id, l.employee.lastName ").setParameter(
							"specializations", specializationGroups).setParameter("school", school).setParameter(
							"dayOfInterest", effectiveDate).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT l FROM Leave l JOIN FETCH l.employee WHERE (l.active IS TRUE AND l.employee.currentEmployment.school=:school AND (:dayOfInterest BETWEEN l.established AND l.dueTo)) "
									+ " ORDER BY l.employee.lastSpecialization.id, l.employee.lastName ").setParameter(
							"school", school).setParameter("dayOfInterest", effectiveDate).getResultList();
		}
	}

	public Collection<Secondment> getSchoolOutgoingSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense) {
		return getSchoolOutgoingSecondments(em, school, schoolYear, dayOfPrecense, (SpecializationGroup) null);
	}

	@SuppressWarnings("unchecked")
	public Collection<Secondment> getSchoolOutgoingSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {

		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)")
					.setParameter("specializations", specializationGroups).setParameter("school", school).setParameter(
							"schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))")
					.setParameter("school", school).setParameter("schoolYear", schoolYear).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Secondment> getSchoolOutgoingSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, SpecializationGroup specializationGroup) {

		if (specializationGroup != null) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specialization AND s.employee.lastSpecialization MEMBER OF g.specializations) "
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"specialization", specializationGroup).setParameter("school", school).setParameter(
							"schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"school", school).setParameter("schoolYear", schoolYear).setParameter("dayOfInterest",
							dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<School> getSchools(EntityManager em) {
		return getEntityManager(em).createQuery("SELECT s from School s ORDER BY s.title ASC").getResultList();

	}

	@SuppressWarnings("unchecked")
	public Collection<School> getSchools(EntityManager em, Character region) {
		if (region != null)
			return getEntityManager(em).createQuery(
					"SELECT s from School s WHERE s.regionCode=:region ORDER BY s.title ASC").setParameter("region",
					region).getResultList();
		else
			return getSchools(em);
	}

	public Collection<Secondment> getSchoolSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense) {
		return getSchoolSecondments(em, school, schoolYear, dayOfPrecense, (SpecializationGroup) null);
	}

	@SuppressWarnings("unchecked")
	public Collection<Secondment> getSchoolSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {

		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)")
					.setParameter("specializations", specializationGroups).setParameter("school", school).setParameter(
							"schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))")
					.setParameter("school", school).setParameter("schoolYear", schoolYear).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Secondment> getSchoolSecondments(EntityManager em, School school, SchoolYear schoolYear,
			Date dayOfPrecense, SpecializationGroup specializationGroup) {

		if (specializationGroup != null) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specialization AND s.employee.lastSpecialization MEMBER OF g.specializations) "
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"specialization", specializationGroup).setParameter("school", school).setParameter(
							"schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense).getResultList();
		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
									+ " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ").setParameter(
							"school", school).setParameter("schoolYear", schoolYear).setParameter("dayOfInterest",
							dayOfPrecense).getResultList();
		}
	}

	public Collection<ServiceAllocation> getSchoolIncomingServiceAllocations(EntityManager em, School school,
			Date dayOfPrecense) {
		return getSchoolIncomingServiceAllocations(em, school, dayOfPrecense, (SpecializationGroup) null);
	}

	@SuppressWarnings("unchecked")
	public Collection<ServiceAllocation> getSchoolIncomingServiceAllocations(EntityManager em, School school,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND a.employee.lastSpecialization MEMBER OF g.specializations)")
					.setParameter("specializations", specializationGroups).setParameter("school", school).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
					.setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

		}

	}

	@SuppressWarnings("unchecked")
	public Collection<ServiceAllocation> getSchoolOutgoingServiceAllocations(EntityManager em, School school,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND a.employee.lastSpecialization MEMBER OF g.specializations)")
					.setParameter("specializations", specializationGroups).setParameter("school", school).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
					.setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

		}

	}

	/**
	 * Returns all service allocations with source the given school and target unit
	 * different than the source
	 * @param em
	 * @param school
	 * @param dayOfPrecense
	 * @param specializationGroups
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<ServiceAllocation> getSchoolReallyOutgoingServiceAllocations(EntityManager em, School school,
			Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
		if (specializationGroups != null && specializationGroups.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.sourceUnit=:school AND a.serviceUnit != :school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))"
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND a.employee.lastSpecialization MEMBER OF g.specializations)")
					.setParameter("specializations", specializationGroups).setParameter("school", school).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.sourceUnit=:school AND a.serviceUnit != :school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
					.setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

		}

	}

	@SuppressWarnings("unchecked")
	public Collection<ServiceAllocation> getSchoolIncomingServiceAllocations(EntityManager em, School school,
			Date dayOfPrecense, SpecializationGroup specializationGroup) {
		if (specializationGroup != null) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g=:specialization AND a.employee.lastSpecialization MEMBER OF g.specializations) "
									+ " ORDER BY a.employee.lastSpecialization.id, a.employee.lastName ").setParameter(
							"specialization", specializationGroup).setParameter("school", school).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
									+ " ORDER BY a.employee.lastSpecialization.id, a.employee.lastName ").setParameter(
							"school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

		}

	}

	@SuppressWarnings("unchecked")
	public Collection<ServiceAllocation> getSchoolIncomingServiceAllocationsOfType(EntityManager em, School school,
			Date dayOfPrecense, Collection<ServiceAllocationType> serviceAllocationTypes) {
		if (serviceAllocationTypes != null && serviceAllocationTypes.size() > 0) {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND a.serviceType IN (:serviceTypes)) AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
					.setParameter("serviceTypes", serviceAllocationTypes).setParameter("school", school).setParameter(
							"dayOfInterest", dayOfPrecense).getResultList();

		} else {
			return getEntityManager(em)
					.createQuery(
							"SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
					.setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

		}

	}

	@SuppressWarnings("unchecked")
	public Collection<TeachingRequirement> getSchoolTeachingRequirement(EntityManager entityManager, School school,
			SchoolYear schoolYear) {
		return getEntityManager(entityManager)
				.createQuery(
						"SELECT t FROM TeachingRequirement t WHERE t.school=:school AND t.schoolYear=:schoolYear ORDER BY t.specialization.title ASC")
				.setParameter("school", school).setParameter("schoolYear", schoolYear).getResultList();
	}

	public Specialization getSpecialization(String id) {
		return getEntityManager().find(Specialization.class, id);
	}

	@SuppressWarnings("unchecked")
	public Collection<SpecializationGroup> getSpecializationGroups(SchoolYear schoolYear, EntityManager em) {
		return (Collection<SpecializationGroup>) em.createQuery(
				"SELECT s FROM SpecializationGroup s WHERE s.schoolYear=:schoolYear ORDER BY s.title ASC")
				.setParameter("schoolYear", schoolYear).getResultList();
	}

	@Factory(value = "specializationGroupSearchTypes")
	public SpecializationGroupSearchType[] getSpecializationGroupSearchTypes() {
		return SpecializationGroupSearchType.values();
	}

	@Factory(value = "specializationSearchTypes")
	public SpecializationSearchType[] getSpecializationSearchTypes() {
		return SpecializationSearchType.values();
	}

	/**
	 * Returns all secondments that have as target the given unit unit during a
	 * school year. The operation will returns ONLY secondments that are not
	 * superseeded by other secondments.
	 * 
	 * @see Secondment#getSupersededBy()
	 * 
	 * @param schoolyear
	 *            The school year we are intereted in or <code>null</code> if we
	 *            are interested in the default active school year.
	 * @param targetUnit
	 *            The target unit
	 * @return All secondments for the target school during the given school
	 *         year.
	 */
	@SuppressWarnings("unchecked")
	public Collection<Secondment> getUnitSecondments(EntityManager entityManager, SchoolYear schoolyear, Unit targetUnit) {
		EntityManager em = getEntityManager(entityManager);
		schoolyear = schoolyear != null ? schoolyear : getActiveSchoolYear(em);
		info("fetching all secondments (not superseded) for school year '#0' with target unit '#1'.", schoolyear,
				targetUnit);
		Collection<Secondment> return_value = entityManager
				.createQuery(
						"SELECT s from Secondment s WHERE s.schoolYear=:schoolyear AND s.targetUnit=:targetunit AND s.supersededBy IS NULL ORDER BY s.insertedOn")
				.setParameter("schoolyear", schoolyear).setParameter("targetunit", targetUnit).getResultList();
		info("found totally '#0' secondments (not superseded) for school year '#1' with target unit '#2'.",
				return_value.size(), schoolyear, targetUnit);
		return return_value;
	}

	public List<OrganizationalOffice> searchOrganizationalOffices(EntityManager entityManager, String search_string) {
		throw new RuntimeException("not implemented yet");
	}

	public List<OrganizationalOffice> searchOrganizationalOffices(String search_string) {
		throw new RuntimeException("not implemented yet");
	}

	@SuppressWarnings("unchecked")
	public List<Principal> searchPrincipals(EntityManager entityManager, String search_string) {
		EntityManager em = getEntityManager(entityManager);
		String pattern = CoreUtils.getSearchPattern(search_string);
		return em.createQuery("SELECT p FROM Principal p WHERE lower(p.username) LIKE :search_pattern").setParameter(
				"search_pattern", pattern).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Role> searchRoles(String role_search_pattern) {
		String pattern = CoreUtils.getSearchPattern(role_search_pattern);
		info("searching for roles with #0 search pattern", pattern);
		List return_value = getEntityManager().createQuery(
				"SELECT r from Role r WHERE lower(r.id) LIKE :search_pattern").setParameter("search_pattern", pattern)
				.getResultList();
		info("found totally #0 role(s).", return_value.size());
		return return_value;
	}

	@SuppressWarnings("unchecked")
	public List<School> searchShools(String school_search_pattern) {
		String pattern = CoreUtils.getSearchPattern(school_search_pattern);
		info("searching for schools with #0 search pattern.", pattern);
		List return_value = getEntityManager().createQuery(
				"SELECT s from School s WHERE lower(s.title) LIKE :search_pattern AND s.ministryCode != '0000000'")
				.setParameter("search_pattern", pattern).getResultList();
		info("found totally #0 school(s).", return_value.size());
		return return_value;
	}

	public List<School> searchShools(String school_search_pattern, String regionCode) {
		throw new RuntimeException("not implemented yet");
	}

}
