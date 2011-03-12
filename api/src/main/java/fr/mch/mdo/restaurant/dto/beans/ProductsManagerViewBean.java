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

    private Map<String, String> categoryLabels;

    private List<IMdoDtoBean> vats;

    private List<IMdoDtoBean> restaurants;
    
    private Map<String, String> languages;

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public List<IMdoDtoBean> getVats() {
        return vats;
    }

    public void setVats(List<IMdoDtoBean> vats) {
        this.vats = vats;
    }

    public Map<String, String> getCategoryLabels() {
        return categoryLabels;
    }

    public void setCategoryLabels(Map<String, String> categoryLabels) {
        this.categoryLabels = categoryLabels;
    }

    public List<IMdoDtoBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<IMdoDtoBean> restaurants) {
        this.restaurants = restaurants;
    }
}
