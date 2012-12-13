package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RankType;
import gr.sch.ira.minoas.model.employee.SectorType;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeInfoHome;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
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
	private int totalCalculatedServiceInDays;
	

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
	 * @return the totalCalculatedServiceInDays
	 */
	public int getTotalCalculatedServiceInDays() {
		return totalCalculatedServiceInDays;
	}

	/**
	 * @param totalCalculatedServiceInDays the totalCalculatedServiceInDays to set
	 */
	public void setTotalCalculatedServiceInDays(int totalCalculatedServiceInDays) {
		this.totalCalculatedServiceInDays = totalCalculatedServiceInDays;
	}
	
	
	/**
	 * @return the totalWorkService as a Year_Month_Day string
	 */
	public String getTotalCalculatedServiceInDaysYear_Month_Day() {
		return CoreUtils.getNoOfDaysInYear_Month_DayFormat(totalCalculatedServiceInDays);
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
    public void prepareNewEmployeeInfo() {
    	Employee employee = getEmployeeHome().getInstance();
    	if(employee.getEmployeeInfo() == null) {

    		
    		EmployeeInfo ei = new EmployeeInfo(employee, "", null, "", null, "", null, false, null, false, null, false, null, false, null, SectorType.PUBLIC_SECTOR, 0, 0, 0, 0);
    		//ei.resetEmployeeInfo();
    		getEntityManager().persist(ei);

    		
    		RankInfo rInfo = new RankInfo(RankType.RANK_ST, 0, EducationalLevelType.UNIVERSITY_EDUCATION_LEVEL);
    		rInfo.setEmployeeInfo(ei);
    		getEntityManager().persist(rInfo);
    		
    		
    		ei.getRankInfos().add(rInfo);
    		ei.setCurrentRankInfo(rInfo);
  		
    		ei.setEmployee(employee);
    		employee.setEmployeeInfo(ei);
    		
//    		getEntityManager().persist(employee);
    		
    		

        }
    }
	
	
    /* this method is called when the user clicks the "add new rank info" */
    public void prepareNewRankInfo() {
        //employeeInfoHome.clearInstance();
        RankInfo rinfo = new RankInfo();
        rinfo.resetRankInfo();
        rinfo.setEmployeeInfo(employeeInfoHome.getInstance());
        
        employeeInfoHome.getInstance().setCurrentRankInfo(rinfo);

    }
    
    public void recalculateTotalWorkService() {
    	EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
    	int totalWorkService = 0;
    	if(totalWorkServiceCalculationDate != null && employeeInfo.getGogAppointmentDate()!=null)
    		totalWorkService = CoreUtils.datesDifferenceIn360DaysYear(employeeInfo.getEntryIntoServiceDate(), totalWorkServiceCalculationDate) + getSumOfExperience();
    	if(totalWorkService != 0)
    		setTotalCalculatedServiceInDays(totalWorkService);
    }
    
    public Integer getEducationalService() {
    	EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
    	if(employeeInfo == null)
    		return 0;
    	else 
    		return employeeInfo.getSumOfEducationalExperience() + employeeInfo.getTotalWorkService();
    }
    
    public String getEducationalServiceYear_Month_Day() {
    	return CoreUtils.getNoOfDaysInYear_Month_DayFormat(getEducationalService());
    }
    
    public Integer getTeachingService() {
    	EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
    	if(employeeInfo == null)
    		return 0;
    	else 
    		return employeeInfo.getSumOfTeachingExperience() + employeeInfo.getTotalWorkService();
    }
    
    public String getTeachingServiceYear_Month_Day() {
    	return CoreUtils.getNoOfDaysInYear_Month_DayFormat(getTeachingService());
    }

    public Integer getSumOfExperience() {
    	EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
    	if(employeeInfo == null)
    		return 0;
    	else 
    		return employeeInfo.getSumOfExperience();
    }
    
    public String getSumOfExperienceYear_Month_Day() {
    	return CoreUtils.getNoOfDaysInYear_Month_DayFormat(getSumOfExperience());
    }
    
    public Integer getTotalServiceIncludingWorkExperience() {
    	EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
    	if(employeeInfo == null)
    		return 0;
    	else 
    		return employeeInfo.getTotalWorkService() + employeeInfo.getSumOfExperience();
    }
    
    public String getTotalServiceIncludingWorkExperienceYear_Month_Day() {
    	return CoreUtils.getNoOfDaysInYear_Month_DayFormat(getTotalServiceIncludingWorkExperience());
    }
    
}
