package gr.sch.ira.minoas.model.employee;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.core.CoreUtils;
import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.employement.EducationalLevelType;

@Entity
@Table(name = "RANK_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RankInfo extends BaseIDModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Join RankInfo with its respective EmployeeInfo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_INFO_ID", nullable = false)
	private EmployeeInfo employeeInfo;

	/**
	 * Εναρκτήριο Μ.Κ. για όλους τους βαθμούς και όλα τα επίπεδα σπουδών
	 */
	private static final int startingSalaryGrade = 0;

	/**
	 * Ελάχιστο απαραίτητο Μ.Κ. για την προαγωγή στον επόμενο βαθμό για όλους
	 * τους βαθμούς και όλα τα επίπεδα σπουδών
	 */
	private static final int minSalaryGrade4Promotion = 1;

	// Πανεπιστημιακής Εκπαίδευσης
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

	// Τεχνολογικής Εκπαίδευσης
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

	// Δευτεροβάθμιας Εκπαίδευσης
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

	// Υποχρεωτικής Εκπαίδευσης
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
	@Column(name = "RANK", nullable = false, updatable = true)
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
	 * Surplus Time in the Salary Grade (Πλεονάζων Χρόνος στο Μ.Κ. σε αριθμό
	 * ημερών)
	 */
	@Basic
	@Column(name = "SURPLUS_TIME_IN_SALARY_GRADE")
	private Integer surplusTimeInSalaryGrade;

	/**
	 * Educational Level (Βαθμός Εκπαίδευσης)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "EDUCATIONAL_LEVEL", nullable = false, updatable = true)
	private EducationalLevelType educationalLevel;

	/**
	 * Comments (Σχόλια)
	 */
	@Basic
	@Column(name = "COMMENTS")
	private String comments;

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *   the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param rank
	 * @param salaryGrade
	 * @param educationalLevel
	 */
	public RankInfo(RankType rank, int salaryGrade,
			EducationalLevelType educationalLevel) {
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
	 * @param hasPossitiveValidationInLast2Years
	 *            If the Employee has a positive evaluation in the last two
	 *            years then he is eligible for promotion.
	 * @param achievedPromotionQuota
	 *            If Employee was included in the promotions quota (Ποσόστωση
	 *            προαγωγών: 90% για τον Δ, 80% για τον Γ, 70% για τον Β, και
	 *            30% για τον Α).
	 * @return Return the new Rank after the promotion. Note that the new
	 *         RankInfo returned may be identical to the one before if
	 *         hasPossitiveValidationInLast2Years is false.
	 */
	public RankInfo promote(Boolean hasPossitiveValidationInLast2Years,
			Boolean achievedPromotionQuota) {
		switch (getEducationalLevel()) {
		case UNIVERSITY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if (hasPossitiveValidationInLast2Years) // Αν έλαβε θετική
														// αξιολόγηση
					increaseRank(); // προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4UERank_E) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4UERank_D) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Β, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4UERank_C) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Α, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4UERank_B) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_A:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < maxSalaryGrade4UERank_A)
						salaryGrade++; // Aνέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case TECHNOLOGIGAL_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if (hasPossitiveValidationInLast2Years) // Αν έλαβε θετική
														// αξιολόγηση
					increaseRank(); // προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4TERank_E) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4TERank_D) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Β, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4TERank_C) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Α, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4TERank_B) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_A:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < maxSalaryGrade4TERank_A)
						salaryGrade++; // Aνέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case SECONDARY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if (hasPossitiveValidationInLast2Years) // Αν έλαβε θετική
														// αξιολόγηση
					increaseRank(); // προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4SERank_E) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4SERank_D) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Β, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4SERank_C) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_B:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < maxSalaryGrade4SERank_B)
						salaryGrade++; // Ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			default:
				break;
			}
			break;
		case COMPULSORY_EDUCATION_LEVEL:
			switch (rank) {
			case RANK_ST:
				if (hasPossitiveValidationInLast2Years) // Αν έλαβε θετική
														// αξιολόγηση
					increaseRank(); // προαγωγή στον βαθμό Ε, ΜΚ 0
				break;
			case RANK_E:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Δ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4CERank_E) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_D:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < minSalaryGrade4Promotion) // Αν έχει ΜΚ <
																// του ελάχιστου
																// παραμονής στο
																// βαθμό
						salaryGrade++; // ανέβασέ τον ΜΚ.
					else if (achievedPromotionQuota) // Αν έχει ΜΚ >=
														// minSalaryGrade4Promotion
														// (άρα συμπλήρωσε τον
														// ελάχιστο χρόνο
														// παραμονή στον βαθμό)
														// & συμπεριελήφθη στην
														// ποσόστωση προαγωγών
						increaseRank(); // προαγωγή στον βαθμό Γ, ΜΚ 0.
					else if (salaryGrade < maxSalaryGrade4CERank_D) // Αν έχει
																	// συμπληρώσει
																	// τον
																	// ελάχιστο
																	// χρόνο
																	// παραμονής
																	// στον
																	// βαθμό
																	// αλλά ΔΕΝ
																	// συμπεριελήφθη
																	// στην
																	// ποσόστωση
																	// προαγωγών
						salaryGrade++; // ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
				}
				break;
			case RANK_C:
				if (hasPossitiveValidationInLast2Years) { // Αν έλαβε θετική
															// αξιολόγηση τα 2
															// τελευταία χρόνια
					if (salaryGrade < maxSalaryGrade4CERank_C)
						salaryGrade++; // Ανέβασέ τον ΜΚ αλλά μέχρι το μέγιστο
										// επιτρεπόμενο για το βαθμό του και την
										// εκπαιδευτική του βαθμίδα.
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
	 * @return The incremented rank of an employee. E.g. from rank 'E' to rank
	 *         'D'
	 */
	public RankType increaseRank() {
		if (rank != null) {
			int possitionInEnum = rank.ordinal();
			if (possitionInEnum < RankType.values().length - 1) {
				rank = RankType.values()[possitionInEnum + 1]; // set rank to
																// the next
																// RankType
				resetSalaryGrade(); // reset salaryGrade (M.K.) to 0
			}
			return rank;
		}
		return null;
	}

	/**
	 * Decrease the Rank (demotion) and set the Salary Grade (Μ.Κ.) to a
	 * particular value.
	 * 
	 * @return The decreased rank of an employee. E.g. from rank 'D' to rank 'E'
	 */
	public RankType decreaseRank(int salaryGrade) {
		if (rank != null) {
			int possitionInEnum = rank.ordinal();
			if (possitionInEnum > 0) {
				rank = RankType.values()[possitionInEnum - 1]; // set rank to
																// the previous
																// RankType
				this.salaryGrade = salaryGrade;
			}
			return rank;
		}
		return null;
	}

	/**
	 * Reset Rank (Βαθμός) to ΣΤ along with the Salary Grade to 0
	 * 
	 */
	public void resetRankInfo() {
		setRank(RankType.RANK_ST);
		setLastRankDate(new Date());
		setSalaryGrade(0);
		setLastSalaryGradeDate(new Date());
		setEducationalLevel(null);
		setSurplusTimeInRank(0);
		setSurplusTimeInSalaryGrade(0);
	}

	/**
	 * Reset Rank (Βαθμός) to ΣΤ along with the Salary Grade to 0
	 */
	public void resetRank() {
		setRank(RankType.RANK_ST);
		setLastRankDate(new Date());
		setSalaryGrade(0);
		setLastSalaryGradeDate(new Date());

	}

	/**
	 * Reset Salary Grade (Μισθολογικό Κλιμάκιο) to 0
	 */
	public void resetSalaryGrade() {
		setSalaryGrade(0);
	}

	/**
	 * Set Rank (Βαθμός) and Salary Grade (Μισθολογικό Κλιμάκιο) to a particular
	 * Rank & Salary Grade value.
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
	 * @param rank
	 *            the rank to set
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
	 * @param salaryGrade
	 *            the salaryGrade to set
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
	 * @param educationalLevel
	 *            the educationalLevel to set
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
	 * @param lastRankDate
	 *            the lastRankDate to set
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
	 * @param lastSalaryGradeDate
	 *            the lastSalaryGradeDate to set
	 */
	public void setLastSalaryGradeDate(Date lastSalaryGradeDate) {
		this.lastSalaryGradeDate = lastSalaryGradeDate;
	}

	/**
	 * @return the employeeInfo
	 */
	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	/**
	 * @param employeeInfo
	 *            the employeeInfo to set
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
	 * @return the surplusTimeInRank in Year_Month_Day formated string
	 */
	public String getSurplusTimeInRankYear_Month_Day() {
		return CoreUtils.Year_Month_Day(surplusTimeInRank);
	}

	/**
	 * @param surplusTimeInRank
	 *            the surplusTimeInRank to set
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
	 * @return the surplusTimeInSalaryGrade in Year_Month_Day formated string
	 */
	public String getSurplusTimeInSalaryGradeYear_Month_Day() {
		return CoreUtils.Year_Month_Day(surplusTimeInSalaryGrade);
	}

	/**
	 * @param surplusTimeInSalaryGrade
	 *            the surplusTimeInSalaryGrade to set
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

	/**
	 * Η ρουτίνα επιστρέφει τον Βαθμό (Α, Β, Γ, Δ, Ε, ΣΤ) και το ΜΚ (0, 1, 2, 3,
	 * 4, 5, 6) κάποιου υπαλλήλου ανάλογα την Katigoria του (ΥΕ, ΔΕ, ΤΕ, ΠΕ) αν
	 * έχει η όχι Μεταπτυχιακό, Διδακτορικό ή Α.Σ.Δ.Δ., και την Proyphresia του
	 * (σε αριθμό ημερών)
	 * 
	 * @param Proyphresia
	 *            The employee's length of service (Προϋπηρεσία) in number of
	 *            days.
	 * @param educationalLevel
	 *            The employee's educational level from
	 *            gr.sch.ira.minoas.model.employement.EducationalLevelType
	 *            (UNIVERSITY_EDUCATION_LEVEL, TECHNOLOGIGAL_EDUCATION_LEVEL,
	 *            SECONDARY_EDUCATION_LEVEL, COMPULSORY_EDUCATION_LEVEL)
	 * @param hasAMasterDegree
	 *            True if the employee has a Master Degree
	 * @param hasAPhD
	 *            True if the employee has a PhD
	 * @param isANatSchPubAdminGraduate
	 *            True if the employee is a National School of Public
	 *            Administration (Α.Σ.Δ.Δ.) graduate
	 * @return Returnw the employee's Rank info (rank & salary grade) after the
	 *         classification in grade.
	 */
	public RankInfo Katataxi(Integer Proyphresia,
			EducationalLevelType educationalLevel, Boolean hasAMasterDegree,
			Boolean hasAPhD, Boolean isANatSchPubAdminGraduate) {
		setEducationalLevel(educationalLevel);

		if (hasAMasterDegree && (!hasAPhD && !isANatSchPubAdminGraduate)) // Αν
																			// έχει
																			// Μεταπτυχιακό
																			// επιδότησέ
																			// τον
			Proyphresia += 720; // με 2 χρόνια προϋπηρεσία (2 * 360 = 720)
		else if (!hasAMasterDegree && (hasAPhD || isANatSchPubAdminGraduate)) // Αν
																				// έχει
																				// Διδακτορικό
																				// ή
																				// είναι
																				// απόφοιτος
																				// της
																				// Α.Σ.Δ.Δ.
																				// αλλά
																				// δεν
																				// έχει
																				// Master,
																				// επιδότησέ
																				// τον
			Proyphresia += 2160; // με 6 χρόνια προϋπηρεσία (6 * 360 = 2160)
		else if (hasAMasterDegree && (hasAPhD || isANatSchPubAdminGraduate)) // Αν
																				// έχει
																				// Διδακτορικό
																				// ή
																				// είναι
																				// απόφοιτος
																				// της
																				// Α.Σ.Δ.Δ.
																				// και
																				// έχει
																				// ΚΑΙ
																				// Master,
																				// επιδότησέ
																				// τον
			Proyphresia += 2520; // με 7 χρόνια προϋπηρεσία (7 * 360 = 2520)

		switch (getEducationalLevel()) {
		case UNIVERSITY_EDUCATION_LEVEL:
			if (Proyphresia >= 0 && Proyphresia <= 1079) {
				setRank(RankType.RANK_ST);
				setSalaryGrade(0);
			} else if (Proyphresia >= 1080 && Proyphresia <= 3239) {
				setRank(RankType.RANK_E);
				if (Proyphresia >= 1080 && Proyphresia <= 1799) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 1800 && Proyphresia <= 2519) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 2520 && Proyphresia <= 3239) {
					setSalaryGrade(2);
				}
			} else if (Proyphresia >= 3240 && Proyphresia <= 5399) {
				setRank(RankType.RANK_D);
				if (Proyphresia >= 3240 && Proyphresia <= 3959) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 3960 && Proyphresia <= 4679) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 4680 && Proyphresia <= 5399) {
					setSalaryGrade(2);
				}
			} else if (Proyphresia >= 5400 && Proyphresia <= 7559) {
				setRank(RankType.RANK_C);
				if (Proyphresia >= 5400 && Proyphresia <= 6119) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 6120 && Proyphresia <= 6839) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 6840 && Proyphresia <= 7559) {
					setSalaryGrade(2);
				}
			} else if (Proyphresia >= 7560) {
				setRank(RankType.RANK_B);
				if (Proyphresia >= 7560 && Proyphresia <= 8639) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 8640 && Proyphresia <= 9719) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 9720 && Proyphresia <= 10799) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 10800 && Proyphresia <= 11879) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 11880 && Proyphresia <= 12959) {
					setSalaryGrade(4);
				} else if (Proyphresia >= 12960 && Proyphresia <= 14039) {
					setSalaryGrade(5);
				} else if (Proyphresia >= 14040 && Proyphresia <= 15119) {
					setSalaryGrade(6);
				} else if (Proyphresia >= 15120 && Proyphresia <= 16199) {
					setSalaryGrade(7);
				} else if (Proyphresia >= 16200 && Proyphresia <= 17279) {
					setSalaryGrade(8);
				}
			}

			break; // End UNIVERSITY_EDUCATION_LEVEL case
		case TECHNOLOGIGAL_EDUCATION_LEVEL:
			if (Proyphresia >= 0 && Proyphresia <= 1079) {
				setRank(RankType.RANK_ST);
				setSalaryGrade(0);
			} else if (Proyphresia >= 1080 && Proyphresia <= 3239) {
				setRank(RankType.RANK_E);
				if (Proyphresia >= 1080 && Proyphresia <= 1799) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 1800 && Proyphresia <= 2519) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 2520 && Proyphresia <= 3239) {
					setSalaryGrade(2);
				}
			} else if (Proyphresia >= 3240 && Proyphresia <= 5399) {
				setRank(RankType.RANK_D);
				if (Proyphresia >= 3240 && Proyphresia <= 3959) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 3960 && Proyphresia <= 4679) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 4680 && Proyphresia <= 5399) {
					setSalaryGrade(2);
				}
			} else if (Proyphresia >= 5400 && Proyphresia <= 8279) {
				setRank(RankType.RANK_C);
				if (Proyphresia >= 5400 && Proyphresia <= 6119) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 6120 && Proyphresia <= 6839) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 6840 && Proyphresia <= 7559) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 7560 && Proyphresia <= 8279) {
					setSalaryGrade(3);
				}
			} else if (Proyphresia >= 8280) {
				setRank(RankType.RANK_B);
				if (Proyphresia >= 8280 && Proyphresia <= 9359) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 9360 && Proyphresia <= 10439) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 10440 && Proyphresia <= 11519) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 11520 && Proyphresia <= 12599) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 12600 && Proyphresia <= 13679) {
					setSalaryGrade(4);
				} else if (Proyphresia >= 13680 && Proyphresia <= 14759) {
					setSalaryGrade(5);
				} else if (Proyphresia >= 14760 && Proyphresia <= 15839) {
					setSalaryGrade(6);
				} else if (Proyphresia >= 15840 && Proyphresia <= 16919) {
					setSalaryGrade(7);
				}
			}

			break; // End TECHNOLOGIGAL_EDUCATION_LEVEL case
		case SECONDARY_EDUCATION_LEVEL:

			if (Proyphresia >= 0 && Proyphresia <= 1079) {
				setRank(RankType.RANK_ST);
				setSalaryGrade(0);
			} else if (Proyphresia >= 1080 && Proyphresia <= 3959) {
				setRank(RankType.RANK_E);
				if (Proyphresia >= 1080 && Proyphresia <= 1799) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 1800 && Proyphresia <= 2519) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 2520 && Proyphresia <= 3239) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 3240 && Proyphresia <= 3959) {
					setSalaryGrade(3);
				}
			} else if (Proyphresia >= 3960 && Proyphresia <= 6839) {
				setRank(RankType.RANK_D);
				if (Proyphresia >= 3960 && Proyphresia <= 4679) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 4680 && Proyphresia <= 5399) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 5400 && Proyphresia <= 6119) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 6120 && Proyphresia <= 6839) {
					setSalaryGrade(3);
				}
			} else if (Proyphresia >= 6840 && Proyphresia <= 9719) {
				setRank(RankType.RANK_C);
				if (Proyphresia >= 6840 && Proyphresia <= 7559) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 7560 && Proyphresia <= 8279) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 8280 && Proyphresia <= 8999) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 9000 && Proyphresia <= 9719) {
					setSalaryGrade(3);
				}
			} else if (Proyphresia >= 9720) {
				setRank(RankType.RANK_B);
				if (Proyphresia >= 9720 && Proyphresia <= 10799) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 10800 && Proyphresia <= 11879) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 11880 && Proyphresia <= 12959) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 12960 && Proyphresia <= 14039) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 14040 && Proyphresia <= 15119) {
					setSalaryGrade(4);
				} else if (Proyphresia >= 15120 && Proyphresia <= 16199) {
					setSalaryGrade(5);
				}
			}

			break; // End SECONDARY_EDUCATION_LEVEL case
		case COMPULSORY_EDUCATION_LEVEL:
			if (Proyphresia >= 0 && Proyphresia <= 1079) {
				setRank(RankType.RANK_ST);
				setSalaryGrade(0);
			} else if (Proyphresia >= 1080 && Proyphresia <= 5399) {
				setRank(RankType.RANK_E);
				if (Proyphresia >= 1080 && Proyphresia <= 1799) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 1800 && Proyphresia <= 2519) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 2520 && Proyphresia <= 3239) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 3240 && Proyphresia <= 3959) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 3960 && Proyphresia <= 4679) {
					setSalaryGrade(4);
				} else if (Proyphresia >= 4680 && Proyphresia <= 5399) {
					setSalaryGrade(5);
				}
			} else if (Proyphresia >= 5400 && Proyphresia <= 8999) {
				setRank(RankType.RANK_D);
				if (Proyphresia >= 5400 && Proyphresia <= 6119) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 6120 && Proyphresia <= 6839) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 6840 && Proyphresia <= 7559) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 7560 && Proyphresia <= 8279) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 8280 && Proyphresia <= 8999) {
					setSalaryGrade(4);
				}
			} else if (Proyphresia >= 9000) {
				setRank(RankType.RANK_C);
				if (Proyphresia >= 9000 && Proyphresia <= 9719) {
					setSalaryGrade(0);
				} else if (Proyphresia >= 9720 && Proyphresia <= 10439) {
					setSalaryGrade(1);
				} else if (Proyphresia >= 10440 && Proyphresia <= 11159) {
					setSalaryGrade(2);
				} else if (Proyphresia >= 11160 && Proyphresia <= 11879) {
					setSalaryGrade(3);
				} else if (Proyphresia >= 11880 && Proyphresia <= 12599) {
					setSalaryGrade(4);
				} else if (Proyphresia >= 12600 && Proyphresia <= 13319) {
					setSalaryGrade(5);
				} else if (Proyphresia >= 13320 && Proyphresia <= 14039) {
					setSalaryGrade(6);
				}
			}

			break; // End COMPULSORY_EDUCATION_LEVEL case

		default:
			break;
		}

		return this;
	}

	/**
	 * Η ρουτίνα επιστρέφει τον Πλεονάζοντα Χρόνο στο Βαθμό (Α, Β, Γ, Δ, Ε, ΣΤ)
	 * (σε αριθμό ημερών) κάποιου υπαλλήλου κατά την επανακατάταξή του την
	 * 1/11/2011, ανάλογα το επίπεδο σπουδών του (ΥΕ, ΔΕ, ΤΕ, ΠΕ) αν έχει η όχι
	 * Μεταπτυχιακό, Διδακτορικό ή Α.Σ.Δ.Δ., και την συνολική υπηρεσία του για
	 * κατάταξη(σε αριθμό ημερών)
	 * 
	 * @param SynolYphrGiaKatatx
	 *            The employee's length of service (Προϋπηρεσία) in number of
	 *            days.
	 * @param educationalLevel
	 *            The employee's educational level from
	 *            gr.sch.ira.minoas.model.employement.EducationalLevelType
	 *            (UNIVERSITY_EDUCATION_LEVEL, TECHNOLOGIGAL_EDUCATION_LEVEL,
	 *            SECONDARY_EDUCATION_LEVEL, COMPULSORY_EDUCATION_LEVEL)
	 * @param hasAMasterDegree
	 *            True if the employee has a Master Degree
	 * @param hasAPhD
	 *            True if the employee has a PhD
	 * @param isANatSchPubAdminGraduate
	 *            True if the employee is a National School of Public
	 *            Administration (Α.Σ.Δ.Δ.) graduate
	 * @return Returns the employee's surplus time in his rank after his
	 *         classification on 1/11/2011
	 */
	public Integer SurplusTimeInRankAfterClassification(
			Integer SynolYphrGiaKatatx, EducationalLevelType educationalLevel,
			Boolean hasAMasterDegree, Boolean hasAPhD,
			Boolean isANatSchPubAdminGraduate) {
		RankInfo rankInfo = Katataxi(SynolYphrGiaKatatx, educationalLevel,
				hasAMasterDegree, hasAPhD, isANatSchPubAdminGraduate);

		switch (educationalLevel) {
		case UNIVERSITY_EDUCATION_LEVEL:
			switch (rankInfo.getRank()) {
			case RANK_ST:
				return SynolYphrGiaKatatx - 0;
			case RANK_E:
				return SynolYphrGiaKatatx - 1080;
			case RANK_D:
				return SynolYphrGiaKatatx - 3240;
			case RANK_C:
				return SynolYphrGiaKatatx - 5400;
			case RANK_B:
				return SynolYphrGiaKatatx - 7560;
			default:
				return -1;
			}
			// End UNIVERSITY_EDUCATION_LEVEL case
		case TECHNOLOGIGAL_EDUCATION_LEVEL:
			switch (rankInfo.getRank()) {
			case RANK_ST:
				return SynolYphrGiaKatatx - 0;
			case RANK_E:
				return SynolYphrGiaKatatx - 1080;
			case RANK_D:
				return SynolYphrGiaKatatx - 3240;
			case RANK_C:
				return SynolYphrGiaKatatx - 5400;
			case RANK_B:
				return SynolYphrGiaKatatx - 8280;
			default:
				return -1;
			}
			// End TECHNOLOGIGAL_EDUCATION_LEVEL case
		case SECONDARY_EDUCATION_LEVEL:
			switch (rankInfo.getRank()) {
			case RANK_ST:
				return SynolYphrGiaKatatx - 0;
			case RANK_E:
				return SynolYphrGiaKatatx - 1080;
			case RANK_D:
				return SynolYphrGiaKatatx - 3960;
			case RANK_C:
				return SynolYphrGiaKatatx - 6840;
			case RANK_B:
				return SynolYphrGiaKatatx - 9720;
			default:
				return -1;
			}
			// End SECONDARY_EDUCATION_LEVEL case
		case COMPULSORY_EDUCATION_LEVEL:
			switch (rankInfo.getRank()) {
			case RANK_ST:
				return SynolYphrGiaKatatx - 0;
			case RANK_E:
				return SynolYphrGiaKatatx - 1080;
			case RANK_D:
				return SynolYphrGiaKatatx - 5400;
			case RANK_C:
				return SynolYphrGiaKatatx - 9000;
			default:
				return -1;
			}
			// End COMPULSORY_EDUCATION_LEVEL case
		default:
			return -1;
		}

	}

	/**
	 * Η ρουτίνα επιστρέφει τον πλεονάζοντα χρόνο (σε αριθμό ημερών) στο ΜΚ του
	 * υπαλλήλου μετά την επανακατάταξή του την 1/11/2011 στο νέο βαθμό, ανάλογα
	 * το επίπεδο σπουδών του (ΥΕ, ΔΕ, ΤΕ, ΠΕ) αν έχει η όχι Μεταπτυχιακό,
	 * Διδακτορικό ή Α.Σ.Δ.Δ., και την συνολική υπηρεσία του για κατάταξη(σε
	 * αριθμό ημερών)
	 * 
	 * @param SynolYphrGiaKatatx
	 *            The employee's length of service (Προϋπηρεσία) in number of
	 *            days.
	 * @param educationalLevel
	 *            The employee's educational level from
	 *            gr.sch.ira.minoas.model.employement.EducationalLevelType
	 *            (UNIVERSITY_EDUCATION_LEVEL, TECHNOLOGIGAL_EDUCATION_LEVEL,
	 *            SECONDARY_EDUCATION_LEVEL, COMPULSORY_EDUCATION_LEVEL)
	 * @param hasAMasterDegree
	 *            True if the employee has a Master Degree
	 * @param hasAPhD
	 *            True if the employee has a PhD
	 * @param isANatSchPubAdminGraduate
	 *            True if the employee is a National School of Public
	 *            Administration (Α.Σ.Δ.Δ.) graduate
	 * @return Returns the employee's surplus time in his salary grade after his
	 *         classification on 1/11/2011
	 */
	public Integer SurplusTimeInSalaryGradeAfterClassification(
			Integer synolYphrGiaKatatx, EducationalLevelType educationalLevel,
			Boolean hasAMasterDegree, Boolean hasAPhD,
			Boolean isANatSchPubAdminGraduate) {

		switch (educationalLevel) {
		case UNIVERSITY_EDUCATION_LEVEL:
			if (synolYphrGiaKatatx >= 0 && synolYphrGiaKatatx <= 1079) {
				return synolYphrGiaKatatx - 0;
			} else if (synolYphrGiaKatatx >= 1080 && synolYphrGiaKatatx <= 1799) {
				return synolYphrGiaKatatx - 1080;
			} else if (synolYphrGiaKatatx >= 1800 && synolYphrGiaKatatx <= 2519) {
				return synolYphrGiaKatatx - 1800;
			} else if (synolYphrGiaKatatx >= 2520 && synolYphrGiaKatatx <= 3239) {
				return synolYphrGiaKatatx - 2520;
			} else if (synolYphrGiaKatatx >= 3240 && synolYphrGiaKatatx <= 3959) {
				return synolYphrGiaKatatx - 3240;
			} else if (synolYphrGiaKatatx >= 3960 && synolYphrGiaKatatx <= 4679) {
				return synolYphrGiaKatatx - 3960;
			} else if (synolYphrGiaKatatx >= 4680 && synolYphrGiaKatatx <= 5399) {
				return synolYphrGiaKatatx - 4680;
			} else if (synolYphrGiaKatatx >= 5400 && synolYphrGiaKatatx <= 6119) {
				return synolYphrGiaKatatx - 5400;
			} else if (synolYphrGiaKatatx >= 6120 && synolYphrGiaKatatx <= 6839) {
				return synolYphrGiaKatatx - 6120;
			} else if (synolYphrGiaKatatx >= 6840 && synolYphrGiaKatatx <= 7559) {
				return synolYphrGiaKatatx - 6840;
			} else if (synolYphrGiaKatatx >= 7560 && synolYphrGiaKatatx <= 8639) {
				return synolYphrGiaKatatx - 7560;
			} else if (synolYphrGiaKatatx >= 8640 && synolYphrGiaKatatx <= 9719) {
				return synolYphrGiaKatatx - 8640;
			} else if (synolYphrGiaKatatx >= 9720
					&& synolYphrGiaKatatx <= 10799) {
				return synolYphrGiaKatatx - 9720;
			} else if (synolYphrGiaKatatx >= 10800
					&& synolYphrGiaKatatx <= 11879) {
				return synolYphrGiaKatatx - 10800;
			} else if (synolYphrGiaKatatx >= 11880
					&& synolYphrGiaKatatx <= 12959) {
				return synolYphrGiaKatatx - 11880;
			} else if (synolYphrGiaKatatx >= 12960
					&& synolYphrGiaKatatx <= 14039) {
				return synolYphrGiaKatatx - 12960;
			} else if (synolYphrGiaKatatx >= 14040
					&& synolYphrGiaKatatx <= 15119) {
				return synolYphrGiaKatatx - 14040;
			} else if (synolYphrGiaKatatx >= 15120
					&& synolYphrGiaKatatx <= 16199) {
				return synolYphrGiaKatatx - 15120;
			} else if (synolYphrGiaKatatx >= 16200
					&& synolYphrGiaKatatx <= 17279) {
				return synolYphrGiaKatatx - 16200;
			}

			return -1; // End UNIVERSITY_EDUCATION_LEVEL case
		case TECHNOLOGIGAL_EDUCATION_LEVEL:
			if (synolYphrGiaKatatx >= 0 && synolYphrGiaKatatx <= 1079) {
				return synolYphrGiaKatatx - 0;
			} else if (synolYphrGiaKatatx >= 1080 && synolYphrGiaKatatx <= 1799) {
				return synolYphrGiaKatatx - 1080;
			} else if (synolYphrGiaKatatx >= 1800 && synolYphrGiaKatatx <= 2519) {
				return synolYphrGiaKatatx - 1800;
			} else if (synolYphrGiaKatatx >= 2520 && synolYphrGiaKatatx <= 3239) {
				return synolYphrGiaKatatx - 2520;
			} else if (synolYphrGiaKatatx >= 3240 && synolYphrGiaKatatx <= 3959) {
				return synolYphrGiaKatatx - 3240;
			} else if (synolYphrGiaKatatx >= 3960 && synolYphrGiaKatatx <= 4679) {
				return synolYphrGiaKatatx - 3960;
			} else if (synolYphrGiaKatatx >= 4680 && synolYphrGiaKatatx <= 5399) {
				return synolYphrGiaKatatx - 4680;
			} else if (synolYphrGiaKatatx >= 5400 && synolYphrGiaKatatx <= 6119) {
				return synolYphrGiaKatatx - 5400;
			} else if (synolYphrGiaKatatx >= 6120 && synolYphrGiaKatatx <= 6839) {
				return synolYphrGiaKatatx - 6120;
			} else if (synolYphrGiaKatatx >= 6840 && synolYphrGiaKatatx <= 7559) {
				return synolYphrGiaKatatx - 6840;
			} else if (synolYphrGiaKatatx >= 7560 && synolYphrGiaKatatx <= 8279) {
				return synolYphrGiaKatatx - 7560;
			} else if (synolYphrGiaKatatx >= 8280 && synolYphrGiaKatatx <= 9359) {
				return synolYphrGiaKatatx - 8280;
			} else if (synolYphrGiaKatatx >= 9360
					&& synolYphrGiaKatatx <= 10439) {
				return synolYphrGiaKatatx - 9360;
			} else if (synolYphrGiaKatatx >= 10440
					&& synolYphrGiaKatatx <= 11519) {
				return synolYphrGiaKatatx - 10440;
			} else if (synolYphrGiaKatatx >= 11520
					&& synolYphrGiaKatatx <= 12599) {
				return synolYphrGiaKatatx - 11520;
			} else if (synolYphrGiaKatatx >= 12600
					&& synolYphrGiaKatatx <= 13679) {
				return synolYphrGiaKatatx - 12600;
			} else if (synolYphrGiaKatatx >= 13680
					&& synolYphrGiaKatatx <= 14759) {
				return synolYphrGiaKatatx - 13680;
			} else if (synolYphrGiaKatatx >= 14760
					&& synolYphrGiaKatatx <= 15839) {
				return synolYphrGiaKatatx - 14760;
			} else if (synolYphrGiaKatatx >= 15840
					&& synolYphrGiaKatatx <= 16919) {
				return synolYphrGiaKatatx - 15840;
			}
			return -1; // End TECHNOLOGIGAL_EDUCATION_LEVEL case
		case SECONDARY_EDUCATION_LEVEL:
			if (synolYphrGiaKatatx >= 0 && synolYphrGiaKatatx <= 1079) {
				return synolYphrGiaKatatx - 0;
			} else if (synolYphrGiaKatatx >= 1080 && synolYphrGiaKatatx <= 1799) {
				return synolYphrGiaKatatx - 1080;
			} else if (synolYphrGiaKatatx >= 1800 && synolYphrGiaKatatx <= 2519) {
				return synolYphrGiaKatatx - 1800;
			} else if (synolYphrGiaKatatx >= 2520 && synolYphrGiaKatatx <= 3239) {
				return synolYphrGiaKatatx - 2520;
			} else if (synolYphrGiaKatatx >= 3240 && synolYphrGiaKatatx <= 3959) {
				return synolYphrGiaKatatx - 3240;
			} else if (synolYphrGiaKatatx >= 3960 && synolYphrGiaKatatx <= 4679) {
				return synolYphrGiaKatatx - 3960;
			} else if (synolYphrGiaKatatx >= 4680 && synolYphrGiaKatatx <= 5399) {
				return synolYphrGiaKatatx - 4680;
			} else if (synolYphrGiaKatatx >= 5400 && synolYphrGiaKatatx <= 6119) {
				return synolYphrGiaKatatx - 5400;
			} else if (synolYphrGiaKatatx >= 6120 && synolYphrGiaKatatx <= 6839) {
				return synolYphrGiaKatatx - 6120;
			} else if (synolYphrGiaKatatx >= 6840 && synolYphrGiaKatatx <= 7559) {
				return synolYphrGiaKatatx - 6840;
			} else if (synolYphrGiaKatatx >= 7560 && synolYphrGiaKatatx <= 8279) {
				return synolYphrGiaKatatx - 7560;
			} else if (synolYphrGiaKatatx >= 8280 && synolYphrGiaKatatx <= 8999) {
				return synolYphrGiaKatatx - 8280;
			} else if (synolYphrGiaKatatx >= 9000 && synolYphrGiaKatatx <= 9719) {
				return synolYphrGiaKatatx - 9000;
			} else if (synolYphrGiaKatatx >= 9720
					&& synolYphrGiaKatatx <= 10799) {
				return synolYphrGiaKatatx - 9720;
			} else if (synolYphrGiaKatatx >= 10800
					&& synolYphrGiaKatatx <= 11879) {
				return synolYphrGiaKatatx - 10800;
			} else if (synolYphrGiaKatatx >= 11880
					&& synolYphrGiaKatatx <= 12959) {
				return synolYphrGiaKatatx - 11880;
			} else if (synolYphrGiaKatatx >= 12960
					&& synolYphrGiaKatatx <= 14039) {
				return synolYphrGiaKatatx - 12960;
			} else if (synolYphrGiaKatatx >= 14040
					&& synolYphrGiaKatatx <= 15119) {
				return synolYphrGiaKatatx - 14040;
			} else if (synolYphrGiaKatatx >= 15120
					&& synolYphrGiaKatatx <= 16199) {
				return synolYphrGiaKatatx - 15120;
			}
			return -1; // End SECONDARY_EDUCATION_LEVEL case
		case COMPULSORY_EDUCATION_LEVEL:
			if (synolYphrGiaKatatx >= 0 && synolYphrGiaKatatx <= 1079) {
				return synolYphrGiaKatatx - 0;
			} else if (synolYphrGiaKatatx >= 1080 && synolYphrGiaKatatx <= 1799) {
				return synolYphrGiaKatatx - 1080;
			} else if (synolYphrGiaKatatx >= 1800 && synolYphrGiaKatatx <= 2519) {
				return synolYphrGiaKatatx - 1800;
			} else if (synolYphrGiaKatatx >= 2520 && synolYphrGiaKatatx <= 3239) {
				return synolYphrGiaKatatx - 2520;
			} else if (synolYphrGiaKatatx >= 3240 && synolYphrGiaKatatx <= 3959) {
				return synolYphrGiaKatatx - 3240;
			} else if (synolYphrGiaKatatx >= 3960 && synolYphrGiaKatatx <= 4679) {
				return synolYphrGiaKatatx - 3960;
			} else if (synolYphrGiaKatatx >= 4680 && synolYphrGiaKatatx <= 5399) {
				return synolYphrGiaKatatx - 4680;
			} else if (synolYphrGiaKatatx >= 5400 && synolYphrGiaKatatx <= 6119) {
				return synolYphrGiaKatatx - 5400;
			} else if (synolYphrGiaKatatx >= 6120 && synolYphrGiaKatatx <= 6839) {
				return synolYphrGiaKatatx - 6120;
			} else if (synolYphrGiaKatatx >= 6840 && synolYphrGiaKatatx <= 7559) {
				return synolYphrGiaKatatx - 6840;
			} else if (synolYphrGiaKatatx >= 7560 && synolYphrGiaKatatx <= 8279) {
				return synolYphrGiaKatatx - 7560;
			} else if (synolYphrGiaKatatx >= 8280 && synolYphrGiaKatatx <= 8999) {
				return synolYphrGiaKatatx - 8280;
			} else if (synolYphrGiaKatatx >= 9000 && synolYphrGiaKatatx <= 9719) {
				return synolYphrGiaKatatx - 9000;
			} else if (synolYphrGiaKatatx >= 9720
					&& synolYphrGiaKatatx <= 10439) {
				return synolYphrGiaKatatx - 9720;
			} else if (synolYphrGiaKatatx >= 10440
					&& synolYphrGiaKatatx <= 11159) {
				return synolYphrGiaKatatx - 10440;
			} else if (synolYphrGiaKatatx >= 11160
					&& synolYphrGiaKatatx <= 11879) {
				return synolYphrGiaKatatx - 11160;
			} else if (synolYphrGiaKatatx >= 11880
					&& synolYphrGiaKatatx <= 12599) {
				return synolYphrGiaKatatx - 11880;
			} else if (synolYphrGiaKatatx >= 12600
					&& synolYphrGiaKatatx <= 13319) {
				return synolYphrGiaKatatx - 12600;
			} else if (synolYphrGiaKatatx >= 13320
					&& synolYphrGiaKatatx <= 14039) {
				return synolYphrGiaKatatx - 13320;
			}
			return -1; // End COMPULSORY_EDUCATION_LEVEL case

		default:
			return -1;
		}
	}



	// public String toString() {
	// return "Βαθμός(Μ.Κ.): "+rank+"("+salaryGrade+")";
	// }

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RankInfo [");
		if (employeeInfo != null) {
			builder.append("employee=");
			builder.append(employeeInfo.getEmployee());
			builder.append(", ");
		}

		if (rank != null) {
			builder.append("rank=");
			builder.append(rank);
			builder.append(", ");
		}

		if (lastRankDate != null) {
			builder.append("lastRankDate=");
			builder.append(lastRankDate);
			builder.append(", ");
		}

		builder.append("salary grade=");
		builder.append(salaryGrade);
		builder.append(", ");

		if (lastSalaryGradeDate != null) {
			builder.append("lastSalaryGradeDate=");
			builder.append(lastSalaryGradeDate);
			builder.append(", ");
		}

		if (educationalLevel != null) {
			builder.append("educationalLevel=");
			builder.append(educationalLevel);
			builder.append(", ");
		}

		if (surplusTimeInRank != null) {
			builder.append("surplusTimeInRank=");
			builder.append(surplusTimeInRank);
			builder.append(", ");
		}

		if (surplusTimeInSalaryGrade != null) {
			builder.append("surplusTimeInSalaryGrade=");
			builder.append(surplusTimeInSalaryGrade);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

}
