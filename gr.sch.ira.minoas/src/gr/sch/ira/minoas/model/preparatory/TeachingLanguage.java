package gr.sch.ira.minoas.model.preparatory;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:fsla@forthnet.gr">Filippos Slavik</a>
 * @version $Id$
 */
@Entity
@Table(name = "TEACHING_LANGUAGE")
public class TeachingLanguage {

	@Basic
	@Column(name = "GENIKI_LANGUAGE", length = 12, unique = true)
	private String genikiLanguage;

	@Id
	@Column(name = "LANGUAGE", length = 12)
	private String language;

	/**
	 * 
	 */
	public TeachingLanguage() {
		super();
	}

	/**
	 * @param language
	 */
	public TeachingLanguage(String language) {
		super();
		this.language = language;
	}

	/**
	 * @return the genikiLanguage
	 */
	protected String getGenikiLanguage() {
		return genikiLanguage;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param genikiLanguage the genikiLanguage to set
	 */
	protected void setGenikiLanguage(String genikiLanguage) {
		this.genikiLanguage = genikiLanguage;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
}
