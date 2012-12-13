/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.INamedQueryConstants;
import gr.sch.ira.minoas.model.employement.SecondmentType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Converter
@Name("secondmentTypeConverter")
@BypassInterceptors
@Scope(ScopeType.STATELESS)
public class SecondmentTypeConverter extends DatabaseAwareBaseConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4196332658706303684L;

	/**
	 * 
	 */
	public SecondmentTypeConverter() {
		super();
	}

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			try {
				return getMinoasDatabase().createNamedQuery(
						INamedQueryConstants.NAMED_QUERY_SECONDMENTTYPE_FIND_BY_TITLE).setParameter(
						INamedQueryConstants.QUERY_PARAMETER_SECONDMENT_TITLE, value).getSingleResult();
			} catch (NoResultException nres) {
				return null;
			} catch (Exception ex) {
				warn("failed to convert value '#0' to a valid secondment type, due to an exception '#1'", value, ex
						.getMessage());
				return null;
			}
		} else
			return null;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof SecondmentType) {
				return ((SecondmentType) value).getKey();
			} else {
				return value.toString();
			}
		} else
			return EMPTY_STRING;
	}

}
