package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author user
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class NameMdoCallbackTest extends MdoTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NameMdoCallbackTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(NameMdoCallbackTest.class);
    }

    public void testCallback() {
	String login = null;
	NameMdoCallback callback = new NameMdoCallback();
	Map<MdoCallBackType, Object> properties = null;
	try {
	    assertEquals("Check Callback Name", login, callback.getName());
	    // properties is null
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}

	properties = new HashMap<MdoCallBackType, Object>();
	try {
	    assertEquals("Check Callback Name", login, callback.getName());
	    // properties is empty
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}
	
	properties.put(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD, "dummy");
	try {
	    assertEquals("Check Callback Name", login, callback.getName());
	    // properties does not contain the right callback
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}

	String realLogin = "realLogin"; 
	properties.put(MdoCallBackType.MDO_CALLBACK_NAME, realLogin);
	try {
	    assertEquals("Check Callback Name", login, callback.getName());
	    // It's OK
	    callback.callback(properties);
	    assertEquals("Check Callback Name", realLogin, callback.getName());
	} catch (MdoAuthenticationException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	}
    }
}
