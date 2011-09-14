package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

import sun.security.action.GetLongAction;

@Name(value = "teachingHoursCDRManagement")
@Scope(ScopeType.CONVERSATION)
public class TeachingHoursCDRManagement extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(required = true, create = true)
    private CoreSearching coreSearching;

    @Transactional
    public void doCalculateCurrentCDRs() {
        long started = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        EntityManager em = getEntityManager();
        SchoolYear currentSchoolYear = coreSearching.getActiveSchoolYear(em);
        info("generating teaching hours CDR for current school year #0", currentSchoolYear);

        int totalCDRsCreated = 0;
        /* first fetch all current CDRs and remove them */
        Query q = getEntityManager().createQuery("DELETE TeachingHourCDR t WHERE t.schoolYear=:schoolYear");
        q.setParameter("schoolYear", currentSchoolYear);
        int cdrsDeleted = q.executeUpdate();       
        em.flush(); /* flush */
        info("successfully deleted totally #0 old CRD(s).", cdrsDeleted);
        

        /* fetch all regular employments */
        Collection<Employment> regularEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.REGULAR,
                currentSchoolYear);
        for (Employment employment : regularEmployments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.EMPLOYMENT);
            StringBuffer sb = new StringBuffer();
            sb.append("Οργανική θέση στην μονάδα απο τις ");
            sb.append(df.format(employment.getEstablished()));
            sb.append(" με υποχρεωτικό ωράριο ");
            sb.append(employment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(employment.getSpecialization());
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            employment.getEmploymentCDRs().add(cdr);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        em.flush(); /* flush */

        /* fetch all deputy employments */
        Collection<Employment> deputyEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.DEPUTY,
                currentSchoolYear);
        for (Employment employment : deputyEmployments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.EMPLOYMENT);
            StringBuffer sb = new StringBuffer();
            sb.append("Τοποθέτηση αναπληρωτή στην μονάδα απο τις ");
            sb.append(df.format(employment.getEstablished()));
            sb.append(" για ");
            sb.append(employment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(employment.getSpecialization());
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            employment.getEmploymentCDRs().add(cdr);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        em.flush(); /* flush */

        /* fetch all hourly employments */
        Collection<Employment> hourlyEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.HOURLYBASED,
                currentSchoolYear);
        for (Employment employment : hourlyEmployments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.EMPLOYMENT);
            StringBuffer sb = new StringBuffer();
            sb.append("Τοποθέτηση ωρομίσθιου στην μονάδα απο τις ");
            sb.append(df.format(employment.getEstablished()));
            sb.append(" για ");
            sb.append(employment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(employment.getSpecialization());
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            employment.getEmploymentCDRs().add(cdr);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        em.flush(); /* flush */
        /* handle secodnments */

        Collection<Secondment> secondments = coreSearching.getActiveSecondments(em);
        for (Secondment secondment : secondments) {

            /* hack for secondments */
            if (secondment.getFinalWorkingHours() != null && secondment.getMandatoryWorkingHours() != null &&
                    secondment.getFinalWorkingHours().intValue() == secondment.getMandatoryWorkingHours().intValue()) {
                /* if final working hour is equal to mandatory working hours then it means
                 * that the valus were copied from the employment
                 */
                Integer workingHours = secondment.getFinalWorkingHours();
                Employment employment = secondment.getAffectedEmployment();
                if (employment != null) {
                    if (employment.getMandatoryWorkingHours().intValue() != workingHours.intValue()) {
                        secondment.setFinalWorkingHours(employment.getMandatoryWorkingHours());
                        secondment.setMandatoryWorkingHours(employment.getMandatoryWorkingHours());
                    }
                }
            }

            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SECONDMENT);
            StringBuffer sb = new StringBuffer();
            sb.append("Αποσπασμένος απο την μονάδα ");
            sb.append(secondment.getSourceUnit().getTitle());
            sb.append(" με απόσπαση τυπου ");
            sb.append(CoreUtils.getLocalizedMessage(secondment.getSecondmentType().getKey()));
            sb.append(" απο τις ");
            sb.append(df.format(secondment.getEstablished()));
            sb.append(" για ");
            sb.append(secondment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(secondment.getEmployee().getLastSpecialization());
            cdr.setEmployee(secondment.getEmployee());
            cdr.setSecondment(secondment);
            cdr.setHours(secondment.getFinalWorkingHours());
            cdr.setUnit(secondment.getTargetUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;

            /* apply on source unit */
            
            cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SECONDMENT);
            sb = new StringBuffer();
            sb.append("Αποσπασμένος στην μονάδα ");
            sb.append(secondment.getTargetUnit().getTitle());
            sb.append(" με απόσπαση τυπου ");
            sb.append(CoreUtils.getLocalizedMessage(secondment.getSecondmentType().getKey()));
            sb.append(" απο τις ");
            sb.append(df.format(secondment.getEstablished()));
            sb.append(" για ");
            sb.append(secondment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setEmployee(secondment.getEmployee());
            cdr.setSpecialization(secondment.getEmployee().getLastSpecialization());
            cdr.setSecondment(secondment);
            cdr.setHours((-1) * secondment.getFinalWorkingHours());
            cdr.setUnit(secondment.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            cdr.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        
        em.flush(); /* flush */
        
        /* handle disposal */

        Collection<Disposal> disposals = coreSearching.getActiveDisposal(em);
        for (Disposal disposal : disposals) {

            Unit sourceUnit = null;
            try {
                sourceUnit = disposal.getAffectedEmployment() != null ? disposal.getAffectedEmployment().getSchool()
                        : disposal.getAffectedSecondment().getTargetUnit();
            } catch (Exception ex) {
                error("unhandled exception with disposal #0. Exception #1", disposal, ex);
                continue;
            }

            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.DISPOSAL);
            StringBuffer sb = new StringBuffer();
            sb.append("Διάθεση απο την μονάδα ");
            sb.append(sourceUnit.getTitle());
            sb.append(" με διάθεση τυπου ");
            sb.append(CoreUtils.getLocalizedMessage(disposal.getType().getKey()));
            sb.append(" απο τις ");
            sb.append(df.format(disposal.getEstablished()));
            sb.append(" για ");
            sb.append(disposal.getHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(disposal.getEmployee().getLastSpecialization());
            cdr.setEmployee(disposal.getEmployee());
            cdr.setDisposal(disposal);
            cdr.setHours(disposal.getHours());
            cdr.setUnit(disposal.getDisposalUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
            
            /* apply on source unit */

            cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.DISPOSAL);
            sb = new StringBuffer();
            sb.append("Διάθεση στην μονάδα ");
            sb.append(disposal.getDisposalUnit().getTitle());
            sb.append(" με διάθεση τυπου ");
            sb.append(CoreUtils.getLocalizedMessage(disposal.getType().getKey()));
            sb.append(" απο τις ");
            sb.append(df.format(disposal.getEstablished()));
            sb.append(" για ");
            sb.append(disposal.getHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setSpecialization(disposal.getEmployee().getLastSpecialization());
            cdr.setEmployee(disposal.getEmployee());
            cdr.setDisposal(disposal);
            cdr.setHours((-1) * disposal.getHours());
            cdr.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
            cdr.setUnit(sourceUnit);
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;

        }
        
        em.flush(); /* flush */
        
        /* handle service allocation */

        Collection<ServiceAllocation> serviceAllocations = coreSearching.getActiveServiceAllocations(em);
        info("found #0 totally active service allocations.", serviceAllocations.size());
        for(ServiceAllocation serviceAllocation : serviceAllocations) {
            try {
                if(serviceAllocation.getSourceUnit()==null) {
                    /* this should never happen */
                    warn("service allocation #0 has no source unit !!!!!!", serviceAllocation);
                    continue;
                }
                
                /* service allocation is always associated with an employment */
                
                Employment relatedEmployment = serviceAllocation.getAffectedEmployment();
                if(relatedEmployment != null) {
                    Collection<TeachingHourCDR> employmentsCDRs = relatedEmployment.getEmploymentCDRs();
                    /* Employment should have one only one CDR */
                    if(employmentsCDRs.size()>1) {
                        /* this should never happen */
                        warn("logic error -> employment #0 is associated with more than one cdrs -> #1", relatedEmployment, employmentsCDRs);
                        continue;
                    }
                   
                    
                    /* construct a special CDR to deduct the employment CDR hours */
                    TeachingHourCDR specialEmploymentCDR = new TeachingHourCDR();
                    specialEmploymentCDR.setCdrType(TeachingHourCDRType.GENERAL);
                    StringBuffer sb = new StringBuffer();
                    sb.append("Αντιλογισμός διδακτικών ωρών της σχέσης εργασίας από ");
                    sb.append(df.format(relatedEmployment.getEstablished()));
                    sb.append(" με υποχρεωτικό ωράριο ");
                    sb.append(relatedEmployment.getFinalWorkingHours());
                    sb.append(" ώρες λόγω θητείας τύπου ");
                    sb.append(CoreUtils.getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
                    specialEmploymentCDR.setComment(sb.toString());
                    specialEmploymentCDR.setSpecialization(relatedEmployment.getSpecialization());
                    specialEmploymentCDR.setEmployee(relatedEmployment.getEmployee());
                    specialEmploymentCDR.setEmployment(relatedEmployment);
                    specialEmploymentCDR.setHours((-1) * relatedEmployment.getFinalWorkingHours());
                    specialEmploymentCDR.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
                    specialEmploymentCDR.setUnit(relatedEmployment.getSchool());
                    specialEmploymentCDR.setSchoolYear(currentSchoolYear);
                    relatedEmployment.getEmploymentCDRs().add(specialEmploymentCDR);
                    entityManager.persist(specialEmploymentCDR);
                    totalCDRsCreated++;
                    
                } else {
                    warn("service allocation #0 does not have an regular employment !", serviceAllocation);
                    continue;
                }
                
               
                
                if(serviceAllocation.getSourceUnit().equals(serviceAllocation.getServiceUnit())) {
                    /* source unit is the same as the servicing unit. This is the case when a regular
                     * employee having a regular employment at a given unit, serves there as the headmaster.
                     */
                    TeachingHourCDR cdr = new TeachingHourCDR();
                    cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
                    
                    StringBuffer sb = new StringBuffer();
                    sb.append("Θητεία στην μονάδα ");
                    sb.append(serviceAllocation.getSourceUnit().getTitle());
                    sb.append(" τυπου ");
                    sb.append(CoreUtils.getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
                    sb.append(" απο τις ");
                    sb.append(df.format(serviceAllocation.getEstablished()));
                    sb.append(" για ");
                    sb.append(serviceAllocation.getWorkingHoursOnServicingPosition());
                    sb.append(" ώρες.");
                    cdr.setComment(sb.toString());
                    cdr.setSpecialization(serviceAllocation.getEmployee().getLastSpecialization());
                    cdr.setEmployee(serviceAllocation.getEmployee());
                    cdr.setServiceAllocation(serviceAllocation);
                    cdr.setHours(serviceAllocation.getWorkingHoursOnServicingPosition());
                    cdr.setUnit(serviceAllocation.getServiceUnit());
                    cdr.setSchoolYear(currentSchoolYear);
                    entityManager.persist(cdr);
                    totalCDRsCreated++;
                    
                } else {
                    /* apply on target unit */
                    TeachingHourCDR cdr = new TeachingHourCDR();
                    cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
                    StringBuffer sb = new StringBuffer();
                    sb.append("Θητεία απο την μονάδα ");
                    sb.append(serviceAllocation.getSourceUnit().getTitle());
                    sb.append(" τυπου ");
                    sb.append(CoreUtils.getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
                    sb.append(" απο τις ");
                    sb.append(df.format(serviceAllocation.getEstablished()));
                    sb.append(" για ");
                    sb.append(serviceAllocation.getWorkingHoursOnServicingPosition());
                    sb.append(" ώρες.");
                    cdr.setComment(sb.toString());
                    cdr.setSpecialization(serviceAllocation.getEmployee().getLastSpecialization());
                    cdr.setEmployee(serviceAllocation.getEmployee());
                    cdr.setServiceAllocation(serviceAllocation);
                    cdr.setHours(serviceAllocation.getWorkingHoursOnServicingPosition());
                    cdr.setUnit(serviceAllocation.getServiceUnit());
                    cdr.setSchoolYear(currentSchoolYear);
                    entityManager.persist(cdr);
                    totalCDRsCreated++;
                    
                    /* apply on source unit */
        
                    cdr = new TeachingHourCDR();
                    cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
                    sb = new StringBuffer();
                    sb.append("Θητεία στην μονάδα ");
                    sb.append(serviceAllocation.getServiceUnit().getTitle());
                    sb.append(" τυπου ");
                    sb.append(CoreUtils.getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
                    sb.append(" απο τις ");
                    sb.append(df.format(serviceAllocation.getEstablished()));
                    sb.append(" για ");
                    sb.append(serviceAllocation.getWorkingHoursOnServicingPosition());
                    sb.append(" ώρες.");
                    cdr.setComment(sb.toString());
                    cdr.setSpecialization(serviceAllocation.getEmployee().getLastSpecialization());
                    cdr.setEmployee(serviceAllocation.getEmployee());
                    cdr.setServiceAllocation(serviceAllocation);
                    cdr.setHours(serviceAllocation.getWorkingHoursOnRegularPosition());
                    cdr.setUnit(serviceAllocation.getSourceUnit());
                    cdr.setSchoolYear(currentSchoolYear);
                    entityManager.persist(cdr);
                    totalCDRsCreated++;
                }
                
            } catch(Exception ex) {
                String msg = String.format("unhandled exception '%s' while handling service allocation %s", ex.getMessage(), serviceAllocation);
                error(msg, ex);
                throw new RuntimeException(msg, ex);
            }
            
            
          }
        
        em.flush(); /* flush */
        
        /* WE NEED TO ADD LEAVES */
        
        /* Leaves are a special case. For each employee with a leave we will compute his hours distribution in all units. For each 
         * unit for which the employee has teaching hours > 0 we will add a LEAVE CDR with negative hours
         */
        
        Collection<Leave> activeLeaves = coreSearching.getActiveLeaves(em);
        info("found #0 totally active leaves.", activeLeaves.size());
        for(Leave activeLeave : activeLeaves) {
            Employee employeeWithLeave = activeLeave.getEmployee();
            /* fix the common leave message */
            StringBuffer sb = new StringBuffer();
            sb.append("Άδεια τύπου  ");
            sb.append(CoreUtils.getLocalizedMessage(activeLeave.getLeaveType().getKey()));
            sb.append(" απο τις ");
            sb.append(df.format(activeLeave.getEstablished()));
            sb.append(" μέχρι και  ");
            sb.append(df.format(activeLeave.getDueTo()));
            
            Collection<Object[]> o = getEntityManager().createQuery("SELECT t.unit.id, SUM(t.hours) FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee GROUP BY (t.unit)").setParameter("schoolYear", currentSchoolYear).setParameter("employee", employeeWithLeave).getResultList();
            for(Object[] r : o) {
                Long hours = (Long)r[1];
                if(hours.longValue()> 0) {
                    TeachingHourCDR cdr = new TeachingHourCDR();
                    cdr.setCdrType(TeachingHourCDRType.LEAVE);
                    cdr.setComment(sb.toString());
                    cdr.setSpecialization(employeeWithLeave.getLastSpecialization());
                    cdr.setEmployee(employeeWithLeave);
                    /* */
                    cdr.setHours(new Integer(hours.intValue() * -1));
                    cdr.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
                    cdr.setSchoolYear(currentSchoolYear);
                    cdr.setUnit(getEntityManager().find(Unit.class, r[0]));
                    cdr.setLeave(activeLeave);
                    entityManager.persist(cdr);
                    totalCDRsCreated++;
                }
            }
         }

        em.flush();
        long finished = System.currentTimeMillis();

        info("generated total CDRs #0 after #1 [ms]", totalCDRsCreated, (finished - started));

    }

}
