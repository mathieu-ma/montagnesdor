package fr.mch.mdo.restaurant.dto.beans;

import java.util.Date;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class ChangeEntryDateDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private Date entryDate;
	private String login;
	private String password;
	private String levelPassword;

	public ChangeEntryDateDto() {
	}

	/**
	 * @param entryDate
	 *            the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevelPassword() {
		return levelPassword;
	}

	public void setLevelPassword(String levelPassword) {
		this.levelPassword = levelPassword;
	}
}
