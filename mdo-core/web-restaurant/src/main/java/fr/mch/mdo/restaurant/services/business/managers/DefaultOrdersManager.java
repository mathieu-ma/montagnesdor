package fr.mch.mdo.restaurant.services.business.managers;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.dao.orders.hibernate.DefaultOrderLinesDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultDinnerTablesDao;
import fr.mch.mdo.restaurant.services.business.utils.DefaultOrdersUtils;
import fr.mch.mdo.restaurant.services.business.utils.IOrdersUtils;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrdersManager extends AbstractRestaurantManager implements IOrdersManager
{
	private IProductsDao productsDao;
	private IProductSpecialCodesDao productSpecialCodeDao;
	private IOrderLinesDao orderLinesDao;
	private IOrdersUtils utils;
	
	private static class LazyHolder {
		private static IOrdersManager instance = new DefaultOrdersManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultOrdersManager.class.getName()),
				DefaultDinnerTablesDao.getInstance());
	}

	private DefaultOrdersManager(ILogger logger, IDaoServices dao) {
		super.logger = logger;
		super.dao = dao;
		this.productsDao = DefaultProductsDao.getInstance();
		this.productSpecialCodeDao = DefaultProductSpecialCodesDao.getInstance();
		this.orderLinesDao = DefaultOrderLinesDao.getInstance();
		this.utils = DefaultOrdersUtils.getInstance(); 
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultOrdersManager() {
	}

	public static IOrdersManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the productsDao
	 */
	public IProductsDao getProductsDao() {
		return productsDao;
	}

	/**
	 * @param productsDao the productsDao to set
	 */
	public void setProductsDao(IProductsDao productsDao) {
		this.productsDao = productsDao;
	}

	/**
	 * @return the productSpecialCodeDao
	 */
	public IProductSpecialCodesDao getProductSpecialCodeDao() {
		return productSpecialCodeDao;
	}

	/**
	 * @param productSpecialCodeDao the productSpecialCodeDao to set
	 */
	public void setProductSpecialCodeDao(
			IProductSpecialCodesDao productSpecialCodeDao) {
		this.productSpecialCodeDao = productSpecialCodeDao;
	}

	/**
	 * @return the orderLinesDao
	 */
	public IOrderLinesDao getOrderLinesDao() {
		return orderLinesDao;
	}

	/**
	 * @param orderLinesDao the orderLinesDao to set
	 */
	public void setOrderLinesDao(IOrderLinesDao orderLinesDao) {
		this.orderLinesDao = orderLinesDao;
	}

	/**
	 * @return the utils
	 */
	public IOrdersUtils getUtils() {
		return utils;
	}

	/**
	 * @param utils the utils to set
	 */
	public void setUtils(IOrdersUtils utils) {
		this.utils = utils;
	}
}
