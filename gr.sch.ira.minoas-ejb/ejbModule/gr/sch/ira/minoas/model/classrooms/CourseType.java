package gr.sch.ira.minoas.model.classrooms;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolType;


public class CourseType extends BaseIDModel {
	
	
	
	@Basic
	@Column(name="TITLE", length=128, unique=true, nullable=false)
	private String title;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Collection<ClassType> classTypes;
	
	
}
