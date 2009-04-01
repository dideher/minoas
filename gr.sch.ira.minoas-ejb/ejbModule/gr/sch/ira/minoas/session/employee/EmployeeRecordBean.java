/**
 * 
 */
package gr.sch.ira.minoas.session.employee;

import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author slavikos
 *
 */
@Name("employeeRecord")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, IEmployeeRecord.class, IEmployeeAware.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EmployeeRecordBean extends EmployeeAwareSeamComponent implements IEmployeeRecord {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In
	private CoreSearching coreSearching;

	@DataModel("deputyEmployments")
	private Collection<Employment> deputyEmployments;

	@DataModel("employeeSecondments")
	private Collection<Secondment> employeeSecondments;

	@DataModel("regularEmployments")
	private Collection<Employment> regularEmployments;

	@DataModelSelection("deputyEmployments")
	private Employment selectedDeputyEmployment;

	@DataModelSelection("employeeSecondments")
	private Secondment selectedEmployeeSecondment;

	@DataModelSelection("regularEmployments")
	private Employment selectedRegularEmployment;

	@End
	public void endViewEmployeeRecord() {
		info("employee record '#0' conversation ended.", getActiveEmployee());
	}

	protected Collection<Employment> getDeputyEmployments() {
		return deputyEmployments;
	}

	protected Collection<Employment> getRegularEmployments() {
		return regularEmployments;
	}

	protected Collection<Secondment> getSecondments() {
		return employeeSecondments;
	}

	protected Employment getSelectedDeputyEmployment() {
		return selectedDeputyEmployment;
	}

	public Secondment getSelectedEmployeeSecondment() {
		return selectedEmployeeSecondment;
	}

	protected Employment getSelectedRegularEmployment() {
		return selectedRegularEmployment;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeRecord#search()
	 */
	public String search() {
		searchEmployeeSecondments();
		searchEmployeeRegularEmployments();
		return SUCCESS_OUTCOME;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeRecord#searchEmployeeDeputyEmployments()
	 */
	@Factory(value = "deputyEmployments")
	public String searchEmployeeDeputyEmployments() {
		info("searching deputy employments for employee '#0'", getActiveEmployee());
		setDeputyEmployments(coreSearching.getEmployeeEmploymentsOfType(getActiveEmployee(), EmploymentType.DEPUTY));
		return SUCCESS_OUTCOME;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeRecord#searchEmployeeRegularEmployments()
	 */
	@Factory(value = "regularEmployments")
	public String searchEmployeeRegularEmployments() {
		info("searching regular employments for employee '#0'", getActiveEmployee());
		setRegularEmployments(coreSearching.getEmployeeEmploymentsOfType(getActiveEmployee(), EmploymentType.REGULAR));
		return SUCCESS_OUTCOME;
	}

	@Factory(value = "employeeSecondments")
	public String searchEmployeeSecondments() {
		info("searching secondments for employee '#0'", getActiveEmployee());
		setSecondments(coreSearching.getEmployeeSecondments(getActiveEmployee()));
		return SUCCESS_OUTCOME;
	}

	/**
	 * @see gr.sch.ira.minoas.session.employee.IEmployeeRecord#select()
	 */
	public String select() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setDeputyEmployments(Collection<Employment> deputyEmployments) {
		this.deputyEmployments = deputyEmployments;
	}

	protected void setRegularEmployments(Collection<Employment> regularEmployments) {
		this.regularEmployments = regularEmployments;
	}

	protected void setSecondments(Collection<Secondment> secondments) {
		this.employeeSecondments = secondments;
	}

	protected void setSelectedDeputyEmployment(Employment selectedDeputyEmployment) {
		this.selectedDeputyEmployment = selectedDeputyEmployment;
	}

	public void setSelectedEmployeeSecondment(Secondment selectedSecondment) {
		this.selectedEmployeeSecondment = selectedSecondment;
	}

	protected void setSelectedRegularEmployment(Employment selectedRegularEmployment) {
		this.selectedRegularEmployment = selectedRegularEmployment;
	}

}
