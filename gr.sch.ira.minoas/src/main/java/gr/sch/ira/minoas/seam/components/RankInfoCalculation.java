package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.TransactionPropagationType;
import org.jboss.seam.annotations.Transactional;

/**
 * @author <a href="mailto:andreadakis@gmail.com">Yorgos Andreadakis</a>
 * @version $Id$
 */
@Name("rankInfoCalculation")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class RankInfoCalculation extends BaseDatabaseAwareSeamComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @In(value="coreSearching")
    protected CoreSearching coreSearching;
    
    @In(required=true, create=true)
    private WorkExperienceCalculation workExperienceCalculation;
    
	public void recalculateRankInfos() {
		Date today = new Date();
	    info("We will recalculate current RegularInfos as of today (#0)", today);
		Collection<RankInfo> rankInfos = coreSearching.getCurrentRankInfosForActiveEmployees();
		info("found totally '#0' current RankInfos that we will examine and check if they need to change.", rankInfos.size());
		int iterations = 0;
		for (Iterator<RankInfo> rInfoItrtr = rankInfos.iterator(); rInfoItrtr.hasNext();) {
			RankInfo rankInfo = (RankInfo) rInfoItrtr.next();
			iterations++;
			EmployeeInfo eInfo = rankInfo.getEmployeeInfo();
			Employee employee = eInfo.getEmployee();
			
			try {
				RankInfo recalculatedRankInfo = CoreUtils.RecalculateRankInfo(rankInfo, employee, workExperienceCalculation);
				
				java.text.DateFormat df = java.text.SimpleDateFormat.getDateInstance(java.text.SimpleDateFormat.SHORT);
				
				if(!rankInfo.getRank().equals(recalculatedRankInfo.getRank()) || rankInfo.getSalaryGrade()!=recalculatedRankInfo.getSalaryGrade()) {
					RegularEmployeeInfo reinfo = rankInfo.getEmployeeInfo().getEmployee().getRegularEmployeeInfo();
					String AM = new String();
					if (reinfo!= null) {
						AM = reinfo.getRegistryID();
					}
					
					info("|"+ AM +
							"|"+rankInfo.getEmployeeInfo().getEmployee().getLastName() + 
							"|" + rankInfo.getEmployeeInfo().getEmployee().getFirstName() + 
							"|" + rankInfo.getEmployeeInfo().getEmployee().getFatherName() +
							"|" + rankInfo.prettyToString() + 
							"|Πλεονάζ. Χρόνος στο βαθμό την|" + df.format(rankInfo.getLastRankDate()) + "|" + rankInfo.getSurplusTimeInRankYear_Month_Day() + "|"+rankInfo.getSurplusTimeInRank() + "|" +
							"|Πλεονάζ. Χρόνος στο βαθμό σήμερα|" + rankInfo.getSurplusTimeInRankUntilTodayYear_Month_Day() + "|"+rankInfo.getSurplusTimeInRankUntilToday() + "|" +
							"|Πλεονάζ. Χρόνος στο Μ.Κ. την|" + df.format(rankInfo.getLastSalaryGradeDate()) + "|" + rankInfo.getSurplusTimeInSalaryGradeYear_Month_Day() + "|"+rankInfo.getSurplusTimeInSalaryGrade() + "|" +
							"|Πλεονάζ. Χρόνος στο Μ.Κ. σήμερα|" + rankInfo.getSurplusTimeInSalaryGradeUntilTodayYear_Month_Day() + "|"+rankInfo.getSurplusTimeInSalaryGradeUntilToday() + "|" +
							"|πάει στο|" + recalculatedRankInfo.prettyToString() + 
							"|Πλεονάζ. Χρόνος στο βαθμό την|" + df.format(recalculatedRankInfo.getLastRankDate()) + "|" + recalculatedRankInfo.getSurplusTimeInRankYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInRank() + "|" +
							"|Πλεονάζ. Χρόνος στο βαθμό σήμερα|" + recalculatedRankInfo.getSurplusTimeInRankUntilTodayYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInRankUntilToday() + "|" +
							"|Πλεονάζ. Χρόνος στο Μ.Κ. την|" + df.format(recalculatedRankInfo.getLastSalaryGradeDate()) + "|" + recalculatedRankInfo.getSurplusTimeInSalaryGradeYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInSalaryGrade() + "|" +
							"|Πλεονάζ. Χρόνος στο Μ.Κ. σήμερα|" + recalculatedRankInfo.getSurplusTimeInSalaryGradeUntilTodayYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInSalaryGradeUntilToday() + "|");
	
//					// set InsertedBy to Minoas user username
//					recalculatedRankInfo.setInsertedBy(null);
//					// set InsertedOn to today's date
//					recalculatedRankInfo.setInsertedOn(new Date());
//					
//					// Link the new RankInfo with the previous one
//					recalculatedRankInfo.setPreviousRankInfo(rankInfo);
//					
//					//	save the new RankInfo
//					getEntityManager().persist(recalculatedRankInfo);
//					
//					//	set the newly created rankInfo as current at the respective employeeInfo
//					//	NOTE: that method setCurrentRankInfo(rInfo) also adds rInfo to the Collection of RankInfos within EmployeeInfo !!!!
//					eInfo.setCurrentRankInfo(recalculatedRankInfo);
//	
//					// Save (flush) every 100 rows
//					if(iterations % 100 == 0) {
//						getEntityManager().flush();
//		            }
//					info("Rank Info #0 for employee #1 has been inserted", recalculatedRankInfo, employee);

				} else {
					info("Recalculated RankInfo ("+recalculatedRankInfo.prettyToString()+") matches the current. Skipping RankInfo for emplooyee: " + rankInfo.getEmployeeInfo().getEmployee().getLastName() + 
							" " + rankInfo.getEmployeeInfo().getEmployee().getFirstName() + 
							" " + rankInfo.getEmployeeInfo().getEmployee().getFatherName());
				}
		    } catch(Exception ex) {
	            error(String.format("failed to update employee '%s' RankInfo due to an exception.", rankInfo.getEmployeeInfo().getEmployee()), ex);
	        }
		}

	} 

}
