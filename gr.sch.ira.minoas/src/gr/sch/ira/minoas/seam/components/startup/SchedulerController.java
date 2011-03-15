package gr.sch.ira.minoas.seam.components.startup;

import java.util.Date;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.async.SecondmentCleanup;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("schedulerController")
@AutoCreate
public class SchedulerController extends BaseDatabaseAwareSeamComponent {

    @In(create=true, value="secondmentCleanupProcessor")
    private SecondmentCleanup secondmentCleanupProcessor;
    
    private QuartzTriggerHandle secondmentCleanerProcessor;
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

   
    public void scheduleJobs() {
        info("creating schedule object");
        if(secondmentCleanupProcessor!=null)
            secondmentCleanerProcessor = secondmentCleanupProcessor.scheduleSecondmentCleanup(new Date(), "* */2 * * * ?");
        info("scheduled #0", secondmentCleanerProcessor);
    }
    
//    @Destroy
//    public void destroy() {
//       info("destroying schedule object");
//       try {
//           if(secondmentCleanupProcessor!=null)
//               secondmentCleanerProcessor.pause();
//       } catch(Exception ex) {
//           error("error while stoping handler", ex);
//       }
//    }
    
    
    
}
