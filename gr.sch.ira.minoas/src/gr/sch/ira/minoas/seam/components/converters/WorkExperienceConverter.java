package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.core.CoreUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Converter
@Name("workExperienceConverter")
@BypassInterceptors
public class WorkExperienceConverter extends BaseConverter {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public WorkExperienceConverter() {
    }

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return null;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if(arg2 instanceof Integer) {
            return CoreUtils.getNoOfDaysInYear_Month_DayFormat((Integer)arg2);
        } else return null;
    }

    

}
