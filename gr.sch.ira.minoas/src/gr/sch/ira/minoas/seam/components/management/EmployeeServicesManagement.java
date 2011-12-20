package gr.sch.ira.minoas.seam.components.management;

import java.util.Date;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.ServiceHome;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;

import org.drools.process.command.GetSessionClockCommand;
import org.jboss.aspects.patterns.readwritelock.writeLockOperation;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.Unwrap;

@Name(value = "employeeServicesManagement")
@Scope(ScopeType.PAGE)
public class EmployeeServicesManagement extends BaseDatabaseAwareSeamComponent {
    
    
    public EmployeeServiceHelper getEmployeeServiceHelper() {
        info("dlfldfksdlfksdf ");
        return employeeServiceHelper;
    }
    
    protected EmployeeServiceHelper employeeServiceHelper = null;
    
    public class EmployeeServiceHelper {
        Boolean hasTeachingHours = Boolean.FALSE;
        
        Boolean employeeHasActiveSecondment = Boolean.FALSE;
        
        EmploymentReportItem employeeEmploymentReportItem;
        
        EmployeeReportItem employeeReportItem;

        /**
         * @return the hasTeachingHours
         */
        public Boolean getHasTeachingHours() {
            return hasTeachingHours;
        }

        /**
         * @param hasTeachingHours the hasTeachingHours to set
         */
        public void setHasTeachingHours(Boolean hasTeachingHours) {
            this.hasTeachingHours = hasTeachingHours;
        }

        /**
         * @return the employeeEmploymentReportItem
         */
        public EmploymentReportItem getEmployeeEmploymentReportItem() {
            return employeeEmploymentReportItem;
        }

        /**
         * @param employeeEmploymentReportItem the employeeEmploymentReportItem to set
         */
        public void setEmployeeEmploymentReportItem(EmploymentReportItem employeeEmploymentReportItem) {
            this.employeeEmploymentReportItem = employeeEmploymentReportItem;
        }

        /**
         * @return the employeeReportItem
         */
        public EmployeeReportItem getEmployeeReportItem() {
            return employeeReportItem;
        }

        /**
         * @param employeeReportItem the employeeReportItem to set
         */
        public void setEmployeeReportItem(EmployeeReportItem employeeReportItem) {
            this.employeeReportItem = employeeReportItem;
        }

        /**
         * @return the employeeHasActiveSecondment
         */
        public Boolean getEmployeeHasActiveSecondment() {
            return employeeHasActiveSecondment;
        }

        /**
         * @param employeeHasActiveSecondment the employeeHasActiveSecondment to set
         */
        public void setEmployeeHasActiveSecondment(Boolean employeeHasActiveSecondment) {
            this.employeeHasActiveSecondment = employeeHasActiveSecondment;
        }
    }
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required=true)
    private CoreSearching coreSearching;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;
	
	
	
	@In(required = true, create = true)
	private ServiceHome serviceHome;
	
	@Transactional
	public String createNewEmployeeService() {
	    return ACTION_OUTCOME_SUCCESS;
	}
	
	
	public void prepareForNewEmployeeService() {
	    info("preparing internal structures for new employee service");
	    if(serviceHome!=null)
	        serviceHome.clearInstance();
	    EmployeeServiceHelper tmpServiceHelper = new EmployeeServiceHelper();
	    tmpServiceHelper.setHasTeachingHours(Boolean.FALSE);
	    
	    /* construct the employment report item */
	    Employee employee = employeeHome.getInstance();
	    Employment currentEmployment = employee.getCurrentEmployment();
	    
	    Secondment currentSecondment = coreSearching.getEmployeeActiveSecondment(getEntityManager(), employee, new Date());
        /* if the employee has an active secondment report that ! */
	    tmpServiceHelper.setEmployeeHasActiveSecondment(currentSecondment!=null);
	    
	    if(currentSecondment!=null)
	        tmpServiceHelper.setEmployeeEmploymentReportItem(new EmploymentReportItem(currentSecondment));
	    else if(currentEmployment!=null)
	        tmpServiceHelper.setEmployeeEmploymentReportItem(new EmploymentReportItem(currentEmployment));
	    
	    this.employeeServiceHelper = tmpServiceHelper;
	    
	}
	
	/**
	 * Employees current leave history
	 */
//	@DataModel(scope=ScopeType.PAGE, value="employeeLeaveHistory")
//	private Collection<Leave> employeeLeaveHistory = null;
//	
//	@Factory(value="employeeActiveLeave")
//	public void findEmployeeActiveLeave() {
//	    Employee employee = getEmployeeHome().getInstance();
//	    setEmployeeActiveLeave(employee.getLeave());
//	}
//
//	
//	@Factory(value="employeeLeaveHistory",autoCreate=true)
//	public void constructLeaveHistory() {
//	    Employee employee = getEmployeeHome().getInstance();
//	    info("constructing leave history for employee #0", employee);
//	    setEmployeeLeaveHistory(coreSearching.getEmployeeLeaveHistory(employee));
//	}
	
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
	
//    /**
//     * @return the leaveHistory
//     */
//    public Collection<Leave> getEmployeeLeaveHistory() {
//        return employeeLeaveHistory;
//    }
//
//    /**
//     * @param leaveHistory the leaveHistory to set
//     */
//    public void setEmployeeLeaveHistory(Collection<Leave> leaveHistory) {
//        this.employeeLeaveHistory = leaveHistory;
//    }
//
//
//    /**
//     * @return the employeeActiveLeave
//     */
//    public Leave getEmployeeActiveLeave() {
//        return employeeActiveLeave;
//    }
//
//
//    /**
//     * @param employeeActiveLeave the employeeActiveLeave to set
//     */
//    public void setEmployeeActiveLeave(Leave employeeActiveLeave) {
//        this.employeeActiveLeave = employeeActiveLeave;
//    }
//    
//    
//    public String modifyInactiveLeave() {
//        if(leaveHome != null && leaveHome.isManaged()) {
//            info("trying to modify inactive leave #0", leaveHome);
//            /* check if the leave dates leads us to activate the leave. */
//            boolean leaveShouldBeActivated = leaveShouldBeActivated(leaveHome.getInstance(), new Date());
//            
//            if(leaveShouldBeActivated) {
//                facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της άδειας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές γιατί καθιστούν την άδεια ενεργή.");
//                return ACTION_OUTCOME_FAILURE;
//            } else {
//                info("employee's #0 leave #1 has been updated!", leaveHome.getInstance().getEmployee(), leaveHome.getInstance());
//                leaveHome.update();
//                return ACTION_OUTCOME_SUCCESS;
//            }
//        } else {
//            facesMessages.add(Severity.ERROR, "leave home #0 is not managed, thus #1 leave can't be modified.", leaveHome, leaveHome.getInstance());
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }
    
    
//    /**
//     * Checks if a leave should be set active in regards to the reference date.
//     * @param leave
//     * @param referenceDate
//     * @return
//     */
//    protected boolean leaveShouldBeActivated(Leave leave, Date referenceDate) {
//        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
//        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
//        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
//        if((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
//            return true;
//        } else return false;
//    }
//    /**
//     * TODO: We need to re-fresh the method
//     * @param leave
//     * @param addMessages
//     * @return
//     */
//    protected boolean validateLeave(Leave leave, boolean addMessages) {
//        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
//        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
//        /* check if the dates are correct */
//        if (established.after(dueTo)) {
//
//            if (addMessages)
//                facesMessages
//                        .add(Severity.ERROR,
//                                "H ημ/νία έναρξης είναι μεταγενέστερη της ημ/νιας λήξης της άδειας. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
//            return false;
//        }
//
//        Collection<Leave> current_leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance());
//        for (Leave current_leave : current_leaves) {
//            if (current_leave.getId().equals(leave.getId()))
//                continue;
//            Date current_established = DateUtils.truncate(current_leave.getEstablished(), Calendar.DAY_OF_MONTH);
//            Date current_dueTo = DateUtils.truncate(current_leave.getDueTo(), Calendar.DAY_OF_MONTH);
//            if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
//                if (addMessages)
//                    facesMessages.add(Severity.ERROR,
//                            "Υπάρχει ήδη άδεια με τις ημερομηνίες που εισάγατε. Μήπως να πιείτε κανα καφεδάκι ;");
//                return false;
//            }
//
//            if (DateUtils.isSameDay(established, current_dueTo)) {
//
//                if (addMessages)
//                    facesMessages
//                            .add(Severity.ERROR,
//                                    "Η ημ/νία έναρξης της άδειας πρέπει να είναι μεταγενέστερη της ημ/νιας λήξης της προηγούμενης άδειας.");
//                return false;
//            }
//
//            if ((established.before(current_established) && dueTo.after(current_established))
//                    || (established.after(current_established) && dueTo.before(current_dueTo))
//                    || (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
//                if (addMessages)
//                    facesMessages
//                            .add(Severity.ERROR,
//                                    "Υπάρχει επικαλυπτόμενο διάστημα με υπάρχουσες άδειες. Μήπως να κάνεις ένα διάλειμα για καφεδάκο για να ξεσκοτίσεις ;");
//                return false;
//            }
//
//        }
//
//        return true;
//
//    }
	
	

}
