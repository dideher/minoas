package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.EstablishmentLocation;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.model.preparatory.EstablishmentLicenseStatusType;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;
import gr.sch.ira.minoas.model.preparatory.TeachingLanguage;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

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

	public Principal getPrincipal(EntityManager entityManager, String username) {
		try {
			EntityManager em = getEntityManager(entityManager);
			return (Principal) em.createQuery("SELECT OBJECT(p) FROM Principal p WHERE p.username=:username")
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
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

	@SuppressWarnings("unchecked")
	@Factory(value = "establishmentLocations")
	public Collection<EstablishmentLocation> getAvailableEstablishments() {
		return getEntityManager().createQuery("FROM EstablishmentLocation e ORDER BY (e.title)").getResultList();
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

	@Factory(value = "secondmentTypes")
	public SecondmentType[] getAvailableSecondmentTypes() {
		return SecondmentType.values();
	}
	
	@Factory(value = "serviceAllocationTypes")
	public ServiceAllocationType[] getAvailableServiceAllocationTypes() {
		return ServiceAllocationType.values();
	}
	
	@Factory(value = "leaveTypes")
	public LeaveType[] getAvailableLeaveTypes() {
		return LeaveType.values();
	}

	@Factory(value = "establishmentLicenseStatusTypes")
	public EstablishmentLicenseStatusType[] getAvailableEstablishmentLicenseStatusTypes() {
		return EstablishmentLicenseStatusType.values();
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

	@SuppressWarnings("unchecked")
	@Factory(value = "teachingLanguages")
	public Collection<TeachingLanguage> getAvailableTeachingLanguages() {
		return getEntityManager().createQuery("FROM TeachingLanguage e ORDER BY (e.language)").getResultList();
	}

	/**
	 * Returns the active secondment (if any) for the given employee.
	 * 
	 * @param employee
	 * @return
	 */
	public Secondment getEmployeeActiveSecondment(EntityManager entityManager, Person employee) {
		Secondment result = null;
		EntityManager em = getEntityManager(entityManager);
		try {
			info("searching employee's '#0' active secondment.");
			SchoolYear activeSchoolYear = getActiveSchoolYear(em);
			result = (Secondment) em
					.createQuery(
							"SELECT s from Secondment s WHERE s.employee=:employee AND s.active=TRUE AND s.supersededBy IS NULL and s.schoolYear=:schoolYear")
					.setParameter("employee", employee).setParameter("schoolYear", activeSchoolYear).getSingleResult();
			info("found an active secondment '#0', for employee '#1' in school year '#2'", result, employee,
					activeSchoolYear);
			return result;
		} catch (NoResultException ex) {
			return null;
		}
	}

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
	public Collection<Secondment> getEmployeeSecondments(Person employee) {
		Collection<Secondment> result = null;
		info("searching employee's '#0' secondments.");
		result = entityManager.createQuery(
				"SELECT s from Secondment s WHERE s.employee=:employee ORDER BY s.insertedOn").setParameter("employee",
				employee).getResultList();
		info("found totally '#0' secondments for employee '#1'.", result.size(), employee);
		return result;
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected EntityManager getEntityManager(EntityManager entityManager) {
		return entityManager != null ? entityManager : getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Employment> getSchoolEmployments(SchoolYear schoolyear, Unit school) {
		long started = System.currentTimeMillis(), finished;
		info("fetching all employments in school unit #0 during school year #1", school, schoolyear);
		List<Employment> return_value = getEntityManager()
				.createQuery(
						"SELECT e FROM Employment e WHERE e.school=:school AND e.schoolYear=:schoolyear ORDER BY e.specialization.id, e.employee.lastName")
				.setParameter("school", school).setParameter("schoolyear", schoolyear).getResultList();
		finished = System.currentTimeMillis();
		info(
				"found totally #0 deputy employment(s) in school unit #1 and school year #2. The operation tool #3 [ms] to complete",
				return_value.size(), school, schoolyear, Long.valueOf((finished - started)));
		return return_value;
	}

	@SuppressWarnings("unchecked")
	public List<Employment> getSchoolEmploymentsOfType(SchoolYear schoolyear, Unit school, EmploymentType type) {
		long started = System.currentTimeMillis(), finished;
		info("fetching all employments of type #3 in school unit #0 during school year #1", school, schoolyear, type);
		List<Employment> return_value = getEntityManager()
				.createQuery(
						"SELECT e FROM Employment e WHERE e.school=:school AND e.schoolYear=:schoolyear AND e.type=:type ORDER BY e.specialization.id, e.employee.lastName")
				.setParameter("school", school).setParameter("schoolyear", schoolyear).setParameter("type", type)
				.getResultList();
		finished = System.currentTimeMillis();
		info(
				"found totally #0 employment(s) of type #3 in school unit #1 during school year #2. The operation took #4 [ms] to complete. ",
				return_value.size(), school, schoolyear, type, Long.valueOf(finished - started));
		return return_value;
	}

	public Specialization getSpecialization(String id) {
		return getEntityManager().find(Specialization.class, id);
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
