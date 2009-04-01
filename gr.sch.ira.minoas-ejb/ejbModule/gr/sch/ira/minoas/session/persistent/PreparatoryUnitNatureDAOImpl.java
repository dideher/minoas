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

package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNature;
import gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.deployment.Endpoint.EndpointState;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Stateless
@Local(IPreparatoryUnitNatureDAO.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Name(value = "preparatoryUnitNatureDAO")
public class PreparatoryUnitNatureDAOImpl extends GenericDAOImpl<PreparatoryUnitNature, Integer> implements
		IPreparatoryUnitNatureDAO {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public Collection<PreparatoryUnitNature> findAll() {
		try {
			return getEntityManager().createQuery("FROM PreparatoryUnitNature p ORDER BY (p.title)").getResultList();
		} catch (javax.persistence.NoResultException nre) {
			return EMPTY_COLLECTION;
		}
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IGenericDAO#findByExample(java.lang.Object)
	 */
	public PreparatoryUnitNature findByExample(PreparatoryUnitNature entityInstance) {
		if (entityInstance.getId() != null)
			return findByID(entityInstance.getId());
		else if (entityInstance.getType() != null) {
			return findByNatureType(entityInstance.getType());
		} else if (entityInstance.getTitle() != null) {
			try {
				return (PreparatoryUnitNature) getEntityManager().createQuery(
						"FROM PreparatoryUnitNature p WHERE p.title=:title").setParameter("title",
						entityInstance.getTitle()).getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				return null;
			}
		}
		return null;
	}

	/**
	 * @see gr.sch.ira.minoas.session.persistent.IPreparatoryUnitNatureDAO#findByNatureType(gr.sch.ira.minoas.model.preparatory.PreparatoryUnitNatureType)
	 */
	public PreparatoryUnitNature findByNatureType(PreparatoryUnitNatureType natureType) {
		try {
			return (PreparatoryUnitNature) getEntityManager().createQuery(
					"FROM PreparatoryUnitNature p WHERE p.type=:type ORDER BY (p.title)").setParameter("type",
					natureType).getSingleResult();
		} catch (javax.persistence.NoResultException nre) {
			return null;
		}
	}

}
