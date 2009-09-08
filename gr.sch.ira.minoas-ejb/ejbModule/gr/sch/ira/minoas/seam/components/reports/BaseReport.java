package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.core.SeamResourceBundle;

public abstract class BaseReport  extends BaseDatabaseAwareSeamComponent {

	private static final String SEAM_MESSAGES_RESOURCE_BUNDLE_NAME = "messages";

	
	@In(required = true, create = true)
	private CoreSearching coreSearching;

	/**
	 * @return the coreSearching
	 */
	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	protected String getLocalizedMessage(String message_key) {
		return getResourceBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME).getString(
				message_key);
	}
	
	protected ResourceBundle getResourceBundle(String resource_budle_name) {
		return SeamResourceBundle.getBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME,
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}

	/**
	 * @param coreSearching the coreSearching to set
	 */
	public void setCoreSearching(CoreSearching coreSearching) {
		this.coreSearching = coreSearching;
	}

	

}
