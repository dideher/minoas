/**
 * 
 */
package gr.sch.ira.minoas.core;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.seam.components.WorkExperienceCalculation;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.core.SeamResourceBundle;

/**
 * @author slavikos
 *
 */
public abstract class CoreUtils {
    
    public static final String SEAM_MESSAGES_RESOURCE_BUNDLE_NAME = "messages";

	public static final String getSearchPattern(String searchString) {
		return searchString == null ? "%" : '%' + searchString.trim().toLowerCase().replace('*', '%') + '%';
	}

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static String getLocalizedMessage(String message_key) {
    	return getResourceBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME).getString(message_key);
    }

    public static ResourceBundle getResourceBundle(String resource_budle_name) {
        return SeamResourceBundle.getBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME, getFacesContext()
    			.getViewRoot().getLocale());
    }
	
    
    
    public static int getDatesDifference(Date fromDate, Date toDate) {
        if (fromDate != null && toDate != null) {
            long DAY_TIME_IN_MILLIS = 24 * 60 * 60 * 1000;
            long date1DaysMS = fromDate.getTime() - (fromDate.getTime() % DAY_TIME_IN_MILLIS);
            long date2DaysMS = toDate.getTime() - (toDate.getTime() % DAY_TIME_IN_MILLIS);

            long timeInMillisDiff = (date2DaysMS - date1DaysMS);
            return (int) ((timeInMillisDiff / DAY_TIME_IN_MILLIS) + 1);
        } else
            return 0;
    }

    public static int getDatesDifferenceWithoutWeekend(Date fromDate, Date toDate) {
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
            return (countDays+1);
        } else
            return 0;
    }
    
    //	Return the days difference between two dates in a 360 days year (12 * 30 days)
    
    /**
     * @param dateFrom: The start date of the period. 
     * @param dateTo: The end date of the period. 
     * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns 0 if start date is AFTER end date.
     */
    public static int datesDifferenceIn360DaysYear(Date dateFrom, Date dateTo) {
    	if(dateFrom == null || dateTo == null || dateFrom.after(dateTo))
    		return 0;
        
    	return get360DaysYearDiff(dateFrom, dateTo);
    }

    
//    /**
//     * @param dateFrom: The start date of the period. 
//     * @param dateTo: The end date of the period. 
//     * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
//     */
//    public static int get360DaysYearDiff(Date dateFrom, Date dateTo) {
//    	if(dateFrom == null || dateTo == null)
//    		return 0;
//        Calendar startingDate = getStartingDate(dateFrom);
//        Calendar endingDate = getEndingDateAccordingToStartingDate(dateTo, startingDate);
//        long startingDay = startingDate.get(Calendar.MONTH) * 30 + startingDate.get(Calendar.DAY_OF_MONTH);
//        long endingDay = (endingDate.get(Calendar.YEAR) - startingDate.get(Calendar.YEAR)) * 360
//                + endingDate.get(Calendar.MONTH) * 30 + endingDate.get(Calendar.DAY_OF_MONTH);
//        return ((int)(endingDay - startingDay));
//    }
    
//    /**
//     * @param dateFrom: The start date of the period. 
//     * @param dateTo: The end date of the period. 
//     * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
//     */
//    public static int get360DaysYearDiffInclusive(Date dateFrom, Date dateTo) {
//    	if(dateFrom == null || dateTo == null)
//    		return 0;
//        Calendar startingDate = getStartingDate(dateFrom);
//        Calendar endingDate = getEndingDateAccordingToStartingDate(dateTo, startingDate);
//        long startingDay = startingDate.get(Calendar.MONTH) * 30 + startingDate.get(Calendar.DAY_OF_MONTH);
//        long endingDay = (endingDate.get(Calendar.YEAR) - startingDate.get(Calendar.YEAR)) * 360
//                + endingDate.get(Calendar.MONTH) * 30 + endingDate.get(Calendar.DAY_OF_MONTH);
//        return ((int)(endingDay - startingDay))+1;
//    }
    
//    /**
//     * @param dateFrom: The start date of the period. 
//     * @param dateTo: The end date of the period. 
//     * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
//     */
//    public static int get360DaysYearDiffSpanoudakis(Date dateFrom, Date dateTo) {
//    	Calendar startCal = new GregorianCalendar();
//    	startCal.setTime(dateFrom);
//    	Calendar endCal = new GregorianCalendar();
//    	endCal.setTime(dateTo);
//    	
//    	int start = startCal.get(Calendar.DAY_OF_MONTH)+ startCal.get(Calendar.MONTH) * 30 + startCal.get(Calendar.YEAR) * 360;
//    	int end = endCal.get(Calendar.DAY_OF_MONTH)+ endCal.get(Calendar.MONTH) * 30 + endCal.get(Calendar.YEAR) * 360;
//
//    	return end - start;
//    }
    

	/**
	 * http://support.sas.com/documentation/cdl/en/lrdict/64316/HTML/default/viewer.htm#a000530603.htm
	 * 
	 * @param dateFrom: The start date of the period. 
	 * @param dateTo: The end date of the period. 
	 * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
	*/
    public static int get360DaysYearDiff(Date dateFrom, Date dateTo) {
	    if(dateFrom == null || dateTo == null)
	    	return 0;
	    
	    Calendar calFrom = Calendar.getInstance();
	    calFrom.setTime(dateFrom);
	    Calendar calTo = Calendar.getInstance();
	    calTo.setTime(dateTo);
	    
	    int y1 = calFrom.get(Calendar.YEAR);
	    int y2 = calTo.get(Calendar.YEAR);
	    int m1 = calFrom.get(Calendar.MONTH)+1;
	    int m2 = calTo.get(Calendar.MONTH)+1;
	    int d1 = calFrom.get(Calendar.DAY_OF_MONTH);
	    int d2 = calTo.get(Calendar.DAY_OF_MONTH);
	
	    /* according to 30E/360 (http://en.wikipedia.org/wiki/Day_count_convention#30E.2F360) :
    	 * If D1 is 31, then change D1 to 30.
    	 * If D2 is 31, then change D2 to 30. 
    	 */
	    d1 = (d1 == 31) ? 30 : d1;
	    d2 = (d2 == 31) ? 30 : d2;
	    
	    return ((y2-y1) * 360) + ((m2-m1) * 30) + (d2-d1);
    }


	/**
	 * http://support.sas.com/documentation/cdl/en/lrdict/64316/HTML/default/viewer.htm#a000530603.htm
	 * 
	 * @param dateFrom: The start date of the period. 
	 * @param dateTo: The end date of the period.
	 * @param inclusive: Whether or not to include the upper day limit (dateTo).
	 * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
	*/
    public static int datesDifferenceIn360DaysYear(Date dateFrom, Date dateTo, boolean inclusive) {
		if(!inclusive) {
			return get360DaysYearDiff(dateFrom, dateTo);
		} else {
			Calendar calTo = Calendar.getInstance();
			calTo.setTime(dateTo);
			calTo.add(Calendar.DAY_OF_MONTH, 1);
		
			return get360DaysYearDiff(dateFrom, calTo.getTime());
		}
	}
    
//    private static Calendar getCalendar(Date date) {
//        Calendar processedDate = new GregorianCalendar();
//        processedDate.setTime(date);
//        return processedDate;
//    }
//
//    private static Calendar getStartingDate(Date date) {
//        Calendar startingDate = getCalendar(date);
//        if (isLastDayOfMonth(startingDate)) {
//            startingDate.set(Calendar.DAY_OF_MONTH, 30);
//        }
//        return startingDate;
//    }
//
//    private static Calendar getEndingDateAccordingToStartingDate(Date date, Calendar startingDate) {
//        Calendar endingDate = getCalendar(date);
//        if (isLastDayOfMonth(endingDate)) {
//            if (startingDate.get(Calendar.DATE) < 30) {
//                endingDate = getFirstDayOfNextMonth(endingDate);
//            }
//        }
//        return endingDate;
//    }
//
//    private static boolean isLastDayOfMonth(Calendar date) {
//        Calendar clone = (Calendar) date.clone();
//        clone.add(java.util.Calendar.MONTH, 1);
//        clone.add(java.util.Calendar.DAY_OF_MONTH, -1);
//        int lastDayOfMonth = clone.get(Calendar.DAY_OF_MONTH);
//        return date.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth;
//    }
//
//    private static Calendar getFirstDayOfNextMonth(Calendar date) {
//        Calendar newDate = (Calendar) date.clone();
//        if (date.get(Calendar.MONTH) < Calendar.DECEMBER) {
//            newDate.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
//        } else {
//            newDate.set(Calendar.MONTH, 1);
//            newDate.set(Calendar.YEAR, date.get(Calendar.YEAR) + 1);
//        }
//        newDate.set(Calendar.DATE, 1);
//        return newDate;
//    }

	/**
	 * Μετατροπή Αριθμού Ημερών σε -> Έτη - Μήνες - Ημέρες
	 * 
	 * @param noOfDays
	 *            The number of days we wish to convers to Years - Months - Days
	 * 
	 * @return Returns a string of the form: 3 έτη 11 μήνες 24 ημέρες
	 */
	public static String getNoOfDaysInYear_Month_DayFormat(Integer noOfDays) {
		// **********************************
		// Μετατροπή Ημερών σε -> ετη - μηνες - ημέρες
		if (noOfDays != null) {
			int years, months, days;

			years = noOfDays / 360;
			months = (noOfDays - years * 360) / 30;
			days = noOfDays - (years * 360) - (months * 30);
			return String.format("%d έτη %d μην. %d ημ.", years, months, days);
		} else
			return "";
	}
	
	public static boolean isEmpty(Object object) {
		return !isNonEmpty(object);
	}

	public static boolean isNonEmpty(Object object) {
		return object != null && String.valueOf(object).trim().length() > 0;
	}

	
	/**
	 * @param startDate. The date you want to start counting.
	 * @param noOfDaysOffset. Positive value will take you to the future. Negative value will take you back in time.
	 * @return the Calendar date found before/after noOfDaysOffset days. 
	 */
	public static Date getDateInXDays(Date startDate, Integer noOfDaysOffset) {
		return DateUtils.addDays(startDate, noOfDaysOffset);
	}

	
	/**
	 * @param startDate. The date you want to start counting.
	 * @param noOfDaysOffset. Positive value will take you to the future. Negative value will take you back in time.
	 * @return the Calendar date found before/after noOfDaysOffset days in a 360 days year. 
	 */
	public static Date getDateInXDays360(Date startDate, int noOfDaysOffset) {
		int efforts = 50;
		java.util.Calendar estimatedDate = new java.util.GregorianCalendar();
		estimatedDate.setTime(startDate);
		
		if(noOfDaysOffset == 0)
			return startDate;
		else	
		if(noOfDaysOffset < 0) {
			int xDays = (int) (Math.ceil(noOfDaysOffset*1.015));
			estimatedDate.setTime(org.apache.commons.lang.time.DateUtils.addDays(estimatedDate.getTime(), xDays));

			for(int effort = 1 ; effort < efforts ; effort++) {
				int difference  = gr.sch.ira.minoas.core.CoreUtils.datesDifferenceIn360DaysYear(estimatedDate.getTime(), startDate, false);

				if(Math.abs(difference) <= Math.abs(noOfDaysOffset)) {
					if(Math.abs(difference) < Math.abs(noOfDaysOffset))
						estimatedDate.add(java.util.Calendar.DAY_OF_YEAR, -1);
					return estimatedDate.getTime();	
				}
				estimatedDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
			}
		} else {
			int xDays = (int) (Math.ceil(noOfDaysOffset*1.0145));
			estimatedDate.setTime(org.apache.commons.lang.time.DateUtils.addDays(estimatedDate.getTime(), xDays));

			for(int effort = 1 ; effort < efforts ; effort++) {
				int difference  = gr.sch.ira.minoas.core.CoreUtils.datesDifferenceIn360DaysYear(estimatedDate.getTime(), startDate, false);

				if(Math.abs(difference) >= Math.abs(noOfDaysOffset)) {
					if(Math.abs(difference) > Math.abs(noOfDaysOffset))
						estimatedDate.add(java.util.Calendar.DAY_OF_YEAR, -1);
					return estimatedDate.getTime();	
				}
				estimatedDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
			}
		}

		return null;
	}


	/**
	 * Η ρουτίνα επιστρέφει τον Βαθμό (Α, Β, Γ, Δ, Ε, ΣΤ) και το ΜΚ (0, 1, 2, 3,
	 * 4, 5, 6) κάποιου υπαλλήλου ανάλογα την Katigoria του (ΥΕ, ΔΕ, ΤΕ, ΠΕ) αν
	 * έχει η όχι Μεταπτυχιακό, Διδακτορικό ή Α.Σ.Δ.Δ., και τον ExcessTimeInRank
	 * (Πλεονάζοντα Χρόνο στον Βαθμό) σε αριθμό ημερών.
	 * 
	 * @param ExcessTimeInRank
	 *            The employee's excess time (in number of days) in the current Rank
	 * @return Returns the employee's Rank info (rank & salary grade) after the
	 *         classification in grade.
	 */
	public static RankInfo RecalculateRankInfo(RankInfo currentRankInfo, Employee employee, WorkExperienceCalculation workExperienceCalculation) {
		java.util.Calendar today = new java.util.GregorianCalendar();
//		today.set(2011, 11, 30);
		
		Integer unPaidDays = workExperienceCalculation.calculateEmployeeUnPaidDays(employee, currentRankInfo.getLastRankDate(), today.getTime());
//		if(unPaidDays > 0)
//			System.out.println("FOUND!!!");
		// Πάρε τον πλεονάζοντα χρόνο στον ΒΑΘΜΟ
		Integer ExcessTimeInRank = (currentRankInfo.getSurplusTimeInRankUntilToday()+1) - unPaidDays;
		RankInfo newRankInfo = new RankInfo(currentRankInfo); 
		newRankInfo.setComments(null);
		switch (newRankInfo.getEducationalLevel()) {
			case UNIVERSITY_EDUCATION_LEVEL:
				switch(newRankInfo.getRank()) {
					case RANK_ST:
						if(ExcessTimeInRank >=0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0);
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else if(ExcessTimeInRank > 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						}
					break;
					case RANK_E:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						}	
						else if(ExcessTimeInRank > 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2160-ExcessTimeInRank), 0);
						}
						break;
					case RANK_D:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						}	
						else if(ExcessTimeInRank > 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2880-ExcessTimeInRank), 0);
						}
						break;
					case RANK_C:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 3600-ExcessTimeInRank), 0);
						}
						break;
					case RANK_B:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 1080) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1081 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 1081-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 3240) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3241 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 3241-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5400) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5401 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 5401-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 6481 && ExcessTimeInRank <= 7560) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 6481-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 7561 && ExcessTimeInRank <= 8640) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 7561-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 8641 && ExcessTimeInRank <= 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 8641-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 9720-ExcessTimeInRank), 0);
						}
						break;
					case RANK_A:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 1080) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1081 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 1081-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 3240) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3241 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 3241-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5400) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5401 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 5401-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 6481 && ExcessTimeInRank <= 7560) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 6481-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 7561 && ExcessTimeInRank <= 8640) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 7561-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 8641 && ExcessTimeInRank <= 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 8641-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 9720-ExcessTimeInRank), 0);
						}
						break;
					default:
						break;
				}
				break; // End UNIVERSITY_EDUCATION_LEVEL case
				
				
			case TECHNOLOGIGAL_EDUCATION_LEVEL:
				switch(newRankInfo.getRank()) {
					case RANK_ST:
						if(ExcessTimeInRank >=0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else if(ExcessTimeInRank > 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						}
					break;
					case RANK_E:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						}	
						else if(ExcessTimeInRank > 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2160-ExcessTimeInRank), 0);
						}
						break;
					case RANK_D:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						}	
						else if(ExcessTimeInRank > 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2880-ExcessTimeInRank), 0);
						}
						break;
					case RANK_C:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 3600-ExcessTimeInRank), 0);
						}
						break;
					case RANK_B:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 1080) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1081 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 1081-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 3240) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3241 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 3241-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5400) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5401 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 5401-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 6481 && ExcessTimeInRank <= 7560) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 6481-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 7561 && ExcessTimeInRank <= 8640) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 7561-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 8641 && ExcessTimeInRank <= 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 8641-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 9720-ExcessTimeInRank), 0);
						}
						break;
					case RANK_A:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 1080) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1081 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 1081-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 3240) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3241 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 3241-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5400) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5401 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 5401-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 6481 && ExcessTimeInRank <= 7560) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 6481-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 7561 && ExcessTimeInRank <= 8640) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 7561-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 8641 && ExcessTimeInRank <= 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 8641-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 9720-ExcessTimeInRank), 0);
						}
						break;
					default:
						break;
				}
				break; // End TECHNOLOGIGAL_EDUCATION_LEVEL case

			case SECONDARY_EDUCATION_LEVEL:
				switch(newRankInfo.getRank()) {
					case RANK_ST:
						if(ExcessTimeInRank >=0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else if(ExcessTimeInRank > 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						}
					break;
					case RANK_E:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2880-ExcessTimeInRank), 0);
						}
						break;
					case RANK_D:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 3600-ExcessTimeInRank), 0);
						}
						break;
					case RANK_C:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3601 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 3601-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 4320-ExcessTimeInRank), 0);
						}
						break;
					case RANK_B:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 1080) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1081 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 1081-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 3240) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3241 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 3241-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5400) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5401 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 5401-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 6481 && ExcessTimeInRank <= 7560) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 6481-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 7561 && ExcessTimeInRank <= 8640) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 7561-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 8641 && ExcessTimeInRank <= 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 8641-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 9720) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 9720-ExcessTimeInRank), 0);
						}
						break;
					default:
						break;
				}
				break; // End SECONDARY_EDUCATION_LEVEL case
			
			case COMPULSORY_EDUCATION_LEVEL:
				switch(newRankInfo.getRank()) {
					case RANK_ST:
						if(ExcessTimeInRank >=0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else if(ExcessTimeInRank > 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						}
					break;
					case RANK_E:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3601 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 3601-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 4320-ExcessTimeInRank), 0);
						}
						break;
					case RANK_D:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3601 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 3601-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5040) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						}
						else if(ExcessTimeInRank > 5040) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 5040-ExcessTimeInRank), 0);
						}
						break;
					case RANK_C:
						if(ExcessTimeInRank >= 0 && ExcessTimeInRank <= 720) {
							newRankInfo.setSalaryGrade(0, CoreUtils.getDateInXDays360(new Date(), 0-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 721 && ExcessTimeInRank <= 1440) {
							newRankInfo.setSalaryGrade(1, CoreUtils.getDateInXDays360(new Date(), 721-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 1441 && ExcessTimeInRank <= 2160) {
							newRankInfo.setSalaryGrade(2, CoreUtils.getDateInXDays360(new Date(), 1441-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2161 && ExcessTimeInRank <= 2880) {
							newRankInfo.setSalaryGrade(3, CoreUtils.getDateInXDays360(new Date(), 2161-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 2881 && ExcessTimeInRank <= 3600) {
							newRankInfo.setSalaryGrade(4, CoreUtils.getDateInXDays360(new Date(), 2881-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 3601 && ExcessTimeInRank <= 4320) {
							newRankInfo.setSalaryGrade(5, CoreUtils.getDateInXDays360(new Date(), 3601-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 4321 && ExcessTimeInRank <= 5040) {
							newRankInfo.setSalaryGrade(6, CoreUtils.getDateInXDays360(new Date(), 4321-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5041 && ExcessTimeInRank <= 5760) {
							newRankInfo.setSalaryGrade(7, CoreUtils.getDateInXDays360(new Date(), 5041-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank >= 5761 && ExcessTimeInRank <= 6480) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 5761-ExcessTimeInRank), 0);
						} else 
						if(ExcessTimeInRank > 6480) {
							newRankInfo.setSalaryGrade(8, CoreUtils.getDateInXDays360(new Date(), 6480-ExcessTimeInRank), 0);
						}
						break;
					default:
						break;
				}
				break; // End COMPULSORY_EDUCATION_LEVEL case
				
			default:
				break;
		}
		
		if(currentRankInfo.getRank().equals(newRankInfo.getRank()) && currentRankInfo.getSalaryGrade() == newRankInfo.getSalaryGrade())
			return currentRankInfo;
		else
			return newRankInfo;
	}
	
	/**
	 * Return a count of days (calendar and calendar based on 360) of leaves bounded by a date period. More
	 * info @ https://github.com/dideher/minoas/issues/195
	 * 
	 * @param dateFrom date from
	 * @param dateTo date to 
	 * @param leaves collection of leaves
	 * @return a Map containing two integer values with keys 'calendarDays' and 'calendar360Days'
	 */
	public static Map<String, Integer> countLeaveDaysWithinPeriod(Date dateFrom, Date dateTo,
			final Collection<EmployeeLeave> leaves) {
		int calendarDays = 0;
		int calendar360Days = 0;
		for (EmployeeLeave leave : leaves) {
			
			EmployeeLeave deattachedLeave = new EmployeeLeave(leave);

			if (deattachedLeave.getDueTo().before(dateFrom)
					|| deattachedLeave.getEstablished().after(dateTo)) {
				// leave ends before dateFrom or leave starts after dateTo
				continue;
			}

			if (deattachedLeave.getEstablished().before(dateFrom)
					&& deattachedLeave.getDueTo().after(dateFrom)) {
				// leave starts before dateFrom but ends within our period of
				// interest
				deattachedLeave.setEstablished(dateFrom);
			}

			if (deattachedLeave.getEstablished().before(dateTo)
					&& deattachedLeave.getDueTo().after(dateTo)) {
				// leave starts before dateTo but ends out of our period
				deattachedLeave.setDueTo(dateTo);
			}
			calendarDays += CoreUtils.getDatesDifference(deattachedLeave.getEstablished(), deattachedLeave.getDueTo());
			calendar360Days += CoreUtils.datesDifferenceIn360DaysYear(deattachedLeave.getEstablished(), deattachedLeave.getDueTo(), true);
		}
		
		Map<String, Integer> returnValue = new HashMap<String, Integer>();
		returnValue.put("calendarDays", calendarDays);
		returnValue.put("calendar360Days", calendar360Days);
		return returnValue;
	}



}
