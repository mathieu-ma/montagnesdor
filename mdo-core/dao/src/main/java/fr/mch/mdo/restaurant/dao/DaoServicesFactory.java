package fr.mch.mdo.restaurant.dao;

import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationDao;
import fr.mch.mdo.restaurant.dao.authentication.hibernate.DefaultAuthenticationDao;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.dao.locales.hibernate.DefaultLocalesDao;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultCategoriesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantsDao;
import fr.mch.mdo.restaurant.dao.tables.ICreditsDao;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultCreditsDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultTableTypesDao;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUsersDao;

public class DaoServicesFactory {

	private enum DaoInstances {
		IAuthenticationDao {
			public IDao getInstance() {
				return DefaultAuthenticationDao.getInstance();
			}
		},
		ILocalesDao;
		public IDao getInstance() {
			throw new IllegalAccessError("This method is not overriden");
		}
	};

	public static IAuthenticationDao getAuthenticationDao() {
		return (IAuthenticationDao) DaoInstances.IAuthenticationDao.getInstance();
	}

	public static ILocalesDao getLocalesDao() {
		// return (ILocalesDao) DaoInstances.ILocalesDao.getInstance();
		return DefaultLocalesDao.getInstance();
	}

	public static IMdoTableAsEnumsDao getMdoTableAsEnumsDao() {
		return DefaultMdoTableAsEnumsDao.getInstance();
	}

	public static ICategoriesDao getCategoriesDao() {
		return DefaultCategoriesDao.getInstance();
	}

	public static IProductPartsDao getProductPartsDao() {
		return DefaultProductPartsDao.getInstance();
	}

	public static IProductsDao getProductsDao() {
		return DefaultProductsDao.getInstance();
	}

	public static IProductSpecialCodesDao getProductSpecialCodesDao() {
		return DefaultProductSpecialCodesDao.getInstance();
	}

	public static IValueAddedTaxesDao getValueAddedTaxesDao() {
		return DefaultValueAddedTaxesDao.getInstance();
	}

	public static IRestaurantsDao getRestaurantsDao() {
		return DefaultRestaurantsDao.getInstance();
	}

	public static ITableTypesDao getTableTypesDao() {
		return DefaultTableTypesDao.getInstance();
	}

	public static IUserAuthenticationsDao getUserAuthenticationsDao() {
		return DefaultUserAuthenticationsDao.getInstance();
	}

	public static IUserRolesDao getUserRolesDao() {
		return DefaultUserRolesDao.getInstance();
	}

	public static IUsersDao getUsersDao() {
		return DefaultUsersDao.getInstance();
	}

	public static ICreditsDao getCreditsDao() {
		return DefaultCreditsDao.getInstance();
	}
}
