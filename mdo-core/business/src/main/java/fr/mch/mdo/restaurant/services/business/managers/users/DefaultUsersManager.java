package fr.mch.mdo.restaurant.services.business.managers.users;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUsersDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
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
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		UsersManagerViewBean usersManagerViewBean = (UsersManagerViewBean) viewBean;
		try {
			//MdoUserContext userContext = viewBean.getUserContext(); 
			usersManagerViewBean.setRestaurants(restaurantsManager.findAll(userContext));
			usersManagerViewBean.setTitles(mdoTableAsEnumsManager.getUserTitles(userContext));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
	
	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		User daoBean = (User) assembler.unmarshal(dtoBean);
		try {
			if (daoBean != null && daoBean.getId() != null) {
				// dummy is just used for updating daoBean.getRestaurants()
				User dummy = (User) dao.findByPrimaryKey(daoBean.getId());
				dummy.getRestaurants().clear();
				dummy.getRestaurants().addAll(daoBean.getRestaurants());
				daoBean.setRestaurants(dummy.getRestaurants());
			}
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		// Load data
		dtoBean = super.findByPrimaryKey(dtoBean.getId(), userContext);
		// Delete Restaurants before
		this.update(dtoBean, userContext);
		// Delete dto
		return super.delete(dtoBean, userContext);
	}

}