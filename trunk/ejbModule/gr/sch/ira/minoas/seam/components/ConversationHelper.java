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
@Scope(ScopeType.EVENT)
public class ConversationHelper extends BaseSeamComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Begin(flushMode = FlushModeType.MANUAL, nested = true, pageflow = "new-secondment")
	public void beginNewSecondmentConversation() {
		info("a new secondment conversation has begun.");
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, nested = true, pageflow = "employee-record")
	public void beginEmployeeRecordConversation() {
		info("a new employee record conversation has begun.");
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, nested = true, pageflow = "school-record")
	public void beginSchoolRecordConversation() {
		info("a new school record conversation has begun.");
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, nested = true, pageflow = "secondments-search" )
	public String beginSecondmentSearchActiveConversation() {
		return "search-active-secondments";
	}
	
	@Begin(flushMode = FlushModeType.MANUAL, nested = true, pageflow = "secondments-search")
	public String beginSecondmentSearchSchoolIncomingConversation() {
		return "search-school-incoming-secondments";
	}
	

}
