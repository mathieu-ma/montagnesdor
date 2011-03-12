package fr.mch.mdo.restaurant.services.logs;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.resources.IResources;

public class LoggerServiceImpl implements ILoggerService 
{
	private ILogger logger;

	/**
	 * This class is only used for Singleton lazy initialization
	 */
	private static class InitializeOnDemandHolder {
		private static ILoggerService instance;
		static {
			final IMessageQuery loggerMessage = new MessageQueryResourceBundleImpl(IResources.LOG_RESOURCE_BUNDLE_MESSAGES_FILE);
			final ILogger logger = new LoggerImpl(loggerMessage);
			instance = new LoggerServiceImpl(logger);
		}
	}

	/**
	 * This method is used to give an Singleton instance. This method can be
	 * used for testing but normally we must use Spring IOC
	 * 
	 * @return the singleton
	 */
	public static ILoggerService getInstance() {
		return InitializeOnDemandHolder.instance;
	}

	/**
	 * Constructor needed for Spring IOC
	 */
	public LoggerServiceImpl() {
	}

	public LoggerServiceImpl(final ILogger logger) {
		this.logger = logger;
	}

	public ILogger getLogger(final String className) {
		return logger.getLogger(className);
	}

	public ILogger getLogger() {
		return logger.getLogger();
	}

	public void setLogger(final ILogger logger) {
		this.logger = logger;
	}
}
