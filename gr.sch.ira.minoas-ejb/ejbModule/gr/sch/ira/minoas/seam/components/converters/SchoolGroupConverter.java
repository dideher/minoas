/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.model.core.SchoolGroup;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

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
@Name("schoolGroupConverter")
@BypassInterceptors
@Scope(ScopeType.STATELESS)
public class SchoolGroupConverter extends DatabaseAwareBaseConverter {

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
			try {
				return getMinoasDatabase().createQuery("FROM SchoolGroup s WHERE s.title=:value").setParameter(
						"value", value).getSingleResult();
			} catch (NoResultException nre) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof SchoolGroup) {
				return ((SchoolGroup) value).getTitle();
			} else {
				return value.toString();
			}
		} else
			return null;
	}

}
