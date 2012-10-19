package gr.sch.ira.minoas.seam.components.management;

import java.util.Collection;
import java.util.Date;

import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employement.EmployeeLeave;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.model.employement.EmploymentType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeInfoHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeLeaveHome;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
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

	@In(required = true)
	EmployeeHome employeeHome;
	
	@In(required = true)
	EmployeeInfoHome employeeInfoHome;
	
	private Date totalWorkServiceCalculationDate;
	

//	/**
//	 * Employee's rank transitions history
//	 */
//	@DataModel(scope=ScopeType.PAGE, value="rankInfoHistory")
//	private Collection<RankInfo> rankInfoHistory = null;

	public void setTotalWorkServiceCalculationDate(
			Date totalWorkServiceCalculationDate) {
		this.totalWorkServiceCalculationDate = totalWorkServiceCalculationDate;
	}

	public Date getTotalWorkServiceCalculationDate() {
		return totalWorkServiceCalculationDate;
	}

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

	
	
//	/**
//	 * @return the rankInfoHistory
//	 */
//	public Collection<RankInfo> getRankInfoHistory() {
//		return rankInfoHistory;
//	}
//
//	/**
//	 * @param rankInfoHistory the rankInfoHistory to set
//	 */
//	public void setRankInfoHistory(Collection<RankInfo> rankInfoHistory) {
//		this.rankInfoHistory = rankInfoHistory;
//	}

	public String modifyEmployeeInfo() {
        if(employeeInfoHome != null && employeeInfoHome.isManaged()) {
            info("trying to modify employee info #0", employeeInfoHome);
            /* check if the work experience dates are allowed. */
            
            EmployeeInfo employeeInfo = employeeInfoHome.getInstance();
            
            if(!employeeInfo.getHasAMasterDegree()) employeeInfo.setMscDate(null);
            if(!employeeInfo.getHasAPhD()) employeeInfo.setPhdDate(null);
            if(!employeeInfo.getIsANatSchPubAdminGraduate()) employeeInfo.setNatSchPubAdminDate(null);
            
            if(employeeInfo.getHasAMasterDegree() && employeeInfo.getMscDate() == null) { 
            	facesMessages.add(Severity.ERROR, "Δεν ορίσατε την ημερομηνία Μεταπτυχιακού.");
                return ACTION_OUTCOME_FAILURE;
            } else if(employeeInfo.getHasAPhD() && employeeInfo.getPhdDate() == null) { 
            	facesMessages.add(Severity.ERROR, "Δεν ορίσατε την ημερομηνία Διδακτορικού.");
                return ACTION_OUTCOME_FAILURE;
            } else if(employeeInfo.getIsANatSchPubAdminGraduate() && employeeInfo.getNatSchPubAdminDate() == null) { 
            	facesMessages.add(Severity.ERROR, "Δεν ορίσατε την ημερομηνία Ανώτ. Σχολ. Δημ. Διοίκ.");
                return ACTION_OUTCOME_FAILURE;
            } else {
                employeeInfoHome.update();
                info("employee's #0 employee info #1 has been updated!", employeeInfoHome.getInstance().getEmployee(), employeeInfoHome.getInstance());
                return ACTION_OUTCOME_SUCCESS;
            }
        } else {
            facesMessages.add(Severity.ERROR, "employeeInfo home #0 is not managed, thus #1 can't be modified.", employeeInfoHome, employeeInfoHome.getInstance());
            return ACTION_OUTCOME_FAILURE;
        }
    }
	
//	public String insertRankInfo() {
//        if(employeeInfoHome != null && employeeInfoHome.isManaged()) {
//            info("trying to insert a rank info for employee info #0", employeeInfoHome);
//                employeeInfoHome.insertRankInfo();
//                info("employee's #0 Rank Info #1 has been updated!", employeeInfoHome.getInstance().getEmployee(), employeeInfoHome.getInstance().getRankInfo());
//                return ACTION_OUTCOME_SUCCESS;
//
//        } else {
//            facesMessages.add(Severity.ERROR, "employeeInfo home #0 is not managed, thus #1 can't be inserted.", employeeInfoHome, employeeInfoHome.getInstance().getRankInfo());
//            return ACTION_OUTCOME_FAILURE;
//        }
//    }
	
	
	@Transactional
    public String insertRankInfo() {
        if (employeeHome.isManaged()) {
        	RankInfo rinfo = employeeInfoHome.getInstance().getCurrentRankInfo();
        	
            //rinfo.setEmployeeInfo(employeeInfoHome.getInstance().getEmployee().getEmployeeInfo());
            rinfo.setInsertedBy(getPrincipal());
            rinfo.setInsertedOn(new Date());
        	
        	
        	getEntityManager().persist(rinfo);
        	employeeInfoHome.getInstance().setCurrentRankInfo(rinfo);
        	
            Employee employee = getEntityManager().merge(employeeHome.getInstance());

                
            //employeeInfoHome.persist();
            getEntityManager().flush();
            
            employeeInfoHome.getInstance().getRankInfos().add(rinfo);
           
//    	    setRankInfoHistory(coreSearching.getRankInfoHistory(employee));		// coreSearching.getRankInfoHistory() is now deprecated.
            //setRankInfoHistory(employee.getEmployeeInfo().getRankInfos());
            info("Rank Info #0 for employee #1 has been created", rinfo, employee);
            return ACTION_OUTCOME_SUCCESS;

        } else {
            facesMessages.add(Severity.ERROR, "employee home #0 is not managed.", employeeHome);
            return ACTION_OUTCOME_FAILURE;
        }
    }
	
	
	@Factory(value="rankInfoHistory",autoCreate=true)
	public void constructRankInfoHistory() {
	    Employee employee = getEmployeeHome().getInstance();
	    info("constructing evaluation history for employee #0", employee);
//	    setRankInfoHistory(coreSearching.getRankInfoHistory(employee));		// coreSearching.getRankInfoHistory() is now deprecated.	
	    //setRankInfoHistory(employee.getEmployeeInfo().getRankInfos());
	}
	
	
	
    /* this method is called when the user clicks the "add new rank info" */
    public void prepeareNewRankInfo() {
        //employeeInfoHome.clearInstance();
        RankInfo rinfo = new RankInfo();
        rinfo.resetRankInfo();
        rinfo.setEmployeeInfo(employeeInfoHome.getInstance());
        
        employeeInfoHome.getInstance().setCurrentRankInfo(rinfo);

    }

    
}
