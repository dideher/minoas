package gr.sch.ira.minoas.seam.components.reports.resource;

import java.util.Date;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employement.Leave;
import gr.sch.ira.minoas.model.employement.ServiceAllocation;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
public class ServiceAllocationItem extends EmployeeReportItem {

	private String comment;

	private Date dueTo;

	private Date establishedIn;

	private String sourceUnit;

	private String servicingUnit;

	private String serviceAllocationType;

	private String serviceAllocationTypeKey;

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
	 * @param dueTo the dueTo to set
	 */
	public void setDueTo(Date dueTo) {
		this.dueTo = dueTo;
	}

	/**
	 * @return the establishedIn
	 */
	public Date getEstablishedIn() {
		return establishedIn;
	}

	/**
	 * @param establishedIn the establishedIn to set
	 */
	public void setEstablishedIn(Date establishedIn) {
		this.establishedIn = establishedIn;
	}

	/**
	 * @return the sourceUnit
	 */
	public String getSourceUnit() {
		return sourceUnit;
	}

	/**
	 * @param sourceUnit the sourceUnit to set
	 */
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}

	/**
	 * @return the servicingUnit
	 */
	public String getServicingUnit() {
		return servicingUnit;
	}

	/**
	 * @param servicingUnit the servicingUnit to set
	 */
	public void setServicingUnit(String servicingUnit) {
		this.servicingUnit = servicingUnit;
	}

	/**
	 * @return the serviceAllocationType
	 */
	public String getServiceAllocationType() {
		return serviceAllocationType;
	}

	/**
	 * @param serviceAllocationType the serviceAllocationType to set
	 */
	public void setServiceAllocationType(String serviceAllocationType) {
		this.serviceAllocationType = serviceAllocationType;
	}

	/**
	 * @return the serviceAllocationTypeKey
	 */
	public String getServiceAllocationTypeKey() {
		return serviceAllocationTypeKey;
	}

	/**
	 * @param serviceAllocationTypeKey the serviceAllocationTypeKey to set
	 */
	public void setServiceAllocationTypeKey(String serviceAllocationTypeKey) {
		this.serviceAllocationTypeKey = serviceAllocationTypeKey;
	}

	/**
	 * @return the workingHoursOnRegularPosition
	 */
	public Integer getWorkingHoursOnRegularPosition() {
		return workingHoursOnRegularPosition;
	}

	/**
	 * @param workingHoursOnRegularPosition the workingHoursOnRegularPosition to set
	 */
	public void setWorkingHoursOnRegularPosition(Integer workingHoursOnRegularPosition) {
		this.workingHoursOnRegularPosition = workingHoursOnRegularPosition;
	}

	/**
	 * @return the workingHoursOnServicingPosition
	 */
	public Integer getWorkingHoursOnServicingPosition() {
		return workingHoursOnServicingPosition;
	}

	/**
	 * @param workingHoursOnServicingPosition the workingHoursOnServicingPosition to set
	 */
	public void setWorkingHoursOnServicingPosition(Integer workingHoursOnServicingPosition) {
		this.workingHoursOnServicingPosition = workingHoursOnServicingPosition;
	}

}
