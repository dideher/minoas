package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.Penalty;
import gr.sch.ira.minoas.model.employement.Employment;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
@Scope(ScopeType.APPLICATION)
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
        Integer totalServiceInDays;
        
        /**
         * Συνολική υπηρεσία (χωρίς προϋπηρεσία).
         */
        Integer totalServiceInDaysRaw;
        
        /**
         * Συνολικός αριθμός ημερών με ποινές
         */
        Integer totalPenaltyDays;

        /**
		 * @return the totalServiceInDays
		 */
		public Integer getTotalServiceInDays() {
			return totalServiceInDays;
		}



		/**
		 * @param totalServiceInDays the totalServiceInDays to set
		 */
		public void setTotalServiceInDays(Integer totalServiceInDays) {
			this.totalServiceInDays = totalServiceInDays;
		}



		/**
		 * @return the totalServiceInDaysRaw
		 */
		public Integer getTotalServiceInDaysRaw() {
			return totalServiceInDaysRaw;
		}



		/**
		 * @param totalServiceInDaysRaw the totalServiceInDaysRaw to set
		 */
		public void setTotalServiceInDaysRaw(Integer totalServiceInDaysRaw) {
			this.totalServiceInDaysRaw = totalServiceInDaysRaw;
		}



		/**
		 * @return the totalPenaltyDays
		 */
		public Integer getTotalPenaltyDays() {
			return totalPenaltyDays;
		}



		/**
		 * @param totalPenaltyDays the totalPenaltyDays to set
		 */
		public void setTotalPenaltyDays(Integer totalPenaltyDays) {
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
        
        Integer educationalTotal;
        
        Integer teachingTotal;
        
        Integer total;
        
        /**
         * @param educationalTotal
         * @param teachingTotal
         * @param total
         */
        public EmployeeWorkExperienceHelper(Integer educationalTotal, Integer teachingTotal, Integer total) {
            super();
            this.educationalTotal = educationalTotal;
            this.teachingTotal = teachingTotal;
            this.total = total;
        }

        /**
		 * @return the educationalTotal
		 */
		public Integer getEducationalTotal() {
			return educationalTotal;
		}



		/**
		 * @param educationalTotal the educationalTotal to set
		 */
		public void setEducationalTotal(Integer educationalTotal) {
			this.educationalTotal = educationalTotal;
		}



		/**
		 * @return the teachingTotal
		 */
		public Integer getTeachingTotal() {
			return teachingTotal;
		}



		/**
		 * @param teachingTotal the teachingTotal to set
		 */
		public void setTeachingTotal(Integer teachingTotal) {
			this.teachingTotal = teachingTotal;
		}



		/**
		 * @return the total
		 */
		public Integer getTotal() {
			return total;
		}



		/**
		 * @param total the total to set
		 */
		public void setTotal(Integer total) {
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

   
    /**
     * Calculates the sums of an employee's work experience
     * @param employee
     * @return
     */
    @Transactional(TransactionPropagationType.REQUIRED)
    public EmployeeWorkExperienceHelper calculateEmployeeWorkExperience(Employee employee) {
        Integer educational = coreSearching.getSummedEducationalWorkExperience(employee).intValue();
        Integer teaching = coreSearching.getSummedTeachingWorkExperience(employee).intValue();
        Integer all = coreSearching.getSummedWorkExperience(employee).intValue();
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
        if (employee != null && employee.getEmployeeInfo() != null) {
            EmployeeInfo einfo = employee.getEmployeeInfo();
            Date gofDate = einfo.getGogAppointmentDate();
            Date entryIntoServiceDate = einfo.getEntryIntoServiceDate();
            if(gofDate != null && entryIntoServiceDate !=null)
            	return CoreUtils.datesDifferenceIn360DaysYear(gofDate, entryIntoServiceDate) > 30 ? entryIntoServiceDate : gofDate;
           	else
            	return null;
        } else
            return null;
    }
    
    /**
     * Calculates an employee's regular service
     * @param employee
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Transactional(TransactionPropagationType.REQUIRED)
    public EmployeeServiceHelper calculateRegularEmployeeService(Employee employee, Date dateFrom, Date dateTo) {
        
        /* determine employee's total service */
        int totalServiceRaw = CoreUtils.datesDifferenceIn360DaysYear(dateFrom, dateTo);
        
        /* handle employee penalties */
        Collection<Penalty> penalties = coreSearching.getPenaltyHistory(employee);
        int totalPenaltyDays = 0;
        for(Penalty p : penalties) {
            totalPenaltyDays+=CoreUtils.datesDifferenceIn360DaysYear(p.getPenaltyStartDate(), p.getPenaltyEndDate());
        }
        
        /* prepare the return value */
        int totalServiceInDays = totalServiceRaw - totalPenaltyDays;
        
        EmployeeServiceHelper returnValue = new EmployeeServiceHelper();
        returnValue.setTotalServiceInDays(totalServiceInDays);
        returnValue.setTotalServiceInDaysRaw(totalServiceRaw);
        returnValue.setTotalPenaltyDays(totalPenaltyDays);
        return returnValue;
    }
    
    @Transactional(TransactionPropagationType.REQUIRED)
    public void updateEmployeeExperience(Employee employee) {
        Date currentDate = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
        Date fromDate = computeEmployeeFirstDayOfRegularWork(employee);
        EmployeeInfo employeeInfo = employee.getEmployeeInfo();
        WorkExperienceCalculation.EmployeeWorkExperienceHelper exp = calculateEmployeeWorkExperience(employee);
        WorkExperienceCalculation.EmployeeServiceHelper serviceHelper = calculateRegularEmployeeService(employee, fromDate, currentDate);
        
        if(employeeInfo != null) {
        	employeeInfo.setSumOfEducationalExperience(exp.getEducationalTotal());
        	employeeInfo.setSumOfTeachingExperience(exp.getTeachingTotal());
        	employeeInfo.setSumOfExperience(exp.getTotal());
        	employeeInfo.setTotalWorkService(serviceHelper.getTotalServiceInDays());
        
        	/* handle working hours */
        	Employment currentEmployment = employee.getCurrentEmployment();
        	if(currentEmployment !=null) {
            		int currentMandatoryWorkHours = currentEmployment.getMandatoryWorkingHours();
            		int totalWorkServicePlusExperience = serviceHelper.getTotalServiceInDays().intValue() + exp.getEducationalTotal().intValue();
            		int mandatoryWorkingHours = calculateEmployeeMandatoryHours(totalWorkServicePlusExperience, EmployeeType.REGULAR);
            		if(currentMandatoryWorkHours!=mandatoryWorkingHours) {
                		/* we have a mandatory work hour mismatch ! */
                		info("Εmployee's '#0' working hours will be updated from '#1' to '#2", employee, currentMandatoryWorkHours, mandatoryWorkingHours);
                		currentEmployment.setMandatoryWorkingHours(mandatoryWorkingHours);
               	 		currentEmployment.setFinalWorkingHours(mandatoryWorkingHours);
            	}
        	}
        }
    }
    
    
    
    
    /**
     * Calculates the mandatory working hours for a employee based on the employee type and his service expressed in days
     * @param totalExperienceInDays
     * @param employeeType
     * @return
     */
    public Integer calculateEmployeeMandatoryHours(Integer totalExperienceInDays, EmployeeType employeeType) {
        int years = totalExperienceInDays / 360; // in question
        /* computation logic from :
         * http://edu.klimaka.gr/leitoyrgia-sxoleivn/anakoinvseis/539-school-diafora-vrario-ergasia-symplhrvsh-orariou.html
         */
        if (years >= 0 && years <= 6)
            return 23;
        else if (years >= 7 && years <= 12)
            return 21;
        else if (years >= 13 && years <= 20)
            return 20;
        else if (years >= 20)
            return 18;
        else
            throw new RuntimeException(String.format(
                    "failed to compute mandatory work hours for experience '%d' and type '%s'",
                    totalExperienceInDays.intValue(), employeeType.toString()));

    }
    

}
