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
public class PrintingInformationsManagerViewBean extends AdministrationManagerViewBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

	private Map<Long, String> labels;

	private Map<Long, String> languages;

	private List<IMdoDtoBean> restaurants;

	private List<IMdoDtoBean> alignments;

	private List<IMdoDtoBean> parts;

	private List<IMdoDtoBean> sizes;

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(Map<Long, String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the labels
	 */
	public Map<Long, String> getLabels() {
		return labels;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(Map<Long, String> languages) {
		this.languages = languages;
	}

	/**
	 * @return the languages
	 */
	public Map<Long, String> getLanguages() {
		return languages;
	}

	/**
	 * @return the restaurants
	 */
	public List<IMdoDtoBean> getRestaurants() {
		return restaurants;
	}

	/**
	 * @param restaurants the restaurants to set
	 */
	public void setRestaurants(List<IMdoDtoBean> restaurants) {
		this.restaurants = restaurants;
	}

	/**
	 * @return the alignments
	 */
	public List<IMdoDtoBean> getAlignments() {
		return alignments;
	}

	/**
	 * @param alignments the alignments to set
	 */
	public void setAlignments(List<IMdoDtoBean> alignments) {
		this.alignments = alignments;
	}

	/**
	 * @return the parts
	 */
	public List<IMdoDtoBean> getParts() {
		return parts;
	}

	/**
	 * @param parts the parts to set
	 */
	public void setParts(List<IMdoDtoBean> parts) {
		this.parts = parts;
	}

	/**
	 * @return the sizes
	 */
	public List<IMdoDtoBean> getSizes() {
		return sizes;
	}

	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(List<IMdoDtoBean> sizes) {
		this.sizes = sizes;
	}
}
