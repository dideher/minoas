package gr.sch.ira.minoas.seam.components.async;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.RankInfoCalculation;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.FinalExpiration;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;

/**
 * @author <a href="mailto:andreadakis@gmail.com">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name("rankInfoUpdaterProcessor")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class RankInfoUpdaterProcessor extends BaseDatabaseAwareSeamComponent {


    @In(required=true, create=true)
    private RankInfoCalculation rankInfoCalculation;
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	/**
	 * 
	 */
	public RankInfoUpdaterProcessor() {
	}

	@Asynchronous
	@Transactional(TransactionPropagationType.REQUIRED)
	public QuartzTriggerHandle schedule(@Expiration Date when, 
            @IntervalDuration Long interval,
            @FinalExpiration Date endDate) {
	    
		rankInfoCalculation.recalculateRankInfos();
	    
	    getEntityManager().flush();
	    info("Finished Recalculating ReankInfos!");
	    return null;
	}
	

}
