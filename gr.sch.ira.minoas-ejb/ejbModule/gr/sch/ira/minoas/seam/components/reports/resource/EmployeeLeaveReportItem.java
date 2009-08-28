package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Leave;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeLeaveReportItem extends EmployeeReportItem {

	/**
	 * 
	 */
	public EmployeeLeaveReportItem() {
		super();
	}

	public EmployeeLeaveReportItem(Leave leave) {
		super(leave.getEmployee());
	}
}
