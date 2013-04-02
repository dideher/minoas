package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.employement.DisposalType;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.DisposalHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;

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

@Name(value = "employeeDisposalsManagement")
@Scope(ScopeType.CONVERSATION)
public class EmployeeDisposalsManagement extends BaseDatabaseAwareSeamComponent {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String diposalUnitHelper;

    @In(required = true, create = true)
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
    private DisposalHome disposalHome;

    @Transactional
    @RaiseEvent("disposalCreated")
    public String addEmployeeDisposalAction() {
        if (employeeHome.isManaged()) {
            Employee employee = employeeHome.getInstance();
            Employment currentEmployment = employee.getCurrentEmployment();
            Disposal newDisposal = disposalHome.getInstance();

            Date established = DateUtils.truncate(newDisposal.getEstablished(), Calendar.DAY_OF_MONTH);
            Date dueTo = DateUtils.truncate(newDisposal.getDueTo(), Calendar.DAY_OF_MONTH);
            Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

            /* get some checking first */
            if (!validateDisposal(newDisposal, true)) {
                return ACTION_OUTCOME_FAILURE;
            }
            newDisposal.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));
            newDisposal.setActive(disposalShouldBeActivated(newDisposal, today));
            newDisposal.setInsertedBy(getPrincipal());
            newDisposal.setInsertedOn(new Date());
            newDisposal.setEmployee(employee);

            disposalHome.persist();
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;

        } else {

            facesMessages.add(Severity.ERROR, "employee home #0 not managed.", employeeHome);
            return ACTION_OUTCOME_FAILURE;

        }

    }

    public String cancelDisposalModificationAction() {
        try {
            System.err.println("pysde : " + disposalHome.getInstance().getPysdeOrder());
        } catch (Exception ex) {

        }
        if (disposalHome.isManaged()) {
            disposalHome.revert();
        } else {
            disposalHome.clearInstance();
        }
        return ACTION_OUTCOME_SUCCESS;
    }

    /* this method is being called from the page containing a list of disposals and returns the CSS class that should be used by the disposal row */
    public String getTableCellClassForDisposal(Disposal disposal) {
        if (disposal.isFuture()) {
            return "rich-table-future-disposal";
        } else if (disposal.isCurrent()) {
            return "rich-table-current-disposal";
        } else if (disposal.isPast()) {
            return "rich-table-past-disposal";
        } else
            return "";
    }

    @Transactional
    @RaiseEvent("disposalDeleted")
    public String deleteEmployeeDisposalAction() {
        if (employeeHome.isManaged() && disposalHome.isManaged()) {
            Employee employee = getEntityManager().merge(employeeHome.getInstance());
            Disposal disposal = disposalHome.getInstance();
            info("deleting employee #0 disposal #1", employee, disposal);

//            for (TeachingHourCDR cdr : leave.getLeaveCDRs()) {
//                info("deleting leave's #0 cdr #1", employee, cdr);
//                cdr.setLeave(null);
//                getEntityManager().remove(cdr);
//            }
//
//            leave.getLeaveCDRs().removeAll(leave.getLeaveCDRs());
            disposal.setActive(Boolean.FALSE);
            disposal.setDeleted(Boolean.TRUE);
            disposal.setDeletedOn(new Date());
            disposal.setDeletedBy(getPrincipal());
            disposalHome.update();
            info("disposal #0 for employee #1 has been deleted", disposal, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "employee home #0 or disposal home #1 not managed.", employeeHome,
                    disposalHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }

    protected boolean validateDisposal(Disposal disposal, boolean addMessages) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date established = DateUtils.truncate(disposal.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(disposal.getDueTo(), Calendar.DAY_OF_MONTH);
        Employee employee = employeeHome.getInstance();
        School currentSchool = null;
        try {
            currentSchool = employee.getCurrentEmployment().getSchool();
        } catch (Exception ex) {
            ; // ignore
        }

        /* check hours & days */
        if (disposal.getHours() == null || disposal.getHours() <= 0) {
            if (addMessages)
                facesMessages.add(Severity.ERROR, "Οι ώρες της διάθεσης πρέπει να είναι θετικός αριθμός");
            return false;
        }

        if (employee.getCurrentEmployment() != null) {
            if (disposal.getHours() == null ||
                    disposal.getHours() > employee.getCurrentEmployment().getMandatoryWorkingHours()) {
                if (addMessages)
                    facesMessages.add(Severity.ERROR,
                            "Οι ώρες της διάθεσης δεν μπορούν να υπερβαίνουν το υποχρεωτικό ωράριο του εκπαιδευτικού.");
                return false;
            }
        }

        if (disposal.getDays() == null || disposal.getDays() <= 0) {
            if (addMessages)
                facesMessages.add(Severity.ERROR, "Οι ήμερες διάθεσης πρέπει να είναι θετικός αριθμός.");
            return false;
        }

        if (disposal.getDays() == null || disposal.getDays() > 5) {
            if (addMessages)
                facesMessages.add(Severity.ERROR, "Οι ήμερες διάθεσης δεν μπορούν να υπερβαίνουν τις πέντε (5).");
            return false;
        }
        
        if (disposal.getDisposalUnit() == null) {
            if (addMessages)
                facesMessages.add(Severity.ERROR, "Προσοχή, δεν έχετε επιλέξει μονάδα διάθεσης.");
            return false;
        }

        /* check if the disposal target unit is the employee's current school */
        if (disposal.getSourceUnit().getId().equals(disposal.getDisposalUnit().getId())) {
            if (addMessages)
                facesMessages.add(Severity.ERROR,
                        "Η τρέχουσα οργανική του εκπαιδευτικού είναι η ίδια με την μονάδα διάθεσης.");
            return false;
        }
        

        /* check if the dates are correct */
        if (established.after(dueTo)) {

            if (addMessages)
                facesMessages
                        .add(Severity.ERROR,
                                "Η ημερομηνία λήξης της διάθεσης πρέπει να είναι μεταγενέστερη της έναρξης. Κάνε ένα διάλειμα για καφέ !");
            return false;
        }

//        /* check if the employee has a disposal that conflicts with the new secondment */
//        Collection<Disposal> conflictingDisposals = getCoreSearching().getEmployeeDisposalWithingPeriod(getEntityManager(), disposal.getEmployee(), established, dueTo);
//        if(conflictingDisposals.size()>0) {
//            if (addMessages) {
//                Disposal d = conflictingDisposals.iterator().next();
//                String dunit = d.getDisposalUnit().getTitle();
//                String dfrom = df.format(d.getEstablished());
//                String dto = df.format(d.getDueTo());
//                
//                facesMessages
//                        .add(Severity.ERROR,String.format("Η διάθεση δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη διάθεση στην μονάδα '%s' απο '%s' εως '%s'.", dunit, dfrom, dto));
//                facesMessages.add(Severity.INFO,"Εαν η απόσπαση πρέπει να καταχωρηθεί, ακυρώστε πρώτα την διάθεση.");
//            }
//            return false;
//        }

        /* check if the employee has a service allocation that conflicts with the new secondment */
        Collection<ServiceAllocation> conflictingServiceAllocations = getCoreSearching()
                .getEmployeeServiceAllocationWithinPeriod(getEntityManager(), disposal.getEmployee(), established,
                        dueTo);
        if (conflictingServiceAllocations.size() > 0) {
            if (addMessages) {
                ServiceAllocation d = conflictingServiceAllocations.iterator().next();
                String dunit = d.getServiceUnit().getTitle();
                String dfrom = df.format(d.getEstablished());
                String dto = df.format(d.getDueTo());

                facesMessages
                        .add(Severity.ERROR,
                                String.format(
                                        "Η διάθεση δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη θητεία στην μονάδα '%s' απο '%s' εως '%s'.",
                                        dunit, dfrom, dto));
                facesMessages.add(Severity.INFO, "Εαν η διάθεση πρέπει να καταχωρηθεί, ακυρώστε πρώτα την θητεία.");
            }
            return false;
        }

        Collection<Disposal> current_disposal = getCoreSearching().getAllEmployeeDisposals(employeeHome.getInstance());
        for (Disposal current_secondment : current_disposal) {
            if (current_secondment.getId().equals(disposal.getId()))
                continue;
            Date current_established = DateUtils.truncate(current_secondment.getEstablished(), Calendar.DAY_OF_MONTH);
            Date current_dueTo = DateUtils.truncate(current_secondment.getDueTo(), Calendar.DAY_OF_MONTH);
            if (DateUtils.isSameDay(established, current_established) || DateUtils.isSameDay(dueTo, current_dueTo)) {
                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    String.format(
                                            "Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη διάθεση από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει τις ίδιες ημ/νιες με αυτή που προσπαθείτε να εισάγετε.",
                                            df.format(current_secondment.getEstablished()), df
                                                    .format(current_secondment.getDueTo()), current_secondment
                                                    .getDisposalUnit().getTitle()));
                return false;
            }

            if (DateUtils.isSameDay(established, current_dueTo)) {

                if (addMessages)
                    facesMessages
                            .add(Severity.ERROR,
                                    "Η ημ/νια έναρξης της διάθεσης πρέπει να είναι μεταγενέστερη της λήξης της προηγούμενης διάθεσης.");
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
                                            df.format(current_secondment.getEstablished()), df
                                                    .format(current_secondment.getDueTo()), current_secondment
                                                    .getDisposalUnit().getTitle(),
                                            df.format(disposal.getEstablished()), df.format(disposal.getDueTo()),
                                            disposal.getDisposalUnit().getTitle()));
                return false;
            }

        }
        return true;

    }

    @Transactional
    @RaiseEvent("disposalModified")
    public String modifyDisposal() {
        if (disposalHome.isManaged()) {
            Disposal current_secondment = disposalHome.getInstance();
            Employee employee = employeeHome.getInstance();
            Employment employment = current_secondment.getAffectedEmployment();

            SchoolYear currentSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

            Date dueTo = DateUtils.truncate(currentSchoolYear.getTeachingSchoolYearStop(), Calendar.DAY_OF_MONTH);
            Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

            if (!validateDisposal(current_secondment, true)) {

                return ACTION_OUTCOME_FAILURE;
            }

            current_secondment.setActive(disposalShouldBeActivated(current_secondment, today));
            disposalHome.update();
            info("disposal #0 for employee #1 has been updated", current_secondment, employee);
            getEntityManager().flush();
            return ACTION_OUTCOME_SUCCESS;
        } else {
            facesMessages.add(Severity.ERROR, "employee home #0 or disposal home #1 not managed.", employeeHome,
                    disposalHome);
            return ACTION_OUTCOME_FAILURE;
        }

    }

    /**
     * Checks if a disposal should be set active in regards to the reference date.
     * @param secondment
     * @param referenceDate
     * @return
     */
    protected boolean disposalShouldBeActivated(Disposal disposal, Date referenceDate) {
        Date established = DateUtils.truncate(disposal.getEstablished(), Calendar.DAY_OF_MONTH);
        Date dueTo = DateUtils.truncate(disposal.getDueTo(), Calendar.DAY_OF_MONTH);
        Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
        if ((established.before(today) || established.equals(today)) && (dueTo.after(today) || dueTo.equals(today))) {
            return true;
        } else
            return false;
    }

    /* this method is called when the user clicks the "add new disposal" */
    public void prepareForNewDisposal() {
        disposalHome.clearInstance();
        Employee employee = employeeHome.getInstance();
        Disposal disposal = disposalHome.getInstance();

        SchoolYear currentSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
        Date today = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);

        Date dueTo = DateUtils.truncate(currentSchoolYear.getTeachingSchoolYearStop(), Calendar.DAY_OF_MONTH);

        disposal.setSchoolYear(currentSchoolYear);
        disposal.setActive(Boolean.TRUE);
        disposal.setEstablished(today);
        disposal.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
        disposal.setType(DisposalType.PARTIAL);
        disposal.setTargetType(DisposalTargetType.TO_SCHOOL);

        /* we need to clarify if the employee has a secondment or not */

        Collection<Secondment> secondments = getCoreSearching().getEmployeeSecondmentWithinPeriod(getEntityManager(),
                employee, today, dueTo);
        if (secondments != null && secondments.size() > 0) {
            disposal.setAffectedSecondment(secondments.iterator().next());
        } else {
            disposal.setAffectedEmployment(employee.getCurrentEmployment());
        }

    }

    /**
     * @return the diposalUnitHelper
     */
    public String getDiposalUnitHelper() {
        return diposalUnitHelper;
    }

    /**
     * @param diposalUnitHelper the diposalUnitHelper to set
     */
    public void setDiposalUnitHelper(String diposalUnitHelper) {
        this.diposalUnitHelper = diposalUnitHelper;
    }

}
