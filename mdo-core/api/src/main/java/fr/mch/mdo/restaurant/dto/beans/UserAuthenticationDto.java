/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for user authentication.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserAuthenticationDto extends MdoDtoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_locale. 
     * It is used to specify the printing language.
     */
    private LocaleDto printingLocale;
    /**
     * This is a foreign key that refers to t_user. 
     * It is used to specify the authenticated user.
     */
    private UserDto user;
    /**
     * This is a foreign key that refers to t_restaurant. 
     * It is used to specify the restaurant of the authenticated user.
     */
    private RestaurantDto restaurant;
    /**
     * This is a foreign key that refers to t_user_role.
     * It is used to specify the role of the authenticated user.
     */
    private UserRoleDto userRole;
    /**
     * This is the authenticated user login. It is an unique field.
     */
    private String login;
    /**
     * This is the authenticated user password.
     */
    private String password;
    /**
     * This is the authenticated user password level 1.
     */
    private String levelPass1;
    /**
     * This is the authenticated user password level 2.
     */
    private String levelPass2;
    /**
     * This is the authenticated user password level 3.
     */
    private String levelPass3;
    /**
     * User locales: have to instance it because we use Struts 2 conversion
     */
    private Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();
    /**
     * @return the printingLocale
     */
    public LocaleDto getPrintingLocale() {
        return printingLocale;
    }
    /**
     * @param printingLocale the printingLocale to set
     */
    public void setPrintingLocale(LocaleDto printingLocale) {
        this.printingLocale = printingLocale;
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
    /**
     * @return the restaurant
     */
    public RestaurantDto getRestaurant() {
        return restaurant;
    }
    /**
     * @param restaurant the restaurant to set
     */
    public void setRestaurant(RestaurantDto restaurant) {
        this.restaurant = restaurant;
    }
    /**
     * @return the userRole
     */
    public UserRoleDto getUserRole() {
        return userRole;
    }
    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(UserRoleDto userRole) {
        this.userRole = userRole;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the levelPass1
     */
    public String getLevelPass1() {
        return levelPass1;
    }
    /**
     * @param levelPass1 the levelPass1 to set
     */
    public void setLevelPass1(String levelPass1) {
        this.levelPass1 = levelPass1;
    }
    /**
     * @return the levelPass2
     */
    public String getLevelPass2() {
        return levelPass2;
    }
    /**
     * @param levelPass2 the levelPass2 to set
     */
    public void setLevelPass2(String levelPass2) {
        this.levelPass2 = levelPass2;
    }
    /**
     * @return the levelPass3
     */
    public String getLevelPass3() {
        return levelPass3;
    }
    /**
     * @param levelPass3 the levelPass3 to set
     */
    public void setLevelPass3(String levelPass3) {
        this.levelPass3 = levelPass3;
    }
    /**
     * @return the locales
     */
    public Set<UserLocaleDto> getLocales() {
        return locales;
    }
    /**
     * @param locales the locales to set
     */
    public void setLocales(Set<UserLocaleDto> locales) {
        this.locales = locales;
    }

    @Override
    public String toString() {
	return "UserAuthenticationDto [levelPass1=" + levelPass1
		+ ", levelPass2=" + levelPass2 + ", levelPass3=" + levelPass3
		+ ", locales=" + locales + ", login=" + login + ", password="
		+ password + ", printingLocale=" + printingLocale
		+ ", restaurant=" + restaurant + ", user=" + user
		+ ", userRole=" + userRole + ", id=" + id + "]";
    }

}
