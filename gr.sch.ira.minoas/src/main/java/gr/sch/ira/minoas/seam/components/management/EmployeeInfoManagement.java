package gr.sch.ira.minoas.seam.components.management;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.employee.Employee;
import gr.sch.ira.minoas.model.employee.EmployeeInfo;
import gr.sch.ira.minoas.model.employee.EmployeeType;
import gr.sch.ira.minoas.model.employee.RankInfo;
import gr.sch.ira.minoas.model.employee.RegularEmployeeInfo;
import gr.sch.ira.minoas.model.employement.Employment;
import gr.sch.ira.minoas.seam.components.BaseDatabaseAwareSeamComponent;
import gr.sch.ira.minoas.seam.components.CoreSearching;
import gr.sch.ira.minoas.seam.components.home.EmployeeHome;
import gr.sch.ira.minoas.seam.components.home.EmployeeInfoHome;
import gr.sch.ira.minoas.seam.components.home.RankInfoHome;

import java.util.Date;

import org.jboss.seam.ScopeType;
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

	// create=true : Auto create employeeInfoHome when needed
	@In(required = true, create = true)
	EmployeeInfoHome employeeInfoHome;

	// create=true : Auto create rankInfoHome when needed
	@In(required = true, create = true)
	RankInfoHome rankInfoHome;

	private Date totalWorkServiceCalculationDate;
	private int totalCalculatedServiceInDays;

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
	 * @param totalCalculatedServiceInDays
	 *            the totalCalculatedServiceInDays to set
	 */
	public void setTotalCalculatedServiceInDays(int totalCalculatedServiceInDays) {
		this.totalCalculatedServiceInDays = totalCalculatedServiceInDays;
	}

	/**
	 * @return the totalWorkService as a Year_Month_Day string
	 */
	public String getTotalCalculatedServiceInDaysYear_Month_Day() {
		return CoreUtils
				.getNoOfDaysInYear_Month_DayFormat(totalCalculatedServiceInDays);
	}

	/**
	 * @return the employeeHome
	 */
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	/**
	 * @param employeeHome
	 *            the employeeHome to set
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
	 * @param coreSearching
	 *            the coreSearching to set
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
	 * @param employeeInfoHome
	 *            the employeeInfoHome to set
	 */
	public void setEmployeeInfoHome(EmployeeInfoHome employeeInfoHome) {
		this.employeeInfoHome = employeeInfoHome;
	}

	/**
	 * @return the rankInfoHome
	 */
	public RankInfoHome getRankInfoHome() {
		return rankInfoHome;
	}

	/**
	 * @param rankInfoHome
	 *            the rankInfoHome to set
	 */
	public void setRankInfoHome(RankInfoHome rankInfoHome) {
		this.rankInfoHome = rankInfoHome;
	}


	/**
	 * Prepare EmployeeInfoHome & RankInfoHome objects for the update or insertion of data   
	 */
	public void prepareForNewOrUpdateEmployeeInfo() {
		Employee employee = getEmployeeHome().getInstance();

		//	If employee HAS EmployeeInfo data
		if (employee.getEmployeeInfo() != null) {
			
			//	Set EmployeeInfoHome with the Employee's EmployeeInfo data
			employeeInfoHome.setId(employee.getEmployeeInfo().getId());
			
			//	If EmployeeInfo HAS a current RankInfo
			if (employee.getEmployeeInfo().getCurrentRankInfo() != null) {
				//	Set RankInfoHome with the Employee's RankInfo data
				rankInfoHome.setId(employee.getEmployeeInfo().getCurrentRankInfo().getId());
			}
		}
		
		//	If employee HAS EmployeeInfo data
		if (employeeInfoHome.isManaged()) {
			/* 
			 * we are updating an existing EmployeeInfo & RankInfo, thus do nothing 
			 * */
		} else {	//	If employee HAS NO EmployeeInfo data
			/* Create new employee & rank info home objects. (see the create=true directives at lines 42 & 46 )*/
			employeeInfoHome.getInstance();
			rankInfoHome.getInstance();
		}
	}

	public void cancelInsertOrUpdateEmployeeInfo() {
		//	Clear the auto created home object if cancel is pressed when updating or inserting rank info data 
		rankInfoHome.clearInstance();
	}

	public String insertOrUpdateEmployeeInfo() {
		if (employeeHome.isManaged()) {

			Employee employee = getEmployeeHome().getInstance();
			EmployeeInfo employeeInfo = employeeInfoHome.getInstance();
			RankInfo rInfo = rankInfoHome.getInstance();

			//	If a user sets a date but forgets to check the corresponding checkbox, assume that he set the date by mistake and clear it.
			if (!employeeInfo.getHasAMasterDegree())
				employeeInfo.setMscDate(null);
			if (!employeeInfo.getHasAPhD())
				employeeInfo.setPhdDate(null);
			if (!employeeInfo.getIsANatSchPubAdminGraduate())
				employeeInfo.setNatSchPubAdminDate(null);

			//	If a user sets a MSc, PhD or a NatSchPubAdmin qualification but forgets to set corresponding date obtained, 
			//	oblige him to set the date.
			if (employeeInfo.getHasAMasterDegree()
					&& employeeInfo.getMscDate() == null) {
				facesMessages.add(Severity.ERROR,
						"Δεν ορίσατε την ημερομηνία Μεταπτυχιακού.");
				return ACTION_OUTCOME_FAILURE;
			} else if (employeeInfo.getHasAPhD()
					&& employeeInfo.getPhdDate() == null) {
				facesMessages.add(Severity.ERROR,
						"Δεν ορίσατε την ημερομηνία Διδακτορικού.");
				return ACTION_OUTCOME_FAILURE;
			} else if (employeeInfo.getIsANatSchPubAdminGraduate()
					&& employeeInfo.getNatSchPubAdminDate() == null) {
				facesMessages.add(Severity.ERROR,
						"Δεν ορίσατε την ημερομηνία Ανώτ. Σχολ. Δημ. Διοίκ.");
				return ACTION_OUTCOME_FAILURE;
			}

			//	If employee HAS EmployeeInfo data, then he is changing existing data
			if (employeeInfoHome.isManaged()) {
				info("trying to update employee info #0", employeeInfoHome);

				//	Apply changes to the existing employeeInfo and rankInfo data
				employeeInfoHome.update();

				info("employee's #0 employee info #1 has been updated!",
						employeeInfoHome.getInstance().getEmployee(),
						employeeInfoHome.getInstance());
				return ACTION_OUTCOME_SUCCESS;
			} 
			else {	//	If employee HAD EmployeeInfo data, then 
				info("trying to insert employee info #0", employeeInfoHome);

				//	set the employee to the newly created employeeInfo object
				employeeInfo.setEmployee(employee);
				//	save the newly created employeeInfo object
				employeeInfoHome.persist();
				//	set the employeeInfo to the newly created rankInfo object
				rInfo.setEmployeeInfo(employeeInfo);
				//	save the newly created rankInfo object
				rankInfoHome.persist();
				//	set the newly created rankInfo as current at the respective employeeInfo 
				//	NOTE: that method setCurrentRankInfo(rInfo) also adds rInfo to the Collection of RankInfos within EmployeeInfo !!!! 
				employeeInfo.setCurrentRankInfo(rInfo);

				//	set the newly created employeeInfo as at the respective employee
				employee.setEmployeeInfo(employeeInfo);

				//	Save
				getEntityManager().flush();

				info("employee's #0 employee info #1 has been inserted!",
						employeeInfoHome.getInstance().getEmployee(),
						employeeInfoHome.getInstance());
				return ACTION_OUTCOME_SUCCESS;
			}

		} else {
			facesMessages.add(Severity.ERROR,
					"employee home #0 is not managed.", employeeHome,
					employeeHome.getInstance());
			return ACTION_OUTCOME_FAILURE;
		}

	}

	/**
	 * Prepare RankInfoHome objects for the insertion of new RankInfo
	 */
	public void prepareNewRankInfo() {
		//	Clear any previous data in RankInfo
		rankInfoHome.clearInstance();

		
		if (!rankInfoHome.isManaged()) {
			/* new rank info */
			RankInfo rInfo = rankInfoHome.getInstance();
			//	Reset to default values ( ΣΤ 0 )
			rInfo.resetRankInfo();
		}
	}

	public void cancelInsertRankInfo() {
		//	Clear the auto created home object if cancel is pressed when inserting rank info data
		rankInfoHome.clearInstance();
	}

	@Transactional
	public String insertRankInfo() {

		if (employeeHome.isManaged()) {
			Employee employee = getEmployeeHome().getInstance();
			EmployeeInfo employeeInfo = employee.getEmployeeInfo();
			RankInfo rinfo = rankInfoHome.getInstance();

			// set InsertedBy to Minoas user username
			rinfo.setInsertedBy(getPrincipal());
			// set InsertedOn to today's date
			rinfo.setInsertedOn(new Date());

			//	set EmployeeInfo to the new RankInfo
			rinfo.setEmployeeInfo(employeeInfo);
			//	save the new RankInfo
			getEntityManager().persist(rinfo);
			
			//	set the newly created rankInfo as current at the respective employeeInfo
			//	NOTE: that method setCurrentRankInfo(rInfo) also adds rInfo to the Collection of RankInfos within EmployeeInfo !!!!
			employeeInfo.setCurrentRankInfo(rinfo);

			// Save
			getEntityManager().flush();

			info("Rank Info #0 for employee #1 has been created", rinfo,
					employee);
			return ACTION_OUTCOME_SUCCESS;

		} else {
			facesMessages.add(Severity.ERROR,
					"employee home #0 is not managed.", employeeHome);
			return ACTION_OUTCOME_FAILURE;
		}
	}

	
	/**
	 * totalWorkService = Ημ/νία Διορισμού + Συνολική Υπηρεσία + Συνολική Προϋπηρεσία
	 */
	public void recalculateTotalWorkService() {
		//EmployeeInfo employeeInfo = employeeHome.getInstance().getEmployeeInfo();
		Employee employee = employeeHome.getInstance();
		if (employee.getType() == EmployeeType.REGULAR) {
			RegularEmployeeInfo regularEmployeeInfo = employee.getRegularEmployeeInfo();
			Employment employment = employee.getCurrentEmployment();
			int totalWorkService = 0;
			if (totalWorkServiceCalculationDate != null
					&& regularEmployeeInfo.getGogAppointmentDate() != null)
				totalWorkService = CoreUtils.datesDifferenceIn360DaysYear(
						employment.getEntryIntoServiceDate(),
						totalWorkServiceCalculationDate)
						+ getSumOfExperience();
			if (totalWorkService != 0)
				setTotalCalculatedServiceInDays(totalWorkService);
		} else if (employee.getType() == EmployeeType.DEPUTY || employee.getType() == EmployeeType.HOURLYPAID) {
			
			
			
			// Total Work Service Recalculation for DEPUTY and HOURLY PAID pending!!!!
			
			
//			Employment employment = employee.getCurrentEmployment();
//			int totalWorkService = 0;
//			if (totalWorkServiceCalculationDate != null
//					&& regularEmployeeInfo.getGogAppointmentDate() != null)
//				totalWorkService = CoreUtils.datesDifferenceIn360DaysYear(
//						employment.getEntryIntoServiceDate(),
//						totalWorkServiceCalculationDate)
//						+ getSumOfExperience();
//			if (totalWorkService != 0)
//				setTotalCalculatedServiceInDays(totalWorkService);
			
			
		}
		
		
	}

	/**
	 * @return Συνολική Εκπ/κή Υπηρεσία = Εκπ/κή Προϋπηρεσία + Συνολική Υπηρεσία  
	 */
	public Integer getEducationalService() {
		EmployeeInfo employeeInfo = employeeHome.getInstance()
				.getEmployeeInfo();
		if (employeeInfo == null)
			return 0;
		else
			return employeeInfo.getSumOfEducationalExperience()
					+ employeeInfo.getTotalWorkService();
	}

	/**
	 * @return Εκπ/κή Υπηρεσία σε μορφή εε Έτη μμ Μήνες ηη Ημέρες  
	 */
	public String getEducationalServiceYear_Month_Day() {
		return CoreUtils
				.getNoOfDaysInYear_Month_DayFormat(getEducationalService());
	}

	/**
	 * @return Συνολική Διδακτική Υπηρεσία = Διδακτική Προϋπηρεσία + Συνολική Υπηρεσία  
	 */
	public Integer getTeachingService() {
		EmployeeInfo employeeInfo = employeeHome.getInstance()
				.getEmployeeInfo();
		if (employeeInfo == null)
			return 0;
		else
			return employeeInfo.getSumOfTeachingExperience()
					+ employeeInfo.getTotalWorkService();
	}

	/**
	 * @return Διδακτική Υπηρεσία σε μορφή εε Έτη μμ Μήνες ηη Ημέρες  
	 */
	public String getTeachingServiceYear_Month_Day() {
		return CoreUtils
				.getNoOfDaysInYear_Month_DayFormat(getTeachingService());
	}

	/**
	 * @return Συνολική Προϋπηρεσία  
	 */
	public Integer getSumOfExperience() {
		EmployeeInfo employeeInfo = employeeHome.getInstance()
				.getEmployeeInfo();
		if (employeeInfo == null)
			return 0;
		else
			return employeeInfo.getSumOfExperience();
	}

	/**
	 * @return Συνολική Προϋπηρεσία σε μορφή εε Έτη μμ Μήνες ηη Ημέρες  
	 */
	public String getSumOfExperienceYear_Month_Day() {
		return CoreUtils
				.getNoOfDaysInYear_Month_DayFormat(getSumOfExperience());
	}

	/**
	 * @return Συνολική Υπηρεσία μαζί με την Προϋπηρεσία  
	 */
	public Integer getTotalServiceIncludingWorkExperience() {
		EmployeeInfo employeeInfo = employeeHome.getInstance()
				.getEmployeeInfo();
		if (employeeInfo == null)
			return 0;
		else
			return employeeInfo.getTotalWorkService()
					+ employeeInfo.getSumOfExperience();
	}

	/**
	 * @return Συνολική Υπηρεσία μαζί με την Προϋπηρεσία σε μορφή εε Έτη μμ Μήνες ηη Ημέρες  
	 */
	public String getTotalServiceIncludingWorkExperienceYear_Month_Day() {
		return CoreUtils
				.getNoOfDaysInYear_Month_DayFormat(getTotalServiceIncludingWorkExperience());
	}

}
