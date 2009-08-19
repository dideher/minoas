package gr.sch.ira.minoas.seam.components.reports.teachingResources;


import gr.sch.ira.minoas.model.core.SpecializationGroup;

public class TeachingResource {
	private Integer required;
	private SpecializationGroup specializationGroup;
	private Integer total;
	/**
	 * 
	 */
	public TeachingResource() {
		super();
	}
	/**
	 * @param specializationGroup
	 */
	public TeachingResource(SpecializationGroup specializationGroup) {
		super();
		this.specializationGroup = specializationGroup;
	}
	/**
	 * @return the specializationGroup
	 */
	public SpecializationGroup getSpecializationGroup() {
		return specializationGroup;
	}
	/**
	 * @param specializationGroup the specializationGroup to set
	 */
	public void setSpecializationGroup(SpecializationGroup specializationGroup) {
		this.specializationGroup = specializationGroup;
	}
	/**
	 * @return the required
	 */
	public Integer getRequired() {
		return required;
	}
	/**
	 * @param required the required to set
	 */
	public void setRequired(Integer required) {
		this.required = required;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}
