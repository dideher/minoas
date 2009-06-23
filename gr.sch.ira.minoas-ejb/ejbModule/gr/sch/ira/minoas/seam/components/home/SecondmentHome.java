package gr.sch.ira.minoas.seam.components.home;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.Secondment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentHome")
public class SecondmentHome extends MinoasEntityHome<Secondment> {

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
		Employment current_employment = null;
		Employee employee = current_employment.getEmployee();
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
		new_employment.setModificationReason(current_employment.getModificationReason());
		getEntityManager().persist(new_employment);
		
		/* get a fresh copy of the current employment */
		getEntityManager().refresh(current_employment);
		
		/* update the current (now old) employment */
		
		current_employment.setModifiedOn(new Date(System.currentTimeMillis()));
		current_employment.setSupersededBy(new_employment);
		current_employment.setActive(Boolean.FALSE);
		current_employment.setTerminated(new_employment.getEstablished());
		
		/* update the employee */
		employee.setCurrentEmployment(new_employment);
		employee.setLastSpecialization(new_employment.getSpecialization());
		employee.setModifiedOn(new Date(System.currentTimeMillis()));
		
		
		return super.update(); 
	  }

	

	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		return "reverted";
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
	@Factory(value="secondment", scope=ScopeType.PAGE)
	public Secondment getInstance() {
		return (Secondment)super.getInstance();
	}

}
