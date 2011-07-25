package fr.mch.mdo.restaurant.ioc.spring;

import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.exception.MdoFunctionalException;
import fr.mch.mdo.restaurant.ioc.IWebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.printings.IPrintingInformationsManager;
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

public class WebAdministractionBeanFactory extends MdoBeanFactory implements IWebAdministractionBeanFactory 
{
	private ILogger logger;
	
	private static class LazyHolder {
		private static IWebAdministractionBeanFactory instance = new WebAdministractionBeanFactory();
	}
	public static IWebAdministractionBeanFactory getInstance() {
		return LazyHolder.instance;
	}

	private WebAdministractionBeanFactory() {
		// Load IOC file configuration
		super();
		try {
			logger = this.getLogger(WebAdministractionBeanFactory.class.getName());
			initGlobalAdminUser();
		} catch (MdoFunctionalException e) {
			logger.error("message.error.ioc.init.admin", e);
		}
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
				// Check and save the existence of locale in database
				try {
					locale = (LocaleDto) this.getLocalesManager().findByLanguage(locale.getLanguageCode(), userContext);
				} catch (MdoException e) {
					logger.fatal("Could not retrieve locale " + locale.getLanguageCode(), e);
					throw new MdoFunctionalException("Could not retrieve locale " + locale.getLanguageCode(), e);
				}
				if (locale == null || locale.getId() == null) {
					locale = userGlobalAdmin.getLocales().iterator().next().getLocale();
					locale = (LocaleDto) this.getLocalesManager().insert(locale, userContext);
				}

				RestaurantDto restaurant = userGlobalAdmin.getRestaurant();
				MdoTableAsEnumDto specificRound = restaurant.getSpecificRound();
				// Check the existence of Specific Round in database
				specificRound = processMdoTableAsEnum(specificRound, userContext);
				restaurant.setSpecificRound(specificRound);
				
				TableTypeDto defaultTableType = restaurant.getDefaultTableType();
				// Check the existence of Table Type in database
				defaultTableType = processTableType(defaultTableType, userContext);
				restaurant.setDefaultTableType(defaultTableType);

				// Check and save the existence of restaurant in database
				try {
					restaurant = (RestaurantDto) this.getRestaurantsManager().findByReference(restaurant.getReference(), userContext);
					if (restaurant == null || restaurant.getId() == null) {
						// Save the restaurant
						restaurant = userGlobalAdmin.getRestaurant();
						restaurant = (RestaurantDto) this.getRestaurantsManager().insert(userGlobalAdmin.getRestaurant(), userContext);
					}
				} catch (MdoException e) {
					logger.fatal("Could not retrieve/save restaurant with reference " + restaurant.getReference(), e);
					throw new MdoFunctionalException("Could not retrieve/save restaurant with reference " + restaurant.getReference(), e);
				}

				UserDto user = userGlobalAdmin.getUser();
				MdoTableAsEnumDto title = user.getTitle();
				// Check and save the existence of title in database
				title = processMdoTableAsEnum(title, userContext);
				user.setTitle(title);
				
				Set<UserRestaurantDto> restaurants = user.getRestaurants();
				UserRestaurantDto userRestaurant = restaurants.iterator().next(); 
				userRestaurant.setRestaurant(restaurant);
				// Merge with the one into the set because they have the same java reference
				restaurants.add(userRestaurant);
				// Save the user
				// TODO : maybe add a functional unique reference ID
				user = (UserDto) this.getUsersManager().insert(user, userContext);

				UserRoleDto userRole = userGlobalAdmin.getUserRole();
				MdoTableAsEnumDto code = userRole.getCode();
				// Check and save the existence of code in database
				code = processMdoTableAsEnum(code, userContext);
				userRole.setCode(code);
				
				// Check and save the existence of user role in database
				try {
					userRole = (UserRoleDto) this.getUserRolesManager().findByCode(userRole.getCode().getName(), userContext);
					if (userRole == null || userRole.getId() == null) {
						// Save the User Role
						userRole = userGlobalAdmin.getUserRole();
						userRole = (UserRoleDto) this.getUserRolesManager().insert(userRole, userContext);
					}
				} catch (MdoException e) {
					logger.fatal("Could not retrieve/save user role with code " + userRole.getCode(), e);
					throw new MdoFunctionalException("Could not retrieve/save user role with code " + userRole.getCode(), e);
				}
				
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

	/**
	 * Check and save the existence of the MdoTableAsEnumDto in database
	 * @param dtBean
	 * @param userContext
	 * @return
	 * @throws MdoFunctionalException
	 */
	private MdoTableAsEnumDto processMdoTableAsEnum(MdoTableAsEnumDto dtBean, MdoUserContext userContext) throws MdoFunctionalException {
		MdoTableAsEnumDto result = dtBean;
		// Check the existence of Specific Round in database
		try {
			result = (MdoTableAsEnumDto) this.getMdoTableAsEnumsManager().findByTypeAndName(result.getType(), result.getName(), userContext);
			if (result == null || result.getId() == null) {
				// Save the bean
				result = (MdoTableAsEnumDto) this.getMdoTableAsEnumsManager().insert(dtBean, userContext);
			}
		} catch (MdoException e) {
			logger.fatal("Could not retrieve/save MdoTableAsEnumDto with type(" + result.getType() + ") and name(" + result.getType() + ")", e);
			throw new MdoFunctionalException("Could not retrieve/save MdoTableAsEnumDto with type(" + result.getType() + ") and name(" + result.getType() + ")", e);
		}
		return result;
	}

	/**
	 * Check and save the existence of the TableTypeDto in database
	 * @param dtBean
	 * @param userContext
	 * @return
	 * @throws MdoFunctionalException
	 */
	private TableTypeDto processTableType(TableTypeDto dtBean, MdoUserContext userContext) throws MdoFunctionalException {
		TableTypeDto result = dtBean;
		// Check the existence of Specific Round in database
		try {
			result = (TableTypeDto) this.getTableTypesManager().findByCodeName(result.getCode().getName(), userContext);
			if (result == null || result.getId() == null) {
				// Save the bean
				result = (TableTypeDto) this.getTableTypesManager().insert(dtBean, userContext);
			}
		} catch (MdoException e) {
			logger.fatal("Could not retrieve/save MdoTableAsEnumDto with type(" + result.getCode().getType() + ") and name(" + result.getCode().getType() + ")", e);
			throw new MdoFunctionalException("Could not retrieve/save MdoTableAsEnumDto with type(" + result.getCode().getType() + ") and name(" + result.getCode().getType() + ")", e);
		}
		return result;
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
	public IPrintingInformationsManager getPrintingInformationsManager() {
		return ((IPrintingInformationsManager) this.getBean(IocBeanName.BEAN_PRINTING_INFORMATIONS_MANAGER_NAME));
	}
}
