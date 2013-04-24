package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.classrooms.SchoolClass;
import gr.sch.ira.minoas.model.core.EstablishmentLocation;
import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.model.core.PYSDE;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolType;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.Evaluation;
import gr.sch.ira.minoas.model.employee.MaritalStatusType;
import gr.sch.ira.minoas.model.employee.PartTimeEmployment;
import gr.sch.ira.minoas.model.employee.Penalty;
import gr.sch.ira.minoas.model.employee.PenaltyType;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.model.employee.RankType;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.employement.DisposalType;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.employement.SelectionTableType;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.model.employement.SpecialAssigment;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.model.employement.WorkExperienceType;
import gr.sch.ira.minoas.model.printout.PrintoutRecipients;
import gr.sch.ira.minoas.model.printout.PrintoutSignatures;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.model.security.Role;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;
import gr.sch.ira.minoas.model.transfers.PermanentTransfer;
import gr.sch.ira.minoas.model.transfers.PermanentTransferType;
import gr.sch.ira.minoas.seam.components.criteria.DateSearchType;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationGroupSearchType;
import gr.sch.ira.minoas.seam.components.criteria.SpecializationSearchType;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
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
    
    /**
     * Represents regular leave types 
     */
    public static final Collection<String> REGULAR_TYPE_LEAVE_TYPES = Arrays.asList("31", "54", "74");
    
    /**
     * Represents medical leave types 
     */
    public static final Collection<String> MEDICAL_TYPE_LEAVE_TYPES = Arrays.asList("41", "42", "47", "48", "55", "71");
    
    

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
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Secondment> getActiveSecondments(EntityManager em) {
        return em.createQuery("SELECT s from Secondment s WHERE s.active IS TRUE").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<PrintoutRecipients> getPrintoutRecipients(EntityManager em) {
        return em.createQuery("SELECT s from PrintoutRecipients s").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<PrintoutSignatures> getPrintoutSignatures(EntityManager em) {
        return em.createQuery("SELECT s from PrintoutSignatures s").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Secondment> getActiveSecondments(EntityManager em, SchoolYear schoolYear) {
        return em.createQuery("SELECT s from Secondment s WHERE s.active IS TRUE AND s.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<ServiceAllocation> getActiveServiceAllocations(EntityManager em) {
        return getEntityManager(em).createQuery("SELECT s FROM ServiceAllocation s WHERE s.active IS TRUE")
                .getResultList();
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<Disposal> getActiveDisposal(EntityManager em, SchoolYear schoolYear) {
        return getEntityManager(em)
                .createQuery("SELECT d FROM Disposal d WHERE d.active IS TRUE AND d.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<Disposal> getActiveDisposal(EntityManager em) {
        return getEntityManager(em).createQuery("SELECT d FROM Disposal d WHERE d.active IS TRUE").getResultList();
    }


    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<EmployeeLeave> getActiveLeaves(EntityManager em) {
        return getEntityManager(em).createQuery("SELECT l FROM EmployeeLeave l WHERE l.active IS TRUE AND (l.deleted IS FALSE OR l.deleted IS NULL)").getResultList();
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<EmployeeLeave> getFutureLeavesThatWillBeActivated(EntityManager em, Date referenceDay, Integer dayThreshold) {
        Calendar establishedFrom = Calendar.getInstance();
        establishedFrom.setTime(referenceDay);
        DateUtils.truncate(establishedFrom, Calendar.DAY_OF_MONTH);
        
        Calendar establishedTo = (Calendar)establishedFrom.clone();
        establishedTo.add(Calendar.DAY_OF_MONTH, dayThreshold);
        
//        Calendar dueToFrom = Calendar.getInstance();
//        dueToFrom.setTime(referenceDay);
//        DateUtils.truncate(dueToFrom, Calendar.DAY_OF_MONTH);
//        
//        Calendar dueToTo = (Calendar)dueToFrom.clone();
//        dueToTo.add(Calendar.DAY_OF_MONTH, dayThreshold);
        
        return getEntityManager(em).createQuery("SELECT l FROM EmployeeLeave l WHERE l.active IS FALSE AND (l.deleted IS FALSE OR l.deleted IS NULL) AND l.established BETWEEN :establishedFrom AND :establishedTo").setParameter("establishedFrom",  establishedFrom.getTime()).setParameter("establishedTo",  establishedTo.getTime()).getResultList();
    }
    
     /**
     * @param employee
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getAllEmployeeEmploymentsOfType(Person employee, EmploymentType type) {
        List<Employment> result;
        debug("trying to featch all  employments for employee '#0'", employee);
        result = entityManager
                .createQuery(
                        "SELECT e from Employment e WHERE e.employee=:employee AND e.type=:type AND e.deleted IS FALSE ORDER BY e.schoolYear.title ")
                .setParameter("employee", employee).setParameter("type", type).getResultList();
        info("found totally '#0' employments for regular employee '#1'.", result.size(), employee);
        return result;
    }

    @SuppressWarnings("unchecked")
    public Collection<Secondment> getAllEmployeeSecondments(Person employee) {
        Collection<Secondment> result = null;
        info("searching employee's '#0' secondments.", employee);
        result = entityManager
                .createQuery("SELECT s from Secondment s WHERE s.employee=:employee AND (s.deleted IS FALSE or s.deleted IS NULL) ORDER BY s.established DESC")
                .setParameter("employee", employee).getResultList();
        info("found totally '#0' secondments for employee '#1'.", result.size(), employee);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Disposal> getAllEmployeeDisposals(Person employee) {
        Collection<Disposal> result = null;
        info("searching employee's '#0' disposals.", employee);
        result = entityManager
                .createQuery("SELECT s from Disposal s WHERE s.employee=:employee AND (s.deleted IS FALSE or s.deleted IS NULL) ORDER BY s.established DESC")
                .setParameter("employee", employee).getResultList();
        info("found totally '#0' disposals for employee '#1'.", result.size(), employee);
        return result;
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

    @Factory(value = "permanentTransferTypes")
    public PermanentTransferType[] getAvailablePermanentTransferTypes() {
        return PermanentTransferType.values();
    }

    @SuppressWarnings("unchecked")
    public List<Role> getAvailableRoles() {
        debug("fetching all available groups");
        List<Role> return_value = (List<Role>)getEntityManager().createQuery("SELECT r from Role r").getResultList();
        debug("found totally #0 role(s).", return_value.size());
        return return_value;

    }

    @Factory(value = "availableSchoolClasses")
    @SuppressWarnings("unchecked")
    public List<SchoolClass> getAvailableSchoolClasses() {
        return getEntityManager().createQuery("SELECT s FROM SchoolClass s ORDER BY (s.schoolType)").getResultList();
    }

    @Factory(value = "schoolTypes")
    public SchoolType[] getAvailableSchoolTypes() {
        return SchoolType.values();
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
    public Collection<Disposal> getDisposalAssociatedWithSecondment(EntityManager em, Secondment secondment) {
        return getEntityManager()
                .createQuery("SELECT DISTINCT d FROM Disposal d WHERE d.affectedSecondment = :secondment")
                .setParameter("secondment", secondment).getResultList();
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
    
    /**
     * Searches for an employee's disposal at a given date that is NOT DELETED or AUTO CANCELED. In other words, the 
     * operation will return a disposal that is active or will be active on a given date.
     * @param em
     * @param employee
     * @param dayOfIntereset
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Disposal> getEmployeeDisposalWithingPeriod(EntityManager em, Person employee, Date periodFrom, Date periodTo) {

        Collection<Disposal> result = null;
        result = getEntityManager(em)
                .createQuery(
                        "SELECT s FROM Disposal s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND s.employee=:employee AND ((:from <= s.established AND :end >= s.established) OR (:from <=s.dueTo AND :end >= s.dueTo ) OR (:from >= s.established AND :end <= s.dueTo)) ORDER BY s.insertedOn")
                .setParameter("employee", employee).setParameter("from", periodFrom).setParameter("end", periodTo).getResultList();
        return result;
    }
    
    /**
     * Searches for an employee's secondment at a given date that is NOT DELETED or AUTO CANCELED. In other words, the 
     * operation will return a secondment that is active or will be active on a given date.
     * @param em
     * @param employee
     * @param dayOfIntereset
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Secondment> getEmployeeSecondmentWithinPeriod(EntityManager em, Person employee,  Date periodFrom, Date periodTo) {

        Collection<Secondment> result = null;
        result = getEntityManager(em)
                .createQuery(
                        "SELECT s FROM Secondment s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND s.employee=:employee AND ((:from <= s.established AND :end >= s.established) OR (:from <=s.dueTo AND :end >= s.dueTo ) OR (:from >= s.established AND :end <= s.dueTo)) ORDER BY s.insertedOn")
                .setParameter("employee", employee).setParameter("from", periodFrom).setParameter("end", periodTo).getResultList();
        return result;
    }
    
    /**
     * Searches for an employee's service allocation at a given date that is NOT DELETED or AUTO CANCELED. In other words, the 
     * operation will return a service allocation that is active or will be active on a given date.
     * @param em
     * @param employee
     * @param dayOfIntereset
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<ServiceAllocation> getEmployeeServiceAllocationWithinPeriod(EntityManager em, Person employee,  Date periodFrom, Date periodTo) {

        Collection<ServiceAllocation> result = null;
        result = getEntityManager(em)
                .createQuery(
                        "SELECT s FROM ServiceAllocation s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND s.employee=:employee AND ((:from <= s.established AND :end >= s.established) OR (:from <=s.dueTo AND :end >= s.dueTo ) OR (:from >= s.established AND :end <= s.dueTo)) ORDER BY s.insertedOn")
                .setParameter("employee", employee).setParameter("from", periodFrom).setParameter("end", periodTo).getResultList();
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<ServiceAllocation> getEmployeeServiceAllocation(EntityManager em, Person employee) {

        Collection<ServiceAllocation> result = null;
        result = getEntityManager(em)
                .createQuery(
                        "SELECT s FROM ServiceAllocation s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND s.employee=:employee ORDER BY s.insertedOn")
                .setParameter("employee", employee).getResultList();
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Disposal> getEmployeeDisposals(EntityManager em, Person employee) {

        Collection<Disposal> result = null;
        result = getEntityManager(em)
                .createQuery(
                        "SELECT s FROM Disposal s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND (s.autoCanceled IS FALSE OR s.autoCanceled IS NULL) AND s.employee=:employee ORDER BY s.insertedOn")
                .setParameter("employee", employee).getResultList();
        return result;
    }
    
    

//    @Transactional(TransactionPropagationType.REQUIRED)
//    @Deprecated
//    public Leave getEmployeeActiveLeave(EntityManager entityManager, Person employee, Date onDate) {
//        EntityManager em = getEntityManager(entityManager);
//
//        try {
//            return (Leave) em
//                    .createQuery(
//                            "SELECT s from Leave s WHERE s.active IS TRUE AND s.employee=:employee AND :onDate BETWEEN s.established AND s.dueTo ORDER BY s.established")
//                    .setParameter("employee", employee).setParameter("onDate", onDate).getSingleResult();
//        } catch (NoResultException nre) {
//            return null;
//        }
//    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public EmployeeLeave getEmployeeActiveLeave2(EntityManager entityManager, Person employee, Date onDate) {
        EntityManager em = getEntityManager(entityManager);

        try {
            return (EmployeeLeave) em
                    .createQuery(
                            "SELECT s from EmployeeLeave s WHERE s.active IS TRUE AND s.employee=:employee AND :onDate BETWEEN s.established AND s.dueTo ORDER BY s.established")
                    .setParameter("employee", employee).setParameter("onDate", onDate).getSingleResult();
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
    public ServiceAllocation getEmployeeActiveServiceAllocation(EntityManager em, Person employee, Date dayOfIntereset) {
        try {
            return (ServiceAllocation) getEntityManager(em)
                    .createQuery(
                            "SELECT s FROM ServiceAllocation s WHERE s.active IS TRUE AND s.employee=:employee AND :dayOfInterest BETWEEN s.established AND s.dueTo ORDER BY s.insertedOn")
                    .setParameter("employee", employee).setParameter("dayOfInterest", dayOfIntereset).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    public Employee getEmployeeByVatNumber(EntityManager entityManager, String vatNumber) {
        EntityManager em = getEntityManager(entityManager);
        try {
            return (Employee) em.createQuery("SELECT e from Employee e WHERE e.vatNumber=:vatNumber")
                    .setParameter("vatNumber", vatNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<Employment> getEmployeeEmployments(Person employee) {
        List<Employment> result;
        debug("trying to featch all  employments for employee '#0'", employee);
        result = entityManager
                .createQuery("SELECT e from Employment e WHERE e.employee=:employee ORDER BY e.schoolYear.title")
                .setParameter("employee", employee).getResultList();
        info("found totally '#0' employments for employee '#1'.", result.size(), employee);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmployeeEmployments(Person employee, SchoolYear schoolyear) {
        List<Employment> result;
        debug("trying to featch all  employments for employee '#0' during school year '#1'.", employee, schoolyear);
        result = entityManager
                .createQuery("SELECT e from Employment e WHERE e.employee=:employee AND e.schoolYear=:schoolyear")
                .setParameter("employee", employee).setParameter("schoolyear", schoolyear).getResultList();
        info("found totally '#0' employments for regular employee '#1' during school year '#2'.", result.size(),
                employee, schoolyear);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmployeeEmploymentsOfType(Person employee, EmploymentType type) {
        List<Employment> result;
        debug("trying to featch all  employments for employee '#0'", employee);
        result = entityManager
                .createQuery(
                        "SELECT e from Employment e WHERE e.employee=:employee AND e.active IS TRUE  AND e.type=:type ORDER BY e.schoolYear.title")
                .setParameter("employee", employee).setParameter("type", type).getResultList();
        info("found totally '#0' employments for regular employee '#1'.", result.size(), employee);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmployeeEmploymentsOfType(Person employee, EmploymentType type,
            SchoolYear schoolYear) {
        List<Employment> result;
        debug("trying to featch all  employments for employee '#0'", employee);
        result = entityManager
                .createQuery(
                        "SELECT e from Employment e WHERE e.employee=:employee AND e.active IS TRUE AND e.type=:type AND e.schoolYear=:schoolyear ORDER BY e.id")
                .setParameter("employee", employee).setParameter("type", type).setParameter("schoolyear", schoolYear)
                .getResultList();
        info("found totally '#0' employments for regular employee '#1'.", result.size(), employee);
        return result;
    }

//    @SuppressWarnings("unchecked")
//    @Transactional(TransactionPropagationType.REQUIRED)
//    @Deprecated
//    public Collection<Leave> getEmployeeLeaves(Person employee) {
//        Collection<Leave> result = null;
//        info("searching employee's '#0' leaves.", employee);
//        result = entityManager.createQuery(
//                "SELECT s from Leave s WHERE s.active IS TRUE AND s.employee=:employee ORDER BY s.established")
//                .setParameter("employee", employee).getResultList();
//        info("found totally '#0' leave(s) for employee '#1'.", result.size(), employee);
//        return result;
//    }

//    @SuppressWarnings("unchecked")
//    @Transactional(TransactionPropagationType.REQUIRED)
//    public Collection<Leave> getEmployeeLeaveHistory(Person employee) {
//        Collection<Leave> result = null;
//        info("searching employee's '#0' leaves.", employee);
//        result = entityManager.createQuery(
//                "SELECT s from Leave s WHERE s.active IS FALSE AND s.employee=:employee ORDER BY s.established")
//                .setParameter("employee", employee).getResultList();
//        info("found totally '#0' leave(s) in employee's '#1' history.", result.size(), employee);
//        return result;
//    }
//    
//    
//    public Collection<Leave> getEmployeeLeaveHistoryWithCurrentActive(Person employee) {
//        Collection<Leave> result = null;
//        info("searching employee's '#0' leaves.", employee);
//        result = entityManager.createQuery(
//                "SELECT s from Leave s WHERE ((s.active IS FALSE AND s.autoCanceled IS TRUE) OR (s.active IS TRUE)) AND s.employee=:employee ORDER BY s.established")
//                .setParameter("employee", employee).getResultList();
//        info("found totally '#0' leave(s) in employee's '#1' history.", result.size(), employee);
//        return result;
//    }

//    @Deprecated
//    public Collection<Leave> getEmployeeLeaves(Person employee) {
//        Collection<Leave> result = null;
//        result = entityManager
//                .createQuery(
//                        "SELECT s from Leave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee ORDER BY s.established DESC")
//                .setParameter("employee", employee).getResultList();
//        return result;
//    }
    
    public Collection<EmployeeLeave> getEmployeeLeaves2(Person employee) {
        Collection<EmployeeLeave> result = null;
        result = entityManager
                .createQuery(
                        "SELECT s from EmployeeLeave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee ORDER BY s.established DESC")
                .setParameter("employee", employee).getResultList();
        return result;
    }
    
    public Collection<EmployeeLeave> getEmployeeLeaves(Person employee, Date established, Date dueTo) {
        Collection<EmployeeLeave> result = null;
        result = entityManager
                .createQuery(
                        "SELECT s from EmployeeLeave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee AND (s.established >= :established AND s.dueTo <= :dueTo) ORDER BY s.established DESC")
                .setParameter("employee", employee).setParameter("established", established).setParameter("dueTo", dueTo).getResultList();
        return result;
    }
    
    public Collection<EmployeeLeave> getEmployeeLeaves(Person employee, Date established, Date dueTo, Collection<EmployeeLeaveType> ofTypeList) {
        Collection<EmployeeLeave> result = null;
        info("searching employee leaves #0 from '#1' to '#2' of type '#3'", employee, established, dueTo, ofTypeList);
        result = entityManager
                .createQuery(
                        "SELECT s from EmployeeLeave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee AND (s.established >= :established AND s.dueTo <= :dueTo AND s.employeeLeaveType IN (:typesList)) ORDER BY s.established DESC")
                .setParameter("employee", employee).setParameter("established", established).setParameter("dueTo", dueTo).setParameter("typesList", ofTypeList).getResultList();
        info("found '#4' leave(s) for employee '#0' from '#1' to '#2' of type '#3'", employee, established, dueTo, ofTypeList, result.size());
        return result;
    }
    
   public Collection<EmployeeLeaveType> getLeaveTypes(Collection<String> legacyCodes) {
       Collection<EmployeeLeaveType> result = null;
       result = entityManager
               .createQuery(
                       "SELECT s from EmployeeLeaveType s WHERE s.legacyCode IN (:legacyCodes) ORDER BY s.legacyCode").setParameter("legacyCodes", legacyCodes).getResultList();
       return result; 
   }

//    @Deprecated
//    public Collection<Leave> getEmployeeFutureLeaves(Person employee, Date referenceDate) {
//        Collection<Leave> result = null;
//        result = entityManager
//                .createQuery(
//                        "SELECT s from Leave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee AND s.established > :referenceDate ORDER BY s.established DESC")
//                .setParameter("employee", employee).setParameter("referenceDate", referenceDate).getResultList();
//        return result;
//    }
    
    public Collection<EmployeeLeave> getEmployeeFutureLeaves2(Person employee, Date referenceDate) {
        Collection<EmployeeLeave> result = null;
        result = entityManager
                .createQuery(
                        "SELECT s from EmployeeLeave s WHERE (s.deleted IS FALSE OR s.deleted IS NULL) AND s.employee=:employee AND s.established > :referenceDate ORDER BY s.established DESC")
                .setParameter("employee", employee).setParameter("referenceDate", referenceDate).getResultList();
        return result;
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    public Employee getEmployeeOfTypeByVatNumber(EntityManager entityManager, EmployeeType employeeType,
            String vatNumber) {
        EntityManager em = getEntityManager(entityManager);
        try {
            return (Employee) em
                    .createQuery("SELECT e from Employee e WHERE e.vatNumber=:vatNumber AND e.type=:employeeType")
                    .setParameter("vatNumber", vatNumber).setParameter("employeeType", employeeType).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employee> getActiveEmployeesOfType(EntityManager entityManager, EmployeeType employeeType) {
        EntityManager em = getEntityManager(entityManager);
        return em.createQuery("SELECT e FROM Employee e WHERE e.type=:employeeType AND e.active IS TRUE")
                .setParameter("employeeType", employeeType).getResultList();

    }

    @SuppressWarnings("unchecked")
    public Collection<Secondment> getEmployeeSecondments(Person employee) {
        Collection<Secondment> result = null;
        info("searching employee's '#0' secondments.", employee);
        result = entityManager
                .createQuery(
                        "SELECT s from Secondment s WHERE s.active IS TRUE AND s.employee=:employee ORDER BY s.insertedOn")
                .setParameter("employee", employee).getResultList();
        info("found totally '#0' secondments for employee '#1'.", result.size(), employee);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employee> getEmployeesWithUnProcessedOutstandingImprovements(EntityManager em,
            SchoolYear schoolYear) {
        return getEntityManager(em)
                .createQuery(
                        "SELECT i.employee FROM OutstandingImprovement i WHERE i.isProcessed IS FALSE AND i.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();

    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employee> getEmployeesWithUnProcessedPermanentTransfers(EntityManager em, SchoolYear schoolYear) {
        return getEntityManager(em)
                .createQuery(
                        "SELECT i.employee FROM PermanentTransfer i WHERE i.isProcessed IS FALSE AND i.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();

    }
    
    @Factory(value = "employeeTypes")
    public EmployeeType[] getEmployeeTypes() {
        return EmployeeType.values();
    }
    
    @Factory(value = "selectionTableTypes")
    public SelectionTableType[] getSelectionTableTypes() {
        return SelectionTableType.values();
    }

    @Factory(value = "maritalStatusTypes")
    public MaritalStatusType[] getMaritalStatusTypes() {
        return MaritalStatusType.values();
    }
    
    public Collection<WorkExperience> getEmployeeWorkExperience(EntityManager em, Person employee) {
        return null;
    }

    public Collection<WorkExperience> getEmployeeWorkExperience(EntityManager em, Person employee,
            Collection<WorkExperienceType> ofType) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmploymentsOfType(EntityManager em, EmploymentType type, SchoolYear schoolYear) {
        List<Employment> result;
        debug("trying to featch all active employments of type '#0'", type);
        result = getEntityManager(em)
                .createQuery(
                        "SELECT e from Employment e WHERE e.active IS TRUE AND e.type=:type AND e.schoolYear=:schoolyear ORDER BY e.id")
                .setParameter("type", type).setParameter("schoolyear", schoolYear).getResultList();
        info("found totally '#0' active employments of type '#1'.", result.size(), type);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmploymentsOfType(EmploymentType type, SchoolYear schoolYear) {
        return getEmploymentsOfType(getEntityManager(), type, schoolYear);
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Employment> getEmploymentsOfType(EmploymentType type, SchoolYear schoolYear,
            Collection<Employee> excludedEmployees) {
        List<Employment> result;
        debug("trying to featch all active employments of type '#0'", type);
        result = entityManager
                .createQuery(
                        "SELECT e from Employment e WHERE e.active IS TRUE AND e.type=:type AND e.schoolYear=:schoolyear AND e.employee NOT IN (:excludedEmployees) ORDER BY e.id")
                .setParameter("type", type).setParameter("schoolyear", schoolYear)
                .setParameter("excludedEmployees", excludedEmployees).getResultList();
        info("found totally '#0' active employments of type '#1'.", result.size(), type);
        return result;
    }

    @Factory(value = "employmentTypes")
    public EmploymentType[] getEmploymentTypes() {
        return EmploymentType.values();
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected EntityManager getEntityManager(EntityManager entityManager) {
        return entityManager != null ? entityManager : getEntityManager();
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<PermanentTransfer> getgetUnProcessedPermanentTransfers(EntityManager em, SchoolYear schoolYear) {
        return getEntityManager(em)
                .createQuery(
                        "SELECT i FROM PermanentTransfer i WHERE i.isProcessed IS FALSE AND i.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();

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

    public Collection<Employee> getSchoolActiveEmployees(EntityManager em, School school, SchoolYear schoolYear,
            Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
        if (specializationGroups != null && specializationGroups.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
                                    + " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND e.lastSpecialization MEMBER OF g.specializations)"
                                    + " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
                    .setParameter("specializations", specializationGroups).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND  e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
                                    + " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
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
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ")
                    .setParameter("specialization", specializationGroup).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND  e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
                                    + " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
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
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
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
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
                    .setParameter("employmentType", employmentType).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
        }
    }

    public Collection<Employee> getSchoolActiveEmployeesOfEmploymentType(EntityManager em, School school,
            SchoolYear schoolYear, Date dayOfPrecense, EmploymentType employmentType) {
        return getSchoolActiveEmployeesOfEmploymentType(em, school, schoolYear, dayOfPrecense,
                (SpecializationGroup) null, employmentType);
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
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ")
                    .setParameter("specialization", specializationGroup).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("employmentType", employmentType)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT e FROM Employee AS e WHERE (e.active IS TRUE AND e.currentEmployment.active IS TRUE AND e.currentEmployment.type=:employmentType AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) "
                                    + " AND NOT EXISTS(SELECT s FROM Secondment s WHERE s.employee=e AND s.active IS TRUE AND s.sourceUnit=:school AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=e AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND NOT EXISTS(SELECT a FROM ServiceAllocation a WHERE a.employee=e AND a.active IS TRUE AND a.sourceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY e.lastSpecialization.id, e.lastName ").setParameter("school", school)
                    .setParameter("employmentType", employmentType).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<SchoolClass> getSchoolClassesForSchoolType(SchoolType schoolType) {
        return getEntityManager()
                .createQuery("SELECT s FROM SchoolClass s WHERE s.schoolType=:schoolType ORDER BY (s.sortOrder)")
                .setParameter("schoolType", schoolType).getResultList();
    }

    @SuppressWarnings("unchecked")
    public Collection<Disposal> getSchoolDisposals(EntityManager em, School school, SchoolYear schoolYear,
            Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {

        if (specializationGroups != null && specializationGroups.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)"
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
        }
    }

    /**
     * Get the schools disposals that are not affected by a active leave.
     * 
     * @param em
     * @param school
     * @param schoolYear
     * @param dayOfPrecense
     * @param specializationGroups
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<Disposal> getSchoolDisposalsNotAffectedByActiveLeave(EntityManager em, School school,
            SchoolYear schoolYear, Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {

        if (specializationGroups != null && specializationGroups.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)"
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=s.employee AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Disposal s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.disposalUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=s.employee AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Employee> getSchoolEmployeeWithPresence(EntityManager entityManager, Date dayOfPrecense,
            School school, SchoolYear schoolYear) {
        return getEntityManager(entityManager)
                .createQuery(
                        "SELECT e FROM Employee e LEFT OUTER JOIN e.secondments sec WHERE (e.currentEmployment.active IS TRUE AND e.currentEmployment.school=:school AND e.currentEmployment.schoolYear=:schoolYear) AND (:dayOfPrecense >= sec.established AND :dayOfPrecense <= sec.dueTo)")
                .setParameter("school", school).setParameter("schoolYear", schoolYear)
                .setParameter("dayOfPrecense", dayOfPrecense).getResultList();
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
        info("found totally #0 deputy employment(s) in school unit #1 and school year #2. The operation tool #3 [ms] to complete",
                return_value.size(), school, schoolyear, Long.valueOf((finished - started)));
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public List<Employment> getSchoolEmploymentsOfType(EntityManager entityManager, SchoolYear schoolyear, Unit school,
            EmploymentType type) {
        long started = System.currentTimeMillis(), finished;
        info("fetching all employments of type #2 in school unit #0 during school year #1", school, schoolyear, type);
        List<Employment> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT e FROM Employment e WHERE e.school=:school AND e.schoolYear=:schoolyear AND e.type=:type AND e.active IS TRUE ORDER BY e.specialization.id, e.employee.lastName")
                .setParameter("school", school).setParameter("schoolyear", schoolyear).setParameter("type", type)
                .getResultList();
        finished = System.currentTimeMillis();
        info("found totally #0 employment(s) of type #3 in school unit #1 during school year #2. The operation took #4 [ms] to complete. ",
                return_value.size(), school, schoolyear, type, Long.valueOf(finished - started));
        return return_value;
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
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
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
                                    + " ORDER BY a.employee.lastSpecialization.id, a.employee.lastName ")
                    .setParameter("specialization", specializationGroup).setParameter("school", school)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo)) "
                                    + " ORDER BY a.employee.lastSpecialization.id, a.employee.lastName ")
                    .setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

        }

    }

    @SuppressWarnings("unchecked")
    public Collection<ServiceAllocation> getSchoolIncomingServiceAllocationsOfType(EntityManager em, School school,
            Date dayOfPrecense, Collection<ServiceAllocationType> serviceAllocationTypes) {
        if (serviceAllocationTypes != null && serviceAllocationTypes.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND a.serviceType IN (:serviceTypes)) AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
                    .setParameter("serviceTypes", serviceAllocationTypes).setParameter("school", school)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.serviceUnit=:school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
                    .setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

        }

    }

    @SuppressWarnings("unchecked")
    @Transactional
    public Collection<EmployeeLeave> getSchoolLeaves(EntityManager em, School school, SchoolYear schoolYear,
            Date effectiveDate, Collection<SpecializationGroup> specializationGroups) {

        if (specializationGroups != null && specializationGroups.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT l FROM EmployeeLeave l JOIN FETCH l.employee WHERE (l.active IS TRUE AND l.employee.currentEmployment.school=:school AND (:dayOfInterest BETWEEN l.established AND l.dueTo))"
                                    + " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations) "
                                    + " ORDER BY l.employee.lastSpecialization.id, l.employee.lastName ")
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("dayOfInterest", effectiveDate).getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT l FROM EmployeeLeave l JOIN FETCH l.employee WHERE (l.active IS TRUE AND l.employee.currentEmployment.school=:school AND (:dayOfInterest BETWEEN l.established AND l.dueTo)) "
                                    + " ORDER BY l.employee.lastSpecialization.id, l.employee.lastName ")
                    .setParameter("school", school).setParameter("dayOfInterest", effectiveDate).getResultList();
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
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
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
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("specialization", specializationGroup).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.sourceUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
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
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

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
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();

        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT a FROM ServiceAllocation a JOIN FETCH a.employee WHERE (a.active IS TRUE AND a.sourceUnit=:school AND a.serviceUnit != :school AND (:dayOfInterest BETWEEN a.established AND a.dueTo))")
                    .setParameter("school", school).setParameter("dayOfInterest", dayOfPrecense).getResultList();

        }

    }

    @SuppressWarnings("unchecked")
    public Collection<School> getSchools(EntityManager em) {
        return getEntityManager(em).createQuery("SELECT s from School s ORDER BY s.title ASC").getResultList();

    }

    @SuppressWarnings("unchecked")
    public Collection<School> getSchools(EntityManager em, Character region) {
        if (region != null)
            return getEntityManager(em)
                    .createQuery("SELECT s from School s WHERE s.regionCode=:region ORDER BY s.title ASC")
                    .setParameter("region", region).getResultList();
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
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
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
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("specialization", specializationGroup).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo)) "
                                    + " ORDER BY s.employee.lastSpecialization.id, s.employee.lastName ")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
        }
    }

    /**
     * Returns a list of secondments targeted to the given school unit that are not associated with a leave
     * @param em
     * @param school
     * @param schoolYear
     * @param dayOfPrecense
     * @param specializationGroups
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<Secondment> getSchoolSecondmentsNotAffectedByActiveLeave(EntityManager em, School school,
            SchoolYear schoolYear, Date dayOfPrecense, Collection<SpecializationGroup> specializationGroups) {
        if (specializationGroups != null && specializationGroups.size() > 0) {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
                                    + " AND EXISTS (SELECT g FROM SpecializationGroup g WHERE g IN (:specializations) AND s.employee.lastSpecialization MEMBER OF g.specializations)"
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=s.employee AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))")
                    .setParameter("specializations", specializationGroups).setParameter("school", school)
                    .setParameter("schoolYear", schoolYear).setParameter("dayOfInterest", dayOfPrecense)
                    .getResultList();
        } else {
            return getEntityManager(em)
                    .createQuery(
                            "SELECT DISTINCT s FROM Secondment s JOIN FETCH s.employee WHERE (s.active IS TRUE AND s.targetUnit=:school AND s.schoolYear=:schoolYear AND (:dayOfInterest BETWEEN s.established AND s.dueTo))"
                                    + " AND NOT EXISTS(SELECT l FROM EmployeeLeave l WHERE l.employee=s.employee AND l.active IS TRUE AND (:dayOfInterest BETWEEN l.established AND l.dueTo))")
                    .setParameter("school", school).setParameter("schoolYear", schoolYear)
                    .setParameter("dayOfInterest", dayOfPrecense).getResultList();
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

    public Collection<SchoolYear> getSchoolYears(EntityManager entityManager) {
        try {
            EntityManager em = getEntityManager(entityManager);
            return em.createQuery("SELECT s from SchoolYear s ORDER BY (s.year)").getResultList();
        } catch (NoResultException nre) {
            warn("no active school year found");
            return null;
        }
    }

    public Specialization getSpecialization(String id) {
        return getEntityManager().find(Specialization.class, id);
    }

    @SuppressWarnings("unchecked")
    public Collection<SpecializationGroup> getSpecializationGroups(SchoolYear schoolYear, EntityManager em) {
        return (Collection<SpecializationGroup>) em
                .createQuery("SELECT s FROM SpecializationGroup s WHERE s.schoolYear=:schoolYear ORDER BY s.title ASC")
                .setParameter("schoolYear", schoolYear).getResultList();
    }

    @Factory(value = "specializationGroupSearchTypes")
    public SpecializationGroupSearchType[] getSpecializationGroupSearchTypes() {
        return SpecializationGroupSearchType.values();
    }

    @SuppressWarnings("unchecked")
    public Collection<Specialization> getSpecializations(EntityManager em) {
        return (Collection<Specialization>) em.createQuery("SELECT s FROM Specialization s ORDER BY s.id ASC")
                .getResultList();
    }

    @Factory(value = "specializationSearchTypes")
    public SpecializationSearchType[] getSpecializationSearchTypes() {
        return SpecializationSearchType.values();
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getTeachingHoursCDRs(EntityManager entityManager, SchoolYear schoolYear) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery("SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();
        info("found totally #0 CDRs", return_value.size());
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getTeachingHoursCDRsRelatedToEmployment(EntityManager entityManager, Employment employment, SchoolYear schoolYear) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery("SELECT t FROM TeachingHourCDR t WHERE t.employment=:employment AND t.schoolYear=:schoolYear")
                .setParameter("employment", employment).setParameter("schoolYear", schoolYear).getResultList();
        return return_value;
    }
    
    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getEmployeeTeachingHoursCDRs(EntityManager entityManager, SchoolYear schoolYear,
            Employee employee) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery("SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee")
                .setParameter("schoolYear", schoolYear).setParameter("employee", employee).getResultList();
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getEmployeeTeachingHoursCDRsWithPositiveHours(EntityManager entityManager,
            SchoolYear schoolYear, Employee employee) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.hours >= 0 AND t.employee=:employee")
                .setParameter("schoolYear", schoolYear).setParameter("employee", employee).getResultList();
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getEmployeeLogisticTeachingHoursCDR(EntityManager entityManager,
            SchoolYear schoolYear, Employee employee) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee AND t.logisticCDR IS TRUE")
                .setParameter("schoolYear", schoolYear).setParameter("employee", employee).getResultList();
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getEmployeeNonLogisticTeachingHoursCDR(EntityManager entityManager,
            SchoolYear schoolYear, Employee employee) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee AND t.logisticCDR IS FALSE")
                .setParameter("schoolYear", schoolYear).setParameter("employee", employee).getResultList();
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getEmployeeTeachingHoursCDRsOfType(EntityManager entityManager,
            SchoolYear schoolYear, Employee employee, TeachingHourCDRType type) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee AND t.cdrType=:type")
                .setParameter("schoolYear", schoolYear).setParameter("employee", employee).setParameter("type", type)
                .getResultList();
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public Collection<TeachingHourCDR> getSchoolTeachingHoursCDRs(EntityManager entityManager, SchoolYear schoolYear,
            Unit unit) {
        List<TeachingHourCDR> return_value = getEntityManager(entityManager)
                .createQuery(
                        "SELECT t FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear "
                                + "AND t.unit=:unit ORDER BY (t.employee)").setParameter("schoolYear", schoolYear)
                .setParameter("unit", unit).getResultList();
        info("found totally #0 CDRs", return_value.size());
        return return_value;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public Collection<TeachingRequirement> getTeachingRequirement(EntityManager em, SchoolYear schoolYear) {
        return getEntityManager()
                .createQuery("SELECT DISTINCT r FROM TeachingRequirement r WHERE r.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();
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

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<OutstandingImprovement> getUnProcessedOutstandingImprovements(EntityManager em,
            SchoolYear schoolYear) {
        return getEntityManager(em)
                .createQuery(
                        "SELECT i FROM OutstandingImprovement i WHERE i.isProcessed IS FALSE AND i.schoolYear=:schoolYear")
                .setParameter("schoolYear", schoolYear).getResultList();

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
        return em.createQuery("SELECT p FROM Principal p WHERE lower(p.username) LIKE :search_pattern")
                .setParameter("search_pattern", pattern).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Role> searchRoles(String role_search_pattern) {
        String pattern = CoreUtils.getSearchPattern(role_search_pattern);
        info("searching for roles with #0 search pattern", pattern);
        List<Role> return_value = getEntityManager()
                .createQuery("SELECT r from Role r WHERE lower(r.id) LIKE :search_pattern")
                .setParameter("search_pattern", pattern).getResultList();
        info("found totally #0 role(s).", return_value.size());
        return return_value;
    }

    @SuppressWarnings("unchecked")
    public List<School> searchShools(String school_search_pattern) {
        String pattern = CoreUtils.getSearchPattern(school_search_pattern);
        info("searching for schools with #0 search pattern.", pattern);
        List<School> return_value = getEntityManager()
                .createQuery(
                        "SELECT s from School s WHERE lower(s.title) LIKE :search_pattern AND s.ministryCode != '0000000'")
                .setParameter("search_pattern", pattern).getResultList();
        info("found totally #0 school(s).", return_value.size());
        return return_value;
    }

    public List<School> searchShools(String school_search_pattern, String regionCode) {
        throw new RuntimeException("not implemented yet");
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<WorkExperience> getWorkExperienceHistory(Person employee) {
        Collection<WorkExperience> result = null;
        info("searching employee's '#0' work experiences.", employee);
        result = entityManager
                .createQuery(
                        "SELECT s from WorkExperience s WHERE s.active IS TRUE AND (s.deleted IS FALSE OR s.deleted is NULL) AND s.employee=:employee ORDER BY s.fromDate")
                .setParameter("employee", employee).getResultList();
//        result = entityManager.createQuery(
//	        "SELECT s from Leave s WHERE s.active IS FALSE AND s.employee=:employee ORDER BY s.established")
//	        .setParameter("employee", employee).getResultList();
        info("found totally '#0' work experience(s) in employee's '#1' history.", result.size(), employee);
        return result;
    }


    
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Evaluation> getEvaluationHistory(Employee employee) {
        Collection<Evaluation> result = null;
        info("searching employee's '#0' evaluations.", employee);
        result = entityManager.createQuery(
            "SELECT s from Evaluation s WHERE s.employee=:employee AND (s.deleted IS FALSE OR s.deleted is NULL) ORDER BY s.evaluationDate")
            .setParameter("employee", employee).getResultList();
        info("found totally '#0' evaluation(s) in employee's '#1' history.", result.size(), employee);
        return result;
    }
   
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<PartTimeEmployment> getPartTimeEmploymentHistory(Employee employee) {
        Collection<PartTimeEmployment> result = null;
        info("searching employee's '#0' part-time employments.", employee);
        result = entityManager.createQuery(
            "SELECT s from PartTimeEmployment s WHERE s.employee=:employee AND (s.deleted IS FALSE OR s.deleted is NULL) ORDER BY s.startDate")
            .setParameter("employee", employee).getResultList();
        info("found totally '#0' part-time employment(s) in employee's '#1' history.", result.size(), employee);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<Penalty> getPenaltyHistory(Employee employee) {
        Collection<Penalty> result = null;
        result = entityManager.createQuery(
            "SELECT s from Penalty s WHERE s.employee=:employee AND (s.deleted IS FALSE OR s.deleted is NULL) ORDER BY s.penaltyAwardDate")
            .setParameter("employee", employee).getResultList();
        return result;
    }
    
    
/*
 * 	Deprecated because RankInfos are now available in employeeInfo.getRankInfos() collection
 * 
 *  @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<RankInfo> getRankInfoHistory(Employee employee) {
        Collection<RankInfo> result = null;
        info("searching employee's '#0' rank information.", employee);
        result = entityManager.createQuery(
            "SELECT r from RankInfo r WHERE r.employeeInfo=:employeeInfo ORDER BY r.lastRankDate, r.lastSalaryGradeDate")
            .setParameter("employeeInfo", employee.getEmployeeInfo()).getResultList();
        info("found totally '#0' rank transitions in employee's '#1' history.", result.size(), employee);
        return result;
    }
    
    */
    @SuppressWarnings("unchecked")
    @Transactional(TransactionPropagationType.REQUIRED)
    public Collection<SpecialAssigment> getEmployeeSpecialAssigments(EntityManager entityManager, Employee employee) {
        List<SpecialAssigment> return_value = entityManager.createQuery(
                "SELECT w FROM SpecialAssigment w WHERE w.employee=:employee AND w.active IS TRUE")
                .setParameter("employee", employee).getResultList();
        return return_value;
    }
    
    
    @Transactional(TransactionPropagationType.REQUIRED)
    @SuppressWarnings("unchecked")
    public Collection<SpecialAssigment> getActiveSpecialAssigments(EntityManager em) {
        return getEntityManager(em).createQuery("SELECT s FROM SpecialAssigment s WHERE s.active IS TRUE").getResultList();
    }
    

    @Factory(value = "rankTypes")
    public RankType[] getRankTypes() {
        return RankType.values();
    }
    
    @Factory(value = "educationalLevelTypes")
    public EducationalLevelType[] getEducationalLevelTypes() {
        return EducationalLevelType.values();
    }

    @Factory(value = "penaltyTypes")
    public PenaltyType[] getPenaltyTypes() {
        return PenaltyType.values();
    }

    @Transactional(TransactionPropagationType.REQUIRED)
    public Long getSummedEducationalWorkExperience(Employee employee) {
        Long returnValue = (Long) entityManager
                .createQuery(
                        "SELECT SUM(actualDays) FROM WorkExperience w WHERE w.active IS TRUE AND w.educational IS TRUE AND (w.deleted IS FALSE OR w.deleted IS NULL) AND w.employee=:employee")
                .setParameter("employee", employee).getSingleResult();
        return returnValue != null ? returnValue : new Long(0);
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public Long getSummedTeachingWorkExperience(Employee employee) {
        Long returnValue = (Long) entityManager
                .createQuery(
                        "SELECT SUM(actualDays) FROM WorkExperience w WHERE w.active IS TRUE AND w.teaching IS TRUE AND (w.deleted IS FALSE OR w.deleted IS NULL) AND w.employee=:employee")
                .setParameter("employee", employee).getSingleResult();
        return returnValue != null ? returnValue : new Long(0);
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public Long getSummedWorkExperience(Employee employee) {
        Long returnValue = (Long) entityManager
                .createQuery(
                        "SELECT SUM(actualDays) FROM WorkExperience w WHERE w.active IS TRUE AND (w.deleted IS FALSE OR w.deleted IS NULL) AND w.employee=:employee")
                .setParameter("employee", employee).getSingleResult();
        return returnValue != null ? returnValue : new Long(0);
    } 
    
    

}
