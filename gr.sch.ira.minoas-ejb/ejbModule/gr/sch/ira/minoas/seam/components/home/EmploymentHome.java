package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employement.Employment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("employmentHome")
public class EmploymentHome extends MinoasEntityHome<Employment> {

	/**
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Transactional
	public String update() {
		/* Employment Update is special. We create a new employment and set the
		 * current employment as parent.
		 */
		joinTransaction();
		Employment current_employment = getInstance();
		Employment new_employment = new Employment();
		
		/* clone the employment */
		new_employment.setEmployee(current_employment.getEmployee());
		new_employment.setActive(Boolean.TRUE);
		new_employment.setEstablished(current_employment.getEstablished());
		new_employment.setFinalWorkingHours(current_employment.getFinalWorkingHours());
		new_employment.setMandatoryWorkingHours(current_employment.getMandatoryWorkingHours());
		new_employment.setSchool(current_employment.getSchool());
		new_employment.setSchoolYear(current_employment.getSchoolYear());
		new_employment.setInsertedOn(new Date(System.currentTimeMillis()));
		new_employment.setSpecialization(current_employment.getSpecialization());
		new_employment.setType(current_employment.getType());
		
		getEntityManager().persist(new_employment);
		
		getEntityManager().refresh(current_employment);
		current_employment.setModifiedOn(new Date(System.currentTimeMillis()));
		current_employment.setSupersededBy(new_employment);
		current_employment.setActive(Boolean.FALSE);
		getEntityManager().flush();
		
		 updatedMessage();
	      raiseAfterTransactionSuccessEvent();
	      return "updated";
	}

	/**
	 * @see org.jboss.seam.framework.Home#setInstance(java.lang.Object)
	 */
	@Override
	public void setInstance(Object instance) {
		super.setInstance(instance);
		System.err.println("************************************************ SET INSTANCE");
	}

	/**
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Object createInstance() {
		return super.createInstance();
	}

	/**
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	@Factory(value="employment", scope=ScopeType.PAGE)
	public Employment getInstance() {
		return (Employment)super.getInstance();
	}

}
