package gr.sch.ira.minoas.seam.components.converters;

import javax.faces.component.UIComponent;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import javax.faces.context.FacesContext;

@Name("teachingLanguageConverter")
@Converter
@BypassInterceptors
public class TeachingLanguageConverter extends DatabaseAwareBaseConverter {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
