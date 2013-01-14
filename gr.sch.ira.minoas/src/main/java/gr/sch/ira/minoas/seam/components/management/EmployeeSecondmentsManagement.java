package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.SecondmentHome;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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

    @Transactional
    @RaiseEvent("secondmentCreated")
    public String addEmployeeSecondmentAction() {
        if (employeeHome.isManaged()) {
            Employee employee = employeeHome.getInstance();
            Employment currentEmployment = employee.getCurrentEmployment();
            Secondment newSecondment = secondmentHome.getInstance();

            Date established = DateUtils.truncate(newSecondment.getEstablished(), Calendar.DAY_OF_MONTH);
            Date dueTo = DateUtils.truncate(newSecondment.getDueTo(), Calendar.DAY_OF_MONTH);
            Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

            /* get some checking first */
            if (!validateSecondment(newSecondment, true)) {
                return ACTION_OUTCOME_FAILURE;
            }
            newSecondment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
            newSecondment.setActive(secondmentShouldBeActivated(newSecondment, today));
            newSecondment.setTargetPYSDE(newSecondment.getTargetUnit().getPysde());
            newSecondment.setSourcePYSDE(newSecondment.getSourceUnit().getPysde());
            newSecondment.setInsertedBy(getPrincipal());

            employee.addSecondment(newSecondment);
            /*
             * check if the secondment should be set as the employee's current secondment. A
             * leave can be set as the employee's current secondment if and only if the
             * secondments's period is current (ie, today is after and before leave's
             * established and dueTo dates respectively).
             */
            if (currentEmployment != null) {
                if (today.after(established) && today.before(dueTo)) {
                    currentEmployment.setSecondment(newSecondment);
                }
                newSecondment.setAffectedEmployment(currentEmployment);

            }

            //
            //      /*
            //       * if there is a current secondment, disabled it and inform the user
            //       */
            //      if (currentSecondment != null) {
            //          currentSecondment.setActive(Boolean.FALSE);
            //          currentSecondment.setSupersededBy(newSecondment);
            //          getEntityManager().merge(currentSecondment);
            //          facesMessages
            //                  .add(
            //                          Severity.WARN,
            //                          "Για τον εκπαιδευτικό #0 ο Μίνωας είχε καταχωρημένη και άλλη ενεργή απόσπαση στην μονάδα #1 με λήξη την #2, η οποία όμως ακυρώθηκε.",
            //                          (employee.getLastName() + " " + employee
            //                                  .getFirstName()), currentSecondment
            //                                  .getTargetUnit().getTitle(),
            //                          currentSecondment.getDueTo());
            //      }
            secondmentHome.persist();
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;

        } else {

            facesMessages.add(Severity.ERROR, "employee home #0 not managed.", employeeHome);
            return ACTION_OUTCOME_FAILURE;

        }

    }
    
    public String cancelSecondmentModificationAction() {
        System.err.println("lalal : " + secondmentHome.getInstance());
        try {
            System.err.println("pysde : " + secondmentHome.getInstance().getPysdeOrder());
        } catch(Exception ex) {
            
        }
        if(secondmentHome.isManaged()) {
            secondmentHome.revert();
        } else {
            secondmentHome.clearInstance();
        }
        return ACTION_OUTCOME_SUCCESS;
    }

    /* this method is being called from the page containing a list of leaves and returns the CSS class that should be used by the leave row */
    public String getTableCellClassForSecondment(Secondment secondment) {
        if (secondment.isFuture()) {
            return "rich-table-future-secondment";
        } else if (secondment.isCurrent()) {
            return "rich-table-current-secondment";
        } else if (secondment.isPast()) {
            return "rich-table-past-secondment";
        } else
            return "";
    }

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
            facesMessages.add(Severity.ERROR, "employee home #0 or secondment home #1 not managed.", employeeHome,
                    secondmentHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

    protected boolean validateSecondment(Secondment secondment, boolean addMessages) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date established = DateUtils.truncate(secondment.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(secondment.getDueTo(), Calendar.DAY_OF_MONTH);
        School currentSchool = null;
        try {
            currentSchool = secondment.getEmployee().getCurrentEmployment().getSchool();
        } catch(Exception ex) {
            ; // ignore
        }
        
        /* check if the secondoment's target unit is the employee's current school */
        if(currentSchool!=null && currentSchool.getId().equals(secondment.getTargetUnit().getId())) {
            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "Η τρέχουσα οργανική του εκπαιδευτικού είναι η ίδια με την μονάδα απόσπασης.");
            return false;
        }
         
        /* check if the dates are correct */
        if (established.after(dueTo)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "Η ημερομηνία λήξης της απόσπασης πρέπει να είναι μεταγενέστερη της έναρξης. Κάνε ένα διάλειμα για καφέ !");
            return false;
        }
        /* source & target unit must not be same */

        if (secondment.getSourceUnit() != null &&
                secondment.getSourceUnit().getId().equals(secondment.getTargetUnit().getId())) {
            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "Η μονάδα αποσπάσης πρέπει να είναι διαφορετική απο την τρέχουσα οργανική του εκπαιδευτικού. Καφέ ήπιες ;");
            return false;
        }
        
        /* check if the employee has a disposal that conflicts with the new secondment */
        Collection<Disposal> conflictingDisposals = getCoreSearching().getEmployeeDisposalWithingPeriod(getEntityManager(), secondment.getEmployee(), established, dueTo);
        if(conflictingDisposals.size()>0) {
            if (addMessages) {
                Disposal d = conflictingDisposals.iterator().next();
                String dunit = d.getDisposalUnit().getTitle();
                String dfrom = df.format(d.getEstablished());
                String dto = df.format(d.getDueTo());
                
                facesMessages
                        .add(Severity.ERROR,String.format("Η απόσπαση δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη διάθεση στην μονάδα '%s' απο '%s' εως '%s'.", dunit, dfrom, dto));
                facesMessages.add(Severity.INFO,"Εαν η απόσπαση πρέπει να καταχωρηθεί, ακυρώστε πρώτα την διάθεση.");
            }
            return false;
        }
        
        /* check if the employee has a service allocation that conflicts with the new secondment */
        Collection<ServiceAllocation> conflictingServiceAllocations = getCoreSearching().getEmployeeServiceAllocationWithinPeriod(getEntityManager(), secondment.getEmployee(), established, dueTo);
        if(conflictingServiceAllocations.size()>0) {
            if (addMessages) {
                ServiceAllocation d = conflictingServiceAllocations.iterator().next();
                String dunit = d.getServiceUnit().getTitle();
                String dfrom = df.format(d.getEstablished());
                String dto = df.format(d.getDueTo());
                
                facesMessages
                        .add(Severity.ERROR,String.format("Η απόσπαση δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη θητεία στην μονάδα '%s' απο '%s' εως '%s'.", dunit, dfrom, dto));
                facesMessages.add(Severity.INFO,"Εαν η απόσπαση πρέπει να καταχωρηθεί, ακυρώστε πρώτα την θητεία.");
            }
            return false;
        }
        
        Collection<Secondment> current_secondments = getCoreSearching().getAllEmployeeSecondments(
                employeeHome.getInstance());
        for (Secondment current_secondment : current_secondments) {
            if (current_secondment.getId().equals(secondment.getId()))
                continue;
            Date current_established = DateUtils.truncate(current_secondment.getEstablished(), Calendar.DAY_OF_MONTH);
            Date current_dueTo = DateUtils.truncate(current_secondment.getDueTo(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    String.format(
                                            "Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη απόσπαση από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει τις ίδιες ημ/νιες με αυτή που προσπαθείτε να εισάγετε.",
                                            df.format(current_secondment.getEstablished()), df.format(current_secondment.getDueTo()),
                                            current_secondment.getTargetUnit().getTitle(),
                                            df.format(secondment.getEstablished()),
                                            df.format(secondment.getDueTo()), 
                                            secondment.getTargetUnit().getTitle()));
                return false;
            }

            if (DateUtils.isSameDay(established, current_dueTo)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νια έναρξης της απόσπασης πρέπει να είναι μεταγενέστερη της λήξης της προηγούμενης απόσπασης.");
                return false;
            }

            if ((established.before(current_established) && dueTo.after(current_established)) ||
                    (established.after(current_established) && dueTo.before(current_dueTo)) ||
                    (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
                if (addMessages)
                    facesMessages
                    .add(Severity.ERROR,
                            String.format(
                                    "Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη απόσπαση από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει επικάλυψη με την απόσπαση από '%s' εως και '%s' στην μονάδα '%s' που προσπαθείτε να εισάγετε.",
                                    df.format(current_secondment.getEstablished()), df.format(current_secondment.getDueTo()),
                                    current_secondment.getTargetUnit().getTitle(),
                                    df.format(secondment.getEstablished()),
                                    df.format(secondment.getDueTo()), 
                                    secondment.getTargetUnit().getTitle()));
                return false;
            }

        }
        return true;

    }

    @Transactional
    @RaiseEvent("secondmentModified")
    public String modifySecondment() {
        if (secondmentHome.isManaged()) {
            Secondment current_secondment = secondmentHome.getInstance();
            Employee employee = employeeHome.getInstance();
            Employment employment = current_secondment.getAffectedEmployment();
            Date established = DateUtils.truncate(current_secondment.getEstablished(), Calendar.DAY_OF_MONTH);
            Date dueTo = DateUtils.truncate(current_secondment.getDueTo(), Calendar.DAY_OF_MONTH);
            Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

            if (!validateSecondment(current_secondment, true)) {
                return ACTION_OUTCOME_FAILURE;
            }

            /*
             * check if the secondment should be set as the employee's current
             * leave. A secondment can be set as the employee's current leave if and
             * only if the secondment's period is current (ie, today is after and
             * before secondment's established and dueTo dates respectively).
             */
            if (employment != null) {
                if (today.after(established) && today.before(dueTo)) {

                    employment.setSecondment(current_secondment);

                } else {
                    /*
                     * if the current secodnment is not the employee's current
                     * secondment, then check if the leave secondment to be the
                     * employee's current secodment and if so, remove it.
                     */
                    if (employment.getSecondment() != null &&
                            employment.getSecondment().getId().equals(current_secondment.getId())) {
                        employment.setSecondment(null);
                    }

                }
            }
            current_secondment.setActive(secondmentShouldBeActivated(current_secondment, today));
            secondmentHome.update();
            info("secondent #0 for employee #1 has been updated", current_secondment, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "employee home #0 or secondment home #1 not managed.", employeeHome,
                    secondmentHome);
            return ACTION_OUTCOME_FAILURE;
        }

    }

    /**
     * Checks if a secondment should be set active in regards to the reference date.
     * @param secondment
     * @param referenceDate
     * @return
     */
    protected boolean secondmentShouldBeActivated(Secondment secondment, Date referenceDate) {
        Date established = DateUtils.truncate(secondment.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(secondment.getDueTo(), Calendar.DAY_OF_MONTH);
        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
        if ((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
            return true;
        } else
            return false;
    }

    /* this method is called when the user clicks the "add new secondment" */
    public void prepareForNewSecondment() {
        secondmentHome.clearInstance();
        Secondment secondment = secondmentHome.getInstance();
        secondment.setSecondmentType(SecondmentType.FULL_TO_SCHOOL);
        secondment.setEmployeeRequested(Boolean.TRUE);
        secondment.setEstablished(getCoreSearching().getActiveSchoolYear(getEntityManager())
                .getTeachingSchoolYearStart());
        secondment.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
        secondment.setEmployee(employeeHome.getInstance());
        secondment.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
        Employment employment = employeeHome.getInstance().getCurrentEmployment();
        if (employment != null) {
            secondment.setSourceUnit(employment.getSchool());
            secondment.setSourcePYSDE(employment.getSchool().getPysde());
            secondment.setMandatoryWorkingHours(employment.getMandatoryWorkingHours());
            secondment.setFinalWorkingHours(employment.getFinalWorkingHours());
        }

    }

}
