/*
 * Created on 29 avr. 2004
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_user_locale mapping.
 * This table is used to specify the authenticated user locales.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserLocale extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_user_authentication.
     * It is used to specify the authenticated user. 
     * This field and the other loc_id field consist of a unique field.
     */
    private UserAuthentication user;
    /**
     * This is a foreign key that refers to t_locale.
     * It is used to specify the authenticated user locale. 
     * This field and the other aut_id field consist of a unique field.
     */
    private Locale locale;
    
    /**
     * @return the user
     */
    public UserAuthentication getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserAuthentication user) {
        this.user = user;
    }
    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }
    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1; // DO NOT call super.hashCode(); because ID could be null.
	result = prime * result + ((locale == null || locale.getId() == null) ? 0 : locale.getId().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	// DO NOT call super.equals(obj) because ID could be null.
//	if (!super.equals(obj)) {
//	    return false;
//	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	UserLocale other = (UserLocale) obj;
	if (locale == null) {
	    if (other.locale != null) {
		return false;
	    }
	} else if (locale.getId() == null) {
	    if (other.locale.getId() != null) {
		return false;
	    }
	} else if (other.locale == null || !locale.getId().equals(other.locale.getId())) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	// DO NOT print parent field because of recursive call.
	return "UserLocale [locale=" + locale + ", deleted=" + deleted + ", id=" + id + "]";
    }

}
