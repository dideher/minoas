/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.solder.logging.Category;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
public abstract class BaseSeamComponent implements Serializable {
	
	public static final String ACTION_OUTCOME_SUCCESS = "success";
	
	public static final String ACTION_OUTCOME_FAILURE = "failure";

	public static final Locale greekLocale = new Locale("el", "GR");

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	 @Inject
	 
	protected Messages facesMessages;

	@Inject
    @Category("minoas-web")
	private Logger logger;

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.jboss.seam.log.Log#debug(java.lang.Object, java.lang.Object[])
	 */
	public void debug(Object arg0, Object... arg1) {
		logger.debugf(arg0.toString(), arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.jboss.seam.log.Log#debug(java.lang.Object, java.lang.Throwable,
	 *      java.lang.Object[])
	 */
	public void debug(Object arg0, Throwable arg1, Object... arg2) {
		logger.debugf(arg1.toString(), arg0, arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.jboss.seam.log.Log#error(java.lang.Object, java.lang.Object[])
	 */
	public void error(Object arg0, Object... arg1) {
		logger.errorf(arg0.toString(), arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.jboss.seam.log.Log#error(java.lang.Object, java.lang.Throwable,
	 *      java.lang.Object[])
	 */
	public void error(Object arg0, Throwable arg1, Object... arg2) {
		logger.errorf(arg1, arg0.toString(), arg2);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.jboss.seam.log.Log#fatal(java.lang.Object, java.lang.Object[])
	 */
	public void fatal(Object arg0, Object... arg1) {
		logger.fatalf(arg0.toString(), arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.jboss.seam.log.Log#fatal(java.lang.Object, java.lang.Throwable,
	 *      java.lang.Object[])
	 */
	public void fatal(Object arg0, Throwable arg1, Object... arg2) {
		logger.fatalf(arg1, arg0.toString(), arg2);
	}

	/**
	 * @return the facesMessages
	 */
	public Messages getFacesMessages() {
		return facesMessages;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.jboss.seam.log.Log#info(java.lang.Object, java.lang.Object[])
	 */
	public void info(Object arg0, Object... arg1) {
		logger.infof(arg0.toString(), arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.jboss.seam.log.Log#info(java.lang.Object, java.lang.Throwable,
	 *      java.lang.Object[])
	 */
	public void info(Object arg0, Throwable arg1, Object... arg2) {
		logger.infof(arg1, arg0.toString(), arg2);
	}

	protected boolean isEmpty(Object object) {
		return !isEmpty(object);
	}

	protected boolean isNonEmpty(Object object) {
		return object != null && String.valueOf(object).trim().length() > 0;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @see org.jboss.seam.log.Log#warn(java.lang.Object, java.lang.Object[])
	 */
	public void warn(Object arg0, Object... arg1) {
		logger.warnf(arg0.toString(), arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see org.jboss.seam.log.Log#warn(java.lang.Object, java.lang.Throwable,
	 *      java.lang.Object[])
	 */
	public void warn(Object arg0, Throwable arg1, Object... arg2) {
		logger.warnf(arg1, arg0.toString(), arg2);
	}

}
