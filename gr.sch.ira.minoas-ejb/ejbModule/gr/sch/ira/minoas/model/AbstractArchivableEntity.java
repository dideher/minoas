/*
 * 
 *
 * Copyright (c) 2009 FORTHnet, S.A. All Rights Reserved.
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

package gr.sch.ira.minoas.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@MappedSuperclass
public abstract class AbstractArchivableEntity<T> extends BaseModel {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@Basic(fetch=FetchType.LAZY)
	@Column(name="CURRENT", nullable=false)
	private boolean current;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PREVIOUS_INSTANCE_ID", nullable=true)
	private T previousInstance;
	
	@Basic(fetch=FetchType.LAZY)
	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_ON", nullable=false, updatable=false)
	private Date modifiedOn;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name="MODIFIED_BY", length=32, nullable=false, updatable=false)
	private String modifiedBy;
	
	@SuppressWarnings("unused")
	@Version
	private Long version;

	/**
	 * @return the current
	 */
	protected boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	protected void setCurrent(boolean current) {
		this.current = current;
	}

	/**
	 * @return the previousInstance
	 */
	protected T getPreviousInstance() {
		return previousInstance;
	}

	/**
	 * @param previousInstance the previousInstance to set
	 */
	protected void setPreviousInstance(T previousInstance) {
		this.previousInstance = previousInstance;
	}

	/**
	 * @return the modifiedOn
	 */
	protected Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	protected void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the modifiedBy
	 */
	protected String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	protected void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
