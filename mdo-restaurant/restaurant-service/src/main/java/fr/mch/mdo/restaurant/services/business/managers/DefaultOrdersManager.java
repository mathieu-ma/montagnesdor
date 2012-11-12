package fr.mch.mdo.restaurant.services.business.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.beans.dto.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.dao.locales.hibernate.DefaultLocalesDao;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.dao.orders.hibernate.DefaultOrderLinesDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantsDao;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultDinnerTablesDao;
import fr.mch.mdo.restaurant.beans.dto.ProductDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.tables.ManagedProductSpecialCode;
import fr.mch.mdo.restaurant.services.business.utils.DefaultOrdersDtoHelper;
import fr.mch.mdo.restaurant.services.business.utils.IOrdersDtoHelper;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrdersManager extends AbstractRestaurantManager implements IOrdersManager
{
	private ILocalesDao localesDao;
	private IRestaurantsDao restaurantsDao;
	private IRestaurantPrefixTablesDao restaurantPrefixTablesDao;
	private IRestaurantReductionTablesDao restaurantReductionTablesDao;
	private IProductsDao productsDao;
	private IProductSpecialCodesDao productSpecialCodeDao;
	private IOrderLinesDao orderLinesDao;
	private IOrdersDtoHelper helper;
	
	private static class LazyHolder {
		private static IOrdersManager instance = new DefaultOrdersManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultOrdersManager.class.getName()),
				DefaultDinnerTablesDao.getInstance());
	}

	private DefaultOrdersManager(ILogger logger, IDaoServices dao) {
		super.logger = logger;
		super.dao = dao;
		this.localesDao = DefaultLocalesDao.getInstance();
		this.restaurantsDao = DefaultRestaurantsDao.getInstance();
		this.restaurantPrefixTablesDao = DefaultRestaurantPrefixTablesDao.getInstance();
		this.restaurantReductionTablesDao = DefaultRestaurantReductionTablesDao.getInstance();
		this.productsDao = DefaultProductsDao.getInstance();
		this.productSpecialCodeDao = DefaultProductSpecialCodesDao.getInstance();
		this.orderLinesDao = DefaultOrderLinesDao.getInstance();
		this.helper = DefaultOrdersDtoHelper.getInstance(); 
	}

	public static IOrdersManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultOrdersManager() {
	}

	/**
	 * @return the localesDao
	 */
	public ILocalesDao getLocalesDao() {
		return localesDao;
	}

	/**
	 * @param localesDao the localesDao to set
	 */
	public void setLocalesDao(ILocalesDao localesDao) {
		this.localesDao = localesDao;
	}

	/**
	 * @return the restaurantsDao
	 */
	public IRestaurantsDao getRestaurantsDao() {
		return restaurantsDao;
	}

	/**
	 * @param restaurantsDao the restaurantsDao to set
	 */
	public void setRestaurantsDao(IRestaurantsDao restaurantsDao) {
		this.restaurantsDao = restaurantsDao;
	}

	/**
	 * @return the restaurantPrefixTablesDao
	 */
	public IRestaurantPrefixTablesDao getRestaurantPrefixTablesDao() {
		return restaurantPrefixTablesDao;
	}

	/**
	 * @param restaurantPrefixTablesDao the restaurantPrefixTablesDao to set
	 */
	public void setRestaurantPrefixTablesDao(
			IRestaurantPrefixTablesDao restaurantPrefixTablesDao) {
		this.restaurantPrefixTablesDao = restaurantPrefixTablesDao;
	}

	/**
	 * @return the restaurantReductionTablesDao
	 */
	public IRestaurantReductionTablesDao getRestaurantReductionTablesDao() {
		return restaurantReductionTablesDao;
	}

	/**
	 * @param restaurantReductionTablesDao the restaurantReductionTablesDao to set
	 */
	public void setRestaurantReductionTablesDao(
			IRestaurantReductionTablesDao restaurantReductionTablesDao) {
		this.restaurantReductionTablesDao = restaurantReductionTablesDao;
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
	 * @return the helper
	 */
	public IOrdersDtoHelper getHelper() {
		return helper;
	}

	/**
	 * @param utils the helper to set
	 */
	public void setHelper(IOrdersDtoHelper helper) {
		this.helper = helper;
	}
	
	@Override
	public List<DinnerTableDto> findAllTables(Long restaurantId, TableState state) throws MdoBusinessException {
		return this.findAllTablesFactoring(restaurantId, null, state);
	}
	
	@Override
	public List<DinnerTableDto> findAllTables(Long restaurantId, Long userAuthenticationId, TableState state) throws MdoBusinessException {
		return this.findAllTablesFactoring(restaurantId, userAuthenticationId, state);
	}

	private List<DinnerTableDto> findAllTablesFactoring(Long restaurantId, Long userAuthenticationId, TableState state) throws MdoBusinessException {
		List<DinnerTableDto> result = new ArrayList<DinnerTableDto>();
		
		// Get list of dinner tables
		try {
			List<DinnerTable> daoBeans = null;
			switch (state) {
				case ALTERABLE:
					if (userAuthenticationId != null) {
						daoBeans = ((IDinnerTablesDao) dao).findAllAlterable(restaurantId, userAuthenticationId, true);
					} else {
						daoBeans = ((IDinnerTablesDao) dao).findAllAlterable(restaurantId, true);
					}
				break;
				case PRINTED:
					if (userAuthenticationId != null) {
						daoBeans = ((IDinnerTablesDao) dao).findAllPrinted(restaurantId, userAuthenticationId, true);
					} else {
						daoBeans = ((IDinnerTablesDao) dao).findAllPrinted(restaurantId, true);
					}
				break;
				case NOT_PRINTED:
					if (userAuthenticationId != null) {
						daoBeans = ((IDinnerTablesDao) dao).findAllNotPrinted(restaurantId, userAuthenticationId, true);
					} else {
						daoBeans = ((IDinnerTablesDao) dao).findAllNotPrinted(restaurantId, true);
					}
				break;
				case CASHED:
					if (userAuthenticationId != null) {
						daoBeans = ((IDinnerTablesDao) dao).findAllCashed(restaurantId, userAuthenticationId, true);
					} else {
						daoBeans = ((IDinnerTablesDao) dao).findAllCashed(restaurantId, true);
					}
				break;
				default:
					logger.error("message.error.business.DefaultOrdersManager.findAllTablesFactoring.state");
					throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findAllTablesFactoring.state");
			}
			// Convert DinnerTable dao bean to DinnerTable visual bean 
			result = helper.findAllTablesFactoring(daoBeans);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findAllTablesFactoring", new Object[]{ restaurantId, userAuthenticationId, state }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findAllTablesFactoring", new Object[]{ restaurantId, userAuthenticationId, state }, e);
		}

		return result;
	}

	@Override
	public List<DinnerTableDto> findAllAlterableTables(Long restaurantId) throws MdoBusinessException {
		return this.findAllAlterableTables(restaurantId, null);
	}

	@Override
	public List<DinnerTableDto> findAllAlterableTables(Long restaurantId, Long userAuthenticationId) throws MdoBusinessException {
		return this.findAllTables(restaurantId, userAuthenticationId, TableState.ALTERABLE);
	}

	@Override
	public void deleteTable(Long id) throws MdoBusinessException {
		try {
			dao.delete(id);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.deleteTable", new Object[]{ id }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.deleteTable", new Object[]{ id }, e);
		}
	}

	@Override
	public OrderLineDto getOrderLine(Long restaurantId, String orderCode) throws MdoBusinessException {
		OrderLineDto result = new OrderLineDto();
		ProductDto product = null;
		ProductSpecialCodeDto productSpecialCode = null;
		if (orderCode != null && orderCode.length() > 0) {
			try {
		 		// The product special code always exists.
				MdoEntry<ManagedProductSpecialCode, ProductSpecialCodeDto> entry = this.getProductSpecialCode(restaurantId, orderCode);
				ManagedProductSpecialCode managedProductSpecialCode = entry.getKey();
				if (managedProductSpecialCode != null) {
					boolean mustCheckProductCode = false;
					String productCode = null;
					productSpecialCode = entry.getValue();
					// For instance productSpecialCode=="/" means we do not have to check the product code. 
					mustCheckProductCode = managedProductSpecialCode.mustCheckProductCode(productSpecialCode, orderCode);
					if (mustCheckProductCode) {
						// If instance productSpecialCode=="/" then the productCode is null. 
						productCode = managedProductSpecialCode.getProductCode(productSpecialCode, orderCode); //orderCode.substring(1);
						// Check there is a product.
						Product foundProduct = (Product) productsDao.find(restaurantId, productCode);
						if (foundProduct != null) {
							product = helper.fromProduct(foundProduct);
						}
					}
				}
			} catch (MdoException e) {
				logger.error("message.error.business.DefaultOrdersManager.getOrderLine", new Object[]{ restaurantId, orderCode }, e);
				throw new MdoBusinessException("message.error.business.DefaultOrdersManager.getOrderLine", new Object[]{ restaurantId, orderCode }, e);
			}
		}
		result.setProductSpecialCode(productSpecialCode);
		result.setProduct(product);
		return result;
	}
	
	@Override
	public MdoEntry<ManagedProductSpecialCode, ProductSpecialCodeDto> getProductSpecialCode(Long restaurantId, String orderCode) throws MdoBusinessException {
		MdoEntry<ManagedProductSpecialCode, ProductSpecialCodeDto> result = new MdoEntry<ManagedProductSpecialCode, ProductSpecialCodeDto>();
		if (orderCode != null) {
			try {
				ProductSpecialCode foundProductSpecialCode = null;
		 		// Check the is a product special code.
				// Must cache the list of productSpecialCodes by restaurantId(with AOP for instance).
				// The list must be ordered by the way that the last element is the default one.
				// The default productSpecialCode.getShortCode() must be ""(empty string).
				List<IMdoBean> productSpecialCodes = productSpecialCodeDao.findAllByRestaurant(restaurantId);
				for (IMdoBean iMdoBean : productSpecialCodes) {
					ProductSpecialCode productSpecialCode = (ProductSpecialCode) iMdoBean;
					ManagedProductSpecialCode managedProductSpecialCode = ManagedProductSpecialCode.getEnum(productSpecialCode.getCode().getName());
					// The default productSpecialCode.getShortCode() must be ""(empty string).
					// So the orderCode.startsWith(productSpecialCode.getShortCode()) is always true for empty string. 
					if (orderCode.startsWith(productSpecialCode.getShortCode())) {
						if (managedProductSpecialCode != null && managedProductSpecialCode.checkOrderCode(orderCode)) {
							result.setKey(managedProductSpecialCode);
							foundProductSpecialCode = productSpecialCode;
							break;
						}
					}
				}
				if (foundProductSpecialCode == null) {
					logger.error("message.error.business.DefaultOrdersManager.getProductSpecialCode.not.found", new Object[]{ restaurantId, orderCode });
					throw new MdoBusinessException("message.error.business.DefaultOrdersManager.getProductSpecialCode.not.found", new Object[]{ restaurantId, orderCode });
				}
				ProductSpecialCodeDto productSpecialCode = helper.fromProductSpecialCode(foundProductSpecialCode);
				result.setValue(productSpecialCode);
			} catch (MdoException e) {
				logger.error("message.error.business.DefaultOrdersManager.getProductSpecialCode", new Object[]{ restaurantId, orderCode }, e);
				throw new MdoBusinessException("message.error.business.DefaultOrdersManager.getProductSpecialCode", new Object[]{ restaurantId, orderCode }, e);
			}
		}
		return result;
	}
	
	@Override
	public void deleteOrderLine(Long id) throws MdoBusinessException {
		try {
			orderLinesDao.delete(id);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.deleteOrderLine", new Object[]{ id }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.deleteOrderLine", new Object[]{ id }, e);
		}
	}

	@Override
	public DinnerTableDto findTableHeader(Long restaurantId, String number) throws MdoBusinessException {
		DinnerTableDto result = this.findTableHeader(restaurantId, null, number);
		return result;
	}

	@Override
	public DinnerTableDto findTableHeader(Long restaurantId, Long userAuthenticationId, String number) throws MdoBusinessException {
		DinnerTableDto result = new DinnerTableDto();
		try {
			DinnerTable table = null;
			if (userAuthenticationId != null) {
				table = ((IDinnerTablesDao) dao).findTableHeader(restaurantId, userAuthenticationId, number);
			} else {
				table = ((IDinnerTablesDao) dao).findTableHeader(restaurantId, number);
			}
			// If table does not exist, don't forget to check if the number is a take-away table, i.e, set the take-away field.
			if (table == null) {
				table = new DinnerTable();
				table.setNumber(number);
				// The type is never null because of restaurant default table type.
				TableType type = this.findTableTypeByTableNumber(restaurantId, number);
				table.setType(type);
			}
			result = helper.findTableHeader(table);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findTableHeader", new Object[]{ restaurantId, userAuthenticationId, number }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findTableHeader", new Object[]{ restaurantId, userAuthenticationId, number }, e);
		}
		return result;
	}
	
	

	@Override
	public DinnerTableDto findTable(Long id, Locale locale) throws MdoBusinessException {
		DinnerTableDto result = new DinnerTableDto();
		try {
			DinnerTable table = ((IDinnerTablesDao) dao).findTable(id);
			if (table != null && table.getId() != null) {
				fr.mch.mdo.restaurant.dao.beans.Locale localeFromDb = (fr.mch.mdo.restaurant.dao.beans.Locale) localesDao.findByUniqueKey(locale.getLanguage());
				List<OrderLine> orders = orderLinesDao.findAllScalarFieldsByDinnerTableId(table.getId(), localeFromDb.getId());
				table.setOrders(new HashSet<OrderLine>(orders));
			} else {
				//TODO throw unknown error DinnerTable not found exception
				
			}
			result = helper.findTable(table);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findTable", new Object[]{ id, locale }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findTable", new Object[]{ id, locale }, e);
		}
		return result;
	}

	@Override
	public DinnerTableDto createTable(Long restaurantId, Long userAuthenticationId, String number, Integer customersNumber) throws MdoBusinessException {
		DinnerTableDto result = new DinnerTableDto();
		
		// 3 requests to perform: 
		DinnerTable table = this.prepareTableResetAndCreation(null, restaurantId, userAuthenticationId, number, customersNumber);
		try {
			// 1 for inserting
			table = (DinnerTable) this.dao.insert(table);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.createTable", new Object[]{ restaurantId, userAuthenticationId, number, customersNumber }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.createTable", new Object[]{ restaurantId, userAuthenticationId, number, customersNumber }, e);
		}
		
		result = helper.findTable(table);
		return result;
	}

	private RestaurantReductionTable findReductionTableByUniqueKey(Long restaurantId, Long typeId) throws MdoBusinessException {
		RestaurantReductionTable result = null;
		try {
			result = restaurantReductionTablesDao.findReductionTable(restaurantId, typeId);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findReductionTableByUniqueKey", new Object[]{ restaurantId, typeId }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findReductionTableByUniqueKey", new Object[]{ restaurantId, typeId }, e);
		}
		return result;
	}

	private BigDecimal getReductionRatio(BigDecimal checkingAmount, BigDecimal minAmountReduction, BigDecimal valueReduction) {
		BigDecimal result = BigDecimal.ZERO;
		if (minAmountReduction.compareTo(checkingAmount) <= 0 ) {
			result = valueReduction;
		}
		return result;
	}

	private TableType findTableTypeByTableNumber(Long restaurantId, String number) throws MdoBusinessException {
		TableType defaultTableType = null;
		try {
			defaultTableType = restaurantsDao.getDefaultTableType(restaurantId);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findTableTypeByTableNumber", new Object[]{ restaurantId, number }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findTableTypeByTableNumber", new Object[]{ restaurantId, number }, e);
		}
		TableType result = this.findTableTypeByTableNumber(restaurantId, defaultTableType, number);
		return result;
	}

	private TableType findTableTypeByTableNumber(Long restaurantId, TableType defaultTableType, String number) throws MdoBusinessException {
		List<RestaurantPrefixTable> prefixTableNames = null;
		try {
			prefixTableNames = restaurantPrefixTablesDao.findOnlyPrefixTables(restaurantId);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.findTableTypeByTableNumber", new Object[]{ restaurantId, defaultTableType, number }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.findTableTypeByTableNumber", new Object[]{ restaurantId, defaultTableType, number }, e);
		}
		TableType result = this.findTableTypeByTableNumber(prefixTableNames, defaultTableType, number);
		return result;
	}

	private TableType findTableTypeByTableNumber(List<RestaurantPrefixTable> prefixTableNames, TableType defaultTableType, String number) throws MdoBusinessException {
		TableType result = defaultTableType;
		for (RestaurantPrefixTable restaurantPrefixTable : prefixTableNames) {
			if (number.startsWith(restaurantPrefixTable.getPrefix().getName())) {
				result = restaurantPrefixTable.getType();
				break;
			}
		}
		return result;
	}

	@Override
	public Integer getTableOrdersSize(Long dinnerTableId) throws MdoBusinessException {
		Integer result = 0;
		try {
			result = orderLinesDao.getOrderLinesSize(dinnerTableId);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.getTableOrdersSize", new Object[]{ dinnerTableId }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.getTableOrdersSize", new Object[]{ dinnerTableId }, e);
		}
		return result;
	}

	@Override
	public void updateTableCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoBusinessException {
		try {
			((IDinnerTablesDao) dao).updateCustomersNumber(dinnerTableId, customersNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.updateTableCustomersNumber", new Object[]{ dinnerTableId, customersNumber }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.updateTableCustomersNumber", new Object[]{ dinnerTableId, customersNumber }, e);
		}
	}

	@Override
	public void resetTableCreationDateCustomersNumber(Long dinnerTableId) throws MdoBusinessException {
		try {
			((IDinnerTablesDao) dao).resetTableCreationDateCustomersNumber(dinnerTableId);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.resetTableCreationDateCustomersNumber", new Object[]{ dinnerTableId }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.resetTableCreationDateCustomersNumber", new Object[]{ dinnerTableId }, e);
		}
	}
	
	@Override
	public void resetTable(Long dinnerTableId, Long restaurantId, Long userAuthenticationId, String number, Integer customersNumber) throws MdoBusinessException {
		// 3 requests to perform: 
		DinnerTable table = this.prepareTableResetAndCreation(dinnerTableId, restaurantId, userAuthenticationId, number, customersNumber);
		try {
			// 1 for updating
			((IDinnerTablesDao) dao).updateResetTable(table);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultOrdersManager.resetTable", new Object[]{ dinnerTableId }, e);
			throw new MdoBusinessException("message.error.business.DefaultOrdersManager.resetTable", new Object[]{ dinnerTableId }, e);
		}
	}

	private DinnerTable prepareTableResetAndCreation(Long dinnerTableId, Long restaurantId,	Long userAuthenticationId, String number, Integer customersNumber) throws MdoBusinessException {
		DinnerTable result = helper.buildTableReset(dinnerTableId, restaurantId, userAuthenticationId, number, customersNumber);
		
		// 3 requests to perform: 
		// 2 for TableType 
		TableType type = this.findTableTypeByTableNumber(restaurantId, number);
		result.setType(type);
		// 1 for RestaurantReductionTable
		BigDecimal reductionRatio = BigDecimal.ZERO;
		RestaurantReductionTable reductionTable = this.findReductionTableByUniqueKey(restaurantId, type.getId());
		if (reductionTable != null) {
			reductionRatio = this.getReductionRatio(BigDecimal.ZERO, reductionTable.getMinAmount(), reductionTable.getValue());
		}
		result.setReductionRatio(reductionRatio);
		return result;
	}
}
