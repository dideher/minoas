package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.SpecializationHome;

import java.util.Collection;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name(value = "specializationManagement")
@Scope(ScopeType.PAGE)
public class SpecializationManagement extends BaseDatabaseAwareSeamComponent {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @In(required = false)
	private SpecializationHome specializationHome;
    
    @DataModel(value="specializations")
	private Collection<Specialization> specializations;

	//@In(required = true, create = true)
	//private CoreSearching coreSearching;
    
    
    @Factory(value="specializations")
    public void fetchSpecializations() {

        specializations = getCoreSearching().getSpecializations(getEntityManager());

    }
    
    

	public String updateSpecialization() {
		String persistanceReturnValue = null;
		try {
			persistanceReturnValue = specializationHome.update();
			getEntityManager().flush();
			specializations = getCoreSearching().getSpecializations(getEntityManager());
		} catch (Exception e) {

			facesMessages.add(Severity.ERROR, "Ο Κωδικός της ειδικότητας υπάρχει ήδη.");
			
		}
		return persistanceReturnValue;
	}
	
	public String insertSpecialization() {
		String persistanceReturnValue = null;
		try {
			persistanceReturnValue = specializationHome.persist();
			getEntityManager().flush();
			specializations = getCoreSearching().getSpecializations(getEntityManager());
		} catch (Exception e) {

			facesMessages.add(Severity.ERROR, "Ο Κωδικός της ειδικότητας υπάρχει ήδη.");
			
		}
		return persistanceReturnValue;
	}
}
