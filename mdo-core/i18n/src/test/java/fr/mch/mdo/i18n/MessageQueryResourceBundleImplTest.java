package fr.mch.mdo.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.test.MdoTestCase;

/**
 * This class is used to test log messages from a properties file
 * 
 * @author Mathieu
 * 
 */
public class MessageQueryResourceBundleImplTest extends MdoTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MessageQueryResourceBundleImplTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MessageQueryResourceBundleImplTest.class);
	}

	public void testMessageQueryResourceBundleImpl() {
		IMessageQuery messageQuery = new MessageQueryResourceBundleImpl(MessageQueryResourceBundleImplTest.class.getName());
		String query = "my.key.en.fr";
		String value = "Impossible de sauver un objet {0} : {1}";
		Object[] params = new Object[] { "1", "2" };
		assertEquals("Message must equal to value", value, messageQuery.getMessage(query));
		assertEquals("Message must equal to value with parameters", MessageFormat.format(value, params), messageQuery.getMessage(query, params));
		messageQuery = new MessageQueryResourceBundleImpl(MessageQueryResourceBundleImplTest.class.getName(), null);
		query = "my.key.en.fr";
		value = "Impossible de sauver un objet {0} : {1}";
		params = new Object[] { "1", "2" };
		assertEquals("Message must equal to value", value, messageQuery.getMessage(query));
		assertEquals("Message must equal to value with parameters", MessageFormat.format(value, params), messageQuery.getMessage(query, params));
		messageQuery = new MessageQueryResourceBundleImpl(MessageQueryResourceBundleImplTest.class.getName(), Locale.ENGLISH);
		query = "my.key.en.fr";
		value = "Could not save an object {0} : {1}";
		params = new Object[] { "1", "2" };
		assertEquals("Message must equal to value", value, messageQuery.getMessage(query));
		assertEquals("Message must equal to value with parameters", MessageFormat.format(value, params), messageQuery.getMessage(query, params));
	}

	/**
	 * Test all getMessage methods
	 */
	public void testGetMessage() {
		// The resource file does not exist
		IMessageQuery messageQuery = new MessageQueryResourceBundleImpl("");
		String query = "query";
		assertEquals("Message must equal to query", query, messageQuery.getMessage(query));
		assertEquals("Message must equal to query", query, messageQuery.getMessage(query, new String[] { "1", "2" }));
		// The resource file does exist
		messageQuery = new MessageQueryResourceBundleImpl(MessageQueryResourceBundleImplTest.class.getName());
		// Query does not exist
		query = "query";
		assertEquals("Message must equal to query", query, messageQuery.getMessage(query));
		assertEquals("Message must equal to query", query, messageQuery.getMessage(query, new String[] { "1", "2" }));
		// Query does exist
		// Key does exist in the resource file
		query = "my.key";
		String value = "Impossible de sauver l''objet {0} : {1}";
		Properties properties = new Properties();
		properties.setProperty(query, value);
		// Add new properties
		updatePropertiesFile(MessageQueryResourceBundleImplTest.class, properties, false);
		try {
			// The resource file does exist
			messageQuery = new MessageQueryResourceBundleImpl(MessageQueryResourceBundleImplTest.class.getName());
			assertEquals("Message must equal to value", value.replaceFirst("'", ""), messageQuery.getMessage(query));
			assertEquals("Message must equal to value with parameters", "Impossible de sauver l'objet 1 : 2", messageQuery.getMessage(query, new String[] { "1", "2" }));
		} finally {
			// Remove new properties
			updatePropertiesFile(MessageQueryResourceBundleImplTest.class, properties, true);
		}
	}
}
