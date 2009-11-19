package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.seam.components.home.SchoolHome;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmploymentItem;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmployments;
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmploymentsGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "schoolEmploymentsReport")
@Scope(ScopeType.CONVERSATION)
public class SchoolEmploymentsReport extends BaseReport {

	@DataModel(value = "schoolEmployments")
	private SchoolUniversalEmployments schoolEmployments;

	private DateFormat dateFormat;

	@In
	private SchoolHome schoolHome;

	/**
	 * 
	 */
	public SchoolEmploymentsReport() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
		sb.append(" (");
		sb.append(disposal.getDays());
		sb.append(" ημέρες). ");
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
		sb.append(" (");
		sb.append(disposal.getDays());
		sb.append(" ημέρες). ");
		return sb.toString();

	}

	public void generateReport() {

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

		SchoolUniversalEmployments reportData = new SchoolUniversalEmployments(getCoreSearching().getSpecializationGroups(activeSchoolYear,
				getEntityManager()));

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
						: new SchoolUniversalEmploymentItem();
				item.setEmploymentComment(constructIncomingComment(secondment));

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
				continue;
			}
		}

		Collection<Disposal> incomingDisposal = getCoreSearching().getSchoolDisposals(getEntityManager(),
				schoolHome.getInstance(), activeSchoolYear, today, null);
		for (Disposal disposal : incomingDisposal) {
			try {
				SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(disposal);
				item.setEmploymentComment(constructIncommingComment(disposal));

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
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(serviceAllocation);
			item.setEmploymentComment(constructIncomingComment(serviceAllocation));

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

		/*
		
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
		*/
		setSchoolEmployments(reportData);
		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished - started));

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
