/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.core.Unit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * @author slavikos
 * 
 */
@Converter
@Name("unitConverter")
@BypassInterceptors
@Scope(ScopeType.STATELESS)
public class UnitConverter extends DatabaseAwareBaseConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			return getMinoasDatabase().createQuery("SELECT u FROM Unit u WHERE u.title = :title").setParameter("title",
					value).getSingleResult();
		} catch (NoResultException nres) {
			return null;
		} catch (Exception ex) {
			warn("failed to convert value '#0' to a valid unit, due to an exception '#1'", value, ex.getMessage());
			return null;
		}
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof Unit) {
				return ((Unit) value).getTitle();
			} else {
				warn("invalid instance '#0' passed to converter.", value);
				return EMPTY_STRING;
			}
		} else
			return EMPTY_STRING;
	}

}
