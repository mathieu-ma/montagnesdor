package fr.mch.mdo.restaurant.services.business.managers.locales;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface ILocalesManager extends IAdministrationManager
{
	/**
	 * Get available languages by current java.util.Locale from server system
	 * java.util.Locale aside not languages that already exist in database. The
	 * result is sorted by value.
	 * 
	 * @param currentLocale
	 *            current java.util.Locale
	 * @param localesListFromDba
	 *            list of Locale retrieved from database
	 * @return Map with key equals to ISO language code and value equals to
	 *         language in current locale, i.e., 
	 *         1) if key=fr and current locale=fr then value=français 
	 *         2) if key=fr and current locale=en then value=french
     * @throws MdoException if any exception occur.
	 */
	Map<String, String> getAvailableLanguages(Locale currentLocale) throws MdoException;

	/**
	 * Get available languages by current java.util.Locale from server system
	 * java.util.Locale. The result is sorted by value.
	 * 
	 * @param currentLocale
	 *            current java.util.Locale
	 * @param localesListFromDba
	 *            list of Locale retrieved from database
	 * @return Map with key equals to ISO language code and value equals to
	 *         language in current locale, i.e., 
	 *         1) if key=fr and current locale=fr then value=français 
	 *         2) if key=fr and current locale=en then value=french
     * @throws MdoException if any exception occur.
	 */
	Map<String, String> getSystemAvailableLanguages(java.util.Locale currentLocale) throws MdoException;
	
    /**
     * Get Map of key=id of mdo locale/value=display language of java.util.Locale by specific java.util.Locale. 
	 * @param currentLocale specific java.util.Locale.
     * @param languageCode the specific language iso code.
     * @return a Map of key=id of mdo locale/value=display language of java.util.language by specific language iso code.
     * @throws MdoException if any exception occur.
     */
    Map<Long, String> getLanguages(Locale currentLocale) throws MdoException;

    /**
     * Get Map of key=id of mdo locale/value=display language of java.util.Locale by specific language iso code. 
     * @param languageCode the specific language iso code.
     * @return a Map of key=id of mdo locale/value=display language of java.util.language by specific language iso code.
     * @throws MdoException if any exception occur.
     */
	Map<Long, String> getLanguages(String languageCode) throws MdoException;

	/**
	 * Find the locale by language. 
	 * @param language the ISO language.
	 * @param userContext the user context.
	 * @return a found locale.
     * @throws MdoException if any exception occur.
	 */
	IMdoBean findByLanguage(Object language, MdoUserContext userContext) throws MdoException;
    
	LocaleDto findLocale(Locale locale, MdoUserContext userContext) throws MdoException;

	List<LocaleDto> getLanguageLocales(MdoUserContext userContext) throws MdoException;
}
