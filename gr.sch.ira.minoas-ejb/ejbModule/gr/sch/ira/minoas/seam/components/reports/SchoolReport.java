package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.seam.components.home.SchoolHome;
import gr.sch.ira.minoas.seam.components.reports.resource.DisposalItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.ServiceAllocationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolReport")
@Scope(ScopeType.CONVERSATION)
public class SchoolReport extends BaseReport {

	@DataModel(value = "schoolChiefs")
	private Collection<ServiceAllocationItem> schoolChiefs;

	@DataModel(value = "incomingServiceAllocations")
	private Collection<ServiceAllocationItem> incomingServiceAllocations;

	@DataModel(value = "incomingSecondments")
	private Collection<SecondmentItem> incomingSecondments;

	@DataModel(value = "incomingDisposals")
	private Collection<DisposalItem> incomingDisposals;

	@DataModel(value = "schoolOutgoingServiceAllocations")
	private Collection<ServiceAllocationItem> outcomingServiceAllocations;
	
	@DataModel(value = "schoolOutgoingSecondments")
	private Collection<SecondmentItem> outcomingSecondments;
	
	@DataModel(value = "schoolOutgoingDisposals")
	private Collection<DisposalItem> outcomingDisposals;
	
	@DataModel(value = "schoolLeaves")
	private Collection<Leave> schoolLeaves;
	
	@DataModel(value = "schoolRegularsEmployees")
	private Collection<EmployeeReportItem> schoolRegularsEmployees;

	@In
	private SchoolHome schoolHome;

	/**
	 * 
	 */
	public SchoolReport() {
	}

	protected Collection<ServiceAllocationItem> convertServiceAllocationCollection(
			Collection<ServiceAllocation> serviceAllocations) {
		Collection<ServiceAllocationItem> returnValue = new ArrayList<ServiceAllocationItem>(serviceAllocations.size());
		for (ServiceAllocation serviceAllocation : serviceAllocations) {
			returnValue.add(new ServiceAllocationItem(serviceAllocation));
		}
		return returnValue;
	}

	protected Collection<DisposalItem> convertDisposalCollection(Collection<Disposal> disposals) {
		Collection<DisposalItem> returnValue = new ArrayList<DisposalItem>(disposals.size());
		for (Disposal disposal : disposals) {
			returnValue.add(new DisposalItem(disposal));
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
	
	protected Collection<EmployeeReportItem> convertEmployeeCollection(Collection<Employee> employees) {
		Collection<EmployeeReportItem> returnValue = new ArrayList<EmployeeReportItem>(employees.size());
		for (Employee employee : employees) {
			returnValue.add(new EmployeeReportItem(employee));
		}
		return returnValue;
	}

	public void generateReport() {

		long started = System.currentTimeMillis(), finished;
		info("generating report ");
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

		schoolChiefs = convertServiceAllocationCollection(getCoreSearching().getSchoolServiceAllocationsOfType(
				getEntityManager(), schoolHome.getInstance(), today,
				Arrays.asList(ServiceAllocationType.SCHOOL_HEADMASTER, ServiceAllocationType.SCHOOL_SUBHEADMASTER)));

		
		schoolRegularsEmployees = convertEmployeeCollection(getCoreSearching().getSchoolRegularEmployees(getEntityManager(), schoolHome.getInstance(), getCoreSearching().getActiveSchoolYear(getEntityManager()), today));

		incomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolSecondments(getEntityManager(),
				schoolHome.getInstance(), getCoreSearching().getActiveSchoolYear(getEntityManager()), today));

		incomingServiceAllocations = convertServiceAllocationCollection(getCoreSearching().getSchoolServiceAllocations(
				getEntityManager(), schoolHome.getInstance(), today));

		incomingDisposals = convertDisposalCollection(getCoreSearching().getSchoolDisposals(getEntityManager(),
				schoolHome.getInstance(), getCoreSearching().getActiveSchoolYear(getEntityManager()), today, null));
		
		
		outcomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolOutgoingSecondments(getEntityManager(),
				schoolHome.getInstance(), getCoreSearching().getActiveSchoolYear(getEntityManager()), today));

		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished - started));

	}

	/**
	 * @return the schoolChiefs
	 */
	public Collection<ServiceAllocationItem> getSchoolChiefs() {
		return schoolChiefs;
	}

	/**
	 * @param schoolChiefs the schoolChiefs to set
	 */
	public void setSchoolChiefs(Collection<ServiceAllocationItem> schoolChiefs) {
		this.schoolChiefs = schoolChiefs;
	}

	/**
	 * @return the incomingServiceAllocations
	 */
	public Collection<ServiceAllocationItem> getIncomingServiceAllocations() {
		return incomingServiceAllocations;
	}

	/**
	 * @param incomingServiceAllocations the incomingServiceAllocations to set
	 */
	public void setIncomingServiceAllocations(Collection<ServiceAllocationItem> incomingServiceAllocations) {
		this.incomingServiceAllocations = incomingServiceAllocations;
	}

	/**
	 * @return the incomingSecondments
	 */
	public Collection<SecondmentItem> getIncomingSecondments() {
		return incomingSecondments;
	}

	/**
	 * @param incomingSecondments the incomingSecondments to set
	 */
	public void setIncomingSecondments(Collection<SecondmentItem> incomingSecondments) {
		this.incomingSecondments = incomingSecondments;
	}

	/**
	 * @return the incomingDisposals
	 */
	public Collection<DisposalItem> getIncomingDisposals() {
		return incomingDisposals;
	}

	/**
	 * @param incomingDisposals the incomingDisposals to set
	 */
	public void setIncomingDisposals(Collection<DisposalItem> incomingDisposals) {
		this.incomingDisposals = incomingDisposals;
	}

	/**
	 * @return the outcomingServiceAllocations
	 */
	public Collection<ServiceAllocationItem> getOutcomingServiceAllocations() {
		return outcomingServiceAllocations;
	}

	/**
	 * @param outcomingServiceAllocations the outcomingServiceAllocations to set
	 */
	public void setOutcomingServiceAllocations(Collection<ServiceAllocationItem> outcomingServiceAllocations) {
		this.outcomingServiceAllocations = outcomingServiceAllocations;
	}

	/**
	 * @return the outcomingSecondments
	 */
	public Collection<SecondmentItem> getOutcomingSecondments() {
		return outcomingSecondments;
	}

	/**
	 * @param outcomingSecondments the outcomingSecondments to set
	 */
	public void setOutcomingSecondments(Collection<SecondmentItem> outcomingSecondments) {
		this.outcomingSecondments = outcomingSecondments;
	}

	/**
	 * @return the outcomingDisposals
	 */
	public Collection<DisposalItem> getOutcomingDisposals() {
		return outcomingDisposals;
	}

	/**
	 * @param outcomingDisposals the outcomingDisposals to set
	 */
	public void setOutcomingDisposals(Collection<DisposalItem> outcomingDisposals) {
		this.outcomingDisposals = outcomingDisposals;
	}

	/**
	 * @return the schoolLeaves
	 */
	public Collection<Leave> getSchoolLeaves() {
		return schoolLeaves;
	}

	/**
	 * @param schoolLeaves the schoolLeaves to set
	 */
	public void setSchoolLeaves(Collection<Leave> schoolLeaves) {
		this.schoolLeaves = schoolLeaves;
	}

	/**
	 * @return the schoolRegularsEmployees
	 */
	public Collection<EmployeeReportItem> getSchoolRegularsEmployees() {
		return schoolRegularsEmployees;
	}

	/**
	 * @param schoolRegularsEmployees the schoolRegularsEmployees to set
	 */
	public void setSchoolRegularsEmployees(Collection<EmployeeReportItem> schoolRegularsEmployees) {
		this.schoolRegularsEmployees = schoolRegularsEmployees;
	}

}
