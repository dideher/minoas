/*
 * 
 *
 * Copyright (c) 2008 FORTHnet, S.A. All Rights Reserved.
 *
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package gr.sch.ira.minoas.seam.components;


import gr.sch.ira.minoas.model.core.SchoolYear;

import javax.ejb.Remove;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */

public abstract class BaseStatefulSeamComponentImpl extends BaseDatabaseAwareSeamComponent implements IBaseStatefulSeamComponent {

	/**
	 * @return the activeSchoolYear
	 */
	public SchoolYear getActiveSchoolYear() {
		return activeSchoolYear;
	}

	/**
	 * @param activeSchoolYear the activeSchoolYear to set
	 */
	public void setActiveSchoolYear(SchoolYear activeSchoolYear) {
		this.activeSchoolYear = activeSchoolYear;
	}

	public static final String SUCCESS_OUTCOME = "success";

	public static final String FAILURE_OUTCOME = "failure";

	public static final String BEGIN_OUTCOME = "begin";
	
	public static final String END_OUTCOME = "end";
	
	public static final String SCHOOL_SELECTED_OUTCOME ="school-selected";
	
	public static final String EMPLOYEE_SELECTED_OUTCOME ="employee-selected";
	
	public static final String EMPLOYMENT_SELECTED_OUTCOME ="employment-selected";
	
	@In(value="activeSchoolYear", required=false)
	private SchoolYear activeSchoolYear;
	
	/**
	 * @see gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent#create()
	 */
	@Create
	public void create() {
		info("stateful seam component created.");
	}

	/**
	 * @see gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent#destroy()
	 */
	@Destroy
	@Remove
	public void destroy() {
		info("stateful seam component destroyed.");
	}

	

}
