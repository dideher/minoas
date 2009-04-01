/**
 * 
 */
package gr.sch.ira.minoas.seam.components.converters;

import gr.sch.ira.minoas.seam.components.BaseSeamComponent;

import javax.faces.convert.Converter;

import org.jboss.seam.Component;

/**
 * @author slavikos
 *
 */
public abstract class BaseConverter extends BaseSeamComponent implements Converter {

	public static final String EMPTY_STRING = new String("");

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	protected Object getSeamComponent(Class componentClass, boolean create) {
		return Component.getInstance(componentClass, create);
	}

	protected Object getSeamComponent(String componentName, boolean create) {
		return Component.getInstance(componentName, create);
	}

}
