package fr.mch.mdo.restaurant.services.logs;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.resources.IResources;

public class LoggerImpl implements ILogger {
	private Logger myLog4j;
	private IMessageQuery loggerMessage;

	static {
		try {
			reload(IResources.LOG4J_CONFIGURATION_FILE);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private static void reload(final String configFile) throws Exception {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = IResources.class.getResourceAsStream(configFile);
			properties.load(inputStream);
			PropertyConfigurator.configure(properties);
		} finally {
			inputStream.close();
		}
	}

	public LoggerImpl(IMessageQuery loggerMessage) {
		this.loggerMessage = loggerMessage;
		this.myLog4j = Logger.getRootLogger();
	}

	public LoggerImpl(IMessageQuery loggerMessage, String className) {
		this.loggerMessage = loggerMessage;
		this.myLog4j = Logger.getLogger(className);
	}

	public LoggerImpl() {
		this.myLog4j = Logger.getRootLogger();
	}

	public LoggerImpl(String className) {
		this.myLog4j = Logger.getLogger(className);
	}

	public IMessageQuery getLoggerMessage() {
		return loggerMessage;
	}

	public void setLoggerMessage(IMessageQuery loggerMessage) {
		this.loggerMessage = loggerMessage;
	}

	public ILogger getLogger() {
		return new LoggerImpl(loggerMessage);
	}

	public ILogger getLogger(String className) {
		return new LoggerImpl(loggerMessage, className);
	}

	public void debug(String query, Throwable exception) {
		myLog4j.debug(processQuery(query), exception);
	}

	public void debug(String query) {
		myLog4j.debug(processQuery(query));
	}

	public void info(String query, Throwable exception) {
		myLog4j.info(processQuery(query), exception);
	}

	public void info(String query) {
		myLog4j.info(processQuery(query));
	}

	public void warn(String query, Throwable exception) {
		myLog4j.warn(processQuery(query), exception);
	}

	public void warn(String query) {
		myLog4j.warn(processQuery(query));
	}

	public void error(String query, Throwable exception) {
		myLog4j.error(processQuery(query), exception);
	}

	public void error(String query) {
		myLog4j.error(processQuery(query));
	}

	public void fatal(String query, Throwable exception) {
		myLog4j.fatal(processQuery(query), exception);
	}

	public void fatal(String query) {
		myLog4j.fatal(processQuery(query));
	}

	public void debug(String query, Object[] params, Throwable exception) {
		myLog4j.debug(processQuery(query, params), exception);
	}

	public void debug(String query, Object[] params) {
		myLog4j.debug(processQuery(query, params));
	}

	public void info(String query, Object[] params, Throwable exception) {
		myLog4j.info(processQuery(query, params), exception);
	}

	public void info(String query, Object[] params) {
		myLog4j.info(processQuery(query, params));
	}

	public void warn(String query, Object[] params, Throwable exception) {
		myLog4j.warn(processQuery(query, params), exception);
	}

	public void warn(String query, Object[] params) {
		myLog4j.warn(processQuery(query, params));
	}

	public void error(String query, Object[] params, Throwable exception) {
		myLog4j.error(processQuery(query, params), exception);
	}

	public void error(String query, Object[] params) {
		myLog4j.error(processQuery(query, params));
	}

	public void fatal(String query, Object[] params, Throwable exception) {
		myLog4j.fatal(processQuery(query, params), exception);
	}

	public void fatal(final String query, final Object[] params) {
		myLog4j.fatal(processQuery(query, params));
	}

	private String processQuery(final String query) {
		return processQuery(query, null);
	}

	private String processQuery(final String query, final Object[] params) {
		String message = query;
		if (loggerMessage != null) {
			message = loggerMessage.getMessage(message, params);
		}
		return message;
	}
}