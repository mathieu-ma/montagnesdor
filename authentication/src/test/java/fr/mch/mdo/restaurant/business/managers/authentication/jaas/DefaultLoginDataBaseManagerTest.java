package fr.mch.mdo.restaurant.business.managers.authentication.jaas;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.authentication.MdoCallBackType;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.UserAuthenticationJaas;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.users.ILoginUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.authentication.jaas.MdoCallBackHandler;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class DefaultLoginDataBaseManagerTest extends MdoTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DefaultLoginDataBaseManagerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DefaultLoginDataBaseManagerTest.class);
    }

    public void testConstructor() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	Object logger = super.getField(defaultLoginDataBaseManager, "logger");
	assertNotNull("Check private field logger", logger);
	assertTrue("Check private field logger instance", logger instanceof ILogger);
	Object dao = super.getField(defaultLoginDataBaseManager, "dao");
	assertNotNull("Check private field dao", dao);
	assertTrue("Check private field dao instance", dao instanceof IUserAuthenticationsDao);
    }

    @SuppressWarnings("unchecked")
    private void initialize(DefaultLoginDataBaseManager defaultLoginDataBaseManager, Subject subject,
	    String login, String password, AuthenticationPasswordLevel levelPassword ) {

	Object subjectField = super.getField(defaultLoginDataBaseManager, "subject");
	assertNull("Check private field subject", subjectField);
	Object callbackHandlerField = super.getField(defaultLoginDataBaseManager, "callbackHandler");
	assertNull("Check private field callbackHandler", callbackHandlerField);
	Object sharedStateField = super.getField(defaultLoginDataBaseManager, "sharedState");
	assertNull("Check private field sharedState", sharedStateField);
	Object optionsField = super.getField(defaultLoginDataBaseManager, "options");
	assertNull("Check private field options", optionsField);
	MdoCallBackHandler callbackHandler = new MdoCallBackHandler(login, password, levelPassword);
	Map<String, String> sharedState = new HashMap<String, String>();
	sharedState.put(login, login);
	Map<String, Object> options = new HashMap<String, Object>();
	options.put(login, levelPassword);
	options.put(login+password, password);
	defaultLoginDataBaseManager.initialize(subject, callbackHandler, sharedState, options);

	if (subject != null) {
	    subjectField = super.getField(defaultLoginDataBaseManager, "subject");
	    assertNotNull("Second Check private field subject after calling initialize method", subjectField);
	    assertEquals("Second Check private field subject after calling initialize method", subject, subjectField);
	}
	
	callbackHandlerField = super.getField(defaultLoginDataBaseManager, "callbackHandler");
	assertNotNull("Second Check private field callbackHandler after calling initialize method", callbackHandlerField);
	assertTrue("Second Check private field callbackHandler instance after calling initialize method", callbackHandlerField instanceof CallbackHandler);
	assertTrue("Second Check private field callbackHandler instance after calling initialize method", callbackHandlerField instanceof MdoCallBackHandler);
	Object properties = super.getField(callbackHandlerField, "properties");
	assertTrue("Second Check private field properties instance after calling initialize method", properties instanceof Map<?, ?>);
	Map<MdoCallBackType, Object> callbackHandlerFieldProperties = (Map<MdoCallBackType, Object>) properties;
	assertEquals("Check private field login MdoCallBackHandler", login, callbackHandlerFieldProperties.get(MdoCallBackType.MDO_CALLBACK_NAME));
	assertEquals("Check private field password MdoCallBackHandler", password, callbackHandlerFieldProperties.get(MdoCallBackType.MDO_CALLBACK_PASSWORD));
	Object levelPasswordField = callbackHandlerFieldProperties.get(MdoCallBackType.MDO_CALLBACK_LEVEL_PASSWORD);
	assertTrue("Check private field levelPasswordField instance", levelPasswordField instanceof AuthenticationPasswordLevel);
	assertEquals("Check private field password levelPassword", levelPassword, levelPasswordField);
	
	sharedStateField = super.getField(defaultLoginDataBaseManager, "sharedState");
	assertNotNull("Second Check private field sharedState after calling initialize method", sharedStateField);
	assertTrue("Second Check private field sharedState instance after calling initialize method", sharedStateField instanceof Map<?, ?>);
	assertEquals("Second Check private field sharedState size after calling initialize method", 1, ((Map<?, ?>) sharedStateField).size());
	
	optionsField = super.getField(defaultLoginDataBaseManager, "options");
	assertNotNull("Second Check private field options after calling initialize method", optionsField);
	assertTrue("Second Check private field options instance after calling initialize method", optionsField instanceof Map<?, ?>);
	assertEquals("Second Check private field options size after calling initialize method", 2, ((Map<?, ?>) optionsField).size());
    }
    
    public void testInitialize() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	String login = "login";
	String password = "password";
	AuthenticationPasswordLevel levelPassword = AuthenticationPasswordLevel.PASSWORD_LEVEL_ZERO;
	initialize(defaultLoginDataBaseManager, new Subject(), login, password, levelPassword);
    }

    private ILoginUserAuthenticationsDao dao = new ILoginUserAuthenticationsDao() {
        private UserAuthenticationJaas userAuthentication = new UserAuthenticationJaas();
        @Override
        public IMdoBean findByLogin(String login) throws MdoException {
            if (userAuthentication != null) {
        	userAuthentication.setLogin(login);
            }
            return userAuthentication;
        }
    };

    public void testLogin() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	// callbackHandler is null
	try {
	    assertFalse("Check login callbackHandler null", defaultLoginDataBaseManager.login());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
	String login = null;
	String password = null;
	AuthenticationPasswordLevel levelPassword = null;
	for (int i = 0; i < 3; i++) {
	    super.setField(defaultLoginDataBaseManager, "callbackHandler", new MdoCallBackHandler(login, password, levelPassword));
	    // callbackHandler.handle throw exception because the login, password or levelPassword are null
	    try {
		super.setField(defaultLoginDataBaseManager, "dao", null);
		assertFalse("callbackHandler.handle throw exception because the login, password or levelPassword are null", defaultLoginDataBaseManager.login());
	    } catch (LoginException e) {
		fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	    }
	    switch (i) {
	    	case 0:
	    	    login = "login";
	    	    break;
	    	case 1:
	    	    password = "password";
	    	    break;
	    	case 2:
	    	    levelPassword = AuthenticationPasswordLevel.PASSWORD_LEVEL_ZERO;
	    	    break;
	    }
	}

	// dao is null
	try {
	    super.setField(defaultLoginDataBaseManager, "dao", null);
	    super.setField(defaultLoginDataBaseManager, "callbackHandler", new MdoCallBackHandler(login, password, levelPassword));
	    assertFalse("Check login dao null", defaultLoginDataBaseManager.login());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}

	// userAuthentication is null
	try {
	    super.setField(dao, "userAuthentication", null);
	    super.setField(defaultLoginDataBaseManager, "dao", dao);
	    super.setField(defaultLoginDataBaseManager, "callbackHandler", new MdoCallBackHandler(login, password, levelPassword));
	    assertFalse("Check login userAuthentication null", defaultLoginDataBaseManager.login());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}

	// current userAuthentication password(==null) is different to the callback password
	UserAuthenticationJaas userAuthentication = new UserAuthenticationJaas();
	try {
	    super.setField(dao, "userAuthentication", userAuthentication);
	    super.setField(defaultLoginDataBaseManager, "callbackHandler", new MdoCallBackHandler(login, password, levelPassword));
	    assertFalse("Check login current userAuthentication password(==null) is different to the callback password", defaultLoginDataBaseManager.login());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}

	// current userAuthentication password(==null) is equals to the callback password
	userAuthentication.setPassword(password);
	try {
	    super.setField(defaultLoginDataBaseManager, "callbackHandler", new MdoCallBackHandler(login, password, levelPassword));
	    assertTrue("Check login current userAuthentication password(==null) is equals to the callback password", defaultLoginDataBaseManager.login());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}

    }

    public void testCommit() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	UserAuthenticationJaas userAuthentication = null;
	Subject subject = null;
	try {
	    // It's because userAuthentication is null
	    assertFalse("Check returned method of commit", defaultLoginDataBaseManager.commit());
	    userAuthentication = (UserAuthenticationJaas) super.getField(defaultLoginDataBaseManager, "userAuthentication");
	    assertNull("Check private field userAuthentication", userAuthentication);
	    subject = (Subject) super.getField(defaultLoginDataBaseManager, "subject");
	    assertNull("Check private field subject", subject);

	    // It's because userAuthentication.getUserRole()
	    userAuthentication = new UserAuthenticationJaas();
	    super.setField(defaultLoginDataBaseManager, "userAuthentication", userAuthentication);
	    assertFalse("Check returned method of commit", defaultLoginDataBaseManager.commit());

	    // It's because userAuthentication.getUserRole().getCode()
	    userAuthentication.setUserRole(new UserRole());
	    assertFalse("Check returned method of commit", defaultLoginDataBaseManager.commit());

	    // It's because userAuthentication.getUserRole().getCode().getName() is null
	    userAuthentication.getUserRole().setCode(new MdoTableAsEnum());
	    assertFalse("Check returned method of commit", defaultLoginDataBaseManager.commit());

	    // It's because subject is null
	    userAuthentication.getUserRole().getCode().setName("name");
	    assertFalse("Check returned method of commit", defaultLoginDataBaseManager.commit());

	    // It's because subject is NOT null
	    subject = new Subject();
	    super.setField(defaultLoginDataBaseManager, "subject", subject);
	    assertTrue("Check returned method of commit", defaultLoginDataBaseManager.commit());
	    assertEquals("Check private field subject principals size", 1, ((Subject) subject).getPrincipals().size());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
    }

    public void testAbort() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	try {
	    assertTrue("Check returned method of abort", defaultLoginDataBaseManager.abort());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
    }

    public void testLogout() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	try {
	    assertTrue("Check returned method of logout", defaultLoginDataBaseManager.logout());
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
    }

    public void testNominalUseCase() {
	DefaultLoginDataBaseManager defaultLoginDataBaseManager = new DefaultLoginDataBaseManager();
	UserAuthenticationJaas userAuthentication = new UserAuthenticationJaas();
	super.setField(dao, "userAuthentication", userAuthentication);
	String login = "mch";
	String password = "mch";
	userAuthentication.setLogin(login);
	userAuthentication.setPassword(login);
	AuthenticationPasswordLevel levelPassword = AuthenticationPasswordLevel.PASSWORD_LEVEL_ZERO;
	// Do not use the real connection to the database.
	super.setField(defaultLoginDataBaseManager, "dao", dao);
	try {
	    initialize(defaultLoginDataBaseManager, new Subject(), login, password, levelPassword);
	    defaultLoginDataBaseManager.login();
	    defaultLoginDataBaseManager.commit();
	} catch (LoginException e) {
	    fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
	}
    }
}
