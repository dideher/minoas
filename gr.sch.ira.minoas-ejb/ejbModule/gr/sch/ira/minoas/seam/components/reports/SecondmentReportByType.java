
package gr.sch.ira.minoas.seam.components.reports;

import gr.sch.ira.minoas.model.employement.Secondment;

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
import org.jboss.seam.framework.EntityQuery;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentReportByType")
public class SecondmentReportByType {

	@In(required=true, value="secondmentsByTypeListQuery")
	private EntityQuery<Secondment> secondmentsByTypeListQuery;
	
	/**
	 * 
	 */
	public SecondmentReportByType() {
	}

	public void runReport() throws Exception {
		try {
		Map<String, String> parameters = new HashMap<String, String>();
		List<SecondmentItem> rows = new ArrayList<SecondmentItem>(100);
		for(Secondment secondment : secondmentsByTypeListQuery.getResultList()) {
			for(int i = 0 ; i < 200 ; i++)
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
