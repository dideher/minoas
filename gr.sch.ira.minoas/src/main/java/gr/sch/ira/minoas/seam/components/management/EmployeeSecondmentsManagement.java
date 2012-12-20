package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.EmployeeLeaveType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.printout.PrintoutRecipients;
import gr.sch.ira.minoas.model.printout.PrintoutSignatures;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeLeaveHome;
import gr.sch.ira.minoas.seam.components.home.SecondmentHome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeSecondmentsManagement")
@Scope(ScopeType.PAGE)
public class EmployeeSecondmentsManagement extends BaseDatabaseAwareSeamComponent {
    
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


    @In(required = true)
    private SecondmentHome secondmentHome;

//    @Transactional
//    @RaiseEvent("leaveCreated")
//    public String addEmployeeLeaveAction() {
//        if (employeeHome.isManaged() && !(employeeLeaveHome.isManaged())) {
//            Employee employee = getEntityManager().merge(employeeHome.getInstance());
//            EmployeeLeave newLeave = employeeLeaveHome.getInstance();
//            newLeave.setEmployee(employee);
//            newLeave.setInsertedBy(getPrincipal());
//            newLeave.setInsertedOn(new Date());
//            newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
//            /* check if the employee has a current regular employment */
//            Employment employment = employee.getCurrentEmployment();
//            if (employment != null && employment.getType() == EmploymentType.REGULAR) {
//                newLeave.setRegularSchool(employment.getSchool());
//            }
//
//            if (validateLeave(newLeave, true)) {
//                newLeave.setNumberOfDays(CoreUtils.getDatesDifference(newLeave.getEstablished(), newLeave.getDueTo()));
//                employeeLeaveHome.persist();
//                setLeaveDurarionInDaysHelper(0);
//                setLeaveDurationInDaysWithoutWeekends(0);
//                getEntityManager().flush();
//                info("leave #0 for employee #1 has been created", newLeave, employee);
//                return ACTION_OUTCOME_SUCCESS;
//            } else {
//                return ACTION_OUTCOME_FAILURE;
//            }
//        } else {
//            facesMessages.add(Severity.ERROR, "employee home #0 is not managed.", employeeHome);
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }
    
    

    @Transactional
    @RaiseEvent("secondmentDeleted")
    public String deleteEmployeeSecondmentAction() {
        if (employeeHome.isManaged() && secondmentHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Secondment secondment = secondmentHome.getInstance();
            info("deleting employee #0 secondment #1", employee, secondment);

//            for (TeachingHourCDR cdr : leave.getLeaveCDRs()) {
//                info("deleting leave's #0 cdr #1", employee, cdr);
//                cdr.setLeave(null);
//                getEntityManager().remove(cdr);
//            }
//
//            leave.getLeaveCDRs().removeAll(leave.getLeaveCDRs());
            secondment.setActive(Boolean.FALSE);
            secondment.setDeleted(Boolean.TRUE);
            secondment.setDeletedOn(new Date());
            secondment.setDeletedBy(getPrincipal());
            secondmentHome.update();
            info("secondent #0 for employee #1 has been deleted", secondment, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages
                    .add(Severity.ERROR, "employee home #0 or secondment home #1 not managed.", employeeHome, secondmentHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

//    @Transactional
//    @RaiseEvent("leaveModified")
//    public String modifyEmployeeLeaveAction() {
//
//        if (employeeLeaveHome.isManaged()) {
//            Employee employee = getEntityManager().merge(employeeHome.getInstance());
//            EmployeeLeave newLeave = employeeLeaveHome.getInstance();
//            if (validateLeave(newLeave, true)) {
//                newLeave.setActive(leaveShouldBeActivated(newLeave, new Date()));
//                newLeave.setNumberOfDays(CoreUtils.getDatesDifference(newLeave.getEstablished(), newLeave.getDueTo()));
//                employeeLeaveHome.update();
//                getEntityManager().flush();
//                info("leave #0 for employee #1 has been modified", newLeave, employee);
//                return ACTION_OUTCOME_SUCCESS;
//            } else {
//                return ACTION_OUTCOME_FAILURE;
//            }
//        } else {
//            facesMessages.add(Severity.ERROR, "leave home #0 is not managed.", employeeLeaveHome);
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }

   
    

//    
//    /**
//     * TODO: We need to re-fresh the method
//     * @param leave
//     * @param addMessages
//     * @return
//     */
//    protected boolean validateLeave(EmployeeLeave leave, boolean addMessages) {
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
//        Collection<EmployeeLeave> current_leaves = getCoreSearching().getEmployeeLeaves2(employeeHome.getInstance());
//        for (EmployeeLeave current_leave : current_leaves) {
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
//            if ((established.before(current_established) && dueTo.after(current_established)) ||
//                    (established.after(current_established) && dueTo.before(current_dueTo)) ||
//                    (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
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
//
//    /**
//     * Checks if a leave should be set active in regards to the reference date.
//     * @param leave
//     * @param referenceDate
//     * @return
//     */
//    protected boolean leaveShouldBeActivated(EmployeeLeave leave, Date referenceDate) {
//        Date established = DateUtils.truncate(leave.getEstablished(), Calendar.DAY_OF_MONTH);
//        Date dueTo = DateUtils.truncate(leave.getDueTo(), Calendar.DAY_OF_MONTH);
//        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
//        if ((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
//            return true;
//        } else
//            return false;
//    }
//
//    /* this method is called when the user clicks the "add new leave" */
//    public void prepeareNewLeave() {
//        employeeLeaveHome.clearInstance();
//        EmployeeLeave leave = employeeLeaveHome.getInstance();
//        leave.setEstablished(new Date());
//        leave.setDueTo(new Date());
//        leave.setEmployee(employeeHome.getInstance());
//    }
    
}
