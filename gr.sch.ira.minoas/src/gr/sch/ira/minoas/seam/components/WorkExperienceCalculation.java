package gr.sch.ira.minoas.seam.components;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.Penalty;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("workExperienceCalculation")
@Scope(ScopeType.PAGE)
@AutoCreate
public class WorkExperienceCalculation extends BaseDatabaseAwareSeamComponent {
    
    /**
     * Helper class for holding / storing employee's service values
     * 
     * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
     * @version $Id$
     */
    public class EmployeeServiceHelper {
        
        /**
         * Συνολική υπηρεσία (χωρίς προϋπηρεσία) αφού έχουν αφαιρεθεί ποινές, μειωμένα ωράρια, κτλ.
         */
        Long totalServiceInDays;
        
        /**
         * Συνολική υπηρεσία (χωρίς προϋπηρεσία).
         */
        Long totalServiceInDaysRaw;
        
        /**
         * Συνολικός αριθμός ημερών με ποινές
         */
        Long totalPenaltyDays;

        /**
         * @return the totalServiceInDays
         */
        public Long getTotalServiceInDays() {
            return totalServiceInDays;
        }

        /**
         * @param totalServiceInDays the totalServiceInDays to set
         */
        public void setTotalServiceInDays(Long totalServiceInDays) {
            this.totalServiceInDays = totalServiceInDays;
        }

        /**
         * @return the totalServiceInDaysRaw
         */
        public Long getTotalServiceInDaysRaw() {
            return totalServiceInDaysRaw;
        }

        /**
         * @param totalServiceInDaysRaw the totalServiceInDaysRaw to set
         */
        public void setTotalServiceInDaysRaw(Long totalServiceInDaysRaw) {
            this.totalServiceInDaysRaw = totalServiceInDaysRaw;
        }

        /**
         * @return the totalPenaltyDays
         */
        public Long getTotalPenaltyDays() {
            return totalPenaltyDays;
        }

        /**
         * @param totalPenaltyDays the totalPenaltyDays to set
         */
        public void setTotalPenaltyDays(Long totalPenaltyDays) {
            this.totalPenaltyDays = totalPenaltyDays;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("EmployeeServiceHelper [totalServiceInDays=");
            builder.append(totalServiceInDays);
            builder.append(", totalServiceInDaysRaw=");
            builder.append(totalServiceInDaysRaw);
            builder.append(", totalPenaltyDays=");
            builder.append(totalPenaltyDays);
            builder.append("]");
            return builder.toString();
        }
    }
    
    /**
     * Helper class for holding / storing employee's work experience values
     * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
     * @version $Id$
     */
    public class EmployeeWorkExperienceHelper {
        
        Long educationalTotal;
        
        Long teachingTotal;
        
        Long total;
        
        /**
         * @param educationalTotal
         * @param teachingTotal
         * @param total
         */
        public EmployeeWorkExperienceHelper(Long educationalTotal, Long teachingTotal, Long total) {
            super();
            this.educationalTotal = educationalTotal;
            this.teachingTotal = teachingTotal;
            this.total = total;
        }

        /**
         * @return the educationalTotal
         */
        public Long getEducationalTotal() {
            return educationalTotal;
        }

        /**
         * @return the teachingTotal
         */
        public Long getTeachingTotal() {
            return teachingTotal;
        }

        /**
         * @return the total
         */
        public Long getTotal() {
            return total;
        }

        /**
         * @param educationalTotal the educationalTotal to set
         */
        public void setEducationalTotal(Long educationalTotal) {
            this.educationalTotal = educationalTotal;
        }

        /**
         * @param teachingTotal the teachingTotal to set
         */
        public void setTeachingTotal(Long teachingTotal) {
            this.teachingTotal = teachingTotal;
        }

        /**
         * @param total the total to set
         */
        public void setTotal(Long total) {
            this.total = total;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("EmployeeWorkExperienceHelper [educationalTotal=");
            builder.append(educationalTotal);
            builder.append(", teachingTotal=");
            builder.append(teachingTotal);
            builder.append(", total=");
            builder.append(total);
            builder.append("]");
            return builder.toString();
        }
    }
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @In(value="coreSearching")
    protected CoreSearching coreSearching;

   
    @Transactional(TransactionPropagationType.REQUIRED)
    public EmployeeWorkExperienceHelper calculateEmployeeWorkExperience(Employee employee) {
        Long educational = coreSearching.getSummedEducationalWorkExperience(employee);
        Long teaching = coreSearching.getSummedTeachingWorkExperience(employee);
        Long all = coreSearching.getSummedWorkExperience(employee);
        return new EmployeeWorkExperienceHelper(educational, teaching, all);
    }
    
    /**
     * Επιστρέφει την ημ/νια "έναρξης" της εργασίας κάποιου μόνιμου εκπαιδευτικού. Η ημ/νια αυτή
     * είναι η ημ/νια διορισμού (ΦΕΚ) ή η ημ/νια ανάληψης υπηρεσίας στην περίπτωση που η ανάληψη γίνει 
     * 30+1 ημέρες μετά το ΦΕΚ.
     * @param employee
     * @return
     */
    public Date computeEmployeeFirstDayOfRegularWork(Employee employee) {
        EmployeeInfo einfo = employee.getEmployeeInfo();
        Date gofDate = einfo.getGogAppointmentDate();
        Date entryIntoServiceDate = einfo.getEntryIntoServiceDate();
        return CoreUtils.DatesDifferenceIn360DaysYear(gofDate, entryIntoServiceDate) > 30 ? entryIntoServiceDate : gofDate;
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public EmployeeServiceHelper calculateRegularEmployeeService(Employee employee, Date dateFrom, Date dateTo) {
        
        /* determine employee's total service */
        int totalServiceRaw = CoreUtils.DatesDifferenceIn360DaysYear(dateFrom, dateTo);
        
        /* handle employee penalties */
        Collection<Penalty> penalties = coreSearching.getPenaltyHistory(employee);
        int totalPenaltyDays = 0;
        for(Penalty p : penalties) {
            totalPenaltyDays+=CoreUtils.DatesDifferenceIn360DaysYear(p.getPenaltyStartDate(), p.getPenaltyEndDate());
        }
        
        /* prepare the return value */
        int totalServiceInDays = totalServiceRaw - totalPenaltyDays;
        
        EmployeeServiceHelper returnValue = new EmployeeServiceHelper();
        returnValue.setTotalServiceInDays(new Long(totalServiceInDays));
        returnValue.setTotalServiceInDaysRaw(new Long(totalServiceRaw));
        returnValue.setTotalPenaltyDays(new Long(totalPenaltyDays));
        return returnValue;
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public void updateEmployeeExperience(Employee employee) {
        Date currentDate = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
        Date fromDate = computeEmployeeFirstDayOfRegularWork(employee);
        EmployeeInfo employeeInfo = employee.getEmployeeInfo();
        WorkExperienceCalculation.EmployeeWorkExperienceHelper exp = calculateEmployeeWorkExperience(employee);
        WorkExperienceCalculation.EmployeeServiceHelper serviceHelper = calculateRegularEmployeeService(employee, fromDate, currentDate);
        
        employeeInfo.setSumOfEducationalExperience(new Integer(exp.getEducationalTotal().intValue()));
        employeeInfo.setSumOfTeachingExperience(new Integer(exp.getTeachingTotal().intValue()));
        employeeInfo.setSumOfExperience(new Integer(exp.getTotal().intValue()));
        employeeInfo.setTotalWorkService(new Integer(serviceHelper.getTotalServiceInDays().intValue()));
        
        info("computed and updated successfully work experience '#0' and service '#1' for employee '#2'.", exp, serviceHelper, employee);
        getEntityManager().flush();
    }
    
    

    

}
