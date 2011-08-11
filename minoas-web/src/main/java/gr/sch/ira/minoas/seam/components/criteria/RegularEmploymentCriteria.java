package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Named("regularEmploymentCriteria")
@ConversationScoped
public class RegularEmploymentCriteria extends BaseCriteria  {

	private boolean onlyActive = true;

	@Inject
	private School school;

	@Inject @Named(value = "activeSchoolYear")
	private SchoolYear schoolYear;

	/**
	 * 
	 */
	public RegularEmploymentCriteria() {
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	/**
	 * @return the onlyActive
	 */
	public boolean isOnlyActive() {
		return onlyActive;
	}

	/**
	 * @param onlyActive the onlyActive to set
	 */
	public void setOnlyActive(boolean onlyActive) {
		this.onlyActive = onlyActive;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		System.err.println("******** someone set the school : " + school);
		this.school = school;
	}

	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

}
