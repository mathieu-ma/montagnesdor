package fr.mch.mdo.restaurant.services.logs;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.test.MdoTestCase;

public class LoggerServiceImplTest extends MdoTestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public LoggerServiceImplTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(LoggerServiceImplTest.class);
    }

    public void testSetGetLogger()
    {
	// Get instance
	assertNotNull("LoggerServiceImpl.getInstance() must return not null object", LoggerServiceImpl.getInstance());
	assertTrue("LoggerServiceImpl.getInstance() must return a ILoggerService instance", LoggerServiceImpl.getInstance() instanceof ILoggerService);
	assertTrue("LoggerServiceImpl.getInstance() must return a LoggerServiceImpl instance", LoggerServiceImpl.getInstance() instanceof LoggerServiceImpl);
	// Get logger
	assertNotNull("LoggerServiceImpl.getInstance().getLogger() must return not null object", LoggerServiceImpl.getInstance().getLogger());
	assertTrue("LoggerServiceImpl.getInstance().getLogger() must return a ILogger instance", LoggerServiceImpl.getInstance().getLogger() instanceof ILogger);
	assertTrue("LoggerServiceImpl.getInstance().getLogger() must return a LoggerImpl instance", LoggerServiceImpl.getInstance().getLogger() instanceof LoggerImpl);
	assertNotNull("LoggerServiceImpl.getInstance().getLogger(toto) must return not null object", LoggerServiceImpl.getInstance().getLogger("toto"));
	assertTrue("LoggerServiceImpl.getInstance().getLogger(toto) must return a ILogger instance", LoggerServiceImpl.getInstance().getLogger("toto") instanceof ILogger);
	assertTrue("LoggerServiceImpl.getInstance().getLogger(too) must return a LoggerImpl instance", LoggerServiceImpl.getInstance().getLogger("toto") instanceof LoggerImpl);
	try {
	    LoggerServiceImpl.getInstance().getLogger(null);
	    fail("None instruction could be performed here");
	} catch(Exception e) {
	    assertTrue(e instanceof NullPointerException);
	}
	// Set logger
	ILogger logger = null;
	LoggerServiceImpl.getInstance().setLogger(logger);
	try {
	    LoggerServiceImpl.getInstance().getLogger();
	    fail("None instruction could be performed here");
	} catch(Exception e) {
	    assertTrue(e instanceof NullPointerException);
	}
	IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(IResources.LOG_RESOURCE_BUNDLE_MESSAGES_FILE);
	logger = new LoggerImpl(loggerMessage);
	LoggerServiceImpl.getInstance().setLogger(logger);
	assertNotNull("LoggerServiceImpl.getInstance().getLogger() must return not null object", LoggerServiceImpl.getInstance().getLogger());
	assertTrue("LoggerServiceImpl.getInstance().getLogger() must return a ILogger instance", LoggerServiceImpl.getInstance().getLogger() instanceof ILogger);
	assertTrue("LoggerServiceImpl.getInstance().getLogger() must return a LoggerImpl instance", LoggerServiceImpl.getInstance().getLogger() instanceof LoggerImpl);
    }
}
