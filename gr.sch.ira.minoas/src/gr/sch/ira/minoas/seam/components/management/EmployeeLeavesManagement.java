package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.printout.PrintoutRecipients;
import gr.sch.ira.minoas.model.printout.PrintoutSignatures;
import gr.sch.ira.minoas.model.security.Principal;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeLeaveHome;
import gr.sch.ira.minoas.seam.components.managers.MedicalEmployeeLeavesOfCurrentYear;
import gr.sch.ira.minoas.seam.components.managers.MedicalEmployeeLeavesOfPrevious5Years;
import gr.sch.ira.minoas.seam.components.managers.RegularEmployeeLeavesOfCurrentYear;
import gr.sch.ira.minoas.seam.components.managers.RegularEmployeeLeavesOfPreviousYear;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeLeavesManagement")
@Scope(ScopeType.PAGE)
public class EmployeeLeavesManagement extends BaseDatabaseAwareSeamComponent {
    
    public class PrintingHelper {
        private String fieldText1;
        private String fieldText2;
        private String fieldText3;
        private String fieldText4;
        private String fieldText5;
        private String fieldText6;
        private String fieldText7;
        private String fieldText8;
        private String fieldText9;
        private Date fieldDate1;
        private Date fieldDate2;
        private Date fieldDate3;
        private Date fieldDate4;
        private Date fieldDate5;
        private Date fieldDate6;
        private Date fieldDate7;
        private Date fieldDate8;
        private Date fieldDate9;
        /**
         * @return the fieldText1
         */
        public String getFieldText1() {
            return fieldText1;
        }
        /**
         * @param fieldText1 the fieldText1 to set
         */
        public void setFieldText1(String fieldText1) {
            this.fieldText1 = fieldText1;
        }
        /**
         * @return the fieldText2
         */
        public String getFieldText2() {
            return fieldText2;
        }
        /**
         * @param fieldText2 the fieldText2 to set
         */
        public void setFieldText2(String fieldText2) {
            this.fieldText2 = fieldText2;
        }
        /**
         * @return the fieldText3
         */
        public String getFieldText3() {
            return fieldText3;
        }
        /**
         * @param fieldText3 the fieldText3 to set
         */
        public void setFieldText3(String fieldText3) {
            this.fieldText3 = fieldText3;
        }
        /**
         * @return the fieldText4
         */
        public String getFieldText4() {
            return fieldText4;
        }
        /**
         * @param fieldText4 the fieldText4 to set
         */
        public void setFieldText4(String fieldText4) {
            this.fieldText4 = fieldText4;
        }
        /**
         * @return the fieldText5
         */
        public String getFieldText5() {
            return fieldText5;
        }
        /**
         * @param fieldText5 the fieldText5 to set
         */
        public void setFieldText5(String fieldText5) {
            this.fieldText5 = fieldText5;
        }
        /**
         * @return the fieldText6
         */
        public String getFieldText6() {
            return fieldText6;
        }
        /**
         * @param fieldText6 the fieldText6 to set
         */
        public void setFieldText6(String fieldText6) {
            this.fieldText6 = fieldText6;
        }
        /**
         * @return the fieldText7
         */
        public String getFieldText7() {
            return fieldText7;
        }
        /**
         * @param fieldText7 the fieldText7 to set
         */
        public void setFieldText7(String fieldText7) {
            this.fieldText7 = fieldText7;
        }
        /**
         * @return the fieldText8
         */
        public String getFieldText8() {
            return fieldText8;
        }
        /**
         * @param fieldText8 the fieldText8 to set
         */
        public void setFieldText8(String fieldText8) {
            this.fieldText8 = fieldText8;
        }
        /**
         * @return the fieldText9
         */
        public String getFieldText9() {
            return fieldText9;
        }
        /**
         * @param fieldText9 the fieldText9 to set
         */
        public void setFieldText9(String fieldText9) {
            this.fieldText9 = fieldText9;
        }
        /**
         * @return the fieldDate1
         */
        public Date getFieldDate1() {
            return fieldDate1;
        }
        /**
         * @param fieldDate1 the fieldDate1 to set
         */
        public void setFieldDate1(Date fieldDate1) {
            this.fieldDate1 = fieldDate1;
        }
        /**
         * @return the fieldDate2
         */
        public Date getFieldDate2() {
            return fieldDate2;
        }
        /**
         * @param fieldDate2 the fieldDate2 to set
         */
        public void setFieldDate2(Date fieldDate2) {
            this.fieldDate2 = fieldDate2;
        }
        /**
         * @return the fieldDate3
         */
        public Date getFieldDate3() {
            return fieldDate3;
        }
        /**
         * @param fieldDate3 the fieldDate3 to set
         */
        public void setFieldDate3(Date fieldDate3) {
            this.fieldDate3 = fieldDate3;
        }
        /**
         * @return the fieldDate4
         */
        public Date getFieldDate4() {
            return fieldDate4;
        }
        /**
         * @param fieldDate4 the fieldDate4 to set
         */
        public void setFieldDate4(Date fieldDate4) {
            this.fieldDate4 = fieldDate4;
        }
        /**
         * @return the fieldDate5
         */
        public Date getFieldDate5() {
            return fieldDate5;
        }
        /**
         * @param fieldDate5 the fieldDate5 to set
         */
        public void setFieldDate5(Date fieldDate5) {
            this.fieldDate5 = fieldDate5;
        }
        /**
         * @return the fieldDate6
         */
        public Date getFieldDate6() {
            return fieldDate6;
        }
        /**
         * @param fieldDate6 the fieldDate6 to set
         */
        public void setFieldDate6(Date fieldDate6) {
            this.fieldDate6 = fieldDate6;
        }
        /**
         * @return the fieldDate7
         */
        public Date getFieldDate7() {
            return fieldDate7;
        }
        /**
         * @param fieldDate7 the fieldDate7 to set
         */
        public void setFieldDate7(Date fieldDate7) {
            this.fieldDate7 = fieldDate7;
        }
        /**
         * @return the fieldDate8
         */
        public Date getFieldDate8() {
            return fieldDate8;
        }
        /**
         * @param fieldDate8 the fieldDate8 to set
         */
        public void setFieldDate8(Date fieldDate8) {
            this.fieldDate8 = fieldDate8;
        }
        /**
         * @return the fieldDate9
         */
        public Date getFieldDate9() {
            return fieldDate9;
        }
        /**
         * @param fieldDate9 the fieldDate9 to set
         */
        public void setFieldDate9(Date fieldDate9) {
            this.fieldDate9 = fieldDate9;
        }
    }

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @In(required = true)
    private EmployeeHome employeeHome;

    private List<PrintoutRecipients> leavePrintounRecipientList = new ArrayList<PrintoutRecipients>();

    private List<PrintoutRecipients> leavePrintounRecipientListSource = new ArrayList<PrintoutRecipients>();

    private PrintoutSignatures leavePrintoutSignature;

    private Collection<PrintoutSignatures> leavePrintoutSignatureSource = new ArrayList<PrintoutSignatures>();

    private Date leavePrintoutDate;

    private Date leavePrintoutRequestDate;

    private String leavePrintoutReferenceNumber;
    
    private PrintingHelper printHelper = new PrintingHelper();
    
    private Integer leaveDurarionInDaysHelper = 0;
    
    private Integer leaveDurationInDaysWithoutWeekends = 0;
    
    /**
     * It is the date used for various leave count computation. It is used by {@link RegularEmployeeLeavesOfCurrentYear}, 
     * {@link RegularEmployeeLeavesOfPreviousYear}, {@link MedicalEmployeeLeavesOfCurrentYear} and {@link MedicalEmployeeLeavesOfPrevious5Years}
     */
    @Out(value="leaveComputationReferenceDay", scope=ScopeType.PAGE, required=true)
    private Date leaveComputationReferenceDay = new Date();

    @SuppressWarnings("unchecked")
    @Transactional
    public Collection<EmployeeLeaveType> suggestLeaveTypesBasedOnSelectedEmployee(Object search_pattern) {
        System.err.println(getEmployeeHome().getInstance().getType());
        return getEntityManager()
                .createQuery(
                        "SELECT s FROM EmployeeLeaveType s WHERE (LOWER(s.description) LIKE LOWER(:search_pattern) OR (s.legacyCode LIKE :search_pattern2)) AND s.suitableForEmployeeType=:employeeType ORDER BY s.legacyCode")
                .setParameter("search_pattern", CoreUtils.getSearchPattern(String.valueOf(search_pattern)))
                .setParameter("search_pattern2", CoreUtils.getSearchPattern(String.valueOf(search_pattern)))
                .setParameter("employeeType", employeeHome.getInstance().getType()).getResultList();
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

    @In(required = true)
    private EmployeeLeaveHome employeeLeaveHome;

    @Transactional
    @RaiseEvent("leaveCreated")
    public String addEmployeeLeaveAction() {
        if (employeeHome.isManaged() && !(employeeLeaveHome.isManaged())) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave newLeave = employeeLeaveHome.getInstance();
            newLeave.setEmployee(employee);
            newLeave.setInsertedBy(getPrincipal());
            newLeave.setInsertedOn(new Date());
            newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));

            /* check if the employee has a current regular employment */
            Employment employment = employee.getCurrentEmployment();
            if (employment != null && employment.getType() == EmploymentType.REGULAR) {
                newLeave.setRegularSchool(employment.getSchool());
            }

            if (validateLeave(newLeave, true)) {
                employeeLeaveHome.persist();
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
    @RaiseEvent("leaveCreated")
    public String triggerLeaveAddedAction() {
        info("triggered!");
        return ACTION_OUTCOME_SUCCESS;
    }

    @Transactional
    @RaiseEvent("leaveDeleted")
    public String deleteEmployeeLeaveAction() {
        if (employeeHome.isManaged() && employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            info("deleting employee #0 leave #1", employee, leave);

//            for (TeachingHourCDR cdr : leave.getLeaveCDRs()) {
//                info("deleting leave's #0 cdr #1", employee, cdr);
//                cdr.setLeave(null);
//                getEntityManager().remove(cdr);
//            }
//
//            leave.getLeaveCDRs().removeAll(leave.getLeaveCDRs());
            leave.setActive(Boolean.FALSE);
            leave.setDeleted(Boolean.TRUE);
            leave.setDeletedOn(new Date());
            leave.setDeletedBy(getPrincipal());
            employeeLeaveHome.update();
            info("leave #0 for employee #1 has been deleted", leave, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_FAILURE;
        } else {
            facesMessages
                    .add(Severity.ERROR, "employee home #0 or leave home #1 not managed.", employeeHome, employeeLeaveHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

    @Transactional
    @RaiseEvent("leaveModified")
    public String modifyEmployeeLeaveAction() {

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave newLeave = employeeLeaveHome.getInstance();
            if (validateLeave(newLeave, true)) {
                newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
                employeeLeaveHome.update();
                getEntityManager().flush();
                info("leave #0 for employee #1 has been modified", newLeave, employee);
                return ACTION_OUTCOME_SUCCESS;
            } else {
                return ACTION_OUTCOME_FAILURE;
            }
        } else {
            facesMessages.add(Severity.ERROR, "leave home #0 is not managed.", employeeLeaveHome);
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
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    

    protected Map<String, Object> prepareParametersForLeavePrintout() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            
                Map<String, Object> parameters = new HashMap<String, Object>();
                Principal currentPrincipal = getPrincipal();
                parameters.put("employeeForInformation", currentPrincipal.getRealName());
                parameters.put("employeeForInformationTelephone", currentPrincipal.getInformationTelephone());
                parameters.put("leaveRequestDate", leavePrintoutRequestDate);
                parameters.put("employeeName", employee.getFirstName());
                parameters.put("employeeSurname", employee.getLastName());
                parameters.put("employeeSpecialization", employee.getLastSpecialization().getTitle());
                parameters.put("leaveDueToDate", leave.getDueTo());
                parameters.put("leaveEstablishedDate", leave.getEstablished());
                parameters.put("leaveDayDuration", leave.getEffectiveNumberOfDays());
                parameters.put("employeeFatherName", employee.getFatherName());
                parameters.put("referenceNumber", leavePrintoutReferenceNumber);
                parameters.put("printDate", leavePrintoutDate);
                parameters.put("signatureTitle", leavePrintoutSignature.getSignatureTitle());
                parameters.put("signatureName", leavePrintoutSignature.getSignatureName());
                
                /* according to the leave type, populate the parameters map accordinally */
                
                if(leave.getEmployeeLeaveType().getLegacyCode().equals("33")) {
                    parameters.put("numberOfBirthCertificate", printHelper.getFieldText1());
                    parameters.put("numberOfCertificateFamilyStatus", printHelper.getFieldText2());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("35")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("36")) {
                    parameters.put("leaveReason", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("37")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("38")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("41")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("42")) {
                    parameters.put("externalDecisionDate", printHelper.getFieldDate1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("45")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                    parameters.put("externalDecisionDate", printHelper.getFieldDate2());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("46")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                    parameters.put("externalDecisionDate", printHelper.getFieldDate2());
                    parameters.put("textField2", printHelper.getFieldText2());
                    parameters.put("numberOfBirthCertificate", printHelper.getFieldText3());
                    parameters.put("textField1", printHelper.getFieldText4());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("47")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                }
                
               /* compute a SHA-1 digest */
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(String.format("%s-%d-%d", parameters.toString(), System.currentTimeMillis(),
                        parameters.hashCode()).getBytes("UTF-8"));
                byte[] digest_array = digest.digest();
                parameters.put("localReferenceNumber", convertToHex(digest_array));

                StringBuffer sb = new StringBuffer();
                sb.append("<b>");
                int counter = 1;
                for (PrintoutRecipients recipient : leavePrintounRecipientList) {
                    sb.append(String.format("%d %s<br />", counter++, recipient.getRecipientTitle()));
                }

                sb.append("</b>");
                parameters.put("notificationList", sb.toString());

                

            
            return parameters;
        } else {
            return null;
        }

    }
    
    protected Map<String, Object> prepareSpecialParametersForLeavePrintout() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            
                Map<String, Object> parameters = new HashMap<String, Object>();
               
                
                /* according to the leave type, populate the parameters map accordinally */
                
                if(leave.getEmployeeLeaveType().getLegacyCode().equals("33")) {
                    parameters.put("numberOfBirthCertificate", printHelper.getFieldText1());
                    parameters.put("numberOfCertificateFamilyStatus", printHelper.getFieldText2());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("35")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("36")) {
                    parameters.put("leaveReason", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("37")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("38")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("41")) {
                    parameters.put("externalDecisionNumber", printHelper.getFieldText1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("42")) {
                    parameters.put("externalDecisionDate", printHelper.getFieldDate1());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("45")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                    parameters.put("externalDecisionDate", printHelper.getFieldDate2());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("46")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                    parameters.put("externalDecisionDate", printHelper.getFieldDate2());
                    parameters.put("textField2", printHelper.getFieldText2());
                    parameters.put("numberOfBirthCertificate", printHelper.getFieldText3());
                    parameters.put("textField1", printHelper.getFieldText4());
                } else if(leave.getEmployeeLeaveType().getLegacyCode().equals("47")) {
                    parameters.put("doctorOpinionDate", printHelper.getFieldDate1());
                    parameters.put("doctorName", printHelper.getFieldText1());
                }
                       
            return parameters;
        } else {
            return null;
        }

    }
    
    protected Map<String, Object> prepareSpecialParametersForLeaveCoverPrintout()  {

        if (employeeLeaveHome.isManaged()) {
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            Map<String, Object> parameters = new HashMap<String, Object>();
            
                if(leave.getEmployeeLeaveType().getLegacyCode().equals("55")) {
                    parameters.put("textField2", printHelper.getFieldText2());
                    parameters.put("textField3", printHelper.getFieldText3());
                    parameters.put("textField4", printHelper.getFieldText4());
                    parameters.put("textField5", printHelper.getFieldText5());
                    parameters.put("textField6", printHelper.getFieldText6());
                    parameters.put("textField1", printHelper.getFieldText8());
                    parameters.put("doctorName", printHelper.getFieldText7());
                    parameters.put("dateField1", printHelper.getFieldDate2());
                    
                } 
            
            return parameters;
        } else {
            return null;
        }

    }

    
    public String printEmployeeLeaveCoverAction() {

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            
            
            try {
                
                InputStream leaveTemplateInputStream = this.getClass().getResourceAsStream(String.format("/reports/leaveHandlingRequest_%s.jasper", leave.getEmployeeLeaveType().getLegacyCode()));
                
                Map<String, Object> parameters = prepareParametersForLeavePrintout();
                /* add special parameters for the cover printout */
                parameters.putAll(prepareSpecialParametersForLeaveCoverPrintout());
                JRBeanCollectionDataSource main = new JRBeanCollectionDataSource(Collections.EMPTY_LIST);
                byte[] bytes = JasperRunManager.runReportToPdf(leaveTemplateInputStream, parameters, (JRDataSource) main);
                HttpServletResponse response = (HttpServletResponse) CoreUtils.getFacesContext().getExternalContext()
                        .getResponse();
                response.setContentType("application/pdf");
                String pdfFile = String.format("ΑΔΕΙΑ_%s_%s_(%s).pdf", employee.getLastName(), employee.getFirstName(),
                        employee.getLastSpecialization().getTitle());
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
    
    public String printEmployeeLeaveAction() {

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            
            
            try {
                
                InputStream leaveTemplateInputStream = this.getClass().getResourceAsStream(String.format("/reports/leavePrintout_%s.jasper", leave.getEmployeeLeaveType().getLegacyCode()));
                
                Map<String, Object> parameters = prepareParametersForLeavePrintout();
                /* add special parameters for the printout */
                parameters.putAll(prepareSpecialParametersForLeavePrintout());
                JRBeanCollectionDataSource main = new JRBeanCollectionDataSource(Collections.EMPTY_LIST);
                byte[] bytes = JasperRunManager.runReportToPdf(leaveTemplateInputStream, parameters, (JRDataSource) main);
                HttpServletResponse response = (HttpServletResponse) CoreUtils.getFacesContext().getExternalContext()
                        .getResponse();
                response.setContentType("application/pdf");
                String pdfFile = String.format("ΑΔΕΙΑ_%s_%s_(%s).pdf", employee.getLastName(), employee.getFirstName(),
                        employee.getLastSpecialization().getTitle());
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
    
    @Transactional
    public String printWordEmployeeLeaveAction() {
        

        if (employeeLeaveHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            EmployeeLeave leave = employeeLeaveHome.getInstance();
            OutputStream output = null;
            try {
                Map<String, Object> parameters = prepareParametersForLeavePrintout();
                JRBeanCollectionDataSource main = new JRBeanCollectionDataSource(Collections.EMPTY_LIST);
                JasperPrint jasperPrint = JasperFillManager.fillReport(this.getClass().getResourceAsStream(
                        "/reports/leavePrintout_31.jasper"), parameters, main);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                output = new BufferedOutputStream(bout);
                JROdtExporter exporter = new JROdtExporter();
                
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
                exporter.setParameter(JRExporterParameter.INPUT_FILE, "ffsd");
                try {
                    exporter.exportReport();
                    output.flush();
                    
                } catch (Throwable t) {
                    System.err.println(t);
                }
                byte[] bytes = bout.toByteArray();
                HttpServletResponse response = (HttpServletResponse) CoreUtils.getFacesContext().getExternalContext()
                        .getResponse();
                response.setContentType("application/vnd.oasis.opendocument.text");
                String pdfFile = String.format("ΑΔΕΙΑ_%s_%s_(%s).pdf", employee.getLastName(), employee.getFirstName(),
                        employee.getLastSpecialization().getTitle());
                // http://greenbytes.de/tech/webdav/rfc6266.html
                //response.addHeader("Content-Disposition", String.format("attachment; filename*=UTF-8 ' '%s", pdfFile));
                response.addHeader("Content-Disposition", String.format("attachment; filename=lalala.odt", pdfFile));
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

    
    protected int computeLeaveDuration(Date fromDate, Date toDate) {
        if (fromDate != null && toDate != null) {
            long DAY_TIME_IN_MILLIS = 24 * 60 * 60 * 1000;
            long date1DaysMS = fromDate.getTime() - (fromDate.getTime() % DAY_TIME_IN_MILLIS);
            long date2DaysMS = toDate.getTime() - (toDate.getTime() % DAY_TIME_IN_MILLIS);

            long timeInMillisDiff = (date2DaysMS - date1DaysMS);
            return (int) (timeInMillisDiff / DAY_TIME_IN_MILLIS);
        } else
            return 0;
    }

    protected int computeLeaveDurationWithoutWeekend(Date fromDate, Date toDate) {
        if (fromDate != null && toDate != null) {
            int countDays = 0;
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(fromDate);
            while (!(DateUtils.isSameDay(fromDate, toDate))) {
                int dayOfWeek = fromCal.get(Calendar.DAY_OF_WEEK);
                fromCal.add(Calendar.DAY_OF_YEAR, 1);
                fromDate = fromCal.getTime();
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
                    continue; // don't count sundays and saturdays
                else
                    countDays++;
            }
            return countDays;
        } else
            return 0;
    }
    
    
    public void silentlyComputeLeaveDuration() {
        EmployeeLeave leave = employeeLeaveHome.getInstance();
        Date established = leave.getEstablished() != null ? DateUtils.truncate(leave.getEstablished(),
                Calendar.DAY_OF_MONTH) : null;
        Date dueTo = leave.getDueTo() != null ? DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH) : null;
        if( established!=null && dueTo !=null &&  established.before(dueTo) ) {
            setLeaveDurarionInDaysHelper(computeLeaveDuration(established, dueTo));
            setLeaveDurationInDaysWithoutWeekends(computeLeaveDurationWithoutWeekend(established, dueTo));
        } else {
            setLeaveDurarionInDaysHelper(new Integer(0));
            setLeaveDurationInDaysWithoutWeekends(new Integer(0));
        }
    }
    
    public void computeLeaveDuration() {
        EmployeeLeave leave = employeeLeaveHome.getInstance();
        Date established = leave.getEstablished() != null ? DateUtils.truncate(leave.getEstablished(),
                Calendar.DAY_OF_MONTH) : null;
        Date dueTo = leave.getDueTo() != null ? DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH) : null;

        info("computeLeaveDuration: established -> '#0', due to -> '#1'", established, dueTo);
        if (established == null || dueTo == null) {
            facesMessages.add(Severity.ERROR, "Πρέπει πρώτα να συμπληρώσετε την ημ/νια έναρξης και λήξεις της άδειας");
            return;
        }

        /* check if the dates are correct */
        if (established.after(dueTo)) {

            facesMessages
                    .add(Severity.ERROR,
                            "H ημ/νία έναρξης είναι μεταγενέστερη της ημ/νιας λήξης της άδειας. Μάλλον πρέπει να κάνεις ενα διάλειμα.");
            return;
        }
        setLeaveDurarionInDaysHelper(computeLeaveDuration(established, dueTo));
        setLeaveDurationInDaysWithoutWeekends(computeLeaveDurationWithoutWeekend(established, dueTo));
        // temp computation
        leave.setEffectiveNumberOfDays(new Integer(computeLeaveDuration(established, dueTo)));
    }

    /**
     * TODO: We need to re-fresh the method
     * @param leave
     * @param addMessages
     * @return
     */
    protected boolean validateLeave(EmployeeLeave leave, boolean addMessages) {
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

        Collection<EmployeeLeave> current_leaves = getCoreSearching().getEmployeeLeaves2(employeeHome.getInstance());
        for (EmployeeLeave current_leave : current_leaves) {
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

            if ((established.before(current_established) && dueTo.after(current_established)) ||
                    (established.after(current_established) && dueTo.before(current_dueTo)) ||
                    (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
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
    protected boolean leaveShouldBeActivated(EmployeeLeave leave, Date referenceDate) {
        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
        if ((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
            return true;
        } else
            return false;
    }

    /* this method is called when the user clicks the "add new leave" */
    public void prepeareNewLeave() {
        employeeLeaveHome.clearInstance();
        EmployeeLeave leave = employeeLeaveHome.getInstance();
        leave.setEstablished(new Date());
        leave.setDueTo(new Date());
        leave.setEmployee(employeeHome.getInstance());
    }

    /* this method is called when the user clicks the "print leave" */
    public void prepeareForLeavePrint() {
        this.leavePrintoutDate = new Date();
        Collection<SelectItem> list = new ArrayList<SelectItem>();
        for (PrintoutRecipients r : getCoreSearching().getPrintoutRecipients(getEntityManager())) {
            list.add(new SelectItem(r, r.getRecipientTitle()));
        }

        this.leavePrintounRecipientListSource = new ArrayList<PrintoutRecipients>(getCoreSearching()
                .getPrintoutRecipients(getEntityManager()));
        this.leavePrintoutSignatureSource = getCoreSearching().getPrintoutSignatures(getEntityManager());
    }

    /**
     * @return the leavePrintoutSignature
     */
    public PrintoutSignatures getLeavePrintoutSignature() {
        return leavePrintoutSignature;
    }

    /**
     * @param leavePrintoutSignature the leavePrintoutSignature to set
     */
    public void setLeavePrintoutSignature(PrintoutSignatures leavePrintoutSignature) {
        this.leavePrintoutSignature = leavePrintoutSignature;
    }

    /**
     * @return the leavePrintoutDate
     */
    public Date getLeavePrintoutDate() {
        return leavePrintoutDate;
    }

    /**
     * @param leavePrintoutDate the leavePrintoutDate to set
     */
    public void setLeavePrintoutDate(Date leavePrintoutDate) {
        this.leavePrintoutDate = leavePrintoutDate;
    }

    /**
     * @return the leavePrintoutRequestDate
     */
    public Date getLeavePrintoutRequestDate() {
        return leavePrintoutRequestDate;
    }

    /**
     * @param leavePrintoutRequestDate the leavePrintoutRequestDate to set
     */
    public void setLeavePrintoutRequestDate(Date leavePrintoutRequestDate) {
        this.leavePrintoutRequestDate = leavePrintoutRequestDate;
    }

    /**
     * @return the leavePrintoutReferenceNumber
     */
    public String getLeavePrintoutReferenceNumber() {
        return leavePrintoutReferenceNumber;
    }

    /**
     * @param leavePrintoutReferenceNumber the leavePrintoutReferenceNumber to set
     */
    public void setLeavePrintoutReferenceNumber(String leavePrintoutReferenceNumber) {
        this.leavePrintoutReferenceNumber = leavePrintoutReferenceNumber;
    }

    /**
     * @return the leavePrintoutSignatureSource
     */
    public Collection<PrintoutSignatures> getLeavePrintoutSignatureSource() {
        return leavePrintoutSignatureSource;
    }

    /**
     * @param leavePrintoutSignatureSource the leavePrintoutSignatureSource to set
     */
    public void setLeavePrintoutSignatureSource(Collection<PrintoutSignatures> leavePrintoutSignatureSource) {
        this.leavePrintoutSignatureSource = leavePrintoutSignatureSource;
    }

    /**
     * @return the leavePrintounRecipientListSource
     */
    public List<PrintoutRecipients> getLeavePrintounRecipientListSource() {
        return leavePrintounRecipientListSource;
    }

    /**
     * @param leavePrintounRecipientListSource the leavePrintounRecipientListSource to set
     */
    public void setLeavePrintounRecipientListSource(List<PrintoutRecipients> leavePrintounRecipientListSource) {
        this.leavePrintounRecipientListSource = leavePrintounRecipientListSource;
    }

    /**
     * @return the leavePrintounRecipientList
     */
    public List<PrintoutRecipients> getLeavePrintounRecipientList() {
        return leavePrintounRecipientList;
    }

    /**
     * @param leavePrintounRecipientList the leavePrintounRecipientList to set
     */
    public void setLeavePrintounRecipientList(List<PrintoutRecipients> leavePrintounRecipientList) {
        this.leavePrintounRecipientList = leavePrintounRecipientList;
    }

    /**
     * @return the leaveComputationReferenceDay
     */
    public Date getLeaveComputationReferenceDay() {
        return leaveComputationReferenceDay;
    }

    /**
     * @param leaveComputationReferenceDay the leaveComputationReferenceDay to set
     */
    public void setLeaveComputationReferenceDay(Date leaveComputationReferenceDay) {
        this.leaveComputationReferenceDay = leaveComputationReferenceDay;
    }
    
    
    /* this method is being called from the page containing a list of leaves and returns the CSS class that should be used by the leave row */
    public String getTableCellClassForLeave(EmployeeLeave leave) {
        if(leave.isFuture()) {
            return "rich-table-future-leave";
        } else if(leave.isCurrent()) {
            return "rich-table-current-leave";
        } else if(leave.isPast()) {
            return "rich-table-past-leave";
        } else return "";
    }

    /**
     * @return the printHelper
     */
    public PrintingHelper getPrintHelper() {
        return printHelper;
    }

    /**
     * @param printHelper the printHelper to set
     */
    public void setPrintHelper(PrintingHelper printHelper) {
        this.printHelper = printHelper;
    }

    /**
     * @return the leaveDurarionInDaysHelper
     */
    public Integer getLeaveDurarionInDaysHelper() {
        return leaveDurarionInDaysHelper;
    }

    /**
     * @param leaveDurarionInDaysHelper the leaveDurarionInDaysHelper to set
     */
    public void setLeaveDurarionInDaysHelper(Integer leaveDurarionInDaysHelper) {
        this.leaveDurarionInDaysHelper = leaveDurarionInDaysHelper;
    }

    /**
     * @return the leaveDurationInDaysWithoutWeekends
     */
    public Integer getLeaveDurationInDaysWithoutWeekends() {
        return leaveDurationInDaysWithoutWeekends;
    }

    /**
     * @param leaveDurationInDaysWithoutWeekends the leaveDurationInDaysWithoutWeekends to set
     */
    public void setLeaveDurationInDaysWithoutWeekends(Integer leaveDurationInDaysWithoutWeekends) {
        this.leaveDurationInDaysWithoutWeekends = leaveDurationInDaysWithoutWeekends;
    }

}
