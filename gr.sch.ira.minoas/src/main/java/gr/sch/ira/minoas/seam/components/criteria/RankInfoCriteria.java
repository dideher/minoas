package gr.sch.ira.minoas.seam.components.criteria;

import gr.sch.ira.minoas.core.CoreUtils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author <a href="mailto:andreadakis@gmail.com">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name("rankInfoCriteria")
@Scope(ScopeType.CONVERSATION)
public class RankInfoCriteria {

	private String registryId;
	
	private String lastName;
	
	private Date effectiveRankDateFrom;

	private Date effectiveRankDateUntil;
	
	private Date effectiveSalaryGradeDateFrom;

	private Date effectiveSalaryGradeDateUntil;
	
	/**
		 * 
		 */
	public RankInfoCriteria() {
		super();
		
		registryId = null;
		lastName = null;
		
		effectiveRankDateFrom = DateUtils.truncate(DateUtils.addDays(new Date(System.currentTimeMillis()), -1), Calendar.DAY_OF_MONTH);
		effectiveRankDateUntil = DateUtils.truncate(new Date(System.currentTimeMillis()), Calendar.DAY_OF_MONTH);
	}

	

	/**
	 * @return the effectiveRankDateFrom
	 */
	public Date getEffectiveRankDateFrom() {
		return effectiveRankDateFrom;
	}



	/**
	 * @param effectiveRankDateFrom the effectiveRankDateFrom to set
	 */
	public void setEffectiveRankDateFrom(Date effectiveRankDateFrom) {
		this.effectiveRankDateFrom = effectiveRankDateFrom;
	}



	/**
	 * @return the effectiveRankDateUntil
	 */
	public Date getEffectiveRankDateUntil() {
		return effectiveRankDateUntil;
	}



	/**
	 * @param effectiveRankDateUntil the effectiveRankDateUntil to set
	 */
	public void setEffectiveRankDateUntil(Date effectiveRankDateUntil) {
		this.effectiveRankDateUntil = effectiveRankDateUntil;
	}



	/**
	 * @return the effectiveSalaryGradeDateFrom
	 */
	public Date getEffectiveSalaryGradeDateFrom() {
		return effectiveSalaryGradeDateFrom;
	}



	/**
	 * @param effectiveSalaryGradeDateFrom the effectiveSalaryGradeDateFrom to set
	 */
	public void setEffectiveSalaryGradeDateFrom(Date effectiveSalaryGradeDateFrom) {
		this.effectiveSalaryGradeDateFrom = effectiveSalaryGradeDateFrom;
	}



	/**
	 * @return the effectiveSalaryGradeDateUntil
	 */
	public Date getEffectiveSalaryGradeDateUntil() {
		return effectiveSalaryGradeDateUntil;
	}



	/**
	 * @param effectiveSalaryGradeDateUntil the effectiveSalaryGradeDateUntil to set
	 */
	public void setEffectiveSalaryGradeDateUntil(Date effectiveSalaryGradeDateUntil) {
		this.effectiveSalaryGradeDateUntil = effectiveSalaryGradeDateUntil;
	}



	/**
	 * @return the lastName
	 */
	public String getLastName() {
		if(CoreUtils.isEmpty(lastName))
			return null;
		else
			return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the registryId
	 */
	public String getRegistryId() {
		if(CoreUtils.isEmpty(registryId))
			return null;
		else
			return registryId;
	}

	/**
	 * @param registryId the registryId to set
	 */
	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}


}
