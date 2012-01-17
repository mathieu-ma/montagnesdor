package fr.mch.mdo.restaurant.dto.beans;

import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.Subject;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoBean;

public class MdoUserContext implements IMdoBean 
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	private Subject subject = null;
	
	private Date entryDate = new Date();
	
	private UserAuthenticationDto userAuthentication = null;
	private LocaleDto currentLocale = new LocaleDto();
	// All dinner tables the user will manage.
	// This is useful when user has several tabs opened in his browser.
	// So DO NOT use current dinner table because browser considers that each tab belongs to the same session.
	private Map<String, DinnerTableDto> myDinnerTables = new HashMap<String, DinnerTableDto>();

	/* Navigation part */
	private String currentURLWithParameters;
	private String currentURLWithParametersWithoutLocale;
	private String currentURL;
	private String currentNameSpace;
	private String currentActionName;
	private String currentActionMethod;

	private Map<String, String> systemAvailableLanguages;

	private String selectedMenuItemId = "1";

	public MdoUserContext(Subject subject) {
		this.subject = subject;
	}

	/**
	 * Returns the subject.
	 * 
	 * @return Subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            The subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * Returns the user.
	 * 
	 * @return User
	 */
	public UserDto getUser() {
		return userAuthentication.getUser();
	}

	public String getName() {
		return this.getUser() == null ? "" : this.getUser().getName();
	}

	public String getForename1() {
		return this.getUser() == null ? "" : this.getUser().getForename1();
	}

	public String getForename2() {
		return this.getUser() == null ? "" : this.getUser().getForename2();
	}

	public String getLevelPass1() {
		return (userAuthentication == null || userAuthentication.getLevelPass1() == null) ? "" : "X";
	}

	public String getLevelPass2() {
		return (userAuthentication == null || userAuthentication.getLevelPass2() == null) ? "" : "X";
	}

	public String getLevelPass3() {
		return (userAuthentication == null || userAuthentication.getLevelPass3() == null) ? "" : "X";
	}

	public String getPicture() {
		return (this.getUser() == null || this.getUser().getPicture() == null || userAuthentication == null) ? Constants.DEFAULT_PICTURE_NAME : (userAuthentication.getLogin()
				+ "." + Constants.DEFAULT_PICTURE_NAME_EXTENSION);
	}

	// Remplacer Sex par civilité
	public boolean getSex() {
		return (this.getUser() == null) ? Boolean.TRUE.booleanValue() : this.getUser().isSex();
	}

	// Remplacer Sex par civilité
	public String getTitleLanguageKeyLabel() {
		return (this.getUser() == null) ? "" : this.getUser().getTitle().getLanguageKeyLabel();
	}

	public String getRole() {
		String result = "";
		if (userAuthentication != null && userAuthentication.getUserRole() != null && userAuthentication.getUserRole().getCode() != null) {
			result = userAuthentication.getUserRole().getCode().getName();
		}
		return result;
	}

	public String getLogin() {
		return (userAuthentication == null) ? "" : userAuthentication.getLogin();
	}

	public Date getBirthday() {
		Date temp = new Date();
		if (this.getUser() != null && this.getUser().getBirthdate() != null) {
			temp = this.getUser().getBirthdate();
		}
		return temp;
	}

	public int getRestaurantRegistrationYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getUserAuthentication().getRestaurant().getRegistrationDate());

		return calendar.get(Calendar.YEAR);
	}

	public String getSelectedMenuItemId() {
		return selectedMenuItemId;
	}

	public void setSelectedMenuItemId(String selectedMenuItemId) {
		this.selectedMenuItemId = selectedMenuItemId;
	}

	public LocaleDto getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(LocaleDto currentLocale) {
		this.currentLocale = currentLocale;
	}

	public UserRoleDto getUserRole() {
		return userAuthentication.getUserRole();
	}

	public UserAuthenticationDto getUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(UserAuthenticationDto userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	/**
	 * @return the myDinnerTables
	 */
	public Map<String, DinnerTableDto> getMyDinnerTables() {
		return myDinnerTables;
	}

	/**
	 * @param myDinnerTables the myDinnerTables to set
	 */
	public void setMyDinnerTables(Map<String, DinnerTableDto> myDinnerTables) {
		this.myDinnerTables = myDinnerTables;
	}

	/**
	 * @param dinnerTableNumber the Dinner Table number
	 * @return the DinnerTableDto
	 */
	public DinnerTableDto getMyDinnerTable(String dinnerTableNumber) {
		return myDinnerTables.get(dinnerTableNumber);
	}

	/**
	 * @param dinnerTable the dinnerTable to set
	 */
	public void setMyDinnerTable(DinnerTableDto dinnerTable) {
		this.myDinnerTables.put(dinnerTable.getNumber(), dinnerTable);
	}

	public Map<String, String> getSystemAvailableLanguages() {
		return systemAvailableLanguages;
	}

	public void setSystemAvailableLanguages(Map<String, String> systemAvailableLanguages) {
		this.systemAvailableLanguages = systemAvailableLanguages;
	}

	public void setCurrentActionName(String currentActionName) {
		this.currentActionName = currentActionName;
	}

	public String getCurrentActionName() {
		return currentActionName;
	}

	public void setCurrentActionMethod(String currentActionMethod) {
		this.currentActionMethod = currentActionMethod;
	}

	public String getCurrentActionMethod() {
		return currentActionMethod;
	}

	public void setCurrentNameSpace(String currentNameSpace) {
		this.currentNameSpace = currentNameSpace;
	}

	public String getCurrentNameSpace() {
		return currentNameSpace;
	}

	public void setCurrentURLWithParameters(String currentURLWithParameters) {
		this.currentURLWithParameters = currentURLWithParameters;
	}

	public String getCurrentURLWithParameters() {
		return currentURLWithParameters;
	}

	/**
	 * @param currentURLWithParametersWithoutLocale the currentURLWithParametersWithoutLocale to set
	 */
	public void setCurrentURLWithParametersWithoutLocale(
			String currentURLWithParametersWithoutLocale) {
		this.currentURLWithParametersWithoutLocale = currentURLWithParametersWithoutLocale;
	}

	/**
	 * @return the currentURLWithParametersWithoutLocale
	 */
	public String getCurrentURLWithParametersWithoutLocale() {
		return currentURLWithParametersWithoutLocale;
	}

	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

	public String getCurrentURL() {
		return currentURL;
	}
	
	public char getCurrentDecimalSeparator() {
		char result = '.';
		Locale locale = Locale.getDefault();
		if (this.getCurrentLocale() != null && this.getCurrentLocale().getLanguageCode() != null) {
			locale = new Locale(this.getCurrentLocale().getLanguageCode());
		}
		result = DecimalFormatSymbols.getInstance(locale).getDecimalSeparator();
		return result;
	}
}
