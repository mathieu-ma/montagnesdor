package fr.mch.mdo.restaurant.services.business.utils;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrdersUtils implements IOrdersUtils 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IOrdersUtils instance = new DefaultOrdersUtils(
				LoggerServiceImpl.getInstance().getLogger(DefaultOrdersUtils.class.getName()));
	}

	private DefaultOrdersUtils(ILogger logger) {
		this.setLogger(logger);
	}

	public static IOrdersUtils getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the logger
	 */
	public ILogger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

}
