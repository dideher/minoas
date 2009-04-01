package gr.sch.ira.minoas.session.school;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.Unit;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Restrict;

@Name("schoolSearch")
@Scope(ScopeType.CONVERSATION)
@Restrict("#{identity.loggedIn}")
@Stateful
@Local( { IBaseStatefulSeamComponent.class, ISchoolSearch.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SchoolSearchBean extends BaseStatefulSeamComponentImpl implements ISchoolSearch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@In(value = "activeSchool", required = false)
	@Out(value = "activeSchool", required = false, scope = ScopeType.CONVERSATION)
	private Unit activeSchool;

	@In(value = "coreSearching")
	private CoreSearching coreSearching;

	@DataModel(value = "schools", scope = ScopeType.PAGE)
	private Collection<School> schools;

	private String searchString;

	@DataModelSelection("schools")
	private School selectedSchool;

	/**
	 * @return the activeSchool
	 */
	public Unit getActiveSchool() {
		return activeSchool;
	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return this.searchString;
	}

	/**
	 * @see gr.sch.ira.minoas.session.school.ISchoolSearch#search()
	 */
	@Factory(value = "schools")
	public String search() {
		schools = coreSearching.searchShools(getSearchString());
		return SUCCESS_OUTCOME;
	}

	public String selectSchool() {
		if (selectedSchool != null) {
			info("school #0 selected successfully.", selectedSchool);
			setActiveSchool(selectedSchool);
		}
		return SCHOOL_SELECTED_OUTCOME;
	}

	/**
	 * @param activeSchool the activeSchool to set
	 */
	public void setActiveSchool(Unit activeSchool) {
		this.activeSchool = activeSchool;
	}

	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
