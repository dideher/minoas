package gr.sch.ira.minoas.seam.components.startup;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
//@Name("schedulerController")
//@AutoCreate
public class SchedulerController extends BaseDatabaseAwareSeamComponent {
    
//    private static final long SECONDMENT_CLEANUP_INTERVAL = 1200000;
//    
//    private static final long LEAVE_CLEANUP_INTERVAL = 1200000;
//    
//    private static final long SERVICE_ALLOCATION_CLEANUP_INTERVAL = 1200000;
//    
//    private static final long DISPOSAL_CLEANUP_INTERVAL = 1200000;
//    
//    private static final long LEAVE_ACTIVATION_INTERVAL = 1200000;
//    
//    private static final long BASIC_USAGE_REPORT_INTERVAL = 2400000;
//    
//    @In(create=true, value="secondmentCleanupProcessor")
//    private SecondmentCleanupProcessor secondmentCleanupProcessor;
//    
//    @In(create=true, value="leaveCleanupProcessor")
//    private LeaveCleanupProcessor leaveCleanupProcessor;
//    
//    @In(create=true, value="disposalCleanupProcessor")
//    private DisposalCleanupProcessor disposalCleanupProcessor;
//    
//    
//    @In(create=true, value="leaveActivationProcessor")
//    private LeaveActivactionProcessor leaveActivationProcessor;
//    
//    @In(create=true, value="serviceAllocationCleanupProcessor")
//    private ServiceAllocationCleanupProcessor serviceAllocationCleanupProcessor;
//    
//    
//    @In(create=true, value="basicUsageReport")
//    private BasicUsageReport basicUsageReportProcessor;
//    
//    private QuartzTriggerHandle secondmentCleanupProcessorHandler;
//    private QuartzTriggerHandle leaveCleanupProcessorHandler;
//    private QuartzTriggerHandle disposalCleanupProcessorHandler;
//    private QuartzTriggerHandle serviceAllocationCleanupProcessorHandler;
//    private QuartzTriggerHandle leaveActivationProcessorHandler;
//    private QuartzTriggerHandle basicUsageReportProcessorHandler;
//	/**
//     * Comment for <code>serialVersionUID</code>
//     */
//    private static final long serialVersionUID = 1L;
//
//   
//    public void scheduleJobs() {
//        try {
//        info("creating schedule object");
//        if(secondmentCleanupProcessor!=null) {
//            secondmentCleanupProcessorHandler = secondmentCleanupProcessor.scheduleSecondmentCleanup(new Date(), SECONDMENT_CLEANUP_INTERVAL, null);
//            info("scheduled #0", secondmentCleanupProcessorHandler.getTrigger().getFullName());
//        }
//        if(leaveCleanupProcessor!=null) {
//            leaveCleanupProcessorHandler = leaveCleanupProcessor.scheduleSecondmentCleanup(new Date(), LEAVE_CLEANUP_INTERVAL, null);
//            info("scheduled #0", leaveCleanupProcessorHandler.getTrigger().getFullName());
//        }
//        
//        if(disposalCleanupProcessor!=null) {
//            disposalCleanupProcessorHandler = disposalCleanupProcessor.scheduleSecondmentCleanup(new Date(), DISPOSAL_CLEANUP_INTERVAL, null);
//            info("scheduled #0", disposalCleanupProcessorHandler.getTrigger().getFullName());
//        }
//        if(serviceAllocationCleanupProcessor!=null) {
//            serviceAllocationCleanupProcessorHandler = serviceAllocationCleanupProcessor.scheduleSecondmentCleanup(new Date(), SERVICE_ALLOCATION_CLEANUP_INTERVAL, null);
//            info("scheduled #0", serviceAllocationCleanupProcessorHandler.getTrigger().getFullName());
//        }
//        
//        if(leaveActivationProcessor!=null) {
//            leaveActivationProcessorHandler = leaveActivationProcessor.scheduleSecondmentCleanup(new Date(), LEAVE_ACTIVATION_INTERVAL, null);
//            info("scheduled #0", leaveActivationProcessorHandler.getTrigger().getFullName());
//        }
//        
//        if(basicUsageReportProcessor!=null) {
//            basicUsageReportProcessorHandler = basicUsageReportProcessor.scheduleReportGeneration(new Date(), BASIC_USAGE_REPORT_INTERVAL, null);
//            info("scheduled #0", basicUsageReportProcessorHandler.getTrigger().getFullName());
//        }
//        
//        } catch(Exception ex) {
//            error("failed to schedule jobs due to an exception #0", ex, ex.getMessage());
//        }
//    }
//
//
//  
//    
////    @Destroy
////    public void destroy() {
////       info("destroying schedule object");
////       try {
////           if(secondmentCleanupProcessor!=null)
////               secondmentCleanerProcessor.pause();
////       } catch(Exception ex) {
////           error("error while stoping handler", ex);
////       }
////    }
//    
    
    
}
