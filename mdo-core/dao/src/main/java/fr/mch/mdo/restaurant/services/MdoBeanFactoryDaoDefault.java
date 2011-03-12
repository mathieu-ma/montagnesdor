package fr.mch.mdo.restaurant.services;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationDao;
import fr.mch.mdo.restaurant.dao.authentication.hibernate.DefaultAuthenticationDao;
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
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactoryDao;

public final class MdoBeanFactoryDaoDefault extends MdoAbstractBeanFactory implements IBeanFactoryDao 
{
	protected Map<IocBeanName, Object> factory;

	protected void init() throws MdoTechnicalException {
		if (factory == null) {
			factory = new HashMap<IocBeanName, Object>();
		}
		try {
			factory.put(IocBeanName.BEAN_AUTHENTICATION_DAO_NAME, DefaultAuthenticationDao.getInstance());
		} catch (Exception e) {
			throw new MdoTechnicalException("mdo.technical.generic.exception", e);
		}
		/*
		 * factory.put(IocBeanName.BEAN_AUTHORIZATION_JAAS_NAME,
		 * MdoAuthorizationServiceImpl.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_AUTHENTICATION_JAAS_NAME,
		 * MdoAuthenticationServiceImpl.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_AUTHENTICATION_NAME,
		 * DefaultAuthenticationManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_LOCALES_MANAGER_NAME,
		 * DefaultLocalesManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_RESTAURANTS_MANAGER_NAME,
		 * DefaultRestaurantsManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_USERS_MANAGER_NAME,
		 * DefaultUsersManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_USER_ROLES_MANAGER_NAME,
		 * DefaultUserRolesManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME,
		 * DefaultUserAuthenticationsManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_PRODUCTS_MANAGER_NAME,
		 * DefaultProductsManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_CATEGORIES_MANAGER_NAME,
		 * DefaultCategoriesManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_PRODUCT_PARTS_MANAGER_NAME,
		 * DefaultProductPartsManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_VALUE_ADDED_TAXES_MANAGER_NAME,
		 * DefaultValueAddedTaxesManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME,
		 * DefaultProductSpecialCodesManager.getInstance());
		 * 
		 * factory.put(IocBeanName.BEAN_TYPETABLES_MANAGER_NAME,
		 * DefaultTypeTablesManager.getInstance());
		 */
	}

	@Override
	public Object getBean(IocBeanName beanName) {
		if (factory == null) {
			try {
				this.init();
			} catch (MdoTechnicalException e) {
				throw new ExceptionInInitializerError(e);
			}
		}
		return factory.get(beanName);
	}

	@Override
	public IAuthenticationDao getAuthenticationDao() {
		return (IAuthenticationDao) factory.get(IocBeanName.BEAN_AUTHENTICATION_DAO_NAME);
	}

	@Override
	public ICategoriesDao getCategoriesDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILocalesDao getLocalesDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProductPartsDao getProductPartsDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProductSpecialCodesDao getProductSpecialCodesDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProductsDao getProductsDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRestaurantsDao getRestaurantsDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITableTypesDao getTableTypesDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUserAuthenticationsDao getUserAuthenticationsDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUserRolesDao getUserRolesDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUsersDao getUsersDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValueAddedTaxesDao getValueAddedTaxesDao() {
		// TODO Auto-generated method stub
		return null;
	}
}
