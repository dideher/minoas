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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
        
        em.flush(); /* flush */
        
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
        
        em.flush(); /* flush */
        
        /* handle service allocation */

        Collection<ServiceAllocation> serviceAllocations = coreSearching.getActiveServiceAllocations(em);
        info("found #0 totally active service allocations.", serviceAllocations.size());
        for(ServiceAllocation serviceAllocation : serviceAllocations) {
            if(serviceAllocation.getSourceUnit()==null) {
                /* this should never happen */
                warn("service allocation #0 has no source unit !!!!!!", serviceAllocation);
                break;
            }
            
            /* service allocation is always associated with an employment */
            Employment relatedEmployment = serviceAllocation.getAffectedEmployment();
            Collection<TeachingHourCDR> employmentsCDRs = relatedEmployment.getEmploymentCDRs();
            /* Employment should have one only one CDR */
            if(employmentsCDRs.size()>1) {
                /* this should never happen */
                warn("logic error -> employment #0 is associated with more than one cdrs -> #1", relatedEmployment, employmentsCDRs);
                break;
            }
            TeachingHourCDR employmentCDR = employmentsCDRs.size() > 0 ? employmentsCDRs.iterator().next() : null;
            Integer hoursOnEmploymentCDR = employmentCDR != null ? employmentCDR.getHours() : 0;
            
            /* construct a special CDR to deduct the employment CDR hours */
            TeachingHourCDR specialEmploymentCDR = new TeachingHourCDR();
            specialEmploymentCDR.setCdrType(TeachingHourCDRType.GENERAL);
            StringBuffer sb = new StringBuffer();
            sb.append("Αντιλογισμός διδακτικών ωρών της σχέσης εργασίας από ");
            sb.append(df.format(relatedEmployment.getEstablished()));
            sb.append(" με υποχρεωτικό ωράριο ");
            sb.append(relatedEmployment.getFinalWorkingHours());
            sb.append(" ώρες λόγω θητείας.");
            specialEmploymentCDR.setComment(sb.toString());

            specialEmploymentCDR.setEmployee(relatedEmployment.getEmployee());
            specialEmploymentCDR.setEmployment(relatedEmployment);
            specialEmploymentCDR.setHours(relatedEmployment.getFinalWorkingHours());
            specialEmploymentCDR.setUnit(relatedEmployment.getSchool());
            specialEmploymentCDR.setSchoolYear(currentSchoolYear);
            relatedEmployment.getEmploymentCDRs().add(specialEmploymentCDR);
            entityManager.persist(specialEmploymentCDR);
            totalCDRsCreated++;
            
            /* apply on target unit */
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
            sb = new StringBuffer();
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
            cdr.setHours(serviceAllocation.getWorkingHoursOnRegularPosition());
            cdr.setUnit(serviceAllocation.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
          }
        
        em.flush(); /* flush */
        
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
            info("when handling leave #0 we found #1 releated CDRs", activeLeave, employeeCDRs.size());
            List<String> unitCache = new ArrayList<String>(employeeCDRs.size()); // use to store units for which we have inserted a leave cdr 
            
            for(TeachingHourCDR employeeCDR : employeeCDRs) {
                if(!unitCache.contains(employeeCDR.getUnit().getId())) {
                    info("related CDR #0", employeeCDR);
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
                    unitCache.add(employeeCDR.getUnit().getId());
                } else {
                    info("ignoring related CDR #0 since a counterpart has been already registered.", employeeCDR);
                }
                
            }
         }

        em.flush();
        long finished = System.currentTimeMillis();

        info("generated total CDRs #0 after #1 [ms]", totalCDRsCreated, (finished - started));

    }

}
