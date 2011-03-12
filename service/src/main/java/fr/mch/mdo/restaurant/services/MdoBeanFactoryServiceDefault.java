package fr.mch.mdo.restaurant.services;

import java.util.HashMap;
import java.util.Map;

import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactoryService;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.restaurant.services.util.IUtils;
import fr.mch.mdo.restaurant.services.util.UtilsImpl;

public final class MdoBeanFactoryServiceDefault extends MdoAbstractBeanFactory implements IBeanFactoryService
{
    protected Map<IocBeanName, Object> factory;

    protected void init() throws MdoTechnicalException {
	if (factory == null) {
	    factory = new HashMap<IocBeanName, Object>();
	}
	try {
	    factory.put(IocBeanName.BEAN_LOG_NAME, LoggerServiceImpl.getInstance());
	    factory.put(IocBeanName.BEAN_UTILS_NAME, UtilsImpl.getInstance());
	} catch (Exception e) {
	    throw new MdoTechnicalException("mdo.technical.generic.exception", e);
	}
	/*	
	factory.put(IocBeanName.BEAN_AUTHORIZATION_JAAS_NAME, MdoAuthorizationServiceImpl.getInstance());

	factory.put(IocBeanName.BEAN_AUTHENTICATION_JAAS_NAME, MdoAuthenticationServiceImpl.getInstance());

	factory.put(IocBeanName.BEAN_AUTHENTICATION_NAME, DefaultAuthenticationManager.getInstance());

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

	factory.put(IocBeanName.BEAN_TYPETABLES_MANAGER_NAME, DefaultTypeTablesManager.getInstance());
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
    public ILoggerService getLoggerService() {
	return (ILoggerService) factory.get(IocBeanName.BEAN_LOG_NAME);
    }

    @Override
    public IUtils getUtils() {
	return (IUtils) factory.get(IocBeanName.BEAN_UTILS_NAME);
    }
}
