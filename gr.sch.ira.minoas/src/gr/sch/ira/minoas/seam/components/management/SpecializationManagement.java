package gr.sch.ira.minoas.seam.components.management;

import java.util.Collection;
import java.util.Date;

import gr.sch.ira.minoas.model.core.Specialization;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.Factory;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.home.SpecializationHome;

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
       
        
        
//        Specialization localSpecialization = new Specialization();
//        localSpecialization.setTitle("Δοκιμαστική Ειδικότητα 1");
//        localSpecialization.setId("ΔΟΚ-03");
//        localSpecialization.setInsertedOn(new Date());
//        localSpecialization.setInsertedBy(getPrincipal());
//        getEntityManager().persist(localSpecialization);
//        getEntityManager().flush();
       
        
        specializations = getCoreSearching().getSpecializations(getEntityManager());
        
        
        
    }
}
