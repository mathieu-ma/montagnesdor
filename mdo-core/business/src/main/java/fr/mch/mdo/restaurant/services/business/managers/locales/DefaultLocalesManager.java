package fr.mch.mdo.restaurant.services.business.managers.locales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.dao.locales.hibernate.DefaultLocalesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.LocalesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultLocalesAssembler;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.restaurant.services.util.UtilsImpl;
import fr.mch.mdo.utils.ILocalesAssembler;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultLocalesManager extends AbstractAdministrationManager implements ILocalesManager 
{
	private static List<java.util.Locale> isoLanguagesList = Arrays.asList(java.util.Locale.getAvailableLocales());

	private static class LazyHolder {
		private static ILocalesManager instance = new DefaultLocalesManager(LoggerServiceImpl.getInstance().getLogger(DefaultLocalesManager.class.getName()), 
				DefaultLocalesDao.getInstance(), DefaultLocalesAssembler.getInstance());
	}

	private DefaultLocalesManager(ILogger logger, ILocalesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultLocalesManager() {
	}

	public static ILocalesManager getInstance() {
		return LazyHolder.instance;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, LocaleDto locale, boolean... lazy) throws MdoBusinessException {
		List<IMdoDtoBean> list = null;
		try {
			List<IMdoBean> locales = dao.findAll(lazy);
			if (locales != null) {
				list = ((ILocalesAssembler) assembler).marshal(locales, locale.getLanguageCode());
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}

		
		LocalesManagerViewBean view = (LocalesManagerViewBean) viewBean;
		view.setList(list);
		java.util.Locale userLocale = new java.util.Locale(locale.getLanguageCode());
		view.setLanguages(this.getAvailableLanguages(userLocale));
	}

	@Override
	public IMdoBean findByLanguage(String language) throws MdoBusinessException {
		try {
			return assembler.marshal((IMdoDaoBean) dao.findByUniqueKey(language));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find", new Object[] { language }, e);
			throw new MdoBusinessException("message.error.administration.business.find", new Object[] { language }, e);
		}
	}

	@Override
	public Map<String, String> getAvailableLanguages(java.util.Locale currentLocale) throws MdoBusinessException {
		java.util.Locale[] locales = java.util.Locale.getAvailableLocales();
		Map<String, String> result = new HashMap<String, String>(locales.length);
		List<IMdoBean> localesListFromDba;
		// Be sure that the server manages this language
		if (!isoLanguagesList.contains(currentLocale)) {
			currentLocale = java.util.Locale.getDefault();
		}
		try {
			localesListFromDba = dao.findAll();
			for (int i = 0; i < locales.length; i++) {
				java.util.Locale locale = locales[i];
				boolean doesLocaleAlreadyExistInDba = false;
				for (int j = 0; j < localesListFromDba.size(); j++) {
					Locale myLocale = (Locale) localesListFromDba.get(j);
					if (locale.getLanguage().equals(myLocale.getLanguage())) {
						doesLocaleAlreadyExistInDba = true;
						break;
					}
				}
				if (!doesLocaleAlreadyExistInDba) {
					result.put(locale.getLanguage(), locale.getDisplayLanguage(currentLocale));
				}
			}
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}

		// Sort the map
		return UtilsImpl.getInstance().sortedMapByValue(result);
	}

	@Override
	public Map<String, String> getSystemAvailableLanguages(java.util.Locale currentLocale) throws MdoBusinessException {
		java.util.Locale[] locales = java.util.Locale.getAvailableLocales();
		Map<String, String> result = new HashMap<String, String>(locales.length);
		// Be sure that the server manages this language
		if (!isoLanguagesList.contains(currentLocale)) {
			currentLocale = java.util.Locale.getDefault();
		}
		for (int i = 0; i < locales.length; i++) {
			java.util.Locale locale = locales[i];
			result.put(locale.getLanguage(), locale.getDisplayLanguage(currentLocale));
		}
		// Sort the map
		return UtilsImpl.getInstance().sortedMapByValue(result);
	}

	@Override
	public Map<Long, String> getLanguages(java.util.Locale currentLocale) throws MdoBusinessException {
		List<IMdoBean> localesListFromDba;
		try {
			localesListFromDba = dao.findAll();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}

		// Be sure that the server manages this language
		if (!isoLanguagesList.contains(currentLocale)) {
			currentLocale = java.util.Locale.getDefault();
		}

		Map<Long, String> result = new HashMap<Long, String>(localesListFromDba.size());
		for (int i = 0; i < localesListFromDba.size(); i++) {
			Locale myLocale = (Locale) localesListFromDba.get(i);
			result.put(myLocale.getId(), (new java.util.Locale(myLocale.getLanguage())).getDisplayLanguage(currentLocale));
		}

		// Sort the map
		return UtilsImpl.getInstance().sortedMapByValue(result);
	}

	@Override
	public Map<Long, String> getLanguages(String languageCode) throws MdoBusinessException {
		return this.getLanguages(this.getLocale(languageCode));
	}
	
	/**
	 * Get the java.util.Locale from locale language iso code
	 * @param languageCode language iso code
	 * @return a locale
	 */
	private java.util.Locale getLocale(String languageCode) {
		if (languageCode != null) {
			return new java.util.Locale(languageCode);
		}
		return java.util.Locale.getDefault();
	}
	
	@Override
	public List<LocaleDto> getLanguageLocales(String defaultLanguageCode) throws MdoBusinessException {
		List<IMdoBean> localesListFromDba;
		try {
			localesListFromDba = dao.findAll();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
/*
 TODO userContext
		String defaultLanguageCode = null;
		if (userContext != null && userContext.getCurrentLocale() != null) {
			defaultLanguageCode = userContext.getCurrentLocale().getLanguageCode();
		}
*/		
		Set<LocaleDto> result = new TreeSet<LocaleDto>(comparator);
		for (int i = 0; i < localesListFromDba.size(); i++) {
			Locale myLocale = (Locale) localesListFromDba.get(i);
			LocaleDto localeDto = (LocaleDto) ((ILocalesAssembler) super.assembler).marshal(myLocale, defaultLanguageCode);
			result.add(localeDto);
		}

		return new ArrayList<LocaleDto>(result);
	}

	private Comparator<LocaleDto> comparator = new Comparator<LocaleDto>() {
		@Override
		public int compare(LocaleDto o1, LocaleDto o2) {
			int result = 0;
			if (o1 == null && o2 == null) {
			} else if (o1 == null) {
				result = -1;
			} else if (o2 == null) {
				result = 1;
			} else {
				// First compare with Display Language
				if (o1.getDisplayLanguage() != null) {
					result = o1.getDisplayLanguage().compareTo(o2.getDisplayLanguage());
				} else if (o2.getDisplayLanguage() != null) {
					result = -1;
				}
				if (result == 0) {
					// Second compare with Language Code
					if (o1.getLanguageCode() != null) {
						result = o1.getLanguageCode().compareTo(o2.getLanguageCode());
					} else if (o2.getLanguageCode() != null) {
						result = -1;
					}
				}
			}
			return result;
		}
	};

	@Override
	public LocaleDto findLocale(java.util.Locale locale, Set<UserLocaleDto> defaultLocales) throws MdoBusinessException {
		LocaleDto result = null;
		// Be sure that the server manages this language
		if (!isoLanguagesList.contains(locale)) {
			locale = java.util.Locale.getDefault();
		}
		String language = locale.getLanguage();
/**		
 TODO userContext
		if (userContext != null) {
			UserAuthenticationDto userX = userContext.getUserAuthentication();
			if (userX != null && userX.getLocales() != null && !userX.getLocales().isEmpty()) {
				UserLocaleDto userLocale = null;
				for (Iterator<UserLocaleDto> i = userX.getLocales().iterator(); i.hasNext();) {
					userLocale = i.next();
					if (userLocale != null && userLocale.getLocale() != null && userLocale.getLocale().getLanguageCode() != null
							&& userLocale.getLocale().getLanguageCode().equals(language)) {
						// Get the one in the user locales equals to the
						// specific locale
						result = userLocale.getLocale();
						break;
					}
				}
				// Get the last one in the user locales
				if (result == null && userLocale != null) {
					result = userLocale.getLocale();
				}
			}
		}
*/		
		if (defaultLocales != null && !defaultLocales.isEmpty()) {
			UserLocaleDto userLocale = null;
			for (Iterator<UserLocaleDto> i = defaultLocales.iterator(); i.hasNext();) {
				userLocale = i.next();
				if (userLocale != null && userLocale.getLocale() != null && userLocale.getLocale().getLanguageCode() != null
						&& userLocale.getLocale().getLanguageCode().equals(language)) {
					// Get the one in the user locales equals to the
					// specific locale
					result = userLocale.getLocale();
					break;
				}
			}
			// Get the last one in the user locales
			if (result == null && userLocale != null) {
				result = userLocale.getLocale();
			}
		}

		if (result == null) {
			// Get the one from database by specific language
			result = (LocaleDto) findByLanguage(language);
		}
		if (result == null) {
			try {
				// Get the first one from database without specific language
				List<IMdoBean> list = dao.findAll();
				if (list != null && !list.isEmpty()) {
					result = (LocaleDto) assembler.marshal((IMdoDaoBean) dao.findAll().get(0));
				}
			} catch (MdoException e) {
				logger.error("message.error.administration.business.find.all", e);
				throw new MdoBusinessException("message.error.administration.business.find.all", e);
			}
		}
		if (result == null) {
			// Create new one with specific language
			result = new LocaleDto();
			result.setLanguageCode(language);
			result.setDisplayLanguage(locale.getDisplayLanguage());
		}

		return result;
	}
}
