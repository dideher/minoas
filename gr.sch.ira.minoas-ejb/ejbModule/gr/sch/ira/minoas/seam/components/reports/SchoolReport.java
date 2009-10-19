package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationType;
import gr.sch.ira.minoas.seam.components.home.SchoolHome;
import gr.sch.ira.minoas.seam.components.reports.resource.DisposalReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmployeeReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.EmploymentReportItem;
import gr.sch.ira.minoas.seam.components.reports.resource.LeaveReportItem;
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
	private Collection<DisposalReportItem> incomingDisposals;

	@DataModel(value = "schoolOutgoingServiceAllocations")
	private Collection<ServiceAllocationItem> outcomingServiceAllocations;

	@DataModel(value = "schoolOutgoingSecondments")
	private Collection<SecondmentItem> outcomingSecondments;

	@DataModel(value = "schoolOutgoingDisposals")
	private Collection<DisposalReportItem> outcomingDisposals;

	@DataModel(value = "schoolLeaves")
	private Collection<LeaveReportItem> schoolLeaves;

	@DataModel(value = "schoolRegularsEmployees")
	private Collection<EmployeeReportItem> schoolRegularsEmployees;

	@DataModel(value = "schoolRegularEmployments")
	private Collection<EmploymentReportItem> schoolRegularEmployments;

	@DataModel(value = "schoolDeputyEmployments")
	private Collection<EmploymentReportItem> schoolDeputyEmployments;

	@DataModel(value = "schoolHourlyBasedEmployments")
	private Collection<EmploymentReportItem> schoolHourlyBasedEmployments;

	@DataModel(value = "schoolDeputyEmployees")
	private Collection<EmployeeReportItem> schoolDeputyEmployees;

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

	protected Collection<DisposalReportItem> convertDisposalCollection(Collection<Disposal> disposals) {
		Collection<DisposalReportItem> returnValue = new ArrayList<DisposalReportItem>(disposals.size());
		for (Disposal disposal : disposals) {
			returnValue.add(new DisposalReportItem(disposal));
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

	protected Collection<LeaveReportItem> convertLeaveCollection(Collection<Leave> leaves) {
		Collection<LeaveReportItem> returnValue = new ArrayList<LeaveReportItem>(leaves.size());
		for (Leave leave : leaves) {
			returnValue.add(new LeaveReportItem(leave));
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

	public void generateReport() {

		long started = System.currentTimeMillis(), finished;
		info("generating report ");
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		schoolChiefs = convertServiceAllocationCollection(getCoreSearching().getSchoolIncomingServiceAllocationsOfType(
				getEntityManager(), schoolHome.getInstance(), today,
				Arrays.asList(ServiceAllocationType.SCHOOL_HEADMASTER, ServiceAllocationType.SCHOOL_SUBHEADMASTER)));

		schoolRegularsEmployees = convertEmployeeCollection(getCoreSearching()
				.getSchoolActiveEmployeesOfEmploymentType(getEntityManager(), schoolHome.getInstance(),
						getCoreSearching().getActiveSchoolYear(getEntityManager()), today, EmploymentType.REGULAR));

		schoolDeputyEmployees = convertEmployeeCollection(getCoreSearching().getSchoolActiveEmployeesOfEmploymentType(
				getEntityManager(), schoolHome.getInstance(),
				getCoreSearching().getActiveSchoolYear(getEntityManager()), today, EmploymentType.DEPUTY));

		incomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolSecondments(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today));

		incomingServiceAllocations = convertServiceAllocationCollection(getCoreSearching()
				.getSchoolIncomingServiceAllocations(getEntityManager(), schoolHome.getInstance(), today));

		incomingDisposals = convertDisposalCollection(getCoreSearching().getSchoolDisposals(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today, null));

		outcomingSecondments = convertSecondmentCollection(getCoreSearching().getSchoolOutgoingSecondments(
				getEntityManager(), schoolHome.getInstance(), activeSchoolYear, today));

		schoolLeaves = convertLeaveCollection(getCoreSearching().getSchoolLeaves(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today, null));

		schoolRegularEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR));

		schoolDeputyEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.DEPUTY));

		schoolHourlyBasedEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.HOURLYBASED));

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
	public Collection<DisposalReportItem> getIncomingDisposals() {
		return incomingDisposals;
	}

	/**
	 * @param incomingDisposals the incomingDisposals to set
	 */
	public void setIncomingDisposals(Collection<DisposalReportItem> incomingDisposals) {
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
	public Collection<DisposalReportItem> getOutcomingDisposals() {
		return outcomingDisposals;
	}

	/**
	 * @param outcomingDisposals the outcomingDisposals to set
	 */
	public void setOutcomingDisposals(Collection<DisposalReportItem> outcomingDisposals) {
		this.outcomingDisposals = outcomingDisposals;
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

	/**
	 * @return the schoolDeputysEmployees
	 */
	public Collection<EmployeeReportItem> getSchoolDeputyEmployees() {
		return schoolDeputyEmployees;
	}

	/**
	 * @param schoolDeputysEmployees the schoolDeputysEmployees to set
	 */
	public void setSchoolDeputyEmployees(Collection<EmployeeReportItem> schoolDeputysEmployees) {
		this.schoolDeputyEmployees = schoolDeputysEmployees;
	}

	/**
	 * @return the schoolLeaves
	 */
	public Collection<LeaveReportItem> getSchoolLeaves() {
		return schoolLeaves;
	}

	/**
	 * @param schoolLeaves the schoolLeaves to set
	 */
	public void setSchoolLeaves(Collection<LeaveReportItem> schoolLeaves) {
		this.schoolLeaves = schoolLeaves;
	}

	/**
	 * @return the schoolRegularEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolRegularEmployments() {
		return schoolRegularEmployments;
	}

	/**
	 * @param schoolRegularEmployments the schoolRegularEmployments to set
	 */
	public void setSchoolRegularEmployments(Collection<EmploymentReportItem> schoolRegularEmployments) {
		this.schoolRegularEmployments = schoolRegularEmployments;
	}

	/**
	 * @return the schoolDeputyEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolDeputyEmployments() {
		return schoolDeputyEmployments;
	}

	/**
	 * @param schoolDeputyEmployments the schoolDeputyEmployments to set
	 */
	public void setSchoolDeputyEmployments(Collection<EmploymentReportItem> schoolDeputyEmployments) {
		this.schoolDeputyEmployments = schoolDeputyEmployments;
	}

	/**
	 * @return the schoolHourlyBasedEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolHourlyBasedEmployments() {
		return schoolHourlyBasedEmployments;
	}

	/**
	 * @param schoolHourlyBasedEmployments the schoolHourlyBasedEmployments to set
	 */
	public void setSchoolHourlyBasedEmployments(Collection<EmploymentReportItem> schoolHourlyBasedEmployments) {
		this.schoolHourlyBasedEmployments = schoolHourlyBasedEmployments;
	}



}
