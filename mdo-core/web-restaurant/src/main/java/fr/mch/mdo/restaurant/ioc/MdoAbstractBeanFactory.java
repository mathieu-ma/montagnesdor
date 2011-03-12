package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.business.managers.authentication.IAuthenticationManager;
import fr.mch.mdo.restaurant.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.business.managers.products.ICategoriesManager;
import fr.mch.mdo.restaurant.business.managers.products.IProductPartsManager;
import fr.mch.mdo.restaurant.business.managers.products.IProductSpecialCodesManager;
import fr.mch.mdo.restaurant.business.managers.products.IProductsManager;
import fr.mch.mdo.restaurant.business.managers.products.IValueAddedTaxesManager;
import fr.mch.mdo.restaurant.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.business.managers.tables.ITypeTablesManager;
import fr.mch.mdo.restaurant.business.managers.tables.orders.ITablesOrdersManager;
import fr.mch.mdo.restaurant.business.managers.users.IUserAuthenticationsManager;
import fr.mch.mdo.restaurant.business.managers.users.IUserRolesManager;
import fr.mch.mdo.restaurant.business.managers.users.IUsersManager;
import fr.mch.mdo.restaurant.ioc.IMdoBeanFactory;

public abstract class MdoAbstractBeanFactory implements IMdoBeanFactory
{
    public abstract Object getBean(String beanName);

    public ILogger getLogger()
    {
	return ((ILoggerService) this
		.getBean(Constants.BEAN_LOG_NAME)).getLogger();
    }

    public ILogger getLogger(String className)
    {
	return ((ILoggerService) this
		.getBean(Constants.BEAN_LOG_NAME)).getLogger(className);
    }

    public IMdoAuthorizationService getMdoAuthorizationService()
    {
	return ((IMdoAuthorizationService) this
		.getBean(Constants.BEAN_AUTHORIZATION_JAAS_NAME));
    }

    public IMdoAuthenticationService getMdoAuthenticationService()
    {
	return ((IMdoAuthenticationService) this
		.getBean(Constants.BEAN_AUTHENTICATION_JAAS_NAME));
    }

    public IAuthenticationManager getAuthenticationManager()
    {
	return ((IAuthenticationManager) this
		.getBean(Constants.BEAN_AUTHENTICATION_NAME));
    }

    public ILocalesManager getLocalesManager()
    {
	return ((ILocalesManager) this
		.getBean(Constants.BEAN_LOCALES_MANAGER_NAME));
    }

    public IRestaurantsManager getRestaurantsManager()
    {
	return ((IRestaurantsManager) this
		.getBean(Constants.BEAN_RESTAURANTS_MANAGER_NAME));
    }

    public IUsersManager getUsersManager()
    {
	return ((IUsersManager) this
		.getBean(Constants.BEAN_USERS_MANAGER_NAME));
    }

    public IUserRolesManager getUserRolesManager()
    {
	return ((IUserRolesManager) this
		.getBean(Constants.BEAN_USER_ROLES_MANAGER_NAME));
    }

    public IUserAuthenticationsManager getUserAuthenticationsManager()
    {
	return ((IUserAuthenticationsManager) this
		.getBean(Constants.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME));
    }

    public IProductsManager getProductsManager()
    {
	return ((IProductsManager) this
		.getBean(Constants.BEAN_PRODUCTS_MANAGER_NAME));
    }

    public ICategoriesManager getCategoriesManager()
    {
	return ((ICategoriesManager) this
		.getBean(Constants.BEAN_CATEGORIES_MANAGER_NAME));
    }

    public IProductPartsManager getProductPartsManager()
    {
	return ((IProductPartsManager) this
		.getBean(Constants.BEAN_PRODUCT_PARTS_MANAGER_NAME));
    }
    
    public IValueAddedTaxesManager getValueAddedTaxesManager()
    {
	return ((IValueAddedTaxesManager) this
		.getBean(Constants.BEAN_VALUE_ADDED_TAXES_MANAGER_NAME));
    }

    public IProductSpecialCodesManager getProductSpecialCodesManager()
    {
	return ((IProductSpecialCodesManager) this
		.getBean(Constants.BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME));
    }

    public ITypeTablesManager getTypeTablesManager()
    {
	return ((ITypeTablesManager) this
		.getBean(Constants.BEAN_TYPETABLES_MANAGER_NAME));
    }

    public ITablesOrdersManager getTablesOrdersManager()
    {
	return ((ITablesOrdersManager) this
		.getBean(Constants.BEAN_TABLE_ORDERS_MANAGER_NAME));
    }
}
