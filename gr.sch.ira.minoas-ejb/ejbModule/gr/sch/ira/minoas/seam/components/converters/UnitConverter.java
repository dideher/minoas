/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.core.Unit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.faces.Converter;

/**
 * @author slavikos
 * 
 */
@Converter
@Name("unitConverter")
@Transactional
public class UnitConverter extends DatabaseAwareBaseConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return getMinoasDatabase().createNamedQuery(
					Unit.NAMED_QUERY_FIND_UNIT_BY_TITLE).setParameter(
					Unit.QUERY_PARAMETER_UNIT_TITLE, value).getSingleResult();
		} catch (NoResultException nres) {
			return null;
		} catch (Exception ex) {
			warn(
					"failed to convert value '#0' to a valid unit, due to an exception '#1'",
					value, ex.getMessage());
			return null;
		}
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
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
