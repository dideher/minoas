package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.transfers.OutstandingImprovement;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class OutstandingImprovementItem extends EmployeeReportItem {

	private Character improvementRegion;
	
	
	private String sourceSchool;
	
	private String targetSchool;
	/**
	 * 
	 */
	public OutstandingImprovementItem() {
		super();
	}

	/**
	 * @param employee
	 */
	public OutstandingImprovementItem(OutstandingImprovement improvement) {
		super(improvement.getEmployee());
		setId(improvement.getId());
		setSourceSchool(improvement.getSourceSchool().getTitle());
		setTargetSchool(improvement.getTargetSchool().getTitle());
		setImprovementRegion(improvement.getSourceSchool().getRegionCode());
	}

	
	/**
	 * @return the sourceSchool
	 */
	public String getSourceSchool() {
		return sourceSchool;
	}

	/**
	 * @param sourceSchool the sourceSchool to set
	 */
	public void setSourceSchool(String sourceSchool) {
		this.sourceSchool = sourceSchool;
	}

	/**
	 * @return the targetSchool
	 */
	public String getTargetSchool() {
		return targetSchool;
	}

	/**
	 * @param targetSchool the targetSchool to set
	 */
	public void setTargetSchool(String targetSchool) {
		this.targetSchool = targetSchool;
	}

	/**
	 * @return the improvementRegion
	 */
	public Character getImprovementRegion() {
		return improvementRegion;
	}

	/**
	 * @param improvementRegion the improvementRegion to set
	 */
	public void setImprovementRegion(Character improvementRegion) {
		this.improvementRegion = improvementRegion;
	}

}
