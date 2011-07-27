package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.printings.IPrintingInformationsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductPartsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductSpecialCodesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.ITableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserRolesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUsersManager;

public interface IWebAdministrationBeanFactory extends IMdoBeanFactory
{
    IMdoTableAsEnumsManager getMdoTableAsEnumsManager();

    IRestaurantsManager getRestaurantsManager();

    IUsersManager getUsersManager();

    IUserRolesManager getUserRolesManager();

    IProductsManager getProductsManager();

    ICategoriesManager getCategoriesManager();

    IProductPartsManager getProductPartsManager();
    
    IValueAddedTaxesManager getValueAddedTaxesManager();

    IProductSpecialCodesManager getProductSpecialCodesManager();
    
    ITableTypesManager getTableTypesManager();
    
	IMdoAuthorizationService getMdoAuthorizationService();

	IMdoAuthenticationService getMdoAuthenticationService();

    IPrintingInformationsManager getPrintingInformationsManager();

}
