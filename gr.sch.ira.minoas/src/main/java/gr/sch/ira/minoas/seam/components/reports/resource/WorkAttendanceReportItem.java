package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.WorkAttendanceEvent;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.security.Principal;

import java.util.Date;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class WorkAttendanceReportItem extends BaseIDReportItem {

	private String principalRealName;

	private String principalName;

	private Date entryDate;

	private Date exitDate;

	private Integer principalId;

	private Integer entryEventId;

	private Integer exitEventId;

	/**
	 * 
	 */
	public WorkAttendanceReportItem() {
		super();
	}

	public WorkAttendanceReportItem(Principal principal) {
		super(principal);
		if (principal != null) {
			this.principalRealName = principal.getRealName();
			this.principalName = principal.getUsername();
			this.principalId = principal.getId();
		}
	}

	public void addEntryEvent(WorkAttendanceEvent event) {
		this.entryEventId = event.getId();
		this.entryDate = event.getOccuredOn();
	}

	public void addExitEvent(WorkAttendanceEvent event) {
		this.exitEventId = event.getId();
		this.exitDate = event.getOccuredOn();
	}

	public String getPrincipalRealName() {
		return principalRealName;
	}

	public void setPrincipalRealName(String principalRealName) {
		this.principalRealName = principalRealName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public Integer getEntryEventId() {
		return entryEventId;
	}

	public void setEntryEventId(Integer entryEventId) {
		this.entryEventId = entryEventId;
	}

	public Integer getExitEventId() {
		return exitEventId;
	}

	public void setExitEventId(Integer exitEventId) {
		this.exitEventId = exitEventId;
	}

}
