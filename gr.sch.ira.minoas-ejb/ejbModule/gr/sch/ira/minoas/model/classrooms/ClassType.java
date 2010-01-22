

package gr.sch.ira.minoas.model.classrooms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolType;

public class ClassType extends BaseIDModel {

	
	@Column(name="SCHOOL_TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private SchoolType schoolType;
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name="TITLE", length=128, unique=true, nullable=false)
	private String title;
	
	

}
