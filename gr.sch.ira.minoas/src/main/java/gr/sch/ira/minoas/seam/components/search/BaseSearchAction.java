/**
 * 
 */
package gr.sch.ira.minoas.seam.components.search;

import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;

import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * @author slavikos
 * 
 */
public abstract class BaseSearchAction<T> extends
		BaseDatabaseAwareSeamComponent {

	private boolean pagingEnabled = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;

	

	private Integer pageSize = 10;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	private Integer page = 0;

	public void find() {
		pagingEnabled = true;
		page = 0;
		performQuery();
		
		
	}

	public void findAll() {
		pagingEnabled = false;
		page = 0;
		performQuery();
	}

	public abstract Query constructQuery();

	public void performQuery() {
		Query query = constructQuery();
		if (pagingEnabled) {
			setResults(query.setMaxResults(pageSize)
					.setFirstResult(page * pageSize).getResultList());
		} else {
			setResults(query.setFirstResult(page * pageSize).getResultList());
		}

	}

	public boolean isNextPageAvailable() {
		return pagingEnabled && getResults() != null
				&& getResults().size() == pageSize;
	}

	public boolean isPreviousPageAvailable() {
		return pagingEnabled && page > 0;
	}

	public void nextPage() {
		if (isNextPageAvailable()) {
			page++;
			performQuery();
		}
	}

	public void previousPage() {
		if (isPreviousPageAvailable()) {
			page--;
			performQuery();
		}
	}

	protected abstract List<T> getResults();

	protected abstract void setResults(List<T> results);
}
