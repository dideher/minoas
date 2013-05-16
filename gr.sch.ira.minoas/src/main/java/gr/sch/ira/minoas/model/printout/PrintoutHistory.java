package gr.sch.ira.minoas.model.printout;

import gr.sch.ira.minoas.model.BaseIDModel;
import gr.sch.ira.minoas.model.security.Principal;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PRINTOUT_HISTORY")

public class PrintoutHistory extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @Basic
    @Column(name = "PRINTOUT_TITLE", nullable = false, length = 512)
    private String printoutTitle;

    @Basic
    @Column(name = "PRINTED_ON", nullable = false)
    private Date printedOn;
    
    @Basic
    @JoinColumn(name = "PRINTOUT_BY_ID", nullable = false)
    private Principal printedBy;
    
    @Basic
    @Column(name = "LOCAL_REFERENCE_NUMBER", nullable = true, length = 128)
    private String localReferenceNumber;
    
    @Basic
    @Column(name = "NOTE", nullable = true, length = 1024)
    private String note;
    
    @Basic
    @Column(name = "PRINTOUT_FROM", nullable = true, length = 128)
    private String printedFrom;

    /**
     * @return the printoutTitle
     */
    public String getPrintoutTitle() {
        return printoutTitle;
    }

    /**
     * @param printoutTitle the printoutTitle to set
     */
    public void setPrintoutTitle(String printoutTitle) {
        this.printoutTitle = printoutTitle;
    }

    /**
     * @return the printedOn
     */
    public Date getPrintedOn() {
        return printedOn;
    }

    /**
     * @param printedOn the printedOn to set
     */
    public void setPrintedOn(Date printedOn) {
        this.printedOn = printedOn;
    }

    /**
     * @return the printedBy
     */
    public Principal getPrintedBy() {
        return printedBy;
    }

    /**
     * @param printedBy the printedBy to set
     */
    public void setPrintedBy(Principal printedBy) {
        this.printedBy = printedBy;
    }

    /**
     * @return the localReferenceNumber
     */
    public String getLocalReferenceNumber() {
        return localReferenceNumber;
    }

    /**
     * @param localReferenceNumber the localReferenceNumber to set
     */
    public void setLocalReferenceNumber(String localReferenceNumber) {
        this.localReferenceNumber = localReferenceNumber;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the printedFrom
     */
    public String getPrintedFrom() {
        return printedFrom;
    }

    /**
     * @param printedFrom the printedFrom to set
     */
    public void setPrintedFrom(String printedFrom) {
        this.printedFrom = printedFrom;
    }
}
