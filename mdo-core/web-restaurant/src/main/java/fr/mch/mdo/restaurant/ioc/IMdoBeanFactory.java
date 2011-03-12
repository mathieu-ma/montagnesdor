package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.logs.ILogger;
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
import fr.mch.mdo.restaurant.business.managers.user.IUserManager;
import fr.mch.mdo.restaurant.business.managers.users.IUserAuthenticationsManager;
import fr.mch.mdo.restaurant.business.managers.users.IUserRolesManager;
import fr.mch.mdo.restaurant.business.managers.users.IUsersManager;

public interface IMdoBeanFactory
{
    public Object getBean(String beanName);
    
/*    
    public ILogger getLogger();
    
    public ILogger getLogger(String className);

    public IMdoAuthorizationService getMdoAuthorizationService();

    public IMdoAuthenticationService getMdoAuthenticationService();

    public IAuthenticationManager getAuthenticationManager();

    public ILocalesManager getLocalesManager();

    public IRestaurantsManager getRestaurantsManager();

    public IUsersManager getUsersManager();

    public IUserRolesManager getUserRolesManager();

    public IUserAuthenticationsManager getUserAuthenticationsManager();

    public IProductsManager getProductsManager();

    public ICategoriesManager getCategoriesManager();

    public IProductPartsManager getProductPartsManager();
    
    public IValueAddedTaxesManager getValueAddedTaxesManager();

    public IProductSpecialCodesManager getProductSpecialCodesManager();
    
    public ITypeTablesManager getTypeTablesManager();

    public ITablesOrdersManager getTablesOrdersManager();

    public IUserManager getUserManager();
*/
}
