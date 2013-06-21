/**
 * 
 */
package gr.sch.ira.minoas.core;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    
    /**
     * @param dateFrom: The start date of the period. 
     * @param dateTo: The end date of the period. 
     * @return the days difference between two dates in a 360 days year (12 * 30 days). Returns NEGATIVE value if start date is AFTER end date.
     */
    public static int get360DaysYearDiff(Date dateFrom, Date dateTo) {
    	if(dateFrom == null || dateTo == null)
    		return 0;
        Calendar startingDate = getStartingDate(dateFrom);
        Calendar endingDate = getEndingDateAccordingToStartingDate(dateTo, startingDate);
        long startingDay = startingDate.get(Calendar.MONTH) * 30 + startingDate.get(Calendar.DAY_OF_MONTH);
        long endingDay = (endingDate.get(Calendar.YEAR) - startingDate.get(Calendar.YEAR)) * 360
                + endingDate.get(Calendar.MONTH) * 30 + endingDate.get(Calendar.DAY_OF_MONTH);
        return ((int)(endingDay - startingDay));
    }
    
    
    private static Calendar getCalendar(Date date) {
        Calendar processedDate = new GregorianCalendar();
        processedDate.setTime(date);
        return processedDate;
    }

    private static Calendar getStartingDate(Date date) {
        Calendar startingDate = getCalendar(date);
        if (isLastDayOfMonth(startingDate)) {
            startingDate.set(Calendar.DAY_OF_MONTH, 30);
        }
        return startingDate;
    }

    private static Calendar getEndingDateAccordingToStartingDate(Date date, Calendar startingDate) {
        Calendar endingDate = getCalendar(date);
        if (isLastDayOfMonth(endingDate)) {
            if (startingDate.get(Calendar.DATE) < 30) {
                endingDate = getFirstDayOfNextMonth(endingDate);
            }
        }
        return endingDate;
    }

    private static boolean isLastDayOfMonth(Calendar date) {
        Calendar clone = (Calendar) date.clone();
        clone.add(java.util.Calendar.MONTH, 1);
        clone.add(java.util.Calendar.DAY_OF_MONTH, -1);
        int lastDayOfMonth = clone.get(Calendar.DAY_OF_MONTH);
        return date.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth;
    }

    private static Calendar getFirstDayOfNextMonth(Calendar date) {
        Calendar newDate = (Calendar) date.clone();
        if (date.get(Calendar.MONTH) < Calendar.DECEMBER) {
            newDate.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
        } else {
            newDate.set(Calendar.MONTH, 1);
            newDate.set(Calendar.YEAR, date.get(Calendar.YEAR) + 1);
        }
        newDate.set(Calendar.DATE, 1);
        return newDate;
    }

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
				int difference  = gr.sch.ira.minoas.core.CoreUtils.get360DaysYearDiff(estimatedDate.getTime(), startDate);

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
				int difference  = gr.sch.ira.minoas.core.CoreUtils.get360DaysYearDiff(estimatedDate.getTime(), startDate);

				if(Math.abs(difference) >= Math.abs(noOfDaysOffset)) {
					return estimatedDate.getTime();	
				}
				estimatedDate.add(java.util.Calendar.DAY_OF_YEAR, 1);
			}
		}

		return null;
	}
}
