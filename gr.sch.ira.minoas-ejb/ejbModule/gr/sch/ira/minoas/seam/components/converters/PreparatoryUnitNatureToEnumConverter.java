package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNature;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;
import gr.sch.ira.minoas.session.persistent.IPreparatoryUnitNatureDAO;
import gr.sch.ira.minoas.session.persistent.PreparatoryUnitNatureDAOImpl;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.PrePersist;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("preparatoryUnitNatureToEnumConverter")
@BypassInterceptors
@Converter
@Scope(ScopeType.STATELESS)
public class PreparatoryUnitNatureToEnumConverter extends BaseConverter {

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		IPreparatoryUnitNatureDAO preparatoryUnitNatureDAO = (IPreparatoryUnitNatureDAO) getSeamComponent(
				PreparatoryUnitNatureDAOImpl.class, true);
		PreparatoryUnitNature entity = new PreparatoryUnitNature();
		System.err.println("**** getAsObject *** : " + arg2);
		entity.setType(PreparatoryUnitNatureType.valueOf(arg2));
		entity = preparatoryUnitNatureDAO.findByExample(entity);
		if (entity != null) {
			System.err.println(entity.getTitle());
			return entity.getType();
		} else {
			System.err.println("***** NOT FOUND *****");
			return null;
		}
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		IPreparatoryUnitNatureDAO preparatoryUnitNatureDAO = (IPreparatoryUnitNatureDAO) getSeamComponent(
				PreparatoryUnitNatureDAOImpl.class, true);
		System.err.println("**** getAsString *** : " + arg2);
		if (arg2 instanceof PreparatoryUnitNature)
			return ((PreparatoryUnitNature) arg2).getType().toString();
		else if (arg2 instanceof PreparatoryUnitNatureType) {
			PreparatoryUnitNature entity = preparatoryUnitNatureDAO.findByNatureType((PreparatoryUnitNatureType) arg2);
			if (entity != null)
				return entity.getType().toString();
			else
				return null;
		} else
			return null;
	}

}
