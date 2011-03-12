/*
 * Created on 29 avr. 2004
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for authenticated user locales.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserLocaleDto extends MdoDtoBean
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
    private UserAuthenticationDto user;
    /**
     * This is a foreign key that refers to t_locale.
     * It is used to specify the authenticated user locale. 
     * This field and the other aut_id field consist of a unique field.
     */
    private LocaleDto locale;
    /**
     * @return the user
     */
    public UserAuthenticationDto getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserAuthenticationDto user) {
        this.user = user;
    }
    /**
     * @return the locale
     */
    public LocaleDto getLocale() {
        return locale;
    }
    /**
     * @param locale the locale to set
     */
    public void setLocale(LocaleDto locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
	return "UserLocaleDto [locale=" + locale + ", user=" + user + ", id="
		+ id + "]";
    }
    
}
