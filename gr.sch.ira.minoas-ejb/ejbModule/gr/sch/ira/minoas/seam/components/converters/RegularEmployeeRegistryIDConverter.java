/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.employee.RegularEmployee;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 *
 */
@Converter
@Name("employeeRegistryIDConverter")
@BypassInterceptors
public class RegularEmployeeRegistryIDConverter extends BaseConverter  {



	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value!=null && value instanceof RegularEmployee) {
			return ((RegularEmployee)value).getRegistryID();
		} else return null;
	}

}
