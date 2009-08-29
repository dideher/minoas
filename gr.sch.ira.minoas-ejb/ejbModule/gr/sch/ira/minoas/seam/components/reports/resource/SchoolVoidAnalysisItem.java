package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.School;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolVoidAnalysisItem {
	/**
	 * @param school
	 */
	public SchoolVoidAnalysisItem(School school) {
		super();
		this.school = school;
	}
	/**
	 * 
	 */
	public SchoolVoidAnalysisItem() {
		super();
	}
	private School school;
	private Map<SpecializationGroup, TeachingResource> teachingResourcesMap = new HashMap<SpecializationGroup, TeachingResource>();
	
	
	public void populateResourcesUsingSpecializationGroups(Collection<SpecializationGroup> groups) {
		for(SpecializationGroup group : groups) {
			add(new TeachingResource(group));
		}
	}
	
	public void updateWithTeachingRequirements(Collection<TeachingRequirement> requirements) {
		for(TeachingRequirement requirement : requirements) {
			TeachingResource resource = teachingResourcesMap.get(requirement.getSpecialization());
			if(resource!=null) {
				resource.setRequired(requirement.getHours());
			}
		}
	}
	
	public void addEmployeeWorkingHours(SpecializationGroup specialization, Integer workHours) {
		TeachingResource resource = teachingResourcesMap.get(specialization);
		if(resource==null) {
			/* ops we have an employee providing hours of some specialization
			 * but the school unit has no intereset of that specialization.
			 */
			resource = new TeachingResource(specialization);
			teachingResourcesMap.put(specialization, resource);
		}
		resource.addHours(workHours);
	}
	
	public void removeEmployeeWorkingHours(SpecializationGroup specialization, Integer workHours) {
		TeachingResource resource = teachingResourcesMap.get(specialization);
		resource.removeHours(workHours);
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
	
	
	public TeachingResource add(TeachingResource newTeachingResource) {
		this.teachingResourcesMap.put(newTeachingResource.getSpecializationGroup(), newTeachingResource);
		return newTeachingResource;
	}
	
	public Collection<TeachingResource> getTeachingResources() {
		List<TeachingResource> result = new ArrayList<TeachingResource>();
		for(TeachingResource resource : teachingResourcesMap.values()) {
			result.add(resource);
		}
		return result;
		//return Collections.checkedCollection(teachingResourcesMap.values(), TeachingResource.class);
		//return teachingResourcesMap.values();
		//return Collections.unmodifiableCollection(teachingResourcesMap.values());
	}
}