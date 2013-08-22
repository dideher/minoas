package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeExclusion;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.SpecialAssigment;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.EmployeeMergeRequest;
import gr.sch.ira.minoas.seam.components.home.EmployeeExclusionHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmploymentHome;
import gr.sch.ira.minoas.seam.components.home.RegularEmployeeInfoHome;
import gr.sch.ira.minoas.seam.components.home.SpecialAssigmentHome;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeWorkExperienceItem;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SpecialAssigmentReportItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeManagement")
@Scope(ScopeType.PAGE)
public class EmployeeManagement extends BaseDatabaseAwareSeamComponent {
    
    public class EmployeeCDRReportItem {
        
        private String comment;
        
        private Date established;
        
        private Date dueTo;
        
        private String schoolUnit;
        
        private String schoolID;
        
        private Integer hours;
        
        private String cdrType;
        
        private String cdrTypeKey;
        
        private TeachingHourCDR cdr;
        /**
         * 
         */
        public EmployeeCDRReportItem(TeachingHourCDR cdr) {
            super();
            this.cdr = cdr;
            this.comment = cdr.getComment();
            this.schoolID = cdr.getUnit().getId();
            this.schoolUnit = cdr.getUnit().getTitle();
            this.hours = cdr.getHours();
            this.cdrType = cdr.getCdrType().toString();
            this.cdrTypeKey = cdr.getCdrType().getKey();
        }
        /**
         * @return the comment
         */
        public String getComment() {
            return comment;
        }
        /**
         * @param comment the comment to set
         */
        public void setComment(String comment) {
            this.comment = comment;
        }
        /**
         * @return the established
         */
        public Date getEstablished() {
            return established;
        }
        /**
         * @param established the established to set
         */
        public void setEstablished(Date established) {
            this.established = established;
        }
        /**
         * @return the dueTo
         */
        public Date getDueTo() {
            return dueTo;
        }
        /**
         * @param dueTo the dueTo to set
         */
        public void setDueTo(Date dueTo) {
            this.dueTo = dueTo;
        }
        /**
         * @return the schoolUnit
         */
        public String getSchoolUnit() {
            return schoolUnit;
        }
        /**
         * @param schoolUnit the schoolUnit to set
         */
        public void setSchoolUnit(String schoolUnit) {
            this.schoolUnit = schoolUnit;
        }
        /**
         * @return the schoolID
         */
        public String getSchoolID() {
            return schoolID;
        }
        /**
         * @param schoolID the schoolID to set
         */
        public void setSchoolID(String schoolID) {
            this.schoolID = schoolID;
        }
        /**
         * @return the hours
         */
        public Integer getHours() {
            return hours;
        }
        /**
         * @param hours the hours to set
         */
        public void setHours(Integer hours) {
            this.hours = hours;
        }
        /**
         * @return the cdrType
         */
        public String getCdrType() {
            return cdrType;
        }
        /**
         * @param cdrType the cdrType to set
         */
        public void setCdrType(String cdrType) {
            this.cdrType = cdrType;
        }
        /**
         * @return the cdr
         */
        public TeachingHourCDR getCdr() {
            return cdr;
        }
        /**
         * @param cdr the cdr to set
         */
        public void setCdr(TeachingHourCDR cdr) {
            this.cdr = cdr;
        }
        /**
         * @return the cdrTypeKey
         */
        public String getCdrTypeKey() {
            return cdrTypeKey;
        }
        /**
         * @param cdrTypeKey the cdrTypeKey to set
         */
        public void setCdrTypeKey(String cdrTypeKey) {
            this.cdrTypeKey = cdrTypeKey;
        }
        
    }
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    
    private String inputTextFieldHelper1 = ""; 
    
    private String inputTextFieldHelper2 = "";
    
    private String inputTextFieldHelper3 = "";
    
    private String inputTextFieldHelper4 = "";
    
    private String inputTextFieldHelper5 = "";
    
    private String inputTextFieldHelper6 = "";
    
    @RequestParameter(value="actionVariation")
    private String actionVariation;
  
	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	@In(required = true, create = true)
	private SpecialAssigmentHome specialAssigmentHome;
	
	@In(required = true, create = true)
	private RegularEmployeeInfoHome regularEmployeeInfoHome;
	
	@In(required = true, create = true)
	private EmploymentHome employmentHome;

	@In(required = true, create = true)
	private EmployeeExclusionHome employeeExclusionHome;
	
	@In(required = true, create = true)
	private EmployeeMergeRequest employeeMergeRequest;
	
	@DataModel(value="employeeCurrentStatusItems")
	private Collection<EmployeeCDRReportItem> employeeCurrentStatusItems;
	
	@DataModel(value="employeeCurrentHoursDistributionItems")
	private Collection<Map<String, Object>> employeeCurrentHoursDistributionItems;
	
	@DataModel(value="employeeWorkExperienceItems")
	private Collection<EmployeeWorkExperienceItem> employeeWorkExperienceItems;
	
	@DataModel(value="employeeLeaveItems")
	private Collection<LeaveReportItem> employeeLeaveItems;
	
	@DataModel(value="employeeSpecialAssigmentItems")
    private Collection<SpecialAssigmentReportItem> employeeSpecialAssigmentItems;
	
	@DataModel(value="duplicateEmployeeList")
	private Collection<EmployeeReportItem> duplicateEmployeeList = new ArrayList<EmployeeReportItem>();
	
	/**
	 * @return the employeeWorkExperienceItems
	 */
	public Collection<EmployeeWorkExperienceItem> getEmployeeWorkExperienceItems() {
		return employeeWorkExperienceItems;
	}

	/**
	 * @param employeeWorkExperienceItems the employeeWorkExperienceItems to set
	 */
	public void setEmployeeWorkExperienceItems(
			Collection<EmployeeWorkExperienceItem> employeeWorkExperienceItems) {
		this.employeeWorkExperienceItems = employeeWorkExperienceItems;
	}

	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}
	
	@Transactional(TransactionPropagationType.REQUIRED)
	public String removeEmployeeFromExclusionList() {
		if (getEmployeeExclusionHome().isManaged()) {
			getEmployeeHome().getInstance().setExclusion(null);
			getEmployeeHome().update();
			getEmployeeExclusionHome().remove();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee Exclusion #0 is not managed.", getEmployeeExclusionHome());
			return ACTION_OUTCOME_FAILURE;
		}

	}
	
	@Factory(value="employeeCurrentHoursDistributionItems")
    public void constructEmployeeCurrentHoursDistributionReport() {
	    SchoolYear currentSchoolYear =  getCoreSearching().getActiveSchoolYear(getEntityManager());
        Employee employee = getEmployeeHome().getInstance();

        String query2 = "SELECT t.unit.id, SUM(t.hours),MAX(t.unit.title) FROM TeachingHourCDR t " +
        "WHERE t.schoolYear=:schoolYear " +
        "AND t.employee=:employee " +
        "GROUP BY(t.unit.id)";
         
        Collection<Object[]> o2  = (Collection<Object[]>)getEntityManager().createQuery(query2).setParameter("schoolYear", currentSchoolYear).setParameter("employee", employee).getResultList();
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
        for(Object[] oo : o2) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("school", getEntityManager().find(Unit.class, oo[0]));
            item.put("unitHours", oo[1]);
            data.add(item);
        }
        setEmployeeCurrentHoursDistributionItems(data);
    }
    
	@Factory(value="employeeCurrentStatusItems")
	public void constructEmployeeCurrentStatusReport() {
	    SchoolYear currentSchoolYear =  getCoreSearching().getActiveSchoolYear(getEntityManager());
	    Employee employee = getEmployeeHome().getInstance();
	    /* fetch all non logistics employee's CDRs */
	    Collection<TeachingHourCDR> employeeCDRs = getCoreSearching().getEmployeeNonLogisticTeachingHoursCDR(entityManager, currentSchoolYear, employee);
	    employeeCurrentStatusItems = new ArrayList<EmployeeManagement.EmployeeCDRReportItem>(employeeCDRs.size());
	    for(TeachingHourCDR cdr : employeeCDRs) {
	        employeeCurrentStatusItems.add(new EmployeeManagement.EmployeeCDRReportItem(cdr));
	    }
	    /* employee leave cdrs  - if they exists just add the first one */
	    Collection<TeachingHourCDR> employeeLeaveCDRs = getCoreSearching().getEmployeeTeachingHoursCDRsOfType(getEntityManager(), currentSchoolYear, employee, TeachingHourCDRType.LEAVE);
	    if(employeeLeaveCDRs.size()>0) {
	        employeeCurrentStatusItems.add(new EmployeeManagement.EmployeeCDRReportItem(employeeLeaveCDRs.iterator().next()));
	    }
	    setEmployeeCurrentStatusItems(employeeCurrentStatusItems);
	}
	
	
	@Factory(value="employeeLeaveItems")
    public void constructEmployeeLeavesHistory() {
        Employee employee = getEmployeeHome().getInstance();
        Collection<EmployeeLeave> employeeLeavesHistory = getCoreSearching().getEmployeeLeaves2(employee);
        List<LeaveReportItem> employeeLeaveItems = new ArrayList<LeaveReportItem>(employeeLeavesHistory.size());
        for(EmployeeLeave leave : employeeLeavesHistory) {
            employeeLeaveItems.add(new LeaveReportItem(leave));
        }
        setEmployeeLeaveItems(employeeLeaveItems);
    }
	
	/**
	 * This operation should be removed when merged with gand's work.
	 * @param entityManager
	 * @param employee
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<WorkExperience> getEmployeeWorkExperience(EntityManager entityManager,
            Employee employee) {
        List<WorkExperience> return_value = entityManager.createQuery(
                "SELECT w FROM WorkExperience w WHERE w.employee=:employee AND w.active IS TRUE")
                .setParameter("employee", employee).getResultList();
        return return_value;
    }
	
	
	
	@Factory(value="employeeWorkExperienceItems")
	public void constructEmployeeWorkExperienceHistory() {
		Employee employee = getEmployeeHome().getInstance();
	    Collection<WorkExperience> employeeExperience = getEmployeeWorkExperience(getEntityManager(), employee);
	    employeeWorkExperienceItems = new ArrayList<EmployeeWorkExperienceItem>(employeeExperience.size());
	    for(WorkExperience experience : employeeExperience) {
	    	employeeWorkExperienceItems.add(new EmployeeWorkExperienceItem(experience));
	    }
	}
	
	@Factory(value="employeeSpecialAssigmentItems")
	public void constructSpecialAssigmentItems() {
	    Employee employee = getEmployeeHome().getInstance();
        Collection<SpecialAssigment> employeeSpecialAssigments = getCoreSearching().getEmployeeSpecialAssigments(getEntityManager(), employee);
        employeeSpecialAssigmentItems  = new ArrayList<SpecialAssigmentReportItem>(employeeSpecialAssigments.size());
        for(SpecialAssigment specialAssigment : employeeSpecialAssigments) {
            employeeSpecialAssigmentItems.add(new SpecialAssigmentReportItem(specialAssigment));
        }
	}
	
	@Transactional(TransactionPropagationType.REQUIRED)
	public String mergeEmployees() {
		EmployeeHome sourceEmployeeHome = new EmployeeHome();
		sourceEmployeeHome.setId(getEmployeeMergeRequest().getSourceEmployee().getId());
		
		EmployeeHome targetEmployeeHome = new EmployeeHome();
		targetEmployeeHome.setId(getEmployeeMergeRequest().getTargetEmployee().getId());
		if(sourceEmployeeHome.isManaged() && targetEmployeeHome.isManaged()) {
			info("trying to merge employee #0 to #1", sourceEmployeeHome.getInstance(), targetEmployeeHome.getInstance());
			
			/* employees must not be same */
			if(sourceEmployeeHome.getId().equals(targetEmployeeHome.getId())) {
				facesMessages.add(Severity.ERROR, "Δεν μπορείτε να συνχωνεύσεται τον εκπαιδευτικό στον εαυτό του. Kάντε ενα διάλλειμα για καφε και τσιγάρο.");
				return ACTION_OUTCOME_FAILURE;
			}
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee #0 is not managed.");
			return ACTION_OUTCOME_FAILURE;
		}
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public String addEmployeeToExclusionList() {
		if (getEmployeeHome().isManaged()) {
			getEmployeeExclusionHome().clearInstance();
			EmployeeExclusion instance = getEmployeeExclusionHome().getInstance();
			instance.setEmployee(getEmployeeHome().getInstance());
			getEmployeeExclusionHome().persist();
			getEmployeeHome().getInstance().setExclusion(instance);
			getEmployeeHome().update();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR, "Employee #0 is not managed.", getEmployeeHome());
			return ACTION_OUTCOME_FAILURE;
		}
	}
	
	/* this method is called when the user clicks the "add new employee special assigment" */
    public void prepareForNewSpecialAssigment() {
        specialAssigmentHome.clearInstance();
        /* if the employee has an current employment prefill the value */
        Employment emp = getEmployeeHome().getInstance().getCurrentEmployment();
        if(emp!=null) {
            inputTextFieldHelper1 = emp.getSchool().getTitle();
            specialAssigmentHome.getInstance().setUnit(emp.getSchool());
        }
    }
    
    public void prepareForEmployeeTermination() {
        inputTextFieldHelper1 = null;
        Employee e = employeeHome.getInstance();
        if(e!=null && e.getActive() == true) {
            e.setTerminationDate(new Date(System.currentTimeMillis()));
        }
            
    }
    
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public String terminateEmployee() {
        
        
        if (getEmployeeHome().isManaged() && getEmployeeHome().getInstance().getActive()) {
            Employee e = getEmployeeHome().getInstance();
            if(e.getTerminationDate() == null || e.getTerminationReason() == null) {
                facesMessages.add(Severity.ERROR, "Πρέπει να συμπληρώσετε την αιτία και την ημ/νια τερματισμού.", getEmployeeHome());
                
                return ACTION_OUTCOME_FAILURE;
            }
            
            
            
            /* Ok, proceed with the termination */
            Collection<EmployeeLeave> leaves = getCoreSearching().getEmployeeLeaves2(e);
            for(EmployeeLeave l : leaves) {
                if(l.getActive()) {
                    l.setActive(false);
                    l.setAutoCanceled(true);
                }
            }
            
            Collection<SpecialAssigment> sa = getCoreSearching().getEmployeeSpecialAssigments(entityManager, e);
            for(SpecialAssigment s : sa) {
                if(s.getActive()) {
                    s.setActive(false);
                }
            }
            
            Collection<Secondment> secondments = getCoreSearching().getEmployeeSecondments(e);
            for(Secondment s : secondments) {
                if(s.isActive()) {
                    s.setActive(false);
                    s.setAutoCanceled(true);
                }
            }
            
            Collection<Disposal> disposals = getCoreSearching().getEmployeeDisposals(entityManager, e);
            for(Disposal s : disposals) {
                if(s.isActive()) {
                    s.setActive(false);
                    s.setAutoCanceled(true);
                }
            }
            
            Collection<ServiceAllocation> serviceAllocations = getCoreSearching().getEmployeeServiceAllocation(entityManager, e);
            for(ServiceAllocation s : serviceAllocations) {
                if(s.getActive()) {
                    s.setActive(false);
                    s.setAutoCanceled(true);
                }
            }
            
            Collection<Employment> employments = getCoreSearching().getEmployeeEmployments(e);
            for(Employment em : employments) {
                if(em.getActive()) {
                    em.setActive(false);
                }
            }
            
            Collection<TeachingHourCDR> cdrs = getCoreSearching().getEmployeeTeachingHoursCDRs(entityManager, getCoreSearching().getActiveSchoolYear(entityManager), e);
            for(TeachingHourCDR c : cdrs) {
                entityManager.remove(c);
            }
            
            e.setActive(false);
            getEmployeeHome().update();
            
            constructEmployeeCurrentHoursDistributionReport();
            constructEmployeeCurrentStatusReport();
            constructEmployeeLeavesHistory();
            constructEmployeeWorkExperienceHistory();
            constructSpecialAssigmentItems();
            
            getEntityManager().flush();
            
        } else {
            facesMessages.add(Severity.ERROR, "Employee #0 is not managed or not active", getEmployeeHome());
            return ACTION_OUTCOME_FAILURE;
        }
        
        return ACTION_OUTCOME_FAILURE;
    }
    
   
    
    public void lalal() {
        
    }
    
    public void prepareForNewEmployee() {
        employeeHome.clearInstance();
        regularEmployeeInfoHome.clearInstance();
        employmentHome.clearInstance();
    }
    
	
	@Transactional(TransactionPropagationType.REQUIRED)
    public String addEmployeeSpecialAssigment() {
	    if (getEmployeeHome().isManaged() && !getSpecialAssigmentHome().isManaged()) {
	        Employee employee = getEmployeeHome().getInstance();
	        SpecialAssigment sa = getSpecialAssigmentHome().getInstance();
	        /* special assigment hours can be more than employee's mandatory working hours */
	        Employment employement = employee.getCurrentEmployment();
	        if(employement != null) {
	            Integer hours = employement.getMandatoryWorkingHours();
	            if(sa.getFinalWorkingHours()>hours) {
	                facesMessages.add(Severity.ERROR, String.format("Η ώρες της ειδικής ασχολίας δεν μπορούν να υπερβαίνουν το υποχρεωτικό ωράριο των '%d' συνολικών ωρών του εκπαιδευτικού.",hours));
	                return ACTION_OUTCOME_FAILURE;
	            }
	            
	            Collection<SpecialAssigment> specialAssigments = getCoreSearching().getEmployeeSpecialAssigments(getEntityManager(), employee);
	            int totalHours = 0;
	            for(SpecialAssigment as : specialAssigments) {
	                totalHours += as.getFinalWorkingHours();
	            }
	            if(totalHours>hours) {
	                facesMessages.add(Severity.ERROR, String.format("Το σύνολο των '%d' ωρών ειδικής ασχολίας δεν μπορούν να υπερβαίνουν το υποχρεωτικό ωράριο των '%d' ωρών του εκπαιδευτικού.",totalHours, hours));
                    return ACTION_OUTCOME_FAILURE;
	            }
	        }
	        
	        
	        sa.setInsertedBy(getPrincipal());
	        sa.setInsertedOn(new Date());
	        sa.setEmployee(employee);
	        sa.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
	        sa.setActive(Boolean.TRUE);
	        getSpecialAssigmentHome().persist();
	        getEntityManager().flush();
	        constructSpecialAssigmentItems(); /* update the list */
	        info("added new special assigment #0 for employee #1", sa, employee);
	        inputTextFieldHelper1 = "";
	        inputTextFieldHelper2 = "";
	        return ACTION_OUTCOME_SUCCESS;
	    } else {
            facesMessages.add(Severity.ERROR, "Employee #0 or is not managed or Special Assigment #1 IS managed.",getEmployeeHome());
            return ACTION_OUTCOME_FAILURE;
        }
	}
	
	
    public void searchForDuplicateEmployees() {
        Employee employee = employeeHome.getInstance();
        
        if (employee.getLastName() != null && employee.getLastName().length() > 4 && employee.getFirstName() != null &&
                employee.getFirstName().length() > 2) {
            String lastName = CoreUtils.getSearchPattern(employee.getLastName());
            String firstName = CoreUtils.getSearchPattern(employee.getFirstName());
            String fatherName = CoreUtils.getSearchPattern(employee.getFatherName());
            String motherName = CoreUtils.getSearchPattern(employee.getMotherName());
            Specialization specialization = employee.getLastSpecialization();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT e FROM Employee e WHERE ");

            /* last name and first name are not null nor empty */
            sb.append("lower(e.lastName) LIKE :lastName AND lower(e.firstName) LIKE :firstName ");

            if (fatherName.length() > 1)
                sb.append("AND lower(e.fatherName) LIKE :fatherName ");
            if (motherName.length() > 1)
                sb.append("AND lower(e.motherName) LIKE :motherName ");
            if (specialization != null)
                sb.append("AND e.lastSpecialization=:specialization ");
            
            Query query = getEntityManager().createQuery(sb.toString());

            query.setParameter("lastName", lastName);
            query.setParameter("firstName", firstName);
            if (fatherName.length() > 1)
                query.setParameter("fatherName", fatherName);
            if (motherName.length() > 1)
                query.setParameter("motherName", motherName);
            if (specialization != null)
                query.setParameter("specialization", specialization);
            
            List<Employee> duplicateEmployees = query.getResultList();

            Collection<EmployeeReportItem> result = new ArrayList<EmployeeReportItem>(duplicateEmployees.size());
            for (Employee e : duplicateEmployees) {
                result.add(new EmployeeReportItem(e));
            }
            this.duplicateEmployeeList = result;
        } 
        else {
            if (duplicateEmployeeList != null)
                duplicateEmployeeList.clear();
        }
    }
	
    
    
	@Transactional(TransactionPropagationType.REQUIRED)
    public String addNewEmployeeFromOtherPYSDE() {
        /*
         * we will quickly create an employee to be used for secondment
         */
        if(!employeeHome.isManaged() && !employmentHome.isManaged() && !regularEmployeeInfoHome.isManaged()) {
            SchoolYear currentYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
            Employee new_employee = employeeHome.getInstance();
            new_employee.setType(EmployeeType.REGULAR);
            new_employee.setActive(Boolean.TRUE);
            new_employee.setComment(String.format("Εισαγωγή εκπαιδευτικού απο άλλο ΠΥΣΔΕ '%s' κατά την σχολική χρονία '%s'.", new_employee.getCurrentPYSDE().getTitle(), currentYear));
            employeeHome.persist();
            
//            Employment newEmployment = employmentHome.getInstance();
//            
//            newEmployment.setActive(Boolean.TRUE);
//            newEmployment.setEmployee(new_employee);
//            newEmployment.setEstablished(new Date());
//            newEmployment.setFinalWorkingHours(21);
//            newEmployment.setMandatoryWorkingHours(21);
//            newEmployment.setSchoolYear(currentYear);
//           
//            newEmployment.setSchool(getEntityManager().find(School.class, getCoreSearching().getLocalPYSDE(getEntityManager()).getRepresentedByUnit().getId()));
//            newEmployment.setSpecialization(new_employee.getLastSpecialization());
//            newEmployment.setType(EmploymentType.REGULAR);
//            newEmployment.setEmployee(new_employee);
//            new_employee.setCurrentEmployment(newEmployment);
//            employmentHome.persist();
            
            RegularEmployeeInfo empInfo = regularEmployeeInfoHome.getInstance();
            new_employee.setRegularDetail(empInfo);
            empInfo.setEmployee(new_employee);
                
            
            regularEmployeeInfoHome.persist();
            
            facesMessages.add(Severity.INFO, String.format("Η καταχώρηση του εκπαιδευτικού '%s %s του %s απο το ΠΥΣΔΕ '%s' έγινε επιτυχώς' ",new_employee.getLastName(), new_employee.getFirstName(), new_employee.getFatherName(), new_employee.getCurrentPYSDE().getTitle()));
            return ACTION_OUTCOME_SUCCESS;
            
        } else {
            facesMessages.add(Severity.ERROR, "Employee #0 is managed!",employeeHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
	
	@Transactional(TransactionPropagationType.REQUIRED)
    public String removeEmployeeSpecialAssigment() {
	    if (getEmployeeHome().isManaged() && getSpecialAssigmentHome().isManaged()) {
            SpecialAssigment sa = getSpecialAssigmentHome().getInstance();
            sa.setActive(Boolean.FALSE);
            sa.setDeleted(Boolean.TRUE);
            sa.setDeletedBy(getPrincipal());
            sa.setDeletedOn(new Date());
            
            // delete all CDRs associated with the given special assigment */
            for(TeachingHourCDR cdr : sa.getSpecialAssigmentCDRs()) {
                cdr.setSpecialAssigment(null);
                getEntityManager().remove(cdr);
            }
            sa.getSpecialAssigmentCDRs().clear();
            getEntityManager().flush();
            constructSpecialAssigmentItems();
            info("removed special assigment #0 for employee #1", specialAssigmentHome.getInstance(), employeeHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        } else {
            facesMessages.add(Severity.ERROR, "Employee #0 or special assigment #1 is not managed.",employeeHome, specialAssigmentHome);
            return ACTION_OUTCOME_FAILURE;
        }
	}
	
	@Transactional(TransactionPropagationType.REQUIRED)
    public String updateEmployeeSpecialAssigment() {
	    return ACTION_OUTCOME_FAILURE;
    }
	
	@Transactional
	public String transferEmployee() {
		Employment newEmployment = new Employment();
		return null;
	}

	/**
	 * @return the employeeExclusionHome
	 */
	public EmployeeExclusionHome getEmployeeExclusionHome() {
		return employeeExclusionHome;
	}

	/**
	 * @param employeeExclusionHome the employeeExclusionHome to set
	 */
	public void setEmployeeExclusionHome(EmployeeExclusionHome employeeExclusionHome) {
		this.employeeExclusionHome = employeeExclusionHome;
	}

	/**
	 * @return the employeeMergeRequest
	 */
	public EmployeeMergeRequest getEmployeeMergeRequest() {
		return employeeMergeRequest;
	}

	/**
	 * @param employeeMergeRequest the employeeMergeRequest to set
	 */
	public void setEmployeeMergeRequest(EmployeeMergeRequest employeeMergeRequest) {
		this.employeeMergeRequest = employeeMergeRequest;
	}

    /**
     * @return the employeeCurrentStatusItems
     */
    public Collection<EmployeeCDRReportItem> getEmployeeCurrentStatusItems() {
        return employeeCurrentStatusItems;
    }

    /**
     * @param employeeCurrentStatusItems the employeeCurrentStatusItems to set
     */
    public void setEmployeeCurrentStatusItems(Collection<EmployeeCDRReportItem> employeeCurrentStatusItems) {
        this.employeeCurrentStatusItems = employeeCurrentStatusItems;
    }

    /**
     * @return the employeeLeaveItems
     */
    protected Collection<LeaveReportItem> getEmployeeLeaveItems() {
        return employeeLeaveItems;
    }

    /**
     * @param employeeLeaveItems the employeeLeaveItems to set
     */
    protected void setEmployeeLeaveItems(Collection<LeaveReportItem> employeeLeaveItems) {
        this.employeeLeaveItems = employeeLeaveItems;
    }

    /**
     * @return the employeeCurrentHoursDistributionItems
     */
    public Collection<Map<String, Object>> getEmployeeCurrentHoursDistributionItems() {
        return employeeCurrentHoursDistributionItems;
    }

    /**
     * @param employeeCurrentHoursDistributionItems the employeeCurrentHoursDistributionItems to set
     */
    public void setEmployeeCurrentHoursDistributionItems(
            Collection<Map<String, Object>> employeeCurrentHoursDistributionItems) {
        this.employeeCurrentHoursDistributionItems = employeeCurrentHoursDistributionItems;
    }
    
    protected boolean isVATValid(String vat) {
        if(vat!=null)
            return Pattern.matches("\\d{9}", vat);
        else return false;
    }
    
    protected boolean isRegularRegistryIDValid(String registryID) {
        if(registryID!=null)
            return Pattern.matches("\\d{6,7}", registryID);
        else return false;
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public String updateEmployeeBasicInfo() {
    	 /* there is a rare situation (due bugs of the past) to have a regular employee with no regular employee info */
        if(getEmployeeHome().isManaged() && (!getRegularEmployeeInfoHome().isManaged())) {
            RegularEmployeeInfo i = new RegularEmployeeInfo();
            i.setInsertedBy(getPrincipal());
            i.setInsertedOn(new Date());
            i.setEmployee(getEmployeeHome().getInstance());
            i.setRegistryID("1234567");
            getEntityManager().persist(i);
            getRegularEmployeeInfoHome().setInstance(i);
            getEmployeeHome().getInstance().setRegularDetail(i);
         }
        
        if (getEmployeeHome().isManaged() && getRegularEmployeeInfoHome().isManaged()) {
            Employee employee = getEmployeeHome().getInstance();
            RegularEmployeeInfo employeeInfo = getRegularEmployeeInfoHome().getInstance();
            if(employeeInfo.getRegistryID()!= "" && !isRegularRegistryIDValid(employeeInfo.getRegistryID())) {
                getFacesMessages().add(Severity.ERROR, "Ο αριθμός μητρώου '#0' που εισάγατε, δεν είναι έγκυρος", employeeInfo.getRegistryID());
                return ACTION_OUTCOME_FAILURE;
            }
            info("updated employee '#0' registry ID to '#1'", employee, employeeInfo.getRegistryID());
            
        } else {
            return ACTION_OUTCOME_FAILURE;
        }
    	
        if (getEmployeeHome().isManaged()) {
            Employee employee = getEmployeeHome().getInstance();
            
            if(employee.getVatNumber() != "" && !isVATValid(employee.getVatNumber())) {
                getFacesMessages().add(Severity.ERROR, "Το ΑΦΜ '#0' που εισάγατε, δεν είναι έγκυρο", employee.getVatNumber());
                return ACTION_OUTCOME_FAILURE;
            }
            info("updated employee '#0' ΑΦΜ to '#1'", employee, employee.getVatNumber());
            
            
            /* fix the current employment field */
            Employment currentEmployment = employee.getCurrentEmployment();
            if(currentEmployment!=null) {
                currentEmployment.setSpecialization(employee.getLastSpecialization());
            }
            
            /* if the employee is hourly paid, then it is quite possible
             * that the employee has more than one employments
             */
            if(employee.isHourlyPaidEmployee()) {
                SchoolYear currentSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
                Collection<Employment> employments = getCoreSearching().getEmployeeEmployments(employee, currentSchoolYear);
                for(Employment em : employments) {
                    em.setSpecialization(employee.getLastSpecialization());
                }
            }
            info("updated employee '#0' specialization to '#1'", employee, employee.getLastSpecialization());

        } else {
            return ACTION_OUTCOME_FAILURE;
        }
        
        getRegularEmployeeInfoHome().update();
        getEmployeeHome().update();
        getEntityManager().flush();
        return ACTION_OUTCOME_SUCCESS;
    }
    

    /**
     * @return the regularEmployeeInfoHome
     */
    public RegularEmployeeInfoHome getRegularEmployeeInfoHome() {
        return regularEmployeeInfoHome;
    }

    /**
     * @param regularEmployeeInfoHome the regularEmployeeInfoHome to set
     */
    public void setRegularEmployeeInfoHome(RegularEmployeeInfoHome regularEmployeeInfoHome) {
        this.regularEmployeeInfoHome = regularEmployeeInfoHome;
    }

    /**
     * @return the specialAssigmentHome
     */
    public SpecialAssigmentHome getSpecialAssigmentHome() {
        return specialAssigmentHome;
    }

    /**
     * @param specialAssigmentHome the specialAssigmentHome to set
     */
    public void setSpecialAssigmentHome(SpecialAssigmentHome specialAssigmentHome) {
        this.specialAssigmentHome = specialAssigmentHome;
    }

    /**
     * @return the employeeSpecialAssigmentItems
     */
    public Collection<SpecialAssigmentReportItem> getEmployeeSpecialAssigmentItems() {
        return employeeSpecialAssigmentItems;
    }

    /**
     * @param employeeSpecialAssigmentItems the employeeSpecialAssigmentItems to set
     */
    public void setEmployeeSpecialAssigmentItems(Collection<SpecialAssigmentReportItem> employeeSpecialAssigmentItems) {
        this.employeeSpecialAssigmentItems = employeeSpecialAssigmentItems;
    }

    /**
     * @return the inputTextFieldHelper1
     */
    public String getInputTextFieldHelper1() {
        return inputTextFieldHelper1;
    }

    /**
     * @param inputTextFieldHelper1 the inputTextFieldHelper1 to set
     */
    public void setInputTextFieldHelper1(String inputTextFieldHelper1) {
        this.inputTextFieldHelper1 = inputTextFieldHelper1;
    }

    /**
     * @return the inputTextFieldHelper2
     */
    public String getInputTextFieldHelper2() {
        return inputTextFieldHelper2;
    }

    /**
     * @param inputTextFieldHelper2 the inputTextFieldHelper2 to set
     */
    public void setInputTextFieldHelper2(String inputTextFieldHelper2) {
        this.inputTextFieldHelper2 = inputTextFieldHelper2;
    }

    /**
     * @return the inputTextFieldHelper3
     */
    public String getInputTextFieldHelper3() {
        return inputTextFieldHelper3;
    }

    /**
     * @param inputTextFieldHelper3 the inputTextFieldHelper3 to set
     */
    public void setInputTextFieldHelper3(String inputTextFieldHelper3) {
        this.inputTextFieldHelper3 = inputTextFieldHelper3;
    }

    /**
     * @return the inputTextFieldHelper4
     */
    public String getInputTextFieldHelper4() {
        return inputTextFieldHelper4;
    }

    /**
     * @param inputTextFieldHelper4 the inputTextFieldHelper4 to set
     */
    public void setInputTextFieldHelper4(String inputTextFieldHelper4) {
        this.inputTextFieldHelper4 = inputTextFieldHelper4;
    }

    /**
     * @return the inputTextFieldHelper5
     */
    public String getInputTextFieldHelper5() {
        return inputTextFieldHelper5;
    }

    /**
     * @param inputTextFieldHelper5 the inputTextFieldHelper5 to set
     */
    public void setInputTextFieldHelper5(String inputTextFieldHelper5) {
        this.inputTextFieldHelper5 = inputTextFieldHelper5;
    }

    /**
     * @return the inputTextFieldHelper6
     */
    public String getInputTextFieldHelper6() {
        return inputTextFieldHelper6;
    }

    /**
     * @param inputTextFieldHelper6 the inputTextFieldHelper6 to set
     */
    public void setInputTextFieldHelper6(String inputTextFieldHelper6) {
        this.inputTextFieldHelper6 = inputTextFieldHelper6;
    }

    

    /**
     * @return the duplicateEmployeeList
     */
    public Collection<EmployeeReportItem> getDuplicateEmployeeList() {
        return duplicateEmployeeList;
    }

    /**
     * @param duplicateEmployeeList the duplicateEmployeeList to set
     */
    public void setDuplicateEmployeeList(Collection<EmployeeReportItem> duplicateEmployeeList) {
        this.duplicateEmployeeList = duplicateEmployeeList;
    }

    /**
     * @return the actionVariation
     */
    public String getActionVariation() {
        return actionVariation;
    }

    /**
     * @param actionVariation the actionVariation to set
     */
    public void setActionVariation(String actionVariation) {
        this.actionVariation = actionVariation;
    }

    /**
     * @return the employmentHome
     */
    public EmploymentHome getEmploymentHome() {
        return employmentHome;
    }

    /**
     * @param employmentHome the employmentHome to set
     */
    public void setEmploymentHome(EmploymentHome employmentHome) {
        this.employmentHome = employmentHome;
    }

}
