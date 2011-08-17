package fr.mch.mdo.applets;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.test.MdoTestCase;

/**
 * Unit test for DateTimeAppletTest.
 */
public class DateTimeAppletTest extends MdoTestCase
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DateTimeAppletTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DateTimeAppletTest.class);
	}

	public void testGetResourceString() {
		Class<?> clazz = DateTimeApplet.class;
		DateTimeApplet applet = new DateTimeApplet();
		String key = "my.key";
		String value = "My Key";
		String backupValue = null;
		Properties properties = new Properties();
		System.out.println("******************* START getResourceString test without resource file *******************");
		try {
			// With default value
			assertEquals("Method getResourceString must return the non found key", key, invokeGetResourceString(applet, key, true));
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		try {
			// With No default value
			assertNull("Method getResourceString must return null", invokeGetResourceString(applet, key, false));
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		System.out.println("******************* END getResourceString test without resource file *******************");

		System.out.println("******************* START getResourceString test with resource file *******************");
		applet = new DateTimeApplet();
		// Load resource properties if needed
		applet.init();
		try {
			// Key does exist in the resource file
			key = "my.key";
			value = "My Key";
			properties = new Properties();
			properties.setProperty(key, value);
			// Add new properties
			updatePropertiesFile(clazz, properties, false);
			// Load resource properties if needed
			applet.init();
			// With default value
			assertEquals("Method getResourceString must return a value", value, invokeGetResourceString(applet, key, true));
			// With No default value
			assertEquals("Method getResourceString must return a value", value, invokeGetResourceString(applet, key, false));
			// Remove new properties
			updatePropertiesFile(clazz, properties, true);
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		applet = new DateTimeApplet();
		// Load resource properties if needed
		applet.init();
		try {
			// Key and
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// do not exist in the resource file
			// Get the current properties file
			properties = super.getProperties(clazz);
			key = DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY;
			backupValue = properties.getProperty(key);
			properties = new Properties();
			if (backupValue != null) {
				properties.setProperty(key, backupValue);
			}
			// Remove properties
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// does not exist anymore
			updatePropertiesFile(clazz, properties, true);
			key = "my.key";
			// With default value
			assertEquals("Method getResourceString must return a default message", String.format(DateTimeApplet.DEFAULT_RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND, key),
					invokeGetResourceString(applet, key, true));
			// With No default value
			assertNull("Method getResourceString must return null", invokeGetResourceString(applet, key, false));
			// Restore new properties
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// does exist again
			updatePropertiesFile(clazz, properties, false);
			// Key does not exist and
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// do exist in the resource file
			key = DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY;
			value = "My Value";
			properties = new Properties();
			properties.setProperty(key, value);
			// Add key
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// to be sure that does exist
			updatePropertiesFile(clazz, properties, false);
			key = "my.key";
			applet = new DateTimeApplet();
			// Load resource properties if needed
			applet.init();
			// With default value
			assertEquals("Method getResourceString must return a default message", String.format(value, key), invokeGetResourceString(applet, key, true));
			// With No default value
			assertNull("Method getResourceString must return null", invokeGetResourceString(applet, key, false));
			// Restore new properties
			// DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY
			// does exist again
			properties = new Properties();
			properties.setProperty(DateTimeApplet.RESOURCE_MESSAGE_APPLET_PARAMETER_NAME_NOT_FOUND_KEY, backupValue);
			updatePropertiesFile(clazz, properties, false);
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		System.out.println("******************* END getResourceString test with resource file *******************");
	}

	public void testGetParameter() {
		Class<?> clazz = DateTimeApplet.class;
		DateTimeApplet applet = new DateTimeApplet();
		String key = "my.key";
		String value = "My Key";
		String defaultValue = "My Default Value";
		Properties properties = new Properties();
		System.out.println("******************* START getParameter test without resource file *******************");
		try {
			// With default value
			assertEquals("Method getParameter must return the default value", defaultValue, invokeGetParameter(applet, key, defaultValue));
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		System.out.println("******************* END getParameter test without resource file *******************");
		// Load resource properties if needed
		applet.init();
		System.out.println("******************* START getParameter test with resource file *******************");
		try {
			// With key does exist and With default value
			assertEquals("Method getParameter must return the default value", defaultValue, invokeGetParameter(applet, key, defaultValue));
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		properties = new Properties();
		properties.setProperty(key, value);
		// Add new properties
		updatePropertiesFile(clazz, properties, false);
		try {
			// ReLoad back again the resource properties if needed
			applet.init();
			// With key exists and With default value
			assertEquals("Method getParameter must return the key value", value, invokeGetParameter(applet, key, defaultValue));
		} catch (InvocationTargetException e) {
			fail("No Exception could be thrown");
		}
		updatePropertiesFile(clazz, properties, true);
		System.out.println("******************* END getParameter test with resource file *******************");
	}

	private Object invokeGetParameter(DateTimeApplet applet, String key, String defaultValue) throws InvocationTargetException {
		// Purposely pass null values to the method, to make sure it throws
		// NullPointerException
		Class<?>[] argClasses = { String.class, String.class };
		Object[] argObjects = { key, defaultValue };

		return invokeInstanceMethod(applet, "getParameter", argClasses, argObjects);
	}

	private Object invokeGetResourceString(DateTimeApplet applet, String key, boolean isDefaultValue) throws InvocationTargetException {
		// Purposely pass null values to the method, to make sure it throws
		// NullPointerException
		Class<?>[] argClasses = { String.class, Boolean.TYPE };
		Object[] argObjects = { key, isDefaultValue };

		return invokeInstanceMethod(applet, "getResourceString", argClasses, argObjects);
	}

	/**
	 * Test applet init method
	 */
	public void testInit() {

		// Test without DateTimeApplet.properties: remove all files
		// DateTimeApplet.properties in the class path(2 files)
		DateTimeApplet applet = new DateTimeApplet();
		Class<?> relativePropertiesFileName = DateTimeApplet.class;
		String fileExtension = ".properties";
		String backupFileExtension = ".backup";

		System.out.println("******************* START init test without properties file *******************");
		// Rename
		renamePropertiesFileExtension(relativePropertiesFileName, fileExtension, backupFileExtension);
		try {
			assertEquals("Checking the debug flag", false, applet.isDebug());
			applet.init();
			checkDefaultInitValue(applet);
			assertEquals("Checking the debug flag", true, applet.isDebug());
			assertNull("The startJavascriptFunction must be null", applet.getStartJavascriptFunction());
		} finally {
			// Rename back
			renamePropertiesFileExtension(relativePropertiesFileName, backupFileExtension, fileExtension);
		}
		System.out.println("******************* END init test without properties file *******************");

		System.out.println("*******************New init test******************");
		applet = new DateTimeApplet();
		assertEquals("Checking the debug flag", false, applet.isDebug());
		applet.init();
		checkDefaultInitValue(applet);
		// Because there is no
		// DateTimeApplet.APPLET_PARAMETER_LOCALE_LANGUAGE_KEY
		assertEquals("Checking the debug flag", true, applet.isDebug());
	}

	/**
	 * 
	 * @param applet
	 */
	private void checkDefaultInitValue(DateTimeApplet applet) {
		assertNotNull("The locale must not be null", applet.getLocale());
		assertEquals("Checking the applet.getLocale().getLanguage() ", Locale.getDefault().getLanguage(), applet.getLocale().getLanguage());

		assertNotNull("The formatDisplayDate must not be null", applet.getFormatDisplayDate());
		assertEquals("Checking the patternDisplayDate", DateTimeApplet.DEFAULT_PATTERN_DISPLAY_DATE, applet.getFormatDisplayDate().toPattern());
		assertNotNull("The formatApplicationDateShort must not be null", applet.getFormatApplicationDateShort());
		assertEquals("Checking the patternApplicationDateShort", DateTimeApplet.DEFAULT_PATTERN_APPLICATION_DATE_SHORT, applet.getFormatApplicationDateShort().toPattern());
		assertNotNull("The formatApplicationDateLong must not be null", applet.getFormatApplicationDateLong());
		assertEquals("Checking the patternApplicationDateLong", DateTimeApplet.DEFAULT_PATTERN_APPLICATION_DATE_LONG, applet.getFormatApplicationDateLong().toPattern());
		assertNotNull("The formatDisplayDateTime must not be null", applet.getFormatDisplayDateTime());
		assertEquals("Checking the patternDisplayDateTime", DateTimeApplet.DEFAULT_PATTERN_DISPLAY_DATE_TIME, applet.getFormatDisplayDateTime().toPattern());

		assertNotNull("The entryDate must not be null", applet.getEntryDate());
		try {
			assertEquals("The entryDate must be equals to the current date without time", applet.getFormatApplicationDateShort().format(new Date()),
					applet.getFormatApplicationDateShort().format(applet.getFormatApplicationDateLong().parse(applet.getEntryDate())));
		} catch (ParseException e) {
			fail("Could not parse the date");
		}
	}

	public void testGetDateShort() {
		DateTimeApplet applet = new DateTimeApplet();
		System.out.println("******************* START getDateShort test default behavior *******************");
		applet.init();
		assertNotNull("The formatApplicationDateShort must not be null", applet.getFormatApplicationDateShort());
		assertEquals("Method getDateShort must return a well formatted date", applet.getFormatApplicationDateShort().format(new Date()), applet.getDateShort());
		System.out.println("******************* END getDateShort test default behavior *******************");
	}

	public void testGetShortEntryDate() {
		String entryDate = "15/08/1970/15/08/1970";
		DateTimeApplet applet = new DateTimeApplet();
		System.out.println("******************* START getShortEntryDate test default behavior *******************");
		applet.init();
		assertNotNull("The formatApplicationDateShort must not be null", applet.getFormatApplicationDateShort());
		assertEquals("Method getShortEntryDate must return a well formatted date", applet.getFormatApplicationDateShort().format(new Date()), applet.getShortEntryDate());
		System.out.println("******************* END getShortEntryDate test default behavior *******************");
		System.out.println("******************* START getShortEntryDate test with well formatted entry date *******************");
		// dd/MM/yyyy/HH/mm/ss
		entryDate = "15/08/1970/15/08/1970";
		applet.setEntryDate(entryDate);
		assertEquals("applet.setEntryDate must return setted entry date", entryDate, applet.getEntryDate());
		assertEquals("Method getShortEntryDate must return nothing", "15/08/1970", applet.getShortEntryDate());
		System.out.println("******************* END getShortEntryDate test with well formatted entry date *******************");
		System.out.println("******************* START getShortEntryDate test with null entry date *******************");
		entryDate = null;
		applet.setEntryDate(entryDate);
		assertEquals("applet.setEntryDate must return setted entry date", entryDate, applet.getEntryDate());
		assertEquals("Method getShortEntryDate must return nothing", "", applet.getShortEntryDate());
		System.out.println("******************* END getShortEntryDate test with null entry date *******************");
		System.out.println("******************* START getShortEntryDate test with bad formatted entry date *******************");
		entryDate = "dd/MM/yyyy/HH/mm/ss";
		applet.setEntryDate(entryDate);
		assertEquals("applet.setEntryDate must return setted entry date", entryDate, applet.getEntryDate());
		assertEquals("Method getShortEntryDate must return nothing", "", applet.getShortEntryDate());
		System.out.println("******************* END getShortEntryDate test with bad formatted entry date *******************");
	}

	public void testFormatUpperCase() {
		String valueToBeFormatted = null;
		assertNull("DateTimeApplet.formatUpperCase must return null", DateTimeApplet.formatUpperCase(valueToBeFormatted));
		// valueToBeFormatted length == 0
		valueToBeFormatted = "";
		assertEquals("DateTimeApplet.formatUpperCase must return nothing", valueToBeFormatted, DateTimeApplet.formatUpperCase(valueToBeFormatted));
		// valueToBeFormatted length == 1
		valueToBeFormatted = "h";
		assertEquals("DateTimeApplet.formatUpperCase must return string with first letter in upper case", valueToBeFormatted.toUpperCase(), DateTimeApplet.formatUpperCase(valueToBeFormatted));
		// valueToBeFormatted length == 1
		valueToBeFormatted = "hello world";
		assertEquals("DateTimeApplet.formatUpperCase must return string with first letter in upper case", valueToBeFormatted.substring(0, 1).toUpperCase() + valueToBeFormatted.substring(1),
				DateTimeApplet.formatUpperCase(valueToBeFormatted));
	}

	public void testGetDate() {
		DateTimeApplet applet = new DateTimeApplet();
		System.out.println("******************* START getDate test default behavior *******************");
		applet.init();
		assertNotNull("The formatDisplayDate must not be null", applet.getFormatDisplayDate());
		assertEquals("Method getDate must return a well formatted date", DateTimeApplet.formatUpperCase(applet.getFormatDisplayDate().format(new Date())), applet.getDate());
		System.out.println("******************* END getDate test default behavior *******************");
	}

	public void testGetDateTime() {
		DateTimeApplet applet = new DateTimeApplet();
		System.out.println("******************* START getDateTime test default behavior *******************");
		applet.init();
		assertNotNull("The formatDisplayDateTime must not be null", applet.getFormatDisplayDateTime());
		assertEquals("Method getDateTime must return a well formatted date", DateTimeApplet.formatUpperCase(applet.getFormatDisplayDateTime().format(new Date())), applet.getDateTime());
		System.out.println("******************* END getDateTime test default behavior *******************");
	}

	public void testGetTime() {
		DateTimeApplet applet = new DateTimeApplet();
		System.out.println("******************* START getTime test default behavior *******************");
		applet.init();
		assertNotNull("The formatDisplayTime must not be null", applet.getFormatDisplayTime());
		assertEquals("Method getDateTime must return a well formatted date", DateTimeApplet.formatUpperCase(applet.getFormatDisplayTime().format(new Date())), applet.getTime());
		System.out.println("******************* END getTime test default behavior *******************");
	}

	public void testStart() {

	}

}
