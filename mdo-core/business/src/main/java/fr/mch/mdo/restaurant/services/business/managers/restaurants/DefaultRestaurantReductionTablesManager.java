package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTablesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultRestaurantReductionTablesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.ITableTypesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantReductionTablesManager extends AbstractAdministrationManager implements IRestaurantReductionTablesManager 
{
	private ITableTypesManager tableTypesManager;
	private IRestaurantsManager restaurantsManager;

	private static class LazyHolder {
		private static IRestaurantReductionTablesManager instance = new DefaultRestaurantReductionTablesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantReductionTablesManager.class.getName()),
				DefaultRestaurantReductionTablesDao.getInstance(), DefaultRestaurantReductionTablesAssembler.getInstance());
	}

	private DefaultRestaurantReductionTablesManager(ILogger logger, IRestaurantReductionTablesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.tableTypesManager = DefaultTableTypesManager.getInstance();
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultRestaurantReductionTablesManager() {
	}

	public static IRestaurantReductionTablesManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the tableTypesManager
	 */
	public ITableTypesManager getTableTypesManager() {
		return tableTypesManager;
	}

	/**
	 * @param tableTypesManager the tableTypesManager to set
	 */
	public void setTableTypesManager(ITableTypesManager tableTypesManager) {
		this.tableTypesManager = tableTypesManager;
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

		RestaurantReductionTablesManagerViewBean restaurantPrefixTablesManagerViewBean = (RestaurantReductionTablesManagerViewBean) viewBean;
		
		try {
			restaurantPrefixTablesManagerViewBean.setTypes(tableTypesManager.findAll(lazy));
			restaurantPrefixTablesManagerViewBean.setRestaurants(restaurantsManager.findAll(lazy));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	@Override
	public List<IMdoDtoBean> findAll(MdoUserContext userContext, Long restaurantId) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<? extends IMdoBean> list = ((IRestaurantReductionTablesDao) dao).findAll(restaurantId);
			if (list != null) {
				result = assembler.marshal(list);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		return result;
	}

	@Override
	public List<IMdoDtoBean> findAll(MdoUserContext userContext, Long restaurantId, String typeName) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<? extends IMdoBean> list = ((IRestaurantReductionTablesDao) dao).findAll(restaurantId, typeName);
			if (list != null) {
				result = assembler.marshal(list);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		return result;
	}
}
