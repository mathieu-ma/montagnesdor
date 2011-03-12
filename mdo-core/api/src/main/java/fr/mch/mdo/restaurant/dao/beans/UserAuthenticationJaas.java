/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationPasswordLevel;

/**
 * This class is used for t_user_authentication mapping. This table is used for
 * user authentication.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserAuthenticationJaas extends MdoDaoBean implements IAuthenticationPasswordLevel 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

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

	public UserAuthenticationJaas() {
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
		UserAuthenticationJaas other = (UserAuthenticationJaas) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAuthentication [levelPass1=" + levelPass1 + ", levelPass2=" + levelPass2 + ", levelPass3=" + levelPass3 + ", login=" + login + ", password=" + password
				+ ", userRole=" + userRole + ", deleted=" + deleted + ", id=" + id + "]";
	}

}
