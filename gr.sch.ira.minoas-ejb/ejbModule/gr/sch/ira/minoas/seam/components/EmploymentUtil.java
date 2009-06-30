/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.employement.Employment;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author slavikos
 * 
 */
@Name("employmentUtil")
@Scope(ScopeType.EVENT)
public class EmploymentUtil extends BaseSeamComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean hasSecondment(Employment employment) {
		if (employment != null) {
			return employment.getSecondment() != null && employment.getSecondment().getActive();
		} else
			return false;
	}

}
