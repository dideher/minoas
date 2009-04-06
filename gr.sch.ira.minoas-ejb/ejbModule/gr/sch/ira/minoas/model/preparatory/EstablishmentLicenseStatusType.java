package gr.sch.ira.minoas.model.preparatory;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum EstablishmentLicenseStatusType {
	
	/**
	 * Establishment license has been canceled
	 */
	CANCELED("EST_LICENSE_STATUS_CANCELED_KEY"),
	/**
	 * Establishment license has expired.
	 */
	EXPIRED("EST_LICENSE_STATUS_EXPIRED_KEY"),
	/**
	 * Establishment license request has been filled 
	 */
	PENDING("EST_LICENSE_STATUS_PENDING_KEY"),
	/**
	 * Establishment license renewal has been filled.
	 */
	PENDING_RENEWAL("EST_LICENSE_STATUS_PENDING_RENEWAL_KEY"),
	/**
	 * Establishment license request has been rejected.
	 */
	REJECTED("EST_LICENSE_STATUS_REJECTED_KEY"),
	/**
	 * Established license is valid
	 */
	VALID("EST_LICENSE_STATUS_VALID_KEY");
	
	private String key;

	/**
	 * @param key
	 */
	private EstablishmentLicenseStatusType(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}