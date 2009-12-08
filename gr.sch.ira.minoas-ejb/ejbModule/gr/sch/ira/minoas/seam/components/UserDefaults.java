/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.core.SchoolYear;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
@Name("userDefaults")
public class UserDefaults {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Out(scope = ScopeType.SESSION, required = false)
	private SchoolYear activeSchoolYear;

	@In
	private CoreSearching coreSearching;

	/**
	 * @return the activeSchoolYear
	 */
	public SchoolYear getActiveSchoolYear() {
		return activeSchoolYear;
	}

	@Observer("org.jboss.seam.security.loginSuccessful")
	public void loginSuccesful() {
		this.activeSchoolYear = coreSearching.getActiveSchoolYear(null);
	}

	/**
	 * @param activeSchoolYear the activeSchoolYear to set
	 */
	public void setActiveSchoolYear(SchoolYear activeSchoolYear) {
		this.activeSchoolYear = activeSchoolYear;
	}
}
