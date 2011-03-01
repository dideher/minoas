package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class EmployeeTeachingHoursReportItem extends EmployeeReportItem {

    /**
     * 
     */
    public EmployeeTeachingHoursReportItem() {
        super();
    }

    /**
     * @param employee
     */
    public EmployeeTeachingHoursReportItem(Employee employee) {
        super(employee);
    }

}
