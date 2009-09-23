package gr.sch.ira.minoas.seam.components.home;

import java.util.Calendar;
import java.util.Date;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Disposal;
import gr.sch.ira.minoas.model.employement.DisposalTargetType;
import gr.sch.ira.minoas.model.employement.DisposalType;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.Secondment;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("disposalHome")
@Scope(ScopeType.CONVERSATION)
public class DisposalHome extends MinoasEntityHome<Disposal> {

	@Transactional
	public String cancel() {
		Disposal current_disposal = getInstance();
		current_disposal.setActive(Boolean.FALSE);
		super.update();
		info("principal '#0' canceled employee #1 current leave #1.", getPrincipalName(), current_disposal
				.getEmployee(), current_disposal);
		return "updated";
	}

	@Transactional
	public String revert() {
		info("principal #0 is reverting updates to disposal #1", getPrincipalName(), getInstance());
		getEntityManager().refresh(getInstance());
		return "reverted";
	}

	@In(create = true)
	private EmploymentHome employmentHome;

	@In(create = true)
	private EmployeeHome employeeHome;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value = "disposal", scope = ScopeType.PAGE)
	public Disposal getInstance() {
		return (Disposal) super.getInstance();

	}

	@Transactional
	public boolean wire() {
		return true;
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#persist()
	 */
	@Override
	@Transactional
	public String persist() {
		Disposal newDisposal = getInstance();
		/* wire things */
		Employee employee = employeeHome.getInstance();
		newDisposal.setEmployee(employee);
		newDisposal.setSchoolYear(getCoreSearching().getActiveSchoolYear(getEntityManager()));

		/*
		 *  so, we need to determine if the employee has a secondment on not
		 */
		Secondment possibleSecondment = getCoreSearching().getEmployeeActiveSecondment(getEntityManager(), employee,
				newDisposal.getEstablished());
		if (possibleSecondment != null) {
			facesMessages
					.add(
							Severity.INFO,
							"Ο εκπαιδευτικός είναι αποσπασμένος στην μονάδα #0 κατά την σχετική περίοδο της διάθεσης, οπότε η διαθέση που μόλις καταχωρήσατε θεωρείται πως επηρεάζει την σχολική μονάδα της απόσπασης.",
							possibleSecondment.getTargetUnit().getTitle());

			newDisposal.setAffectedSecondment(possibleSecondment);
		} else {
			newDisposal.setAffectedEmployment(employee.getCurrentEmployment());
		}
		newDisposal.setInsertedBy(getPrincipal());
		//Employee employee = getInstance();
		//employee.setInsertedBy(getPrincipal());
		return super.persist();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.home.MinoasEntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		return super.update();
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		Disposal new_instance = new Disposal();
		new_instance.setActive(Boolean.TRUE);
		new_instance.setDueTo(getCoreSearching().getActiveSchoolYear(getEntityManager()).getTeachingSchoolYearStop());
		new_instance.setType(DisposalType.PARTIAL);
		new_instance.setTargetType(DisposalTargetType.TO_SCHOOL);
		return new_instance;
	}

	@Transactional
	public void prepareForNewDisposal() {
		this.clearInstance();
		employmentHome.clearInstance();
	}

}
