package gr.sch.ira.minoas.seam.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.SpecialAssigment;
import gr.sch.ira.minoas.model.employement.TeachingHourCDR;
import gr.sch.ira.minoas.model.employement.TeachingHourCDRType;
import gr.sch.ira.minoas.seam.components.management.TeachingHoursCDRManagement;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employeeCRDCalculation")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class EmployeeCRDCalculation extends BaseDatabaseAwareSeamComponent {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Transactional(TransactionPropagationType.REQUIRED)
	public void calculateEmployeeCDR(Employee employee) {

		CoreSearching coreSearching = getCoreSearching();

		long started = System.currentTimeMillis();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		EntityManager em = getEntityManager();

		SchoolYear currentSchoolYear = coreSearching.getActiveSchoolYear(em);
		info(String
				.format("[EmployeeCRDCalculation] : generating teaching hours CDR for employee '%s' during current school year '%s'.",
						employee, currentSchoolYear));

		/* first fetch all current CDRs and remove them */
		Query q = getEntityManager()
				.createQuery(
						"DELETE TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee");
		q.setParameter("schoolYear", currentSchoolYear);
		q.setParameter("employee", employee);

		int cdrsDeleted = q.executeUpdate();
		info(String
				.format("[EmployeeCRDCalculation] : successfully deleted totally '%d' old CRD(s) for employee '%s'.",
						cdrsDeleted, employee));

		/* fetch employee active employments - we can't the the current employment since we can have HOURLYBASED employees
		 * which have many active employments during the year
		 */
		Collection<Employment> employeeEmployments = coreSearching
				.getEmployeeActiveEmployments(employee, currentSchoolYear);
		for (Employment employment : employeeEmployments) {
			TeachingHourCDR cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.EMPLOYMENT);
			switch (employment.getType()) {
			case REGULAR:
				cdr.setComment(String
						.format("Οργανική θέση στην μονάδα από τις '%s' με υποχρεωτικό ωράριο '%d' ώρες",
								df.format(employment.getEstablished()),
								employment.getFinalWorkingHours()));
				cdr.setSpecialization(employment.getSpecialization());
				cdr.setEmployee(employment.getEmployee());
				cdr.setEmployment(employment);
				cdr.setHours(employment.getFinalWorkingHours());
				cdr.setUnit(employment.getSchool());
				cdr.setSchoolYear(currentSchoolYear);
				break;
			case DEPUTY:
				cdr.setComment(String
						.format("Τοποθέτηση αναπληρωτή στην μονάδα από τις %s για %d ώρες.",
								df.format(employment.getEstablished()),
								employment.getFinalWorkingHours()));
				cdr.setSpecialization(employment.getSpecialization());
				cdr.setEmployee(employment.getEmployee());
				cdr.setEmployment(employment);
				cdr.setHours(employment.getFinalWorkingHours());
				cdr.setUnit(employment.getSchool());
				cdr.setSchoolYear(currentSchoolYear);
				employment.getEmploymentCDRs().add(cdr);
				break;
			case HOURLYBASED:
				cdr.setComment(String
						.format("Τοποθέτηση ωρομίσθιου στην μονάδα από τις %s για %d ώρες.",
								df.format(employment.getEstablished()),
								employment.getFinalWorkingHours()));
				cdr.setSpecialization(employment.getSpecialization());
				cdr.setEmployee(employment.getEmployee());
				cdr.setEmployment(employment);
				cdr.setHours(employment.getFinalWorkingHours());
				cdr.setUnit(employment.getSchool());
				cdr.setSchoolYear(currentSchoolYear);
				employment.getEmploymentCDRs().add(cdr);
			default:
				break;
			}
			employment.getEmploymentCDRs().add(cdr);
			entityManager.persist(cdr);

		}

		/* handle secodnments */

		Secondment secondment = coreSearching.getEmployeeActiveSecondment(getEntityManager(), employee, new Date());
		if(secondment != null) {

			/* hack for secondments */
			if (secondment.getFinalWorkingHours() != null
					&& secondment.getMandatoryWorkingHours() != null
					&& secondment.getFinalWorkingHours().intValue() == secondment
							.getMandatoryWorkingHours().intValue()) {
				/*
				 * if final working hour is equal to mandatory working hours
				 * then it means that the valus were copied from the employment
				 */
				Integer workingHours = secondment.getFinalWorkingHours();
				Employment employment = secondment.getAffectedEmployment();
				if (employment != null) {
					if (employment.getMandatoryWorkingHours().intValue() != workingHours
							.intValue()) {
						secondment.setFinalWorkingHours(employment
								.getMandatoryWorkingHours());
						secondment.setMandatoryWorkingHours(employment
								.getMandatoryWorkingHours());
					}
				}
			}

			TeachingHourCDR cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.SECONDMENT);
			StringBuffer sb = new StringBuffer();
			sb.append("Αποσπασμένος από την μονάδα ");
			sb.append(secondment.getSourceUnit().getTitle());
			sb.append(" με απόσπαση τύπου ");
			sb.append(CoreUtils.getLocalizedMessage(secondment
					.getSecondmentType().getKey()));
			sb.append(" από τις ");
			sb.append(df.format(secondment.getEstablished()));
			sb.append(" για ");
			sb.append(secondment.getFinalWorkingHours());
			sb.append(" ώρες.");
			cdr.setComment(sb.toString());
			cdr.setSpecialization(secondment.getEmployee()
					.getLastSpecialization());
			cdr.setEmployee(secondment.getEmployee());
			cdr.setSecondment(secondment);
			cdr.setHours(secondment.getFinalWorkingHours());
			cdr.setUnit(secondment.getTargetUnit());
			cdr.setSchoolYear(currentSchoolYear);
			entityManager.persist(cdr);
			
			/* apply on source unit */

			cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.SECONDMENT);
			sb = new StringBuffer();
			sb.append("Αποσπασμένος στην μονάδα ");
			sb.append(secondment.getTargetUnit().getTitle());
			sb.append(" με απόσπαση τυπου ");
			sb.append(CoreUtils.getLocalizedMessage(secondment
					.getSecondmentType().getKey()));
			sb.append(" από τις ");
			sb.append(df.format(secondment.getEstablished()));
			sb.append(" για ");
			sb.append(secondment.getFinalWorkingHours());
			sb.append(" ώρες.");
			cdr.setComment(sb.toString());
			cdr.setEmployee(secondment.getEmployee());
			cdr.setSpecialization(secondment.getEmployee()
					.getLastSpecialization());
			cdr.setSecondment(secondment);
			cdr.setHours((-1) * secondment.getFinalWorkingHours());
			cdr.setUnit(secondment.getSourceUnit());
			cdr.setSchoolYear(currentSchoolYear);
			cdr.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
			entityManager.persist(cdr);
			

		}

		/* handle disposal */

		Collection<Disposal> disposals = coreSearching.getEmployeeActiveDisposals(getEntityManager(), employee, new Date());
		for (Disposal disposal : disposals) {

			Unit sourceUnit = null;
			try {
				sourceUnit = disposal.getAffectedEmployment() != null ? disposal
						.getAffectedEmployment().getSchool() : disposal
						.getAffectedSecondment().getTargetUnit();
			} catch (Exception ex) {
				error("[EmployeeCRDCalculation] : unhandled exception with disposal #0. Exception #1",
						disposal, ex);
				continue;
			}

			TeachingHourCDR cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.DISPOSAL);
			StringBuffer sb = new StringBuffer();
			sb.append("Διάθεση από την μονάδα ");
			sb.append(sourceUnit.getTitle());
			sb.append(" με διάθεση τυπου ");
			sb.append(CoreUtils
					.getLocalizedMessage(disposal.getType().getKey()));
			sb.append(" από τις ");
			sb.append(df.format(disposal.getEstablished()));
			sb.append(" για ");
			sb.append(disposal.getHours());
			sb.append(" ώρες.");
			cdr.setComment(sb.toString());
			cdr.setSpecialization(disposal.getEmployee()
					.getLastSpecialization());
			cdr.setEmployee(disposal.getEmployee());
			cdr.setDisposal(disposal);
			cdr.setHours(disposal.getHours());
			cdr.setUnit(disposal.getDisposalUnit());
			cdr.setSchoolYear(currentSchoolYear);
			entityManager.persist(cdr);

			/* apply on source unit */

			cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.DISPOSAL);
			sb = new StringBuffer();
			sb.append("Διάθεση στην μονάδα ");
			sb.append(disposal.getDisposalUnit().getTitle());
			sb.append(" με διάθεση τυπου ");
			sb.append(CoreUtils
					.getLocalizedMessage(disposal.getType().getKey()));
			sb.append(" από τις ");
			sb.append(df.format(disposal.getEstablished()));
			sb.append(" για ");
			sb.append(disposal.getHours());
			sb.append(" ώρες.");
			cdr.setComment(sb.toString());
			cdr.setSpecialization(disposal.getEmployee()
					.getLastSpecialization());
			cdr.setEmployee(disposal.getEmployee());
			cdr.setDisposal(disposal);
			cdr.setHours((-1) * disposal.getHours());
			cdr.setLogisticCDR(Boolean.TRUE); /* this is a logistic CDR */
			cdr.setUnit(sourceUnit);
			cdr.setSchoolYear(currentSchoolYear);
			entityManager.persist(cdr);

		}

		

		/* handle service allocation */
		
		ServiceAllocation serviceAllocation = coreSearching.getEmployeeActiveServiceAllocation(getEntityManager(), employee, new Date());
		if (serviceAllocation != null) {
			try {
				if (serviceAllocation.getSourceUnit() == null) {
					/* this should never happen */
					warn("[EmployeeCRDCalculation] : service allocation #0 has no source unit !!!!!!",
							serviceAllocation);
					
				}

				/* service allocation is always associated with an employment */
				Employment relatedEmployment = null;
				try {
					relatedEmployment = serviceAllocation.getEmployee()
							.getCurrentEmployment();
				} catch (Exception ex) {
					warn(String
							.format("[EmployeeCRDCalculation] : logic error -> failed to extract current employment for employee '%s'",
									serviceAllocation.getEmployee()));
				}

				if (relatedEmployment == null)
					serviceAllocation.getAffectedEmployment();

				serviceAllocation.getEmployee().getCurrentEmployment();
				if (relatedEmployment != null) {
					Collection<TeachingHourCDR> employmentsCDRs = getCoreSearching()
							.getTeachingHoursCDRsRelatedToEmployment(
									getEntityManager(), relatedEmployment,
									currentSchoolYear);
					/* Employment should have one only one CDR */
					if (employmentsCDRs.size() > 1) {
						/* this should never happen */
						warn("[EmployeeCRDCalculation] : logic error -> employment #0 is associated with more than one cdrs -> #1",
								relatedEmployment, employmentsCDRs);
					}

					/*
					 * construct a special CDR to deduct the employment CDR
					 * hours
					 */
					TeachingHourCDR specialEmploymentCDR = new TeachingHourCDR();
					specialEmploymentCDR
							.setCdrType(TeachingHourCDRType.GENERAL);
					StringBuffer sb = new StringBuffer();
					sb.append("Αντιλογισμός διδακτικών ωρών της σχέσης εργασίας από ");
					sb.append(df.format(relatedEmployment.getEstablished()));
					sb.append(" με υποχρεωτικό ωράριο ");
					sb.append(relatedEmployment.getFinalWorkingHours());
					sb.append(" ώρες λόγω θητείας τύπου ");
					sb.append(CoreUtils.getLocalizedMessage(serviceAllocation
							.getServiceType().getKey()));
					specialEmploymentCDR.setComment(sb.toString());
					specialEmploymentCDR.setSpecialization(relatedEmployment
							.getSpecialization());
					specialEmploymentCDR.setEmployee(relatedEmployment
							.getEmployee());
					specialEmploymentCDR.setEmployment(relatedEmployment);
					specialEmploymentCDR.setHours((-1)
							* relatedEmployment.getFinalWorkingHours());
					specialEmploymentCDR.setLogisticCDR(Boolean.TRUE); /*
																		 * this
																		 * is a
																		 * logistic
																		 * CDR
																		 */
					specialEmploymentCDR.setUnit(relatedEmployment.getSchool());
					specialEmploymentCDR.setSchoolYear(currentSchoolYear);
					relatedEmployment.getEmploymentCDRs().add(
							specialEmploymentCDR);
					entityManager.persist(specialEmploymentCDR);

				} else {
					warn("[EmployeeCRDCalculation] : service allocation #0 does not have an regular employment !",
							serviceAllocation);
					
				}

				if (serviceAllocation.getSourceUnit().equals(
						serviceAllocation.getServiceUnit())) {
					/*
					 * source unit is the same as the servicing unit. This is
					 * the case when a regular employee having a regular
					 * employment at a given unit, serves there as the
					 * headmaster.
					 */
					TeachingHourCDR cdr = new TeachingHourCDR();
					cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);

					StringBuffer sb = new StringBuffer();
					sb.append("Θητεία στην μονάδα ");
					sb.append(serviceAllocation.getSourceUnit().getTitle());
					sb.append(" τυπου ");
					sb.append(CoreUtils.getLocalizedMessage(serviceAllocation
							.getServiceType().getKey()));
					sb.append(" από τις ");
					sb.append(df.format(serviceAllocation.getEstablished()));
					sb.append(" για ");
					sb.append(serviceAllocation
							.getWorkingHoursOnServicingPosition());
					sb.append(" ώρες.");
					cdr.setComment(sb.toString());
					cdr.setSpecialization(serviceAllocation.getEmployee()
							.getLastSpecialization());
					cdr.setEmployee(serviceAllocation.getEmployee());
					cdr.setServiceAllocation(serviceAllocation);
					cdr.setHours(serviceAllocation
							.getWorkingHoursOnServicingPosition());
					cdr.setUnit(serviceAllocation.getServiceUnit());
					cdr.setSchoolYear(currentSchoolYear);
					entityManager.persist(cdr);

				} else {
					/* apply on target unit */
					TeachingHourCDR cdr = new TeachingHourCDR();
					cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
					StringBuffer sb = new StringBuffer();
					sb.append("Θητεία από την μονάδα ");
					sb.append(serviceAllocation.getSourceUnit().getTitle());
					sb.append(" τυπου ");
					sb.append(CoreUtils.getLocalizedMessage(serviceAllocation
							.getServiceType().getKey()));
					sb.append(" από τις ");
					sb.append(df.format(serviceAllocation.getEstablished()));
					sb.append(" για ");
					sb.append(serviceAllocation
							.getWorkingHoursOnServicingPosition());
					sb.append(" ώρες.");
					cdr.setComment(sb.toString());
					cdr.setSpecialization(serviceAllocation.getEmployee()
							.getLastSpecialization());
					cdr.setEmployee(serviceAllocation.getEmployee());
					cdr.setServiceAllocation(serviceAllocation);
					cdr.setHours(serviceAllocation
							.getWorkingHoursOnServicingPosition());
					cdr.setUnit(serviceAllocation.getServiceUnit());
					cdr.setSchoolYear(currentSchoolYear);
					entityManager.persist(cdr);

					/* apply on source unit */

					cdr = new TeachingHourCDR();
					cdr.setCdrType(TeachingHourCDRType.SERVICE_ALLOCATION);
					sb = new StringBuffer();
					sb.append("Θητεία στην μονάδα ");
					sb.append(serviceAllocation.getServiceUnit().getTitle());
					sb.append(" τυπου ");
					sb.append(CoreUtils.getLocalizedMessage(serviceAllocation
							.getServiceType().getKey()));
					sb.append(" από τις ");
					sb.append(df.format(serviceAllocation.getEstablished()));
					sb.append(" για ");
					sb.append(serviceAllocation
							.getWorkingHoursOnServicingPosition());
					sb.append(" ώρες.");
					cdr.setComment(sb.toString());
					cdr.setSpecialization(serviceAllocation.getEmployee()
							.getLastSpecialization());
					cdr.setEmployee(serviceAllocation.getEmployee());
					cdr.setServiceAllocation(serviceAllocation);
					cdr.setHours(serviceAllocation
							.getWorkingHoursOnRegularPosition());
					cdr.setUnit(serviceAllocation.getSourceUnit());
					cdr.setSchoolYear(currentSchoolYear);
					entityManager.persist(cdr);

				}

			} catch (Exception ex) {
				String msg = String
						.format("[EmployeeCRDCalculation] : unhandled exception '%s' while handling service allocation %s",
								ex.getMessage(), serviceAllocation);
				error(msg, ex);
				throw new RuntimeException(msg, ex);
			}

		}

		

		/* handle special assigments */
		Collection<SpecialAssigment> activeSpecialAssigments = coreSearching
				.getEmployeeSpecialAssigments(getEntityManager(), employee);
		for (SpecialAssigment specialAssigment : activeSpecialAssigments) {

			/*
			 * deduct the special assigments hours from the employee's original
			 * specialization. We are deducting, because the employee will not
			 * provide all his hours for his primary specialization
			 */

			TeachingHourCDR cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.SPECIAL_ASSIGMENT);
			String msg = String
					.format("Αντιλογισμός διδακτικών ωρών κύριας ειδικότητας, από την μονάδα '%s', λόγω ειδικής ασχολίας τύπου '%s' για συνολικά '%d' ώρες.",
							specialAssigment.getUnit().getTitle(),
							specialAssigment.getSpecializationGroup()
									.getTitle(), specialAssigment
									.getFinalWorkingHours());
			cdr.setComment(msg);
			cdr.setSpecialization(specialAssigment.getEmployee()
					.getLastSpecialization());
			cdr.setEmployee(specialAssigment.getEmployee());
			cdr.setSpecialAssigment(specialAssigment);
			cdr.setHours(-1 * specialAssigment.getFinalWorkingHours());
			cdr.setUnit(specialAssigment.getUnit());
			cdr.setSchoolYear(currentSchoolYear);
			cdr.setLogisticCDR(Boolean.TRUE);
			entityManager.persist(cdr);

			/* apply on target unit */
			cdr = new TeachingHourCDR();
			cdr.setCdrType(TeachingHourCDRType.SPECIAL_ASSIGMENT);
			msg = String
					.format("Ειδική ασχολία στην μονάδα '%s' τύπου '%s' για συνολικά '%d' ώρες.",
							specialAssigment.getUnit().getTitle(),
							specialAssigment.getSpecializationGroup()
									.getTitle(), specialAssigment
									.getFinalWorkingHours());
			cdr.setComment(msg);
			cdr.setSpecialization(specialAssigment.getSpecializationGroup()
					.getVirtualSpecialization());
			cdr.setEmployee(specialAssigment.getEmployee());
			cdr.setSpecialAssigment(specialAssigment);
			cdr.setHours(specialAssigment.getFinalWorkingHours());
			cdr.setUnit(specialAssigment.getUnit());
			cdr.setSchoolYear(currentSchoolYear);
			entityManager.persist(cdr);

		}
		
		/* WE NEED TO ADD LEAVES */

		/*
		 * Leaves are a special case. For each employee with a leave we will
		 * compute his hours distribution in all units. For each unit for which
		 * the employee has teaching hours > 0 we will add a LEAVE CDR with
		 * negative hours
		 */

		/* found active leaves */

		EmployeeLeave activeLeave = coreSearching.getEmployeeActiveLeave2(getEntityManager(), employee, new Date());
		
		/* found future leaves */
		Collection<EmployeeLeave> futureLeaves = coreSearching
				.getEmployeeFutureLeavesThatWillBeActivated(em, employee, new Date(), 20);
		info(String.format("[EmployeeCRDCalculation] : found %d totally future leaves for employee '%s'", futureLeaves.size(), employee));

		Map<Integer, Employee> employeeWithAccountedLeaves = new HashMap<Integer, Employee>();

		/* create the final array of all leaves that we should process */
		Collection<EmployeeLeave> leavesToProcess = new ArrayList<EmployeeLeave>();

		/* first check the active Leaves */
		if (activeLeave != null) {
			if (activeLeave.getEffectiveNumberOfDays() != null
					&& activeLeave.getEffectiveNumberOfDays() > TeachingHoursCDRManagement.LEAVE_DAYS_THREASHOLD) {
				Employee employeeWithLeave = activeLeave.getEmployee();
				employeeWithAccountedLeaves.put(employeeWithLeave.getId(),
						employeeWithLeave);
				leavesToProcess.add(activeLeave);
			} else {
				info("[EmployeeCRDCalculation] : ignoring leave #0 because it's duration does not exceeds #1 days",
						activeLeave, TeachingHoursCDRManagement.LEAVE_DAYS_THREASHOLD);
			}
		}

		/* now check all future leaves */
		Map<Integer, EmployeeLeave> futureLeavesIndex = new HashMap<Integer, EmployeeLeave>();
		for (EmployeeLeave futureLeave : futureLeaves) {
			if (futureLeave.getEffectiveNumberOfDays() != null
					&& futureLeave.getEffectiveNumberOfDays() > TeachingHoursCDRManagement.LEAVE_DAYS_THREASHOLD) {
				Employee employeeWithLeave = futureLeave.getEmployee();
				if (employeeWithAccountedLeaves.containsKey(employeeWithLeave
						.getId())) {
					info("[EmployeeCRDCalculation] : ignoring future leave #0 because for the employee #1 we have already an accounted leave",
							futureLeave, employeeWithLeave);
				} else {
					/*
					 * future leave is exceeded required duration and it affects
					 * an employee for which we have not accounted a leave so
					 * far
					 */
					leavesToProcess.add(futureLeave);
					futureLeavesIndex.put(futureLeave.getId(), futureLeave);
				}
			} else {
				info("[EmployeeCRDCalculation] : ignoring future leave #0 because it's duration does not exceeds #1 days",
						futureLeave, TeachingHoursCDRManagement.LEAVE_DAYS_THREASHOLD);
			}
		}

		/* no process the leaves */

		for (EmployeeLeave leave : leavesToProcess) {
			Boolean generatesCDR = leave.getGeneratesCDRs();
			boolean isFutureLeave = (futureLeavesIndex.containsKey(leave
					.getId()));
			Employee employeeWithLeave = leave.getEmployee();
			/*
			 * since we are about to include these leave in our computation, add
			 * it to the index for future reference
			 */
			employeeWithAccountedLeaves.put(employeeWithLeave.getId(),
					employeeWithLeave);
			/* fix the common leave message */
			StringBuffer commonLeaveMessagePattern = new StringBuffer();
			commonLeaveMessagePattern
					.append("Άδεια τύπου '%s' από τις '%s' μέχρι και τις '%s'.");
			if (isFutureLeave) {
				commonLeaveMessagePattern
						.append(" Προσοχή, η άδεια είναι μελλοντική με έναρξη εντός εικοσαημέρου.");
			}
			String msg = String.format(commonLeaveMessagePattern.toString(),
					leave.getEmployeeLeaveType().getDescription(),
					df.format(leave.getEstablished()),
					df.format(leave.getDueTo()));
			/*
			 * for the given employee with a leave search and find in which
			 * units he provides work hour
			 */
			Collection<Object[]> o = getEntityManager()
					.createQuery(
							"SELECT t.unit.id, SUM(t.hours) FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee GROUP BY (t.unit)")
					.setParameter("schoolYear", currentSchoolYear)
					.setParameter("employee", employeeWithLeave)
					.getResultList();
			/* for each unit with possitive work hours create a leave CDR */
			for (Object[] r : o) {
				Long hours = (Long) r[1];
				if (hours.longValue() > 0) {

					/*
					 * 
					 * /* In 'hours' variable we have the total hours provided
					 * by the given employee to the given unit. Since there are
					 * SpecialAssigments, ie employee teaching hours of
					 * different specialization, we cannot just deduct the hours
					 * from the above query.
					 * 
					 * We need to group the provided hours by specialization and
					 * add the corresponding leave CDRs
					 */
					Collection<Object[]> hoursPerSpecialization = getEntityManager()
							.createQuery(
									"SELECT SUM(t.hours), t.specialization.id FROM TeachingHourCDR t WHERE t.schoolYear=:schoolYear AND t.employee=:employee AND t.unit.id=:unit GROUP BY (t.specialization)")
							.setParameter("schoolYear", currentSchoolYear)
							.setParameter("employee", employeeWithLeave)
							.setParameter("unit", r[0]).getResultList();
					/*
					 * For each hours of a given specialization in the given
					 * unit, create one LEAVE cdr. We will by 99% generate only
					 * one CDR, but two when we have special assigments
					 */
					for (Object[] hourPerSpecialization : hoursPerSpecialization) {
						Long _hours = (Long) hourPerSpecialization[0];
						Specialization _specialization = getEntityManager()
								.find(Specialization.class,
										String.valueOf(hourPerSpecialization[1]));
						TeachingHourCDR cdr = new TeachingHourCDR();
						cdr.setCdrType(TeachingHourCDRType.LEAVE);
						cdr.setFutureCDR(isFutureLeave);
						cdr.setComment(msg);
						cdr.setSpecialization(_specialization);
						cdr.setEmployee(employeeWithLeave);
						/* */
						cdr.setHours(new Integer(_hours.intValue() * -1));
						cdr.setLogisticCDR(Boolean.TRUE); /*
														 * this is a logistic
														 * CDR
														 */
						cdr.setSchoolYear(currentSchoolYear);
						cdr.setUnit(getEntityManager().find(Unit.class, r[0]));
						cdr.setLeave(leave);
						entityManager.persist(cdr);
						

					}
				}
			}

		}

		
		long finished = System.currentTimeMillis();

		info("[EmployeeCRDCalculation] : generated CDRs for employee #0 after #1 [ms]", employee,
				(finished - started));

	}

}
