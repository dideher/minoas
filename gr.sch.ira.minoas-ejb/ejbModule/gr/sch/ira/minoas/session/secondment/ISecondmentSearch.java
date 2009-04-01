package gr.sch.ira.minoas.session.secondment;

public interface ISecondmentSearch {

	/**
	 * Search for all current active secondments.
	 * @return
	 */
	public String searchActiveSecondments();

	public String searchSchoolIncomingSecondments();

	public String searchSchoolOutgoingSecondments();

	public String searchWithinPYSDESecondments();
}
