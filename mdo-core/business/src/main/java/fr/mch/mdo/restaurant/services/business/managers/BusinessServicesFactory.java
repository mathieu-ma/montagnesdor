package fr.mch.mdo.restaurant.services.business.managers;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;
import fr.mch.mdo.restaurant.services.authentication.jaas.MdoAuthenticationServiceImpl;
import fr.mch.mdo.restaurant.services.authorization.jaas.MdoAuthorizationServiceImpl;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultCategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultProductPartsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultProductSpecialCodesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultProductsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserAuthenticationsManager;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserRolesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUsersManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class BusinessServicesFactory implements IBeanFactory
{
	private static Map<IocBeanName, Object> factory = new HashMap<IocBeanName, Object>();

	static {
		factory.put(IocBeanName.BEAN_LOG_NAME, LoggerServiceImpl.getInstance());

		factory.put(IocBeanName.BEAN_MDO_TABLE_AS_ENUMS_MANAGER_NAME, DefaultMdoTableAsEnumsManager.getInstance());

		factory.put(IocBeanName.BEAN_LOCALES_MANAGER_NAME, DefaultLocalesManager.getInstance());

		factory.put(IocBeanName.BEAN_RESTAURANTS_MANAGER_NAME, DefaultRestaurantsManager.getInstance());

		factory.put(IocBeanName.BEAN_USERS_MANAGER_NAME, DefaultUsersManager.getInstance());

		factory.put(IocBeanName.BEAN_USER_ROLES_MANAGER_NAME, DefaultUserRolesManager.getInstance());

		factory.put(IocBeanName.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME, DefaultUserAuthenticationsManager.getInstance());

		factory.put(IocBeanName.BEAN_PRODUCTS_MANAGER_NAME, DefaultProductsManager.getInstance());

		factory.put(IocBeanName.BEAN_CATEGORIES_MANAGER_NAME, DefaultCategoriesManager.getInstance());

		factory.put(IocBeanName.BEAN_PRODUCT_PARTS_MANAGER_NAME, DefaultProductPartsManager.getInstance());

		factory.put(IocBeanName.BEAN_VALUE_ADDED_TAXES_MANAGER_NAME, DefaultValueAddedTaxesManager.getInstance());

		factory.put(IocBeanName.BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME, DefaultProductSpecialCodesManager.getInstance());

		factory.put(IocBeanName.BEAN_TABLE_TYPES_MANAGER_NAME, DefaultTableTypesManager.getInstance());
	    
		factory.put(IocBeanName.BEAN_AUTHENTICATION_JAAS_NAME, MdoAuthenticationServiceImpl.getInstance());

		factory.put(IocBeanName.BEAN_AUTHORIZATION_JAAS_NAME, MdoAuthorizationServiceImpl.getInstance());
	}
	
	private static class LazyHolder {
		private static IBeanFactory instance = new BusinessServicesFactory();
	}
	public static IBeanFactory getInstance() {
		return LazyHolder.instance;
	}

	private BusinessServicesFactory() {
	}

	@Override
	public Object getBean(IocBeanName beanName) {
		return factory.get(beanName);
	}

}
