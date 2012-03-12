/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.employee.Employee;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("employeeTypeConverter")
@Converter
@BypassInterceptors
@Scope(ScopeType.STATELESS)
public class EmployeeTypeConverter extends BaseConverter {

	public static final String DEPUTY_EMPLOYEE_TYPE_STR = "Αναπληρωτής";

	public static final String HOURLY_PAID_EMPLOYEE_TYPE_STR = "Ωρομίσθιος";

	public static final String REGULAR_EMPLOYEE_TYPE_STR = "Μόνιμος";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Employee) {
			switch (((Employee) value).getType()) {
			case REGULAR:
				return REGULAR_EMPLOYEE_TYPE_STR;
			case DEPUTY:
				return DEPUTY_EMPLOYEE_TYPE_STR;
			case HOURLYPAID:
				return HOURLY_PAID_EMPLOYEE_TYPE_STR;
			default:
				return null;
			}
		} else
			return null;
	}

}
