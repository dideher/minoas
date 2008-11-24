/**
 * 
 */
package gr.sch.ira.minoas.session.school;

import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("schoolRecord")
@Stateful
@Restrict("#{identity.loggedIn}")
@Local( { IBaseStatefulSeamComponent.class, ISchoolRecord.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchoolRecordBean extends BaseStatefulSeamComponentImpl implements ISchoolRecord {

	
	@Out(required=false, scope=ScopeType.CONVERSATION)
	private Employee activeEmployee;

	public String reset() {
		setActiveEmployee(null);
		setActiveEmployment(null);
		setSchoolDeputyEmployments(null);
		setSchoolRegularEmployments(null);
		setSchoolSecondments(null);
		setSelectedSchoolDeputyEmployment(null);
		setSelectedSchoolRegularEmployment(null);
		setSelectedSchoolSecondment(null);
		info("succesfull reset - component is reusable.");
		return SUCCESS_OUTCOME;
	}

	@Out(required=false, scope=ScopeType.CONVERSATION)
	private Employment activeEmployment;

	@In(value = "activeSchool", required = false)
	@Out(value = "activeSchool", required = false, scope = ScopeType.CONVERSATION)
	private Unit activeSchool;
	
	@In(value = "coreSearching")
	private CoreSearching coreSearching;

	@DataModel(value="schoolDeputyEmployments", scope = ScopeType.PAGE)
	private Collection<Employment> schoolDeputyEmployments;
	
	@DataModel(value="schoolRegularEmployments", scope = ScopeType.PAGE)
	private Collection<Employment> schoolRegularEmployments;

	@DataModel(value="schoolSecondments", scope = ScopeType.PAGE)
	private Collection<Secondment> schoolSecondments;
	
	@DataModelSelection(value="schoolDeputyEmployments")
	private Employment selectedSchoolDeputyEmployment;
	
	@DataModelSelection(value="schoolRegularEmployments")
	private Employment selectedSchoolRegularEmployment;
	
	@DataModelSelection(value="schoolSecondments")
	private Secondment selectedSchoolSecondment;

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#create()
	 */
	@Create
	@Override
	public void create() {
		super.create();
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#destroy()
	 */
	@Remove
	@Destroy
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@End
	public void end() {
		info("school record '#0' conversation has ended.", getActiveSchool());
	}

	public Employee getActiveEmployee() {
		return activeEmployee;
	}

	public Employment getActiveEmployment() {
		return activeEmployment;
	}

	/**
	 * @return the activeSchool
	 */
	public Unit getActiveSchool() {
		return activeSchool;
	}

	/**
	 * @return the schoolDeputyEmployments
	 */
	public Collection<Employment> getSchoolDeputyEmployments() {
		return schoolDeputyEmployments;
	}

	/**
	 * @return the schoolRegularEmployments
	 */
	public Collection<Employment> getSchoolRegularEmployments() {
		return schoolRegularEmployments;
	}

	/**
	 * @return the schoolSecondments
	 */
	public Collection<Secondment> getSchoolSecondments() {
		return schoolSecondments;
	}

	public Employment getSelectedSchoolDeputyEmployment() {
		return selectedSchoolDeputyEmployment;
	}

	public Employment getSelectedSchoolRegularEmployment() {
		return selectedSchoolRegularEmployment;
	}

	public Secondment getSelectedSchoolSecondment() {
		return selectedSchoolSecondment;
	}

	/**
	 * @see gr.sch.ira.minoas.session.school.ISchoolRecord#hasActiveSchool()
	 */
	public boolean hasActiveSchool() {
		return activeSchool != null;
	}

	@Factory(value = "schoolDeputyEmployments")
	public void searchDeputyEmployments() {
		setSchoolDeputyEmployments(coreSearching.getSchoolEmploymentsOfType(coreSearching.getActiveSchoolYear(),
				getActiveSchool(), EmploymentType.DEPUTY));
	}

	@Factory(value = "schoolRegularEmployments")
	public void searchRegularEmployments() {
		setSchoolRegularEmployments(coreSearching.getSchoolEmploymentsOfType(coreSearching.getActiveSchoolYear(),
				getActiveSchool(), EmploymentType.REGULAR));
	}

	/**
	 * @see gr.sch.ira.minoas.session.school.ISchoolRecord#searchSchoolSecondments()
	 */
	@Factory(value="schoolSecondments")
	public void searchSchoolSecondments() {
		setSchoolSecondments(coreSearching.getUnitSecondments(getActiveSchoolYear(), getActiveSchool()));
	}

	public String selectDeputyEmployment() {
		if(getSelectedSchoolDeputyEmployment()!=null) {
			setActiveEmployment(getMinoasDatabase().merge(getSelectedSchoolDeputyEmployment()));
			setActiveEmployee(getMinoasDatabase().merge(getSelectedSchoolDeputyEmployment().getEmployee()));
			info("selected deputy employment '#0'.", getSelectedSchoolDeputyEmployment());
			return FAILURE_OUTCOME;
		}
		return SUCCESS_OUTCOME;
	}

	public String selectRegularEmployment() {
		if(getSelectedSchoolRegularEmployment()!=null) {
			setActiveEmployment(getMinoasDatabase().merge(getSelectedSchoolRegularEmployment()));
			setActiveEmployee(getMinoasDatabase().merge(getSelectedSchoolRegularEmployment().getEmployee()));
			info("selected regular employment '#0'.", getSelectedSchoolRegularEmployment());
			return FAILURE_OUTCOME;
		}
		return SUCCESS_OUTCOME;
	}

	public String selectSecondment() {
		if(getSelectedSchoolSecondment()!=null) {
			setActiveEmployee(getMinoasDatabase().merge(getSelectedSchoolSecondment().getEmployee()));
			info("selected secondment  '#0'.", getSelectedSchoolSecondment());
			return SUCCESS_OUTCOME;
		}
		return FAILURE_OUTCOME;
	}

	public void setActiveEmployee(Employee activeEmployee) {
		this.activeEmployee = activeEmployee;
	}

	public void setActiveEmployment(Employment activeEmployment) {
		this.activeEmployment = activeEmployment;
	}

	/**
	 * @param activeSchool the activeSchool to set
	 */
	public void setActiveSchool(Unit activeSchool) {
		this.activeSchool = activeSchool;
	}

	/**
	 * @param schoolDeputyEmployments the schoolDeputyEmployments to set
	 */
	public void setSchoolDeputyEmployments(Collection<Employment> schoolDeputyEmployments) {
		this.schoolDeputyEmployments = schoolDeputyEmployments;
	}

	/**
	 * @param schoolRegularEmployments the schoolRegularEmployments to set
	 */
	public void setSchoolRegularEmployments(Collection<Employment> schoolRegularEmployments) {
		this.schoolRegularEmployments = schoolRegularEmployments;
	}

	/**
	 * @param schoolSecondments the schoolSecondments to set
	 */
	public void setSchoolSecondments(Collection<Secondment> schoolSecondments) {
		this.schoolSecondments = schoolSecondments;
	}

	public void setSelectedSchoolDeputyEmployment(
			Employment selectedSchoolDeputyEmployment) {
		this.selectedSchoolDeputyEmployment = selectedSchoolDeputyEmployment;
	}

	public void setSelectedSchoolRegularEmployment(
			Employment selectedSchoolRegularEmployment) {
		this.selectedSchoolRegularEmployment = selectedSchoolRegularEmployment;
	}

	public void setSelectedSchoolSecondment(Secondment selectedSchoolSecondment) {
		this.selectedSchoolSecondment = selectedSchoolSecondment;
	}

}
