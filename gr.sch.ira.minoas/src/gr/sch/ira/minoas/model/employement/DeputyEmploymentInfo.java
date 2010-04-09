package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */

@Entity
@Table(name = "DEPUTY_EMPLOYMENT_INFO")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class DeputyEmploymentInfo extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "ANALIPSI_ACT", nullable = true, length = 32)
	private String analipsiAct;

	@Basic
	@Column(name = "CHIEF_VERDICT", nullable = true, length = 32)
	private String chiefVerdict;

	@OneToOne(mappedBy = "deputyEmploymentInfo", fetch = FetchType.LAZY)
	private Employment employment;

	@Basic
	@Column(name = "MINISTRY_VERDICT", nullable = true, length = 32)
	private String ministryVerdict;

	@Basic
	@Column(name = "PYSDE_ACT", nullable = true, length = 32)
	private String pysdeAct;

	/**
	 * @return the analipsiAct
	 */
	public String getAnalipsiAct() {
		return analipsiAct;
	}

	/**
	 * @return the chiefVerdict
	 */
	public String getChiefVerdict() {
		return chiefVerdict;
	}

	/**
	 * @return the employment
	 */
	public Employment getEmployment() {
		return employment;
	}

	/**
	 * @return the ministryVerdict
	 */
	public String getMinistryVerdict() {
		return ministryVerdict;
	}

	/**
	 * @return the pysdeAct
	 */
	public String getPysdeAct() {
		return pysdeAct;
	}

	/**
	 * @param analipsiAct the analipsiAct to set
	 */
	public void setAnalipsiAct(String analipsiAct) {
		this.analipsiAct = analipsiAct;
	}

	/**
	 * @param chiefVerdict the chiefVerdict to set
	 */
	public void setChiefVerdict(String chiefVerdict) {
		this.chiefVerdict = chiefVerdict;
	}

	/**
	 * @param employment the employment to set
	 */
	public void setEmployment(Employment employment) {
		this.employment = employment;
	}

	/**
	 * @param ministryVerdict the ministryVerdict to set
	 */
	public void setMinistryVerdict(String ministryVerdict) {
		this.ministryVerdict = ministryVerdict;
	}

	/**
	 * @param pysdeAct the pysdeAct to set
	 */
	public void setPysdeAct(String pysdeAct) {
		this.pysdeAct = pysdeAct;
	}

}
