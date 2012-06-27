package gr.sch.ira.minoas.seam.components.management;

import java.util.Collection;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeInfoHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage.Severity;

/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Scope(ScopeType.PAGE)
@Name(value = "employeeInfoManagement")
public class EmployeeInfoManagement extends BaseDatabaseAwareSeamComponent {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@In(required = true)
	private CoreSearching coreSearching;

	@In(required = true, create = true)
	EmployeeHome employeeHome;
	
	@In(required = false)
	EmployeeInfoHome employeeInfoHome;

	/**
	 * Employee's rank transitions history
	 */
	@DataModel(scope=ScopeType.PAGE, value="rankInfoHistory")
	private Collection<RankInfo> rankInfoHistory = null;

	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome the employeeHome to set
	 */
	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}



	/**
	 * @return the coreSearching
	 */
	public CoreSearching getCoreSearching() {
		return coreSearching;
	}

	/**
	 * @param coreSearching the coreSearching to set
	 */
	public void setCoreSearching(CoreSearching coreSearching) {
		this.coreSearching = coreSearching;
	}

	/**
	 * @return the employeeInfoHome
	 */
	public EmployeeInfoHome getEmployeeInfoHome() {
		return employeeInfoHome;
	}

	/**
	 * @param employeeInfoHome the employeeInfoHome to set
	 */
	public void setEmployeeInfoHome(EmployeeInfoHome employeeInfoHome) {
		this.employeeInfoHome = employeeInfoHome;
	}

	
	
	/**
	 * @return the rankInfoHistory
	 */
	public Collection<RankInfo> getRankInfoHistory() {
		return rankInfoHistory;
	}

	/**
	 * @param rankInfoHistory the rankInfoHistory to set
	 */
	public void setRankInfoHistory(Collection<RankInfo> rankInfoHistory) {
		this.rankInfoHistory = rankInfoHistory;
	}

	public String modifyEmployeeInfo() {
        if(employeeInfoHome != null && employeeInfoHome.isManaged()) {
            info("trying to modify employee info #0", employeeInfoHome);
            /* check if the work experience dates are allowed. */
//            EmployeeInfo employeeInfo = employeeInfoHome.getInstance();
//            if(workExp.getToDate().compareTo(workExp.getFromDate()) <= 0 ) {
//            	facesMessages.add(Severity.ERROR, "Οι ημ/νιες έναρξης και λήξης της προϋπηρεσίας, έτσι όπως τις τροποποιήσατε, είναι μή αποδεκτές.");
//                return ACTION_OUTCOME_FAILURE;
//            } else {
                employeeInfoHome.update();
                info("employee's #0 employee info #1 has been updated!", employeeInfoHome.getInstance().getEmployee(), employeeInfoHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
//            }
        } else {
            facesMessages.add(Severity.ERROR, "employeeInfo home #0 is not managed, thus #1 can't be modified.", employeeInfoHome, employeeInfoHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
	
	public String insertRankInfo() {
        if(employeeInfoHome != null && employeeInfoHome.isManaged()) {
            info("trying to insert a rank info for employee info #0", employeeInfoHome);
                employeeInfoHome.insertRankInfo();
                info("employee's #0 Rank Info #1 has been updated!", employeeInfoHome.getInstance().getEmployee(), employeeInfoHome.getInstance().getRankInfo());
                return ACTION_OUTCOME_SUCCESS;

        } else {
            facesMessages.add(Severity.ERROR, "employeeInfo home #0 is not managed, thus #1 can't be inserted.", employeeInfoHome, employeeInfoHome.getInstance().getRankInfo());
            return ACTION_OUTCOME_FAILURE;
        }
    }
	
	@Factory(value="rankInfoHistory",autoCreate=true)
	public void constructRankInfoHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing evaluation history for employee #0", employee);
	    setRankInfoHistory(coreSearching.getRankInfoHistory(employee));
	}
}
