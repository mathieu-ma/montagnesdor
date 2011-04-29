/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class ProductPartsManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private Map<Long, String> labels;

	private Map<Long, String> languages;

	private List<IMdoDtoBean> codes;

	/**
	 * @return the labels
	 */
	public Map<Long, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(Map<Long, String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the languages
	 */
	public Map<Long, String> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(Map<Long, String> languages) {
		this.languages = languages;
	}

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(List<IMdoDtoBean> codes) {
		this.codes = codes;
	}

	/**
	 * @return the codes
	 */
	public List<IMdoDtoBean> getCodes() {
		return codes;
	}
}
