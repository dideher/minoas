
package gr.sch.ira.minoas.seam.components.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr.sch.ira.minoas.model.employement.Secondment;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Name("secondmentReportByType")
public class SecondmentReportByType {

	@In(required=true, scope=ScopeType.CONVERSATION, value="secondmentsByTypeListQuery")
	private EntityQuery<Secondment> secondmentsByTypeListQuery;
	
	
	/**
	 * 
	 */
	public SecondmentReportByType() {
	}

	public void runReport() throws Exception {
		try {
		Map<String, String> parameters = new HashMap<String, String>();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(secondmentsByTypeListQuery.getResultList());
		//byte[] bytes = JasperRunManager.runReportToPdf(this.getClass().getResourceAsStream("/reports/secondmentByType.jasper"), parameters,(JRDataSource)null);
		byte[] bytes = JasperRunManager.runReportToPdf("/home/fsla/work/java/eclipse-workspace-ganymede/gr.sch.ira.minoas/src/reports/secondmentByType.jasper", parameters,ds);
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
