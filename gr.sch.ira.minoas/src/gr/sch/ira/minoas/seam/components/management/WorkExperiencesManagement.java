package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.WorkExperience;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.WorkExperienceCalculation;
import gr.sch.ira.minoas.seam.components.WorkExperienceCalculation.EmployeeWorkExperienceHelper;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.WorkExperienceHome;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.record.formula.functions.Days360;
import org.apache.poi.ss.usermodel.DateUtil;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "workExperiencesManagement")
@Scope(ScopeType.PAGE)
public class WorkExperiencesManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	@In(required=false)
	private WorkExperienceHome workExperienceHome;
	
	private EmployeeType fooEmployeeType;
	
	@In(required=true, create=true)
	private WorkExperienceCalculation workExperienceCalculation;
	
	
	/**
	 * Employees current leave history
	 */
	@DataModel(scope=ScopeType.PAGE, value="workExperienceHistory")
	private Collection<WorkExperience> workExperienceHistory = null;
	
	@Factory(value="workExperienceHistory",autoCreate=true)
	public void constructWorkExperienceHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing work experience history for employee #0", employee);
	    setWorkExperienceHistory(coreSearching.getWorkExperienceHistory(employee));
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
	
    /**
     * @return the workExperienceHistory
     */
    public Collection<WorkExperience> getWorkExperienceHistory() {
        return workExperienceHistory;
    }

    /**
     * @param workExperienceHistory the workExperienceHistory to set
     */
    public void setWorkExperienceHistory(Collection<WorkExperience> workExperienceHistory) {
        this.workExperienceHistory = workExperienceHistory;
    }


	/**
	 * @return the fooEmployeeType
	 */
	public EmployeeType getFooEmployeeType() {
		return fooEmployeeType;
	}



	/**
	 * @param fooEmployeeType the fooEmployeeType to set
	 */
	public void setFooEmployeeType(EmployeeType fooEmployeeType) {
		this.fooEmployeeType = fooEmployeeType;
	}



	public String modifyWorkExperience() {
        if(workExperienceHome != null && workExperienceHome.isManaged()) {
            info("trying to modify work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
            	facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                //	Αν η προϋπηρεσία είναι Διδακτική τότε θα είναι και Εκπαιδευτική
            	if(workExp.getTeaching())
            		workExp.setEducational(true);
            	
                workExperienceHome.update();
                workExperienceCalculation.updateEmployeeExperience(getEmployeeHome().getInstance());
                info("employee's #0 work experience #1 has been updated!", workExperienceHome.getInstance().getEmployee(), workExperienceHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
            }
            
            
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is not managed, thus #1 work experience can't be modified.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    public String deleteWorkExperience() {
        if(workExperienceHome != null && workExperienceHome.isManaged()) {
            info("trying to delete work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            workExp.setActive(Boolean.FALSE);
            workExp.setDeleted(Boolean.TRUE);
            workExp.setDeletedOn(new Date());
            workExp.setDeletedBy(getPrincipal());
            workExperienceHome.update();
            workExperienceCalculation.updateEmployeeExperience(getEmployeeHome().getInstance());
            constructWorkExperienceHistory();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is not managed, thus #1 work experience can't be deleted.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    
    }
    @Transactional
    public String createWorkExperience() {
        if(workExperienceHome != null && (!workExperienceHome.isManaged())) {
            info("trying to modify work experience #0", workExperienceHome);
            /* check if the work experience dates are allowed. */
            WorkExperience workExp = workExperienceHome.getInstance();
            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
                facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις εισάγατε, δεν είναι αποδεκτές.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                info("employee's #0 work experience #1 has been updated!", workExperienceHome.getInstance().getEmployee(), workExperienceHome.getInstance());
                
                workExp.setActive(Boolean.TRUE);
                workExp.setEmployee(getEmployeeHome().getInstance());
                workExp.setInsertedBy(getPrincipal());
                workExp.setInsertedOn(new Date());
                
                workExp.setCalendarExperienceDays(CoreUtils.getDatesDifference(workExp.getFromDate(), workExp.getToDate()));
                
                //	Αν η νέα προϋπηρεσία είναι Διδακτική τότε θα είναι και Εκπαιδευτική
                if(workExp.getTeaching())
            		workExp.setEducational(true);
                
//                if(workExp.getType().getId() == 4 || workExp.getType().getId() == 5) {
//                	
//                } else {
//                	
//                }
//                
//                //	If Employee is HOURLY_PAID, calculate his actual work experience in days based on his mandatoryHours and his hours worked.
//                if((workExp.getActualDays() == null || workExp.getActualDays() == 0) && 
//                		(workExp.getNumberOfWorkExperienceHours() !=null && workExp.getNumberOfWorkExperienceHours() != 0) && 
//                		(workExp.getMandatoryHours() !=null) && workExp.getMandatoryHours() != 0) {
//                	workExp.setActualDays((int)Math.ceil((((float)(workExp.getNumberOfWorkExperienceHours()*6)/workExp.getMandatoryHours())*30)/25));
//                }
                workExperienceHome.persist();
                workExperienceCalculation.updateEmployeeExperience(getEmployeeHome().getInstance());
                constructWorkExperienceHistory();
                
                return ACTION_OUTCOME_SUCCESS;
            }
            
            
        } else {
            facesMessages.add(Severity.ERROR, "work experience home #0 is managed, thus #1 work experience can't be created.", workExperienceHome, workExperienceHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    
    
    public void reCalculateActualDays() {
    	
    	WorkExperience workExp = workExperienceHome.getInstance();
    	if(workExp.getType().getId() == 3 || workExp.getType().getId() == 5) {	// Άν Ωρομίσθιος ή ΝΠΔΔ
    		if(workExp.getType().getId() == 5 && workExp.getNumberOfWorkExperienceHours() > 0) {			//	Αν ΝΠΔΔ και έχει ώρες ωρομισθίας
    			workExp.setActualDays((int)Math.round((((float)(workExp.getNumberOfWorkExperienceHours()*6)/workExp.getMandatoryHours())*30)/25));
    		} else if(workExp.getType().getId() == 5 && workExp.getNumberOfWorkExperienceHours() == 0) {	//	Αν ΝΠΔΔ και ΔΕΝ έχει ώρες ωρομισθίας
    			Date dateFrom = workExp.getFromDate() != null ? DateUtils.truncate(workExp.getFromDate(), Calendar.DAY_OF_MONTH) : null;
    	        Date dateTo = workExp.getToDate() != null ? DateUtils.truncate(workExp.getToDate(), Calendar.DAY_OF_MONTH) : null;
    			workExp.setActualDays((CoreUtils.DatesDifferenceIn360DaysYear(dateFrom, dateTo)*workExp.getFinalWorkingHours())/workExp.getMandatoryHours());
    		} else {	//	Αν Ωρομίσθιος
    			workExp.setActualDays((int)Math.ceil((((float)(workExp.getNumberOfWorkExperienceHours()*6)/workExp.getMandatoryHours())*30)/25));
    		}
        } else { //	Όλοι οι υπόλοιποι τύποι εκπαιδευτικού
        	Date dateFrom = workExp.getFromDate() != null ? DateUtils.truncate(workExp.getFromDate(), Calendar.DAY_OF_MONTH) : null;
	        Date dateTo = workExp.getToDate() != null ? DateUtils.truncate(workExp.getToDate(), Calendar.DAY_OF_MONTH) : null;
			workExp.setActualDays((CoreUtils.DatesDifferenceIn360DaysYear(dateFrom, dateTo)*workExp.getFinalWorkingHours())/workExp.getMandatoryHours());
        }
    }

    

    /* this method is called when the user clicks the "add new leave" */
    public void prepareNewWorkExperience() {
    	workExperienceHome.clearInstance();
    	WorkExperience workExp = workExperienceHome.getInstance();
    	workExp.setFromDate(new Date());
    	workExp.setToDate(new Date());
    	workExp.setComment(null);
    	workExp.setEmployee(employeeHome.getInstance());
    	
    	workExp.setActualDays(new Integer(0));
    	workExp.setNumberOfWorkExperienceHours(new Integer(0));
    	workExp.setMandatoryHours(new Integer(21));
    	workExp.setFinalWorkingHours(new Integer(21));
    }
    
    public void changedEmployeeType () {
    	WorkExperience workExp = workExperienceHome.getInstance();
    	
    	workExp.setNumberOfWorkExperienceHours(new Integer(0));
    	workExp.setCalendarExperienceDays(new Integer(0));
    	workExp.setActualDays(new Integer(0));
    	workExp.setMandatoryHours(new Integer(21));
    	workExp.setFinalWorkingHours(new Integer(21));
    	
    	
    	//
    	//	Depending of work experience type set educational (Εκπαιδευτική) and teaching(διδακτική) as true
    	//	in the cases of Ωρομίσθιος & Αναπληρωτής
    	//
    	switch (workExp.getType().getId()) {
			case 3: case 4:
				workExp.setEducational(true);
				workExp.setTeaching(true);
				break;
	
			default:
				workExp.setEducational(false);
				workExp.setTeaching(false);
				break;
		}
    }
    
    public void silentlyComputeDateDifference() {
    	WorkExperience workExp = workExperienceHome.getInstance();
    	Date dateFrom = workExp.getFromDate() != null ? DateUtils.truncate(workExp.getFromDate(), Calendar.DAY_OF_MONTH) : null;
        Date dateTo = workExp.getToDate() != null ? DateUtils.truncate(workExp.getToDate(), Calendar.DAY_OF_MONTH) : null;
        if( dateFrom!=null && dateTo !=null ) {
        	workExp.setCalendarExperienceDays(CoreUtils.getDatesDifference(dateFrom, dateTo));
        	workExp.setActualDays(CoreUtils.DatesDifferenceIn360DaysYear(dateFrom, dateTo));
        } else {
        	workExp.setCalendarExperienceDays(new Integer(0));
        	workExp.setActualDays(new Integer(0));
        }
    }
    
    public String computeEmployeeWorkExperience() {
        try {
            if (employeeHome.isManaged()) {
                Employee employee = employeeHome.getInstance();
                EmployeeInfo employeeInfo = employee.getEmployeeInfo();
                if(employeeInfo!=null) {
                    workExperienceCalculation.updateEmployeeExperience(employee);
                    info("updated employee #0 experience values [educational : #1, teaching : #2, total #3] and total service #4.", employee,employeeInfo.getSumOfEducationalExperience(), employeeInfo.getSumOfTeachingExperience(), employeeInfo.getSumOfExperience(), employeeInfo.getTotalWorkService());
                    getEntityManager().flush();
                    return ACTION_OUTCOME_SUCCESS;
                } else {
                    facesMessages.add(Severity.ERROR, "Ο υπάλληλος δεν έχει EMPLOYEE_INFO");
                    return ACTION_OUTCOME_FAILURE;
                }
            } else {
                facesMessages.add(Severity.ERROR, "employeehome is not managed.");
                return ACTION_OUTCOME_FAILURE;
            }
        } catch (Exception ex) {
            error(ex);
            facesMessages.add(Severity.ERROR, String.format("Αποτυχιά ενημέρωσεις, λόγω '%s'", ex.getMessage()));
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
}
