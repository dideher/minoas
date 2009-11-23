package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;
import gr.sch.ira.minoas.model.core.TeachingRequirement;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SchoolUniversalEmployments extends AbstractList<SchoolUniversalEmploymentsGroup> {

	private HashMap<String, SpecializationGroup> specializationGroupMap = new HashMap<String, SpecializationGroup>();

	private HashMap<SpecializationGroup, SchoolUniversalEmploymentsGroup> employmentsGroupMap = new HashMap<SpecializationGroup, SchoolUniversalEmploymentsGroup>();

	private List<SchoolUniversalEmploymentsGroup> employmentsGroupIndexList = new ArrayList<SchoolUniversalEmploymentsGroup>();
	/**
	 * 
	 */
	public SchoolUniversalEmployments() {
		super();
	}

	public SchoolUniversalEmployments(Collection<TeachingRequirement> schoolRequirements, Collection<SpecializationGroup> specializationGroups) {
		this();
		HashMap<SpecializationGroup, TeachingRequirement> schoolRequirementsMap = new HashMap<SpecializationGroup, TeachingRequirement>(specializationGroups.size());
		for(TeachingRequirement requirement : schoolRequirements) {
			schoolRequirementsMap.put(requirement.getSpecialization(), requirement);
		}
		for (SpecializationGroup specializationGroup : specializationGroups) {
			SchoolUniversalEmploymentsGroup item = new SchoolUniversalEmploymentsGroup(specializationGroup);
			/* check if we have a teaching requirement for the given specialization group */
			TeachingRequirement requirement = schoolRequirementsMap.get(specializationGroup);
			if(requirement!=null)
				item.setRequiredHours(requirement.getHours());
			
			employmentsGroupIndexList.add(item);
			employmentsGroupMap.put(specializationGroup, item);
			for (Specialization specialization : specializationGroup.getSpecializations())
				specializationGroupMap.put(specialization.getId(), specializationGroup);
		}
		
	}

	public void add(SchoolUniversalEmploymentItem item) {
		SpecializationGroup specializationGroup = specializationGroupMap.get(item.getEmployeeSpecializationID());
		if(specializationGroup!=null) {
			SchoolUniversalEmploymentsGroup group = employmentsGroupMap.get(specializationGroup);
			group.add(item);
		} else {
			System.err.println("WE COULD NOT FIND A SPECIALIZATION GROUP FOR SPECIALIZATION "+item.getEmployeeSpecializationID());
			
		}
		
	}

	/**
	 * @see java.util.AbstractList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, SchoolUniversalEmploymentsGroup element) {
		super.add(index, element);
	}

	/**
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public SchoolUniversalEmploymentsGroup get(int index) {
		return employmentsGroupIndexList.get(index);
	}

	/**
	 * @see java.util.AbstractList#remove(int)
	 */
	@Override
	public SchoolUniversalEmploymentsGroup remove(int index) {
		return super.remove(index);
	}

	/**
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return employmentsGroupMap.size();
	}



}
