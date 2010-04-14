package gr.sch.ira.minoas.model.classrooms;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;


public class CourseType extends BaseIDModel {
	
	
	
	@Basic
	@Column(name="TITLE", length=128, unique=true, nullable=false)
	private String title;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Collection<SchoolClass> schoolClass;
	
	
}
