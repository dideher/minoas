/**
 * 
 */
package gr.sch.ira.minoas.core;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;



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

//    public static String getLocalizedMessage(String message_key) {
//    	return getResourceBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME).getString(message_key);
//    }

//    public static ResourceBundle getResourceBundle(String resource_budle_name) {
//        return SeamResourceBundle.getBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME, getFacesContext()
//    			.getViewRoot().getLocale());
//    }
	
	

}
