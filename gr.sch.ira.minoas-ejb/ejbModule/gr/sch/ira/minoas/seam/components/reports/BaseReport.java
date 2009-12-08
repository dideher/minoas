package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.reports.resource.DisposalReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.ServiceAllocationItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.core.SeamResourceBundle;

public abstract class BaseReport extends BaseDatabaseAwareSeamComponent {

	private static final String SEAM_MESSAGES_RESOURCE_BUNDLE_NAME = "messages";

	@In(required = true, create = true)
	private CoreSearching coreSearching;

	protected Collection<DisposalReportItem> convertDisposalCollection(Collection<Disposal> disposals) {
		Collection<DisposalReportItem> returnValue = new ArrayList<DisposalReportItem>(disposals.size());
		for (Disposal disposal : disposals) {
			returnValue.add(new DisposalReportItem(disposal));
		}
		return returnValue;
	}

	protected Collection<EmployeeReportItem> convertEmployeeCollection(Collection<Employee> employees) {
		Collection<EmployeeReportItem> returnValue = new ArrayList<EmployeeReportItem>(employees.size());
		for (Employee employee : employees) {
			returnValue.add(new EmployeeReportItem(employee));
		}
		return returnValue;
	}

	protected Collection<EmploymentReportItem> convertEmploymentCollection(Collection<Employment> employments) {
		Collection<EmploymentReportItem> returnValue = new ArrayList<EmploymentReportItem>(employments.size());
		for (Employment employment : employments) {
			returnValue.add(new EmploymentReportItem(employment));
		}
		return returnValue;
	}

	protected Collection<LeaveReportItem> convertLeaveCollection(Collection<Leave> leaves) {
		Collection<LeaveReportItem> returnValue = new ArrayList<LeaveReportItem>(leaves.size());
		for (Leave leave : leaves) {
			returnValue.add(new LeaveReportItem(leave));
		}
		return returnValue;
	}

	protected Collection<SecondmentItem> convertSecondmentCollection(Collection<Secondment> secondments) {
		Collection<SecondmentItem> returnValue = new ArrayList<SecondmentItem>(secondments.size());
		for (Secondment secondment : secondments) {
			returnValue.add(new SecondmentItem(secondment));
		}
		return returnValue;
	}

	protected Collection<ServiceAllocationItem> convertServiceAllocationCollection(
			Collection<ServiceAllocation> serviceAllocations) {
		Collection<ServiceAllocationItem> returnValue = new ArrayList<ServiceAllocationItem>(serviceAllocations.size());
		for (ServiceAllocation serviceAllocation : serviceAllocations) {
			returnValue.add(new ServiceAllocationItem(serviceAllocation));
		}
		return returnValue;
	}

	/**
	 * @return the coreSearching
	 */
	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	protected String getLocalizedMessage(String message_key) {
		return getResourceBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME).getString(message_key);
	}

	protected ResourceBundle getResourceBundle(String resource_budle_name) {
		return SeamResourceBundle.getBundle(SEAM_MESSAGES_RESOURCE_BUNDLE_NAME, FacesContext.getCurrentInstance()
				.getViewRoot().getLocale());
	}

	/**
	 * @param coreSearching the coreSearching to set
	 */
	public void setCoreSearching(CoreSearching coreSearching) {
		this.coreSearching = coreSearching;
	}

}
