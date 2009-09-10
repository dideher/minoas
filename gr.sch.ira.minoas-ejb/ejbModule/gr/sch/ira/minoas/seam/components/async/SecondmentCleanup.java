package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.seam.components.BaseSeamComponent;

import java.util.Date;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalDuration;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentCleanupHandler")
public class SecondmentCleanup extends BaseSeamComponent {

	/**
	 * 
	 */
	public SecondmentCleanup() {
	}

	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public void processRecurringSecondmentCleanup(@Expiration Date date, @IntervalDuration Long interval) {
		info("We're runninggg!!!!");
		
		
	}
}
