

package gr.sch.ira.minoas.model.classrooms;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.core.SchoolType;

@Entity
@Table(name = "SCHOOL_CLASS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SchoolClass extends BaseIDModel {

	
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
	
	@Basic
	@Column(name="SORT_ORDER", nullable=true)
	private Integer sortOrder;

	/**
	 * @return the schoolType
	 */
	protected SchoolType getSchoolType() {
		return schoolType;
	}

	/**
	 * @param schoolType the schoolType to set
	 */
	protected void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	/**
	 * @return the title
	 */
	protected String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	protected void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the sortOrder
	 */
	protected Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	protected void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	

}
