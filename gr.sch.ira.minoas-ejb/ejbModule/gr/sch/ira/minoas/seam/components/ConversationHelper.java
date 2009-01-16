/**
 * 
 */
package gr.sch.ira.minoas.seam.components;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author slavikos
 *
 */
@Name("conversationHelper")
@Scope(ScopeType.APPLICATION)
public class ConversationHelper extends BaseSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Begin(flushMode = FlushModeType.MANUAL, pageflow = "new-secondment")
	public String beginNewSecondmentConversation() {
		info("a new secondment conversation has begun.");
		return "new-secondment";
	}
	
	@Begin(flushMode = FlushModeType.MANUAL,  pageflow = "employee-record")
	public String beginEmployeeRecordConversation() {
		info("a new employee record conversation has begun.");
		return "search-employee";
	}
	
	@Begin(flushMode = FlushModeType.MANUAL,  pageflow = "school-record")
	public String beginSchoolRecordConversation() {
		info("a new school record conversation has begun.");
		return "search-school";
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, join = true, pageflow = "secondments-search" )
	public String beginSecondmentSearchActiveConversation() {
		return "search-active-secondments";
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, join = true, pageflow = "secondments-search")
	public String beginSecondmentSearchSchoolIncomingConversation() {
		return "search-school-incoming-secondments";
	}
	

}
