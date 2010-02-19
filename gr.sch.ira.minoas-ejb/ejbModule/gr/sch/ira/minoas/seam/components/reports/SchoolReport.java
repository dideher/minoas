package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolYear;
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
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmploymentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmployments;
import gr.sch.ira.minoas.seam.components.reports.resource.SecondmentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.ServiceAllocationItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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

	private DateFormat dateFormat;

	@DataModel(value = "incomingDisposals")
	private Collection<DisposalReportItem> incomingDisposals;

	@DataModel(value = "incomingSecondments")
	private Collection<SecondmentItem> incomingSecondments;

	@DataModel(value = "incomingServiceAllocations")
	private Collection<ServiceAllocationItem> incomingServiceAllocations;

	@DataModel(value = "schoolOutgoingDisposals")
	private Collection<DisposalReportItem> outcomingDisposals;

	@DataModel(value = "schoolOutgoingSecondments")
	private Collection<SecondmentItem> outcomingSecondments;

	@DataModel(value = "schoolOutgoingServiceAllocations")
	private Collection<ServiceAllocationItem> outcomingServiceAllocations;

	@DataModel(value = "schoolChiefs")
	private Collection<ServiceAllocationItem> schoolChiefs;

	@DataModel(value = "schoolDeputyEmployees")
	private Collection<EmployeeReportItem> schoolDeputyEmployees;

	@DataModel(value = "schoolDeputyEmployments")
	private Collection<EmploymentReportItem> schoolDeputyEmployments;

	@DataModel(value = "schoolEmployments")
	private SchoolUniversalEmployments schoolEmployments;

	@In
	private SchoolHome schoolHome;

	@DataModel(value = "schoolHourlyBasedEmployments")
	private Collection<EmploymentReportItem> schoolHourlyBasedEmployments;

	@DataModel(value = "schoolLeaves")
	private Collection<LeaveReportItem> schoolLeaves;

	@DataModel(value = "schoolRegularEmployments")
	private Collection<EmploymentReportItem> schoolRegularEmployments;

	@DataModel(value = "schoolRegularsEmployees")
	private Collection<EmployeeReportItem> schoolRegularsEmployees;

	/**
	 * 
	 */
	public SchoolReport() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	private String constructComment(Disposal disposal) {
		StringBuffer sb = new StringBuffer();
		sb.append("Διάθεση απο τις ");
		sb.append(this.dateFormat.format(disposal.getEstablished()));
		sb.append(" εως και ");
		sb.append(this.dateFormat.format(disposal.getDueTo()));
		sb.append(" στο/σε ");
		sb.append(disposal.getDisposalUnit().getTitle());
		sb.append(" για ");
		sb.append(disposal.getHours());
		sb.append(" ωρες και ");
		sb.append(disposal.getDays());
		sb.append(" ημέρες. ");
		return sb.toString();

	}

	private String constructComment(Leave leave) {
		StringBuffer sb = new StringBuffer();
		sb.append("Άδεια τύπου ");
		sb.append(getLocalizedMessage(leave.getLeaveType().getKey()));
		sb.append(" απο τις ");
		sb.append(this.dateFormat.format(leave.getEstablished()));
		sb.append(" εως και ");
		sb.append(this.dateFormat.format(leave.getDueTo()));
		sb.append(". ");
		return sb.toString();
	}

	private String constructComment(Secondment secondment) {
		StringBuffer sb = new StringBuffer();
		sb.append("Αποσπάση απο τις ");
		sb.append(this.dateFormat.format(secondment.getEstablished()));
		sb.append(" στο/σε ");
		sb.append(secondment.getTargetUnit().getTitle());
		sb.append(". ");
		return sb.toString();
	}

	private String constructComment(ServiceAllocation serviceAllocation) {
		StringBuffer sb = new StringBuffer();
		sb.append("Θητεία τύπου ");
		sb.append(getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
		sb.append(" απο τις ");
		sb.append(this.dateFormat.format(serviceAllocation.getEstablished()));
		sb.append(" εως και ");
		sb.append(this.dateFormat.format(serviceAllocation.getDueTo()));
		sb.append(" στο/σε ");
		sb.append(serviceAllocation.getServiceUnit().getTitle());
		sb.append(". ");
		return sb.toString();
	}

	private String constructIncomingComment(Secondment secondment) {
		StringBuffer sb = new StringBuffer();
		sb.append("Αποσπασμένος/νη στην μονάδα απο τις ");
		sb.append(this.dateFormat.format(secondment.getEstablished()));
		sb.append(" απο το/την ");
		sb.append(secondment.getSourceUnit().getTitle());
		sb.append(". ");
		return sb.toString();
	}

	private String constructIncomingComment(ServiceAllocation serviceAllocation) {
		StringBuffer sb = new StringBuffer();
		sb.append("Υπηρετή στην μονάδα με θητεία τύπου ");
		sb.append(getLocalizedMessage(serviceAllocation.getServiceType().getKey()));
		sb.append(" απο τις ");
		sb.append(this.dateFormat.format(serviceAllocation.getEstablished()));
		sb.append(" εως και ");
		sb.append(this.dateFormat.format(serviceAllocation.getDueTo()));
		sb.append(" απο το/την ");
		sb.append(serviceAllocation.getSourceUnit().getTitle());
		sb.append(". ");
		return sb.toString();
	}

	private String constructIncommingComment(Disposal disposal) {
		StringBuffer sb = new StringBuffer();
		sb.append("Υπηρετή στην μονάδα λόγω διάθεσης απο τις ");
		sb.append(this.dateFormat.format(disposal.getEstablished()));
		sb.append(" εως και ");
		sb.append(this.dateFormat.format(disposal.getDueTo()));
		sb.append(" απο την μονάδα ");
		sb.append(disposal.getAffectedEmployment().getSchool().getTitle());
		sb.append(" για ");
		sb.append(disposal.getHours());
		sb.append(" ώρες και ");
		sb.append(disposal.getDays());
		sb.append(" ημέρες. ");
		return sb.toString();

	}

	public void fetchSchoolChiefs() {
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		schoolChiefs = convertServiceAllocationCollection(getCoreSearching().getSchoolIncomingServiceAllocationsOfType(
				getEntityManager(), schoolHome.getInstance(), today,
				Arrays.asList(ServiceAllocationType.SCHOOL_HEADMASTER, ServiceAllocationType.SCHOOL_SUBHEADMASTER)));
	}

	public void generateEmploymentsReport() {

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * CURRENT PROBLEM THERE ARE SECONDMENT WITHOUT EMPLOYMENT (FROM OTHER PYSDE) AND DISPOSALS (NO AFFECTED EMPLOYMENT CAUSE THE 
		 * DISPOSAL OVERRIDES A SECONDMENT).
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		long started = System.currentTimeMillis(), finished;
		info("generating report school employment analysis");
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

		SchoolUniversalEmployments reportData = new SchoolUniversalEmployments(getCoreSearching()
				.getSchoolTeachingRequirement(getEntityManager(), schoolHome.getInstance(), activeSchoolYear),
				getCoreSearching().getSpecializationGroups(activeSchoolYear, getEntityManager()));

		/* ************************************************************************************ */
		/* REGULAR EMPLOYMENTS 																	*/
		/* ************************************************************************************ */

		Collection<Employment> schoolRegularEmployments = getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR);

		for (Employment employment : schoolRegularEmployments) {
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(employment);

			/* check if the employment is associated with an secondment */
			Secondment s = getCoreSearching().getEmployeeActiveSecondment(getEntityManager(), employment.getEmployee(),
					today);
			if (s != null) {
				/* the employment is overriden by a secondment */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment(constructComment(s));
			}

			/* check if the employment is associated with a disposal */
			Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
					employment.getEmployee(), today);
			if (disposals != null && disposals.size() > 0) {
				StringBuffer sb = new StringBuffer();
				int total_hours = 0;
				for (Disposal d : disposals) {
					sb.append(constructComment(d));
					total_hours += d.getHours();
				}
				item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours() - total_hours);
				item.setEmploymentComment(sb.toString());
			}

			/* check if the employment is associated with a service allocation */
			ServiceAllocation serviceAllocation = getCoreSearching().getEmployeeActiveServiceAllocation(
					getEntityManager(), employment.getEmployee(), today);
			if (serviceAllocation != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnRegularPosition());
				item.setEmploymentComment(constructComment(serviceAllocation));
			}

			/* check if the employment is associated with a leave */
			Leave leave = getCoreSearching()
					.getEmployeeActiveLeave(getEntityManager(), employment.getEmployee(), today);
			if (leave != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment(constructComment(leave));
			}

			reportData.add(item);
		}

		/* ************************************************************************************ */
		/* DEPUTY EMPLOYMENTS 																	*/
		/* ************************************************************************************ */
		Collection<Employment> schoolDeputyEmployments = getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.DEPUTY);

		for (Employment employment : schoolDeputyEmployments) {
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(employment);

			/* check if the employment is associated with a disposal */
			Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
					employment.getEmployee(), today);
			if (disposals != null && disposals.size() > 0) {
				StringBuffer sb = new StringBuffer();
				int total_hours = 0;
				for (Disposal d : disposals) {
					sb.append(constructComment(d));
					total_hours += d.getHours();
				}
				item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours() - total_hours);
				item.setEmploymentComment(sb.toString());
			}

			/* check if the employment is associated with a service allocation */
			ServiceAllocation serviceAllocation = getCoreSearching().getEmployeeActiveServiceAllocation(
					getEntityManager(), employment.getEmployee(), today);
			if (serviceAllocation != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnRegularPosition());
				item.setEmploymentComment(constructComment(serviceAllocation));
			}

			/* check if the employment is associated with a leave */
			Leave leave = getCoreSearching()
					.getEmployeeActiveLeave(getEntityManager(), employment.getEmployee(), today);
			if (leave != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment(constructComment(leave));
			}

			reportData.add(item);
		}

		/* ************************************************************************************ */
		/* HOURLY BASED EMPLOYMENTS 																	*/
		/* ************************************************************************************ */
		Collection<Employment> schoolHourlyBasedEmployments = getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.HOURLYBASED);

		for (Employment employment : schoolHourlyBasedEmployments) {
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(employment);
			item.setEmployeeMandatoryHours(11);
			/* check if the employment is associated with a leave */
			Leave leave = getCoreSearching()
					.getEmployeeActiveLeave(getEntityManager(), employment.getEmployee(), today);
			if (leave != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment(constructComment(leave));
			}

			reportData.add(item);
		}

		/* now handle incomming employees */

		/* secondments */
		Collection<Secondment> incomingSecondents = getCoreSearching().getSchoolSecondments(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today);
		for (Secondment secondment : incomingSecondents) {
			try {
				SchoolUniversalEmploymentItem item = secondment.getAffectedEmployment() != null ? new SchoolUniversalEmploymentItem(
						secondment)
						: new SchoolUniversalEmploymentItem(secondment.getEmployee());
				item.setEmploymentComment(constructIncomingComment(secondment));

				if (secondment.getAffectedEmployment() == null) {
					/* this is a special case of secondement when it is not
					 * associated with an employment
					 * 
					 * This shit happens with secondments from other PYSDE
					 */

					if (secondment.getFinalWorkingHours() != null)
						item.setEmployeeFinalWorkingHours(secondment.getFinalWorkingHours());
					if (secondment.getMandatoryWorkingHours() != null)
						item.setEmployeeMandatoryHours(secondment.getMandatoryWorkingHours());
					//item.setEmployeeEmploymentEstablishedDate(employment.getEstablished());
					//item.setEmployeeEmploymentTerminatedDate(employment.getTerminated());

				}

				/* check if the employment is associated with a disposal */
				Collection<Disposal> disposals = getCoreSearching().getEmployeeActiveDisposals(getEntityManager(),
						secondment.getEmployee(), today);
				if (disposals != null && disposals.size() > 0) {
					StringBuffer sb = new StringBuffer();
					int total_hours = 0;
					for (Disposal d : disposals) {
						sb.append(constructComment(d));
						total_hours += d.getHours();
					}
					item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours() - total_hours);
					item.setEmploymentComment(item.getEmploymentComment().concat(sb.toString()));
				}

				/* check if the employment is associated with a leave */
				Leave leave = getCoreSearching().getEmployeeActiveLeave(getEntityManager(), secondment.getEmployee(),
						today);
				if (leave != null) {
					/* the employment is overriden by a service allocation */
					item.setEmployeeFinalWorkingHours(0);
					item.setEmploymentComment(item.getEmploymentComment().concat(constructComment(leave)));
				}

				reportData.add(item);
			} catch (Exception ex) {
				System.err.println(ex);
				error("error with secondment #1", incomingSecondents);
				continue;
			}
		}

		Collection<Disposal> incomingDisposal = getCoreSearching().getSchoolDisposals(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today, null);
		for (Disposal disposal : incomingDisposal) {
			try {
				SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(disposal);
				item.setEmploymentComment(constructIncommingComment(disposal));
				item.setEmployeeFinalWorkingHours(disposal.getHours());

				/* check if the employment is associated with a leave */
				Leave leave = getCoreSearching().getEmployeeActiveLeave(getEntityManager(), disposal.getEmployee(),
						today);
				if (leave != null) {
					/* the employment is overriden by a service allocation */
					item.setEmployeeFinalWorkingHours(0);
					item.setEmploymentComment(item.getEmploymentComment().concat(constructComment(leave)));
				}

				reportData.add(item);
			} catch (Exception ex) {
				continue;
			}
		}

		Collection<ServiceAllocation> incomingServiceAllocation = getCoreSearching()
				.getSchoolIncomingServiceAllocations(getEntityManager(), schoolHome.getInstance(), today);
		for (ServiceAllocation serviceAllocation : incomingServiceAllocation) {

			/* check if the incoming service allocation has regular position in the school. 
			 * If so, then it has been already handled.
			 */
			if (serviceAllocation.getServiceUnit().getId().equals(serviceAllocation.getSourceUnit().getId())) {
				continue;
			}
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(serviceAllocation);
			item.setEmploymentComment(constructIncomingComment(serviceAllocation));
			item.setEmployeeFinalWorkingHours(serviceAllocation.getWorkingHoursOnServicingPosition());

			/* check if the employment is associated with a leave */
			Leave leave = getCoreSearching().getEmployeeActiveLeave(getEntityManager(),
					serviceAllocation.getEmployee(), today);
			if (leave != null) {
				/* the employment is overriden by a service allocation */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment(item.getEmploymentComment().concat(constructComment(leave)));
			}

			reportData.add(item);

		}

		setSchoolEmployments(reportData);
		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished - started));

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

		outcomingServiceAllocations = convertServiceAllocationCollection(getCoreSearching()
				.getSchoolReallyOutgoingServiceAllocations(getEntityManager(), schoolHome.getInstance(), today, null));

		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished - started));

	}

	/**
	 * @return the incomingDisposals
	 */
	public Collection<DisposalReportItem> getIncomingDisposals() {
		return incomingDisposals;
	}

	/**
	 * @return the incomingSecondments
	 */
	public Collection<SecondmentItem> getIncomingSecondments() {
		return incomingSecondments;
	}

	/**
	 * @return the incomingServiceAllocations
	 */
	public Collection<ServiceAllocationItem> getIncomingServiceAllocations() {
		return incomingServiceAllocations;
	}

	/**
	 * @return the outcomingDisposals
	 */
	public Collection<DisposalReportItem> getOutcomingDisposals() {
		return outcomingDisposals;
	}

	/**
	 * @return the outcomingSecondments
	 */
	public Collection<SecondmentItem> getOutcomingSecondments() {
		return outcomingSecondments;
	}

	/**
	 * @return the outcomingServiceAllocations
	 */
	public Collection<ServiceAllocationItem> getOutcomingServiceAllocations() {
		return outcomingServiceAllocations;
	}

	/**
	 * @return the schoolChiefs
	 */
	public Collection<ServiceAllocationItem> getSchoolChiefs() {
		return schoolChiefs;
	}

	/**
	 * @return the schoolDeputysEmployees
	 */
	public Collection<EmployeeReportItem> getSchoolDeputyEmployees() {
		return schoolDeputyEmployees;
	}

	/**
	 * @return the schoolDeputyEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolDeputyEmployments() {
		return schoolDeputyEmployments;
	}

	/**
	 * @return the schoolHourlyBasedEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolHourlyBasedEmployments() {
		return schoolHourlyBasedEmployments;
	}

	/**
	 * @return the schoolLeaves
	 */
	public Collection<LeaveReportItem> getSchoolLeaves() {
		return schoolLeaves;
	}

	/**
	 * @return the schoolRegularEmployments
	 */
	public Collection<EmploymentReportItem> getSchoolRegularEmployments() {
		return schoolRegularEmployments;
	}

	/**
	 * @return the schoolRegularsEmployees
	 */
	public Collection<EmployeeReportItem> getSchoolRegularsEmployees() {
		return schoolRegularsEmployees;
	}

	public void lookupDeputyEmployments() {
		long started = System.currentTimeMillis(), finished;
		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		info("deputy employee lookup in school unit #0 during school year #1", schoolHome.getInstance(),
				activeSchoolYear);
		schoolDeputyEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.DEPUTY));
		finished = System.currentTimeMillis();
		info("lookup found #0 deputy employment(s) totally in #1 [ms]", schoolDeputyEmployments.size(),
				(finished - started));
	}

	public void lookupHourlyBasedEmployments() {
		long started = System.currentTimeMillis(), finished;
		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		info("hourly based employee lookup in school unit #0 during school year #1", schoolHome.getInstance(),
				activeSchoolYear);
		schoolHourlyBasedEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.HOURLYBASED));
		finished = System.currentTimeMillis();
		info("lookup found #0 hourly based employment(s) totally in #1 [ms]", schoolHourlyBasedEmployments.size(),
				(finished - started));
	}

	public void lookupRegularEmployments() {
		long started = System.currentTimeMillis(), finished;
		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());
		info("regular employee lookup in school unit #0 during school year #1", schoolHome.getInstance(),
				activeSchoolYear);
		schoolRegularEmployments = convertEmploymentCollection(getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR));
		finished = System.currentTimeMillis();
		info("lookup found #0 regular employment(s) totally in #1 [ms]", schoolRegularEmployments.size(),
				(finished - started));
	}

	/**
	 * @param incomingDisposals the incomingDisposals to set
	 */
	public void setIncomingDisposals(Collection<DisposalReportItem> incomingDisposals) {
		this.incomingDisposals = incomingDisposals;
	}

	/**
	 * @param incomingSecondments the incomingSecondments to set
	 */
	public void setIncomingSecondments(Collection<SecondmentItem> incomingSecondments) {
		this.incomingSecondments = incomingSecondments;
	}

	/**
	 * @param incomingServiceAllocations the incomingServiceAllocations to set
	 */
	public void setIncomingServiceAllocations(Collection<ServiceAllocationItem> incomingServiceAllocations) {
		this.incomingServiceAllocations = incomingServiceAllocations;
	}

	/**
	 * @param outcomingDisposals the outcomingDisposals to set
	 */
	public void setOutcomingDisposals(Collection<DisposalReportItem> outcomingDisposals) {
		this.outcomingDisposals = outcomingDisposals;
	}

	/**
	 * @param outcomingSecondments the outcomingSecondments to set
	 */
	public void setOutcomingSecondments(Collection<SecondmentItem> outcomingSecondments) {
		this.outcomingSecondments = outcomingSecondments;
	}

	/**
	 * @param outcomingServiceAllocations the outcomingServiceAllocations to set
	 */
	public void setOutcomingServiceAllocations(Collection<ServiceAllocationItem> outcomingServiceAllocations) {
		this.outcomingServiceAllocations = outcomingServiceAllocations;
	}

	/**
	 * @param schoolChiefs the schoolChiefs to set
	 */
	public void setSchoolChiefs(Collection<ServiceAllocationItem> schoolChiefs) {
		this.schoolChiefs = schoolChiefs;
	}

	/**
	 * @param schoolDeputysEmployees the schoolDeputysEmployees to set
	 */
	public void setSchoolDeputyEmployees(Collection<EmployeeReportItem> schoolDeputysEmployees) {
		this.schoolDeputyEmployees = schoolDeputysEmployees;
	}

	/**
	 * @param schoolDeputyEmployments the schoolDeputyEmployments to set
	 */
	public void setSchoolDeputyEmployments(Collection<EmploymentReportItem> schoolDeputyEmployments) {
		this.schoolDeputyEmployments = schoolDeputyEmployments;
	}

	/**
	 * @param schoolHourlyBasedEmployments the schoolHourlyBasedEmployments to set
	 */
	public void setSchoolHourlyBasedEmployments(Collection<EmploymentReportItem> schoolHourlyBasedEmployments) {
		this.schoolHourlyBasedEmployments = schoolHourlyBasedEmployments;
	}

	/**
	 * @param schoolLeaves the schoolLeaves to set
	 */
	public void setSchoolLeaves(Collection<LeaveReportItem> schoolLeaves) {
		this.schoolLeaves = schoolLeaves;
	}

	/**
	 * @param schoolRegularEmployments the schoolRegularEmployments to set
	 */
	public void setSchoolRegularEmployments(Collection<EmploymentReportItem> schoolRegularEmployments) {
		this.schoolRegularEmployments = schoolRegularEmployments;
	}

	/**
	 * @param schoolRegularsEmployees the schoolRegularsEmployees to set
	 */
	public void setSchoolRegularsEmployees(Collection<EmployeeReportItem> schoolRegularsEmployees) {
		this.schoolRegularsEmployees = schoolRegularsEmployees;
	}

	/**
	 * @return the schoolEmployments
	 */
	public SchoolUniversalEmployments getSchoolEmployments() {
		return schoolEmployments;
	}

	/**
	 * @param schoolEmployments the schoolEmployments to set
	 */
	public void setSchoolEmployments(SchoolUniversalEmployments schoolEmployments) {
		this.schoolEmployments = schoolEmployments;
	}

}
