package gr.sch.ira.minoas.model.preparatory;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public enum EstablishmentLicenseStatusType {
	/**
	 * Establishment license has been canceled
	 */
	CANCELED,
	/**
	 * Establishment license has expired.
	 */
	EXPIRED,
	/**
	 * Establishment license request has been filled 
	 */
	PENDING,
	/**
	 * Establishment license renewal has been filled.
	 */
	PENDING_RENEWAL,
	/**
	 * Establishment license request has been rejected.
	 */
	REJECTED,
	/**
	 * Established license is valid
	 */
	VALID
}