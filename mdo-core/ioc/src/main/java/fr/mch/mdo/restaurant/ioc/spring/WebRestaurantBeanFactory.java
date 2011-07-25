package fr.mch.mdo.restaurant.ioc.spring;

import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.ioc.IWebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.tables.IDinnerTablesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;

public class WebRestaurantBeanFactory extends MdoBeanFactory implements IWebRestaurantBeanFactory 
{
	private static class LazyHolder {
		private static IWebRestaurantBeanFactory instance = new WebRestaurantBeanFactory();
	}
	public static IWebRestaurantBeanFactory getInstance() {
		return LazyHolder.instance;
	}

	private WebRestaurantBeanFactory() {
		// Load IOC file configuration
		super();
	}
	
	@Override
	public IUserAuthenticationsManager getUserAuthenticationsManager() {
		return ((IUserAuthenticationsManager) this.getBean(IocBeanName.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME));
	}

	@Override
	public IDinnerTablesManager getDinnerTablesManager() {
		return ((IDinnerTablesManager) this.getBean(IocBeanName.BEAN_TABLE_ORDERS_MANAGER_NAME));
	}
}
