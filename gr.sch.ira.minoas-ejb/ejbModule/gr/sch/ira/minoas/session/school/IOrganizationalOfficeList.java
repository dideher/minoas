package gr.sch.ira.minoas.session.school;

import gr.sch.ira.minoas.model.core.OrganizationalOffice;

import java.util.List;
import java.util.Map;

public interface IOrganizationalOfficeList {

	public abstract Map<String, OrganizationalOffice> getAsMap();

	public abstract List<OrganizationalOffice> getAsList();

}