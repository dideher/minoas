package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.LeaveType;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.holders.EmployeeLeaveHolder;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.LeaveHome;

import java.security.MessageDigest;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeLeavesManagement")
@Scope(ScopeType.PAGE)
public class EmployeeLeavesManagement extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
  	@In(required = true)
	private EmployeeHome employeeHome;
	
		
  	@SuppressWarnings("unchecked")
    @Transactional
    public Collection<EmployeeLeaveType> suggestLeaveTypesBasedOnSelectedEmployee(Object search_pattern) {
        System.err.println(getEmployeeHome().getInstance().getType());
        return getEntityManager()
                .createQuery("SELECT s FROM EmployeeLeaveType s WHERE (LOWER(s.description) LIKE LOWER(:search_pattern) OR (s.legacyCode LIKE :search_pattern2)) AND s.suitableForEmployeeType=:employeeType ORDER BY s.legacyCode" ).setParameter(
                        "search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).setParameter(
                                "search_pattern2", CoreUtils.getSearchPattern(String.valueOf(search_pattern))).setParameter("employeeType", employeeHome.getInstance().getType())
                .getResultList();
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
	
    

   
    
//    @Transactional
//    public String createNewLeave() {
//        if(!leaveHome.isManaged()) {
//            info("trying to create new leave #0", leaveHome.getInstance());
//            if(validateLeave(leaveHome.getInstance(), true)) {
//                leaveHome.persist();
//                constructLeaveHistory();
//                findEmployeeActiveLeave();
//                facesMessages.add(Severity.INFO, "Η εισαγωγή νέας άδειας έγινε");
//                return ACTION_OUTCOME_SUCCESS;
//            } else {
//                facesMessages.add(Severity.WARN, "Η εισαγωγή νέας άδειας δεν ήταν εφικτή.");
//                return ACTION_OUTCOME_FAILURE;
//            }
//        } else {
//            facesMessages.add(Severity.ERROR, "Employee's #0 leave #1 is managed.", employeeHome.getInstance(), leaveHome.getInstance());
//            return ACTION_OUTCOME_FAILURE;
//        }
//        
//    }
//    
//    @Transactional
//    public String deleteLeave() {
//        if(leaveHome.isManaged()) {
//            Leave leave = leaveHome.getInstance();
//            leave.setActive(Boolean.FALSE);
//            leave.setDeleted(Boolean.TRUE);
//            leave.setDeletedOn(new Date());
//            leave.setDeletedBy(getPrincipal());
//            leaveHome.update();
//            constructLeaveHistory();
//            findEmployeeActiveLeave();
//            return ACTION_OUTCOME_SUCCESS;
//        } else {
//            facesMessages.add(Severity.ERROR, "Employee's #0 leave #1 is not managed.", employeeHome.getInstance(), leaveHome.getInstance());
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }
//    
//    /* this method is called when the user clicks the "add new leave" */
//    public void prepeareNewLeave() {
//        leaveHome.clearInstance();
//        Leave leave = leaveHome.getInstance();
//        leave.setEstablished(new Date());
//        leave.setDueTo(null);
//        leave.setEmployee(employeeHome.getInstance());
//        leave.setLeaveType(LeaveType.MEDICAL_LABOUR_LEAVE);
//    }
    
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
    /**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
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


	
    
    
    @In(required=true)
    private LeaveHome leaveHome;
    
    
    
    @Transactional
    @RaiseEvent("leaveCreated")
    public String addEmployeeLeaveAction() {
        if(employeeHome.isManaged() && !(leaveHome.isManaged())) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Leave newLeave = leaveHome.getInstance();
            newLeave.setEmployee(employee);
            newLeave.setInsertedBy(getPrincipal());
            newLeave.setInsertedOn(new Date());
            newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
            
            /* check if the employee has a current regular employment */
            Employment employment = employee.getCurrentEmployment();
            if(employment!=null && employment.getType()==EmploymentType.REGULAR) {
                newLeave.setRegularSchool(employment.getSchool());
            }
            
            if(validateLeave(newLeave, true)) {
                leaveHome.persist();
                getEntityManager().flush();
                info("leave #0 for employee #1 has been created", newLeave, employee);
                return ACTION_OUTCOME_SUCCESS;
            } else {
                return ACTION_OUTCOME_FAILURE;
            }
        } else {
            facesMessages.add(Severity.ERROR, "employee home #0 is not managed.", employeeHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    @Transactional
    @RaiseEvent("leaveDeleted")
    public String deleteEmployeeLeaveAction() {
        if (employeeHome.isManaged() && leaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Leave leave = leaveHome.getInstance();
            info("deleting employee #0 leave #1", employee, leave);
            
            for (TeachingHourCDR cdr : leave.getLeaveCDRs()) {
                info("deleting leave's #0 cdr #1", employee, cdr);
                cdr.setLeave(null);
                getEntityManager().remove(cdr);
            }
            
            leave.getLeaveCDRs().removeAll(leave.getLeaveCDRs());
            leave.setActive(Boolean.FALSE);
            leave.setDeleted(Boolean.TRUE);
            leave.setDeletedOn(new Date());
            leave.setDeletedBy(getPrincipal());
            leaveHome.update();
            info("leave #0 for employee #1 has been deleted", leave, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_FAILURE;
        } else {
            facesMessages
                    .add(Severity.ERROR, "employee home #0 or leave home #1 not managed.", employeeHome, leaveHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

    @Transactional
    @RaiseEvent("leaveModified")
    public String modifyEmployeeLeaveAction() {
        
        if(leaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Leave newLeave = leaveHome.getInstance();
            if(validateLeave(newLeave, true)) {
                newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
                leaveHome.update();
                getEntityManager().flush();
                info("leave #0 for employee #1 has been modified", newLeave, employee);
                return ACTION_OUTCOME_SUCCESS;
            } else {
                return ACTION_OUTCOME_FAILURE;
            }
        } else {
            facesMessages.add(Severity.ERROR, "leave home #0 is not managed.", leaveHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
    
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
    
    @Transactional
    public String printEmployeeLeaveAction() {
        
        if(leaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Leave leave = leaveHome.getInstance();
            
            
            


            try {
                Map<String, Object> parameters = new HashMap<String, Object>();
                Principal currentPrincipal = getPrincipal();
                parameters.put("employeeForInformation", currentPrincipal.getRealName());
                parameters.put("employeeForInformationTelephone", currentPrincipal.getInformationTelephone());
                parameters.put("leaveRequestDate", new Date());
                parameters.put("employeeName", employee.getFirstName());
                parameters.put("employeeSurname", employee.getLastName());
                parameters.put("employeeSpecialization", employee.getLastSpecialization().getTitle());
                parameters.put("leaveDueToDate", leave.getDueTo());
                parameters.put("leaveEstablishedDate", leave.getEstablished());
                parameters.put("leaveDayDuration", leave.getEffectiveNumberOfDays());
                parameters.put("employeeFatherName", employee.getFatherName());
                parameters.put("referenceNumber", "53453456");
                parameters.put("printDate", new Date());
                parameters.put("signatureTitle", "Ο Διευθυντής της Δ/νσης Εκπ/σης");
                parameters.put("signatureName", "Μανουσάκης Γεώργιος");
                
                /* compute a SHA-1 digest */
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(String.format("%s-%d-%d", parameters.toString(), System.currentTimeMillis(), parameters.hashCode()).getBytes("UTF-8"));
                byte[] digest_array = digest.digest();
                parameters.put("localReferenceNumber", convertToHex(digest_array));
                
                List<String> notificationList = new ArrayList<String>();
                notificationList.add("Ενδιαφερόμενος/νη");
                notificationList.add("Σχολείο Οργανικής");
                notificationList.add("Σχολείο που Υπηρετεί");
                notificationList.add("Υπηρεσία που Υπηρετεί");
                
                StringBuffer sb = new StringBuffer();
                sb.append("<b>");
                int counter = 1;
                for(String lineString : notificationList) {
                    sb.append(String.format("%d %s<br />", counter++, lineString));
                }
                sb.append("</b>");
                parameters.put("notificationList", sb.toString());
//                parameters.put("LEAVE_TYPE_FILTER",
//                        employeeLeaveCriteria.getLeaveType() != null ? getLocalizedMessage(employeeLeaveCriteria
//                                .getLeaveType().getKey()) : "Όλοι οι Τύποι");
//                parameters.put("LEAVE_SPECIALIZATION_FILTER",
//                        employeeLeaveCriteria.getSpecializationGroup() != null ? employeeLeaveCriteria
//                                .getSpecializationGroup().getTitle() : "Όλες οι Ειδικότητες");
//
//                parameters.put("LEAVE_SCHOOL_FILTER",
//                        employeeLeaveCriteria.getSchoolOfIntereset() != null ? employeeLeaveCriteria.getSchoolOfIntereset()
//                                .getTitle() : "Όλες οι Σχολικές Μονάδες");
//
//                parameters.put("LEAVE_REGION_FILTER", employeeLeaveCriteria.getRegion() != null ? employeeLeaveCriteria
//                        .getRegion().toString()
//                        + "' Ηρακλείου" : "Όλες οι Περιοχές");
//
//                parameters.put("LEAVE_DATE_SEARCH_FILTER", getLocalizedMessage(employeeLeaveCriteria.getDateSearchType()
//                        .getKey()));
//
//                parameters.put("LEAVE_EFFECTIVE_DATE_FILTER", employeeLeaveCriteria.getEffectiveDate());
//                parameters.put("LEAVE_EFFECTIVE_DATE_FROM_FILTER", employeeLeaveCriteria.getEffectiveDateFrom());
//                parameters.put("LEAVE_EFFECTIVE_DATE_UNTIL_FILTER", employeeLeaveCriteria.getEffectiveDateUntil());

                /* create the leave type helper */
                
//                for (LeaveType leaveType : getCoreSearching().getAvailableLeaveTypes()) {
//                    parameters.put(leaveType.name(), getLocalizedMessage(leaveType.getKey()));
//                }

                //JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportData);
                
                JRBeanCollectionDataSource main = new JRBeanCollectionDataSource(Collections.EMPTY_LIST);
                byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream(
                        "/reports/leavePrintout_31.jasper"), parameters, (JRDataSource) main);
                HttpServletResponse response = (HttpServletResponse) CoreUtils.getFacesContext().getExternalContext()
                        .getResponse();
                response.setContentType("application/pdf");
                String pdfFile = String.format("ΑΔΕΙΑ_%s_%s_(%s).pdf", employee.getLastName(), employee.getFirstName(), employee.getLastSpecialization().getTitle());
                // http://greenbytes.de/tech/webdav/rfc6266.html
                //response.addHeader("Content-Disposition", String.format("attachment; filename*=UTF-8 ' '%s", pdfFile));
                response.addHeader("Content-Disposition", String.format("attachment; filename=lalala.pdf", pdfFile));
                response.setContentLength(bytes.length);
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                CoreUtils.getFacesContext().responseComplete();
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }

        
            
            
            
                info("leave #0 for employee #1 has been printed !", leave, employee);
                
                return ACTION_OUTCOME_SUCCESS;
            } else {
                return ACTION_OUTCOME_FAILURE;
            }
    }
    
    public void computeLeaveDuration() {
        Leave leave = leaveHome.getInstance();
        Date established = leave.getEstablished() != null ? DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH) : null;
        Date dueTo = leave.getDueTo() != null ? DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH) : null;
        
        info("computeLeaveDuration: established -> '#0', due to -> '#1'", established, dueTo);
        if(established==null || dueTo==null) {
            facesMessages
            .add(Severity.ERROR,
                    "Πρέπει πρώτα να συμπληρώσετε την ημ/νια έναρξης και λήξεις της άδειας");
            return;
        }
        
     
        /* check if the dates are correct */
        if (established.after(dueTo)) {

                facesMessages
                        .add(Severity.ERROR,
                                "H ημ/νία έναρξης είναι μεταγενέστερη της ημ/νιας λήξης της άδειας. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
            return;
        }
        // temp computation
        long DAY_TIME_IN_MILLIS = 24 * 60 * 60 * 1000;
        long date1DaysMS = established.getTime() - (established.getTime() % DAY_TIME_IN_MILLIS);
        long date2DaysMS = dueTo.getTime() - (dueTo.getTime() % DAY_TIME_IN_MILLIS);

        long timeInMillisDiff = (date2DaysMS - date1DaysMS);
        int ret = (int) (timeInMillisDiff / DAY_TIME_IN_MILLIS); 
        leave.setEffectiveNumberOfDays(new Integer(ret)); 
    }
    
    /**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
    protected boolean validateLeave(Leave leave, boolean addMessages) {
        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
        /* check if the dates are correct */
        if (established.after(dueTo)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "H ημ/νία έναρξης είναι μεταγενέστερη της ημ/νιας λήξης της άδειας. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
            return false;
        }

        Collection<Leave> current_leaves = getCoreSearching().getEmployeeLeaves(employeeHome.getInstance());
        for (Leave current_leave : current_leaves) {
            if (current_leave.getId().equals(leave.getId()))
                continue;
            Date current_established = DateUtils.truncate(current_leave.getEstablished(), Calendar.DAY_OF_MONTH);
            Date current_dueTo = DateUtils.truncate(current_leave.getDueTo(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
                if (addMessages)
                    facesMessages.add(Severity.ERROR,
                            "Υπάρχει ήδη άδεια με τις ημερομηνίες που εισάγατε. Μήπως να πιείτε κανα καφεδάκι ;");
                return false;
            }

            if (DateUtils.isSameDay(established, current_dueTo)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νία έναρξης της άδειας πρέπει να είναι μεταγενέστερη της ημ/νιας λήξης της προηγούμενης άδειας.");
                return false;
            }

            if ((established.before(current_established) && dueTo.after(current_established))
                    || (established.after(current_established) && dueTo.before(current_dueTo))
                    || (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Υπάρχει επικαλυπτόμενο διάστημα με υπάρχουσες άδειες. Μήπως να κάνεις ένα διάλειμα για καφεδάκο για να ξεσκοτίσεις ;");
                return false;
            }

        }

        return true;

    }
    
    /**
     * Checks if a leave should be set active in regards to the reference date.
     * @param leave
     * @param referenceDate
     * @return
     */
    protected boolean leaveShouldBeActivated(Leave leave, Date referenceDate) {
        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
        if((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
            return true;
        } else return false;
    }
    
  /* this method is called when the user clicks the "add new leave" */
  public void prepeareNewLeave() {
      leaveHome.clearInstance();
      Leave leave = leaveHome.getInstance();
      leave.setEstablished(new Date());
      leave.setDueTo(null);
      leave.setEmployee(employeeHome.getInstance());
      leave.setLeaveType(LeaveType.MEDICAL_LABOUR_LEAVE);
  }
	

}
