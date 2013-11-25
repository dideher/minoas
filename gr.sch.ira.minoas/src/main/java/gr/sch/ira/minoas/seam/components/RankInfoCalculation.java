package gr.sch.ira.minoas.seam.components;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

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
    
	public void recalculateRankInfo(Employee employee) {
		
		try {
        	EmployeeInfo eInfo = employee.getEmployeeInfo();
        	RankInfo rankInfo = eInfo.getCurrentRankInfo();
        	
        	RankInfo recalculatedRankInfo = CoreUtils.RecalculateRankInfo(rankInfo, employee, workExperienceCalculation);
			
			java.text.DateFormat df = java.text.SimpleDateFormat.getDateInstance(java.text.SimpleDateFormat.SHORT);
			
			if(!rankInfo.getRank().equals(recalculatedRankInfo.getRank()) || rankInfo.getSalaryGrade()!=recalculatedRankInfo.getSalaryGrade()) {
				RegularEmployeeInfo reinfo = employee.getRegularEmployeeInfo();
				String AM = new String();
				if (reinfo!= null) {
					AM = reinfo.getRegistryID();
				}
				
//				info("|"+ AM +
//						"|"+employee.getLastName() + 
//						"|" + employee.getFirstName() + 
//						"|" + employee.getFatherName() +
//						"|" + rankInfo.prettyToString() + 
//						"|Πλεονάζ. Χρόνος στο βαθμό την|" + df.format(rankInfo.getLastRankDate()) + "|" + rankInfo.getSurplusTimeInRankYear_Month_Day() + "|"+rankInfo.getSurplusTimeInRank() + "|" +
//						"|Πλεονάζ. Χρόνος στο βαθμό σήμερα|" + rankInfo.getSurplusTimeInRankUntilTodayYear_Month_Day() + "|"+rankInfo.getSurplusTimeInRankUntilToday() + "|" +
//						"|Πλεονάζ. Χρόνος στο Μ.Κ. την|" + df.format(rankInfo.getLastSalaryGradeDate()) + "|" + rankInfo.getSurplusTimeInSalaryGradeYear_Month_Day() + "|"+rankInfo.getSurplusTimeInSalaryGrade() + "|" +
//						"|Πλεονάζ. Χρόνος στο Μ.Κ. σήμερα|" + rankInfo.getSurplusTimeInSalaryGradeUntilTodayYear_Month_Day() + "|"+rankInfo.getSurplusTimeInSalaryGradeUntilToday() + "|" +
//						"|πάει στο|" + recalculatedRankInfo.prettyToString() + 
//						"|Πλεονάζ. Χρόνος στο βαθμό την|" + df.format(recalculatedRankInfo.getLastRankDate()) + "|" + recalculatedRankInfo.getSurplusTimeInRankYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInRank() + "|" +
//						"|Πλεονάζ. Χρόνος στο βαθμό σήμερα|" + recalculatedRankInfo.getSurplusTimeInRankUntilTodayYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInRankUntilToday() + "|" +
//						"|Πλεονάζ. Χρόνος στο Μ.Κ. την|" + df.format(recalculatedRankInfo.getLastSalaryGradeDate()) + "|" + recalculatedRankInfo.getSurplusTimeInSalaryGradeYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInSalaryGrade() + "|" +
//						"|Πλεονάζ. Χρόνος στο Μ.Κ. σήμερα|" + recalculatedRankInfo.getSurplusTimeInSalaryGradeUntilTodayYear_Month_Day() + "|"+recalculatedRankInfo.getSurplusTimeInSalaryGradeUntilToday() + "|");

				// set InsertedBy to Minoas user username
				recalculatedRankInfo.setInsertedBy(null);
				// set InsertedOn to today's date
				recalculatedRankInfo.setInsertedOn(new Date());
				
				// Link the new RankInfo with the previous one
				recalculatedRankInfo.setPreviousRankInfo(rankInfo);
				
				//	save the new RankInfo
				getEntityManager().persist(recalculatedRankInfo);
				
				//	set the newly created rankInfo as current at the respective employeeInfo
				//	NOTE: that method setCurrentRankInfo(rInfo) also adds rInfo to the Collection of RankInfos within EmployeeInfo !!!!
				eInfo.setCurrentRankInfo(recalculatedRankInfo);

				info("Rank Info #0 for employee #1 has been inserted", recalculatedRankInfo, employee);

			} else {
//				info("Recalculated RankInfo ("+recalculatedRankInfo.prettyToString()+") matches the current. Skipping RankInfo update for emplooyee: " + rankInfo.getEmployeeInfo().getEmployee().getLastName() + 
//						" " + rankInfo.getEmployeeInfo().getEmployee().getFirstName() + 
//						" " + rankInfo.getEmployeeInfo().getEmployee().getFatherName());
			}
			
        } catch(Exception ex) {
            error(String.format("failed to recalculate rankInfo for employee '%s' due to an exception.", employee), ex);
        }


	} 

}
