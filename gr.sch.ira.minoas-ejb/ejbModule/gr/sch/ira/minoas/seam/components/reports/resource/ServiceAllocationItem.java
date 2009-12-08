package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.employement.ServiceAllocation;

import java.util.Date;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class ServiceAllocationItem extends EmployeeReportItem {

	private String comment;

	private Date dueTo;

	private Date establishedIn;

	private String serviceAllocationType;

	private String serviceAllocationTypeKey;

	private String servicingUnit;

	private String sourceUnit;

	private Integer workingHoursOnRegularPosition;

	private Integer workingHoursOnServicingPosition;

	/**
	 * 
	 */
	public ServiceAllocationItem() {
		super();
	}

	/**
	 * @param employee
	 */
	public ServiceAllocationItem(ServiceAllocation serviceAllocation) {
		super(serviceAllocation.getEmployee());
		this.comment = serviceAllocation.getComment();
		this.dueTo = serviceAllocation.getDueTo();
		this.establishedIn = serviceAllocation.getEstablished();
		this.sourceUnit = serviceAllocation.getSourceUnit().getTitle();
		this.servicingUnit = serviceAllocation.getServiceUnit().getTitle();
		this.serviceAllocationType = serviceAllocation.getServiceType().name();
		this.serviceAllocationTypeKey = serviceAllocation.getServiceType().getKey();
		this.workingHoursOnRegularPosition = serviceAllocation.getWorkingHoursOnRegularPosition();
		this.workingHoursOnServicingPosition = serviceAllocation.getWorkingHoursOnServicingPosition();
	}

	/**
	 * @return the dueTo
	 */
	public Date getDueTo() {
		return dueTo;
	}

	/**
	 * @return the establishedIn
	 */
	public Date getEstablishedIn() {
		return establishedIn;
	}

	/**
	 * @return the serviceAllocationType
	 */
	public String getServiceAllocationType() {
		return serviceAllocationType;
	}

	/**
	 * @return the serviceAllocationTypeKey
	 */
	public String getServiceAllocationTypeKey() {
		return serviceAllocationTypeKey;
	}

	/**
	 * @return the servicingUnit
	 */
	public String getServicingUnit() {
		return servicingUnit;
	}

	/**
	 * @return the sourceUnit
	 */
	public String getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @return the workingHoursOnRegularPosition
	 */
	public Integer getWorkingHoursOnRegularPosition() {
		return workingHoursOnRegularPosition;
	}

	/**
	 * @return the workingHoursOnServicingPosition
	 */
	public Integer getWorkingHoursOnServicingPosition() {
		return workingHoursOnServicingPosition;
	}

	/**
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	/**
	 * @param establishedIn the establishedIn to set
	 */
	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	/**
	 * @param serviceAllocationType the serviceAllocationType to set
	 */
	public void setServiceAllocationType(String serviceAllocationType) {
		this.serviceAllocationType = serviceAllocationType;
	}

	/**
	 * @param serviceAllocationTypeKey the serviceAllocationTypeKey to set
	 */
	public void setServiceAllocationTypeKey(String serviceAllocationTypeKey) {
		this.serviceAllocationTypeKey = serviceAllocationTypeKey;
	}

	/**
	 * @param servicingUnit the servicingUnit to set
	 */
	public void setServicingUnit(String servicingUnit) {
		this.servicingUnit = servicingUnit;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @param workingHoursOnRegularPosition the workingHoursOnRegularPosition to set
	 */
	public void setWorkingHoursOnRegularPosition(Integer workingHoursOnRegularPosition) {
		this.workingHoursOnRegularPosition = workingHoursOnRegularPosition;
	}

	/**
	 * @param workingHoursOnServicingPosition the workingHoursOnServicingPosition to set
	 */
	public void setWorkingHoursOnServicingPosition(Integer workingHoursOnServicingPosition) {
		this.workingHoursOnServicingPosition = workingHoursOnServicingPosition;
	}

}
