/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationPasswordLevel;

/**
 * This class is used for t_user_authentication mapping. This table is used for
 * user authentication.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserAuthentication extends MdoDaoBean implements IAuthenticationPasswordLevel
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_locale. It is used to specify the
	 * printing language.
	 */
	private Locale printingLocale;
	/**
	 * This is a foreign key that refers to t_user. It is used to specify the
	 * authenticated user.
	 */
	private User user;
	/**
	 * This is a foreign key that refers to t_restaurant. It is used to specify
	 * the restaurant of the authenticated user.
	 */
	private Restaurant restaurant;
	/**
	 * This is a foreign key that refers to t_user_role. It is used to specify
	 * the role of the authenticated user.
	 */
	private UserRole userRole;
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
	 * User locales
	 */
	private Set<UserLocale> locales;

	public UserAuthentication() {
	}

	public UserAuthentication(String login) {
	}

	/**
	 * @return the printingLocale
	 */
	public Locale getPrintingLocale() {
		return printingLocale;
	}

	/**
	 * @param printingLocale
	 *            the printingLocale to set
	 */
	public void setPrintingLocale(Locale printingLocale) {
		this.printingLocale = printingLocale;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
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
	 * @param password
	 *            the password to set
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
	 * @param levelPass1
	 *            the levelPass1 to set
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
	 * @param levelPass2
	 *            the levelPass2 to set
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
	 * @param levelPass3
	 *            the levelPass3 to set
	 */
	public void setLevelPass3(String levelPass3) {
		this.levelPass3 = levelPass3;
	}

	/**
	 * @return the locales
	 */
	public Set<UserLocale> getLocales() {
		return locales;
	}

	/**
	 * @param locales
	 *            the locales to set
	 */
	public void setLocales(Set<UserLocale> locales) {
		this.locales = locales;
	}

	/**
	 * Add User Locale to locales
	 * 
	 * @param locale
	 *            the User Locale
	 */
	public void addLocale(UserLocale locale) {
		if (locales == null) {
			locales = new HashSet<UserLocale>();
		}
		if (locale != null) {
			locale.setUser(this);
		}
		locales.add(locale);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		UserAuthentication other = (UserAuthentication) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAuthentication [levelPass1=" + levelPass1 + ", levelPass2=" + levelPass2 + ", levelPass3=" + levelPass3 + ", locales=" + locales + ", login=" + login + ", password=" + password
				+ ", printingLocale=" + printingLocale + ", restaurant=" + restaurant + ", user=" + user + ", userRole=" + userRole + ", deleted=" + deleted + ", id=" + id + "]";
	}

}
