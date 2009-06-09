
package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SchoolYear;
import gr.sch.ira.minoas.model.employement.EmploymentType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("regularEmploymentCriteria")
@Scope(ScopeType.CONVERSATION)
public class RegularEmploymentCriteria {

	@In
	private School school;
	
	private boolean onlyActive = true;
	
	@In(value="activeSchoolYear")
	private SchoolYear schoolYear;
	/**
	 * @return the schoolYear
	 */
	public SchoolYear getSchoolYear() {
		return schoolYear;
	}
	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
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
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		System.err.println("******** someone set the school : "+school);
		this.school = school;
	}

}
