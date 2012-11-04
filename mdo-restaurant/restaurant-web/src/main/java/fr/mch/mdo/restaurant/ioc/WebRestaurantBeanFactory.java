package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;

/**
 * This class is useful if you do not use spring MVC but only spring core. 
 *  
 * @author mathieu
 *
 */
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
	public IOrdersManager getOrdersManager() {
		return ((IOrdersManager) this.getBean(IocBeanName.BEAN_ORDERS_MANAGER_NAME));
	}
}
