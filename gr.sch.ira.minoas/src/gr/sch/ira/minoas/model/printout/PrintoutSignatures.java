package gr.sch.ira.minoas.model.printout;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import gr.sch.ira.minoas.model.BaseIDModel;

@Entity
@Table(name = "PRINTOUT_SIGNATURES")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class PrintoutSignatures extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    @Basic
    @Column(name = "SIGNATURE_TITLE", nullable = false, length = 128)
    private String signatureTitle;
    
    @Basic
    @Column(name = "SIGNATURE_NAME", nullable = false, length = 128)
    private String signatureName;

    /**
     * @return the signatureTitle
     */
    public String getSignatureTitle() {
        return signatureTitle;
    }

    /**
     * @param signatureTitle the signatureTitle to set
     */
    public void setSignatureTitle(String signatureTitle) {
        this.signatureTitle = signatureTitle;
    }

    /**
     * @return the signatureName
     */
    public String getSignatureName() {
        return signatureName;
    }

    /**
     * @param signatureName the signatureName to set
     */
    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }
}
