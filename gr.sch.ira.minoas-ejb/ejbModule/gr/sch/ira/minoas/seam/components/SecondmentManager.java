
package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.SecondmentHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Scope(ScopeType.CONVERSATION)
public class SecondmentManager {
	
	@In(create=true)
	protected SecondmentHome secondmentHome;
	
	@In(create=true)
	protected EmployeeHome employeeHome;

	public void updateNewSecondment() {
		
	}
}
