package gr.sch.ira.minoas.model.preparatory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.model.employee.Person;

@MappedSuperclass
public abstract class PreparatoryOwnerOrDirector extends Person {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPECIALIZATION_ID", nullable = true)
	private Specialization specialization;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PERSON_LANG_SUFFICIENCY")
	private List<TeachingLanguage> teachingSufficiency = new ArrayList<TeachingLanguage>();

	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	/**
	 * @return the teachingSufficiency
	 */
	public List<TeachingLanguage> getTeachingSufficiency() {
		return teachingSufficiency;
	}

	/**
	 * @param teachingSufficiency the teachingSufficiency to set
	 */
	public void setTeachingSufficiency(List<TeachingLanguage> teachingSufficiency) {
		this.teachingSufficiency = teachingSufficiency;
	}


}
