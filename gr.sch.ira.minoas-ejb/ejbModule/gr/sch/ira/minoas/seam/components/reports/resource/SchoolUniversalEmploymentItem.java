package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.Employment;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class SchoolUniversalEmploymentItem extends EmploymentReportItem {

	private String employmentComment;

	/**
	 * 
	 */
	public SchoolUniversalEmploymentItem() {
		super();
	}

	/**
	 * @param employment
	 */
	public SchoolUniversalEmploymentItem(Employment employment) {
		super(employment);
	}

	/**
	 * @return the employmentComment
	 */
	public String getEmploymentComment() {
		return employmentComment;
	}

	/**
	 * @param employmentComment the employmentComment to set
	 */
	public void setEmploymentComment(String employmentComment) {
		this.employmentComment = employmentComment;
	}

}
