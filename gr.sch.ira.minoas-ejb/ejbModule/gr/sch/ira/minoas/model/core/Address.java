/**
 * 
 */
package gr.sch.ira.minoas.model.core;

import gr.sch.ira.minoas.model.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.Name;

/**
 * @author <a href="mailto:filippos@slavik.gr">Filippos Slavik</a>
 * 
 */
@Entity
@Name("address")
@Table(name = "ADDRESS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Address extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "ADDRESS", length = 128, nullable = false)
	private String address;

	@Basic
	@Column(name = "ADDRESS_ADDITIONAL", length = 128, nullable = true)
	private String addressAdditional;

	@Basic
	@Column(name = "CITY", length = 15, nullable = true)
	private String city;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	@Column(name = "LATITUDE", nullable = true)
	private Double latitude;

	@Basic
	@Column(name = "LONGITUDE", nullable = true)
	private Double longitude;

	@Basic
	@Column(name = "NUMBER", length = 8, nullable = true)
	private String number;

	@Basic
	@Column(name = "POSTAL_CODE", length = 10, nullable = true)
	private String postCode;

	/**
	 * 
	 */
	public Address() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the addressAdditional
	 */
	public String getAddressAdditional() {
		return addressAdditional;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param addressAdditional the addressAdditional to set
	 */
	public void setAddressAdditional(String addressAdditional) {
		this.addressAdditional = addressAdditional;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}
