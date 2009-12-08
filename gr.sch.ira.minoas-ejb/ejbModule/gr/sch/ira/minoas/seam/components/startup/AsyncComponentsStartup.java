package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.seam.components.BaseSeamComponent;
import gr.sch.ira.minoas.seam.components.async.SecondmentCleanup;

import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Startup
@Scope(ScopeType.APPLICATION)
@Name("AsyncStartup")
public class AsyncComponentsStartup extends BaseSeamComponent {

	private long ONE_DAY = 24 * 60 * 60 * 1000;

	@In(create = true, value = "secondmentCleanupHandler")
	private SecondmentCleanup secondmentCleanupHandler;

	@Create
	@Transactional(TransactionPropagationType.REQUIRED)
	public void init() {
		info("preparing async components");
		Calendar cal = Calendar.getInstance();
		cal = DateUtils.truncate(cal, Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		//secondmentCleanupHandler.processRecurringSecondmentCleanup(cal.getTime(), ONE_DAY);

		Calendar temp = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		//secondmentCleanupHandler.processRecurringSecondmentCleanup(temp.getTime(), 30000L);
	}
}
