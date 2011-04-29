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
public class ProductsManagerViewBean extends AdministrationManagerViewBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private List<IMdoDtoBean> categories;

    private List<IMdoDtoBean> vats;

    private List<IMdoDtoBean> restaurants;
    
	private Map<Long, String> labels;

	private Map<Long, String> languages;

    /**
	 * @param categories the categories to set
	 */
	public void setCategories(List<IMdoDtoBean> categories) {
		this.categories = categories;
	}

	/**
	 * @return the categories
	 */
	public List<IMdoDtoBean> getCategories() {
		return categories;
	}

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

	public Map<Long, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<Long, String> languages) {
        this.languages = languages;
    }

    public List<IMdoDtoBean> getVats() {
        return vats;
    }

    public void setVats(List<IMdoDtoBean> vats) {
        this.vats = vats;
    }

    public List<IMdoDtoBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<IMdoDtoBean> restaurants) {
        this.restaurants = restaurants;
    }
}
