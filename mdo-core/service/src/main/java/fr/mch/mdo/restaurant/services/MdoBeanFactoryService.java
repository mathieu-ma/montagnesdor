package fr.mch.mdo.restaurant.services;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.ioc.IBeanFactoryService;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class MdoBeanFactoryService 
{
	public static final String MDO_BEAN_FACTORY_CLASS_KEY = "mdo.bean.factory.class";
	public static final String MDO_BEAN_FACTORY_CLASS = "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceDefault";

	/**
	 * This class is only used for Singleton lazy initialization
	 * 
	 * @author Mathieu
	 * 
	 */
	private static class InitializeOnDemandHolder {
		/** Singleton */
		private static IBeanFactoryService instance;
		/** logger */
		private static ILogger innerLogger = LoggerServiceImpl.getInstance().getLogger(MdoBeanFactoryService.InitializeOnDemandHolder.class.getName());

		static {
			String clazzName = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS;
			ResourceBundle resources = null;
			try {
				resources = ResourceBundle.getBundle(MdoBeanFactoryService.class.getName());
			} catch (Exception e) {
				innerLogger.warn("Could not find the resource class " + MdoBeanFactoryService.class.getName(), e);
			}
			if (resources != null) {
				try {
					clazzName = resources.getString(MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY);
				} catch (MissingResourceException e) {
					innerLogger.warn("Could not find the resource class name with key " + MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY, e);
				}
			}
			Class<?> clazz = null;
			try {
				clazz = Class.forName(clazzName);
			} catch (ClassNotFoundException e) {
				innerLogger.fatal("Could not find the class " + clazzName, e);
				throw new ExceptionInInitializerError(e);
			} catch (Exception e) {
				innerLogger.fatal("Could not find the class " + clazzName, e);
				throw new ExceptionInInitializerError(e);
			}
			try {
				innerLogger.info("Class to be instantiated " + clazzName);
				instance = (IBeanFactoryService) clazz.newInstance();
				innerLogger.info(clazzName + " class is instantiated");
			} catch (InstantiationException e) {
				innerLogger.fatal("Could not instantiate the IMdoBeanFactory", e);
				throw new ExceptionInInitializerError(e);
			} catch (IllegalAccessException e) {
				innerLogger.fatal("Could not access the IMdoBeanFactory", e);
				throw new ExceptionInInitializerError(e);
			} catch (Exception e) {
				innerLogger.fatal("Could not get the instance of IMdoBeanFactory", e);
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	public static IBeanFactoryService getInstance() {
		return InitializeOnDemandHolder.instance;
	}
}
