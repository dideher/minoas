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
import gr.sch.ira.minoas.seam.components.reports.resource.SchoolUniversalEmploymentItem;
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
@Name(value = "schoolEmploymentsReport")
@Scope(ScopeType.CONVERSATION)
public class SchoolEmploymentsReport extends BaseReport {

	@DataModel(value = "schoolEmploymentsAnalysis")
	private Collection<SchoolUniversalEmploymentItem> schoolEmploymentsAnalysis;

	@In
	private SchoolHome schoolHome;

	/**
	 * 
	 */
	public SchoolEmploymentsReport() {
	}

	public void generateReport() {

		long started = System.currentTimeMillis(), finished;
		info("generating report school employment analysis");
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

		SchoolYear activeSchoolYear = getCoreSearching().getActiveSchoolYear(getEntityManager());

		
		
		schoolEmploymentsAnalysis = new ArrayList<SchoolUniversalEmploymentItem>();
		
		Collection<Employment> schoolRegularEmployments = getCoreSearching().getSchoolEmploymentsOfType(
				getEntityManager(), activeSchoolYear, schoolHome.getInstance(), EmploymentType.REGULAR);
		
		
		for(Employment employment : schoolRegularEmployments) {
			SchoolUniversalEmploymentItem item = new SchoolUniversalEmploymentItem(employment);
			
			
			/* check if the employment is associated with an secondment */
			Secondment s = getCoreSearching().getEmployeeActiveSecondment(getEntityManager(), employment.getEmployee(), today);
			if(s!=null) {
				/* the employment is overriden by a secondment */
				item.setEmployeeFinalWorkingHours(0);
				item.setEmploymentComment("Αποσπάση απο "+s.getEstablished()+" στο/σε "+s.getTargetUnit().getTitle());
			}
			
			/* check if the employment is associated with a disposal */
			Collection<Disposal> disposals = getCoreSearching().getEmployeeDisposals(getEntityManager(), employment.getEmployee(), today);
			if(disposals!=null && disposals.size() > 0) { 
				StringBuffer sb = new StringBuffer();
				int total_hours = 0;
				for(Disposal d : disposals) {
					sb.append(" Διάθεση απο "+d.getEstablished()+" στο/σε "+d.getDisposalUnit().getTitle()+" για "+d.getHours()+" ώρες ("+d.getDays()+" ημέρες). ");
					total_hours+=d.getHours();
				}
				item.setEmployeeFinalWorkingHours(item.getEmployeeFinalWorkingHours()-total_hours);
				item.setEmploymentComment(sb.toString());
			}
			
			getCoreSearching().getSch
			
			schoolEmploymentsAnalysis.add(item);
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

		finished = System.currentTimeMillis();
		info("report has been generated in #0 [ms]", (finished - started));

	}

}
