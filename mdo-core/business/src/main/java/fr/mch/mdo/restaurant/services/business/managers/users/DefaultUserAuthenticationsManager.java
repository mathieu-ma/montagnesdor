package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserLocale;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultUserAuthenticationsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserAuthenticationsManager extends AbstractAdministrationManager implements IUserAuthenticationsManager 
{
	private ILocalesManager localesManager;
	private IUsersManager usersManager;
	private IUserRolesManager userRolesManager;
	private IRestaurantsManager restaurantsManager;

	private static class LazyHolder {
		private static IUserAuthenticationsManager instance = new DefaultUserAuthenticationsManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultUserAuthenticationsManager.class.getName()),
				DefaultUserAuthenticationsDao.getInstance(), DefaultUserAuthenticationsAssembler.getInstance());
	}

	private DefaultUserAuthenticationsManager(ILogger logger, IUserAuthenticationsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.localesManager = DefaultLocalesManager.getInstance();
		this.usersManager = DefaultUsersManager.getInstance();
		this.userRolesManager = DefaultUserRolesManager.getInstance();
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultUserAuthenticationsManager() {
	}

	public static IUserAuthenticationsManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the localesManager
	 */
	public ILocalesManager getLocalesManager() {
		return localesManager;
	}

	/**
	 * @param localesManager the localesManager to set
	 */
	public void setLocalesManager(ILocalesManager localesManager) {
		this.localesManager = localesManager;
	}

	/**
	 * @return the usersManager
	 */
	public IUsersManager getUsersManager() {
		return usersManager;
	}

	/**
	 * @param usersManager the usersManager to set
	 */
	public void setUsersManager(IUsersManager usersManager) {
		this.usersManager = usersManager;
	}

	/**
	 * @return the userRolesManager
	 */
	public IUserRolesManager getUserRolesManager() {
		return userRolesManager;
	}

	/**
	 * @param userRolesManager the userRolesManager to set
	 */
	public void setUserRolesManager(IUserRolesManager userRolesManager) {
		this.userRolesManager = userRolesManager;
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	@Override
	public IMdoDtoBean findByLogin(String login, MdoUserContext userContext) throws MdoBusinessException {
		IMdoDtoBean result = null;
		
		try {
			UserAuthentication user = (UserAuthentication) ((IUserAuthenticationsDao) dao).findByUniqueKey(login);
			result = assembler.marshal(user, userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.user.authentication.not.found", new Object[] {login}, e);
			throw new MdoBusinessException("message.error.administration.business.user.authentication.not.found", new Object[] {login}, e);
		}
		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		UserAuthenticationsManagerViewBean userAuthenticationsManagerViewBean = (UserAuthenticationsManagerViewBean) viewBean;
		try {
			userAuthenticationsManagerViewBean.setLanguages(localesManager.getLanguageLocales(userContext));
			userAuthenticationsManagerViewBean.setUsers(usersManager.findAll(userContext));
			userAuthenticationsManagerViewBean.setUserRoles(userRolesManager.findAll(userContext));
			
			UserAuthenticationDto dtoBean = (UserAuthenticationDto) userAuthenticationsManagerViewBean.getDtoBean();
			Long userIdForRestaurant = null;
			if (dtoBean != null && dtoBean.getUser() != null) {
				userIdForRestaurant = dtoBean.getUser().getId();
			} else if (userAuthenticationsManagerViewBean.getUsers() != null && !userAuthenticationsManagerViewBean.getUsers().isEmpty()) {
				userIdForRestaurant = ((UserDto) userAuthenticationsManagerViewBean.getUsers().get(0)).getId();
			}
			userAuthenticationsManagerViewBean.setUserRestaurants(restaurantsManager.findRestaurantsByUser(userIdForRestaurant, userContext));

		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
	
	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		UserAuthenticationDto result = null;  
		UserAuthentication daoBean = (UserAuthentication) assembler.unmarshal(dtoBean);
		try {
			// Deleting daoBean.getLocales() before inserting new ones
			Set<UserLocale> backup = new HashSet<UserLocale>(daoBean.getLocales());
			// Removing
			daoBean.getLocales().clear();
			dao.update(daoBean);
			// Restoring
			daoBean.getLocales().addAll(backup);

			result = (UserAuthenticationDto) assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
		return result;
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		// No need to Delete Locales before Deleting user because of hibernate mapping all-delete-orphan in collection
		// Delete dto
		return super.delete(dtoBean, userContext);
	}

	@Override
	public void changePassword(Long id, String levelPassword, String newPassword, IMdoBean userContext) throws MdoException {
		IUserAuthenticationsDao castedDao = (IUserAuthenticationsDao) dao;
		castedDao.changePassword(id, AuthenticationPasswordLevel.valueOf(levelPassword), newPassword);
	}
}
