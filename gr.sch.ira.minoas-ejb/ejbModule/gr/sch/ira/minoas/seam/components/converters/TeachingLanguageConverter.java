

package gr.sch.ira.minoas.seam.components.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Name("teachingLanguageConverter")
@Converter
@BypassInterceptors
public class TeachingLanguageConverter extends DatabaseAwareBaseConverter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
