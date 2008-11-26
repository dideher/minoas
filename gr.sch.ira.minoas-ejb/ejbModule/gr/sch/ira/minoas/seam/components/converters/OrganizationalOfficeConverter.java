/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.core.OrganizationalOffice;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.framework.EntityController;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Converter
@Name("organizationalOfficeConverter")
@BypassInterceptors
@Scope(ScopeType.STATELESS)
public class OrganizationalOfficeConverter extends EntityController  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4376345579096455221L;

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {

			return getEntityManager().find(OrganizationalOffice.class, value);
		}
		else
			return null;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof OrganizationalOffice) {
				return ((OrganizationalOffice) value).getId();
			}
			else {
				return value.toString();
			}
		}
		else
			return null;
	}

}
