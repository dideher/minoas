package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;

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
	    EntityManager em = getEntityManager();
	    SchoolYear currentSchoolYear = coreSearching.getActiveSchoolYear(em);
	    info("generating teaching hours CDR for current school year #0", currentSchoolYear);
	    
	    int totalCDRsCreated = 0;
	    /* first fetch all current CDRs and remove them */
	    Collection<TeachingHourCDR> oldCRDs = coreSearching.getTeachingHoursCDRs(em, currentSchoolYear);
	    if(oldCRDs.size()>0) {
	        info("found totally #0 old CRD(s) which will need to be deleted.", oldCRDs.size());
	        for(TeachingHourCDR cdr : oldCRDs) {
	            entityManager.remove(cdr);
	        }
	        info("successfully deleted totally #0 old CRD(s).", oldCRDs.size());
        }
	    
	    /* fetch all regular employments */
	    Collection<Employment> regularEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.REGULAR, currentSchoolYear);
	    for(Employment employment : regularEmployments) {
	        TeachingHourCDR cdr = new TeachingHourCDR();
	        cdr.setCdrType(TeachingHourCDRType.INITIAL);
	        cdr.setComment("Οργανική θέση Mόνιμου");
	        cdr.setEmployee(employment.getEmployee());
	        cdr.setEmployment(employment);
	        cdr.setHours(employment.getFinalWorkingHours());
	        cdr.setUnit(employment.getSchool());
	        cdr.setSchoolYear(currentSchoolYear);
	        entityManager.persist(cdr);
	        totalCDRsCreated++;
	    }
	    
	    /* fetch all deputy employments */
        Collection<Employment> deputyEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.DEPUTY, currentSchoolYear);
        for(Employment employment : deputyEmployments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.INITIAL);
            cdr.setComment("Οργανική θέση Αναπληρωτή");
            cdr.setEmployee(employment.getEmployee());
            cdr.setEmployment(employment);
            cdr.setHours(employment.getFinalWorkingHours());
            cdr.setUnit(employment.getSchool());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        
        /* fetch all hourly employments */
        Collection<Employment> hourlyEmployments = coreSearching.getEmploymentsOfType(em, EmploymentType.HOURLYBASED, currentSchoolYear);
        for(Employment employment : hourlyEmployments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.INITIAL);
            cdr.setComment("Θέση Ορομίσθιου");
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
        info("found #0 totally active secondments during school year #1", secondments.size(), currentSchoolYear);
        for(Secondment secondment : secondments) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.ADD);
            cdr.setComment("Απόσπαση απο");
            cdr.setEmployee(secondment.getEmployee());
            cdr.setSecondment(secondment);
            cdr.setHours(secondment.getFinalWorkingHours());
            cdr.setUnit(secondment.getTargetUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
            
            cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SUBTRACT);
            cdr.setComment("Απόσπαση σε");
            cdr.setEmployee(secondment.getEmployee());
            cdr.setSecondment(secondment);
            cdr.setHours(secondment.getFinalWorkingHours());
            cdr.setUnit(secondment.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        
        /* handle disposal */
        
        Collection<Disposal> disposals = coreSearching.getActiveDisposal(em, currentSchoolYear);
        info("found #0 totally active disposal during school year #1", disposals.size(), currentSchoolYear);
        for(Disposal disposal : disposals) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.ADD);
            cdr.setComment("Διάθεση απο");
            cdr.setEmployee(disposal.getEmployee());
            cdr.setDisposal(disposal);
            cdr.setHours(disposal.getHours());
            cdr.setUnit(disposal.getDisposalUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
            
            cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SUBTRACT);
            cdr.setComment("Διάθεση απο");
            cdr.setEmployee(disposal.getEmployee());
            cdr.setDisposal(disposal);
            cdr.setHours(disposal.getHours());
            Unit sourceUnit = disposal.getAffectedEmployment() != null ? disposal.getAffectedEmployment().getSchool() : disposal.getAffectedSecondment().getTargetUnit();
            cdr.setUnit(sourceUnit);
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        
        /* handle service allocation */
        
        Collection<ServiceAllocation> serviceAllocations = coreSearching.getActiveServiceAllocations(em, currentSchoolYear);
        info("found #0 totally active service allocations during school year #1", serviceAllocations.size(), currentSchoolYear);
        for(ServiceAllocation serviceAllocation : serviceAllocations) {
            TeachingHourCDR cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.ADD);
            cdr.setComment("Θητεία απο");
            cdr.setEmployee(serviceAllocation.getEmployee());
            cdr.setServiceAllocation(serviceAllocation);
            cdr.setHours(serviceAllocation.getWorkingHoursOnServicingPosition());
            cdr.setUnit(serviceAllocation.getServiceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
            
            cdr = new TeachingHourCDR();
            cdr.setCdrType(TeachingHourCDRType.SUBTRACT);
            cdr.setComment("Θητεία απο");
            cdr.setEmployee(serviceAllocation.getEmployee());
            cdr.setServiceAllocation(serviceAllocation);
            cdr.setHours(serviceAllocation.getWorkingHoursOnServicingPosition());
            cdr.setUnit(serviceAllocation.getSourceUnit());
            cdr.setSchoolYear(currentSchoolYear);
            entityManager.persist(cdr);
            totalCDRsCreated++;
        }
        
        
        /* WE NEED TO ADD LEAVES */
        
        

        
        em.flush();
	    long finished = System.currentTimeMillis();
	    
	    info("generated total CDRs #0 after #1 [ms]", totalCDRsCreated, (finished-started));
	    
	    
}

	

}
