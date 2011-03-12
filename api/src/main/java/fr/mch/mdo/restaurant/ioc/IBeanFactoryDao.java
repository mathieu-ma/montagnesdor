package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationDao;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;


public interface IBeanFactoryDao extends IBeanFactory
{
    IAuthenticationDao getAuthenticationDao();

    ILocalesDao getLocalesDao();

    ICategoriesDao getCategoriesDao();

    IProductPartsDao getProductPartsDao();

    IProductsDao getProductsDao();

    IProductSpecialCodesDao getProductSpecialCodesDao();

    IValueAddedTaxesDao getValueAddedTaxesDao();

    IRestaurantsDao getRestaurantsDao();

    ITableTypesDao getTableTypesDao();

    IUserAuthenticationsDao getUserAuthenticationsDao();

    IUserRolesDao getUserRolesDao();

    IUsersDao getUsersDao();
}
