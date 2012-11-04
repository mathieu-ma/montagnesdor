package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserRestaurant;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUsersDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.UsersManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultUsersAssembler;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUsersManager extends AbstractAdministrationManager implements IUsersManager 
{
	private IRestaurantsManager restaurantsManager;
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;

	private static class LazyHolder {
		private static IUsersManager instance = new DefaultUsersManager(LoggerServiceImpl.getInstance().getLogger(DefaultUsersManager.class.getName()), 
				DefaultUsersDao.getInstance(), DefaultUsersAssembler.getInstance());
	}

	private DefaultUsersManager(ILogger logger, IUsersDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultUsersManager() {
	}

	public static IUsersManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @param mdoTableAsEnumsManager the mdoTableAsEnumsManager to set
	 */
	public void setMdoTableAsEnumsManager(IMdoTableAsEnumsManager mdoTableAsEnumsManager) {
		this.mdoTableAsEnumsManager = mdoTableAsEnumsManager;
	}

	/**
	 * @return the mdoTableAsEnumsManager
	 */
	public IMdoTableAsEnumsManager getMdoTableAsEnumsManager() {
		return mdoTableAsEnumsManager;
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
	public void processList(IAdministrationManagerViewBean viewBean, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, lazy);
		UsersManagerViewBean usersManagerViewBean = (UsersManagerViewBean) viewBean;
		try {
			//MdoUserContext userContext = viewBean.getUserContext(); 
			usersManagerViewBean.setRestaurants(restaurantsManager.findAll());
			usersManagerViewBean.setTitles(mdoTableAsEnumsManager.getUserTitles());
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
	
	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean) throws MdoBusinessException {
		User daoBean = (User) assembler.unmarshal(dtoBean);
		try {
			// Deleting daoBean.getRestaurants() before inserting new ones
			Set<UserRestaurant> backup = new HashSet<UserRestaurant>(daoBean.getRestaurants());
			// Removing
			daoBean.getRestaurants().clear();
			dao.update(daoBean);
			// Restoring
			daoBean.getRestaurants().addAll(backup);

			return assembler.marshal((IMdoDaoBean) dao.update(daoBean));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean) throws MdoBusinessException {
		// No need to Delete Restaurants before Deleting user because of hibernate mapping all-delete-orphan in collection
		return super.delete(dtoBean);
	}

}
