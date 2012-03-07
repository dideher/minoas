package gr.sch.ira.minoas.seam.components.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeExclusion;
import gr.sch.ira.minoas.model.employee.Person;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.EmployeeMergeRequest;
import gr.sch.ira.minoas.seam.components.home.EmployeeExclusionHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.RegularEmployeeInfoHome;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeWorkExperienceItem;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
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
    

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	@In(required = true, create = true)
	private RegularEmployeeInfoHome regularEmployeeInfoHome;

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
        Collection<Leave> employeeLeavesHistory = getCoreSearching().getEmployeeLeaveHistoryWithCurrentActive(employee);
        List<LeaveReportItem> employeeLeaveItems = new ArrayList<LeaveReportItem>(employeeLeavesHistory.size());
        for(Leave leave : employeeLeavesHistory) {
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
			facesMessages.add(Severity.ERROR, "Employee #0 is not managed.");
			return ACTION_OUTCOME_FAILURE;
		}

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
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public String updateEmployeeSpecialization() {
        if (getEmployeeHome().isManaged()) {
            Employee employee = getEmployeeHome().getInstance();
            
            /* fix the current employement field */
            Employment currentEmployment = employee.getCurrentEmployment();
            if(currentEmployment!=null) {
                currentEmployment.setSpecialization(employee.getLastSpecialization());
            }
            
            /* if the employee is hourly paid, then it is quite possible
             * that the employee has more than one employments
             */
            if(employee.isHourlyPaid()) {
                SchoolYear currentSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
                Collection<Employment> employments = getCoreSearching().getEmployeeEmployments(employee, currentSchoolYear);
                for(Employement em : employments) {
                    em.setSpecialization(employee.getLastSpecialization());
                }
            }
            info("updated employee '#0' specialization", employee);
            getEmployeeHome().update();
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    protected boolean isVATValid(String vat) {
        if(vat!=null)
            return Pattern.matches("d{9}", vat);
        else return false;
    }
    
    protected boolean isRegularRegistryIDValid(String registryID) {
        if(registryID!=null)
            return Pattern.matches("d{6}", registryID);
        else return false;
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public String updateEmployeeBasicInfo() {
        if (getEmployeeHome().isManaged()) {
            Employee employee = getEmployeeHome().getInstance();
            if(!isVATValid(employee.getVatNumber())) {
                getFacesMessages().add(Severity.ERROR, "Το ΑΦΜ '#0' που εισάγατε, δεν είναι έγκυρο", employee.getVatNumber());
                return ACTION_OUTCOME_FAILURE;
            }
            info("updated employee '#0'", employee);
            getEmployeeHome().update();
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public String updateEmployeeRegularRegistry() {
        if (getEmployeeHome().isManaged() && getRegularEmployeeInfoHome().isManaged()) {
            Employee employee = getEmployeeHome().getInstance();
            RegularEmployeeInfo employeeInfo = getRegularEmployeeInfoHome().getInstance();
            if(!isRegularRegistryIDValid(employeeInfo.getRegistryID())) {
                getFacesMessages().add(Severity.ERROR, "Ο αριθμός μητρώου '#0' που εισάγατε, δεν είναι έγκυρος", employeeInfo.getRegistryID());
                return ACTION_OUTCOME_FAILURE;
            }
            info("updated employee '#0' registry ID to '#1'", employee, employeeInfo.getRegistryID());
            getEmployeeHome().update();
            getRegularEmployeeInfoHome().update();
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            return ACTION_OUTCOME_FAILURE;
        }
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

}
