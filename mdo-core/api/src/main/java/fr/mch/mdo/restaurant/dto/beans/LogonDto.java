/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class LogonDto implements IMdoDtoBean 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private IMdoBean userContext;

	public LogonDto() {
	}

	/**
	 * @return
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public IMdoBean getUserContext() {
		return userContext;
	}

	public void setUserContext(IMdoBean userContext) {
		this.userContext = userContext;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
}
