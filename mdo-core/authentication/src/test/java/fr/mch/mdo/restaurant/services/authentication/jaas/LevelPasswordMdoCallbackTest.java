package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author user
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class LevelPasswordMdoCallbackTest extends MdoTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LevelPasswordMdoCallbackTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(LevelPasswordMdoCallbackTest.class);
    }

    public void testCallback() {
	AuthenticationPasswordLevel levelPassword = null;
	LevelPasswordMdoCallback callback = new LevelPasswordMdoCallback();
	Map<MdoCallBackType, Object> properties = null;
	try {
	    assertEquals("Check Callback LevelPassword", levelPassword, callback.getLevelPassword());
	    // properties is null
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}

	properties = new HashMap<MdoCallBackType, Object>();
	try {
	    assertEquals("Check Callback LevelPassword", levelPassword, callback.getLevelPassword());
	    // properties is empty
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}
	
	properties.put(MdoCallBackType.MDO_CALLBACK_NAME, "dummy");
	try {
	    assertEquals("Check Callback LevelPassword", levelPassword, callback.getLevelPassword());
	    // properties does not contain the right callback
	    callback.callback(properties);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (MdoAuthenticationException e) {
	    assertNotNull("This exception must be thrown", e.getMessage());
	}

	AuthenticationPasswordLevel realLevelPassword = AuthenticationPasswordLevel.PASSWORD_LEVEL_TWO;
	properties.put(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD, realLevelPassword);
	try {
	    assertEquals("Check Callback LevelPassword", levelPassword, callback.getLevelPassword());
	    // It's OK
	    callback.callback(properties);
	    assertEquals("Check Callback LevelPassword", realLevelPassword, callback.getLevelPassword());
	} catch (MdoAuthenticationException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	}
    }
}
