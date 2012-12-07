package fr.mch.mdo.restaurant.beans.dto;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class UserAuthenticationDto extends MdoDtoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

	/**
	 * This is the authenticated user login. It is an unique field.
	 */
	private String login;
	/**
	 * User locales
	 */
	private Map<String, Long> locales;

	/**
	 * This is a foreign key that refers to t_user. It is used to specify the
	 * authenticated user.
	 */
	private UserDto user;
    
    public UserAuthenticationDto()
    {
    }


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}


	/**
	 * @return the locales
	 */
	public Map<String, Long> getLocales() {
		return locales;
	}


	/**
	 * @param locales the locales to set
	 */
	public void setLocales(Map<String, Long> locales) {
		this.locales = locales;
	}


	/**
	 * @return the user
	 */
	public UserDto getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(UserDto user) {
		this.user = user;
	}
}
