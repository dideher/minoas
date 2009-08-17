package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;

import org.jboss.seam.annotations.In;
import org.jboss.seam.faces.FacesMessages;

public abstract class BaseReport  extends BaseDatabaseAwareSeamComponent {

	@In(required = true, create = true)
	private CoreSearching coreSearching;

	

	/**
	 * @return the coreSearching
	 */
	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	/**
	 * @param coreSearching the coreSearching to set
	 */
	public void setCoreSearching(CoreSearching coreSearching) {
		this.coreSearching = coreSearching;
	}

	

}
