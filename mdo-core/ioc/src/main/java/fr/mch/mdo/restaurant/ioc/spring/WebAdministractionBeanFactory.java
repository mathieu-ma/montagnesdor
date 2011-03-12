package fr.mch.mdo.restaurant.ioc.spring;

import java.util.Set;

import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.exception.MdoFunctionalException;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;
import fr.mch.mdo.restaurant.ioc.IWebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductPartsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductSpecialCodesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IProductsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.ITableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserRolesManager;
import fr.mch.mdo.restaurant.services.business.managers.users.IUsersManager;

public class WebAdministractionBeanFactory implements IWebAdministractionBeanFactory 
{
	private ILogger logger;
	private IBeanFactory beanFactory;
	
	private static class LazyHolder {
		private static IWebAdministractionBeanFactory instance = new WebAdministractionBeanFactory();
	}
	public static IWebAdministractionBeanFactory getInstance() {
		return LazyHolder.instance;
	}

	private WebAdministractionBeanFactory() {
		try {
			this.beanFactory = new SpringBeanFactory(Constants.SPRING_CONFIGURATION_FILE.split(","));
			logger = this.getLogger(WebAdministractionBeanFactory.class.getName());
			initGlobalAdminUser();
		} catch (MdoFunctionalException e) {
			logger.error("message.error.ioc.init.admin", e);
		} catch (MdoTechnicalException e) {
			// Could not use IOC
			throw new ExceptionInInitializerError(MessageQueryResourceBundleImpl.getInstance().getMessage("message.error.ioc.init.spring"));
		}
	}
	
	@Override
	public Object getBean(IocBeanName beanName) {
		return beanFactory.getBean(beanName);
	}

	/**
	 * Maybe this method has to be transactional by AspectJ.
	 * @throws MdoFunctionalException
	 */
	private void initGlobalAdminUser() throws MdoFunctionalException {

		UserAuthenticationDto userGlobalAdmin = (UserAuthenticationDto) this.getBean(IocBeanName.BEAN_USER_AUTHENTICATION_GLOBAL_ADMIN_NAME);
		MdoUserContext userContext = null;// this.getBean(IocBeanName.BEAN_USER_CONTEXT_GLOBAL_ADMIN_NAME);
		/*
		 * userGlobalAdmin.getUserAuthentication().setId(new Long(5));
		 * userGlobalAdmin.getUserRole().setId(new Long(5));
		 * userGlobalAdmin.getRestaurant().setId(new Long(2));
		 */
		IMdoBean userAuthentication = null;
		try {
			userAuthentication = this.getUserAuthenticationsManager().findByLogin(userGlobalAdmin.getLogin(), userContext);
		} catch (MdoException e) {
			logger.fatal("Could not retreive user " + userGlobalAdmin.getLogin(), e);
			throw new MdoFunctionalException("Could not retreive user " + userGlobalAdmin.getLogin(), e);
		}
		if (userAuthentication == null) {
			// Create the super admin
			try {
				LocaleDto locale = userGlobalAdmin.getLocales().iterator().next().getLocale();
				locale = (LocaleDto) this.getLocalesManager().insert(locale, userContext);

				RestaurantDto restaurant = userGlobalAdmin.getRestaurant();
				MdoTableAsEnumDto specificRound = restaurant.getSpecificRound();
				specificRound = (MdoTableAsEnumDto) this.getMdoTableAsEnumsManager().insert(restaurant.getSpecificRound(), userContext);
				restaurant.setSpecificRound(specificRound);
				// Save the restaurant
				restaurant = (RestaurantDto) this.getRestaurantsManager().insert(userGlobalAdmin.getRestaurant(), userContext);

				UserDto user = userGlobalAdmin.getUser();
				MdoTableAsEnumDto title = user.getTitle();
				title = (MdoTableAsEnumDto) this.getMdoTableAsEnumsManager().insert(title, userContext);
				user.setTitle(title);
				Set<UserRestaurantDto> restaurants = user.getRestaurants();
				UserRestaurantDto userRestaurant = restaurants.iterator().next(); 
				userRestaurant.setRestaurant(restaurant);
				restaurants.add(userRestaurant);
				// Save the user
				user = (UserDto) this.getUsersManager().insert(user, userContext);

				UserRoleDto userRole = userGlobalAdmin.getUserRole();
				MdoTableAsEnumDto code = userRole.getCode();
				code = (MdoTableAsEnumDto) this.getMdoTableAsEnumsManager().insert(code, userContext);
				userRole.setCode(code);
				// Save the user role
				userRole = (UserRoleDto) this.getUserRolesManager().insert(userRole, userContext);
				
				userGlobalAdmin.setRestaurant(restaurant);
				userGlobalAdmin.setUser(user);
				userGlobalAdmin.setUserRole(userRole);
				userGlobalAdmin.setPrintingLocale(locale);
				Set<UserLocaleDto> locales = userGlobalAdmin.getLocales();
				UserLocaleDto userLocale = locales.iterator().next();
				userLocale.setLocale(locale);
				locales.add(userLocale);
				// Save the user authentication
				this.getUserAuthenticationsManager().insert(userGlobalAdmin, userContext);
			} catch (MdoException e) {
				logger.fatal("Could not save user " + userGlobalAdmin.getLogin(), e);
				throw new MdoFunctionalException("Could not save user " + userGlobalAdmin.getLogin(), e);
			}
		}
	}

	@Override
	public ILocalesManager getLocalesManager() {
		return ((ILocalesManager) this.getBean(IocBeanName.BEAN_LOCALES_MANAGER_NAME));
	}

	@Override
	public IMdoTableAsEnumsManager getMdoTableAsEnumsManager() {
		return ((IMdoTableAsEnumsManager) this.getBean(IocBeanName.BEAN_MDO_TABLE_AS_ENUMS_MANAGER_NAME));
	}
	
	@Override
	public IRestaurantsManager getRestaurantsManager() {
		return ((IRestaurantsManager) this.getBean(IocBeanName.BEAN_RESTAURANTS_MANAGER_NAME));
	}

	@Override
	public IUsersManager getUsersManager() {
		return ((IUsersManager) this.getBean(IocBeanName.BEAN_USERS_MANAGER_NAME));
	}

	@Override
	public IUserRolesManager getUserRolesManager() {
		return ((IUserRolesManager) this.getBean(IocBeanName.BEAN_USER_ROLES_MANAGER_NAME));
	}

	@Override
	public IUserAuthenticationsManager getUserAuthenticationsManager() {
		return ((IUserAuthenticationsManager) this.getBean(IocBeanName.BEAN_USER_AUTHENTICATIONS_MANAGER_NAME));
	}

	@Override
	public IProductsManager getProductsManager() {
		return ((IProductsManager) this.getBean(IocBeanName.BEAN_PRODUCTS_MANAGER_NAME));
	}

	@Override
	public ICategoriesManager getCategoriesManager() {
		return ((ICategoriesManager) this.getBean(IocBeanName.BEAN_CATEGORIES_MANAGER_NAME));
	}

	@Override
	public IProductPartsManager getProductPartsManager() {
		return ((IProductPartsManager) this.getBean(IocBeanName.BEAN_PRODUCT_PARTS_MANAGER_NAME));
	}

	@Override
	public IValueAddedTaxesManager getValueAddedTaxesManager() {
		return ((IValueAddedTaxesManager) this.getBean(IocBeanName.BEAN_VALUE_ADDED_TAXES_MANAGER_NAME));
	}

	@Override
	public IProductSpecialCodesManager getProductSpecialCodesManager() {
		return ((IProductSpecialCodesManager) this.getBean(IocBeanName.BEAN_PRODUCT_SPECIAL_CODES_MANAGER_NAME));
	}

	@Override
	public ITableTypesManager getTableTypesManager() {
		return ((ITableTypesManager) this.getBean(IocBeanName.BEAN_TABLE_TYPES_MANAGER_NAME));
	}

	@Override
	public IMdoAuthenticationService getMdoAuthenticationService() {
		return ((IMdoAuthenticationService) this.getBean(IocBeanName.BEAN_AUTHENTICATION_JAAS_NAME));
	}

	@Override
	public IMdoAuthorizationService getMdoAuthorizationService() {
		return ((IMdoAuthorizationService) this.getBean(IocBeanName.BEAN_AUTHORIZATION_JAAS_NAME));
	}

	@Override
	public IMessageQuery getMessageQuery() {
		return ((IMessageQuery) getBean(IocBeanName.BEAN_I18N_NAME));
	}

	@Override
	public ILogger getLogger() {
		return ((ILoggerService) getBean(IocBeanName.BEAN_LOG_NAME)).getLogger();
	}

	@Override
	public ILogger getLogger(String className) {
		return ((ILoggerService) getBean(IocBeanName.BEAN_LOG_NAME)).getLogger(className);
	}
}
