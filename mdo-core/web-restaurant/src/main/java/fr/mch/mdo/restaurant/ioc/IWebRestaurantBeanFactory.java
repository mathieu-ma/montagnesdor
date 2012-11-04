package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.restaurant.services.business.managers.IOrdersManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;


public interface IWebRestaurantBeanFactory extends IMdoBeanFactory
{
	IUserAuthenticationsManager getUserAuthenticationsManager();
	IOrdersManager getOrdersManager();
}
