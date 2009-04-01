/**
 * 
 */
package gr.sch.ira.minoas.core;

/**
 * @author slavikos
 *
 */
public abstract class CoreUtils {

	public static final String getSearchPattern(String searchString) {
		return searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
	}

}
