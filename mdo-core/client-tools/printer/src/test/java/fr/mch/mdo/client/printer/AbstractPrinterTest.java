/*
 * AbstractPrinterTest.java
 *
 * Created on 21 février 2002, 20:57
 */
package fr.mch.mdo.client.printer;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.test.MdoTestCase;


/**
 * In order to run this class, we have 2 choices depending on how you are going to run it:
 * 1) If you run it in command line then add the system property java.library.path and point to the right folder.
 * 2) If you run it in eclipse, 
 * 		2.1) Open the "Package Explorer", 
 * 		2.2) "Alt + Enter" on the rxtx jar file(in "Referenced Libraries"), 
 * 		2.3) Enter into "Native Lirary" menu,
 * 		2.4) Point to the right location path.
 * @author mathieu ma
 * @version
 */
public class AbstractPrinterTest extends MdoTestCase
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AbstractPrinterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AbstractPrinterTest.class);
	}

	public void testAnonymousInstance() {
		IPrinter printer = new AbstractPrinter() {
			@Override
			public String getParameter(String key, String defaultValue) {
				return defaultValue;
			}
		};
		assertNotNull("Check AbstractPrinter instance", printer);
	}

	public void testInit() {
		IPrinter printer = new AbstractPrinter() {
			@Override
			public String getParameter(String key, String defaultValue) {
				return defaultValue;
			}
		};
		assertNotNull("Check AbstractPrinter instance", printer);
		printer.init();
	}
}
