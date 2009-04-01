package gr.sch.ira.minoas.session.persistent;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public interface IGenericDAO<T, ID extends Serializable> {
	public Collection<T> findAll();

	public T findByExample(T entityInstance);

	public T findByID(ID id);

	public T persist(T entityInstance);
}
