package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.core.SpecializationGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SchoolUniversalEmployments implements Collection<SchoolUniversalEmploymentsGroup> {

	private HashMap<String, SpecializationGroup> specializationGroupMap = new HashMap<String, SpecializationGroup>();

	private HashMap<SpecializationGroup, SchoolUniversalEmploymentsGroup> employmentsGroupMap = new HashMap<SpecializationGroup, SchoolUniversalEmploymentsGroup>();

	/**
	 * 
	 */
	public SchoolUniversalEmployments() {
		super();
	}

	public SchoolUniversalEmployments(Collection<SpecializationGroup> specializationGroups) {
		this();
		for (SpecializationGroup specializationGroup : specializationGroups) {
			employmentsGroupMap.put(specializationGroup, new SchoolUniversalEmploymentsGroup());
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

	public Collection<SchoolUniversalEmploymentsGroup> getEmploymentGroups() {
		return employmentsGroupMap.values();
	}

	public boolean add(SchoolUniversalEmploymentsGroup o) {
		return false;
	}

	public void add(int index, SchoolUniversalEmploymentsGroup element) {
	}

	public boolean addAll(Collection<? extends SchoolUniversalEmploymentsGroup> c) {
		return false;
	}

	public boolean addAll(int index, Collection<? extends SchoolUniversalEmploymentsGroup> c) {
		return false;
	}

	public void clear() {
	}

	public boolean contains(Object o) {
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		return false;
	}

	public SchoolUniversalEmploymentsGroup get(int index) {
		return null;
	}

	public int indexOf(Object o) {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}

	public Iterator<SchoolUniversalEmploymentsGroup> iterator() {
		return null;
	}

	public boolean remove(Object o) {
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		return false;
	}

	public int size() {
		return 0;
	}

	public Object[] toArray() {
		return null;
	}

	public <T> T[] toArray(T[] a) {
		return null;
	}


}
