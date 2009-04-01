/**
 * 
 */
package gr.sch.ira.minoas.session.school;

import gr.sch.ira.minoas.model.core.OrganizationalOffice;
import gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.IBaseStatefulSeamComponent;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Local;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Name("availableOrganizationalOffices")
@Scope(ScopeType.EVENT)
@Restrict("#{identity.loggedIn}")
@Stateful
@Local( { IBaseStatefulSeamComponent.class, IOrganizationalOfficeList.class })
public class OrganizationalOfficeListBean extends BaseStatefulSeamComponentImpl implements IOrganizationalOfficeList {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(value = "coreSearching")
	private CoreSearching coreSearching;

	public List<OrganizationalOffice> organizationalOfficesList;

	public Map<String, OrganizationalOffice> organizationalOfficesMap;

	/**
	 * @see gr.sch.ira.minoas.seam.components.BaseStatefulSeamComponentImpl#create()
	 */
	@Override
	public void create() {
		super.create();
		this.organizationalOfficesList = coreSearching.getAvailableOrganizationalOffices();
		TreeMap<String, OrganizationalOffice> officesMap = new TreeMap<String, OrganizationalOffice>();
		for (OrganizationalOffice office : this.organizationalOfficesList) {
			officesMap.put(office.getTitle(), office);
		}
		this.organizationalOfficesMap = officesMap;
	}

	/**
	 * @see gr.sch.ira.minoas.session.school.IOrganizationalOfficeList#getAsList()
	 */
	public List<OrganizationalOffice> getAsList() {
		return this.organizationalOfficesList;
	}

	/**
	 * @see gr.sch.ira.minoas.session.school.IOrganizationalOfficeList#getAsMap()
	 */
	public Map<String, OrganizationalOffice> getAsMap() {
		return this.organizationalOfficesMap;
	}

}
