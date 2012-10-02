/**
 * 
 */
package gr.sch.ira.minoas.core;

import java.util.Calendar;
import java.util.Date;
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
		return searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
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
            return (int) (timeInMillisDiff / DAY_TIME_IN_MILLIS);
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
            return countDays;
        } else
            return 0;
    }

}
