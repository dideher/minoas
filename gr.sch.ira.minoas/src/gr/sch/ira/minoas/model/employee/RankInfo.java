package gr.sch.ira.minoas.model.employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;

@Entity
@Table(name = "RANK_INFO")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RankInfo extends BaseIDModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Join RankInfo with its respective EmployeeInfo
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_INFO_ID", nullable = false)
	private EmployeeInfo employeeInfo;

	
	/**
	 * Εναρκτήριο Μ.Κ. για όλους τους βαθμούς και όλα τα επίπεδα σπουδών
	 */
	private static final int startingSalaryGrade = 0;
	
	/**
	 * Ελάχιστο απαραίτητο Μ.Κ. για την προαγωγή στον επόμενο βαθμό για όλους τους βαθμούς και όλα τα επίπεδα σπουδών
	 */
	private static final int minSalaryGrade4Promotion = 1;
	
	
	//				Πανεπιστημιακής Εκπαίδευσης
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό ΣΤ στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_ST = 0;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Ε στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_E = 2;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Δ στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_D = 3;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Γ στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_C = 4;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Β στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_B = 11;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Α στους Πανεπιστημιακής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4UERank_A = 9;
	
	
	//				Τεχνολογικής Εκπαίδευσης
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό ΣΤ στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_ST = 0;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Ε στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_E = 2;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Δ στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_D = 3;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Γ στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_C = 5;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Β στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_B = 11;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Α στους Τεχνολογικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4TERank_A = 8;


	//				Δευτεροβάθμιας Εκπαίδευσης
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό ΣΤ στους Δευτεροβάθμιας Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4SERank_ST = 0;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Ε στους Δευτεροβάθμιας Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4SERank_E = 3;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Δ στους Δευτεροβάθμιας Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4SERank_D = 4;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Γ στους Δευτεροβάθμιας Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4SERank_C = 5;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Β στους Δευτεροβάθμιας Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4SERank_B = 8;

	
	//				Υποχρεωτικής Εκπαίδευσης
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό ΣΤ στους Υποχρεωτικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4CERank_ST = 0;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Ε στους Υποχρεωτικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4CERank_E = 5;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Δ στους Υποχρεωτικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4CERank_D = 6;
	/**
	 * Μέγιστο επιτρεπτό Μ.Κ. για τον βαθμό Γ στους Υποχρεωτικής Εκπαίδευσης
	 */
	private static final int maxSalaryGrade4CERank_C = 12;

	
	/**
	 * Rank (Βαθμός)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "RANK", nullable = false, updatable = false)
	private RankType rank;
	

	/**
	 * Salary Grade (Μισθολογικό Κλιμάκιο - Μ.Κ.)
	 */
	@Basic
	@Column(name = "SALARY_GRADE")
	private int salaryGrade;

	/**
	 * Last Rank Date (Ημερομηνία λήψης τελευταίου Βαθμού)
	 */
	@Basic
	@Column(name = "LAST_RANK_DATE")
	private Date lastRankDate;
	
	/**
	 * Last Salary Grade Date (Ημερομηνία λήψης τελευταίου Μ.Κ.)
	 */
	@Basic
	@Column(name = "LAST_SALARY_GRADE_DATE")
	private Date lastSalaryGradeDate;
	
	/**
	 * Surplus Time in the Rank (Πλεονάζων Χρόνος στο Βαθμό σε αριθμό ημερών)
	 */
	@Basic
	@Column(name = "SURPLUS_TIME_IN_RANK")
	private Integer surplusTimeInRank;
	
	/**
	 * Surplus Time in the Salary Grade (Πλεονάζων Χρόνος στο Μ.Κ. σε αριθμό ημερών)
	 */
	@Basic
	@Column(name = "SURPLUS_TIME_IN_SALARY_GRADE")
	private Integer surplusTimeInSalaryGrade;

	/**
	 * Educational Level (Βαθμός Εκπαίδευσης)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "EDUCATIONAL_LEVEL", nullable = false, updatable = false)
	private EducationalLevelType educationalLevel;

	
	/**
	 * @param rank
	 * @param salaryGrade
	 * @param educationalLevel
	 */
	public RankInfo(RankType rank, int salaryGrade, EducationalLevelType educationalLevel) {
		super();
		this.rank = rank;
		this.salaryGrade = salaryGrade;
		this.educationalLevel = educationalLevel;
	}
	
	
	/**
	 * @param rank
	 * @param salaryGrade
	 * @param educationalLevel
	 */
	public RankInfo(RankInfo rankInfo) {
		super();
		this.rank = rankInfo.getRank();
		this.salaryGrade = rankInfo.getSalaryGrade();
		this.educationalLevel = rankInfo.getEducationalLevel();
	}
	

	/**
	 * 
	 */
	public RankInfo() {
		super();
	}
	
	/**
	 * @param hasPossitiveValidationInLast2Years If the Employee has a positive evaluation in the last two years then he is eligible for promotion.
	 * @param achievedPromotionQuota If Employee was included in the promotions quota (Ποσόστωση προαγωγών: 90% για τον Δ, 80% για τον Γ, 70% για τον Β, και 30% για τον Α).
	 * @return Return the new Rank after the promotion. Note that the new RankInfo returned may be identical to the one before if hasPossitiveValidationInLast2Years is false.  
	 */
	public RankInfo promote(Boolean hasPossitiveValidationInLast2Years, Boolean achievedPromotionQuota) {
		switch (educationalLevel) {
		case UNIVERSITY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if(hasPossitiveValidationInLast2Years)		//	Αν έλαβε θετική αξιολόγηση
					increaseRank();							//	προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4UERank_E)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4UERank_D)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Β, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4UERank_C)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Α, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4UERank_B)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_A:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < maxSalaryGrade4UERank_A)		
						salaryGrade++;								//	Aνέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case TECHNOLOGIGAL_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if(hasPossitiveValidationInLast2Years)		//	Αν έλαβε θετική αξιολόγηση
					increaseRank();							//	προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4TERank_E)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4TERank_D)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Β, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4TERank_C)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Α, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4TERank_B)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_A:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < maxSalaryGrade4TERank_A)		
						salaryGrade++;								//	Aνέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case SECONDARY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if(hasPossitiveValidationInLast2Years)		//	Αν έλαβε θετική αξιολόγηση
					increaseRank();							//	προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4SERank_E)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4SERank_D)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Β, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4SERank_C)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < maxSalaryGrade4SERank_B)	
						salaryGrade++;								//	Ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case COMPULSORY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if(hasPossitiveValidationInLast2Years)		//	Αν έλαβε θετική αξιολόγηση
					increaseRank();							//	προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4CERank_E)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < minSalaryGrade4Promotion)		//	Αν έχει ΜΚ < του ελάχιστου παραμονής στο βαθμό
						salaryGrade++;								//		ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota)				//	Αν έχει ΜΚ >= minSalaryGrade4Promotion (άρα συμπλήρωσε τον ελάχιστο χρόνο παραμονή στον βαθμό) & συμπεριελήφθη στην ποσόστωση προαγωγών
						increaseRank();								//		προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if(salaryGrade < maxSalaryGrade4CERank_D)	//	Αν έχει συμπληρώσει τον ελάχιστο χρόνο παραμονής στον βαθμό αλλά ΔΕΝ συμπεριελήφθη στην ποσόστωση προαγωγών
						salaryGrade++;								//		ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) {			//	Αν έλαβε θετική αξιολόγηση τα 2 τελευταία χρόνια
					if(salaryGrade < maxSalaryGrade4CERank_C)
						salaryGrade++;								//	Ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο επιτρεπόμενο για το βαθμό του και την εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		
		default:
			break;
		}
		
		return this;
	}
	
	
	/**
	 * @return The incremented rank of an employee. E.g. from rank 'E' to rank 'D' 
	 */
	public RankType increaseRank() {
		if(rank != null) {
			int possitionInEnum = rank.ordinal();
			if(possitionInEnum < RankType.values().length-1 ) {
				rank = RankType.values()[possitionInEnum+1];	// set rank to the next RankType
				resetSalaryGrade();								// reset salaryGrade (M.K.) to 0
			}
			return rank;
		}
		return null;
	}
	
	/**
	 * Decrease the Rank (demotion) and set the Salary Grade (Μ.Κ.) to a particular value.
	 * @return The decreased rank of an employee. E.g. from rank 'D' to rank 'E'
	 */
	public RankType decreaseRank(int salaryGrade) {
		if(rank != null) {
			int possitionInEnum = rank.ordinal();
			if(possitionInEnum > 0 ) {
				rank = RankType.values()[possitionInEnum-1];	// set rank to the previous RankType
				this.salaryGrade = salaryGrade;
			}
			return rank;	
		}
		return null;
	}

	/**
	 * Reset Rank (Βαθμός) to ΣΤ along with the Salary Grade to 0
	 */
	public void resetRank() {
		this.rank = RankType.RANK_ST;
		salaryGrade = 0;
	}
	
	/**
	 * Reset Salary Grade (Μισθολογικό Κλιμάκιο) to 0
	 */
	public void resetSalaryGrade() {
		salaryGrade = 0;
	}
	
	/**
	 * Set Rank (Βαθμός) and Salary Grade (Μισθολογικό Κλιμάκιο) to a particular Rank & Salary Grade value.  
	 */
	public void setRankInfo(RankType rank, int salaryGrade) {
		this.rank = rank;
		this.salaryGrade = salaryGrade;
	}
	
	
	
	
	
	/**
	 * @return the rank
	 */
	public RankType getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(RankType rank) {
		this.rank = rank;
	}

	/**
	 * @return the salaryGrade
	 */
	public int getSalaryGrade() {
		return salaryGrade;
	}

	/**
	 * @param salaryGrade the salaryGrade to set
	 */
	public void setSalaryGrade(int salaryGrade) {
		this.salaryGrade = salaryGrade;
	}

	/**
	 * @return the educationalLevel
	 */
	public EducationalLevelType getEducationalLevel() {
		return educationalLevel;
	}

	/**
	 * @param educationalLevel the educationalLevel to set
	 */
	public void setEducationalLevel(EducationalLevelType educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	/**
	 * @return the lastRankDate
	 */
	public Date getLastRankDate() {
		return lastRankDate;
	}


	/**
	 * @param lastRankDate the lastRankDate to set
	 */
	public void setLastRankDate(Date lastRankDate) {
		this.lastRankDate = lastRankDate;
	}


	/**
	 * @return the lastSalaryGradeDate
	 */
	public Date getLastSalaryGradeDate() {
		return lastSalaryGradeDate;
	}


	/**
	 * @param lastSalaryGradeDate the lastSalaryGradeDate to set
	 */
	public void setLastSalaryGradeDate(Date lastSalaryGradeDate) {
		this.lastSalaryGradeDate = lastSalaryGradeDate;
	}


	public String toString() {
		return "Βαθμός(Μ.Κ.): "+rank+"("+salaryGrade+")";
	}


	/**
	 * @return the employeeInfo
	 */
	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}


	/**
	 * @param employeeInfo the employeeInfo to set
	 */
	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	/**
	 * @return the surplusTimeInRank
	 */
	public Integer getSurplusTimeInRank() {
		return surplusTimeInRank;
	}

	/**
	 * @param surplusTimeInRank the surplusTimeInRank to set
	 */
	public void setSurplusTimeInRank(Integer surplusTimeInRank) {
		this.surplusTimeInRank = surplusTimeInRank;
	}

	/**
	 * @return the surplusTimeInSalaryGrade
	 */
	public Integer getSurplusTimeInSalaryGrade() {
		return surplusTimeInSalaryGrade;
	}

	/**
	 * @param surplusTimeInSalaryGrade the surplusTimeInSalaryGrade to set
	 */
	public void setSurplusTimeInSalaryGrade(Integer surplusTimeInSalaryGrade) {
		this.surplusTimeInSalaryGrade = surplusTimeInSalaryGrade;
	}

	/**
	 * @return the startingsalarygrade
	 */
	public static int getStartingsalarygrade() {
		return startingSalaryGrade;
	}
	
	
}
