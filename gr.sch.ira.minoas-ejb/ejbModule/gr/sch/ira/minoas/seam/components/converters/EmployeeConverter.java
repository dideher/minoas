/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author slavikos
 *
 */
@Name("employeeConverter")
@Transactional
public class EmployeeConverter extends DatabaseAwareBaseConverter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
