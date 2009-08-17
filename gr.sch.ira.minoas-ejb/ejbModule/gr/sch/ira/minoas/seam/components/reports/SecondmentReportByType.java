
package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employement.Secondment;
import gr.sch.ira.minoas.model.employement.SecondmentType;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.criteria.SecondmentCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.SeamResourceBundle;
import org.jboss.seam.framework.EntityQuery;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentReportByType")
public class SecondmentReportByType extends BaseReport {

	@In(required=true, value="secondmentsByTypeListQuery")
	private EntityQuery<Secondment> secondmentsByTypeListQuery;
	
	@In(required=true)
	private SecondmentCriteria secondmentCriteria;
	
	/**
	 * 
	 */
	public SecondmentReportByType() {
	}

	public void runReport() throws Exception {
		try {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("SECONDMENT_TYPE_FILTER", secondmentCriteria.getSecondmentType() != null ? SeamResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString( secondmentCriteria.getSecondmentType().getKey()) : "Όλοι οι Τύποι");
		/* create the secondment type helper */
		for(SecondmentType secondmentType : getCoreSearching().getAvailableSecondmentTypes()) {
			String l = SeamResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(secondmentType.getKey());
			parameters.put(secondmentType.name(), l);
		}
		List<SecondmentItem> rows = new ArrayList<SecondmentItem>();
		for(Secondment secondment : secondmentsByTypeListQuery.getResultList()) {
			rows.add(new SecondmentItem(secondment));
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rows);
		byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream("/reports/secondmentByType.jasper"), parameters,(JRDataSource)ds);
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition","attachment;filename=SecondmentReportByType.pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		servletOutputStream.write(bytes, 0, bytes.length);
		servletOutputStream.flush();
		servletOutputStream.close();
		FacesContext.getCurrentInstance().responseComplete();
		} catch(Exception ex) {
			ex.printStackTrace(System.err);
		}
	}

	
}
