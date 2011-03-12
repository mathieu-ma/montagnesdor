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
public class CategoriesManagerViewBean extends AdministrationManagerViewBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private Map<String, String> languages;

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

}
