/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.Map;

/**
 * @author Mathieu MA
 * 
 */
public class UserRolesManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	private Map<String, String> labels;

	private Map<String, String> languages;

	
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(Map<String, String> languages) {
		this.languages = languages;
	}

	/**
	 * @return the languages
	 */
	public Map<String, String> getLanguages() {
		return languages;
	}

}
