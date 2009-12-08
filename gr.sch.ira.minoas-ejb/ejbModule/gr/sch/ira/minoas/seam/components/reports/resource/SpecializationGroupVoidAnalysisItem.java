package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SpecializationGroupVoidAnalysisItem implements Comparable<SpecializationGroupVoidAnalysisItem> {

	private TeachingResource resource;

	private School school;

	/**
	 * 
	 */
	public SpecializationGroupVoidAnalysisItem() {
	}

	/**
	 * @param school
	 * @param resource
	 */
	public SpecializationGroupVoidAnalysisItem(School school, TeachingResource resource) {
		super();
		this.school = school;
		this.resource = resource;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SpecializationGroupVoidAnalysisItem o) {
		return this.school.compareTo(o.getSchool());
	}

	/**
	 * @return the resource
	 */
	public TeachingResource getResource() {
		return resource;
	}

	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(TeachingResource resource) {
		this.resource = resource;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}

}
