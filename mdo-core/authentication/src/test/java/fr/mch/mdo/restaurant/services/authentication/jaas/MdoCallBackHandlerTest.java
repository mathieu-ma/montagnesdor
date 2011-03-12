package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MdoCallBackHandlerTest extends MdoTestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *                name of the test case
     */
    public MdoCallBackHandlerTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoCallBackHandlerTest.class);
    }
    
    @SuppressWarnings("unchecked")
    public void testConstructor() {
	String login = null;
	String password = null;
	AuthenticationPasswordLevel levelPassword = null;
	MdoCallBackHandler mdoCallBackHandler = new MdoCallBackHandler(login, password, levelPassword);
	Object propertiesField = super.getField(mdoCallBackHandler, "properties");
	assertNotNull("Check MdoCallBackHandler properties", propertiesField);
	assertTrue("Check MdoCallBackHandler properties instance", propertiesField instanceof Map<?, ?>);
	Map<MdoCallBackType, Object> properties = (Map<MdoCallBackType, Object>) propertiesField;
	assertEquals("Check MdoCallBackHandler properties size", 3, properties.size());
	for (MdoCallBackType callbackType : properties.keySet()) {
	    assertNull("Check MdoCallBackHandler properties value", properties.get(callbackType));
	}
	
	login = "login";
	password = "password";
	levelPassword = AuthenticationPasswordLevel.PASSWORD_LEVEL_ONE;
	mdoCallBackHandler = new MdoCallBackHandler(login, password, levelPassword);
	propertiesField = super.getField(mdoCallBackHandler, "properties");
	assertNotNull("Check MdoCallBackHandler properties", propertiesField);
	assertTrue("Check MdoCallBackHandler properties instance", propertiesField instanceof Map<?, ?>);
	properties = (Map<MdoCallBackType, Object>) propertiesField;
	assertEquals("Check MdoCallBackHandler properties size", 3, properties.size());
	for (MdoCallBackType callbackType : properties.keySet()) {
	    assertNotNull("Check MdoCallBackHandler properties value", properties.get(callbackType));
	}
    }

    @SuppressWarnings("unchecked")
    public void testHandle() {
	Map<MdoCallBackType, Object> addedProperties = new HashMap<MdoCallBackType, Object>();
	// callbacks null
	checkHandle(null, addedProperties);
	// callbacks empty
	checkHandle(new Callback[] {}, addedProperties);
	// PasswordCallback callback is not managed by MdoCallBackHandler
	checkHandle(new Callback[] {new PasswordCallback("prompt", false)}, addedProperties);
	// LevelPasswordMdoCallback callback will rise an exception
	// because value in properties for LevelPasswordMdoCallback is null
	checkHandle(new Callback[] {new LevelPasswordMdoCallback()}, addedProperties);
	
	String login = null;
	String password = null;
	AuthenticationPasswordLevel levelPassword = null;
	MdoCallBackHandler mdoCallBackHandler = new MdoCallBackHandler(login, password, levelPassword);
	Object propertiesField = super.getField(mdoCallBackHandler, "properties");
	Map<MdoCallBackType, Object> properties = (Map<MdoCallBackType, Object>) propertiesField;
	properties.put(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD, AuthenticationPasswordLevel.PASSWORD_LEVEL_ONE);
	// It's OK
	try {
	    assertEquals("Check MdoCallBackHandler properties size", 3, properties.size());
	    mdoCallBackHandler.handle(new Callback[] {new LevelPasswordMdoCallback()});
	} catch (Exception e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
	assertEquals("Check MdoCallBackHandler properties size", 0, properties.size());
    }
    
    @SuppressWarnings("unchecked")
    private void checkHandle(Callback[] callbacks, Map<MdoCallBackType, Object> addedProperties) {
	String login = null;
	String password = null;
	AuthenticationPasswordLevel levelPassword = null;
	MdoCallBackHandler mdoCallBackHandler = new MdoCallBackHandler(login, password, levelPassword);
	Object propertiesField = super.getField(mdoCallBackHandler, "properties");
	Map<MdoCallBackType, Object> properties = (Map<MdoCallBackType, Object>) propertiesField;
	properties.putAll(addedProperties);
	try {
	    assertEquals("Check MdoCallBackHandler properties size", 3, properties.size());
	    mdoCallBackHandler.handle(callbacks);
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
	} catch (Exception e) {
	    assertTrue("Check mdoCallBackHandler.handle exception", e instanceof UnsupportedCallbackException);
	}
	assertEquals("Check MdoCallBackHandler properties size", 0, properties.size());
    }
}
