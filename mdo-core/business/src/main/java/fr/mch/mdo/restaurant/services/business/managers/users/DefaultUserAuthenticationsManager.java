package fr.mch.mdo.restaurant.services.business.managers.users;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
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
}