package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.logs.LoggerImpl;
import fr.mch.mdo.test.MdoTestCase;

public class MdoCommonExceptionTest extends MdoTestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoCommonExceptionTest(String testName) {
	super(testName);
    }

    protected void checkMdoMdoExceptionConstrutors(Class<? extends MdoException> clazz, Locale locale) {

	IMessageQuery messageQuery = new MessageQueryResourceBundleImpl(IResources.EXCEPTION_RESOURCE_BUNDLE_MESSAGES_FILE, locale);

	String message = null;
	String localizedMessage = messageQuery.getMessage(message);
	Object[] params = null;
	Throwable throwable = null;
	try {
	    throw clazz.getConstructor().newInstance();
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params);
	}

	throwable = new NullPointerException();
	params = null;
	message = "java.lang.NullPointerException";
	localizedMessage = messageQuery.getMessage(message);
	try {
	    throw clazz.getConstructor(Throwable.class).newInstance(throwable);
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}

	throwable = null;
	params = null;
	message = null;
	localizedMessage = messageQuery.getMessage(message);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class).newInstance(locale, message);
	    } else {
		throw clazz.getConstructor(String.class).newInstance(message);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message);
	}

	throwable = null;
	params = null;
	message = "message.error.dinner.tablesX";
	localizedMessage = messageQuery.getMessage(message);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class).newInstance(locale, message);
	    } else {
		throw clazz.getConstructor(String.class).newInstance(message);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message);
	}

	throwable = null;
	params = null;
	message = "message.error.dinner.tables";
	localizedMessage = messageQuery.getMessage(message);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class).newInstance(locale, message);
	    } else {
		throw clazz.getConstructor(String.class).newInstance(message);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}

	throwable = null;
	params = null;
	message = "message.error.product";
	localizedMessage = messageQuery.getMessage(message);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class).newInstance(locale, message);
	    } else {
		throw clazz.getConstructor(String.class).newInstance(message);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}

	throwable = null;
	params = new Object[] { "11" };
	message = "message.error.product";
	localizedMessage = messageQuery.getMessage(message, params);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class, Object[].class).newInstance(locale, message, params);
	    } else {
		throw clazz.getConstructor(String.class, Object[].class).newInstance(message, params);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}

	throwable = new NullPointerException();
	params = null;
	message = "message.error.product";
	localizedMessage = messageQuery.getMessage(message);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class, Throwable.class).newInstance(locale, message, throwable);
	    } else {
		throw clazz.getConstructor(String.class, Throwable.class).newInstance(message, throwable);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}

	throwable = new NullPointerException();
	params = new Object[] { "11" };
	message = "message.error.product";
	localizedMessage = messageQuery.getMessage(message, params);
	try {
	    if (locale != null) {
		throw clazz.getConstructor(Locale.class, String.class, Object[].class, Throwable.class).newInstance(locale, message, params, throwable);
	    } else {
		throw clazz.getConstructor(String.class, Object[].class, Throwable.class).newInstance(message, params, throwable);
	    }
	} catch (Exception e) {
	    // Check all fields private, protected and public
	    checkFields(e, throwable, params, message, localizedMessage);
	}
    }
    
    private void checkFields(Exception e, Throwable expectedParentException, Object[] expectedParams, String... messages) {
	// Check all fields private, protected and public
	assertTrue("The exception must be MdoMessageManagerException", e instanceof MdoMessageManagerException);
	assertEquals("Check parent exception", expectedParentException, e.getCause());
	
	String message = messages.length == 0 ? null: messages[0];
	String localizedMessage = messages.length == 2 ? messages[1]: messages.length == 1 ? messages[0]: null;
	if (message == null) {
	    assertNull("The message must be null", e.getMessage());
	    assertNull("The localized message must be null", e.getLocalizedMessage());
	} else {
	    assertEquals("The message must be " + message, message, e.getMessage());
	    assertEquals("The localized message must be " + localizedMessage, localizedMessage, e.getLocalizedMessage());
	}
	Object[] params = (Object[]) super.getField(e, "params");
	if (params != null) {
	    assertTrue("The params must be an array", params.getClass().isArray());
	    assertEquals("Check params array length", expectedParams.length, params.length);
	    for (int i=0; i<expectedParams.length; i++) {
		assertEquals("Check param " + expectedParams[i], expectedParams[i], params[i]);
	    }
	} else {
	    assertNull("The params must be null", params);
	}
	Object messageQuery = super.getField(e, "messageQuery");
	assertNotNull("The messageQuery must be not null", messageQuery);
	assertTrue("The messageQuery is an instance of IMessageQuery", messageQuery instanceof IMessageQuery);
	assertTrue("The messageQuery is an instance of MessageQueryResourceBundleImpl", messageQuery instanceof MessageQueryResourceBundleImpl);
	Object logger = super.getField(e, "logger");
	assertNotNull("The logger must be not null", logger);
	assertTrue("The logger is an instance of ILogger", logger instanceof ILogger);
	assertTrue("The logger is an instance of LoggerImpl", logger instanceof LoggerImpl);
    }
    
    public void testDummy() {
	// This is a dummy test.
	// Because PMD needs that there is at least one test in a TestCase.
	assertTrue(true);
    }
}
