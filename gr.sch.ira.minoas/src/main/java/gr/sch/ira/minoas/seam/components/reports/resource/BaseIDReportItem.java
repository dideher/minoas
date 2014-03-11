package gr.sch.ira.minoas.seam.components.reports.resource;

import java.util.Date;

import gr.sch.ira.minoas.model.BaseIDModel;

public abstract class BaseIDReportItem {

	private Integer id;
	
	private Date insertedOn;

	public BaseIDReportItem() {
	}

	public BaseIDReportItem(BaseIDModel entity) {
		this();
		if (entity != null) {
			setId(entity.getId());
			setInsertedOn(entity.getInsertedOn());
		}
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInsertedOn() {
		return insertedOn;
	}

	public void setInsertedOn(Date insertedOn) {
		this.insertedOn = insertedOn;
	}

}
