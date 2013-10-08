package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.Penalty;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		 * Συνολική υπηρεσία (χωρίς προϋπηρεσία) αφού έχουν αφαιρεθεί ποινές,
		 * μειωμένα ωράρια, κτλ.
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
		 * Συνολικός αριθμός ημερών σε άδεια ανεύ αποδοχών
		 */
		Integer totalUnpaidDays;

		public Integer getTotalUnpaidDays() {
			return totalUnpaidDays;
		}

		public void setTotalUnpaidDays(Integer totalUnpaidDays) {
			this.totalUnpaidDays = totalUnpaidDays;
		}

		/**
		 * @return the totalServiceInDays
		 */
		public Integer getTotalServiceInDays() {
			return totalServiceInDays;
		}

		/**
		 * @param totalServiceInDays
		 *            the totalServiceInDays to set
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
		 * @param totalServiceInDaysRaw
		 *            the totalServiceInDaysRaw to set
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
		 * @param totalPenaltyDays
		 *            the totalPenaltyDays to set
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
	 * 
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
		public EmployeeWorkExperienceHelper(Integer educationalTotal,
				Integer teachingTotal, Integer total) {
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
		 * @param educationalTotal
		 *            the educationalTotal to set
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
		 * @param teachingTotal
		 *            the teachingTotal to set
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
		 * @param total
		 *            the total to set
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

	@In(value = "coreSearching")
	protected CoreSearching coreSearching;

	/**
	 * Calculates the sums of an employee's work experience
	 * 
	 * @param employee
	 * @return
	 */
	@Transactional(TransactionPropagationType.REQUIRED)
	public EmployeeWorkExperienceHelper calculateEmployeeWorkExperience(
			Employee employee) {
		Integer educational = coreSearching.getSummedEducationalWorkExperience(
				employee).intValue();
		Integer teaching = coreSearching.getSummedTeachingWorkExperience(
				employee).intValue();
		Integer all = coreSearching.getSummedWorkExperience(employee)
				.intValue();
		return new EmployeeWorkExperienceHelper(educational, teaching, all);
	}

	/**
	 * Επιστρέφει την ημ/νία "έναρξης" της εργασίας κάποιου μόνιμου
	 * εκπαιδευτικού. Η ημ/νία αυτή είναι η ημ/νία διορισμού (ΦΕΚ) ή η ημ/νία
	 * ανάληψης υπηρεσίας στην περίπτωση που η ανάληψη γίνει 30+1 ημέρες μετά το
	 * ΦΕΚ.
	 * 
	 * @param employee
	 * @return
	 */
	public Date computeEmployeeFirstDayOfRegularWork(Employee employee) {
		if (employee != null && employee.getRegularEmployeeInfo() != null) {
			try {
				RegularEmployeeInfo reinfo = employee.getRegularEmployeeInfo();
				Employment curr_employment = employee.getCurrentEmployment();
				Date gofDate = reinfo.getAppointmentGOGDate();
				Date entryIntoServiceDate = curr_employment != null ?curr_employment
						.getEntryIntoServiceDate() : null;
				if (gofDate != null && entryIntoServiceDate != null)
					return CoreUtils.datesDifferenceIn360DaysYear(gofDate,
							entryIntoServiceDate) > 60 ? entryIntoServiceDate
							: gofDate;
				else
					return null;
			} catch (Exception ex) {
				throw new RuntimeException(String.format("exception while handling employee %s with exception %s", employee, ex));
			}
		} else
			return null;
	}

	public Integer calculateEmployeeUnPaidDays(Employee employee,
			Date dateFrom, Date dateTo) {
		List<String> legacyCodes = Arrays.asList("32", "33", "34", "44");

		// Calendar cal = Calendar.getInstance();
		// cal.set(Calendar.DAY_OF_MONTH, 01);
		// cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		// cal.set(Calendar.YEAR, 2011);
		//
		// dateFrom = DateUtils.truncate(cal.getTime(), Calendar.DAY_OF_MONTH);

		Collection<EmployeeLeave> leaves = coreSearching.getEmployeeLeaves2(
				employee, dateFrom, dateTo);
		
		/*
		 * Interate over the employee's leaves (of the given type) and construct
		 * a hash with key the leave's year and value the sum of leave days (360
		 * per year) during this year. Take extra care for leaves spanning
		 * across several years.
		 */
		Map<Integer, Integer> daysOfLeavePerYearHash = new HashMap<Integer, Integer>();
		int duration = 0;
		int daysToTrim = 0; // we use the variable to store the number of days needed to trim from an leave (ie a leave which is due to in the past)
		for (EmployeeLeave leave : leaves) {
			
			duration = 0;
			daysToTrim = 0;
			String legacyCode = leave.getEmployeeLeaveType().getLegacyCode();
			if (legacyCodes.contains(legacyCode)) {
				// System.err.println(leave);
				if (leave.getEstablished().before(dateFrom)) {
					daysToTrim += CoreUtils.datesDifferenceIn360DaysYear(
							leave.getEstablished(), dateFrom);
					//System.err.println(daysToTrim);
				}

				if (leave.getDueTo().after(dateTo)) {
					daysToTrim += CoreUtils.datesDifferenceIn360DaysYear(
							dateTo, leave.getDueTo());
					//System.err.println(daysToTrim);
				}

				Calendar leaveStart = Calendar.getInstance();
				leaveStart.setTime(leave.getEstablished());
				Calendar leaveStop = Calendar.getInstance();
				leaveStop.setTime(leave.getDueTo());
				int leaveYearStart = leaveStart.get(Calendar.YEAR);
				int leaveYearStop = leaveStop.get(Calendar.YEAR);
				if (leaveYearStart != leaveYearStop) {
					/* leave spans across years */
					for (int yearIndex = leaveYearStart; yearIndex <= leaveYearStop; yearIndex++) {
						Calendar tempLeaveStart = Calendar.getInstance();
						Calendar tempLeaveStop = Calendar.getInstance();
						if (leaveYearStart == yearIndex) {
							/*
							 * this is the section of the leave within the start
							 * year
							 */
							tempLeaveStart.setTime(leave.getEstablished());
							tempLeaveStop.set(Calendar.DAY_OF_MONTH, 31);
							tempLeaveStop
									.set(Calendar.MONTH, Calendar.DECEMBER);
							tempLeaveStop.set(Calendar.YEAR, yearIndex);

						} else if (leaveYearStop == yearIndex) {
							/*
							 * this is the section of the leave during the stop
							 * year
							 */
							tempLeaveStart.set(Calendar.DAY_OF_MONTH, 1);
							tempLeaveStart
									.set(Calendar.MONTH, Calendar.JANUARY);
							tempLeaveStart.set(Calendar.YEAR, yearIndex);
							tempLeaveStop.setTime(leave.getDueTo());
						} else {
							/*
							 * this is the section of the leave not during the
							 * start or end year
							 */
							tempLeaveStart.set(Calendar.DAY_OF_MONTH, 1);
							tempLeaveStart
									.set(Calendar.MONTH, Calendar.JANUARY);
							tempLeaveStart.set(Calendar.YEAR, yearIndex);
							tempLeaveStop.set(Calendar.DAY_OF_MONTH, 31);
							tempLeaveStop
									.set(Calendar.MONTH, Calendar.DECEMBER);
							tempLeaveStop.set(Calendar.YEAR, yearIndex);
						}

						duration = CoreUtils.datesDifferenceIn360DaysYear(
								tempLeaveStart.getTime(),
								tempLeaveStop.getTime(), true);
						}

				} else {
					/* leave does not span across years */
					duration = CoreUtils.datesDifferenceIn360DaysYear(
							leaveStart.getTime(), leaveStop.getTime(), true);
					
				}
				
				/* from the duration of the leave, trim days if required */
				duration = duration - daysToTrim;
				
				
				Integer currentYearDuration = daysOfLeavePerYearHash
						.get(leaveYearStart);
				if (currentYearDuration == null) {
					daysOfLeavePerYearHash.put(leaveYearStart, duration);
				} else {
					daysOfLeavePerYearHash.put(leaveYearStart,
							currentYearDuration + duration);
				}
			}
		}
		// System.err.println(daysOfLeavePerYearHash);
		/*
		 * at this point we have a Map with duration in days for each year. We
		 * will iterate over the years and deduct 30 days for each year
		 */
		int totalDaysWithoutPayment = 0; // ημέρες άδειας χωρίς αποδοχές
		for (Integer year : daysOfLeavePerYearHash.keySet()) {
			Integer _duration = daysOfLeavePerYearHash.get(year);
			totalDaysWithoutPayment += _duration > 30 ? _duration - 30 : 0;
		}
		//System.err.println(daysOfLeavePerYearHash);
		//System.err.println(totalDaysWithoutPayment);
		return totalDaysWithoutPayment - daysToTrim;
	}

	/**
	 * Calculates an employee's regular service
	 * 
	 * @param employee
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@Transactional(TransactionPropagationType.REQUIRED)
	public EmployeeServiceHelper calculateRegularEmployeeService(
			Employee employee, Date dateFrom, Date dateTo) {
		
		//DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, new Locale("el", "gr"));

		/* determine employee's total service */
		int totalServiceRaw = CoreUtils.datesDifferenceIn360DaysYear(dateFrom,
				dateTo, true);
		//System.err.println(String.format("Employee '%s' service from '%s' to '%s' is '%d' day(s).", employee, df.format(DateUtils.truncate(dateFrom, Calendar.HOUR)), df.format(DateUtils.truncate(dateTo, Calendar.HOUR)), totalServiceRaw));

		/* handle employee penalties */
		Collection<Penalty> penalties = coreSearching
				.getPenaltyHistory(employee);
		int totalPenaltyDays = 0;
		for (Penalty p : penalties) {
			totalPenaltyDays += CoreUtils.datesDifferenceIn360DaysYear(
					p.getPenaltyStartDate(), p.getPenaltyEndDate(), true);
		}

		/* gh-75 - handle employee leaves */
		int totalDaysWithoutPayment = calculateEmployeeUnPaidDays(employee,
				dateFrom, dateTo);
		/* gh-75 - handle employee leaves */

		/* prepare the return value */
		int totalServiceInDays = totalServiceRaw
				- (totalPenaltyDays + totalDaysWithoutPayment);

		EmployeeServiceHelper returnValue = new EmployeeServiceHelper();
		returnValue.setTotalServiceInDays(totalServiceInDays);
		returnValue.setTotalServiceInDaysRaw(totalServiceRaw);
		returnValue.setTotalPenaltyDays(totalPenaltyDays);
		returnValue.setTotalUnpaidDays(totalDaysWithoutPayment);
		return returnValue;
	}

	@Transactional(TransactionPropagationType.REQUIRED)
	public void updateEmployeeExperience(Employee employee) {
		Date currentDate = DateUtils.truncate(
				new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
		Date fromDate = computeEmployeeFirstDayOfRegularWork(employee);
		EmployeeInfo employeeInfo = employee.getEmployeeInfo();
		WorkExperienceCalculation.EmployeeWorkExperienceHelper exp = calculateEmployeeWorkExperience(employee);
		WorkExperienceCalculation.EmployeeServiceHelper serviceHelper = calculateRegularEmployeeService(
				employee, fromDate, currentDate);

		if (employeeInfo != null) {
			employeeInfo.setSumOfEducationalExperience(exp
					.getEducationalTotal());
			employeeInfo.setSumOfTeachingExperience(exp.getTeachingTotal());
			employeeInfo.setSumOfExperience(exp.getTotal());
			employeeInfo.setTotalWorkService(serviceHelper
					.getTotalServiceInDays());
			employeeInfo.setSumOfDaysDuringUnpaidLeaves(serviceHelper
					.getTotalUnpaidDays());

			/* handle working hours */
			Employment currentEmployment = employee.getCurrentEmployment();
			if (currentEmployment != null) {
				int currentMandatoryWorkHours = currentEmployment
						.getMandatoryWorkingHours();
				int totalWorkServicePlusExperience = serviceHelper
						.getTotalServiceInDays().intValue()
						+ exp.getEducationalTotal().intValue();
				int mandatoryWorkingHours = calculateEmployeeMandatoryHours(
						totalWorkServicePlusExperience, EmployeeType.REGULAR);
				if (currentMandatoryWorkHours != mandatoryWorkingHours) {
					/* we have a mandatory work hour mismatch ! */
					info("Εmployee's '#0' working hours will be updated from '#1' to '#2",
							employee, currentMandatoryWorkHours,
							mandatoryWorkingHours);
					currentEmployment
							.setMandatoryWorkingHours(mandatoryWorkingHours);
					currentEmployment
							.setFinalWorkingHours(mandatoryWorkingHours);
				}
			}
		}
	}

	/**
	 * Calculates the mandatory working hours for a employee based on the
	 * employee type and his service expressed in days
	 * 
	 * @param totalExperienceInDays
	 * @param employeeType
	 * @return
	 */
	public Integer calculateEmployeeMandatoryHours(
			Integer totalExperienceInDays, EmployeeType employeeType) {
		int years = totalExperienceInDays / 360; // in question
		/*
		 * computation logic from :
		 * http://edu.klimaka.gr/leitoyrgia-sxoleivn/anakoinvseis
		 * /539-school-diafora-vrario-ergasia-symplhrvsh-orariou.html
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
			throw new RuntimeException(
					String.format(
							"failed to compute mandatory work hours for experience '%d' and type '%s'",
							totalExperienceInDays.intValue(),
							employeeType.toString()));

	}

}
