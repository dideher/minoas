package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseIDModel;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "WORKING_OFFICE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WorkingOffice extends BaseIDModel {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name="OFFICIAL_TITLE", length=128, nullable=false)
    private String officialTitle;
    
    @OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinTable(name="WORKING_OFFICE_TELEPHONES", joinColumns={@JoinColumn(name="WORK_OFFICE_ID")}, inverseJoinColumns={@JoinColumn(name="TELEPHONE_ID")})
    private Collection<Telephone> telephones = new ArrayList<Telephone>();
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ADDRESS_ID", nullable=true)
    private Address address;
    
    @Basic
    @Column(name="OFFICE_FLOOR", nullable=true, length=32)
    private String floor;
    
    @Basic
    @Column(name="OFFICE_TAG", nullable=true, length=32)
    private String officeTag;

    /**
     * @return the officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * @return the telephones
     */
    public Collection<Telephone> getTelephones() {
        return telephones;
    }

    /**
     * @param telephones the telephones to set
     */
    public void setTelephones(Collection<Telephone> telephones) {
        this.telephones = telephones;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * @return the officeTag
     */
    public String getOfficeTag() {
        return officeTag;
    }

    /**
     * @param officeTag the officeTag to set
     */
    public void setOfficeTag(String officeTag) {
        this.officeTag = officeTag;
    }
}
