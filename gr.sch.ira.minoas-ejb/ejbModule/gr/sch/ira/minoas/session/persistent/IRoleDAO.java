package gr.sch.ira.minoas.session.persistent;

import gr.sch.ira.minoas.model.security.Role;

public interface IRoleDAO extends IGenericDAO<Role, Long> {
	public Role findByName(String name);
}
