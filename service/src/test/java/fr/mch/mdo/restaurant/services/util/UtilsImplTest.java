package fr.mch.mdo.restaurant.services.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.test.MdoTestCase;

public class UtilsImplTest extends MdoTestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public UtilsImplTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(UtilsImplTest.class);
    }

    public void testGetSystemAvailableLanguages() {
	IUtils utils = UtilsImpl.getInstance();

	// Locale null
	Locale locale = null;
	Map<String, String> map = utils.getSystemAvailableLanguages(locale);
	checkLanguage(map, Locale.getDefault());

	// Locale does not exist
	locale = new Locale("titi", "toto");
	map = utils.getSystemAvailableLanguages(locale);
	checkLanguage(map, Locale.getDefault());
	
	// Nominal Scenario 
	locale = Locale.FRENCH;
	map = utils.getSystemAvailableLanguages(locale);
	checkLanguage(map, Locale.FRENCH);
    }

    private void checkLanguage(Map<String, String> map, Locale selectedLocale) {

	// Check only sorted displayed languages
	String[] values = new String[map.size()];
	int index = 0;
	for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
            String key = i.next();
            values[index++] = (map.get(key));
	}
	// Get Available Locales
	Locale[] locales = Locale.getAvailableLocales();
	// Several locales could have the same displayed language
	// So put all displayed languages in a set to have no duplicated displayed languages
	Set<String> languagesSet = new HashSet<String>();
	for (Locale locale : locales) {
	    languagesSet.add(locale.getDisplayLanguage(selectedLocale));
	}
	// Create sorted arrays by displayed languages
	String[] languages = new String[languagesSet.size()];
	// Sort displayed languages
	languagesSet.toArray(languages);
	Arrays.sort(languages);
	// Check languages
	assertEquals("The 2 arrays languages length must be equals", languages.length, values.length);
	for (index = 0; index < languages.length; index++) {
	    assertEquals("Each language must be equals in string natural order", languages[index], values[index]);
	}
    }
    
    public void testSortedMapByValue() {
	
	IUtils utils = UtilsImpl.getInstance();
	
	// 1) Case where the 2 maps size is equals
	// map contains key=ISO code language and value=displayed language in specific language
	Map<String, String> map = new HashMap<String, String>();
	map.put(Locale.CANADA.getLanguage(), Locale.CANADA.getDisplayLanguage(Locale.FRANCE));
	map.put(Locale.CANADA_FRENCH.getLanguage(), Locale.CANADA_FRENCH.getDisplayLanguage(Locale.FRANCE));
	map.put(Locale.CHINA.getLanguage(), Locale.CANADA_FRENCH.getDisplayLanguage(Locale.FRANCE));
	map.put(Locale.FRANCE.getLanguage(), Locale.FRANCE.getDisplayLanguage(Locale.FRANCE));
	Map<String, String> sortedMap = utils.sortedMapByValue(map);
	// But in this case the size is equals
	// Check languages
	assertEquals("The 2 arrays languages lenfth must be equals", map.size(), sortedMap.size());
	// Create sorted arrays by displayed languages
	String[] languages = new String[map.size()];
	// Sort displayed languages
	map.values().toArray(languages);
	Arrays.sort(languages);
	// Check languages
	assertEquals("The 2 arrays languages length must be equals", languages.length, map.size());
	int index = 0;
	for (String key : sortedMap.keySet()) {
	    assertEquals("Each language must be equals in string natural order", languages[index], sortedMap.get(key));
	    index++;
	}
	
	// 2) Case where the original map size is greater than the swapped map size
	map = new HashMap<String, String>();
	map.put("1", "value");
	map.put("2", "value");
	map.put("3", "value3");
	sortedMap = utils.sortedMapByValue(map);
	// Check size
	assertEquals("Original map size is equals to sorted map size", map.size(), sortedMap.size());
	// But in this case the size is equals
	// Check languages
	assertEquals("The 2 arrays languages lenfth must be equals", map.size(), sortedMap.size());
	// Create sorted arrays by displayed languages
	languages = new String[map.size()];
	// Sort displayed languages
	map.values().toArray(languages);
	Arrays.sort(languages);
	// Check languages
	assertEquals("The 2 arrays languages length must be equals", languages.length, map.size());
	index = 0;
	for (String key : sortedMap.keySet()) {
	    assertEquals("Each language must be equals in string natural order", languages[index], sortedMap.get(key));
	    index++;
	}

	// 3) Map is null
	map = null;
	sortedMap = utils.sortedMapByValue(map);
	assertNull("Swapped map is null", sortedMap);
    }
}
