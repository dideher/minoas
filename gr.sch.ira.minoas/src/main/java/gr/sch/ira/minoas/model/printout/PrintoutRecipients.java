package gr.sch.ira.minoas.model.printout;

import gr.sch.ira.minoas.model.BaseIDModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRINTOUT_RECIPIENTS")

public class PrintoutRecipients extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @Basic
    @Column(name = "RECIPIENT_TITLE", nullable = false, length = 128)
    private String recipientTitle;

    /**
     * @return the recipientTitle
     */
    public String getRecipientTitle() {
        return recipientTitle;
    }

    /**
     * @param recipientTitle the recipientTitle to set
     */
    public void setRecipientTitle(String recipientTitle) {
        this.recipientTitle = recipientTitle;
    }
    
    
   
        
}
