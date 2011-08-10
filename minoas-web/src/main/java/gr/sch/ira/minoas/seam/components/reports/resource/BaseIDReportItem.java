package gr.sch.ira.minoas.seam.components.reports.resource;

import gr.sch.ira.minoas.model.BaseIDModel;

public abstract class BaseIDReportItem {

	private Integer id;

	public BaseIDReportItem() {
	}

	public BaseIDReportItem(BaseIDModel entity) {
		this();
		if (entity != null) {
			setId(entity.getId());
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

}
