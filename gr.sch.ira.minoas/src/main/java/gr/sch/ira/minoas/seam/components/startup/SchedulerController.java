package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.async.DisposalCleanupProcessor;
import gr.sch.ira.minoas.seam.components.async.LeaveActivactionProcessor;
import gr.sch.ira.minoas.seam.components.async.LeaveCleanupProcessor;
import gr.sch.ira.minoas.seam.components.async.RegularEmployeeServiceUpdaterProcessor;
import gr.sch.ira.minoas.seam.components.async.SecondmentActivactionProcessor;
import gr.sch.ira.minoas.seam.components.async.SecondmentCleanupProcessor;
import gr.sch.ira.minoas.seam.components.async.ServiceAllocationCleanupProcessor;
import gr.sch.ira.minoas.seam.components.reports.BasicUsageReport;

import java.util.Date;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("schedulerController")
@AutoCreate
public class SchedulerController extends BaseDatabaseAwareSeamComponent {
    
    private static final long SECONDMENT_CLEANUP_INTERVAL = 1200000;
    
    private static final long LEAVE_CLEANUP_INTERVAL = 1200000;
    
    private static final long SERVICE_ALLOCATION_CLEANUP_INTERVAL = 1200000;
    
    private static final long DISPOSAL_CLEANUP_INTERVAL = 1200000;
    
    private static final long BASIC_USAGE_REPORT_INTERVAL = 2400000;
    
    
    private static final long REGULAR_EMPLOYEE_SERVICE_SYNC_INTERVAL = 60000;
    
    private static final long ACTIVATION_TASK_INTERVAL = 1200000;
    
    private static final long RANK_INFO_SYNC_INTERVAL = 86400000;
    
    
    
    
    @In(create=true, value="secondmentCleanupProcessor")
    private SecondmentCleanupProcessor secondmentCleanupProcessor;
    
    @In(create=true, value="leaveCleanupProcessor")
    private LeaveCleanupProcessor leaveCleanupProcessor;
    
    @In(create=true, value="disposalCleanupProcessor")
    private DisposalCleanupProcessor disposalCleanupProcessor;
    
    
    @In(create=true, value="leaveActivationProcessor")
    private LeaveActivactionProcessor leaveActivationProcessor;
    
    @In(create=true, value="secondmentActivationProcessor")
    private SecondmentActivactionProcessor secondmentActivationProcessor;
    
//    @In(create=true, value="serviceAllocationActivationProcessor")
//    private ServiceAllocationActivactionProcessor serviceAllocationActivationProcessor;
    
//    @In(create=true, value="disposalActivationProcessor")
//    private DisposalActivactionProcessor disposalActivationProcessor;
    
    
    @In(create=true, value="serviceAllocationCleanupProcessor")
    private ServiceAllocationCleanupProcessor serviceAllocationCleanupProcessor;
    
    
    @In(create=true, value="regularEmployeeServiceUpdaterProcessor")
    private RegularEmployeeServiceUpdaterProcessor regularEmployeeServiceUpdaterProcessor;
    
    @In(create=true, value="basicUsageReport")
    private BasicUsageReport basicUsageReportProcessor;
    
    private QuartzTriggerHandle secondmentCleanupProcessorHandler;
    private QuartzTriggerHandle leaveCleanupProcessorHandler;
    private QuartzTriggerHandle disposalCleanupProcessorHandler;
    private QuartzTriggerHandle serviceAllocationCleanupProcessorHandler;
    private QuartzTriggerHandle leaveActivationProcessorHandler;
    private QuartzTriggerHandle secondmentActivationProcessorHandler;
    //private QuartzTriggerHandle serviceAllocationActivationProcessorHandler;
    //private QuartzTriggerHandle disposalActivationProcessorHandler;
    private QuartzTriggerHandle regularEmployeeServiceUpdaterProcessorHandler;
    private QuartzTriggerHandle basicUsageReportProcessorHandler;
    
    
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

   
    public void scheduleJobs() {
        try {
	        info("creating schedule object");
	        if(secondmentCleanupProcessor!=null) {
	            secondmentCleanupProcessorHandler = secondmentCleanupProcessor.scheduleSecondmentCleanup(new Date(), SECONDMENT_CLEANUP_INTERVAL, null);
	            info("scheduled #0", secondmentCleanupProcessorHandler.getTrigger().getFullName());
	        }
	        if(leaveCleanupProcessor!=null) {
	            leaveCleanupProcessorHandler = leaveCleanupProcessor.scheduleSecondmentCleanup(new Date(), LEAVE_CLEANUP_INTERVAL, null);
	            info("scheduled #0", leaveCleanupProcessorHandler.getTrigger().getFullName());
	        }
	        
	        if(disposalCleanupProcessor!=null) {
	            disposalCleanupProcessorHandler = disposalCleanupProcessor.scheduleSecondmentCleanup(new Date(), DISPOSAL_CLEANUP_INTERVAL, null);
	            info("scheduled #0", disposalCleanupProcessorHandler.getTrigger().getFullName());
	        }
	        
	        if(serviceAllocationCleanupProcessor!=null) {
	            serviceAllocationCleanupProcessorHandler = serviceAllocationCleanupProcessor.scheduleSecondmentCleanup(new Date(), SERVICE_ALLOCATION_CLEANUP_INTERVAL, null);
	            info("scheduled #0", serviceAllocationCleanupProcessorHandler.getTrigger().getFullName());
	        }
	        
	        if(leaveActivationProcessor!=null) {
	            leaveActivationProcessorHandler = leaveActivationProcessor.scheduleSecondmentCleanup(new Date(), ACTIVATION_TASK_INTERVAL, null);
	            info("scheduled #0", leaveActivationProcessorHandler.getTrigger().getFullName());
	        }
	        
	        if(secondmentActivationProcessor!=null) {
	            secondmentActivationProcessorHandler = secondmentActivationProcessor.scheduleSecondmentActivation(new Date(), ACTIVATION_TASK_INTERVAL, null);
	            info("scheduled #0", secondmentActivationProcessorHandler.getTrigger().getFullName());
	        }
	        
//	        if(serviceAllocationActivationProcessor!=null) {
//	            serviceAllocationActivationProcessorHandler = serviceAllocationActivationProcessor.scheduleServiceAllocationActivation(new Date(), ACTIVATION_TASK_INTERVAL, null);
//	            info("scheduled #0", serviceAllocationActivationProcessorHandler.getTrigger().getFullName());
//	        }
//	        
//	        if(disposalActivationProcessor!=null) {
//	            disposalActivationProcessorHandler = disposalActivationProcessor.scheduleDisposalActivation(new Date(), ACTIVATION_TASK_INTERVAL, null);
//	            info("scheduled #0", disposalActivationProcessorHandler.getTrigger().getFullName());
//	        }
	        
	        if(basicUsageReportProcessor!=null) {
	            basicUsageReportProcessorHandler = basicUsageReportProcessor.scheduleReportGeneration(new Date(), BASIC_USAGE_REPORT_INTERVAL, null);
	            info("scheduled #0", basicUsageReportProcessorHandler.getTrigger().getFullName());
	        }
	        
	        if(regularEmployeeServiceUpdaterProcessor!=null) {
	            regularEmployeeServiceUpdaterProcessorHandler = regularEmployeeServiceUpdaterProcessor.schedule(new Date(), REGULAR_EMPLOYEE_SERVICE_SYNC_INTERVAL, null);
	            info("scheduled #0", regularEmployeeServiceUpdaterProcessorHandler.getTrigger().getFullName());
	        }
        
        } catch(Exception ex) {
            error("failed to schedule jobs due to an exception #0", ex, ex.getMessage());
        }
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
