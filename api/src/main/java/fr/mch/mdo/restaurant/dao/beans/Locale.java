/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_locale mapping.
 * This table is used for i18n.
 * 
 * @author Mathieu MA sous conrad
 */
public class Locale extends MdoDaoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is language ISO code.
     */
    private String language;
    
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((language == null) ? 0 : language.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Locale other = (Locale) obj;
	if (language == null) {
	    if (other.language != null)
		return false;
	} else if (!language.equals(other.language))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Locale [language=" + language + ", deleted=" + deleted
		+ ", id=" + id + "]";
    }
}
