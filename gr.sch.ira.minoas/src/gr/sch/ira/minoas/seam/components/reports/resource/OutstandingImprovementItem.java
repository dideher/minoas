package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class OutstandingImprovementItem extends EmployeeReportItem {

	/**
	 * 
	 */
	public OutstandingImprovementItem() {
		super();
	}

	/**
	 * @param employee
	 */
	public OutstandingImprovementItem(OutstandingImprovement improvement) {
		super(improvement.getEmployee());
	}

}
