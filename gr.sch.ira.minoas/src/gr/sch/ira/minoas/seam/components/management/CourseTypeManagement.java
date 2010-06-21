
package gr.sch.ira.minoas.seam.components.management;

import java.util.Collection;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import gr.sch.ira.minoas.model.classrooms.CourseType;
import gr.sch.ira.minoas.model.classrooms.SchoolClass;
import gr.sch.ira.minoas.model.core.Specialization;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.CourseTypeHome;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name(value = "courseTypeManagement")
@Scope(ScopeType.CONVERSATION)
public class CourseTypeManagement extends BaseDatabaseAwareSeamComponent {
	
	@In(required=false, create=false)
	private CourseTypeHome courseTypeHome;
	
	@In(required=true, create=true)
	private CoreSearching coreSearcing;
	
	@Out(value="firstAssigmentsSource", required=true)
	private Collection<Specialization> firstAssigmentsSource;
	
	@Out(value="secondAssigmentsSource", required=true)
	private Collection<Specialization> secondAssigmentsSource;
	
	private Collection<Specialization> firstAssigmentsTarget;
	
	private Collection<Specialization> secondAssigmentsTarget;

	/**
	 * @return the courseTypeHome
	 */
	public CourseTypeHome getCourseTypeHome() {
		return courseTypeHome;
	}

	/**
	 * @param courseTypeHome the courseTypeHome to set
	 */
	public void setCourseTypeHome(CourseTypeHome courseTypeHome) {
		this.courseTypeHome = courseTypeHome;
	}

	/**
	 * @return the firstAssigmentsSource
	 */
	public Collection<Specialization> getFirstAssigmentsSource() {
		return firstAssigmentsSource;
	}

	/**
	 * @param firstAssigmentsSource the firstAssigmentsSource to set
	 */
	public void setFirstAssigmentsSource(Collection<Specialization> firstAssigmentsSource) {
		this.firstAssigmentsSource = firstAssigmentsSource;
	}

	/**
	 * @return the secondAssigmentsSource
	 */
	public Collection<Specialization> getSecondAssigmentsSource() {
		return secondAssigmentsSource;
	}

	/**
	 * @param secondAssigmentsSource the secondAssigmentsSource to set
	 */
	public void setSecondAssigmentsSource(Collection<Specialization> secondAssigmentsSource) {
		this.secondAssigmentsSource = secondAssigmentsSource;
	}

	/**
	 * @return the firstAssigmentsTarget
	 */
	public Collection<Specialization> getFirstAssigmentsTarget() {
		return firstAssigmentsTarget;
	}

	/**
	 * @param firstAssigmentsTarget the firstAssigmentsTarget to set
	 */
	public void setFirstAssigmentsTarget(Collection<Specialization> firstAssigmentsTarget) {
		this.firstAssigmentsTarget = firstAssigmentsTarget;
	}

	/**
	 * @return the secondAssigmentsTarget
	 */
	public Collection<Specialization> getSecondAssigmentsTarget() {
		return secondAssigmentsTarget;
	}

	/**
	 * @param secondAssigmentsTarget the secondAssigmentsTarget to set
	 */
	public void setSecondAssigmentsTarget(Collection<Specialization> secondAssigmentsTarget) {
		this.secondAssigmentsTarget = secondAssigmentsTarget;
	}
	
	@Factory(value="firstAssigmentsSource", scope=ScopeType.CONVERSATION)
	public void constructFirstAssigmentsSource() {
		setFirstAssigmentsSource(coreSearcing.getSpecializations(getEntityManager()));
	}
	
	@Factory(value="secondAssigmentsSource", scope=ScopeType.CONVERSATION)
	public void constructSecondAssigmentsSource() {
		setSecondAssigmentsSource(coreSearcing.getSpecializations(getEntityManager()));
	}
	
	
}
