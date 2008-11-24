package gr.sch.ira.minoas.session.security;

import gr.sch.ira.minoas.model.security.RoleGroup;

import java.util.List;
import java.util.Map;

public interface IRoleGroupList {

	public abstract Map<String, RoleGroup> getAsMap();

	public abstract List<RoleGroup> getAsList();

}