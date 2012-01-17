package fr.mch.mdo.restaurant.web.admin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;

public class MdoGlobalAdminListener implements ServletContextListener
{
	/**
	 * A listener class must have a zero-argument constructor
	 **/
	public MdoGlobalAdminListener() {
		// Initialize the WebAdministrationBeanFactory in order to create user GLOBAL_ADMINISTRATOR 
		WebAdministrationBeanFactory.getInstance();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
