package gr.sch.ira.minoas.seam.components.management;

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
import java.util.Collection;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;

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
        Collection<TeachingHourCDR> oldCRDs = coreSearching.getTeachingHoursCDRs(em, currentSchoolYear);
        int cdrsDeleted = 0;
        for (TeachingHourCDR cdr : oldCRDs) {
            entityManager.remove(cdr);
            cdrsDeleted++;
        }
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

            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }

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
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }

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
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }

        /* handle secodnments */

        Collection<Secondment> secondments = coreSearching.getActiveSecondments(em, currentSchoolYear);
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
            sb.append(" απο τις ");
            sb.append(df.format(secondment.getEstablished()));
            sb.append(" για ");
            sb.append(secondment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
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
            sb.append(" απο τις ");
            sb.append(df.format(secondment.getEstablished()));
            sb.append(" για ");
            sb.append(secondment.getFinalWorkingHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setEmployee(secondment.getEmployee());
            cdr.setSecondment(secondment);
            cdr.setHours((-1) * secondment.getFinalWorkingHours());
            cdr.setUnit(secondment.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }

        /* handle disposal */

        Collection<Disposal> disposals = coreSearching.getActiveDisposal(em, currentSchoolYear);
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
            sb.append(" απο τις ");
            sb.append(df.format(disposal.getEstablished()));
            sb.append(" για ");
            sb.append(disposal.getHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
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
            sb.append(" απο τις ");
            sb.append(df.format(disposal.getEstablished()));
            sb.append(" για ");
            sb.append(disposal.getHours());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setEmployee(disposal.getEmployee());
            cdr.setDisposal(disposal);
            cdr.setHours((-1) * disposal.getHours());
            cdr.setUnit(sourceUnit);
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;

        }

        /* handle service allocation */

        Collection<ServiceAllocation> serviceAllocations = coreSearching.getActiveServiceAllocations(em);
        info("found #0 totally active service allocations.", serviceAllocations.size());
        for(ServiceAllocation serviceAllocation : serviceAllocations) {
            if(serviceAllocation.getSourceUnit()==null) {
                /* this should never happen */
                warn("service allocation #0 has no source unit !!!!!!", serviceAllocation);
                break;
            }
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
            StringBuffer sb = new StringBuffer();
            sb.append("Θητεία απο την μονάδα ");
            sb.append(serviceAllocation.getSourceUnit().getTitle());
            sb.append(" απο τις ");
            sb.append(df.format(serviceAllocation.getEstablished()));
            sb.append(" για ");
            sb.append(serviceAllocation.getWorkingHoursOnServicingPosition());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
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
            sb.append(" απο τις ");
            sb.append(df.format(serviceAllocation.getEstablished()));
            sb.append(" για ");
            sb.append(serviceAllocation.getWorkingHoursOnServicingPosition());
            sb.append(" ώρες.");
            cdr.setComment(sb.toString());
            cdr.setEmployee(serviceAllocation.getEmployee());
            cdr.setServiceAllocation(serviceAllocation);
            cdr.setHours((-1) * serviceAllocation.getWorkingHoursOnServicingPosition());
            cdr.setUnit(serviceAllocation.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
          
                
        }

        /* WE NEED TO ADD LEAVES */
        
        Collection<Leave> activeLeaves = coreSearching.getActiveLeaves(em);
        info("found #0 totally active leaves.", activeLeaves.size());
        for(Leave activeLeave : activeLeaves) {
            Employee employeeWithLeave = activeLeave.getEmployee();
            /* fix the common leave message */
            StringBuffer sb = new StringBuffer();
            sb.append("Άδεια τύπου  ");
            sb.append(activeLeave.getLeaveType());
            sb.append(" απο τις ");
            sb.append(df.format(activeLeave.getEstablished()));
            sb.append(" μέχρι και  ");
            sb.append(df.format(activeLeave.getDueTo()));
            
            Collection<TeachingHourCDR> employeeCDRs = coreSearching.getEmployeeTeachingHoursCDRs(em, currentSchoolYear, employeeWithLeave);
            for(TeachingHourCDR employeeCDR : employeeCDRs) {
                TeachingHourCDR cdr = new TeachingHourCDR();
                cdr.setCdrType(TeachingHourCDRType.LEAVE);
                cdr.setComment(sb.toString());
                cdr.setEmployee(employeeWithLeave);
                cdr.setHours(0);
                cdr.setSchoolYear(currentSchoolYear);
                cdr.setUnit(employeeCDR.getUnit());
                cdr.setLeave(activeLeave);
                entityManager.persist(cdr);
                totalCDRsCreated++;
            }
         }

        em.flush();
        long finished = System.currentTimeMillis();

        info("generated total CDRs #0 after #1 [ms]", totalCDRsCreated, (finished - started));

    }

}
