package gr.sch.ira.minoas.seam.components.reports.teachingResources;

import gr.sch.ira.minoas.model.core.School;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SpecializationGroupVoidAnalysisItem {

	private School school;
	
	private TeachingResource resource;
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
	 * 
	 */
	public SpecializationGroupVoidAnalysisItem() {
	}
	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(School school) {
		this.school = school;
	}
	/**
	 * @return the resource
	 */
	public TeachingResource getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(TeachingResource resource) {
		this.resource = resource;
	}

}
