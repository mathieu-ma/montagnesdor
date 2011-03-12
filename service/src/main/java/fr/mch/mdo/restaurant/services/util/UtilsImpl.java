package fr.mch.mdo.restaurant.services.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class UtilsImpl implements IUtils {

	private static ILogger logger = LoggerServiceImpl.getInstance().getLogger(UtilsImpl.class.getName());

	private static List<java.util.Locale> isoLanguagesList = Arrays.asList(java.util.Locale.getAvailableLocales());

	/**
	 * This class is only used for Singleton lazy initialization
	 * 
	 * @author Mathieu
	 * 
	 */
	private static class InitializeOnDemandHolder {
		/** Singleton */
		private static UtilsImpl instance;
		static {
			logger.info("Instantiating the class " + UtilsImpl.class.getName());
			instance = new UtilsImpl();
			logger.info(UtilsImpl.class.getName() + " class instantiated");
		}
	}

	public static IUtils getInstance() {
		return InitializeOnDemandHolder.instance;
	}

	public Map<String, String> getSystemAvailableLanguages(final Locale currentLocale) {
		Locale currentLocaleX = currentLocale;
		Locale[] locales = Locale.getAvailableLocales();

		// Check that the currentLocale locale exists in the system available
		// locales
		// Be sure that the server manages this language
		if (!isoLanguagesList.contains(currentLocaleX)) {
			currentLocaleX = java.util.Locale.getDefault();
		}

		// Fill map
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < locales.length; i++) {
			Locale locale = locales[i];
			String languageCode = locale.getLanguage();
			String displayedLanguage = locale.getDisplayLanguage(currentLocaleX);
			result.put(languageCode, displayedLanguage);
			logger.debug(i + ") Key=" + languageCode + " == Value=" + displayedLanguage);
		}

		// Sorted map
		return sortedMapByValue(result);
	}

	@SuppressWarnings("unchecked")
	public <K extends Comparable, V extends Comparable> Map<K, V> sortedMapByValue(Map<K, V> sortableMap) {
		Map<K, V> result = null;
		if (sortableMap != null) {
			// Sort the entries of map by value
			List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>(sortableMap.entrySet());
			Collections.sort(entries, new Comparator<Entry<K, V>>() {
				@Override
				public int compare(Entry<K, V> o1, Entry<K, V> o2) {
					int result = 0;
					if (o1 == null && o2 == null) {
					} else if (o1 == null) {
						result = -1;
					} else if (o2 == null) {
						result = 1;
					} else {
						// First compare with Value
						if (o1.getValue() != null) {
							result = o1.getValue().compareTo(o2.getValue());
						} else if (o2.getValue() != null) {
							result = -1;
						}
						if (result == 0) {
							// Second compare with Key
							if (o1.getKey() != null) {
								result = o1.getKey().compareTo(o2.getKey());
							} else if (o2.getKey() != null) {
								result = -1;
							}
						}
					}
					return result;
				}
			});
			// Rebuild the map
			result = new LinkedHashMap<K, V>(entries.size());
			for (Entry<K, V> entry : entries) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}
}
