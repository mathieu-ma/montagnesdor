package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTablesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultRestaurantPrefixTablesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.ITableTypesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantPrefixTablesManager extends AbstractAdministrationManager implements IRestaurantPrefixTablesManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;
	private ITableTypesManager tableTypesManager;

	private static class LazyHolder {
		private static IRestaurantPrefixTablesManager instance = new DefaultRestaurantPrefixTablesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantPrefixTablesManager.class.getName()),
				DefaultRestaurantPrefixTablesDao.getInstance(), DefaultRestaurantPrefixTablesAssembler.getInstance());
	}

	private DefaultRestaurantPrefixTablesManager(ILogger logger, IRestaurantPrefixTablesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
		this.tableTypesManager = DefaultTableTypesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultRestaurantPrefixTablesManager() {
	}

	public static IRestaurantPrefixTablesManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the mdoTableAsEnumsManager
	 */
	public IMdoTableAsEnumsManager getMdoTableAsEnumsManager() {
		return mdoTableAsEnumsManager;
	}

	/**
	 * @param mdoTableAsEnumsManager the mdoTableAsEnumsManager to set
	 */
	public void setMdoTableAsEnumsManager(IMdoTableAsEnumsManager mdoTableAsEnumsManager) {
		this.mdoTableAsEnumsManager = mdoTableAsEnumsManager;
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

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, lazy);

		RestaurantPrefixTablesManagerViewBean restaurantPrefixTablesManagerViewBean = (RestaurantPrefixTablesManagerViewBean) viewBean;
		
		try {
			restaurantPrefixTablesManagerViewBean.setPrefixes(mdoTableAsEnumsManager.getPrefixTableNames());
			restaurantPrefixTablesManagerViewBean.setTypes(tableTypesManager.findAll(lazy));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	@Override
	public List<IMdoDtoBean> findAll(MdoUserContext userContext, Long restaurantId) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<? extends IMdoBean> list = ((IRestaurantPrefixTablesDao) dao).findAll(restaurantId);
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
			List<? extends IMdoBean> list = ((IRestaurantPrefixTablesDao) dao).findAll(restaurantId, typeName);
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
