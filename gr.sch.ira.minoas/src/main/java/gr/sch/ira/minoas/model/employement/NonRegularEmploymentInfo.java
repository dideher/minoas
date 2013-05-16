package gr.sch.ira.minoas.model.employement;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * @author <a href="mailto:gand@sch.gr">Yorgos Andreadakis</a>
 * @version $Id$
 */

@Entity
@Table(name = "NONREGULAR_EMPLOYMENT_INFO")

public class NonRegularEmploymentInfo extends BaseIDModel {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "nonRegularEmploymentInfo", fetch = FetchType.LAZY)
	private Employment employment;

	/*
	 * Απόφαση Πρόσληψης
	 * (Υπουργική απόφαση στην περίπτωση Αναπληρωτή)
	 * (Απόφαση Δευτεροβάθμιας στην περίπτωση Ωρομισθίου)
	 */
	@Basic
	@Column(name = "ACT", nullable = true, length = 32)
	private String act;

	
	/**
	 * Ημερομηνία Απόφασης Πρόσληψης
	 * (Υπουργική απόφαση στην περίπτωση Αναπληρωτή)
	 * (Απόφαση Δευτεροβάθμιας στην περίπτωση Ωρομισθίου)
	 */
	@Basic
	@Column(name = "ACT_DATE")
	private Date actDate;
	
	/**
	 * Απόφαση ΠΥΣΔΕ
	 */
	@Basic
	@Column(name = "PYSDE_ACT", nullable = true, length = 32)
	private String pysdeAct;
	
	/**
	 * Ημερομηνία Απόφασης ΠΥΣΔΕ
	 */
	@Basic
	@Column(name = "PYSDE_ACT_DATE")
	private Date pysdeActDate;

	
	/**
     * Πίνακας Προέλευσης
     * (Ενιαίος Πίνακας / Πίνακας Ειδικής Αγωγής) 
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "SELECTION_TABLE", length = 30, nullable = true)
	private SelectionTableType selectionTableType;
	
	
	
	/*
	 * Αριθμός Διαύγειας (ΑΔΑ)
	 */
	@Basic
	@Column(name = "DIAYGEIA_NO", nullable = true, length = 20)
	private String diaygeiaNo;
	
	
	/*
	 * Απόφαση Τοποθέτησης
	 */
	@Basic
	@Column(name = "PLACEMENT_ORDER", nullable = true, length = 5)
	private String placementOrder;

	
	/*
	 * Ημερομηνία Απόφασης Τοποθέτησης
	 */
	@Basic
	@Column(name = "PLACEMENT_ORDER_DATE")
	private Date placementOrderDate;
	
	
	/*
	 * Τροποποίηση Απόφασης Τοποθέτησης
	 */
	@Basic
	@Column(name = "PLACEMENT_ORDER_MODIFICATION", nullable = true, length = 5)
	private String placementOrderModification;

	
	/*
	 * Ημερομηνία Απόφασης Τροποποίησης Τοποθέτησης
	 */
	@Basic
	@Column(name = "PLACEMENT_ORDER_MODIFICATION_DATE")
	private Date placementOrderModificationDate;	
	
	/*
	 * Πλήρες Ωράριο
	 * TRUE when Πλήρες Ωράριο
	 * FALSE when Μειωμένο Ωράριο
	 */
	@Basic
	@Column(name = "FULL_TIME", nullable = true)
	private Boolean fullTime;

	
	/*
	 * Απόφαση Απόλυσης
	 */
	@Basic
	@Column(name = "TERMINATION_ORDER", nullable = true, length = 5)
	private String terminationOrder;

	
	/*
	 * Ημερομηνία Απόφασης Απόλυσης
	 */
	@Basic
	@Column(name = "TERMINATION_ORDER_DATE")
	private Date terminationOrderDate;
	
	
	
	
	/**
	 * @return the employment
	 */
	public Employment getEmployment() {
		return employment;
	}


	/**
	 * @return the pysdeAct
	 */
	public String getPysdeAct() {
		return pysdeAct;
	}


	/**
	 * @param employment the employment to set
	 */
	public void setEmployment(Employment employment) {
		this.employment = employment;
	}



	/**
	 * @param pysdeAct the pysdeAct to set
	 */
	public void setPysdeAct(String pysdeAct) {
		this.pysdeAct = pysdeAct;
	}


	/**
	 * @return the act
	 * Απόφαση 
	 * (Υπουργική απόφαση στην περίπτωση Αναπληρωτή)
	 * (Απόφαση Δευτεροβάθμιας στην περίπτωση Ωρομισθίου)
	 */
	public String getAct() {
		return act;
	}


	/**
	 * @param act the act to set
	 * Απόφαση 
	 * (Υπουργική απόφαση στην περίπτωση Αναπληρωτή)
	 * (Απόφαση Δευτεροβάθμιας στην περίπτωση Ωρομισθίου)
	 */
	public void setAct(String act) {
		this.act = act;
	}


	/**
	 * @return the actDate
	 */
	public Date getActDate() {
		return actDate;
	}


	/**
	 * @param actDate the actDate to set
	 */
	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}


	/**
	 * @return the pysdeActDate
	 */
	public Date getPysdeActDate() {
		return pysdeActDate;
	}


	/**
	 * @param pysdeActDate the pysdeActDate to set
	 */
	public void setPysdeActDate(Date pysdeActDate) {
		this.pysdeActDate = pysdeActDate;
	}


	/**
	 * @return the selectionTableType
	 */
	public SelectionTableType getSelectionTableType() {
		return selectionTableType;
	}


	/**
	 * @param selectionTableType the selectionTableType to set
	 */
	public void setSelectionTableType(SelectionTableType selectionTableType) {
		this.selectionTableType = selectionTableType;
	}


	/**
	 * @return the diaygeiaNo
	 */
	public String getDiaygeiaNo() {
		return diaygeiaNo;
	}


	/**
	 * @param diaygeiaNo the diaygeiaNo to set
	 */
	public void setDiaygeiaNo(String diaygeiaNo) {
		this.diaygeiaNo = diaygeiaNo;
	}


	/**
	 * @return the placementOrder
	 */
	public String getPlacementOrder() {
		return placementOrder;
	}


	/**
	 * @param placementOrder the placementOrder to set
	 */
	public void setPlacementOrder(String placementOrder) {
		this.placementOrder = placementOrder;
	}


	/**
	 * @return the placementOrderDate
	 */
	public Date getPlacementOrderDate() {
		return placementOrderDate;
	}


	/**
	 * @param placementOrderDate the placementOrderDate to set
	 */
	public void setPlacementOrderDate(Date placementOrderDate) {
		this.placementOrderDate = placementOrderDate;
	}


	/**
	 * @return the placementOrderModification
	 */
	public String getPlacementOrderModification() {
		return placementOrderModification;
	}


	/**
	 * @param placementOrderModification the placementOrderModification to set
	 */
	public void setPlacementOrderModification(String placementOrderModification) {
		this.placementOrderModification = placementOrderModification;
	}


	/**
	 * @return the placementOrderModificationDate
	 */
	public Date getPlacementOrderModificationDate() {
		return placementOrderModificationDate;
	}


	/**
	 * @param placementOrderModificationDate the placementOrderModificationDate to set
	 */
	public void setPlacementOrderModificationDate(
			Date placementOrderModificationDate) {
		this.placementOrderModificationDate = placementOrderModificationDate;
	}


	/**
	 * @return the fullTime
	 */
	public Boolean getFullTime() {
		return fullTime;
	}


	/**
	 * @param fullTime the fullTime to set
	 */
	public void setFullTime(Boolean fullTime) {
		this.fullTime = fullTime;
	}


	/**
	 * @return the terminationOrder
	 */
	public String getTerminationOrder() {
		return terminationOrder;
	}


	/**
	 * @param terminationOrder the terminationOrder to set
	 */
	public void setTerminationOrder(String terminationOrder) {
		this.terminationOrder = terminationOrder;
	}


	/**
	 * @return the terminationOrderDate
	 */
	public Date getTerminationOrderDate() {
		return terminationOrderDate;
	}


	/**
	 * @param terminationOrderDate the terminationOrderDate to set
	 */
	public void setTerminationOrderDate(Date terminationOrderDate) {
		this.terminationOrderDate = terminationOrderDate;
	}

}
