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
public class UserAuthenticationsManagerViewBean extends AdministrationManagerViewBean
{
    /**
	 * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private List<LocaleDto> languages;

    private Map<String, String> avalaibleLanguages;

    private List<UserLocaleDto> userLocales;

    private List<IMdoDtoBean> userRestaurants;
    
    private List<IMdoDtoBean> userRoles;

    private List<IMdoDtoBean> users;

	public List<LocaleDto> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LocaleDto> languages) {
		this.languages = languages;
	}

	public Map<String, String> getAvalaibleLanguages() {
		return avalaibleLanguages;
	}

	public void setAvalaibleLanguages(Map<String, String> avalaibleLanguages) {
		this.avalaibleLanguages = avalaibleLanguages;
	}

	public List<UserLocaleDto> getUserLocales() {
		return userLocales;
	}

	public void setUserLocales(List<UserLocaleDto> userLocales) {
		this.userLocales = userLocales;
	}

	public List<IMdoDtoBean> getUserRestaurants() {
		return userRestaurants;
	}

	public void setUserRestaurants(List<IMdoDtoBean> userRestaurants) {
		this.userRestaurants = userRestaurants;
	}

	public List<IMdoDtoBean> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<IMdoDtoBean> userRoles) {
		this.userRoles = userRoles;
	}

	public List<IMdoDtoBean> getUsers() {
		return users;
	}

	public void setUsers(List<IMdoDtoBean> users) {
		this.users = users;
	}
}
