package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;
import gr.sch.ira.minoas.model.employement.ServiceAllocationVariation;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.ServiceAllocationHome;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

@Name(value = "employeeServiceAllocationsManagement")
@Scope(ScopeType.CONVERSATION)
public class EmployeeServiceAllocationsManagement extends
		BaseDatabaseAwareSeamComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(required = true, create = true)
	private EmployeeHome employeeHome;

	@In(required = true)
	private ServiceAllocationHome serviceAllocationHome;

	private String serviceUnitHelper;

	@Transactional
	@RaiseEvent("serviceAllocationCreated")
	public String addEmployeeServiceAllocationAction() {
		if (employeeHome.isManaged()) {
			Employee employee = employeeHome.getInstance();
			Employment currentEmployment = employee.getCurrentEmployment();
			Date today = DateUtils
					.truncate(new Date(System.currentTimeMillis()),
							Calendar.DAY_OF_MONTH);
			ServiceAllocation serviceAllocation = serviceAllocationHome
					.getInstance();

			/* handle secondments */
			Collection<Secondment> secondments = getCoreSearching()
					.getEmployeeSecondmentWithinPeriod(getEntityManager(),
							employee, serviceAllocation.getEstablished(),
							serviceAllocation.getDueTo());
			if (secondments != null && secondments.size() > 0) {
				/*
				 * there is an secondment within that period, adjust the source
				 * unit and inform the user
				 */
				Secondment s = secondments.iterator().next();
				serviceAllocation.setSourceUnit(s.getTargetUnit());
				getFacesMessages()
						.add(Severity.WARN,
								String.format(
										"Ο εκπαιδευτικός την περίοδο της θητείας θα τελεί σε απόσπαση στην μονάδα '%s'. Αλλάξε ανάλογα η μονάδα 'οργανικής' της Θητείας",
										s));
			}

			/* since source unit has been possibly adjusted perform validation */
			if (!validateServiceAllocation(serviceAllocation, true)) {
				return ACTION_OUTCOME_FAILURE;
			}

			serviceAllocation.setInsertedBy(getPrincipal());
			serviceAllocation.setActive(serviceAllocationShouldBeActivated(
					serviceAllocation, today));
			serviceAllocation.setEmployee(employee);
			if (currentEmployment != null) {
				currentEmployment.setServiceAllocation(serviceAllocation);
				serviceAllocation.setAffectedEmployment(currentEmployment);
			}

			serviceAllocationHome.persist();
			getEntityManager().flush();
			return ACTION_OUTCOME_SUCCESS;

		} else {

			facesMessages.add(Severity.ERROR, "employee home #0 not managed.",
					employeeHome);
			return ACTION_OUTCOME_FAILURE;

		}

	}

	/**
	 * Called from the UI when the user decides to cancel the creation of
	 * modification of a service allocation
	 * 
	 * @return
	 */
	public String cancelServiceAllocationModificationAction() {
		if (serviceAllocationHome.isManaged()) {
			serviceAllocationHome.revert();
		} else {
			serviceAllocationHome.clearInstance();
		}
		return ACTION_OUTCOME_SUCCESS;
	}

	@Transactional
	@RaiseEvent("serviceAllocationDeleted")
	public String deleteEmployeeServiceAllocationAction() {

		if (employeeHome.isManaged() && serviceAllocationHome.isManaged()) {
			Employee employee = getEntityManager().merge(
					employeeHome.getInstance());
			ServiceAllocation serviceAllocation = serviceAllocationHome
					.getInstance();
			info("deleting employee #0 service allocation #1", employee,
					serviceAllocation);

			serviceAllocation.setActive(Boolean.FALSE);
			serviceAllocation.setDeleted(Boolean.TRUE);
			serviceAllocation.setDeletedOn(new Date());
			serviceAllocation.setDeletedBy(getPrincipal());
			serviceAllocationHome.update();
			info("service allocation #0 for employee #1 has been deleted",
					serviceAllocation, employee);
			getEntityManager().flush();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages
					.add(Severity.ERROR,
							"employee home #0 or service allocation home #1 not managed.",
							employeeHome, serviceAllocationHome);
			return ACTION_OUTCOME_FAILURE;
		}
	}

	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	public String getServiceUnitHelper() {
		return serviceUnitHelper;
	}

	/*
	 * this method is being called from the page containing a list of disposals
	 * and returns the CSS class that should be used by the disposal row
	 */
	public String getTableCellClassForDisposal(Disposal disposal) {
		if (disposal.isFuture()) {
			return "rich-table-future-disposal";
		} else if (disposal.isCurrent()) {
			return "rich-table-current-disposal";
		} else if (disposal.isPast()) {
			return "rich-table-past-disposal";
		} else
			return "";
	}

	@Transactional
	@RaiseEvent("serviceAllocationModified")
	public String modifyServiceAllocation() {
		if (serviceAllocationHome.isManaged()) {
			ServiceAllocation current_serviceAllocation = serviceAllocationHome
					.getInstance();
			Employee employee = employeeHome.getInstance();
			Date today = DateUtils
					.truncate(new Date(System.currentTimeMillis()),
							Calendar.DAY_OF_MONTH);

			if (!validateServiceAllocation(current_serviceAllocation, true)) {

				return ACTION_OUTCOME_FAILURE;
			}

			current_serviceAllocation
					.setActive(serviceAllocationShouldBeActivated(
							current_serviceAllocation, today));
			serviceAllocationHome.update();
			info("service allocation #0 for employee #1 has been updated",
					current_serviceAllocation, employee);
			getEntityManager().flush();
			return ACTION_OUTCOME_SUCCESS;
		} else {
			facesMessages.add(Severity.ERROR,
					"service allocation home #0 is not managed.",
					serviceAllocationHome);
			return ACTION_OUTCOME_FAILURE;
		}

	}

	/*
	 * this method is called when the user clicks the
	 * "add new service allocation"
	 */
	public void prepareForNewServiceAllocation() {
		serviceAllocationHome.clearInstance();
		Employee employee = employeeHome.getInstance();
		Employment currentEmployment = employee.getCurrentEmployment();
		ServiceAllocation serviceAllocation = serviceAllocationHome
				.getInstance();

		SchoolYear currentSchoolYear = getCoreSearching().getActiveSchoolYear(
				getEntityManager());
		Date today = DateUtils.truncate(new Date(System.currentTimeMillis()),
				Calendar.DAY_OF_MONTH);

		Date dueTo = DateUtils.truncate(
				currentSchoolYear.getTeachingSchoolYearStop(),
				Calendar.DAY_OF_MONTH);

		serviceAllocation.setActive(Boolean.TRUE);
		serviceAllocation.setEstablished(today);
		serviceAllocation.setDueTo(dueTo);
		serviceAllocation.setEmployee(employee);
		serviceAllocation
				.setServiceAllocationVariationType(ServiceAllocationVariation.WITH_TEACHING);

		if (currentEmployment != null) {
			serviceAllocation.setSourceUnit(currentEmployment.getSchool());
		} else {
			serviceAllocation.setSourceUnit(employee.getCurrentPYSDE()
					.getRepresentedByUnit());
		}

		/* reset service unit helper */
		serviceUnitHelper = null;
	}

	public void prepareForServiceAllocationModification() {
		ServiceAllocation serviceAllocation = serviceAllocationHome
				.getInstance();
		serviceUnitHelper = serviceAllocation.getServiceUnit() != null ? serviceAllocation
				.getServiceUnit().getTitle() : null;
	}

	/**
	 * Checks if a service allocation should be set active in regards to the
	 * reference date.
	 * 
	 * @param serviceAllocation
	 * @param referenceDate
	 * @return
	 */
	protected boolean serviceAllocationShouldBeActivated(
			ServiceAllocation serviceAllocation, Date referenceDate) {
		Date established = DateUtils.truncate(
				serviceAllocation.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(serviceAllocation.getDueTo(),
				Calendar.DAY_OF_MONTH);
		Date today = DateUtils.truncate(referenceDate, Calendar.DAY_OF_MONTH);
		if ((established.before(today) || established.equals(today))
				&& (dueTo.after(today) || dueTo.equals(today))) {
			return true;
		} else
			return false;
	}

	/**
	 * @param employeeHome
	 *            the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}

	public void setServiceUnitHelper(String serviceUnitHelper) {
		this.serviceUnitHelper = serviceUnitHelper;
	}

	protected boolean validateServiceAllocation(
			ServiceAllocation serviceAllocation, boolean addMessages) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date established = DateUtils.truncate(
				serviceAllocation.getEstablished(), Calendar.DAY_OF_MONTH);
		Date dueTo = DateUtils.truncate(serviceAllocation.getDueTo(),
				Calendar.DAY_OF_MONTH);
		Employee employee = employeeHome.getInstance();
		Integer hoursOnSourceUnit = serviceAllocation
				.getWorkingHoursOnRegularPosition() != null ? serviceAllocation
				.getWorkingHoursOnRegularPosition() : 0;
		Integer hoursOnServiceUnit = serviceAllocation
				.getWorkingHoursOnServicingPosition() != null ? serviceAllocation
				.getWorkingHoursOnServicingPosition() : 0;

		if (!employee.getActive()) {
			if (addMessages)
				facesMessages.add(Severity.ERROR,
						"O εκπαιδευτικός δεν είναι ενεργός.");
			return false;
		}

		// check if service unit has been specified
		if (serviceAllocation.getServiceUnit() == null) {
			if (addMessages)
				facesMessages.add(Severity.ERROR,
						"Προσοχή, δεν έχετε επιλέξει μονάδα θητείας.");
			return false;
		}

		// check if source unit has been specified
		if (serviceAllocation.getSourceUnit() == null) {
			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Προσοχή, δεν έχει ορισθεί η μονάδα οργανικής / τρέχουσας θέσης.");
			return false;
		}

		/* check hours */
		if (hoursOnSourceUnit < 0 || hoursOnServiceUnit < 0) {
			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Το ωράριο πρέπει να είναι σε κάθε περίπτωση θετικός αριθμός");
			return false;
		}

		if (employee.getCurrentEmployment() != null) {
			if (hoursOnServiceUnit + hoursOnSourceUnit > employee
					.getCurrentEmployment().getMandatoryWorkingHours()) {
				if (addMessages)
					facesMessages
							.add(Severity.ERROR,
									"Το συνολικό ωράριο δεν μπορούν να υπερβαίνει το υποχρεωτικό ωράριο του εκπαιδευτικού.");
				return false;
			}
		}

		/*
		 * checks that have to be performed when the source unit is the same as
		 * the target unit
		 */
		if (serviceAllocation.getServiceUnit().getId()
				.equals(serviceAllocation.getSourceUnit().getId())) {
			if (hoursOnServiceUnit > 0 && hoursOnSourceUnit > 0) {
				if (addMessages)
					facesMessages
							.add(Severity.ERROR,
									"Όταν η μονάδα θητείας είναι ίδια με την μονάδα οργανικής, δεν μπορείτε να συμπληρώσετε και τα δυο ωράρια.");
				return false;
			}

		}

		//
		// /* check if the disposal target unit is the employee's current school
		// */
		// if
		// (disposal.getSourceUnit().getId().equals(disposal.getDisposalUnit().getId()))
		// {
		// if (addMessages)
		// facesMessages.add(Severity.ERROR,
		// "Η τρέχουσα οργανική του εκπαιδευτικού είναι η ίδια με την μονάδα διάθεσης.");
		// return false;
		// }
		//
		//
		/* check if the dates are correct */
		if (established.after(dueTo)) {

			if (addMessages)
				facesMessages
						.add(Severity.ERROR,
								"Η ημερομηνία λήξης της θητείας πρέπει να είναι μεταγενέστερη της έναρξης. Κάνε ένα διάλειμα για καφέ !");
			return false;
		}
		//
		/*
		 * check if the employee has a disposal that conflicts with the new
		 * service allocation
		 */
		Collection<Disposal> conflictingDisposals = getCoreSearching()
				.getEmployeeDisposalWithingPeriod(getEntityManager(),
						serviceAllocation.getEmployee(), established, dueTo);
		if (conflictingDisposals.size() > 0) {
			if (addMessages) {
				Disposal d = conflictingDisposals.iterator().next();
				String dunit = d.getDisposalUnit().getTitle();
				String dfrom = df.format(d.getEstablished());
				String dto = df.format(d.getDueTo());

				facesMessages
						.add(Severity.ERROR,
								String.format(
										"Η θητεία δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη διάθεση στην μονάδα '%s' από '%s' εως '%s'.",
										dunit, dfrom, dto));
				facesMessages
						.add(Severity.INFO,
								"Εαν η θητεία πρέπει να καταχωρηθεί, ακυρώστε πρώτα την διάθεση.");
			}
			return false;
		}
		//
		// /* check if the employee has a service allocation that conflicts with
		// the new secondment */
		// Collection<ServiceAllocation> conflictingServiceAllocations =
		// getCoreSearching()
		// .getEmployeeServiceAllocationWithinPeriod(getEntityManager(),
		// disposal.getEmployee(), established,
		// dueTo);
		// if (conflictingServiceAllocations.size() > 0) {
		// if (addMessages) {
		// ServiceAllocation d =
		// conflictingServiceAllocations.iterator().next();
		// String dunit = d.getServiceUnit().getTitle();
		// String dfrom = df.format(d.getEstablished());
		// String dto = df.format(d.getDueTo());
		//
		// facesMessages
		// .add(Severity.ERROR,
		// String.format(
		// "Η διάθεση δεν μπορεί να καταχωρηθεί γίατι για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη θητεία στην μονάδα '%s' από '%s' εως '%s'.",
		// dunit, dfrom, dto));
		// facesMessages.add(Severity.INFO,
		// "Εαν η διάθεση πρέπει να καταχωρηθεί, ακυρώστε πρώτα την θητεία.");
		// }
		// return false;
		// }
		//
		// find conflicting service allocations
		Collection<ServiceAllocation> conflictingServiceAlloctions = getCoreSearching()
				.getEmployeeServiceAllocationWithinPeriod(getEntityManager(),
						employee, established, dueTo);
		for (ServiceAllocation current_serviceAllocation : conflictingServiceAlloctions) {
			if (current_serviceAllocation.getId().equals(
					serviceAllocation.getId()))
				continue;
			else {
				if (addMessages)
					facesMessages
							.add(Severity.ERROR,
									String.format(
											"Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη θητεία από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει επικάλυψη με την θητεία από '%s' εως και '%s' στην μονάδα '%s' που προσπαθείτε να εισάγετε.",
											df.format(current_serviceAllocation
													.getEstablished()),
											df.format(current_serviceAllocation
													.getDueTo()),
											current_serviceAllocation
													.getServiceUnit()
													.getTitle(), df
													.format(serviceAllocation
															.getEstablished()),
											df.format(serviceAllocation
													.getDueTo()),
											serviceAllocation.getServiceUnit()
													.getTitle()));
				return false;
			}

		}
		// Collection<ServiceAllocation> serviceAllocations =
		// getCoreSearching().getEmployeeServiceAllocation(entityManager,
		// employeeHome.getInstance());
		// for (ServiceAllocation current_serviceAllocation :
		// serviceAllocations) {
		// if
		// (current_serviceAllocation.getId().equals(serviceAllocation.getId()))
		// continue;
		// Date current_established =
		// DateUtils.truncate(current_serviceAllocation.getEstablished(),
		// Calendar.DAY_OF_MONTH);
		// Date current_dueTo =
		// DateUtils.truncate(current_serviceAllocation.getDueTo(),
		// Calendar.DAY_OF_MONTH);
		// if (DateUtils.isSameDay(established, current_established) ||
		// DateUtils.isSameDay(dueTo, current_dueTo)) {
		// if (addMessages)
		// facesMessages
		// .add(Severity.ERROR,
		// String.format(
		// "Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη θητεία από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει τις ίδιες ημ/νιες με αυτή που προσπαθείτε να εισάγετε.",
		// df.format(current_serviceAllocation.getEstablished()), df
		// .format(current_serviceAllocation.getDueTo()),
		// current_serviceAllocation
		// .getServiceUnit().getTitle()));
		// return false;
		// }
		//
		// if (DateUtils.isSameDay(established, current_dueTo)) {
		//
		// if (addMessages)
		// facesMessages
		// .add(Severity.ERROR,
		// "Η ημ/νία έναρξης της θητείας πρέπει να είναι μεταγενέστερη της λήξης της προηγούμενης θητείας.");
		// return false;
		// }
		//
		// if ((established.before(current_established) &&
		// dueTo.after(current_established)) ||
		// (established.after(current_established) &&
		// dueTo.before(current_dueTo)) ||
		// (established.before(current_dueTo) && dueTo.after(current_dueTo))) {
		// if (addMessages)
		// facesMessages
		// .add(Severity.ERROR,
		// String.format(
		// "Για τον εκπαιδευτικό υπάρχει ήδη καταχωρημένη απόσπαση από '%s' εώς και '%s' στην μονάδα '%s' η οποία έχει επικάλυψη με την απόσπαση από '%s' εως και '%s' στην μονάδα '%s' που προσπαθείτε να εισάγετε.",
		// df.format(current_serviceAllocation.getEstablished()), df
		// .format(current_serviceAllocation.getDueTo()),
		// current_serviceAllocation
		// .getServiceUnit().getTitle(),
		// df.format(serviceAllocation.getEstablished()),
		// df.format(serviceAllocation.getDueTo()),
		// serviceAllocation.getServiceUnit().getTitle()));
		// return false;
		// }
		//
		// }
		return true;

	}

}
