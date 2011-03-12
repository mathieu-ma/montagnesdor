package fr.mch.mdo.restaurant.services.logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.test.MdoTestCase;

/**
 * Coverage code is based on the loaded classes. So the Coverage code tools
 * takes into account the last loaded classes. The LoggerImpl uses the static
 * bloc for initialization. So we have to check this static code. In order to do
 * it, we have to reload the LoggerImpl class to check all possible
 * configurations. Because of LoggerImpl reloaded class(static initialization)
 * and because of technical coverage code issue, we have to create 2 types of
 * tests: 1) Test to really check all possible cases 2) Test to coverage code
 * 
 * @author Mathieu
 */
public class LoggerImplTest extends MdoTestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public LoggerImplTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(LoggerImplTest.class);
	}

	public void checkLoggerImpl(Class<?> loggerClass) {

		Class<?> clazz = loggerClass;
		if (clazz == null) {
			clazz = LoggerImpl.class;
		}
		// Constructor without argument
		ILogger logger = null;
		try {
			logger = (ILogger) clazz.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor without argument", logger);
		assertNull("Constructor without argument", logger.getLoggerMessage());

		// Constructor with class name argument
		try {
			logger = (ILogger) clazz.getConstructor(String.class).newInstance("");
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor with class name argument", logger);
		assertNull("Constructor with class name argument", logger.getLoggerMessage());

		// Constructor with ILoggerMessage argument
		IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(IResources.LOG_RESOURCE_BUNDLE_MESSAGES_FILE);
		try {
			logger = (ILogger) clazz.getConstructor(IMessageQuery.class).newInstance(loggerMessage);
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor with ILoggerMessage argument", logger);
		assertNotNull("Constructor with ILoggerMessage argument", logger.getLoggerMessage());

		// Constructor with ILoggerMessage argument and class name argument
		try {
			logger = (ILogger) clazz.getConstructor(IMessageQuery.class, String.class).newInstance(loggerMessage, "");
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor with ILoggerMessage argument and class name argument", logger);
		assertNotNull("Constructor with ILoggerMessage argument and class name argument", logger.getLoggerMessage());
	}

	public void checkSetGetLoggerMessage(Class<?> loggerClass) {
		Class<?> clazz = loggerClass;
		if (clazz == null) {
			clazz = LoggerImpl.class;
		}
		// Constructor without argument
		ILogger logger = null;
		try {
			logger = (ILogger) clazz.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor without argument", logger);
		assertNull("Constructor without argument", logger.getLoggerMessage());

		IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(IResources.LOG_RESOURCE_BUNDLE_MESSAGES_FILE);
		logger.setLoggerMessage(loggerMessage);
		assertNotNull("test setLoggerMessage", logger.getLoggerMessage());
	}

	public void checkGetLogger(Class<?> loggerClass) {
		Class<?> clazz = loggerClass;
		if (clazz == null) {
			clazz = LoggerImpl.class;
		}
		// Constructor without argument
		ILogger logger = null;
		try {
			logger = (ILogger) clazz.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor without argument", logger);
		assertNull("Constructor without argument", logger.getLoggerMessage());

		ILogger newLogger = logger.getLogger();
		assertNotNull("New Logger not null", newLogger);
		assertNull("New Logger message is null", newLogger.getLoggerMessage());

		IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(IResources.LOG_RESOURCE_BUNDLE_MESSAGES_FILE);
		logger.setLoggerMessage(loggerMessage);
		newLogger = logger.getLogger();
		assertNotNull("New Logger not null", newLogger);
		assertNotNull("New Logger message is not null", newLogger.getLoggerMessage());

		newLogger = logger.getLogger("");
		assertNotNull("New Logger not null", newLogger);
		assertNotNull("New Logger message is not null", newLogger.getLoggerMessage());

	}

	private Object invokeProcessQuery(final ILogger logger, final String query, final Object[] params) throws InvocationTargetException {
		// Purposely pass null values to the method, to make sure it throws
		// NullPointerException
		Class<?>[] argClasses = { String.class, Object[].class };
		Object[] argObjects = { query, params };
		if (params == null) {
			argClasses = new Class<?>[] { String.class };
			argObjects = new Object[] { query };
		}

		return invokeInstanceMethod(logger, "processQuery", argClasses, argObjects);
	}

	public void checkProcessQuery(Class<?> loggerClass) {
		String query = "query";
		String[] params = null;
		Class<?> clazz = loggerClass;
		if (clazz == null) {
			clazz = LoggerImpl.class;
		}
		// Constructor without argument
		ILogger logger = null;
		try {
			logger = (ILogger) clazz.getConstructor().newInstance();
		} catch (Exception e) {
			fail("Could not create new LoggerImpl instance");
		}
		assertNotNull("Constructor without argument", logger);
		assertNull("Constructor without argument", logger.getLoggerMessage());

		try {
			assertEquals("Invoke processQuery private method", query, invokeProcessQuery(logger, query, params));
			params = new String[] { " " };
			assertEquals("Invoke processQuery private method", query, invokeProcessQuery(logger, query, params));
		} catch (InvocationTargetException e) {
			fail("Could not invoke processQuery private method");
		}

		query = "my.key";
		String value = "Impossible de sauver l''objet {0} : {1}";
		Properties properties = new Properties();
		properties.setProperty(query, value);
		// Add new properties
		updatePropertiesFile(LoggerImplTest.class, properties, false);
		// Set ILoggerMessage
		IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(LoggerImplTest.class.getName());
		logger.setLoggerMessage(loggerMessage);
		assertNotNull("setLoggerMessage", logger.getLoggerMessage());
		try {
			assertEquals("Invoke processQuery private method", value.replaceFirst("'", ""), invokeProcessQuery(logger, query, null));
			params = new String[] { "a", "b" };
			assertEquals("Invoke processQuery private method", value.replaceFirst("'", "").replaceFirst("\\{0\\}", "a").replaceFirst("\\{1\\}", "b"), invokeProcessQuery(logger,
					query, params));
		} catch (InvocationTargetException e) {
			fail("Could not invoke processQuery private method");
		} finally {
			// Remove new properties
			updatePropertiesFile(LoggerImplTest.class, properties, true);
		}
	}

	private Object invokeReload(final String configFile) throws InvocationTargetException {
		// Purposely pass null values to the method, to make sure it throws
		// NullPointerException
		Class<?>[] argClasses = { String.class };
		Object[] argObjects = { configFile };
		return invokeStaticMethod(LoggerImpl.class, "reload", argClasses, argObjects);
	}

	public void checkReload(Class<?> reloadedClass) {
		// Change the log4j configuration file
		String backupExtension = ".backup";
		String propertiesExtension = ".properties";
		String propertiesFile = IResources.class.getPackage().getName().replace(".", "/") + "/" + IResources.LOG4J_CONFIGURATION_FILE;
		renamePropertiesFileExtension(propertiesFile, backupExtension);
		try {
			if (reloadedClass == null) {
				// Reload the class LoggerImpl.class in order to take into
				// account the
				// new configuration file
				Class<?> clazz = super.reloadClass(LoggerImpl.class);
				// Load the class and create a new instance
				clazz.getConstructor(String.class).newInstance(LoggerImplTest.class.getName());

			}
		} catch (Throwable e) {
			assertTrue("ExceptionInInitializerError when reloading the class LoggerImpl", e instanceof ExceptionInInitializerError);
			ExceptionInInitializerError nullPointerException = (ExceptionInInitializerError) e;
			assertTrue("ExceptionInInitializerError when reloading the class LoggerImpl", nullPointerException.getException() instanceof NullPointerException);
		} finally {
			// Rename back the log4j properties file
			propertiesFile = propertiesFile.replace(propertiesExtension, backupExtension);
			renamePropertiesFileExtension(propertiesFile, propertiesExtension);
		}
		try {
			invokeReload(null);
		} catch (InvocationTargetException e) {
			assertTrue("Could not invoke reload private method", e.getTargetException() instanceof NullPointerException);
		}
		// Check that the right properties file is loaded
		invokeLoggerLogByLogLevel(reloadedClass, "info", null, null);
	}

	private Class<?> invokeLoggerLogByLogLevel(Class<?> reloadedClass, String logLevelMethod, Object[] params, Throwable exception) {
		String propertiesFile = IResources.class.getPackage().getName().replace(".", "/") + "/" + IResources.LOG4J_CONFIGURATION_FILE;
		// Check that the right properties file is loaded
		File file = super.getFileFromClassPath(IResources.class, "logs/log4j/log4j.log");
		Properties properties = updateLog4jPropertiesFile(file.getAbsolutePath(), logLevelMethod.toUpperCase());
		// Update the configuration file and backup
		properties = super.updatePropertiesFile(propertiesFile, properties, false);
		Class<?> clazz = reloadedClass;
		// This will instantiate the LoggerImpl class in this class loader
		ILogger logger = new LoggerImpl(LoggerImplTest.class.getName());
		try {
			if (clazz == null) {
				// Reload the class in order to take into account the new
				// properties
				// above for static fields or blocks
				clazz = reloadClass(LoggerImpl.class);
			}
			// Instantiate the loaded class, this will initialize the log4j
			// configuration for this JVM
			logger = (ILogger) clazz.getConstructor(String.class).newInstance(LoggerImplTest.class.getName());
		} catch (Exception e) {
			fail("Could not instantiate the class LoggerImpl");
		}
		assertNotNull("LoggerImpl instance must not be null", logger);
		if (reloadedClass == null) {
			assertTrue("The log file " + file.getAbsolutePath() + " must be empty ", file.length() == 0);
		}

		String message = "Hello World " + logLevelMethod;
		// Log the info message
		Class<?>[] argClasses = { String.class };
		Object[] argObjects = { message };
		if (params == null && exception != null) {
			argClasses = new Class<?>[] { String.class, Throwable.class };
			message += " " + exception.getMessage();
			argObjects = new Object[] { message, exception };
		} else if (params != null && exception == null) {
			argClasses = new Class<?>[] { String.class, Object[].class };
			message += " " + Arrays.toString(params);
			argObjects = new Object[] { message, params };
		} else if (params != null && exception != null) {
			argClasses = new Class<?>[] { String.class, Object[].class, Throwable.class };
			message += " " + Arrays.toString(params) + " " + exception.getMessage();
			argObjects = new Object[] { message, params, exception };
		}

		try {
			invokeInstanceMethod(logger, logLevelMethod, argClasses, argObjects);
		} catch (InvocationTargetException e) {
			fail("Could not invoke the logger method " + logLevelMethod + ": " + e.getTargetException());
		}

		if (reloadedClass == null) {
			assertTrue("The log file " + file.getAbsolutePath() + " must not be empty", file.length() > 0);
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line = bufferedReader.readLine();
				assertNotNull("There is at least 1 line in the file", line);
				assertTrue("The line must contain the message", line.contains(message));
				if (exception == null) {
					assertNull("There is only 1 line in the file", bufferedReader.readLine());
				}
			} catch (Exception e) {
				fail("Could not open the logger file: " + e);
			} finally {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					fail("Could not close the logger file: " + e);
				}
			}
		}
		// Roll back old values
		super.updatePropertiesFile(propertiesFile, properties, false);

		return logger.getClass();
	}

	private Properties updateLog4jPropertiesFile(String logFilePath, String logLevel) {

		Properties result = new Properties();
		// Modify the file path in the properties files
		String key = "log4j.appender.A2.File";
		String value = logFilePath;
		result.put(key, value);
		// The log file must be empty before to write in it
		key = "log4j.appender.A2.Append";
		value = "false";
		result.put(key, value);
		// Logger for LoggerImplTest class must be the logLevel argument
		key = "log4j.logger.fr.mch.mdo.restaurant.services.logs.LoggerImplTest";
		value = logLevel;
		result.put(key, value);

		return result;
	}

	public Class<?> checkDebug(Class<?> reloadedClass) {
		Class<?> result = reloadedClass;
		// Test debug with 1 parameter
		result = invokeLoggerLogByLogLevel(reloadedClass, "debug", null, null);
		// Test debug with 2 parameters without Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "debug", new String[] { "1" }, null);
		// Test debug with 2 parameters with Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "debug", null, new NullPointerException("Logger Test in debug method"));
		// Test debug with 3 parameters
		result = invokeLoggerLogByLogLevel(reloadedClass, "debug", new String[] { "1" }, new NullPointerException("Logger Test in debug method"));
		return result;
	}

	public Class<?> checkInfo(Class<?> reloadedClass) {
		Class<?> result = reloadedClass;
		// Test info with 1 parameter
		result = invokeLoggerLogByLogLevel(reloadedClass, "info", null, null);
		// Test info with 2 parameters without Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "info", new String[] { "1" }, null);
		// Test info with 2 parameters with Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "info", null, new ClassCastException("Logger Test in info method"));
		// Test info with 3 parameters
		result = invokeLoggerLogByLogLevel(reloadedClass, "info", new String[] { "1" }, new ClassCastException("Logger Test in info method"));
		return result;
	}

	public Class<?> checkWarn(Class<?> reloadedClass) {
		Class<?> result = reloadedClass;
		// Test warn with 1 parameter
		result = invokeLoggerLogByLogLevel(reloadedClass, "warn", null, null);
		// Test warn with 2 parameters without Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "warn", new String[] { "1" }, null);
		// Test warn with 2 parameters with Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "warn", null, new NoClassDefFoundError("Logger Test in warn method"));
		// Test warn with 3 parameters
		result = invokeLoggerLogByLogLevel(reloadedClass, "warn", new String[] { "1" }, new NoClassDefFoundError("Logger Test in warn method"));
		return result;
	}

	public Class<?> checkError(Class<?> reloadedClass) {
		Class<?> result = reloadedClass;
		// Test error with 1 parameter
		result = invokeLoggerLogByLogLevel(reloadedClass, "error", null, null);
		// Test error with 2 parameters without Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "error", new String[] { "1" }, null);
		// Test error with 2 parameters with Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "error", null, new ExceptionInInitializerError("Logger Test in error method"));
		// Test error with 3 parameters
		result = invokeLoggerLogByLogLevel(reloadedClass, "error", new String[] { "1" }, new ExceptionInInitializerError("Logger Test in error method"));
		return result;
	}

	public Class<?> checkFatal(Class<?> reloadedClass) {
		Class<?> result = reloadedClass;
		// Test fatal with 1 parameter
		result = invokeLoggerLogByLogLevel(reloadedClass, "fatal", null, null);
		// Test fatal with 2 parameters without Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "fatal", new String[] { "1" }, null);
		// Test fatal with 2 parameters with Exception
		result = invokeLoggerLogByLogLevel(reloadedClass, "fatal", null, new NumberFormatException("Logger Test in fatal method"));
		// Test fatal with 3 parameters
		result = invokeLoggerLogByLogLevel(reloadedClass, "fatal", new String[] { "1" }, new NumberFormatException("Logger Test in fatal method"));
		return result;
	}

	/**
	 * Coverage code is based on the loaded classes. So the Coverage code tools
	 * takes into account the last loaded classes. The LoggerImpl uses the
	 * static bloc for initialization. So we have to check this static code. In
	 * order to do it, we have to reload the LoggerImpl class to check all
	 * possible configurations. Because of LoggerImpl reloaded class(static
	 * initialization) and because of technical coverage code issue, we have to
	 * create 2 types of tests: 1) Test to really check all possible cases 2)
	 * Test to coverage code
	 */
	public void testAll() {

		// 1) Test to really check all possible cases
		checkLoggerImpl(null);
		checkSetGetLoggerMessage(null);
		checkGetLogger(null);
		checkProcessQuery(null);
		checkReload(null);
		checkFatal(null);
		checkError(null);
		checkWarn(null);
		checkInfo(null);
		checkDebug(null);

		// 2) Test to coverage code
		// checkLoggerImpl(loggerClass);
		// checkSetGetLoggerMessage(loggerClass);
		// checkGetLogger(loggerClass);
		// checkProcessQuery(loggerClass);
		// checkReload(loggerClass);
		// checkDebug(loggerClass);
		// checkInfo(loggerClass);
		// checkWarn(loggerClass);
		// checkError(loggerClass);
		// checkFatal(loggerClass);
	}
}